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
package com.github.exerrk.engine.xml;

import org.xml.sax.Attributes;

import com.github.exerrk.engine.design.DesignDatasetPropertyExpression;
import com.github.exerrk.engine.type.PropertyEvaluationTimeEnum;

/**
 * {@link DesignDatasetPropertyExpression} factory.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class DatasetPropertyExpressionFactory extends JRBaseFactory
{

	@Override
	public Object createObject(Attributes attrs) throws Exception
	{
		DesignDatasetPropertyExpression propertyExpression = new DesignDatasetPropertyExpression();
		
		String name = attrs.getValue(JRXmlConstants.ATTRIBUTE_name);
		propertyExpression.setName(name);
		
		PropertyEvaluationTimeEnum evaluationTime = PropertyEvaluationTimeEnum.byName(attrs.getValue(JRXmlConstants.ATTRIBUTE_evaluationTime));
		if (evaluationTime != null)
		{
			propertyExpression.setEvaluationTime(evaluationTime);
		}

		return propertyExpression;
	}

}
