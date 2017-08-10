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
package com.github.exerrk.engine.base;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRDefaultStyleProvider;
import com.github.exerrk.engine.JRLineBox;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRPrintElementContainer;
import com.github.exerrk.engine.JRPrintFrame;
import com.github.exerrk.engine.PrintElementVisitor;
import com.github.exerrk.engine.type.ModeEnum;

/**
 * Base implementation of {@link com.github.exerrk.engine.JRPrintFrame JRPrintFrame}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class JRBasePrintFrame extends JRBasePrintElement implements JRPrintFrame, JRPrintElementContainer
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private List<JRPrintElement> elements;
	private JRLineBox lineBox;
	
	public JRBasePrintFrame(JRDefaultStyleProvider defaultStyleProvider)
	{
		super(defaultStyleProvider);
		
		elements = new ArrayList<JRPrintElement>();

		lineBox = new JRBaseLineBox(this);
	}

	@Override
	public ModeEnum getModeValue()
	{
		return getStyleResolver().getMode(this, ModeEnum.TRANSPARENT);
	}
		
	/**
	 *
	 */
	public void copyBox(JRLineBox lineBox)
	{
		this.lineBox = lineBox.clone(this);
	}
	
	@Override
	public JRLineBox getLineBox()
	{
		return lineBox;
	}

	@Override
	public void addElement(JRPrintElement element)
	{
		elements.add(element);
	}
	
	@Override
	public List<JRPrintElement> getElements()
	{
		return elements;
	}
	

	@Override
	public Color getDefaultLineColor() 
	{
		return getForecolor();
	}


	@Override
	public <T> void accept(PrintElementVisitor<T> visitor, T arg)
	{
		visitor.visit(this, arg);
	}
}
