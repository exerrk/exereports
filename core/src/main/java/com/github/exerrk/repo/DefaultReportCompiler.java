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
package com.github.exerrk.repo;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JasperCompileManager;
import com.github.exerrk.engine.JasperReport;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.design.JasperDesign;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class DefaultReportCompiler implements ReportCompiler
{
	private JasperCompileManager compileManager;

	public DefaultReportCompiler(JasperReportsContext context)
	{
		compileManager = JasperCompileManager.getInstance(context);
	}

	@Override
	public JasperReport compile(JasperDesign design) throws JRException
	{
		return compileManager.compile(design);
	}

}
