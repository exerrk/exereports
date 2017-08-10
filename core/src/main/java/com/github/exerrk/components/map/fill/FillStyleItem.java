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
package com.github.exerrk.components.map.fill;

import java.util.Map;

import com.github.exerrk.components.items.Item;
import com.github.exerrk.components.items.ItemProperty;
import com.github.exerrk.components.items.fill.FillItem;
import com.github.exerrk.components.map.MapComponent;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.fill.JRFillExpressionEvaluator;
import com.github.exerrk.engine.fill.JRFillObjectFactory;
import com.github.exerrk.engine.type.ColorEnum;
import com.github.exerrk.engine.util.JRColorUtil;

/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class FillStyleItem extends FillItem
{
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_COLOR = "color";
	
	/**
	 *
	 */
	public FillStyleItem(
		Item item, 
		JRFillObjectFactory factory
		)
	{
		super(item, factory);
	}

	@Override
	public Object getEvaluatedValue(ItemProperty property, JRFillExpressionEvaluator evaluator, byte evaluation) throws JRException
	{
		Object result = super.getEvaluatedValue(property, evaluator, evaluation);
		return PROPERTY_COLOR.equals(property.getName()) 
				? JRColorUtil.getColorHexa(JRColorUtil.getColor((String)result, ColorEnum.RED.getColor()))
				: result;
	}
	

	@Override
	public void verifyValue(ItemProperty property, Object value) throws JRException {
		if(PROPERTY_NAME.equals(property.getName()) && (value == null || (value instanceof String && ((String)value).length() == 0))){
			throw 
				new JRException(
					MapFillComponent.EXCEPTION_MESSAGE_KEY_NULL_OR_EMPTY_VALUE_NOT_ALLOWED,  
					new Object[]{property.getName()} 
					);
		}
	}
	
	@Override
	public void verifyValues(Map<String, Object> result) throws JRException {
		if(result != null) {
			//these are not intended for the style property
			result.remove(MapComponent.ITEM_PROPERTY_latitude);
			result.remove(MapComponent.ITEM_PROPERTY_longitude);
			result.remove(MapComponent.ITEM_PROPERTY_address);
		}
	}
}
