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
package com.github.exerrk.governors;

import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.engine.JRAbstractScriptlet;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.scriptlets.DefaultScriptletFactory;
import com.github.exerrk.engine.scriptlets.ScriptletFactory;
import com.github.exerrk.engine.scriptlets.ScriptletFactoryContext;
import com.github.exerrk.engine.util.JRClassLoader;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class GovernorFactory implements ScriptletFactory
{

	private static final GovernorFactory INSTANCE = new GovernorFactory();
	
	private GovernorFactory()
	{
	}
	
	/**
	 * 
	 */
	public static GovernorFactory getInstance()
	{
		return INSTANCE;
	}

	@Override
	public List<JRAbstractScriptlet> getScriplets(ScriptletFactoryContext context) throws JRException
	{
		List<JRAbstractScriptlet> scriptlets = new ArrayList<JRAbstractScriptlet>();

		boolean maxPagesEnabled = JRPropertiesUtil.getInstance(context.getJasperReportsContext()).getBooleanProperty(context.getDataset(), MaxPagesGovernor.PROPERTY_MAX_PAGES_ENABLED, true);
		if (maxPagesEnabled)
		{
			int maxPages = JRPropertiesUtil.getInstance(context.getJasperReportsContext()).getIntegerProperty(context.getDataset(), MaxPagesGovernor.PROPERTY_MAX_PAGES, 0);
			if (maxPages > 0)
			{
				scriptlets.add(new MaxPagesGovernor(maxPages));
			}
		}
		
		boolean timeoutEnabled = JRPropertiesUtil.getInstance(context.getJasperReportsContext()).getBooleanProperty(context.getDataset(), TimeoutGovernor.PROPERTY_TIMEOUT_ENABLED, true);
		if (timeoutEnabled)
		{
			long timeout = JRPropertiesUtil.getInstance(context.getJasperReportsContext()).getLongProperty(context.getDataset(), TimeoutGovernor.PROPERTY_TIMEOUT, 0l);
			if (timeout > 0)
			{
				scriptlets.add(new TimeoutGovernor(timeout));
			}
		}
		
		return scriptlets;
	}
	
	/**
	 *
	 */
	protected JRAbstractScriptlet getScriptlet(String scriptletClassName) throws JRException
	{
		JRAbstractScriptlet scriptlet = null;

		try
		{
			Class<?> scriptletClass = JRClassLoader.loadClassForName(scriptletClassName);	
			scriptlet = (JRAbstractScriptlet) scriptletClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw 
				new JRException(
					DefaultScriptletFactory.EXCEPTION_MESSAGE_KEY_CLASS_LOADING_ERROR,
					new Object[]{scriptletClassName}, 
					e);
		}
		catch (Exception e)
		{
			throw 
				new JRException(
					DefaultScriptletFactory.EXCEPTION_MESSAGE_KEY_INSTANCE_LOADING_ERROR,
					new Object[]{scriptletClassName}, 
					e);
		}
		
		return scriptlet;
	}
	
}
