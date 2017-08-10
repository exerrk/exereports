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
package com.github.exerrk.charts.base;

import com.github.exerrk.charts.ChartCopyObjectFactory;
import com.github.exerrk.charts.JRItemLabel;
import com.github.exerrk.engine.JRChart;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class ChartCopyBaseObjectFactory implements ChartCopyObjectFactory
{
	
	private static final ChartCopyBaseObjectFactory INSTANCE = new ChartCopyBaseObjectFactory();
	
	public static ChartCopyBaseObjectFactory instance()
	{
		return INSTANCE;
	}

	@Override
	public JRItemLabel copyItemLabel(JRItemLabel itemLabel, JRChart chart)
	{
		return new JRBaseItemLabel(itemLabel, chart);
	}

}