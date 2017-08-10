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

import com.github.exerrk.charts.design.JRDesignValueDisplay;
import com.github.exerrk.engine.JRChartPlot;
import com.github.exerrk.engine.util.JRColorUtil;
import com.github.exerrk.engine.xml.JRBaseFactory;

import org.xml.sax.Attributes;


/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 */
public class JRValueDisplayFactory extends JRBaseFactory
{
	private static final String ATTRIBUTE_color = "color";
	private static final String ATTRIBUTE_mask = "mask";

	@Override
	public Object createObject(Attributes atts)
	{
		JRDesignValueDisplay valueDisplay = new JRDesignValueDisplay(null, ((JRChartPlot)digester.peek()).getChart());

		String color = atts.getValue(ATTRIBUTE_color);
		if (color != null && color.length() > 0)
		{
			valueDisplay.setColor(JRColorUtil.getColor(color, null));
		}

		String mask = atts.getValue(ATTRIBUTE_mask);
		if (mask != null && mask.length() > 0)
		{
			valueDisplay.setMask(mask);
		}

		return valueDisplay;
	}
}
