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
package com.github.exerrk.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.ParameterContributor;
import com.github.exerrk.engine.ParameterContributorContext;
import com.github.exerrk.engine.ParameterContributorFactory;
import com.github.exerrk.properties.PropertyConstants;
import com.github.exerrk.repo.DataAdapterResource;
import com.github.exerrk.repo.RepositoryUtil;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public final class DataAdapterParameterContributorFactory implements ParameterContributorFactory
{

	/**
	 * A report/dataset level property that provides the location of a data adapter resource 
	 * to be used for the dataset.
	 */
	@Property(
			category = PropertyConstants.CATEGORY_DATA_SOURCE,
			valueType = DataAdapter.class,//for JSS
			scopes = {PropertyScope.CONTEXT, PropertyScope.DATASET},
			sinceVersion = PropertyConstants.VERSION_4_1_1
			)
	public static final String PROPERTY_DATA_ADAPTER_LOCATION = JRPropertiesUtil.PROPERTY_PREFIX + "data.adapter";

	private static final DataAdapterParameterContributorFactory INSTANCE = new DataAdapterParameterContributorFactory();
	
	private DataAdapterParameterContributorFactory()
	{
	}
	
	/**
	 * 
	 */
	public static DataAdapterParameterContributorFactory getInstance()
	{
		return INSTANCE;
	}

	@Override
	public List<ParameterContributor> getContributors(ParameterContributorContext context) throws JRException
	{
		List<ParameterContributor> contributors = new ArrayList<ParameterContributor>();

		String dataAdapterUri = JRPropertiesUtil.getInstance(context.getJasperReportsContext()).getProperty(context.getDataset(), PROPERTY_DATA_ADAPTER_LOCATION); 
		if (dataAdapterUri != null)
		{
			DataAdapterResource dataAdapterResource = RepositoryUtil.getInstance(context.getJasperReportsContext()).getResourceFromLocation(dataAdapterUri, DataAdapterResource.class);
			ParameterContributor dataAdapterService = DataAdapterServiceUtil.getInstance(context).getService(dataAdapterResource.getDataAdapter());
			
			return Collections.singletonList(dataAdapterService);
		}

		return contributors;
	}
	
}
