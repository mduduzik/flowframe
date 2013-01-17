package org.flowframe.ui.vaadin.expressions.utils.tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.flowframe.ui.vaadin.expressions.utils.SPELUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.ast.VariableReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.vaadin.data.Container.Filter;

public class SPUtilTests {
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSimple() {
/*		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("myname", "World");
		
		SpelExpressionParser expressionParser = new SpelExpressionParser();
		Expression expr = expressionParser.parseExpression("name == #myname");
		SpelNode ast = ((SpelExpression)expr).getAST();
		
		PropertyOrFieldReference prop = (PropertyOrFieldReference)ast.getChild(0);
		VariableReference var = (VariableReference)ast.getChild(1);
		ExpressionState varState = new ExpressionState(context);
		Object value = var.getValue(varState);
		Class valueType = var.getTypedValue(varState).getTypeDescriptor().getType();
		
		Class<? extends SpelNode> cls = ast.getClass();
		int count = ast.getChildCount();
		Assert.assertEquals(1, count);*/
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("myname", "World");
		Filter fltr = SPELUtil.toContainerFilter("name == #myname", paramMap);
		Assert.assertNotNull(fltr);
	}
}