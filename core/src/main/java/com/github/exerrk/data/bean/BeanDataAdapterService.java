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
package com.github.exerrk.data.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.github.exerrk.data.AbstractClasspathAwareDataAdapterService;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.ParameterContributorContext;
import com.github.exerrk.engine.data.JRAbstractBeanDataSource;
import com.github.exerrk.engine.data.JRBeanArrayDataSource;
import com.github.exerrk.engine.data.JRBeanCollectionDataSource;
import com.github.exerrk.engine.util.JRClassLoader;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class BeanDataAdapterService extends AbstractClasspathAwareDataAdapterService 
{

	public static final String EXCEPTION_MESSAGE_KEY_INVALID_RETURN_TYPE = "data.bean.invalid.return.type";
	
	/**
	 * 
	 */
	public BeanDataAdapterService(ParameterContributorContext paramContribContext, BeanDataAdapter beanDataAdapter) 
	{
		super(paramContribContext, beanDataAdapter);
	}

	/**
	 * @deprecated Replaced by {@link #BeanDataAdapterService(ParameterContributorContext, BeanDataAdapter)}.
	 */
	public BeanDataAdapterService(JasperReportsContext jasperReportsContext, BeanDataAdapter beanDataAdapter) 
	{
		super(jasperReportsContext, beanDataAdapter);
	}

	public BeanDataAdapter getBeanDataAdapter() {
		return (BeanDataAdapter) getDataAdapter();
	}

	@Override
	public void contributeParameters(Map<String, Object> parameters) throws JRException 
	{
		BeanDataAdapter beanDataAdapter = getBeanDataAdapter();
		if (beanDataAdapter != null)
		{
			JRAbstractBeanDataSource beanDataSource = null;

			ClassLoader oldThreadClassLoader = Thread.currentThread().getContextClassLoader();

			try 
			{
				Thread.currentThread().setContextClassLoader(getClassLoader(oldThreadClassLoader));

				Class<?> clazz = JRClassLoader.loadClassForRealName(beanDataAdapter.getFactoryClass());
				Method method = clazz.getMethod(beanDataAdapter.getMethodName());
				Object res = method.invoke(null);
				if (res instanceof Collection) {
					beanDataSource = new JRBeanCollectionDataSource(
							(Collection<?>) res,
							beanDataAdapter.isUseFieldDescription());
				} else if (res instanceof Object[]) {
					beanDataSource = new JRBeanArrayDataSource((Object[]) res,
							beanDataAdapter.isUseFieldDescription());
				} else {
					throw 
						new JRException(
							EXCEPTION_MESSAGE_KEY_INVALID_RETURN_TYPE,
							new Object[]{clazz.getName()});
				}
			}
			catch (ClassNotFoundException e) {
				throw new JRException(e);
			} catch (IllegalAccessException e) {
				throw new JRException(e);
			} catch (SecurityException e) {
				throw new JRException(e);
			} catch (NoSuchMethodException e) {
				throw new JRException(e);
			} catch (IllegalArgumentException e) {
				throw new JRException(e);
			} catch (InvocationTargetException e) {
				throw new JRException(e);
			}
			finally
			{
				Thread.currentThread().setContextClassLoader(oldThreadClassLoader);
			}

			parameters.put(JRParameter.REPORT_DATA_SOURCE, beanDataSource);
		}
	}
}
