package org.vaadin.mvp.tests.eventbus;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/EventBusTests-module-context.xml"})
public class EventBusTests extends AbstractJUnit4SpringContextTests {

	@Before
	public void init(){
	}
	
    @Test
    public void testFactory() throws Exception {   	
    }	
}
