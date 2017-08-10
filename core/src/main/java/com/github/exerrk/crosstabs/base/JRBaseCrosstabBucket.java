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
package com.github.exerrk.crosstabs.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.github.exerrk.crosstabs.JRCrosstabBucket;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.analytics.dataset.BucketOrder;
import com.github.exerrk.engine.base.JRBaseObjectFactory;
import com.github.exerrk.engine.type.SortOrderEnum;
import com.github.exerrk.engine.util.JRClassLoader;
import com.github.exerrk.engine.util.JRCloneUtils;

/**
 * Base read-only implementation of {@link com.github.exerrk.crosstabs.JRCrosstabBucket JRCrosstabBucket}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class JRBaseCrosstabBucket implements JRCrosstabBucket, Serializable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String EXCEPTION_MESSAGE_KEY_BUCKET_LOAD_ERROR = "crosstabs.bucket.load.error";

	protected String valueClassName;
	protected String valueClassRealName;
	protected Class<?> valueClass;

	// only used for deserialization
	@Deprecated
	protected SortOrderEnum orderValue = SortOrderEnum.ASCENDING;
	protected BucketOrder bucketOrder = BucketOrder.ASCENDING;
	
	protected JRExpression expression;
	protected JRExpression orderByExpression;
	protected JRExpression comparatorExpression;

	protected JRBaseCrosstabBucket()
	{
	}
	
	public JRBaseCrosstabBucket(JRCrosstabBucket bucket, JRBaseObjectFactory factory)
	{
		factory.put(bucket, this);
		
		this.valueClassName = bucket.getValueClassName();
		this.bucketOrder = bucket.getOrder();
		this.expression = factory.getExpression(bucket.getExpression());
		this.orderByExpression = factory.getExpression(bucket.getOrderByExpression());
		this.comparatorExpression = factory.getExpression(bucket.getComparatorExpression());
	}

	@Override
	public String getValueClassName()
	{
		return valueClassName;
	}

	@Override
	@Deprecated
	public SortOrderEnum getOrderValue()
	{
		return BucketOrder.toSortOrderEnum(bucketOrder);
	}

	@Override
	public BucketOrder getOrder()
	{
		return bucketOrder;
	}

	@Override
	public JRExpression getExpression()
	{
		return expression;
	}

	@Override
	public JRExpression getOrderByExpression()
	{
		return orderByExpression;
	}

	@Override
	public JRExpression getComparatorExpression()
	{
		return comparatorExpression;
	}
	
	@Override
	public Class<?> getValueClass()
	{
		if (valueClass == null)
		{
			String className = getValueClassRealName();
			if (className != null)
			{
				try
				{
					valueClass = JRClassLoader.loadClassForName(className);
				}
				catch (ClassNotFoundException e)
				{
					throw 
						new JRRuntimeException(
							EXCEPTION_MESSAGE_KEY_BUCKET_LOAD_ERROR,
							(Object[])null,
							e);
				}
			}
		}
		
		return valueClass;
	}

	/**
	 *
	 */
	private String getValueClassRealName()
	{
		if (valueClassRealName == null)
		{
			valueClassRealName = JRClassLoader.getClassRealName(valueClassName);
		}
		
		return valueClassRealName;
	}

	@Override
	public Object clone()
	{
		JRBaseCrosstabBucket clone = null;
		try
		{
			clone = (JRBaseCrosstabBucket) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// never
			throw new JRRuntimeException(e);
		}
		clone.expression = JRCloneUtils.nullSafeClone(expression);
		clone.orderByExpression = JRCloneUtils.nullSafeClone(orderByExpression);
		clone.comparatorExpression = JRCloneUtils.nullSafeClone(comparatorExpression);
		return clone;
	}


	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private byte order;
	
	@SuppressWarnings("deprecation")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			SortOrderEnum sortOrder = SortOrderEnum.getByValue(order);
			bucketOrder = BucketOrder.fromSortOrderEnum(sortOrder);
		}
		else if (orderValue != null && bucketOrder == null)
		{
			// deserializing old version object
			bucketOrder = BucketOrder.fromSortOrderEnum(orderValue);
			orderValue = null;
		}

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_4_0_3)
		{
			//expression can never be null due to verifier
			valueClassName = getExpression().getValueClassName();//we probably can never remove this method from expression, if we want to preserve backward compatibility
		}
	}
	
}
