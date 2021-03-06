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
package com.github.exerrk.components.barbecue;

import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRDefaultStyleProvider;
import com.github.exerrk.engine.JRStyle;
import com.github.exerrk.engine.type.RotationEnum;
import com.github.exerrk.engine.util.StyleResolver;

/**
 * 
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public final class BarbecueStyleResolver {
	
	private BarbecueStyleResolver()
	{
	}
	
	/**
	 * 
	 */
	public static StyleResolver getStyleResolver(JRComponentElement element)
	{
		if (element != null)
		{
			JRDefaultStyleProvider defaultStyleProvider = element.getDefaultStyleProvider();
			if (defaultStyleProvider != null)
			{
				return defaultStyleProvider.getStyleResolver();
			}
		}
		return StyleResolver.getInstance();
	}

	/**
	 * 
	 */
	public static RotationEnum getRotationValue(JRComponentElement element)
	{
		RotationEnum ownRotation = ((BarbecueComponent)element.getComponent()).getOwnRotation();
		if (ownRotation != null) {
			return ownRotation;
		}
		JRStyle style = getStyleResolver(element).getBaseStyle(element);
		if (style != null) {
			RotationEnum rotation = style.getRotationValue();
			if (rotation != null) {
				return rotation;
			}
		}
		return RotationEnum.NONE;
	}

}
