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

import org.xml.sax.Attributes;

import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.base.JRBasePrintImage;
import com.github.exerrk.engine.type.HorizontalImageAlignEnum;
import com.github.exerrk.engine.type.OnErrorTypeEnum;
import com.github.exerrk.engine.type.ScaleImageEnum;
import com.github.exerrk.engine.type.VerticalImageAlignEnum;
import com.github.exerrk.renderers.ResourceRenderer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRPrintImageFactory extends JRBaseFactory
{

	@Override
	public Object createObject(Attributes atts)
	{
		JasperPrint jasperPrint = (JasperPrint)digester.peek(digester.getCount() - 2);

		JRBasePrintImage image = new JRBasePrintImage(jasperPrint.getDefaultStyleProvider());

		// get image attributes
		ScaleImageEnum scaleImage = ScaleImageEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_scaleImage));
		if (scaleImage != null)
		{
			image.setScaleImage(scaleImage);
		}

		HorizontalImageAlignEnum horizontalImageAlign = HorizontalImageAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_hAlign));
		if (horizontalImageAlign != null)
		{
			image.setHorizontalImageAlign(horizontalImageAlign);
		}

		VerticalImageAlignEnum verticalImageAlign = VerticalImageAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_vAlign));
		if (verticalImageAlign != null)
		{
			image.setVerticalImageAlign(verticalImageAlign);
		}

		OnErrorTypeEnum onErrorType = OnErrorTypeEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_onErrorType));
		if (onErrorType != null)
		{
			image.setOnErrorType(onErrorType);
		}

		image.setLinkType(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkType));
		image.setLinkTarget(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkTarget));
		image.setAnchorName(atts.getValue(JRXmlConstants.ATTRIBUTE_anchorName));
		image.setHyperlinkReference(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkReference));
		image.setHyperlinkAnchor(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkAnchor));
		
		String hyperlinkPage = atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkPage);
		if (hyperlinkPage != null)
		{
			image.setHyperlinkPage(Integer.valueOf(hyperlinkPage));
		}
		
		image.setHyperlinkTooltip(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkTooltip));

		String bookmarkLevelAttr = atts.getValue(JRXmlConstants.ATTRIBUTE_bookmarkLevel);
		if (bookmarkLevelAttr != null)
		{
			image.setBookmarkLevel(Integer.parseInt(bookmarkLevelAttr));
		}
		
		String isLazy = atts.getValue(JRXmlConstants.ATTRIBUTE_isLazy);
		if (isLazy != null && isLazy.length() > 0)
		{
			//we use a resource renderer just to pass the value of isLazy flag to image source factory
			image.setRenderer(ResourceRenderer.getInstance("", Boolean.valueOf(isLazy).booleanValue()));
		}

		return image;
	}
	

}
