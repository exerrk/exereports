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
package com.github.exerrk.data;

import com.github.exerrk.data.bean.BeanDataAdapter;
import com.github.exerrk.data.bean.BeanDataAdapterService;
import com.github.exerrk.data.csv.CsvDataAdapter;
import com.github.exerrk.data.csv.CsvDataAdapterService;
import com.github.exerrk.data.ds.DataSourceDataAdapter;
import com.github.exerrk.data.ds.DataSourceDataAdapterService;
import com.github.exerrk.data.ejbql.EjbqlDataAdapter;
import com.github.exerrk.data.ejbql.EjbqlDataAdapterService;
import com.github.exerrk.data.empty.EmptyDataAdapter;
import com.github.exerrk.data.empty.EmptyDataAdapterService;
import com.github.exerrk.data.excel.ExcelDataAdapter;
import com.github.exerrk.data.excel.ExcelDataAdapterService;
import com.github.exerrk.data.hibernate.HibernateDataAdapter;
import com.github.exerrk.data.hibernate.HibernateDataAdapterService;
import com.github.exerrk.data.hibernate.spring.SpringHibernateDataAdapter;
import com.github.exerrk.data.hibernate.spring.SpringHibernateDataAdapterService;
import com.github.exerrk.data.jdbc.JdbcDataAdapter;
import com.github.exerrk.data.jdbc.JdbcDataAdapterImpl;
import com.github.exerrk.data.jdbc.JdbcDataAdapterService;
import com.github.exerrk.data.jndi.JndiDataAdapter;
import com.github.exerrk.data.jndi.JndiDataAdapterService;
import com.github.exerrk.data.json.JsonDataAdapter;
import com.github.exerrk.data.json.JsonDataAdapterService;
import com.github.exerrk.data.mondrian.MondrianDataAdapter;
import com.github.exerrk.data.mondrian.MondrianDataAdapterService;
import com.github.exerrk.data.provider.DataSourceProviderDataAdapter;
import com.github.exerrk.data.provider.DataSourceProviderDataAdapterService;
import com.github.exerrk.data.qe.QueryExecuterDataAdapter;
import com.github.exerrk.data.qe.QueryExecuterDataAdapterService;
import com.github.exerrk.data.xls.XlsDataAdapter;
import com.github.exerrk.data.xls.XlsDataAdapterService;
import com.github.exerrk.data.xlsx.XlsxDataAdapter;
import com.github.exerrk.data.xlsx.XlsxDataAdapterService;
import com.github.exerrk.data.xml.XmlDataAdapter;
import com.github.exerrk.data.xml.XmlDataAdapterService;
import com.github.exerrk.data.xmla.XmlaDataAdapter;
import com.github.exerrk.data.xmla.XmlaDataAdapterService;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.ParameterContributorContext;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class DefaultDataAdapterServiceFactory implements DataAdapterContributorFactory, DataAdapterServiceFactory
{

	/**
	 *
	 */
	private static final DefaultDataAdapterServiceFactory INSTANCE = new DefaultDataAdapterServiceFactory();
	
	/**
	 *
	 */
	public static DefaultDataAdapterServiceFactory getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public DataAdapterService getDataAdapterService(ParameterContributorContext context, DataAdapter dataAdapter)
	{
		JasperReportsContext jasperReportsContext = context.getJasperReportsContext();
		DataAdapterService dataAdapterService = null;
		
		if (dataAdapter instanceof BeanDataAdapter)
		{
			dataAdapterService = new BeanDataAdapterService(context, (BeanDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof CsvDataAdapter)
		{
			dataAdapterService = new CsvDataAdapterService(context, (CsvDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof DataSourceDataAdapter)
		{
			dataAdapterService = new DataSourceDataAdapterService(context, (DataSourceDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof EmptyDataAdapter)
		{
			dataAdapterService = new EmptyDataAdapterService(context, (EmptyDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof JndiDataAdapter)
		{
			dataAdapterService = new JndiDataAdapterService(context, (JndiDataAdapter)dataAdapter);//FIXME maybe want some cache here
		}
		else if (dataAdapter instanceof DataSourceProviderDataAdapter)
		{
			dataAdapterService = new DataSourceProviderDataAdapterService(context, (DataSourceProviderDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof QueryExecuterDataAdapter)
		{
			dataAdapterService = new QueryExecuterDataAdapterService(context, (QueryExecuterDataAdapter)dataAdapter);
		}
		
		// these following three adapters must be kept in order of inheritance hierarchy
		else if (dataAdapter instanceof ExcelDataAdapter)
		{
			dataAdapterService = new ExcelDataAdapterService(context, (ExcelDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof XlsxDataAdapter)
		{
			dataAdapterService = new XlsxDataAdapterService(context, (XlsxDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof XlsDataAdapter)
		{
			dataAdapterService = new XlsDataAdapterService(context, (XlsDataAdapter)dataAdapter);
		}
		// end excel

		else if (dataAdapter instanceof XmlDataAdapter)
		{
			dataAdapterService = new XmlDataAdapterService(context, (XmlDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof JsonDataAdapter)
		{
			dataAdapterService = new JsonDataAdapterService(context, (JsonDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof HibernateDataAdapter)
		{
			dataAdapterService = new HibernateDataAdapterService(context, (HibernateDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof SpringHibernateDataAdapter)
		{
			dataAdapterService = new SpringHibernateDataAdapterService(context, (SpringHibernateDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof EjbqlDataAdapter)
		{
			dataAdapterService = new EjbqlDataAdapterService(context, (EjbqlDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof MondrianDataAdapter)
		{
			dataAdapterService = new MondrianDataAdapterService(context, (MondrianDataAdapter)dataAdapter);
		}
		else if (dataAdapter instanceof XmlaDataAdapter)
		{
			dataAdapterService = new XmlaDataAdapterService(context, (XmlaDataAdapter)dataAdapter);
		}
		else if (dataAdapter.getClass().getName().equals(JdbcDataAdapterImpl.class.getName()))
		{
			dataAdapterService = new JdbcDataAdapterService(context, (JdbcDataAdapter)dataAdapter);
		}
		
		return dataAdapterService;
	}
	
	/**
	 * @deprecated Replaced by {@link #getDataAdapterService(ParameterContributorContext, DataAdapter)}.
	 */
	@Override
	public DataAdapterService getDataAdapterService(JasperReportsContext jasperReportsContext, DataAdapter dataAdapter)
	{
		return getDataAdapterService(new ParameterContributorContext(jasperReportsContext, null, null), dataAdapter);
	}
  
}
