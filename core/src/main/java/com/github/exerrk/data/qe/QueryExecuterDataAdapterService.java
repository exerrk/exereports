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
package com.github.exerrk.data.qe;

import java.util.Map;

import com.github.exerrk.data.AbstractDataAdapterService;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.ParameterContributorContext;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class QueryExecuterDataAdapterService extends AbstractDataAdapterService 
{

	/**
	 * 
	 */
	public QueryExecuterDataAdapterService(ParameterContributorContext paramContribContext, QueryExecuterDataAdapter qeDataAdapter)
	{
		super(paramContribContext, qeDataAdapter);
	}
	
	/**
	 * @deprecated Replaced by {@link #QueryExecuterDataAdapterService(ParameterContributorContext, QueryExecuterDataAdapter)}.
	 */
	public QueryExecuterDataAdapterService(JasperReportsContext jasperReportsContext, QueryExecuterDataAdapter qeDataAdapter)
	{
		super(jasperReportsContext, qeDataAdapter);
	}
	
	public QueryExecuterDataAdapter getQueryExecuterDataAdapter()
	{
		return (QueryExecuterDataAdapter)getDataAdapter();
	}
	
	@Override
	public void contributeParameters(Map<String, Object> parameters) {
		// does nothing
	}

}
