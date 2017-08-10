package com.github.exerrk.engine.style;
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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JRPropertiesUtil.PropertySuffix;
import com.github.exerrk.engine.JRPropertyExpression;
import com.github.exerrk.engine.JasperReportsContext;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class PropertyStyleProviderFactory implements StyleProviderFactory
{

	private static final PropertyStyleProviderFactory INSTANCE = new PropertyStyleProviderFactory();
	
	private PropertyStyleProviderFactory()
	{
	}
	
	/**
	 * 
	 */
	public static PropertyStyleProviderFactory getInstance()
	{
		return INSTANCE;
	}

	@Override
	public StyleProvider getStyleProvider(StyleProviderContext context, JasperReportsContext jasperreportsContext)
	{
		Map<String, JRPropertyExpression> stylePropertyExpressions = null;
		JRPropertyExpression[] propertyExpressions = context.getElement().getPropertyExpressions();
		if (propertyExpressions != null)
		{
			for(JRPropertyExpression propertyExpression : propertyExpressions)
			{
				if (propertyExpression.getName().startsWith(PropertyStyleProvider.STYLE_PROPERTY_PREFIX))
				{
					if (stylePropertyExpressions == null)
					{
						stylePropertyExpressions = new HashMap<String, JRPropertyExpression>();
					}
					stylePropertyExpressions.put(propertyExpression.getName(), propertyExpression);
				}
			}
		}
		
		List<PropertySuffix> styleProperties = JRPropertiesUtil.getProperties(context.getElement(), PropertyStyleProvider.STYLE_PROPERTY_PREFIX);
		if (
			stylePropertyExpressions != null
			|| (styleProperties != null && styleProperties.size() > 0)
			)
		{
			return new PropertyStyleProvider(context, stylePropertyExpressions);
		}
		
		return null;
	}
	
}
