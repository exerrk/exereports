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
package com.github.exerrk.engine.fill;

import java.awt.Color;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRPen;
import com.github.exerrk.engine.JRPrintGraphicElement;
import com.github.exerrk.engine.type.FillEnum;


/**
 * Base implementation of {@link com.github.exerrk.engine.JRPrintGraphicElement} that uses
 * a {@link com.github.exerrk.engine.fill.JRTemplateGraphicElement} instance to
 * store common attributes. 
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRTemplatePrintGraphicElement extends JRTemplatePrintElement implements JRPrintGraphicElement
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public JRTemplatePrintGraphicElement()
	{
		
	}
	
	/**
	 *
	 * @deprecated provide a source Id via {@link #JRTemplatePrintGraphicElement(JRTemplateGraphicElement, int)}
	 */
	protected JRTemplatePrintGraphicElement(JRTemplateGraphicElement graphicElement)
	{
		super(graphicElement);
	}

	/**
	 *
	 * @param sourceElementId the Id of the source element
	 * @deprecated replaced by {@link #JRTemplatePrintGraphicElement(JRTemplateGraphicElement, PrintElementOriginator)}
	 */
	protected JRTemplatePrintGraphicElement(JRTemplateGraphicElement graphicElement, int sourceElementId)
	{
		super(graphicElement, sourceElementId);
	}

	/**
	 *
	 * @param originator
	 */
	protected JRTemplatePrintGraphicElement(JRTemplateGraphicElement graphicElement, PrintElementOriginator originator)
	{
		super(graphicElement, originator);
	}

	@Override
	public JRPen getLinePen()
	{
		return ((JRTemplateGraphicElement)template).getLinePen();
	}
		

	@Override
	public FillEnum getFillValue()
	{
		return ((JRTemplateGraphicElement)this.template).getFillValue();
	}

	@Override
	public FillEnum getOwnFillValue()
	{
		return ((JRTemplateGraphicElement)this.template).getOwnFillValue();
	}

	@Override
	public void setFill(FillEnum fill)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Float getDefaultLineWidth() 
	{
		return ((JRTemplateGraphicElement)template).getDefaultLineWidth();
	}

	@Override
	public Color getDefaultLineColor() 
	{
		return ((JRTemplateGraphicElement)template).getDefaultLineColor();
	}

	
}
