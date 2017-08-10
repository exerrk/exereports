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
package com.github.exerrk.components.list;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.fill.JRFillCloneFactory;
import com.github.exerrk.engine.fill.JRFillCloneable;
import com.github.exerrk.engine.fill.JRFillElementContainer;
import com.github.exerrk.engine.fill.JRFillObjectFactory;

/**
 * List contents fill element container.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class FillListContents extends JRFillElementContainer
{

	private final int contentsHeight;
	
	protected FillListContents(ListContents listContents,
			JRFillObjectFactory factory)
	{
		super(factory.getFiller(), listContents, factory);
		
		this.contentsHeight = listContents.getHeight();
		
		initElements();
		initConditionalStyles();
	}

	public FillListContents(FillListContents fillListContents,
			JRFillCloneFactory factory)
	{
		super(fillListContents, factory);
		
		this.contentsHeight = fillListContents.contentsHeight;

		initElements();
		initConditionalStyles();
	}

	public int getHeight()
	{
		return contentsHeight;
	}
	
	@Override
	protected int getContainerHeight()
	{
		return contentsHeight;
	}

	@Override
	protected int getActualContainerHeight()
	{
		return getContainerHeight(); 
	}
	
	protected void evaluateContents() throws JRException
	{
		evaluateConditionalStyles(JRExpression.EVALUATION_DEFAULT);
		evaluate(JRExpression.EVALUATION_DEFAULT);
	}
	
	protected void prepare(int availableHeight) throws JRException
	{
		initFill();
		resetElements();
		prepareElements(availableHeight, true);
	}
	
	@Override
	public JRFillCloneable createClone(JRFillCloneFactory factory)
	{
		return new FillListContents(this, factory);
	}
	
	public FillListContents createClone()
	{
		JRFillCloneFactory factory = new JRFillCloneFactory();
		return new FillListContents(this, factory);
	}
	
	// overridden for access
	@Override
	protected int getStretchHeight()
	{
		return super.getStretchHeight();
	}
	
	// overridden for access
	@Override
	protected void rewind() throws JRException
	{
		super.rewind();
	}
	
	protected void stretchTo(int height)
	{
		setStretchHeight(height);
	}

	// double check the usefulness of this whole method when removing legacy stretch;
	// it looks like it will be needed only on HorizontalFillList, in which case its content could be copied only there
	// and this method could be removed
	protected void finalizeElementPositions()
	{
		if (isLegacyElementStretchEnabled())
		{
			stretchElements();
			moveBandBottomElements();
			removeBlankElements();
		}
		else
		{
			stretchElementsToContainer();
			moveBandBottomElements();
		}
	}
}
