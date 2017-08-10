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
package com.github.exerrk.engine.util.xml;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.util.JRSingletonCache;
import com.github.exerrk.properties.PropertyConstants;

/**
 * Helper class used to instantiate {@link JRXPathExecuter XPath executers}.
 * <p/>
 * The {@link JRXPathExecuterFactory XPath executer factory} class name is given by the
 * {@link #PROPERTY_XPATH_EXECUTER_FACTORY com.github.exerrk.xpath.executer.factory} property.
 * The class should have a public default constructor so that it can be instantiated via reflection.
 * <p/>
 * By default, {@link XalanXPathExecuter XPath executers} based on <a href="http://xml.apache.org/xalan-j/" target="_blank">Apache Xalan</a>
 * are used.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public final class JRXPathExecuterUtils
{

	public static final String EXCEPTION_MESSAGE_KEY_XPATH_EXECUTER_FACTORY_NOT_FOUND = "util.xml.xpath.executer.factory.property.not.found";
	/**
	 * Property that holds the {@link JRXPathExecuterFactory XPath executer factory} class name.
	 */
	@Property(
			category = PropertyConstants.CATEGORY_DATA_SOURCE,
			defaultValue = "com.github.exerrk.engine.util.xml.XalanXPathExecuterFactory",
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_2_0_0
			)
	public static final String PROPERTY_XPATH_EXECUTER_FACTORY = JRPropertiesUtil.PROPERTY_PREFIX + "xpath.executer.factory";
	
	private static final JRSingletonCache<JRXPathExecuterFactory> cache = 
			new JRSingletonCache<JRXPathExecuterFactory>(JRXPathExecuterFactory.class);
	
	
	/**
	 * Return an {@link JRXPathExecuterFactory XPath executer factory} instance.
	 * 
	 * @return a JRXPathExecuterFactory instance
	 * @throws JRException if the {@link #PROPERTY_XPATH_EXECUTER_FACTORY XPath factory property} is not defined
	 * or the factory cannot be instantiated.
	 */
	public static JRXPathExecuterFactory getXPathExecuterFactory(JasperReportsContext jasperReportsContext) throws JRException
	{
		String factoryClassName = JRPropertiesUtil.getInstance(jasperReportsContext).getProperty(PROPERTY_XPATH_EXECUTER_FACTORY);
		if (factoryClassName == null)
		{
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_XPATH_EXECUTER_FACTORY_NOT_FOUND,
					new Object[]{PROPERTY_XPATH_EXECUTER_FACTORY});
		}
		
		return cache.getCachedInstance(factoryClassName);
	}
	
	
	/**
	 * Produces an {@link JRXPathExecuter XPath executer} instance by means of the factory
	 * returned by {@link #getXPathExecuterFactory(JasperReportsContext) getXPathExecuterFactory(JasperReportsContext)}.
	 * 
	 * @return an JRXPathExecuter instance
	 * @throws JRException if the {@link #PROPERTY_XPATH_EXECUTER_FACTORY XPath factory property} is not defined
	 * or the factory cannot be instantiated.
	 */
	public static JRXPathExecuter getXPathExecuter(JasperReportsContext jasperReportsContext) throws JRException
	{
		JRXPathExecuterFactory executerFactory = getXPathExecuterFactory(jasperReportsContext);
		return executerFactory.getXPathExecuter();
	}
	

	private JRXPathExecuterUtils()
	{
	}
}
