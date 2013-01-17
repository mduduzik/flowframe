package org.flowframe.ui.vaadin.expressions.utils.tests;

import java.util.Date;
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
	public void testCompoundWithParams() {
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("tenant",new Object());
		context.setVariable("id",1L);
		context.setVariable("code","test");
		context.setVariable("dateCreated",new Date());
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("tenant",new Object());	
		paramMap.put("id",1L);
		paramMap.put("code","test");
		paramMap.put("dateCreated",new Date());
		
		Filter filter = SPELUtil.toContainerFilter("tenant.id == #id AND tenant.code == #code AND tenant.dateCreated > #dateCreated", paramMap);
		Assert.assertNotNull(filter);
	}
}