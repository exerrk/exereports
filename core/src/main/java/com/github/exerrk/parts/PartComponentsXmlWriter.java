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
package com.github.exerrk.parts;

import java.io.IOException;

import com.github.exerrk.components.ComponentsExtensionsRegistryFactory;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRPart;
import com.github.exerrk.engine.JRSubreportParameter;
import com.github.exerrk.engine.JRSubreportReturnValue;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.component.ComponentKey;
import com.github.exerrk.engine.part.PartComponent;
import com.github.exerrk.engine.util.JRXmlWriteHelper;
import com.github.exerrk.engine.util.XmlNamespace;
import com.github.exerrk.engine.xml.JRXmlConstants;
import com.github.exerrk.engine.xml.JRXmlWriter;
import com.github.exerrk.parts.subreport.SubreportPartComponent;

/**
 * XML writer for built-in part component implementations.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @see ComponentsExtensionsRegistryFactory
 */
public class PartComponentsXmlWriter extends AbstractPartComponentXmlWriter
{
	/**
	 * 
	 */
	public PartComponentsXmlWriter(JasperReportsContext jasperReportsContext)
	{
		super(jasperReportsContext);
	}

	@Override
	public void writeToXml(JRPart part, JRXmlWriter reportWriter) throws IOException
	{
		PartComponent component = part.getComponent();
		if (component instanceof SubreportPartComponent)
		{
			writeSubreport(part, reportWriter);
		}
	}

	protected void writeSubreport(JRPart part, JRXmlWriter reportWriter) throws IOException
	{
		SubreportPartComponent subreport = (SubreportPartComponent) part.getComponent();
		ComponentKey componentKey = part.getComponentKey();
		JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();
		
		XmlNamespace namespace = new XmlNamespace(
				PartComponentsExtensionsRegistryFactory.NAMESPACE, 
				componentKey.getNamespacePrefix(),
				PartComponentsExtensionsRegistryFactory.XSD_LOCATION);
		
		writer.startElement(PartComponentsExtensionsRegistryFactory.SUBREPORT_PART_COMPONENT_NAME, namespace);
		writer.addAttribute("usingCache", subreport.getUsingCache());

		writer.writeExpression(JRXmlConstants.ELEMENT_parametersMapExpression, JRXmlWriter.JASPERREPORTS_NAMESPACE, subreport.getParametersMapExpression());

		JRSubreportParameter[] parameters = subreport.getParameters();
		if (parameters != null && parameters.length > 0)
		{
			for(int i = 0; i < parameters.length; i++)
			{
				reportWriter.writeSubreportParameter(parameters[i], JRXmlWriter.JASPERREPORTS_NAMESPACE);
			}
		}

		JRSubreportReturnValue[] returnValues = subreport.getReturnValues();
		if (returnValues != null && returnValues.length > 0)
		{
			for(int i = 0; i < returnValues.length; i++)
			{
				reportWriter.writeSubreportReturnValue(returnValues[i], JRXmlWriter.JASPERREPORTS_NAMESPACE);
			}
		}
		
		writer.writeExpression(JRXmlConstants.ELEMENT_subreportExpression, JRXmlWriter.JASPERREPORTS_NAMESPACE, subreport.getExpression());
		
		writer.closeElement();
	}

	@Override
	public boolean isToWrite(JRPart part, JRXmlWriter reportWriter) 
	{
		ComponentKey componentKey = part.getComponentKey();
		if (ComponentsExtensionsRegistryFactory.NAMESPACE.equals(componentKey.getNamespace()))
		{
			if(PartComponentsExtensionsRegistryFactory.SUBREPORT_PART_COMPONENT_NAME.equals(componentKey.getName()))
			{
				return isNewerVersionOrEqual(part, reportWriter, JRConstants.VERSION_6_0_0);
			}
		}

		return true;
	}
}
