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
package com.github.exerrk.components.map.type;

import com.github.exerrk.engine.type.EnumUtil;
import com.github.exerrk.engine.type.NamedEnum;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public enum MapScaleEnum implements NamedEnum
{
	/**
	 * 
	 */
	ONE("1"),

	/**
	 * 
	 */
	TWO("2"),

	/**
	 * 
	 */
	FOUR("4");

	/**
	 *
	 */
	private final transient String name;

	private MapScaleEnum(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 *
	 */
	public static MapScaleEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
}
