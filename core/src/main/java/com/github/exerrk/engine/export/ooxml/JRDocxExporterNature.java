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

/*
 * Contributors:
 * Greg Hilton
 */

package com.github.exerrk.engine.export.ooxml;

import com.github.exerrk.engine.JRGenericPrintElement;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRPrintFrame;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.export.ExporterFilter;
import com.github.exerrk.engine.export.GenericElementHandler;
import com.github.exerrk.export.DocxReportConfiguration;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class JRDocxExporterNature extends JROfficeOpenXmlExporterNature
{

	private final boolean deepGrid;

	/**
	 *
	 */
	public JRDocxExporterNature(JasperReportsContext jasperReportsContext, ExporterFilter filter, boolean deepGrid)
	{
		super(jasperReportsContext, filter);
		
		this.deepGrid = deepGrid;
	}

	@Override
	public boolean isToExport(JRPrintElement element)
	{
		boolean isToExport = true;
		if (element instanceof JRGenericPrintElement)
		{
			JRGenericPrintElement genericElement = (JRGenericPrintElement) element;
			GenericElementHandler handler = handlerEnvironment.getElementHandler(
					genericElement.getGenericType(), JRDocxExporter.DOCX_EXPORTER_KEY);
			if (handler == null || !handler.toExport(genericElement))
			{
				isToExport = false;
			}
		}

		return isToExport && super.isToExport(element);
	}

	@Override
	public boolean isDeep(JRPrintFrame frame)
	{
		if (
			frame.hasProperties()
			&& frame.getPropertiesMap().containsProperty(DocxReportConfiguration.PROPERTY_FRAMES_AS_NESTED_TABLES)
			)
		{
			// we make this test to avoid reaching the global default value of the property directly
			// and thus skipping the report level one, if present
			return !getPropertiesUtil().getBooleanProperty(frame, DocxReportConfiguration.PROPERTY_FRAMES_AS_NESTED_TABLES, !deepGrid);
		}
		return deepGrid;
	}

}