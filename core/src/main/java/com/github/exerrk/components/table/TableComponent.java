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
package com.github.exerrk.components.table;

import java.util.List;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRCloneable;
import com.github.exerrk.engine.JRDatasetRun;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JRVisitable;
import com.github.exerrk.engine.component.Component;
import com.github.exerrk.properties.PropertyConstants;


/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface TableComponent extends Component, JRCloneable, JRVisitable
{
	/**
	 * Property that specifies a default value for the <code>whenNoDataType</code> attribute of table components.
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = "Blank",
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT},
			sinceVersion = PropertyConstants.VERSION_6_0_0,
			valueType = WhenNoDataTypeTableEnum.class
			)
	public static final String CONFIG_PROPERTY_WHEN_NO_DATA_TYPE = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.when.no.data.type";

	/**
	 * @deprecated Replaced by {@link #CONFIG_PROPERTY_WHEN_NO_DATA_TYPE}.
	 */
	public static final String PROPERTY_WHEN_NO_DATA_TYPE = CONFIG_PROPERTY_WHEN_NO_DATA_TYPE;

	JRDatasetRun getDatasetRun();

	List<BaseColumn> getColumns();
	
	WhenNoDataTypeTableEnum getWhenNoDataType();
	
}
