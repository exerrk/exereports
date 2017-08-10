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
package com.github.exerrk.engine.xml;

import java.io.IOException;

import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.export.JRXmlExporter;

/**
 * A handler that deals with arbitrary values being exported to XML and parsed back 
 * to {@link JasperPrint} objects.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface XmlValueHandler
{

	/**
	 * Returns the namespace of the elements generated on XML export.
	 * 
	 * @return the namespace of the elements generated on XML export
	 */
	XmlHandlerNamespace getNamespace();

	/**
	 * Configures an XML digester by adding the rules required to parse exported values.
	 * 
	 * @param digester the digester to configure
	 */
	void configureDigester(JRXmlDigester digester);

	/**
	 * Outputs the XML representation of a value if the value is supported by
	 * this handler.
	 * 
	 * @param value the value
	 * @param exporter the XML exporter
	 * @return <code>true</code> iff the value is supported by this handler
	 * @throws IOException
	 */
	boolean writeToXml(Object value, JRXmlExporter exporter) throws IOException;
	
}