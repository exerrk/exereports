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

import java.util.HashMap;

import com.github.exerrk.components.list.ListComponent;
import com.github.exerrk.engine.JRPropertiesMap;
import com.github.exerrk.engine.component.DefaultComponentXmlParser;
import com.github.exerrk.engine.part.DefaultPartComponentsBundle;
import com.github.exerrk.engine.part.PartComponentManager;
import com.github.exerrk.engine.part.PartComponentsBundle;
import com.github.exerrk.extensions.ExtensionsRegistry;
import com.github.exerrk.extensions.ExtensionsRegistryFactory;
import com.github.exerrk.extensions.ListExtensionsRegistry;
import com.github.exerrk.parts.subreport.FillSubreportPartFactory;
import com.github.exerrk.parts.subreport.SubreportPartComponentCompiler;

/**
 * Extension registry factory that includes built-in part component implementations.
 * 
 * <p>
 * This registry factory is registered by default in JasperReports.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @see ListComponent
 */
public class PartComponentsExtensionsRegistryFactory implements
		ExtensionsRegistryFactory
{

	public static final String NAMESPACE = 
		"http://jasperreports.sourceforge.net/jasperreports/parts";
	public static final String XSD_LOCATION = 
		"http://jasperreports.sourceforge.net/xsd/parts.xsd";
	public static final String XSD_RESOURCE =
			"com/github/exerrk/parts/parts.xsd";
	
	public static final String SUBREPORT_PART_COMPONENT_NAME = "subreportPart";
	
	private static final ExtensionsRegistry REGISTRY;
	
	static
	{
		final DefaultPartComponentsBundle bundle = new DefaultPartComponentsBundle();

		DefaultComponentXmlParser parser = new DefaultComponentXmlParser();
		parser.setNamespace(NAMESPACE);
		parser.setPublicSchemaLocation(XSD_LOCATION);
		parser.setInternalSchemaResource(XSD_RESOURCE);
		parser.setDigesterConfigurer(new PartComponentsXmlDigesterConfigurer());
		bundle.setXmlParser(parser);
		
		HashMap<String, PartComponentManager> componentManagers = new HashMap<String, PartComponentManager>();
		
		PartComponentsManager reportManager = new PartComponentsManager();
		reportManager.setComponentCompiler(new SubreportPartComponentCompiler());
		//listManager.setComponentXmlWriter(xmlHandler);
		reportManager.setComponentFillFactory(new FillSubreportPartFactory());
		componentManagers.put(SUBREPORT_PART_COMPONENT_NAME, reportManager);
		
		bundle.setComponentManagers(componentManagers);
		
		ListExtensionsRegistry registry = new ListExtensionsRegistry();
		registry.add(PartComponentsBundle.class, bundle);
		
		REGISTRY = registry;
	}
	
	@Override
	public ExtensionsRegistry createRegistry(String registryId,
			JRPropertiesMap properties)
	{
		return REGISTRY;
	}

}
