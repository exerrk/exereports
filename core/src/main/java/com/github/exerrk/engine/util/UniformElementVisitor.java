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

import com.github.exerrk.crosstabs.JRCrosstab;
import com.github.exerrk.engine.JRBreak;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRElement;
import com.github.exerrk.engine.JREllipse;
import com.github.exerrk.engine.JRFrame;
import com.github.exerrk.engine.JRGenericElement;
import com.github.exerrk.engine.JRImage;
import com.github.exerrk.engine.JRLine;
import com.github.exerrk.engine.JRRectangle;
import com.github.exerrk.engine.JRStaticText;
import com.github.exerrk.engine.JRSubreport;
import com.github.exerrk.engine.JRTextField;
import com.github.exerrk.engine.JRVisitor;

/**
 * An abstract visitor class that treats all report elements in the same way. 
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @see #visitElement(JRElement)
 */
public abstract class UniformElementVisitor implements JRVisitor
{

	/**
	 * Method that gets called when any element is visited, no matter what its type is.
	 * 
	 * @param element the element to be visited
	 */
	protected abstract void visitElement(JRElement element);
	
	@Override
	public void visitBreak(JRBreak breakElement)
	{
		visitElement(breakElement);
	}

	@Override
	public void visitChart(JRChart chart)
	{
		visitElement(chart);
	}

	@Override
	public void visitComponentElement(JRComponentElement componentElement)
	{
		visitElement(componentElement);
	}

	@Override
	public void visitCrosstab(JRCrosstab crosstab)
	{
		visitElement(crosstab);
	}

	@Override
	public void visitEllipse(JREllipse ellipse)
	{
		visitElement(ellipse);
	}

	@Override
	public void visitFrame(JRFrame frame)
	{
		visitElement(frame);
	}

	@Override
	public void visitGenericElement(JRGenericElement element)
	{
		visitElement(element);
	}

	@Override
	public void visitImage(JRImage image)
	{
		visitElement(image);
	}

	@Override
	public void visitLine(JRLine line)
	{
		visitElement(line);
	}

	@Override
	public void visitRectangle(JRRectangle rectangle)
	{
		visitElement(rectangle);
	}

	@Override
	public void visitStaticText(JRStaticText staticText)
	{
		visitElement(staticText);
	}

	@Override
	public void visitSubreport(JRSubreport subreport)
	{
		visitElement(subreport);
	}

	@Override
	public void visitTextField(JRTextField textField)
	{
		visitElement(textField);
	}

}
