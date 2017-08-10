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
import com.github.exerrk.engine.export.JRGridLayout;
import com.github.exerrk.engine.export.oasis.GenericElementOdsHandler;
import com.github.exerrk.engine.export.oasis.JROdsExporter;
import com.github.exerrk.engine.export.oasis.JROdsExporterContext;

/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class MapElementOdsHandler implements GenericElementOdsHandler
{
	private static final MapElementOdsHandler INSTANCE = new MapElementOdsHandler();
	
	public static MapElementOdsHandler getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public void exportElement(
		JROdsExporterContext exporterContext,
		JRGenericPrintElement element,
		JRExporterGridCell gridCell,
		int colIndex,
		int rowIndex,
		int emptyCols,
		int yCutsRow, 
		JRGridLayout layout
		)
	{
		try
		{
			JROdsExporter exporter = (JROdsExporter)exporterContext.getExporterRef();
			exporter.exportImage(
				MapElementImageProvider.getImage(exporterContext.getJasperReportsContext(), element), 
				gridCell, 
				colIndex, 
				rowIndex, 
				0, 
				0, 
				null
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
