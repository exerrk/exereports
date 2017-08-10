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
package com.github.exerrk.engine;

import java.util.HashMap;
import java.util.Map;

import com.github.exerrk.charts.JRAreaPlot;
import com.github.exerrk.charts.JRBar3DPlot;
import com.github.exerrk.charts.JRBarPlot;
import com.github.exerrk.charts.JRBubblePlot;
import com.github.exerrk.charts.JRCandlestickPlot;
import com.github.exerrk.charts.JRCategoryDataset;
import com.github.exerrk.charts.JRCategorySeries;
import com.github.exerrk.charts.JRLinePlot;
import com.github.exerrk.charts.JRPie3DPlot;
import com.github.exerrk.charts.JRPieDataset;
import com.github.exerrk.charts.JRPiePlot;
import com.github.exerrk.charts.JRPieSeries;
import com.github.exerrk.charts.JRTimePeriodDataset;
import com.github.exerrk.charts.JRTimePeriodSeries;
import com.github.exerrk.charts.JRTimeSeries;
import com.github.exerrk.charts.JRTimeSeriesDataset;
import com.github.exerrk.charts.JRXyzDataset;
import com.github.exerrk.charts.JRXyzSeries;
import com.github.exerrk.engine.base.JRBaseFont;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class JRAbstractObjectFactory implements JRVisitor
{


	/**
	 *
	 */
	private Map<Object,Object> objectsMap = new HashMap<Object,Object>();
	private Object visitResult;


	/**
	 *
	 */
	protected Object get(Object object)
	{
		return objectsMap.get(object);
	}

	/**
	 *
	 */
	public void put(Object object, Object copy)
	{
		objectsMap.put(object, copy);
	}


	/**
	 *
	 */
	public Object getVisitResult(JRVisitable visitable)
	{
		if (visitable != null)
		{
			visitable.visit(this);
			return visitResult;
		}
		return null;
	}


	/**
	 *
	 */
	protected void setVisitResult(Object visitResult)
	{
		this.visitResult = visitResult;
	}


	/**
	 *
	 */
	public abstract JRDefaultStyleProvider getDefaultStyleProvider();


	/**
	 *
	 */
	public abstract JRStyle getStyle(JRStyle style);

	/**
	 * Sets a style or a style reference on an object.
	 * <p/>
	 * If the container includes a style (see {@link JRStyleContainer#getStyle() getStyle()},
	 * a copy of this style will be created via {@link #getStyle(JRStyle) getStyle(JRStyle)}
	 * and set on the object.
	 * <p/>
	 * In addition to this, the implementation needs to handle the case when the container includes
	 * an external style reference (see {@link JRStyleContainer#getStyleNameReference() getStyleNameReference()}.
	 * 
	 * @param setter a setter for the object on which the style should be set.
	 * @param styleContainer the original style container
	 * @see #getStyle(JRStyle)
	 */
	public abstract void setStyle(JRStyleSetter setter, JRStyleContainer styleContainer);
	
	
	/**
	 *
	 */
	public JRFont getFont(JRStyleContainer styleContainer, JRFont font)
	{
		JRBaseFont baseFont = null;

		if (font != null)
		{
			baseFont = (JRBaseFont)get(font);
			if (baseFont == null)
			{
				baseFont = new JRBaseFont(styleContainer, font, this);
			}
		}

		return baseFont;
	}

	
	/**
	 *
	 */
	public abstract JRPieDataset getPieDataset(JRPieDataset pieDataset);

	/**
	 *
	 */
	public abstract JRPiePlot getPiePlot(JRPiePlot piePlot);


	/**
	 *
	 */
	public abstract JRPie3DPlot getPie3DPlot(JRPie3DPlot pie3DPlot);


	/**
	 *
	 */
	public abstract JRCategoryDataset getCategoryDataset(JRCategoryDataset categoryDataset);


	/**
	 * 
	 */
	public abstract JRTimeSeriesDataset getTimeSeriesDataset( JRTimeSeriesDataset timeSeriesDataset );

	/**
	 * 
	 */
	public abstract JRTimePeriodDataset getTimePeriodDataset( JRTimePeriodDataset timePeriodDataset );

	/**
	 * 
	 */
	public abstract JRTimePeriodSeries getTimePeriodSeries( JRTimePeriodSeries timePeriodSeries );

	/**
	 * 
	 */
	public abstract JRTimeSeries getTimeSeries( JRTimeSeries timeSeries );

	/**
	 *
	 */
	public abstract JRPieSeries getPieSeries(JRPieSeries pieSeries);

	/**
	 *
	 */
	public abstract JRCategorySeries getCategorySeries(JRCategorySeries categorySeries);

	/**
	 *
	 */
	public abstract JRXyzDataset getXyzDataset( JRXyzDataset xyzDataset );

	/**
	 *
	 */
	public abstract JRXyzSeries getXyzSeries( JRXyzSeries xyzSeries );


	/**
	 *
	 */
	public abstract JRBarPlot getBarPlot(JRBarPlot barPlot);

	/**
	 *
	 */
	public abstract JRBar3DPlot getBar3DPlot( JRBar3DPlot barPlot );


	/**
	 *
	 */
	public abstract JRLinePlot getLinePlot( JRLinePlot linePlot );


	/**
	 *
	 */
	public abstract JRAreaPlot getAreaPlot( JRAreaPlot areaPlot );


	/**
	 *
	 */
	public abstract JRBubblePlot getBubblePlot( JRBubblePlot bubblePlot );


	/**
	 *
	 */
	public abstract JRCandlestickPlot getCandlestickPlot(JRCandlestickPlot candlestickPlot);


	/**
	 *
	 */
	public abstract JRConditionalStyle getConditionalStyle(JRConditionalStyle conditionalStyle, JRStyle parentStyle);

	public abstract JRExpression getExpression(JRExpression expression, boolean assignNotUsedId);
	
	public JRExpression getExpression(JRExpression expression)
	{
		return getExpression(expression, false);
	}
}
