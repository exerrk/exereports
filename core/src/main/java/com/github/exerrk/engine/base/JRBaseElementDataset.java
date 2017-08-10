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
package com.github.exerrk.engine.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRDatasetRun;
import com.github.exerrk.engine.JRElementDataset;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRGroup;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.type.IncrementTypeEnum;
import com.github.exerrk.engine.type.ResetTypeEnum;
import com.github.exerrk.engine.util.JRCloneUtils;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRBaseElementDataset implements JRElementDataset, Serializable
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	protected ResetTypeEnum resetTypeValue = ResetTypeEnum.REPORT;
	protected IncrementTypeEnum incrementTypeValue = IncrementTypeEnum.NONE;
	protected JRGroup resetGroup;
	protected JRGroup incrementGroup;
	
	protected JRDatasetRun datasetRun;
	protected JRExpression incrementWhenExpression;

	
	protected JRBaseElementDataset()
	{
	}

	
	/**
	 *
	 */
	protected JRBaseElementDataset(JRElementDataset dataset)
	{
		if (dataset != null) {
			resetTypeValue = dataset.getResetTypeValue();
			incrementTypeValue = dataset.getIncrementTypeValue();
			resetGroup = dataset.getResetGroup();
			incrementGroup = dataset.getIncrementGroup();
			datasetRun = dataset.getDatasetRun();
			incrementWhenExpression = dataset.getIncrementWhenExpression();
		}
	}


	/**
	 *
	 */
	protected JRBaseElementDataset(JRElementDataset dataset, JRBaseObjectFactory factory)
	{
		factory.put(dataset, this);

		resetTypeValue = dataset.getResetTypeValue();
		incrementTypeValue = dataset.getIncrementTypeValue();
		resetGroup = factory.getGroup(dataset.getResetGroup());
		incrementGroup = factory.getGroup(dataset.getIncrementGroup());
		
		datasetRun = factory.getDatasetRun(dataset.getDatasetRun());
		incrementWhenExpression = factory.getExpression(dataset.getIncrementWhenExpression());
	}

	
	@Override
	public ResetTypeEnum getResetTypeValue()
	{
		return this.resetTypeValue;
	}
		
	@Override
	public IncrementTypeEnum getIncrementTypeValue()
	{
		return this.incrementTypeValue;
	}
		
	@Override
	public JRGroup getResetGroup()
	{
		return resetGroup;
	}
		
	@Override
	public JRGroup getIncrementGroup()
	{
		return incrementGroup;
	}


	@Override
	public JRDatasetRun getDatasetRun()
	{
		return datasetRun;
	}


	@Override
	public JRExpression getIncrementWhenExpression()
	{
		return incrementWhenExpression;
	}

	
	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		// expressions are collected in JRExpressionCollector directly
	}
	

	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2; //NOPMD
	/**
	 * @deprecated
	 */
	private byte resetType;
	/**
	 * @deprecated
	 */
	private byte incrementType;
	
	@SuppressWarnings("deprecation")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			resetTypeValue = ResetTypeEnum.getByValue(resetType);
			incrementTypeValue = IncrementTypeEnum.getByValue(incrementType);
		}
		
	}

	
	@Override
	public Object clone() 
	{
		JRBaseElementDataset clone = null;

		try
		{
			clone = (JRBaseElementDataset)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}

		clone.incrementWhenExpression = JRCloneUtils.nullSafeClone(incrementWhenExpression);
		clone.datasetRun = JRCloneUtils.nullSafeClone(datasetRun);
		
		return clone;
	}

	
}