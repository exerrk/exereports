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
package com.github.exerrk.charts.xml;

import com.github.exerrk.charts.design.JRDesignPie3DPlot;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.xml.JRBaseFactory;
import com.github.exerrk.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;


/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 */
public class JRPie3DPlotFactory extends JRBaseFactory
{
	/**
	 *
	 */
	private static final String ATTRIBUTE_depthFactor = "depthFactor";
	private static final String ATTRIBUTE_isShowLabels = "isShowLabels";

	@Override
	public Object createObject(Attributes atts)
	{
		JRChart chart = (JRChart) digester.peek();
		JRDesignPie3DPlot pie3DPlot = (JRDesignPie3DPlot)chart.getPlot();
		
		String depthFactor = atts.getValue(ATTRIBUTE_depthFactor);
		if (depthFactor != null && depthFactor.length() > 0)
		{
			pie3DPlot.setDepthFactor(Double.valueOf(depthFactor));
		}
		
		String isCircular = atts.getValue(JRXmlConstants.ATTRIBUTE_isCircular);
		if (isCircular != null && isCircular.length() > 0) {
			pie3DPlot.setCircular(Boolean.valueOf(isCircular));
		}

		pie3DPlot.setLabelFormat(atts.getValue(JRXmlConstants.ATTRIBUTE_labelFormat));
		pie3DPlot.setLegendLabelFormat(atts.getValue(JRXmlConstants.ATTRIBUTE_legendLabelFormat));

		String isShowLabels = atts.getValue( ATTRIBUTE_isShowLabels );
		if( isShowLabels != null && isShowLabels.length() > 0 ){
			pie3DPlot.setShowLabels(Boolean.valueOf(isShowLabels));
		}
		return pie3DPlot;
	}
}
