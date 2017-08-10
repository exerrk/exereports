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
package com.github.exerrk.engine;


/**
 * Represents a query used for generation of report data.
 * <p/>
 * Based on the query language, query executer implementations are used to retrieve the data and
 * create a {@link com.github.exerrk.engine.JRDataSource JRDataSource} to be used by the filling process.
 * <p/>
 * When using the default SQL connection data source,
 * an SQL query must also be provided for JasperReports to automatically retrieve the data.
 * For SQL queries, a very important aspect is that column names in the result set obtained from the query must match the field names
 * defined in the report template.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @see com.github.exerrk.engine.query.QueryExecuterFactory
 */
public interface JRQuery extends JRCloneable
{
	
	
	/**
	 *
	 */
	public JRQueryChunk[] getChunks();

	/**
	 * Returns the query string.
	 */
	public String getText();
	

	/**
	 * Returns the query language.
	 * <p/>
	 * Based on the query language, a corresponding
	 * {@link com.github.exerrk.engine.query.QueryExecuterFactory QueryExecuterFactory} is used to create
	 * a {@link com.github.exerrk.engine.query.JRQueryExecuter JRQueryExecuter} instance.  The query executer
	 * is responsible for executing the query and creating a {@link JRDataSource JRDataSource} from the result.
	 * 
	 * @return the query language
	 */
	public String getLanguage();
}