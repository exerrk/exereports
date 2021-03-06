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

import com.github.exerrk.engine.CommonReturnValue;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRVariable;
import com.github.exerrk.engine.VariableReturnValue;
import com.github.exerrk.engine.type.CalculationEnum;

/**
 * Class used to instantiate sub datasets.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public abstract class AbstractVariableReturnValueSourceContext implements FillReturnValues.SourceContext
{
	public static final String EXCEPTION_MESSAGE_KEY_NUMERIC_TYPE_REQUIRED = "fill.return.values.numeric.type.required";
	public static final String EXCEPTION_MESSAGE_KEY_SOURCE_NOT_FOUND = "fill.return.values.source.not.found";
	public static final String EXCEPTION_MESSAGE_KEY_VARIABLE_NOT_ASSIGNABLE = "fill.return.values.variable.not.assignable";

	@Override
	public void check(CommonReturnValue commonReturnValue) throws JRException
	{
		VariableReturnValue returnValue = (VariableReturnValue)commonReturnValue;
		
		String subreportVariableName = returnValue.getFromVariable();
		JRVariable subrepVariable = getFromVariable(subreportVariableName);
		if (subrepVariable == null)
		{
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_SOURCE_NOT_FOUND,  
					new Object[]{subreportVariableName, returnValue.getToVariable()} 
					);
		}
		
		JRVariable variable = getToVariable(returnValue.getToVariable());
		if (
			returnValue.getCalculation() == CalculationEnum.COUNT
			|| returnValue.getCalculation() == CalculationEnum.DISTINCT_COUNT
			)
		{
			if (!Number.class.isAssignableFrom(variable.getValueClass()))
			{
				throw 
					new JRException(
						EXCEPTION_MESSAGE_KEY_NUMERIC_TYPE_REQUIRED,  
						new Object[]{returnValue.getToVariable()} 
						);
			}
		}
		else if (!variable.getValueClass().isAssignableFrom(subrepVariable.getValueClass()) &&
				!(Number.class.isAssignableFrom(variable.getValueClass()) && Number.class.isAssignableFrom(subrepVariable.getValueClass())))
		{
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_VARIABLE_NOT_ASSIGNABLE,  
					new Object[]{returnValue.getToVariable(), subreportVariableName}
					);
		}
	}
	
	public abstract JRVariable getFromVariable(String name);

	public abstract JRVariable getToVariable(String name);
}
