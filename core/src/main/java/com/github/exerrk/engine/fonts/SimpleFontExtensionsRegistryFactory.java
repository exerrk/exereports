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
package com.github.exerrk.engine.fonts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.exerrk.engine.JRPropertiesMap;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JRPropertiesUtil.PropertySuffix;
import com.github.exerrk.extensions.DefaultExtensionsRegistry;
import com.github.exerrk.extensions.ExtensionsRegistry;
import com.github.exerrk.extensions.ExtensionsRegistryFactory;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SimpleFontExtensionsRegistryFactory implements ExtensionsRegistryFactory
{

	/**
	 * 
	 */
	public final static String SIMPLE_FONT_FAMILIES_PROPERTY_PREFIX = 
		DefaultExtensionsRegistry.PROPERTY_REGISTRY_PREFIX + "simple.font.families.";
	public final static String PROPERTY_SIMPLE_FONT_FAMILIES_REGISTRY_FACTORY =
		DefaultExtensionsRegistry.PROPERTY_REGISTRY_FACTORY_PREFIX + "simple.font.families";
	
	@Override
	public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties)
	{
		List<PropertySuffix> fontFamiliesProperties = JRPropertiesUtil.getProperties(properties, SIMPLE_FONT_FAMILIES_PROPERTY_PREFIX);
		List<String> fontFamiliesLocations = new ArrayList<String>();
		for (Iterator<PropertySuffix> it = fontFamiliesProperties.iterator(); it.hasNext();)
		{
			PropertySuffix fontFamiliesProp = it.next();
			//String fontFamiliesName = fontFamiliesProp.getSuffix();
			String fontFamiliesLocation = fontFamiliesProp.getValue();
			//fontFamiliesLocations.addAll(SimpleFontExtensionHelper.getInstance().loadFontFamilies(fontFamiliesLocation));
			fontFamiliesLocations.add(fontFamiliesLocation);
		}
		
		return new FontExtensionsRegistry(fontFamiliesLocations);
	}

}
