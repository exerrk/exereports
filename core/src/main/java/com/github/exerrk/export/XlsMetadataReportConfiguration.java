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
package com.github.exerrk.export;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.export.JRXlsMetadataExporter;
import com.github.exerrk.export.annotations.ExporterParameter;
import com.github.exerrk.export.annotations.ExporterProperty;
import com.github.exerrk.properties.PropertyConstants;


/**
 * Interface containing settings used by the Excel metadata exporters.
 *
 * @see JRXlsMetadataExporter
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface XlsMetadataReportConfiguration extends XlsReportConfiguration
{
	/**
	 * Property whose value is used as default for the {@link #isWriteHeader()} export configuration flag.
	 * <p>
	 * The property itself defaults to <code>false</code>.
	 * </p>
	 * 
	 * @see JRPropertiesUtil
	 */
	@Property(
			category = PropertyConstants.CATEGORY_EXPORT,
			defaultValue = PropertyConstants.BOOLEAN_FALSE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT},
			sinceVersion = PropertyConstants.VERSION_4_0_2,
			valueType = Boolean.class
			)
	public static final String PROPERTY_WRITE_HEADER = JRPropertiesUtil.PROPERTY_PREFIX + "export.xls.write.header";

	/**
	 * Properties having this prefix contain comma-separated column names. If set, these values are considered 
	 * as defaults for the {@link #getColumnNames()} export configuration setting.
	 * 
	 * @see JRPropertiesUtil
	 */
	@Property(
			name = "com.github.exerrk.export.xls.column.names.{arbitrary_name}",
			category = PropertyConstants.CATEGORY_EXPORT,
			scopes = {PropertyScope.REPORT},
			sinceVersion = PropertyConstants.VERSION_4_0_2
			)
	public static final String PROPERTY_COLUMN_NAMES_PREFIX = JRPropertiesUtil.PROPERTY_PREFIX + "export.xls.column.names";

	/**
	 * Returns a boolean that specifies whether the export header (the column names) should be written or not.
	 * @see #PROPERTY_WRITE_HEADER
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=com.github.exerrk.engine.export.JRXlsAbstractMetadataExporterParameter.class,
		name="WRITE_HEADER"
		)
	@ExporterProperty(
		value=PROPERTY_WRITE_HEADER, 
		booleanDefault=false
		)
	public Boolean isWriteHeader();

	/**
	 * Returns an array of strings representing the comma-separated names of the columns that should be exported.
	 * NOTE: The order of the columns is important and for accurate results they should be in the same order as the original columns.
	 * @see #PROPERTY_COLUMN_NAMES_PREFIX
	 */
	@SuppressWarnings("deprecation")
	@ExporterParameter(
		type=com.github.exerrk.engine.export.JRXlsAbstractMetadataExporterParameter.class,
		name="COLUMN_NAMES"
		)
	@ExporterProperty(PROPERTY_COLUMN_NAMES_PREFIX)
	public String[] getColumnNames();
}
