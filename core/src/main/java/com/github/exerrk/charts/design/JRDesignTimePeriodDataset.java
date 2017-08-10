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
package com.github.exerrk.charts.design;

import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.charts.JRTimePeriodDataset;
import com.github.exerrk.charts.JRTimePeriodSeries;
import com.github.exerrk.engine.JRChartDataset;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.design.JRDesignChartDataset;
import com.github.exerrk.engine.design.JRVerifier;
import com.github.exerrk.engine.util.JRCloneUtils;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 */
public class JRDesignTimePeriodDataset extends JRDesignChartDataset implements JRTimePeriodDataset {
	
	/**
	 * 
	 */
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_TIME_PERIODS_SERIES = "timePeriodSeries";
	
	private List<JRTimePeriodSeries> timePeriodSeriesList = new ArrayList<JRTimePeriodSeries>();
	

	/**
	 * 
	 */
	public JRDesignTimePeriodDataset(JRChartDataset dataset)
	{
		super( dataset );
	}
	
	@Override
	public JRTimePeriodSeries[] getSeries()
	{
		JRTimePeriodSeries[] timePeriodSeriesArray = new JRTimePeriodSeries[timePeriodSeriesList.size()];
		timePeriodSeriesList.toArray(timePeriodSeriesArray);
		
		return timePeriodSeriesArray;
	}
	
	/**
	 * 
	 */
	public List<JRTimePeriodSeries> getSeriesList()
	{
		return timePeriodSeriesList;
	}

	/**
	 * 
	 */
	public void addTimePeriodSeries( JRTimePeriodSeries timePeriodSeries ) 
	{
		timePeriodSeriesList.add(timePeriodSeries);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_TIME_PERIODS_SERIES, 
				timePeriodSeries, timePeriodSeriesList.size() - 1);
	}
	
	/**
	 * 
	 */
	public void addTimePeriodSeries(int index, JRTimePeriodSeries timePeriodSeries ) 
	{
		timePeriodSeriesList.add(index, timePeriodSeries);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_TIME_PERIODS_SERIES, 
				timePeriodSeries, index);
	}
	
	/**
	 * 
	 */
	public JRTimePeriodSeries removeTimePeriodSeries(JRTimePeriodSeries timePeriodSeries)
	{
		if( timePeriodSeries != null)
		{
			int idx = timePeriodSeriesList.indexOf(timePeriodSeries);
			if (idx >= 0)
			{
				timePeriodSeriesList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_TIME_PERIODS_SERIES, 
						timePeriodSeries, idx);
			}
		}
		
		return timePeriodSeries;
	}
	
	@Override
	public byte getDatasetType() 
	{
		return JRChartDataset.TIMEPERIOD_DATASET;
	}
	
	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	@Override
	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

	@Override
	public Object clone() 
	{
		JRDesignTimePeriodDataset clone = (JRDesignTimePeriodDataset)super.clone();
		clone.timePeriodSeriesList = JRCloneUtils.cloneList(timePeriodSeriesList);
		return clone;
	}
}
