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
package com.github.exerrk.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.github.exerrk.components.barbecue.BarbecueCompiler;
import com.github.exerrk.components.barbecue.BarbecueDesignConverter;
import com.github.exerrk.components.barbecue.BarbecueFillFactory;
import com.github.exerrk.components.barcode4j.BarcodeCompiler;
import com.github.exerrk.components.barcode4j.BarcodeDesignConverter;
import com.github.exerrk.components.barcode4j.BarcodeFillFactory;
import com.github.exerrk.components.iconlabel.IconLabelComponentCompiler;
import com.github.exerrk.components.iconlabel.IconLabelComponentDesignConverter;
import com.github.exerrk.components.iconlabel.IconLabelComponentFillFactory;
import com.github.exerrk.components.iconlabel.IconLabelComponentManager;
import com.github.exerrk.components.list.FillListFactory;
import com.github.exerrk.components.list.ListComponent;
import com.github.exerrk.components.list.ListComponentCompiler;
import com.github.exerrk.components.list.ListDesignConverter;
import com.github.exerrk.components.map.MapCompiler;
import com.github.exerrk.components.map.MapDesignConverter;
import com.github.exerrk.components.map.fill.MapFillFactory;
import com.github.exerrk.components.sort.SortComponentCompiler;
import com.github.exerrk.components.sort.SortComponentDesignConverter;
import com.github.exerrk.components.sort.SortComponentFillFactory;
import com.github.exerrk.components.spiderchart.SpiderChartCompiler;
import com.github.exerrk.components.spiderchart.SpiderChartDesignConverter;
import com.github.exerrk.components.spiderchart.SpiderChartFillFactory;
import com.github.exerrk.components.table.FillTableFactory;
import com.github.exerrk.components.table.TableCompiler;
import com.github.exerrk.components.table.TableDesignConverter;
import com.github.exerrk.engine.JRPropertiesMap;
import com.github.exerrk.engine.component.ComponentManager;
import com.github.exerrk.engine.component.ComponentsBundle;
import com.github.exerrk.engine.component.DefaultComponentXmlParser;
import com.github.exerrk.engine.component.DefaultComponentsBundle;
import com.github.exerrk.extensions.ExtensionsRegistry;
import com.github.exerrk.extensions.ExtensionsRegistryFactory;
import com.github.exerrk.extensions.ListExtensionsRegistry;

/**
 * Extension registry factory that includes built-in component element
 * implementations.
 * 
 * <p>
 * This registry factory is registered by default in JasperReports.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @see ListComponent
 */
public class ComponentsExtensionsRegistryFactory implements
		ExtensionsRegistryFactory
{

	public static final String NAMESPACE = 
		"http://jasperreports.sourceforge.net/jasperreports/components";
	public static final String XSD_LOCATION = 
		"http://jasperreports.sourceforge.net/xsd/components.xsd";
	public static final String XSD_RESOURCE = 
		"net/sf/jasperreports/components/components.xsd";
	
	public static final String LIST_COMPONENT_NAME = "list";
	public static final String TABLE_COMPONENT_NAME = "table";
	public static final String BARBECUE_COMPONENT_NAME = "barbecue";
	public static final List<String> BARCODE4J_COMPONENT_NAMES = Collections.unmodifiableList(Arrays.asList(
			"Codabar", "Code128", "EAN128", "DataMatrix", "Code39", "Interleaved2Of5",
			"UPCA", "UPCE", "EAN13", "EAN8", "USPSIntelligentMail", "RoyalMailCustomer", 
			"POSTNET", "PDF417", "QRCode"));
	public static final String SPIDERCHART_COMPONENT_NAME = "spiderChart";
	public static final String MAP_COMPONENT_NAME = "map";
	public static final String SORT_COMPONENT_NAME = "sort";
	public static final String ICONLABEL_COMPONENT_NAME = "iconLabel";
	
	private static final ExtensionsRegistry REGISTRY;
	
	static
	{
		final DefaultComponentsBundle bundle = new DefaultComponentsBundle();

		DefaultComponentXmlParser parser = new DefaultComponentXmlParser();
		parser.setNamespace(NAMESPACE);
		parser.setPublicSchemaLocation(XSD_LOCATION);
		parser.setInternalSchemaResource(XSD_RESOURCE);
		parser.setDigesterConfigurer(new ComponentsXmlDigesterConfigurer());
		bundle.setXmlParser(parser);
		
		HashMap<String, ComponentManager> componentManagers = new HashMap<String, ComponentManager>();
		
		ComponentsManager listManager = new ComponentsManager();
		listManager.setDesignConverter(new ListDesignConverter());
		listManager.setComponentCompiler(new ListComponentCompiler());
		//listManager.setComponentXmlWriter(xmlHandler);
		listManager.setComponentFillFactory(new FillListFactory());
		componentManagers.put(LIST_COMPONENT_NAME, listManager);
		
		ComponentsManager tableManager = new ComponentsManager();
		tableManager.setDesignConverter(new TableDesignConverter());
		tableManager.setComponentCompiler(new TableCompiler());
		//tableManager.setComponentXmlWriter(xmlHandler);
		tableManager.setComponentFillFactory(new FillTableFactory());
		componentManagers.put(TABLE_COMPONENT_NAME, tableManager);
		
		ComponentsManager barbecueManager = new ComponentsManager();
		barbecueManager.setDesignConverter(new BarbecueDesignConverter());
		barbecueManager.setComponentCompiler(new BarbecueCompiler());
		//barbecueManager.setComponentXmlWriter(xmlHandler);
		barbecueManager.setComponentFillFactory(new BarbecueFillFactory());
		componentManagers.put(BARBECUE_COMPONENT_NAME, barbecueManager);
		
		ComponentsManager barcode4jManager = new ComponentsManager();
		barcode4jManager.setDesignConverter(new BarcodeDesignConverter());
		barcode4jManager.setComponentCompiler(new BarcodeCompiler());
		//barcode4jManager.setComponentXmlWriter(xmlHandler);
		barcode4jManager.setComponentFillFactory(new BarcodeFillFactory());
		for (String name : BARCODE4J_COMPONENT_NAMES)
		{
			componentManagers.put(name, barcode4jManager);
		}
		
		ComponentsManager spiderChartManager = new ComponentsManager();
		spiderChartManager.setDesignConverter(new SpiderChartDesignConverter());
		spiderChartManager.setComponentCompiler(new SpiderChartCompiler());
		//spiderChartManager.setComponentXmlWriter(xmlHandler);
		spiderChartManager.setComponentFillFactory(new SpiderChartFillFactory());
		componentManagers.put(SPIDERCHART_COMPONENT_NAME, spiderChartManager);
		
		ComponentsManager mapManager = new ComponentsManager();
		mapManager.setDesignConverter(MapDesignConverter.getInstance());
		mapManager.setComponentCompiler(new MapCompiler());
		//mapManager.setComponentXmlWriter(xmlHandler);
		mapManager.setComponentFillFactory(new MapFillFactory());
		componentManagers.put(MAP_COMPONENT_NAME, mapManager);

		ComponentsManager sortManager = new ComponentsManager();
		sortManager.setDesignConverter(SortComponentDesignConverter.getInstance());
		sortManager.setComponentCompiler(new SortComponentCompiler());
		//sortManager.setComponentXmlWriter(xmlHandler);
		sortManager.setComponentFillFactory(new SortComponentFillFactory());
		componentManagers.put(SORT_COMPONENT_NAME, sortManager);
		
		IconLabelComponentManager iconLabelManager = new IconLabelComponentManager();
		iconLabelManager.setDesignConverter(IconLabelComponentDesignConverter.getInstance());
		iconLabelManager.setComponentCompiler(new IconLabelComponentCompiler());
		iconLabelManager.setComponentFillFactory(new IconLabelComponentFillFactory());
		componentManagers.put(ICONLABEL_COMPONENT_NAME, iconLabelManager);

		bundle.setComponentManagers(componentManagers);
		
		ListExtensionsRegistry registry = new ListExtensionsRegistry();
		registry.add(ComponentsBundle.class, bundle);
		
		REGISTRY = registry;
	}
	
	@Override
	public ExtensionsRegistry createRegistry(String registryId,
			JRPropertiesMap properties)
	{
		return REGISTRY;
	}

}
