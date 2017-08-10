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
package com.github.exerrk.engine.fill;

import java.util.Map;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class FillExpressionOldValues extends FillBaseExpressionValues
{

	public FillExpressionOldValues(JREvaluator evaluator, Map<String, JRFillParameter> parametersMap,
			Map<String, JRFillField> fieldsMap, Map<String, JRFillVariable> variablesMap)
	{
		super(evaluator, parametersMap, fieldsMap, variablesMap);
	}

	@Override
	public Object getFieldValue(String fieldName)
	{
		return field(fieldName).getOldValue();
	}

	@Override
	public Object getVariableValue(String variableName)
	{
		return variable(variableName).getOldValue();
	}

}
