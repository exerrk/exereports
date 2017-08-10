/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2016 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.exerrk.engine;

import java.util.Locale;
import java.util.MissingResourceException;

import com.github.exerrk.engine.util.MessageProvider;
import com.github.exerrk.engine.util.MessageUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String EXCEPTION_MESSAGES_BUNDLE = "jasperreports_messages";
	public static final String EXCEPTION_MESSAGE_KEY_PREFIX = "com.github.exerrk.exception.";
	
	private String messageKey;
	private Object[] args;
	/**
	 * @deprecated To be removed.
	 */
	private JasperReportsContext jasperReportsContext;
	/**
	 * @deprecated To be removed.
	 */
	private Locale locale;


	/**
	 *
	 */
	public JRRuntimeException(String message)
	{
		super(message);
	}


	/**
	 *
	 */
	public JRRuntimeException(Throwable t)
	{
		super(t);
	}


	/**
	 *
	 */
	public JRRuntimeException(String message, Throwable t)
	{
		super(message, t);
	}


	/**
	 *
	 */
	public JRRuntimeException(String messageKey, Object[] args, Throwable t)
	{
		super(messageKey, t);
		this.messageKey = messageKey;
		this.args = args;
	}


	/**
	 *
	 */
	public JRRuntimeException(String messageKey, Object[] args)
	{
		super(messageKey);
		this.messageKey = messageKey;
		this.args = args;
	}


	/**
	 * @deprecated Replaced by {@link #JRRuntimeException(String, Object[])}.
	 */
	public JRRuntimeException(String messageKey, Object[] args, JasperReportsContext jasperReportsContext, Locale locale)
	{
		super(messageKey);
		this.messageKey = messageKey;
		this.args = args;
		this.jasperReportsContext = jasperReportsContext;
		this.locale = locale;
	}


	/**
	 *
	 */
	public String getMessageKey()
	{
		return messageKey;
	}


	/**
	 *
	 */
	public Object[] getArgs()
	{
		return args;
	}


	@Override
	public String getMessage()
	{
		return getMessage(jasperReportsContext, locale);
	}


	public String getMessage(JasperReportsContext jasperReportsContext, Locale locale)
	{
		if (messageKey != null)
		{
			return 
				resolveMessage(
					jasperReportsContext == null ? DefaultJasperReportsContext.getInstance() : jasperReportsContext, 
					locale == null ? Locale.getDefault() : locale
					);
		}
		return super.getMessage();
	}


	/**
	 *
	 */
	protected String resolveMessage(JasperReportsContext jasperReportsContext, Locale locale)
	{
		if (messageKey != null)
		{
			try
			{
				String bundleName = getMessageBundleName();
				MessageProvider messageProvider = MessageUtil.getInstance(jasperReportsContext).getMessageProvider(bundleName);
				return messageProvider.getMessage(getMessageKeyPrefix() + messageKey, args, locale);
			}
			catch (MissingResourceException e)
			{
			}
		}
		return messageKey;
	}
	
	protected String getMessageBundleName()
	{
		return EXCEPTION_MESSAGES_BUNDLE;
	}
	
	protected String getMessageKeyPrefix()
	{
		return EXCEPTION_MESSAGE_KEY_PREFIX;
	}
}
