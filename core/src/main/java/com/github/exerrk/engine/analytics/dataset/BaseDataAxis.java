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
package com.github.exerrk.engine.analytics.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.analytics.data.Axis;
import com.github.exerrk.engine.base.JRBaseObjectFactory;
import com.github.exerrk.engine.util.JRCloneUtils;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class BaseDataAxis implements DataAxis, Serializable
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected Axis axis;
	protected List<DataAxisLevel> levels;
	
	public BaseDataAxis()
	{
		this.levels = new ArrayList<DataAxisLevel>();
	}
	
	public BaseDataAxis(DataAxis dataAxis, JRBaseObjectFactory factory)
	{
		factory.put(dataAxis, this);
		
		this.axis = dataAxis.getAxis();
		
		List<DataAxisLevel> dataLevels = dataAxis.getLevels();
		this.levels = new ArrayList<DataAxisLevel>(dataLevels.size());
		for (DataAxisLevel level : dataLevels)
		{
			this.levels.add(factory.getDataAxisLevel(level));
		}
	}

	@Override
	public Axis getAxis()
	{
		return axis;
	}

	@Override
	public List<DataAxisLevel> getLevels()
	{
		return levels;
	}

	@Override
	public Object clone() 
	{
		BaseDataAxis clone = null;
		try
		{
			clone = (BaseDataAxis) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// never
			throw new JRRuntimeException(e);
		}
		
		clone.levels = JRCloneUtils.cloneList(levels);
		return clone;
	}

}
