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
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRExpressionCollector;
import com.github.exerrk.engine.JRSubreport;
import com.github.exerrk.engine.JRSubreportParameter;
import com.github.exerrk.engine.JRSubreportReturnValue;
import com.github.exerrk.engine.JRVisitor;
import com.github.exerrk.engine.type.ModeEnum;
import com.github.exerrk.engine.type.OverflowType;
import com.github.exerrk.engine.util.JRCloneUtils;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRBaseSubreport extends JRBaseElement implements JRSubreport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_USING_CACHE = "isUsingCache";
	
	public static final String PROPERTY_RUN_TO_BOTTOM = "runToBottom";

	public static final String PROPERTY_OVERFLOW_TYPE = "overflowType";

	/**
	 *
	 */
	protected Boolean isUsingCache;

	private Boolean runToBottom;
	private OverflowType overflowType;

	/**
	 *
	 */
	protected JRExpression parametersMapExpression;
	protected JRSubreportParameter[] parameters;
	protected JRExpression connectionExpression;
	protected JRExpression dataSourceExpression;
	protected JRExpression expression;
	
	/**
	 * Values to be copied from the subreport into the master report.
	 */
	protected JRSubreportReturnValue[] returnValues;


	@Override
	public ModeEnum getModeValue()
	{
		return getStyleResolver().getMode(this, ModeEnum.TRANSPARENT);
	}


	/**
	 *
	 */
	protected JRBaseSubreport(JRSubreport subreport, JRBaseObjectFactory factory)
	{
		super(subreport, factory);
		
		isUsingCache = subreport.getUsingCache();
		
		runToBottom = subreport.isRunToBottom();
		overflowType = subreport.getOverflowType();

		parametersMapExpression = factory.getExpression(subreport.getParametersMapExpression());

		/*   */
		JRSubreportParameter[] jrSubreportParameters = subreport.getParameters();
		if (jrSubreportParameters != null && jrSubreportParameters.length > 0)
		{
			parameters = new JRSubreportParameter[jrSubreportParameters.length];
			for(int i = 0; i < parameters.length; i++)
			{
				parameters[i] = factory.getSubreportParameter(jrSubreportParameters[i]);
			}
		}

		connectionExpression = factory.getExpression(subreport.getConnectionExpression());
		dataSourceExpression = factory.getExpression(subreport.getDataSourceExpression());
		
		JRSubreportReturnValue[] subrepReturnValues = subreport.getReturnValues();
		if (subrepReturnValues != null && subrepReturnValues.length > 0)
		{
			this.returnValues = new JRSubreportReturnValue[subrepReturnValues.length];
			for (int i = 0; i < subrepReturnValues.length; i++)
			{
				this.returnValues[i] = factory.getSubreportReturnValue(subrepReturnValues[i]);
			}
		}
		
		expression = factory.getExpression(subreport.getExpression());
	}
		

	/**
	 * @deprecated Replaced by {@link #getUsingCache()}.
	 */
	@Override
	public boolean isUsingCache()
	{
		if (isUsingCache == null)
		{
			JRExpression subreportExpression = getExpression();
			if (subreportExpression != null)
			{
				return String.class.getName().equals(subreportExpression.getValueClassName());
			}
			return true;
		}
		return isUsingCache.booleanValue();
	}


	@Override
	public JRExpression getParametersMapExpression()
	{
		return this.parametersMapExpression;
	}


	@Override
	public JRSubreportParameter[] getParameters()
	{
		return this.parameters;
	}


	@Override
	public JRExpression getConnectionExpression()
	{
		return this.connectionExpression;
	}


	@Override
	public JRExpression getDataSourceExpression()
	{
		return this.dataSourceExpression;
	}


	@Override
	public JRExpression getExpression()
	{
		return this.expression;
	}
	
	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	@Override
	public void visit(JRVisitor visitor)
	{
		visitor.visitSubreport(this);
	}

	
	/**
	 * Returns the list of values to be copied from the subreport into the master.
	 * 
	 * @return the list of values to be copied from the subreport into the master.
	 */
	@Override
	public JRSubreportReturnValue[] getReturnValues()
	{
		return this.returnValues;
	}


	/**
	 * @deprecated Replaced by {@link #getUsingCache()}.
	 */
	@Override
	public Boolean isOwnUsingCache()
	{
		return isUsingCache;
	}


	@Override
	public Boolean getUsingCache()
	{
		return isUsingCache;
	}


	@Override
	public void setUsingCache(Boolean isUsingCache)
	{
		Object old = this.isUsingCache;
		this.isUsingCache = isUsingCache;
		getEventSupport().firePropertyChange(PROPERTY_USING_CACHE, old, this.isUsingCache);
	}

	
	@Override
	public Boolean isRunToBottom()
	{
		return runToBottom;
	}


	@Override
	public void setRunToBottom(Boolean runToBottom)
	{
		Object old = this.runToBottom;
		this.runToBottom = runToBottom;
		getEventSupport().firePropertyChange(PROPERTY_RUN_TO_BOTTOM, old, this.runToBottom);
	}

	@Override
	public OverflowType getOverflowType()
	{
		return overflowType;
	}

	@Override
	public void setOverflowType(OverflowType overflowType)
	{
		Object old = this.overflowType;
		this.overflowType = overflowType;
		getEventSupport().firePropertyChange(PROPERTY_OVERFLOW_TYPE, old, this.overflowType);
	}

	@Override
	public Object clone() 
	{
		JRBaseSubreport clone = (JRBaseSubreport)super.clone();
		clone.parameters = JRCloneUtils.cloneArray(parameters);
		clone.returnValues = JRCloneUtils.cloneArray(returnValues);
		clone.parametersMapExpression = JRCloneUtils.nullSafeClone(parametersMapExpression);
		clone.connectionExpression = JRCloneUtils.nullSafeClone(connectionExpression);
		clone.dataSourceExpression = JRCloneUtils.nullSafeClone(dataSourceExpression);
		clone.expression = JRCloneUtils.nullSafeClone(expression);
		return clone;
	}
}
