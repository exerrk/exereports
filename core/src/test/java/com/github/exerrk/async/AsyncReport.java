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
package com.github.exerrk.async;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.github.exerrk.Report;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.fill.AsynchronousFillHandle;
import com.github.exerrk.engine.fill.FillListener;
import com.github.exerrk.web.servlets.AsyncJasperPrintAccessor;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class AsyncReport extends Report
{

	public AsyncReport(String jrxml, String jrpxml)
	{
		super(jrxml, jrpxml);
	}
	
	public void runReport(Map<String, Object> params, FillListener fillListener)
	{
		try
		{
			Map<String, Object> reportParams = reportParams(params);
			AsynchronousFillHandle asyncHandle = AsynchronousFillHandle.createHandle(
					jasperReportsContext, report, reportParams);
			
			if (fillListener != null)
			{
				asyncHandle.addFillListener(fillListener);
			}
			
			AsyncJasperPrintAccessor accessor = new AsyncJasperPrintAccessor(asyncHandle);
			asyncHandle.startFill();
			JasperPrint print = accessor.getFinalJasperPrint();
			reportComplete(reportParams, print);
		}
		catch (JRException | NoSuchAlgorithmException | IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}
