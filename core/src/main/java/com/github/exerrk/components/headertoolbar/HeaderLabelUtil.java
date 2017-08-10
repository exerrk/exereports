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
package com.github.exerrk.components.headertoolbar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import com.github.exerrk.components.table.fill.BuiltinExpressionEvaluator;
import com.github.exerrk.engine.JRElement;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRStaticText;
import com.github.exerrk.engine.JRTextField;
import com.github.exerrk.engine.JRVisitor;
import com.github.exerrk.engine.design.JRDesignFrame;
import com.github.exerrk.engine.fill.DatasetExpressionEvaluator;
import com.github.exerrk.engine.fill.JRExpressionEvalException;
import com.github.exerrk.engine.fill.JRFillField;
import com.github.exerrk.engine.fill.JRFillParameter;
import com.github.exerrk.engine.fill.JRFillVariable;
import com.github.exerrk.engine.type.WhenResourceMissingTypeEnum;

/**
 * 
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class HeaderLabelUtil
{
	/**
	 * 
	 */
	public static HeaderLabelBuiltinExpression alterHeaderLabel(JRDesignFrame frame, String suffix)
	{
		HeaderLabelBuiltinExpression evaluator = null;
		
		JRElement[] elements = frame.getElements();
		JRElement element = (elements == null || elements.length == 0) ? null : elements[0];

		if (element instanceof JRStaticText)
		{
			JRElement elementProxy = getProxy((JRStaticText)element, suffix); 
			frame.getChildren().set(0, elementProxy);
		}
		else if (element instanceof JRTextField)
		{
			evaluator = new HeaderLabelBuiltinExpression(((JRTextField)element).getExpression(), suffix); 
		}

		
		return evaluator;
	}


	/**
	 * 
	 */
	private static JRStaticText getProxy(final JRStaticText staticText, final String suffix)
	{
		return 
			(JRStaticText)Proxy.newProxyInstance(
				HeaderLabelUtil.class.getClassLoader(), 
				new Class<?>[]{JRStaticText.class}, 
				new InvocationHandler() 
				{
					@Override
					public Object invoke(
						Object proxy, 
						Method method, 
						Object[] args
						) throws Throwable 
					{
						if ("getText".equals(method.getName()))
						{
							return 
								staticText.getText() 
								+ suffix;
						}
						if ("visit".equals(method.getName()))
						{
							((JRVisitor)args[0]).visitStaticText((JRStaticText)proxy);
							return null;
						}
						return method.invoke(staticText, args);
					}
				}
			);
	}


	/**
	 * 
	 *
	private static JRTextField getProxy(final JRTextField textField, final SortOrderEnum sortOrder)
	{
		return 
			(JRTextField)Proxy.newProxyInstance(
				HeaderLabelUtil.class.getClassLoader(), 
				new Class<?>[]{JRTextField.class}, 
				new InvocationHandler() 
				{
					public Object invoke(
						Object proxy, 
						Method method, 
						Object[] args
						) throws Throwable 
					{
						if ("getExpression".equals(method.getName()))
						{
							JRDesignExpression expression = new JRDesignExpression();
							return 
								Color.blue;
						}
						if ("visit".equals(method.getName()))
						{
							((JRVisitor)args[0]).visitTextField((JRTextField)proxy);
							return null;
						}
						return method.invoke(textField, args);
					}
				}
			);
	}


	/**
	 * 
	 */
	public static class HeaderLabelBuiltinExpression implements BuiltinExpressionEvaluator
	{
		private final JRExpression expression;
		private String suffix;

		public HeaderLabelBuiltinExpression(JRExpression expression, String suffix)
		{
			this.expression = expression;
			this.suffix = suffix;
		}
		
		@Override
		public void init(Map<String, JRFillParameter> parametersMap,
				Map<String, JRFillField> fieldsMap, 
				Map<String, JRFillVariable> variablesMap,
				WhenResourceMissingTypeEnum resourceMissingType) throws JRException
		{
			// NOP
		}

		@Override
		public Object evaluate(DatasetExpressionEvaluator evaluator) throws JRExpressionEvalException
		{
			return evaluator.evaluate(expression) + suffix;
		}

		@Override
		public Object evaluateOld(DatasetExpressionEvaluator evaluator) throws JRExpressionEvalException
		{
			return evaluator.evaluateOld(expression) + suffix;
		}

		@Override
		public Object evaluateEstimated(DatasetExpressionEvaluator evaluator) throws JRExpressionEvalException
		{
			return evaluator.evaluateEstimated(expression) + suffix;
		}
		
		public JRExpression getExpression()
		{
			return expression;
		}

	}

}
