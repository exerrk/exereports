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

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRLine;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRVisitor;
import com.github.exerrk.engine.type.LineDirectionEnum;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRFillLine extends JRFillGraphicElement implements JRLine
{


	/**
	 *
	 */
	protected JRFillLine(
		JRBaseFiller filler,
		JRLine line, 
		JRFillObjectFactory factory
		)
	{
		super(filler, line, factory);
	}


	protected JRFillLine(JRFillLine line, JRFillCloneFactory factory)
	{
		super(line, factory);
	}


	@Override
	public LineDirectionEnum getDirectionValue()
	{
		return ((JRLine)this.parent).getDirectionValue();
	}
		
	@Override
	public void setDirection(LineDirectionEnum direction)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 */
	protected JRTemplateLine getJRTemplateLine()
	{
		return (JRTemplateLine) getElementTemplate();
	}

	@Override
	protected JRTemplateElement createElementTemplate()
	{
		return new JRTemplateLine(
				getElementOrigin(), 
				filler.getJasperPrint().getDefaultStyleProvider(), 
				this
				);
	}


	@Override
	protected void evaluate(
		byte evaluation
		) throws JRException
	{
		this.reset();
		
		this.evaluatePrintWhenExpression(evaluation);
		evaluateProperties(evaluation);
		evaluateStyle(evaluation);
		
		setValueRepeating(true);
	}


	@Override
	protected JRPrintElement fill()
	{
		JRTemplatePrintLine printLine = new JRTemplatePrintLine(this.getJRTemplateLine(), printElementOriginator);
		printLine.setUUID(this.getUUID());
		printLine.setX(this.getX());
		printLine.setY(this.getRelativeY());
		printLine.setWidth(getWidth());
		printLine.setHeight(this.getStretchHeight());
		transferProperties(printLine);
		
		return printLine;
	}


	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	@Override
	public void visit(JRVisitor visitor)
	{
		visitor.visitLine(this);
	}

	@Override
	protected void resolveElement (JRPrintElement element, byte evaluation)
	{
		// nothing
	}


	@Override
	public JRFillCloneable createClone(JRFillCloneFactory factory)
	{
		return new JRFillLine(this, factory);
	}

}
