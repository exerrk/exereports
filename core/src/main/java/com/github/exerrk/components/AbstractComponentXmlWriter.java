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
package com.github.exerrk.components;

import java.io.IOException;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JRReport;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.component.ComponentKey;
import com.github.exerrk.engine.component.ComponentXmlWriter;
import com.github.exerrk.engine.util.JRXmlWriteHelper;
import com.github.exerrk.engine.util.VersionComparator;
import com.github.exerrk.engine.util.XmlNamespace;
import com.github.exerrk.engine.xml.JRXmlBaseWriter;
import com.github.exerrk.engine.xml.JRXmlWriter;
import com.github.exerrk.properties.PropertyConstants;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @see ComponentsExtensionsRegistryFactory
 */
public abstract class AbstractComponentXmlWriter implements ComponentXmlWriter
{
	/**
	 * 
	 */
	public static final String PROPERTY_COMPONENTS_PREFIX = JRPropertiesUtil.PROPERTY_PREFIX + "components.";

	/**
	 * 
	 */
	@Property(
			name = "com.github.exerrk.components.{built_in_component_name}.version",
			category = PropertyConstants.CATEGORY_OTHER,
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT, PropertyScope.PART, PropertyScope.COMPONENT},
			sinceVersion = PropertyConstants.VERSION_4_8_0
			)
	public static final String PROPERTY_COMPONENTS_VERSION_SUFFIX = ".version";

	protected final JasperReportsContext jasperReportsContext;
	protected final VersionComparator versionComparator;
	
	/**
	 * 
	 */
	public AbstractComponentXmlWriter(JasperReportsContext jasperReportsContext)
	{
		this.jasperReportsContext = jasperReportsContext;
		this.versionComparator = new VersionComparator();
	}
	
	/**
	 * 
	 */
	public static String getVersion(JasperReportsContext jasperReportsContext, JRComponentElement componentElement, JRXmlWriter reportWriter) 
	{
		String version = null;

		ComponentKey componentKey = componentElement.getComponentKey();
		String versionProperty = PROPERTY_COMPONENTS_PREFIX + componentKey.getName() + PROPERTY_COMPONENTS_VERSION_SUFFIX;
		
		if (componentElement.getPropertiesMap().containsProperty(versionProperty))
		{
			version = componentElement.getPropertiesMap().getProperty(versionProperty);
		}
		else
		{
			JRReport report = reportWriter.getReport();
			version = JRPropertiesUtil.getInstance(jasperReportsContext).getProperty(report, versionProperty);
			
			if (version == null)
			{
				version = JRPropertiesUtil.getInstance(jasperReportsContext).getProperty(report, JRXmlBaseWriter.PROPERTY_REPORT_VERSION);
			}
		}
		
		return version;
	}
	
	/**
	 * 
	 */
	protected boolean isNewerVersionOrEqual(JRComponentElement componentElement, JRXmlWriter reportWriter, String oldVersion) //FIXMEVERSION can we pass something else then reportWriter?
	{
		return versionComparator.compare(getVersion(jasperReportsContext, componentElement, reportWriter), oldVersion) >= 0;
	}

	/*
	 *
	 */
	protected boolean isOlderVersionThan(JRComponentElement componentElement, JRXmlWriter reportWriter, String version) 
	{
		return versionComparator.compare(getVersion(jasperReportsContext, componentElement, reportWriter), version) < 0;
	}

	@SuppressWarnings("deprecation")
	protected void writeExpression(String name, JRExpression expression, boolean writeClass, JRComponentElement componentElement, JRXmlWriter reportWriter)  throws IOException
	{
		JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();
		if(isNewerVersionOrEqual(componentElement, reportWriter, JRConstants.VERSION_4_1_1))
		{
			writer.writeExpression(name, expression);
		}
		else
		{
			writer.writeExpression(name, expression, writeClass);
		}
	}
	
	@SuppressWarnings("deprecation")
	protected void writeExpression(String name, XmlNamespace namespace, JRExpression expression, boolean writeClass, JRComponentElement componentElement, JRXmlWriter reportWriter)  throws IOException
	{
		JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();
		if(isNewerVersionOrEqual(componentElement, reportWriter, JRConstants.VERSION_4_1_1))
		{
			writer.writeExpression(name, namespace, expression);
		}
		else
		{
			writer.writeExpression(name, namespace, expression, writeClass);
		}
	}
}
