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
package com.github.exerrk.components.map;

import com.github.exerrk.engine.JRGenericPrintElement;
import com.github.exerrk.engine.export.JRExporterGridCell;
import com.github.exerrk.engine.export.ooxml.GenericElementDocxHandler;
import com.github.exerrk.engine.export.ooxml.JRDocxExporter;
import com.github.exerrk.engine.export.ooxml.JRDocxExporterContext;

/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class MapElementDocxHandler implements GenericElementDocxHandler
{
	private static final MapElementDocxHandler INSTANCE = new MapElementDocxHandler();
	
	public static MapElementDocxHandler getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public void exportElement(
		JRDocxExporterContext exporterContext,
		JRGenericPrintElement element,
		JRExporterGridCell gridCell
		)
	{
		try
		{
			JRDocxExporter exporter = (JRDocxExporter)exporterContext.getExporterRef();
			exporter.exportImage(
				exporterContext.getTableHelper(), 
				MapElementImageProvider.getImage(exporterContext.getJasperReportsContext(), element), 
				gridCell
				);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean toExport(JRGenericPrintElement element) {
		return true;
	}

}
