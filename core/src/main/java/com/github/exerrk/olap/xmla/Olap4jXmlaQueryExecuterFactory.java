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
package com.github.exerrk.olap.xmla;

import java.util.Map;

import com.github.exerrk.engine.JRDataset;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRValueParameter;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.query.JRQueryExecuter;


/**
 * @author swood
 */
public class Olap4jXmlaQueryExecuterFactory extends JRXmlaQueryExecuterFactory
{

	private final static Object[] XMLA_BUILTIN_PARAMETERS = { 
		PARAMETER_XMLA_URL, "java.lang.String", 
		PARAMETER_XMLA_DATASOURCE, "java.lang.String", 
		PARAMETER_XMLA_CATALOG, "java.lang.String",
		PARAMETER_XMLA_USER, "java.lang.String",
		PARAMETER_XMLA_PASSWORD, "java.lang.String",
	};

	@Override
	public Object[] getBuiltinParameters()
	{
		return XMLA_BUILTIN_PARAMETERS;
	}

	@Override
	public JRQueryExecuter createQueryExecuter(
		JasperReportsContext jasperReportsContext, 
		JRDataset dataset, 
		Map<String, ? extends JRValueParameter> parameters
		) throws JRException
	{
		return new Olap4jXmlaQueryExecuter(jasperReportsContext, dataset, parameters);
	}

}
