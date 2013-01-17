package org.flowframe.ui.vaadin.expressions.utils;


import java.util.Map;

import org.apache.commons.collections.functors.EqualPredicate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.OpEQ;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.ast.VariableReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Or;

public class SPELUtil {
	public static Filter toContainerFilter(String spelWhereExpression, Map<String,Object> paramMap) {
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (String key : paramMap.keySet())
		{
			context.setVariable(key,paramMap.get(key));
		}
		
		SpelExpressionParser expressionParser = new SpelExpressionParser();
		Expression expr = expressionParser.parseExpression(spelWhereExpression);
		
		SpelNode node = ((SpelExpression)expr).getAST();
		
		if (node instanceof OpEQ)
			return translate((OpEQ)node,context);
		else
			return null;
	}
	
	public static Filter  translate(OpEQ op, EvaluationContext context) {
		SpelNode oprnd1 = op.getChild(0);
		SpelNode oprnd2 = op.getChild(1);
		
		Filter fltr = null;
		
		if (oprnd1 instanceof PropertyOrFieldReference &&  oprnd2 instanceof PropertyOrFieldReference)
		{
			fltr = new Compare.Equal(((PropertyOrFieldReference)oprnd1).getName(),((PropertyOrFieldReference)oprnd2).getName());
		}
		else if (oprnd1 instanceof PropertyOrFieldReference &&  oprnd2 instanceof VariableReference)
		{
			fltr = new Compare.Equal(((PropertyOrFieldReference)oprnd1).getName(),getVariableValue(((VariableReference)oprnd2),context));
		}
		else if (oprnd2 instanceof PropertyOrFieldReference &&  oprnd1 instanceof VariableReference)
		{
			fltr = new Compare.Equal(((PropertyOrFieldReference)oprnd2).getName(),getVariableValue(((VariableReference)oprnd1),context));
		}
		
		return fltr;
	}

	private static String getVariableValue(VariableReference variableReference, EvaluationContext context) {
		ExpressionState varState = new ExpressionState(context);
		Object value = variableReference.getValue(varState);
		Class valueType = variableReference.getTypedValue(varState).getTypeDescriptor().getType();
		if (valueType == String.class)
			return "'"+value+"'";
		else
			return value.toString();
	}
}
