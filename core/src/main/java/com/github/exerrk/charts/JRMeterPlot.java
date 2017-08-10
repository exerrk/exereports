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
package com.github.exerrk.charts;

import java.awt.Color;
import java.util.List;

import com.github.exerrk.charts.type.MeterShapeEnum;
import com.github.exerrk.charts.util.JRMeterInterval;
import com.github.exerrk.engine.JRChartPlot;
import com.github.exerrk.engine.JRFont;


/**
 * This type of plot can be used only for Meter charts and 
 * represents the display options of a Meter Chart.  A meter chart consists of a dial, 
 * a needle pointing to the current value on the dial, and the value itself. The Meter plot 
 * can be broken up into shaded regions to highlight portions of the range. 
 *
 * @author Barry Klawans (bklawans@users.sourceforge.net)
 */
public interface JRMeterPlot extends JRChartPlot
{
	/**
	 * Returns the range of values that the Meter can display.
	 *
	 * @return the range of values that the Meter can display
	 */
	public JRDataRange getDataRange();

	/**
	 * Returns a description of how the value of the Meter is displayed.  This includes
	 * any font and color information, the location of the value, a formatting mask and
	 * an optional "units" string to append to the value.
	 *
	 * @return a description of how the value of the Meter is displayed.
	 */
	public JRValueDisplay getValueDisplay();

	/**
	 * Returns the shape of the Meter.  The shape is only relevant if the Meter face is
	 * over 180 degrees wide, and controls how the portion of the circle described by the
	 * Meter but outside of the Meter is drawn.  (If the meter is 240 degrees wide the shape
	 * setting controls how the remaining 120 degrees is displayed.)
	 * <br><br>
	 * The value returned is one of the shape constants defined in {@link MeterShapeEnum}.
	 *
	 * @return a description of how the value of the Meter is displayed.
	 */
	public MeterShapeEnum getShapeValue();

	/**
	 * Returns a list of all the intervals contained in this Meter.  The return value is never
	 * <code>null</code> but can be an empty list.  Each element in the list is a
	 * {@link com.github.exerrk.charts.util.JRMeterInterval JRMeterInterval}
	 *
	 * @return a list of all the intervals contained in this Meter
	 */
	public List<JRMeterInterval> getIntervals();

	/**
	 * Returns the size of the Meter face in degrees. By default 
     * the meter dial is a semicircle. 
	 *
	 * @return the size of the Meter face in degrees
	 */
	public Integer getMeterAngleInteger();

	/**
	 * Returns the name of the units that the Meter is displaying.  This value will be
	 * appended to the value when displayed.
	 *
	 * @return the name of the units that the Meter is displaying
	 */
	public String getUnits();

	/**
	 * Returns the spacing between the ticks on the face of the meter.  The spacing is relative
	 * to the range that the meter is displaying - if the range is 0 to 500 and the tick interval
	 * is 50 then 10 ticks will be displayed.
	 *
	 * @return the spacing between the ticks on the face of the meter
	 */
	public Double getTickIntervalDouble();
	
	/**
	 * Returns the number of major ticks on the meter scale.
	 *
	 * @return the spacing between the ticks on the face of the meter
	 */
	public Integer getTickCount();

	/**
	 * Returns the background color of the meter.  This is the color of the meter's face.
	 *
	 * @return the background color of the meter.
	 */
	public Color getMeterBackgroundColor();

	/**
	 * Returns the color used when drawing the meter's pointer.
	 *
	 * @return the color used when drawing the meter's pointer
	 */
	public Color getNeedleColor();

	/**
	 * Returns the color used when drawing tick marks on the meter.
	 *
	 * @return the color used when drawing tick marks on the meter
	 */
	public Color getTickColor();
	
	/**
	 * Returns the font used to write tick labels on the meter.
	 *
	 * @return the font used to write tick labels on the meter
	 */
	public JRFont getTickLabelFont();
	
}
