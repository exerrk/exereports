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

import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.DefaultJasperReportsContext;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.properties.PropertyConstants;

import org.apache.commons.collections.map.ReferenceMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The default XML export SAX parser factory.
 * 
 * <p>
 * This factory creates a parser via the default SAX parser factory
 * (<code>javax.xml.parsers.SAXParserFactory.newInstance()</code>).
 * 
 * <p>
 * XML exports are always validated using W3C XML schemas.  Reports that refer
 * the JasperReports DTD (which has been deprecated) are validated using an
 * internal XML schema equivalent to the DTD.
 * 
 * <p>
 * To improve performance, XML schemas can be cached when using a Xerces
 * SAX parser.  See {@link #PROPERTY_CACHE_SCHEMAS}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class PrintSaxParserFactory extends BaseSaxParserFactory
{
	
	private static final Log log = LogFactory.getLog(PrintSaxParserFactory.class);
	
	/**
	 * Validation flag used by the XML exporter.
	 * <p>
	 * Defaults to <code>true</code>.
	 */
	@Property(
			category = PropertyConstants.CATEGORY_EXPORT,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT},
			sinceVersion = PropertyConstants.VERSION_1_0_0,
			valueType = Boolean.class
			)
	public static final String EXPORT_XML_VALIDATION = JRPropertiesUtil.PROPERTY_PREFIX + "export.xml.validation";
	
	private final static ThreadLocal<ReferenceMap> GRAMMAR_POOL_CACHE = new ThreadLocal<ReferenceMap>();
	
	/**
	 * @deprecated Replaced by {@link #PrintSaxParserFactory(JasperReportsContext)}.
	 */
	public PrintSaxParserFactory() 
	{
		this(DefaultJasperReportsContext.getInstance());
	}

	public PrintSaxParserFactory(JasperReportsContext jasperReportsContext) 
	{
		super(jasperReportsContext);
	}

	@Override
	protected boolean isValidating()
	{
		return JRPropertiesUtil.getInstance(jasperReportsContext).getBooleanProperty(EXPORT_XML_VALIDATION);
	}

	@Override
	protected List<String> getSchemaLocations()
	{
		List<String> schemas = new ArrayList<String>();
		schemas.add(getResourceURI(JRXmlConstants.JASPERPRINT_XSD_RESOURCE));
		schemas.add(getResourceURI(JRXmlConstants.JASPERPRINT_XSD_DTD_COMPAT_RESOURCE));
		
		List<XmlValueHandler> handlers = XmlValueHandlerUtils.instance().getHandlers();
		for (XmlValueHandler handler : handlers)
		{
			XmlHandlerNamespace namespace = handler.getNamespace();
			if (namespace != null)
			{
				String schemaURI;
				String schemaResource = namespace.getInternalSchemaResource();
				if (schemaResource != null)
				{
					schemaURI = getResourceURI(schemaResource);
				}
				else
				{
					schemaURI = namespace.getPublicSchemaLocation();
				}

				if (log.isDebugEnabled())
				{
					log.debug("Adding schema at " + schemaURI);
				}
				
				schemas.add(schemaURI);
			}
		}
		
		return schemas;
	}

	@Override
	protected ThreadLocal<ReferenceMap> getGrammarPoolCache()
	{
		return GRAMMAR_POOL_CACHE;
	}

}
