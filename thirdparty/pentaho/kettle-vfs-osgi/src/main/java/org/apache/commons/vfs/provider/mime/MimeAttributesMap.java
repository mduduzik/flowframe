/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs.provider.mime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A map which tries to allow access to the various aspects of the mail
 */
public class MimeAttributesMap implements Map
{
	private Log log = LogFactory.getLog(MimeAttributesMap.class);

	private final static String OBJECT_PREFIX = "obj.";

	private final Part part;
	private Map backingMap;

	private final Map mimeMessageGetters = new TreeMap();

	public MimeAttributesMap(Part part)
	{
		this.part = part;
		addMimeMessageMethod(part.getClass().getMethods());
		addMimeMessageMethod(part.getClass().getDeclaredMethods());
	}

	private void addMimeMessageMethod(Method[] methods)
	{
		for (int i = 0; i < methods.length; i++)
		{
			Method method = methods[i];
			if (!Modifier.isPublic(method.getModifiers()))
			{
				continue;
			}
			if (method.getParameterTypes().length > 0)
			{
				continue;
			}

			if (method.getName().startsWith("get"))
			{
				mimeMessageGetters.put(method.getName().substring(3), method);
			}
			else if (method.getName().startsWith("is"))
			{
				mimeMessageGetters.put(method.getName().substring(2), method);
			}
		}
	}

	private Map getMap()
	{
		if (backingMap == null)
		{
			backingMap = createMap();
		}

		return backingMap;
	}

	private Map createMap()
	{
		Map ret = new TreeMap();

		Enumeration headers;
		try
		{
			headers = part.getAllHeaders();
		}
		catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}

		// add all headers
		while (headers.hasMoreElements())
		{
			Header header = (Header) headers.nextElement();
			String headerName = header.getName();

			Object values = ret.get(headerName);

			if (values == null)
			{
				ret.put(headerName, header.getValue());
			}
			else if (values instanceof String)
			{
				List newValues = new ArrayList();
				newValues.add(values);
				newValues.add(header.getValue());
				ret.put(headerName, newValues);
			}
			else if (values instanceof List)
			{
				((List) values).add(header.getValue());
			}
		}

		// add all simple get/is results (with obj. prefix)
		Iterator iterEntries = mimeMessageGetters.entrySet().iterator();
		while (iterEntries.hasNext())
		{
			Map.Entry entry = (Map.Entry) iterEntries.next();
			String name = (String) entry.getKey();
			Method method = (Method) entry.getValue();

			try
			{
				Object value = method.invoke(part, null);
				ret.put(OBJECT_PREFIX + name, value);
			}
			catch (IllegalAccessException e)
			{
				log.debug(e.getLocalizedMessage(), e);
			}
			catch (InvocationTargetException e)
			{
				log.debug(e.getLocalizedMessage(), e);
			}
		}

		// add extended fields (with obj. prefix too)
		if (part instanceof MimeMessage)
		{
			MimeMessage message = (MimeMessage) part;
			try
			{
				Address[] address = message.getRecipients(MimeMessage.RecipientType.BCC);
				ret.put(OBJECT_PREFIX + "Recipients.BCC", address);
			}
			catch (MessagingException e)
			{
				log.debug(e.getLocalizedMessage(), e);
			}
			try
			{
				Address[] address = message.getRecipients(MimeMessage.RecipientType.CC);
				ret.put(OBJECT_PREFIX + "Recipients.CC", address);
			}
			catch (MessagingException e)
			{
				log.debug(e.getLocalizedMessage(), e);
			}
			try
			{
				Address[] address = message.getRecipients(MimeMessage.RecipientType.TO);
				ret.put(OBJECT_PREFIX + "Recipients.TO", address);
			}
			catch (MessagingException e)
			{
				log.debug(e.getLocalizedMessage(), e);
			}
			try
			{
				Address[] address = message.getRecipients(MimeMessage.RecipientType.NEWSGROUPS);
				ret.put(OBJECT_PREFIX + "Recipients.NEWSGROUPS", address);
			}
			catch (MessagingException e)
			{
				log.debug(e.getLocalizedMessage(), e);
			}
		}

		return ret;
	}

	public int size()
	{
		return getMap().size();
	}

	public boolean isEmpty()
	{
		return getMap().size() < 1;
	}

	public boolean containsKey(Object key)
	{
		return getMap().containsKey(key);
	}

	public boolean containsValue(Object value)
	{
		return getMap().containsValue(value);
	}

	public Object get(Object key)
	{
		return getMap().get(key);
	}

	public Object put(Object key, Object value)
	{
		throw new UnsupportedOperationException();
	}

	public Object remove(Object key)
	{
		throw new UnsupportedOperationException();
	}

	public void putAll(Map t)
	{
		throw new UnsupportedOperationException();
	}

	public void clear()
	{
		throw new UnsupportedOperationException();
	}

	public Set keySet()
	{
		return Collections.unmodifiableSet(getMap().keySet());
	}

	public Collection values()
	{
		return Collections.unmodifiableCollection(getMap().values());
	}

	public Set entrySet()
	{
		return Collections.unmodifiableSet(getMap().entrySet());
	}
}
