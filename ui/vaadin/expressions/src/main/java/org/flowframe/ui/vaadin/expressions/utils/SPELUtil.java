package org.flowframe.ui.vaadin.expressions.utils;

import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.constants.RoleCustomCONSTANTS;
import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.portal.remote.services.IPortalRoleService;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.CompoundExpression;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.expression.spel.ast.OpEQ;
import org.springframework.expression.spel.ast.OpGE;
import org.springframework.expression.spel.ast.OpGT;
import org.springframework.expression.spel.ast.OpLE;
import org.springframework.expression.spel.ast.OpLT;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.expression.spel.ast.PropertyOrFieldReference;
import org.springframework.expression.spel.ast.VariableReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.Compare.Greater;
import com.vaadin.data.util.filter.Compare.GreaterOrEqual;
import com.vaadin.data.util.filter.Compare.Less;
import com.vaadin.data.util.filter.Compare.LessOrEqual;
import com.vaadin.data.util.filter.Or;

public class SPELUtil {
	private static void putDefaults(StandardEvaluationContext context) {
		context.setVariable("true", true);
		context.setVariable("false", false);
		context.setVariable("null", null);
	}
	
	public static Filter toContainerFilter(User tenantUser, IPortalRoleService portalRoleService,String spelWhereExpression, Map<String, Object> paramMap) {
		boolean hasRole = false;
		Filter filter = null;
		try
		{
			hasRole = portalRoleService.userHasRole(Long.toString(tenantUser.getUserId()),RoleCustomCONSTANTS.ROLE_CONX_ADMIN_CODE);
		}
		catch(Exception e) {
			throw new RuntimeException("Error obtaining role for user("+tenantUser.getScreenName()+")",e);
		}
		
		StandardEvaluationContext context = new StandardEvaluationContext();
		putDefaults(context);
		
		for (String key : paramMap.keySet()) {
			context.setVariable(key, paramMap.get(key));
		}

		SpelExpressionParser expressionParser = new SpelExpressionParser();
		if (!hasRole)
		{
			context.setVariable("tenantid", tenantUser.getId());
			if (spelWhereExpression != null)
				spelWhereExpression += " and tenant.id == #tenantid";
			else
				spelWhereExpression = "tenant.id == #tenantid";
		}
		else
		{
			
		}
			
		if (spelWhereExpression != null)
		{
			Expression expr = expressionParser.parseExpression(spelWhereExpression);
			SpelNode node = ((SpelExpression) expr).getAST();
			filter = translateOp(node, context);
		}

		return filter;
	}	

	public static Filter toContainerFilter(String spelWhereExpression, Map<String, Object> paramMap) {
		StandardEvaluationContext context = new StandardEvaluationContext();
		putDefaults(context);
		for (String key : paramMap.keySet()) {
			context.setVariable(key, paramMap.get(key));
		}

		SpelExpressionParser expressionParser = new SpelExpressionParser();
		Expression expr = expressionParser.parseExpression(spelWhereExpression);

		SpelNode node = ((SpelExpression) expr).getAST();

		return translateOp(node, context);
	}

	private static Filter translateOp(SpelNode node, EvaluationContext context) {
		/**
		 * Comparisons
		 */
		// EQ
		if (node instanceof OpEQ) {
			return translate((OpEQ) node, context);
		}
		// GT/GE
		else if (node instanceof OpGT) {
			return translate((OpGT) node, context);
		} else if (node instanceof OpGE) {
			return translate((OpGE) node, context);
		}
		// LT/LE
		else if (node instanceof OpLT) {
			return translate((OpLT) node, context);
		} else if (node instanceof OpLE) {
			return translate((OpLE) node, context);
		}
		/**
		 * Logical
		 */
		else if (node instanceof OpAnd) {
			return translate((OpAnd) node, context);
		} else if (node instanceof OpOr) {
			return translate((OpOr) node, context);
		} else
			return null;
	}

	/**
	 * 
	 * 
	 * Logical methods
	 * 
	 * 
	 */
	private static Filter translate(OpAnd node, EvaluationContext context) {
		SpelNode left = node.getLeftOperand();
		SpelNode right = node.getRightOperand();

		Filter leftFilter = translateOp(left, context);
		Filter rightFilter = translateOp(right, context);

		And op = new And(leftFilter, rightFilter);

		return op;
	}

	private static Filter translate(OpOr node, EvaluationContext context) {
		SpelNode left = node.getLeftOperand();
		SpelNode right = node.getRightOperand();

		Filter leftFilter = translateOp(left, context);
		Filter rightFilter = translateOp(right, context);

		Or op = new Or(leftFilter, rightFilter);

		return op;
	}

	/**
	 * 
	 * 
	 * Comparison methods
	 * 
	 * 
	 */
	public static Filter translate(OpEQ op, EvaluationContext context) {
		SpelNode left = op.getLeftOperand();
		SpelNode right = op.getRightOperand();

		Object leftOprnd = translatePForV(left, context);
		Object rightOprnd = translatePForV(right, context);

		Equal eqOp = new Equal(leftOprnd, rightOprnd);

		return eqOp;
	}

	private static Filter translate(OpGT node, EvaluationContext context) {
		SpelNode left = node.getLeftOperand();
		SpelNode right = node.getRightOperand();

		Object leftOprnd = translatePForV(left, context);
		Object rightOprnd = translatePForV(right, context);

		Greater op = new Greater(leftOprnd, rightOprnd);

		return op;
	}

	private static Filter translate(OpGE node, EvaluationContext context) {
		SpelNode left = node.getLeftOperand();
		SpelNode right = node.getRightOperand();

		Object leftOprnd = translatePForV(left, context);
		Object rightOprnd = translatePForV(right, context);

		GreaterOrEqual op = new GreaterOrEqual(leftOprnd, rightOprnd);

		return op;
	}

	private static Filter translate(OpLT node, EvaluationContext context) {
		SpelNode left = node.getLeftOperand();
		SpelNode right = node.getRightOperand();

		Object leftOprnd = translatePForV(left, context);
		Object rightOprnd = translatePForV(right, context);

		Less op = new Less(leftOprnd, rightOprnd);

		return op;
	}

	private static Filter translate(OpLE node, EvaluationContext context) {
		SpelNode left = node.getLeftOperand();
		SpelNode right = node.getRightOperand();

		Object leftOprnd = translatePForV(left, context);
		Object rightOprnd = translatePForV(right, context);

		LessOrEqual op = new LessOrEqual(leftOprnd, rightOprnd);

		return op;
	}

	/**
	 * 
	 * 
	 * Utility methods
	 * 
	 * 
	 */
	private static Object translatePForV(SpelNode node, EvaluationContext context) {
		Object pfOrV = null;
		if (node instanceof PropertyOrFieldReference) {
			pfOrV = ((PropertyOrFieldReference) node).getName();
		} else if (node instanceof VariableReference) {
			pfOrV = getVariableValue(((VariableReference) node), context);
		} else if (node instanceof CompoundExpression) {
			pfOrV = ((CompoundExpression) node).toStringAST();
		} else
			throw new IllegalArgumentException("Node(" + node.toString() + " is neither VariableReference or PropertyOrFieldReference");

		return pfOrV;
	}

	private static Object getVariableValue(VariableReference variableReference, EvaluationContext context) {
		ExpressionState varState = new ExpressionState(context);
		Object value = variableReference.getValue(varState);
		Class valueType = variableReference.getTypedValue(varState).getTypeDescriptor().getType();
		return value;
	}
}
