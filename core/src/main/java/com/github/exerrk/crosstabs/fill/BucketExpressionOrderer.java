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
package com.github.exerrk.crosstabs.fill;

import java.util.Comparator;

import com.github.exerrk.crosstabs.fill.calculation.BucketingData;
import com.github.exerrk.crosstabs.fill.calculation.BucketDefinition.Bucket;
import com.github.exerrk.crosstabs.fill.calculation.BucketingService.BucketMap;
import com.github.exerrk.crosstabs.fill.calculation.BucketingServiceContext;
import com.github.exerrk.crosstabs.fill.calculation.MeasureDefinition.MeasureValue;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class BucketExpressionOrderer implements BucketOrderer
{
	private final JRExpression orderByExpression;
	private final Comparator<Object> orderValueComparator;
	
	private BucketingData bucketingData;
	
	public BucketExpressionOrderer(JRExpression orderByExpression, Comparator<Object> orderValueComparator)
	{
		this.orderByExpression = orderByExpression;
		this.orderValueComparator = orderValueComparator;
	}

	@Override
	public void init(BucketingData bucketingData)
	{
		this.bucketingData = bucketingData;
	}

	@Override
	public Object getOrderValue(BucketMap bucketMap, Bucket bucketValue) throws JRException
	{
		MeasureValue[] bucketTotals = bucketingData.getMeasureTotals(bucketMap, bucketValue);
		BucketingServiceContext serviceContext = bucketingData.getServiceContext();
		return serviceContext.evaluateMeasuresExpression(orderByExpression, bucketTotals);
	}
	
	@Override
	public int compareOrderValues(Object value1, Object value2)
	{
		// FIXME lucianc handle nulls
		return orderValueComparator.compare(value1, value2);
	}

}
