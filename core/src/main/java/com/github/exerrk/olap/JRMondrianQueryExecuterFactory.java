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
package com.github.exerrk.olap;

import java.util.Map;

import com.github.exerrk.engine.JRDataset;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRValueParameter;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.query.AbstractQueryExecuterFactory;
import com.github.exerrk.engine.query.JRQueryExecuter;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class JRMondrianQueryExecuterFactory extends AbstractQueryExecuterFactory
{
	/**
	 * Built-in parameter holding the value of the Mondrian connection to be used for creating the query.
	 */
	public final static String PARAMETER_MONDRIAN_CONNECTION = "MONDRIAN_CONNECTION";
	
	private final static Object[] MONDRIAN_BUILTIN_PARAMETERS = {
		PARAMETER_MONDRIAN_CONNECTION,  "mondrian.olap.Connection",
		};
	
	@Override
	public Object[] getBuiltinParameters()
	{
		return MONDRIAN_BUILTIN_PARAMETERS;
	}

	@Override
	public JRQueryExecuter createQueryExecuter(
		JasperReportsContext jasperReportsContext, 
		JRDataset dataset, 
		Map<String,? extends JRValueParameter> parameters
		) throws JRException
	{
		return new JRMondrianQueryExecuter(jasperReportsContext, dataset, parameters);
	}

	@Override
	public boolean supportsQueryParameterType(String className)
	{
		return true;
	}
}
