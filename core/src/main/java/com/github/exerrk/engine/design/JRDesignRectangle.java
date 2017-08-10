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
package com.github.exerrk.engine.design;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRDefaultStyleProvider;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRRectangle;
import com.github.exerrk.engine.JRVisitor;
import com.github.exerrk.engine.base.JRBaseStyle;
import com.github.exerrk.engine.design.events.JRPropertyChangeSupport;


/**
 * The actual implementation of a graphic element representing a rectangle, used at design time.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRDesignRectangle extends JRDesignGraphicElement implements JRRectangle
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	protected Integer radius;


	/**
	 *
	 */
	public JRDesignRectangle()
	{
		super(null);
	}
		

	/**
	 *
	 */
	public JRDesignRectangle(JRDefaultStyleProvider defaultStyleProvider)
	{
		super(defaultStyleProvider);
	}
		

	@Override
	public int getRadius()
	{
		return getStyleResolver().getRadius(this);
	}

	@Override
	public Integer getOwnRadius()
	{
		return this.radius;
	}

	@Override
	public void setRadius(int radius)
	{
		setRadius(Integer.valueOf(radius));
	}

	@Override
	public void setRadius(Integer radius)
	{
		Object old = this.radius;
		this.radius = radius;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_RADIUS, old, this.radius);
	}

	@Override
	public void visit(JRVisitor visitor)
	{
		visitor.visitRectangle(this);
	}

	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}
	
	@Override
	public Object clone()
	{
		JRDesignRectangle clone = (JRDesignRectangle)super.clone();
		clone.eventSupport = null;
		return clone;
	}

	private transient JRPropertyChangeSupport eventSupport;
	
	@Override
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}

}