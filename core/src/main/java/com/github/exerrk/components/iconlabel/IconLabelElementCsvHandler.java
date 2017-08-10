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
import com.github.exerrk.engine.JRPrintText;
import com.github.exerrk.engine.export.GenericElementCsvHandler;
import com.github.exerrk.engine.export.JRCsvExporter;
import com.github.exerrk.engine.export.JRCsvExporterContext;
import com.github.exerrk.engine.util.JRStyledText;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class IconLabelElementCsvHandler implements GenericElementCsvHandler
{
	private static final IconLabelElementCsvHandler INSTANCE = new IconLabelElementCsvHandler();
	
	public static IconLabelElementCsvHandler getInstance()
	{
		return INSTANCE;
	}

	@Override
	public String getTextValue(JRCsvExporterContext exporterContext, JRGenericPrintElement element)
	{
		JRPrintText labelPrintText = (JRPrintText)element.getParameterValue(IconLabelElement.PARAMETER_LABEL_TEXT_ELEMENT);
		if (labelPrintText == null)
		{
			return null;
		}
		
		String text = null;
		
		JRStyledText styledText = ((JRCsvExporter)exporterContext.getExporterRef()).getStyledText(labelPrintText);
		if (styledText == null)
		{
			text = "";
		}
		else
		{
			text = styledText.getText();
		}
		
		return text;
	}

	@Override
	public boolean toExport(JRGenericPrintElement element) 
	{
		return true;
	}
	
}
