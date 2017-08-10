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
package com.github.exerrk.components.items.fill;

import com.github.exerrk.components.items.fill.FillItemData;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.fill.JRCalculator;
import com.github.exerrk.engine.fill.JRExpressionEvalException;
import com.github.exerrk.engine.fill.JRFillElementDataset;
import com.github.exerrk.engine.fill.JRFillExpressionEvaluator;
import com.github.exerrk.engine.fill.JRFillObjectFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class FillItemDataset extends JRFillElementDataset
{

	protected final FillItemData itemData;
//	protected List<FillMarker> markerList;
	//protected List<Map<String,Object>> evaluatedMarkers;
	protected JRFillExpressionEvaluator evaluator;
	protected byte evaluation = JRExpression.EVALUATION_DEFAULT;
	
	public FillItemDataset(FillItemData itemData, JRFillObjectFactory factory)
	{
		super(itemData.getDataset(), factory);

		this.itemData = itemData;

		factory.registerElementDataset(this);
	}

	@Override
	protected void customEvaluate(JRCalculator calculator)
			throws JRExpressionEvalException
	{
		try
		{
			itemData.evaluateItems(calculator, evaluation);
//			if(markerList != null && ! markerList.isEmpty()) 
//			{
//				for (FillMarker marker : markerList)
//				{
//					marker.evaluateProperties(calculator, evaluation);
//				}
//			}
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
	protected void customIncrement()
	{
//		if(markerList != null && ! markerList.isEmpty()) {
//			for (FillMarker marker : markerList)
//			{
//				Map<String,Object> evaluatedProperties = marker.getEvaluatedProperties();
//				if (evaluatedProperties != null)
//				{
//					if(evaluatedMarkers == null) {
//						evaluatedMarkers = new ArrayList<Map<String,Object>>();
//					}
//					evaluatedMarkers.add(evaluatedProperties);
//				}
//			}
//		}
		itemData.addEvaluateItems();
	}

	@Override
	protected void customInitialize()
	{
		//evaluatedMarkers = null;
	}

	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		//MapCompiler.collectExpressions(markerDataset, collector);
	}

	@Override
	public void increment()
	{
		super.increment();
	}
	
//	public List<Map<String,Object>> getEvaluatedMarkers()
//	{
//		return evaluatedMarkers;
//	}
	
	/**
	 * @return the evaluation
	 */
	public byte getEvaluation() {
		return evaluation;
	}

	/**
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(byte evaluation) {
		this.evaluation = evaluation;
	}
}