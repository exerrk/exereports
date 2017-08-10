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
package com.github.exerrk.components.charts;

import java.awt.Color;

import com.github.exerrk.charts.type.EdgeEnum;
import com.github.exerrk.components.spiderchart.SpiderChartCompiler;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRFont;
import com.github.exerrk.engine.JRHyperlinkParameter;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.fill.JRFillObjectFactory;
import com.github.exerrk.engine.type.HyperlinkTypeEnum;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class FillChartSettings implements ChartSettings
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/**
	 *
	 */
	protected ChartSettings parent;

	/**
	 *
	 */
	public FillChartSettings(
		ChartSettings chartSettings, 
		JRFillObjectFactory factory
		)
	{
		factory.put(chartSettings, this);
		parent = chartSettings;
	}

	/**
	 * @see com.github.exerrk.engine.JRAnchor#getAnchorNameExpression()
	 */
	@Override
	public JRExpression getAnchorNameExpression() {
		return parent.getAnchorNameExpression();
	}

	/**
	 * @see com.github.exerrk.engine.JRAnchor#getBookmarkLevel()
	 */
	@Override
	public int getBookmarkLevel() {
		
		return parent.getBookmarkLevel();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		
		ChartSettings clone = null;
		
		try
		{
			clone = (ChartSettings)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
		return clone;
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getChartType()
	 */
	@Override
	public byte getChartType() {
		
		return parent.getChartType();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getLegendBackgroundColor()
	 */
	@Override
	public Color getLegendBackgroundColor() {
		
		return parent.getLegendBackgroundColor();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getLegendColor()
	 */
	@Override
	public Color getLegendColor() {
		
		return parent.getLegendColor();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getLegendFont()
	 */
	@Override
	public JRFont getLegendFont() {
		
		return parent.getLegendFont();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getLegendPosition()
	 */
	@Override
	public EdgeEnum getLegendPosition() {
		
		return parent.getLegendPosition();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getRenderType()
	 */
	@Override
	public String getRenderType() {
		
		return parent.getRenderType() == null ? JRChart.RENDER_TYPE_DRAW : parent.getRenderType();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getShowLegend()
	 */
	@Override
	public Boolean getShowLegend() {
		
		return parent.getShowLegend();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getSubtitleColor()
	 */
	@Override
	public Color getSubtitleColor() {
		
		return parent.getSubtitleColor();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getSubtitleExpression()
	 */
	@Override
	public JRExpression getSubtitleExpression() {
		
		return parent.getSubtitleExpression();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getSubtitleFont()
	 */
	@Override
	public JRFont getSubtitleFont() {
		
		return parent.getSubtitleFont();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getTitleColor()
	 */
	@Override
	public Color getTitleColor() {
		
		return parent.getTitleColor();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getTitleExpression()
	 */
	@Override
	public JRExpression getTitleExpression() {
		
		return parent.getTitleExpression();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getTitleFont()
	 */
	@Override
	public JRFont getTitleFont() {
		
		return parent.getTitleFont();
	}

	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getTitlePosition()
	 */
	@Override
	public EdgeEnum getTitlePosition() {
		
		return parent.getTitlePosition();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkAnchorExpression()
	 */
	@Override
	public JRExpression getHyperlinkAnchorExpression() {
		
		return parent.getHyperlinkAnchorExpression();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkPageExpression()
	 */
	@Override
	public JRExpression getHyperlinkPageExpression() {
		
		return parent.getHyperlinkPageExpression();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkParameters()
	 */
	@Override
	public JRHyperlinkParameter[] getHyperlinkParameters() {
		
		return parent.getHyperlinkParameters();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkReferenceExpression()
	 */
	@Override
	public JRExpression getHyperlinkReferenceExpression() {
		
		return parent.getHyperlinkReferenceExpression();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkWhenExpression()
	 */
	@Override
	public JRExpression getHyperlinkWhenExpression() {
		
		return parent.getHyperlinkWhenExpression();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkTarget()
	 */
	@Override
	public byte getHyperlinkTarget() {
		
		return parent.getHyperlinkTarget();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkTooltipExpression()
	 */
	@Override
	public JRExpression getHyperlinkTooltipExpression() {
		
		return parent.getHyperlinkTooltipExpression();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getHyperlinkTypeValue()
	 */
	@Override
	public HyperlinkTypeEnum getHyperlinkTypeValue() {
		
		return parent.getHyperlinkTypeValue();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getLinkTarget()
	 */
	@Override
	public String getLinkTarget() {
		
		return parent.getLinkTarget();
	}

	/**
	 * @see com.github.exerrk.engine.JRHyperlink#getLinkType()
	 */
	@Override
	public String getLinkType() {
		
		return parent.getLinkType();
	}
	
	/**
	 * @see com.github.exerrk.components.charts.ChartSettings#getLegendColor()
	 */
	@Override
	public Color getBackcolor() {
		
		return parent.getBackcolor();
	}

	@Override
	public String getCustomizerClass()
	{
		return parent.getCustomizerClass();
	}
	
	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		SpiderChartCompiler.collectExpressions(this, collector);
	}

}
