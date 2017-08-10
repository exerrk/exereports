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

import java.util.Map;

import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.export.ExporterOutput;


/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractParametersExporterOutput implements ExporterOutput
{
	public static final String EXCEPTION_MESSAGE_KEY_NO_OUTPUT_SPECIFIED = "export.parameters.no.output.specified";

	/**
	 * 
	 */
	private final JRPropertiesUtil propertiesUtil;
	protected final Map<com.github.exerrk.engine.JRExporterParameter, Object> parameters;
	private ParameterResolver parameterResolver;
	
	/**
	 * 
	 */
	public AbstractParametersExporterOutput(
		JasperReportsContext jasperReportsContext,
		Map<com.github.exerrk.engine.JRExporterParameter, Object> parameters,
		JasperPrint jasperPrint
		)
	{
		this.propertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
		this.parameters = parameters;

		boolean parametersOverrideHints;
		Boolean param = (Boolean) parameters.get(com.github.exerrk.engine.JRExporterParameter.PARAMETERS_OVERRIDE_REPORT_HINTS);
		if (param == null)
		{
			parametersOverrideHints = getPropertiesUtil().getBooleanProperty(
				com.github.exerrk.engine.JRExporterParameter.PROPERTY_EXPORT_PARAMETERS_OVERRIDE_REPORT_HINTS
				);
		}
		else
		{
			parametersOverrideHints = param.booleanValue();
		}
		
		if (parametersOverrideHints)
		{
			parameterResolver = 
				new ParameterOverrideResolver(
					jasperReportsContext,
					jasperPrint,
					parameters
					);
		}
		else
		{
			parameterResolver = 
				new ParameterOverriddenResolver(
					jasperReportsContext,
					jasperPrint,
					parameters
					);
		}

	}
	

	/**
	 * 
	 */
	protected ParameterResolver getParameterResolver()
	{
		return parameterResolver;
	}

	
	/**
	 * 
	 */
	protected JRPropertiesUtil getPropertiesUtil()
	{
		return propertiesUtil;
	}
	

}
