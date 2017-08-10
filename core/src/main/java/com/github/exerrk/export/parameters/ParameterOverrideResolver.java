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
package com.github.exerrk.export.parameters;

import java.util.List;
import java.util.Map;

import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JRPropertiesUtil.PropertySuffix;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReportsContext;


/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class ParameterOverrideResolver implements ParameterResolver
{
	private final JRPropertiesUtil propertiesUtil;
	private final JasperPrint jasperPrint;
	private final Map<com.github.exerrk.engine.JRExporterParameter, Object> parameters;
	

	/**
	 *
	 */
	public ParameterOverrideResolver(
		JasperReportsContext jasperReportsContext,
		JasperPrint jasperPrint,
		Map<com.github.exerrk.engine.JRExporterParameter, Object> parameters
		)
	{
		this.propertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
		this.jasperPrint = jasperPrint;
		this.parameters = parameters;
	}
	
	
	@Override
	public String getStringParameter(com.github.exerrk.engine.JRExporterParameter parameter, String property)
	{
		if (parameters.containsKey(parameter))
		{
			return (String)parameters.get(parameter);
		}
		else
		{
			return 
				getPropertiesUtil().getProperty(
					jasperPrint.getPropertiesMap(),
					property
					);
		}
	}

	@Override
	public String[] getStringArrayParameter(com.github.exerrk.engine.JRExporterParameter parameter, String propertyPrefix)
	{
		String[] values = null; 
		if (parameters.containsKey(parameter))
		{
			values = (String[])parameters.get(parameter);
		}
		else
		{
			List<PropertySuffix> properties = JRPropertiesUtil.getProperties(jasperPrint.getPropertiesMap(), propertyPrefix);
			if (properties != null && !properties.isEmpty())
			{
				values = new String[properties.size()];
				for(int i = 0; i < values.length; i++)
				{
					values[i] = properties.get(i).getValue();
				}
			}
		}
		return values;
	}

	@Override
	public String getStringParameterOrDefault(com.github.exerrk.engine.JRExporterParameter parameter, String property)
	{
		if (parameters.containsKey(parameter))
		{
			String value = (String)parameters.get(parameter);
			if (value == null)
			{
				return getPropertiesUtil().getProperty(property);
			}
			else
			{
				return value;
			}
		}
		else
		{
			return
				getPropertiesUtil().getProperty(
					jasperPrint.getPropertiesMap(),
					property
					);
		}
	}

	@Override
	public boolean getBooleanParameter(com.github.exerrk.engine.JRExporterParameter parameter, String property, boolean defaultValue)
	{
		if (parameters.containsKey(parameter))
		{
			Boolean booleanValue = (Boolean)parameters.get(parameter);
			if (booleanValue == null)
			{
				return getPropertiesUtil().getBooleanProperty(property);
			}
			else
			{
				return booleanValue.booleanValue();
			}
		}
		else
		{
			return 
				getPropertiesUtil().getBooleanProperty(
					jasperPrint.getPropertiesMap(),
					property,
					defaultValue
					);
		}
	}

	@Override
	public int getIntegerParameter(com.github.exerrk.engine.JRExporterParameter parameter, String property, int defaultValue)
	{
		if (parameters.containsKey(parameter))
		{
			Integer integerValue = (Integer)parameters.get(parameter);
			if (integerValue == null)
			{
				return getPropertiesUtil().getIntegerProperty(property);
			}
			else
			{
				return integerValue.intValue();
			}
		}
		else
		{
			return 
				getPropertiesUtil().getIntegerProperty(
					jasperPrint.getPropertiesMap(),
					property,
					defaultValue
					);
		}
	}
	
	@Override
	public float getFloatParameter(com.github.exerrk.engine.JRExporterParameter parameter, String property, float defaultValue)
	{
		if (parameters.containsKey(parameter))
		{
			Float floatValue = (Float)parameters.get(parameter);
			if (floatValue == null)
			{
				return getPropertiesUtil().getFloatProperty(property);
			}
			else
			{
				return floatValue.floatValue();
			}
		}
		else
		{
			return 
				getPropertiesUtil().getFloatProperty(
					jasperPrint.getPropertiesMap(),
					property,
					defaultValue
					);
		}
	}
	
	@Override
	public Character getCharacterParameter(
		com.github.exerrk.engine.JRExporterParameter parameter,
		String property
		)
	{
		if (parameters.containsKey(parameter))
		{
			return (Character) parameters.get(parameter);
		}
		else
		{
			return getPropertiesUtil().getCharacterProperty(
					jasperPrint.getPropertiesMap(), property);
		}
	}

	/**
	 *
	 */
	private JRPropertiesUtil getPropertiesUtil()
	{
		return propertiesUtil;
	}
}
