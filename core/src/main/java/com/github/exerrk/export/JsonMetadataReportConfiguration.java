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
import com.github.exerrk.export.annotations.ExporterProperty;
import com.github.exerrk.properties.PropertyConstants;


/**
 * Interface containing settings used by the JSON Metadata exporter.
 *
 * @see com.github.exerrk.engine.export.JsonMetadataExporter
 *
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public interface JsonMetadataReportConfiguration extends JsonReportConfiguration {

	/**
	 * Property whose value is used as default for the {@link #isEscapeMembers()} export configuration setting.
	 * <p>
	 * The property itself defaults to <code>true</code>.
	 * </p>
	 *
	 * @see com.github.exerrk.engine.JRPropertiesUtil
	 */
	@Property(
			category = PropertyConstants.CATEGORY_EXPORT,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.REPORT},
			sinceVersion = PropertyConstants.VERSION_6_0_0,
			valueType = Boolean.class
			)
	public static final String JSON_EXPORTER_ESCAPE_MEMBERS = JRPropertiesUtil.PROPERTY_PREFIX + "export.json.escape.members";


	/**
	 * Property whose value is used as default for the {@link #getJsonSchemaResource()} export configuration setting.
	 *
	 * @see com.github.exerrk.engine.JRPropertiesUtil
	 */
	@Property(
			category = PropertyConstants.CATEGORY_EXPORT,
			scopes = {PropertyScope.REPORT},
			sinceVersion = PropertyConstants.VERSION_6_0_0
			)
	public static final String JSON_EXPORTER_JSON_SCHEMA = JRPropertiesUtil.PROPERTY_PREFIX + "export.json.schema";



	/**
	 *
	 */
	@ExporterProperty(
		value=JSON_EXPORTER_ESCAPE_MEMBERS,
		booleanDefault=true
		)
	public Boolean isEscapeMembers();

	/**
	 *
	 */
	@ExporterProperty(value=JSON_EXPORTER_JSON_SCHEMA)
	public String getJsonSchemaResource();
}