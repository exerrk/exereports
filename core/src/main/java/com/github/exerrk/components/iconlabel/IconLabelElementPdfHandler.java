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
package com.github.exerrk.components.iconlabel;

import com.github.exerrk.engine.JRGenericPrintElement;
import com.github.exerrk.engine.JRLineBox;
import com.github.exerrk.engine.JRPrintText;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.base.JRBasePrintFrame;
import com.github.exerrk.engine.export.GenericElementPdfHandler;
import com.github.exerrk.engine.export.JRPdfExporter;
import com.github.exerrk.engine.export.JRPdfExporterContext;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class IconLabelElementPdfHandler implements GenericElementPdfHandler
{
	@Override
	public void exportElement(JRPdfExporterContext exporterContext, JRGenericPrintElement element)
	{
		JRPrintText labelPrintText = (JRPrintText)element.getParameterValue(IconLabelElement.PARAMETER_LABEL_TEXT_ELEMENT);
		if (labelPrintText == null) //FIXMEINPUT deal with xml serialization
		{
			return;
		}
		
		JRBasePrintFrame frame = new JRBasePrintFrame(element.getDefaultStyleProvider());
		frame.setX(element.getX());
		frame.setY(element.getY());
		frame.setWidth(element.getWidth());
		frame.setHeight(element.getHeight());
		frame.setStyle(element.getStyle());
		frame.setBackcolor(element.getBackcolor());
		frame.setForecolor(element.getForecolor());
		frame.setMode(element.getModeValue());
		JRLineBox lineBox = (JRLineBox)element.getParameterValue(IconLabelElement.PARAMETER_LINE_BOX);
		if (lineBox != null)
		{
			frame.copyBox(lineBox);
		}
		
		frame.addElement(labelPrintText);
		
		JRPrintText iconPrintText = (JRPrintText)element.getParameterValue(IconLabelElement.PARAMETER_ICON_TEXT_ELEMENT);
		if (iconPrintText != null) //FIXMEINPUT deal with xml serialization
		{
			frame.addElement(iconPrintText);
		}

		JRPdfExporter exporter = (JRPdfExporter)exporterContext.getExporterRef();
		try
		{
			exporter.exportFrame(frame);
		}
		catch(Exception e)
		{
			throw new JRRuntimeException(e);
		}
	}
	
	@Override
	public boolean toExport(JRGenericPrintElement element)
	{
		return true;
	}
}
