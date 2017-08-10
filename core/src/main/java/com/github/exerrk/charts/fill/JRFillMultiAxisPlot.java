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

import com.github.exerrk.charts.JRChartAxis;
import com.github.exerrk.charts.JRMultiAxisPlot;
import com.github.exerrk.engine.fill.JRFillBand;
import com.github.exerrk.engine.fill.JRFillChartDataset;
import com.github.exerrk.engine.fill.JRFillChartPlot;
import com.github.exerrk.engine.fill.JRFillObjectFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 */
public class JRFillMultiAxisPlot extends JRFillChartPlot implements JRMultiAxisPlot
{

	private List<JRChartAxis> axes;

	public JRFillMultiAxisPlot(JRMultiAxisPlot multiAxisPlot, JRFillObjectFactory factory)
	{
		super(multiAxisPlot, factory);

		List<JRChartAxis> parentAxes = multiAxisPlot.getAxes();
		this.axes = new ArrayList<JRChartAxis>(parentAxes.size());
		Iterator<JRChartAxis> iter = parentAxes.iterator();
		while (iter.hasNext())
		{
			JRChartAxis axis = iter.next();
			this.axes.add(factory.getChartAxis(axis));
		}
	}

	@Override
	protected void setBand(JRFillBand band)
	{
		super.setBand(band);
		
		for (JRChartAxis axis : axes)
		{
			((JRFillChartAxis) axis).fillChart.setBand(band);
		}
	}

	@Override
	public List<JRChartAxis> getAxes()
	{
		return axes;
	}

	public JRFillChartDataset getMainDataset()
	{
		return (JRFillChartDataset) ((JRFillChartAxis) axes.get(0)).getFillChart().getDataset();
	}
}
