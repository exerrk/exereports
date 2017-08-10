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
package com.github.exerrk.charts.fill;

import com.github.exerrk.charts.JRXySeries;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRHyperlink;
import com.github.exerrk.engine.JRHyperlinkHelper;
import com.github.exerrk.engine.JRPrintHyperlink;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.fill.JRCalculator;
import com.github.exerrk.engine.fill.JRExpressionEvalException;
import com.github.exerrk.engine.fill.JRFillHyperlinkHelper;
import com.github.exerrk.engine.fill.JRFillObjectFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRFillXySeries implements JRXySeries
{

	/**
	 *
	 */
	protected JRXySeries parent;

	private Comparable<?> series;
	private Number xValue;
	private Number yValue;
	private String label;
	private JRPrintHyperlink itemHyperlink;
	private Boolean autoSort;
	
	
	/**
	 *
	 */
	public JRFillXySeries(
		JRXySeries xySeries, 
		JRFillObjectFactory factory
		)
	{
		factory.put(xySeries, this);

		parent = xySeries;
	}


	@Override
	public JRExpression getSeriesExpression()
	{
		return parent.getSeriesExpression();
	}
		
	@Override
	public JRExpression getXValueExpression()
	{
		return parent.getXValueExpression();
	}
		
	@Override
	public JRExpression getYValueExpression()
	{
		return parent.getYValueExpression();
	}
		
	@Override
	public JRExpression getLabelExpression()
	{
		return parent.getLabelExpression();
	}
	
	
	@Override
	public Boolean getAutoSort()
	{
		return parent.getAutoSort();
	}
	
	
	/**
	 *
	 */
	protected Comparable<?> getSeries()
	{
		return series;
	}
		
	/**
	 *
	 */
	protected Number getXValue()
	{
		return xValue;
	}
		
	/**
	 *
	 */
	protected Number getYValue()
	{
		return yValue;
	}
		
	/**
	 *
	 */
	protected String getLabel()
	{
		return label;
	}
	
	protected JRPrintHyperlink getPrintItemHyperlink()
	{
		return itemHyperlink;
	}
	
	
	/**
	 *
	 */
	protected void evaluate(JRCalculator calculator) throws JRExpressionEvalException
	{
		series = (Comparable<?>)calculator.evaluate(getSeriesExpression()); 
		xValue = (Number)calculator.evaluate(getXValueExpression()); 
		yValue = (Number)calculator.evaluate(getYValueExpression());
		label = (String)calculator.evaluate(getLabelExpression());
		
		if (hasItemHyperlinks())
		{
			evaluateItemHyperlink(calculator);
		}
	}


	protected void evaluateItemHyperlink(JRCalculator calculator) throws JRExpressionEvalException
	{
		try
		{
			itemHyperlink = JRFillHyperlinkHelper.evaluateHyperlink(getItemHyperlink(), calculator, JRExpression.EVALUATION_DEFAULT);
		}
		catch (JRExpressionEvalException e)
		{
			throw e;
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}


	@Override
	public JRHyperlink getItemHyperlink()
	{
		return parent.getItemHyperlink();
	}

	
	public boolean hasItemHyperlinks()
	{
		return !JRHyperlinkHelper.isEmpty(getItemHyperlink()); 
	}

	@Override
	public Object clone() 
	{
		throw new UnsupportedOperationException();
	}
}
