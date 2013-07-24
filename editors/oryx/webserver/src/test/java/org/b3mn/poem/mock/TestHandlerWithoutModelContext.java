package org.b3mn.poem.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.b3mn.poem.Identity;
import org.b3mn.poem.handler.HandlerBase;
import org.b3mn.poem.util.HandlerWithoutModelContext;

@HandlerWithoutModelContext(uri="/test2", filterBrowser=true, denyPublicUserAccess=true)
public class TestHandlerWithoutModelContext extends HandlerBase {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response, Identity subject, Identity object) throws Exception {
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response, Identity subject, Identity object) throws Exception {
	
	}
	
}
