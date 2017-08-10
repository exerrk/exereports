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
package com.github.exerrk.components.spiderchart;

import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.charts.JRCategorySeries;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.base.JRBaseObjectFactory;
import com.github.exerrk.engine.design.JRDesignElementDataset;

/**
 * 
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class StandardSpiderDataset extends JRDesignElementDataset implements SpiderDataset
{
	
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_CATEGORY_SERIES = "categorySeries";
	
	private List<JRCategorySeries> categorySeriesList = new ArrayList<JRCategorySeries>();
	
	public StandardSpiderDataset()
	{
		super();
	}

	public StandardSpiderDataset(SpiderDataset dataset, JRBaseObjectFactory factory)
	{
		super(dataset, factory);
		JRCategorySeries[] srcCategorySeries = dataset.getSeries();
		if (srcCategorySeries != null && srcCategorySeries.length > 0)
		{
			for(int i = 0; i < srcCategorySeries.length; i++)
			{
				addCategorySeries(factory.getCategorySeries(srcCategorySeries[i]));
			}
		}
	}

	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		SpiderChartCompiler.collectExpressions(this, collector);
	}

	@Override
	public JRCategorySeries[] getSeries()
	{
		JRCategorySeries[] categorySeriesArray = new JRCategorySeries[categorySeriesList.size()];
		categorySeriesList.toArray(categorySeriesArray);
		return categorySeriesArray;
	}

	/**
	 * 
	 */
	public List<JRCategorySeries> getSeriesList()
	{
		return categorySeriesList;
	}

	/**
	 *
	 */
	public void addCategorySeries(JRCategorySeries categorySeries)
	{
		categorySeriesList.add(categorySeries);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_CATEGORY_SERIES, 
				categorySeries, categorySeriesList.size() - 1);
	}
	
	/**
	 *
	 */
	public void addCategorySeries(int index, JRCategorySeries categorySeries)
	{
		if(index >=0 && index < categorySeriesList.size())
			categorySeriesList.add(index, categorySeries);
		else{
			categorySeriesList.add(categorySeries);
			index = categorySeriesList.size() - 1;
		}
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_CATEGORY_SERIES, 
				categorySeries, index);
	}

	/**
	 *
	 */
	public JRCategorySeries removeCategorySeries(JRCategorySeries categorySeries)
	{
		if (categorySeries != null)
		{
			int idx = categorySeriesList.indexOf(categorySeries);
			if (idx >= 0)
			{
				categorySeriesList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_CATEGORY_SERIES, 
						categorySeries, idx);
			}
		}
		
		return categorySeries;
	}

}
