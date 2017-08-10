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

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JREllipse;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRVisitor;


/**
 * The actual implementation of a graphic element representing an ellipse.
 *
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRBaseEllipse extends JRBaseGraphicElement implements JREllipse
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;


	/**
	 * Initializes properties that are specific to ellipses. Common properties are initialized by its
	 * parent constructors.
	 * @param ellipse an element whose properties are copied to this element. Usually it is a
	 * {@link com.github.exerrk.engine.design.JRDesignEllipse} that must be transformed into an
	 * <tt>JRBaseEllipse</tt> at compile time.
	 * @param factory a factory used in the compile process
	 */
	protected JRBaseEllipse(JREllipse ellipse, JRBaseObjectFactory factory)
	{
		super(ellipse, factory);
	}


	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	@Override
	public void visit(JRVisitor visitor)
	{
		visitor.visitEllipse(this);
	}


}
