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
package com.github.exerrk.components.sort;

import java.util.Map;

import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRFont;
import com.github.exerrk.engine.JRStyle;
import com.github.exerrk.engine.design.JRDesignFont;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.engine.xml.JRFontFactory;
import com.github.exerrk.engine.xml.JRXmlConstants;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SortComponentSymbolFontFactory extends JRFontFactory
{

	@Override
	public JRFont getFont()
	{
		int i = 0;
		JRComponentElement component = null;
		while (component == null && i < digester.getCount())
		{
			Object obj = digester.peek(i);
			component = obj instanceof JRComponentElement ? (JRComponentElement)obj : null;
			i++;
		}
		
		return new JRDesignFont(component);
	}
	
	@Override
	public void setStyle(JRFont font, Attributes atts)
	{
		JRDesignFont designFont = (JRDesignFont)font;
			String styleName = atts.getValue(JRXmlConstants.ATTRIBUTE_reportFont);
			
			if (styleName != null)
			{
				JasperDesign jasperDesign = (JasperDesign)digester.peek(digester.getCount() - 2);
				Map<String,JRStyle> stylesMap = jasperDesign.getStylesMap();

				if (stylesMap.containsKey(styleName))
				{
					JRStyle style = stylesMap.get(styleName);
					designFont.setStyle(style);
				}
				else
				{
					designFont.setStyleNameReference(styleName);
				}
			}
	}
	
}
