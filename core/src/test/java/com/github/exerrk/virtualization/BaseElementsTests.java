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
package com.github.exerrk.virtualization;

import java.io.StringWriter;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.base.JRBasePrintPage;
import com.github.exerrk.engine.export.JRXmlExporter;
import com.github.exerrk.engine.fill.JRTemplatePrintElement;
import com.github.exerrk.engine.fill.JRVirtualizationContext;
import com.github.exerrk.export.SimpleExporterInput;
import com.github.exerrk.export.SimpleXmlExporterOutput;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class BaseElementsTests extends BaseSerializationTests
{

	protected <T extends JRTemplatePrintElement> T compareSerialized(T element)
	{
		T readElement = passThroughElementSerialization(element);
		compareXml(element, readElement);
		return readElement;
	}

	protected <T extends JRTemplatePrintElement> T passThroughElementSerialization(T element)
	{
		JRVirtualizationContext virtualizationContext = createVirtualizationContext();
		T read = passThroughElementSerialization(virtualizationContext, element);
		assert read.getTemplate() == element.getTemplate();
		return read;
	}

	protected <T extends JRTemplatePrintElement> T passThroughElementSerialization(
			JRVirtualizationContext virtualizationContext, T element)
	{
		virtualizationContext.cacheTemplate(element);
		
		T readElement = passThroughSerialization(virtualizationContext, element);
		return readElement;
	}

	protected <T extends JRPrintElement> void compareXml(T element0, T element1)
	{
		String element0Xml = elementToXml(element0);
		String element1Xml = elementToXml(element1);
		assert element0Xml.equals(element1Xml);
	}
	
	protected String elementToXml(JRPrintElement element)
	{
		JRBasePrintPage page = new JRBasePrintPage();
		page.addElement(element);
		JasperPrint jasperPrint = new JasperPrint();
		jasperPrint.addPage(page);
		jasperPrint.setName("test");
		
		StringWriter writer = new StringWriter();
		JRXmlExporter exporter = new JRXmlExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		SimpleXmlExporterOutput output = new SimpleXmlExporterOutput(writer);
		output.setEmbeddingImages(true);
		exporter.setExporterOutput(output);
		try
		{
			exporter.exportReport();
		}
		catch (JRException e)
		{
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

}
