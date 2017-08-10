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

import com.github.exerrk.charts.design.JRDesignThermometerPlot;
import com.github.exerrk.charts.type.ValueLocationEnum;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.util.JRColorUtil;
import com.github.exerrk.engine.xml.JRBaseFactory;

import org.xml.sax.Attributes;


/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 */
public class JRThermometerPlotFactory extends JRBaseFactory
{
	public static final String EXCEPTION_MESSAGE_KEY_INVALID_VALUE_LOCATION = "charts.thermometer.plot.invalid.value.location";
	
	public static final String ELEMENT_thermometerPlot = "thermometerPlot";
	public static final String ELEMENT_lowRange = "lowRange";
	public static final String ELEMENT_mediumRange = "mediumRange";
	public static final String ELEMENT_highRange = "highRange";

	public static final String ATTRIBUTE_valueLocation = "valueLocation";
	public static final String ATTRIBUTE_mercuryColor = "mercuryColor";

	@Override
	public Object createObject(Attributes atts) throws JRException
	{
		JRChart chart = (JRChart)digester.peek();
		JRDesignThermometerPlot thermometerPlot = (JRDesignThermometerPlot)chart.getPlot();

		String location = atts.getValue(ATTRIBUTE_valueLocation);
		ValueLocationEnum loc = ValueLocationEnum.getByName(atts.getValue(ATTRIBUTE_valueLocation));
		if (loc == null)
		{
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_INVALID_VALUE_LOCATION,
					new Object[]{location});
		}
		else
		{
			thermometerPlot.setValueLocation(loc);
		}

		String mercuryColor = atts.getValue(ATTRIBUTE_mercuryColor);
		if (mercuryColor != null && mercuryColor.length() > 0)
		{
			thermometerPlot.setMercuryColor(JRColorUtil.getColor(mercuryColor, null));
		}

		return thermometerPlot;
	}
}
