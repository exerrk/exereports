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

import com.github.exerrk.data.http.HttpDataLocation;
import com.github.exerrk.data.http.HttpDataService;
import com.github.exerrk.engine.ParameterContributorContext;
import com.github.exerrk.engine.util.Designator;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class BuiltinDataFileServiceFactory implements DataFileServiceFactory, Designator<DataFile>
{
	
	private static final BuiltinDataFileServiceFactory INSTANCE = new BuiltinDataFileServiceFactory();
	
	public static BuiltinDataFileServiceFactory instance()
	{
		return INSTANCE;
	}
	
	protected BuiltinDataFileServiceFactory()
	{
	}

	@Override
	public DataFileService createService(ParameterContributorContext context, DataFile dataFile)
	{
		if (dataFile instanceof RepositoryDataLocation)
		{
			return new RepositoryDataLocationService(context, (RepositoryDataLocation) dataFile);
		}
		if (dataFile instanceof HttpDataLocation)
		{
			return new HttpDataService(context, (HttpDataLocation) dataFile);
		}
		return null;
	}

	@Override
	public String getName(DataFile dataFile)
	{
		if (dataFile instanceof HttpDataLocation)
		{
			return HttpDataService.HTTP_DATA_SERVICE_NAME;
		}
		
		return null;
	}

}
