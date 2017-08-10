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

import java.io.Serializable;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRPropertyExpression;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.design.events.JRChangeEventsSupport;
import com.github.exerrk.engine.design.events.JRPropertyChangeSupport;
import com.github.exerrk.engine.util.JRCloneUtils;

/**
 * Base implementation of {@link JRPropertyExpression}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class JRBasePropertyExpression implements JRPropertyExpression, Serializable, JRChangeEventsSupport
{
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_VALUE_EXPRESSION = "valueExpression";
	/**
	 * @deprecated Replaced by {@link #PROPERTY_VALUE_EXPRESSION}. 
	 */
	public static final String pROPERTY_VALUE_EXPRESSION = PROPERTY_VALUE_EXPRESSION;

	private String name;
	private JRExpression valueExpression;

	protected JRBasePropertyExpression()
	{
		//empty
	}
	
	public JRBasePropertyExpression(JRPropertyExpression propertyExpression,
			JRBaseObjectFactory factory)
	{
		this.name = propertyExpression.getName();
		this.valueExpression = factory.getExpression(propertyExpression.getValueExpression());
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		Object old = this.name;
		this.name = name;
		getEventSupport().firePropertyChange(PROPERTY_NAME, old, this.name);
	}

	@Override
	public JRExpression getValueExpression()
	{
		return valueExpression;
	}

	protected void setValueExpression(JRExpression valueExpression)
	{
		Object old = this.valueExpression;
		this.valueExpression = valueExpression;
		getEventSupport().firePropertyChange(PROPERTY_VALUE_EXPRESSION, old, this.valueExpression);
	}
	
	@Override
	public Object clone()
	{
		JRBasePropertyExpression clone = null;
		
		try
		{
			clone = (JRBasePropertyExpression) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
		
		clone.valueExpression = JRCloneUtils.nullSafeClone(valueExpression);
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