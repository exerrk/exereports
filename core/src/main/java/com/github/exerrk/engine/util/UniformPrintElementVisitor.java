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
package com.github.exerrk.engine.util;

import java.util.List;

import com.github.exerrk.engine.JRGenericPrintElement;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRPrintEllipse;
import com.github.exerrk.engine.JRPrintFrame;
import com.github.exerrk.engine.JRPrintImage;
import com.github.exerrk.engine.JRPrintLine;
import com.github.exerrk.engine.JRPrintRectangle;
import com.github.exerrk.engine.JRPrintText;
import com.github.exerrk.engine.PrintElementVisitor;

/**
 * Print element visitor that delegates all visit calls to a single method.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public abstract class UniformPrintElementVisitor<T> implements
		PrintElementVisitor<T>
{

	private final boolean deep;
	
	/**
	 * Creates a uniform visitor.
	 */
	protected UniformPrintElementVisitor()
	{
		this.deep = false;
	}
	
	/**
	 * Creates an optionally deep uniform visitor.
	 * 
	 * @param deep whether elements are to be deeply visited
	 * @see DeepPrintElementVisitor
	 */
	protected UniformPrintElementVisitor(boolean deep)
	{
		this.deep = deep;
	}
	
	protected abstract void visitElement(JRPrintElement element, T arg);
	
	@Override
	public void visit(JRPrintText textElement, T arg)
	{
		visitElement(textElement, arg);
	}

	@Override
	public void visit(JRPrintImage image, T arg)
	{
		visitElement(image, arg);
	}

	@Override
	public void visit(JRPrintRectangle rectangle, T arg)
	{
		visitElement(rectangle, arg);
	}

	@Override
	public void visit(JRPrintLine line, T arg)
	{
		visitElement(line, arg);
	}

	@Override
	public void visit(JRPrintEllipse ellipse, T arg)
	{
		visitElement(ellipse, arg);
	}

	@Override
	public void visit(JRPrintFrame frame, T arg)
	{
		visitElement(frame, arg);
		
		if (deep)
		{
			List<JRPrintElement> elements = frame.getElements();
			if (elements != null)
			{
				for (JRPrintElement element : elements)
				{
					element.accept(this, arg);
				}
			}
		}
	}

	@Override
	public void visit(JRGenericPrintElement printElement, T arg)
	{
		visitElement(printElement, arg);
	}

}
