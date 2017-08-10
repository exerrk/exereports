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
package com.github.exerrk.charts.util;

import java.awt.geom.Rectangle2D;

import org.jfree.chart.JFreeChart;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.properties.PropertyConstants;
import com.github.exerrk.renderers.Renderable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface ChartRenderableFactory
{
	@Property(
			name = "com.github.exerrk.chart.renderer.factory.{render_type}",
			category = PropertyConstants.CATEGORY_FILL,
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_2_0_5
			)
	public static final String PROPERTY_CHART_RENDERER_FACTORY_PREFIX = JRPropertiesUtil.PROPERTY_PREFIX + "chart.renderer.factory.";

	public Renderable getRenderable(
		JasperReportsContext jasperReportsContext,
		JFreeChart chart, 
		ChartHyperlinkProvider chartHyperlinkProvider,
		Rectangle2D rectangle
		);
}
