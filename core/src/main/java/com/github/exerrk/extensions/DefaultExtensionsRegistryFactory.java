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
package com.github.exerrk.extensions;

import java.util.Collections;
import java.util.List;

import com.github.exerrk.charts.ChartThemeBundle;
import com.github.exerrk.components.headertoolbar.HeaderToolbarElement;
import com.github.exerrk.components.headertoolbar.json.HeaderToolbarElementJsonHandler;
import com.github.exerrk.components.iconlabel.IconLabelElement;
import com.github.exerrk.components.iconlabel.IconLabelElementCsvHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementDocxHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementGraphics2DHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementHtmlHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementOdsHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementOdtHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementPdfHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementPptxHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementRtfHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementXlsHandler;
import com.github.exerrk.components.iconlabel.IconLabelElementXlsxHandler;
import com.github.exerrk.components.map.MapComponent;
import com.github.exerrk.components.map.MapElementDocxHandler;
import com.github.exerrk.components.map.MapElementGraphics2DHandler;
import com.github.exerrk.components.map.MapElementHtmlHandler;
import com.github.exerrk.components.map.MapElementJsonHandler;
import com.github.exerrk.components.map.MapElementOdsHandler;
import com.github.exerrk.components.map.MapElementOdtHandler;
import com.github.exerrk.components.map.MapElementPdfHandler;
import com.github.exerrk.components.map.MapElementPptxHandler;
import com.github.exerrk.components.map.MapElementRtfHandler;
import com.github.exerrk.components.map.MapElementXlsHandler;
import com.github.exerrk.components.map.MapElementXlsxHandler;
import com.github.exerrk.components.sort.SortElement;
import com.github.exerrk.components.sort.SortElementHtmlHandler;
import com.github.exerrk.components.sort.SortElementJsonHandler;
import com.github.exerrk.crosstabs.interactive.CrosstabInteractiveJsonHandler;
import com.github.exerrk.engine.JRPropertiesMap;
import com.github.exerrk.engine.export.FlashHtmlHandler;
import com.github.exerrk.engine.export.FlashPrintElement;
import com.github.exerrk.engine.export.GenericElementHandler;
import com.github.exerrk.engine.export.GenericElementHandlerBundle;
import com.github.exerrk.engine.export.HtmlExporter;
import com.github.exerrk.engine.export.JRCsvExporter;
import com.github.exerrk.engine.export.JRGraphics2DExporter;
import com.github.exerrk.engine.export.JRPdfExporter;
import com.github.exerrk.engine.export.JRRtfExporter;
import com.github.exerrk.engine.export.JRXlsExporter;
import com.github.exerrk.engine.export.JsonExporter;
import com.github.exerrk.engine.export.oasis.JROdsExporter;
import com.github.exerrk.engine.export.oasis.JROdtExporter;
import com.github.exerrk.engine.export.ooxml.JRDocxExporter;
import com.github.exerrk.engine.export.ooxml.JRPptxExporter;
import com.github.exerrk.engine.export.ooxml.JRXlsxExporter;
import com.github.exerrk.engine.fill.DefaultChartTheme;
import com.github.exerrk.engine.fill.JRFillCrosstab;
import com.github.exerrk.engine.query.DefaultQueryExecuterFactoryBundle;
import com.github.exerrk.engine.query.JRQueryExecuterFactoryBundle;
import com.github.exerrk.engine.scriptlets.DefaultScriptletFactory;
import com.github.exerrk.engine.scriptlets.ScriptletFactory;
import com.github.exerrk.engine.util.MessageProviderFactory;
import com.github.exerrk.engine.util.ResourceBundleMessageProviderFactory;
import com.github.exerrk.engine.xml.JRXmlConstants;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class DefaultExtensionsRegistryFactory implements ExtensionsRegistryFactory
{
	private static final GenericElementHandlerBundle HANDLER_BUNDLE = 
		new GenericElementHandlerBundle()
		{
			@Override
			public String getNamespace()
			{
				return JRXmlConstants.JASPERREPORTS_NAMESPACE;
			}
			
			@Override
			public GenericElementHandler getHandler(String elementName,
					String exporterKey)
			{
				if (
					FlashPrintElement.FLASH_ELEMENT_NAME.equals(elementName) 
					&& HtmlExporter.HTML_EXPORTER_KEY.equals(exporterKey)
					)
				{
					return FlashHtmlHandler.getInstance();
				}
				if (MapComponent.MAP_ELEMENT_NAME.equals(elementName))
				{
					if (JRGraphics2DExporter.GRAPHICS2D_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementGraphics2DHandler.getInstance();
					}
					if (HtmlExporter.HTML_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementHtmlHandler.getInstance();
					}
					else if (JsonExporter.JSON_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementJsonHandler.getInstance();
					}
					else if(JRPdfExporter.PDF_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementPdfHandler.getInstance();
					}
					else if(JRXlsExporter.XLS_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementXlsHandler.getInstance();
					}
					else if(JRXlsxExporter.XLSX_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementXlsxHandler.getInstance();
					}
					else if(JRDocxExporter.DOCX_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementDocxHandler.getInstance();
					}
					else if(JRPptxExporter.PPTX_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementPptxHandler.getInstance();
					}
					else if(JRRtfExporter.RTF_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementRtfHandler.getInstance();
					}
					else if(JROdtExporter.ODT_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementOdtHandler.getInstance();
					}
					else if(JROdsExporter.ODS_EXPORTER_KEY.equals(exporterKey))
					{
						return MapElementOdsHandler.getInstance();
					}
				}
				if (SortElement.SORT_ELEMENT_NAME.equals(elementName))
				{
					if (HtmlExporter.HTML_EXPORTER_KEY.equals(exporterKey))
					{
						return new SortElementHtmlHandler();
					} else if (JsonExporter.JSON_EXPORTER_KEY.equals(exporterKey))
					{
						return new SortElementJsonHandler();
					}
				}
				if (HeaderToolbarElement.ELEMENT_NAME.equals(elementName) && JsonExporter.JSON_EXPORTER_KEY.equals(exporterKey))
				{
					return new HeaderToolbarElementJsonHandler();
				}
				if (IconLabelElement.ELEMENT_NAME.equals(elementName))
				{
					if (JRPdfExporter.PDF_EXPORTER_KEY.equals(exporterKey))
					{
						return new IconLabelElementPdfHandler();
					}
					else if (JRGraphics2DExporter.GRAPHICS2D_EXPORTER_KEY.equals(exporterKey))
					{
						return new IconLabelElementGraphics2DHandler();
					}		
					else if (HtmlExporter.HTML_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementHtmlHandler.getInstance();
					}		
					else if (JRCsvExporter.CSV_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementCsvHandler.getInstance();
					}		
					else if (JRXlsExporter.XLS_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementXlsHandler.getInstance();
					}		
					else if (JRXlsxExporter.XLSX_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementXlsxHandler.getInstance();
					}		
					else if (JRDocxExporter.DOCX_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementDocxHandler.getInstance();
					}		
					else if (JRPptxExporter.PPTX_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementPptxHandler.getInstance();
					}		
					else if (JROdsExporter.ODS_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementOdsHandler.getInstance();
					}		
					else if (JROdtExporter.ODT_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementOdtHandler.getInstance();
					}		
					else if (JRRtfExporter.RTF_EXPORTER_KEY.equals(exporterKey))
					{
						return IconLabelElementRtfHandler.getInstance();
					}		
//					else if (JRXmlExporter.XML_EXPORTER_KEY.equals(exporterKey))
//					{
//						return IconLabelElementXmlHandler.getInstance();
//					}		
				}
				
				if (JRFillCrosstab.CROSSTAB_INTERACTIVE_ELEMENT_NAME.equals(elementName))
				{
					if (JsonExporter.JSON_EXPORTER_KEY.equals(exporterKey))
					{
						return new CrosstabInteractiveJsonHandler();
					}
				}
				
				return null;
			}
		};

	private static final ExtensionsRegistry defaultExtensionsRegistry = 
		new ExtensionsRegistry()
		{
			@Override
			public <T> List<T> getExtensions(Class<T> extensionType) 
			{
				if (JRQueryExecuterFactoryBundle.class.equals(extensionType))
				{
					return (List<T>) Collections.singletonList((Object)DefaultQueryExecuterFactoryBundle.getInstance());
				}
				else if (ScriptletFactory.class.equals(extensionType))
				{
					return (List<T>) Collections.singletonList((Object)DefaultScriptletFactory.getInstance());
				}
				else if (ChartThemeBundle.class.equals(extensionType))
				{
					return (List<T>) Collections.singletonList((Object)DefaultChartTheme.BUNDLE);
				}
				else if (GenericElementHandlerBundle.class.equals(extensionType))
				{
					return (List<T>) Collections.singletonList((Object)HANDLER_BUNDLE);
				}
				else if (MessageProviderFactory.class.equals(extensionType))
				{
					return (List<T>) Collections.singletonList((Object) new ResourceBundleMessageProviderFactory());
				}
				return null;
			}
		};
	
	@Override
	public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) 
	{
		return defaultExtensionsRegistry;
	}
}
