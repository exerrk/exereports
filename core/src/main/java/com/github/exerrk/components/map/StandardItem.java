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
package com.github.exerrk.components.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.components.items.Item;
import com.github.exerrk.components.items.ItemProperty;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.design.events.JRChangeEventsSupport;
import com.github.exerrk.engine.design.events.JRPropertyChangeSupport;
import com.github.exerrk.engine.util.JRCloneUtils;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @deprecated Replaced by {@link com.github.exerrk.components.items.StandardItem}.
 */
public class StandardItem implements Item, JRChangeEventsSupport, Serializable
{
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	public static final String PROPERTY_ITEM_PROPERTIES = "itemProperties";
	
	private transient JRPropertyChangeSupport eventSupport;

	private List<ItemProperty> properties = new ArrayList<ItemProperty>();

	public StandardItem()
	{
	}
	
	public StandardItem(List<ItemProperty> properties)
	{
		this.properties = properties;
	}
	
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

	@Override
	public Object clone()
	{
		StandardItem clone = null;
		try
		{
			clone = (StandardItem) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// never
			throw new RuntimeException(e);
		}
		clone.properties = JRCloneUtils.cloneList(properties);
		return clone;
	}

	@Override
	public List<ItemProperty> getProperties() 
	{
		return properties;
	}
	
	public void addItemProperty(ItemProperty property)
	{
		properties.add(property);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_ITEM_PROPERTIES, property, properties.size() - 1);
	}
	
	public void removeItemProperty(ItemProperty property)
	{
		int idx = properties.indexOf(property);
		if (idx >= 0)
		{
			properties.remove(idx);
			
			getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_ITEM_PROPERTIES, 
					property, idx);
		}
	}
}
