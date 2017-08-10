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
package com.github.exerrk.components.map.fill;

import com.github.exerrk.components.list.FillDatasetRun;
import com.github.exerrk.engine.JRDatasetRun;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.fill.JRFillDataset;
import com.github.exerrk.engine.fill.JRFillObjectFactory;


/**
 * Used to iterate on a subdataset and create a sorted data source.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class MarkerFillDatasetRun extends FillDatasetRun
{

//	private JRSortField[] allSortFields;
//	private SortInfo sortInfo;
//	private int recordIndex;
//	private List<SortedDataSource.SortRecord> records;

	
	public MarkerFillDatasetRun(JRDatasetRun datasetRun, JRFillObjectFactory factory) throws JRException
	{
		super(datasetRun, factory);
	}


	@Override
	protected JRFillDataset getDataset()
	{
		// only added here for visibility
		return super.getDataset();
	}
}