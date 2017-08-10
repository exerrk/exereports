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
package com.github.exerrk.components.table.fill;

import java.util.Map;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.fill.DatasetExpressionEvaluator;
import com.github.exerrk.engine.fill.JRExpressionEvalException;
import com.github.exerrk.engine.fill.JRFillExpressionEvaluator;
import com.github.exerrk.engine.fill.JRFillField;
import com.github.exerrk.engine.fill.JRFillParameter;
import com.github.exerrk.engine.fill.JRFillVariable;
import com.github.exerrk.engine.type.WhenResourceMissingTypeEnum;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class FillExpressionEvaluatorDatasetAdapter implements DatasetExpressionEvaluator
{

	private final JRFillExpressionEvaluator evaluator;
	
	public FillExpressionEvaluatorDatasetAdapter(JRFillExpressionEvaluator evaluator)
	{
		this.evaluator = evaluator;
	}

	@Override
	public void init(Map<String, JRFillParameter> parametersMap,
			Map<String, JRFillField> fieldsMap,
			Map<String, JRFillVariable> variablesMap,
			WhenResourceMissingTypeEnum resourceMissingType,
			boolean ignoreNPE) throws JRException
	{
		// NOP
	}

	@Override
	public Object evaluate(JRExpression expression) throws JRExpressionEvalException
	{
		try
		{
			return evaluator.evaluate(expression, JRExpression.EVALUATION_DEFAULT);
		}
		catch (JRException e)
		{
			throw new JRExpressionEvalException(expression, e);
		}
	}

	@Override
	public Object evaluateOld(JRExpression expression)
			throws JRExpressionEvalException
	{
		try
		{
			return evaluator.evaluate(expression, JRExpression.EVALUATION_OLD);
		}
		catch (JRException e)
		{
			throw new JRExpressionEvalException(expression, e);
		}
	}

	@Override
	public Object evaluateEstimated(JRExpression expression)
			throws JRExpressionEvalException
	{
		try
		{
			return evaluator.evaluate(expression, JRExpression.EVALUATION_ESTIMATED);
		}
		catch (JRException e)
		{
			throw new JRExpressionEvalException(expression, e);
		}
	}

}
