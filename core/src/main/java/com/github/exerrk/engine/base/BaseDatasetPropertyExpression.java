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

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.DatasetPropertyExpression;
import com.github.exerrk.engine.type.PropertyEvaluationTimeEnum;

/**
 * Base implementation of {@link DatasetPropertyExpression}.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class BaseDatasetPropertyExpression extends JRBasePropertyExpression implements DatasetPropertyExpression
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";

	private PropertyEvaluationTimeEnum evaluationTime;

	protected BaseDatasetPropertyExpression()
	{
		//empty
	}
	
	public BaseDatasetPropertyExpression(
		DatasetPropertyExpression propertyExpression,
		JRBaseObjectFactory factory)
	{
		super(propertyExpression, factory);
		this.evaluationTime = propertyExpression.getEvaluationTime();
	}

	@Override
	public PropertyEvaluationTimeEnum getEvaluationTime()
	{
		return evaluationTime;
	}

	protected void setEvaluationTime(PropertyEvaluationTimeEnum evaluationTime)
	{
		Object old = this.evaluationTime;
		this.evaluationTime = evaluationTime;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, old, this.evaluationTime);
	}
}
