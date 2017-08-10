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
package com.github.exerrk.web.servlets;

import com.github.exerrk.data.cache.ColumnDataCacheHandler;
import com.github.exerrk.data.cache.DataCacheHandler;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.JasperFillManager;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReport;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.ReportContext;
import com.github.exerrk.engine.fill.AsynchronousFillHandle;
import com.github.exerrk.repo.RepositoryUtil;
import com.github.exerrk.web.JRInteractiveException;
import com.github.exerrk.web.WebReportContext;
import com.github.exerrk.web.actions.AbstractAction;
import com.github.exerrk.web.actions.Action;
import com.github.exerrk.web.commands.CommandStack;
import com.github.exerrk.web.util.WebUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class Controller
{
	private static final Log log = LogFactory.getLog(Controller.class);
	public static final String EXCEPTION_MESSAGE_KEY_REPORT_NOT_FOUND = "web.servlets.controller.report.not.found";

	/**
	 *
	 */
	private JasperReportsContext jasperReportsContext;

	
	/**
	 *
	 */
	public Controller(JasperReportsContext jasperReportsContext)
	{
		this.jasperReportsContext = jasperReportsContext;
	}
	
	
	/**
	 * @throws JRInteractiveException 
	 *
	 */
	public void runReport(
		ReportContext reportContext,
		Action action
		) throws JRException
	{
		String reportUri = (String) reportContext.getParameterValue(WebUtil.REQUEST_PARAMETER_REPORT_URI);
		int initialStackSize = 0;
		CommandStack commandStack = (CommandStack) reportContext.getParameterValue(AbstractAction.PARAM_COMMAND_STACK);
		if (commandStack != null) {
			initialStackSize = commandStack.getExecutionStackSize();
		}

		setDataCache(reportContext);
		
		JasperReport jasperReport = null;
		
		
		if (reportUri != null && reportUri.trim().length() > 0)
		{
			reportUri = reportUri.trim();

			if (action != null) 
			{
				action.run();
				if (log.isDebugEnabled()) {
					log.debug("action requires refill: " + action.requiresRefill());
				}
				if (!action.requiresRefill()) { // stop here because this action does not modify jasperDesign
					return;
				}
			}

			jasperReport = RepositoryUtil.getInstance(jasperReportsContext).getReport(reportContext, reportUri);
		}


		if (jasperReport == null)
		{
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_REPORT_NOT_FOUND,
					new Object[]{reportUri});
		}
		
		Boolean async = (Boolean) reportContext.getParameterValue(WebUtil.REQUEST_PARAMETER_ASYNC_REPORT);
		if (async == null)
		{
			async = Boolean.FALSE;
		}
		reportContext.setParameterValue(WebUtil.REQUEST_PARAMETER_ASYNC_REPORT, async);
		
		try {
			runReport(reportContext, jasperReport, async.booleanValue());
		} catch (JRException e) {
			undoAction(reportContext, initialStackSize);
			throw e;
		} catch (JRRuntimeException e) {
			undoAction(reportContext, initialStackSize);
			throw e;
		}
	}
	
	private void undoAction(ReportContext reportContext, int initialStackSize) {
		CommandStack commandStack = (CommandStack) reportContext.getParameterValue(AbstractAction.PARAM_COMMAND_STACK);
		if (commandStack != null) {
			for (int i = 0; i < (commandStack.getExecutionStackSize() - initialStackSize); i++) {
				commandStack.undo();
			}
		}
	}


	protected void setDataCache(ReportContext reportContext)
	{
		DataCacheHandler dataCacheHandler = (DataCacheHandler) reportContext.getParameterValue(
				DataCacheHandler.PARAMETER_DATA_CACHE_HANDLER);
		if (dataCacheHandler != null && !dataCacheHandler.isSnapshotPopulated())
		{
			// if we have an old cache handler which is not yet final, create a new one
			// TODO lucianc also check for final but disabled caches
			
			if (log.isDebugEnabled())
			{
				log.debug("Data cache handler not final " + dataCacheHandler);
			}
			
			dataCacheHandler = null;
		}
		
		if (dataCacheHandler == null)
		{
			//initialize the data cache handler
			dataCacheHandler = new ColumnDataCacheHandler();
			
			if (log.isDebugEnabled())
			{
				log.debug("Created data cache handler " + dataCacheHandler);
			}
			
			reportContext.setParameterValue(
					DataCacheHandler.PARAMETER_DATA_CACHE_HANDLER, dataCacheHandler);
		}
	}


	/**
	 *
	 */
	protected void runReport(
		ReportContext reportContext,
		JasperReport jasperReport, 
		boolean async
		) throws JRException
	{
		JasperPrintAccessor accessor;
		if (async)
		{
			AsynchronousFillHandle fillHandle = 
				AsynchronousFillHandle.createHandle(
					jasperReportsContext,
					jasperReport, 
					reportContext.getParameterValues()
					);
			AsyncJasperPrintAccessor asyncAccessor = new AsyncJasperPrintAccessor(fillHandle);
			
			fillHandle.startFill();
			
			accessor = asyncAccessor;
		}
		else
		{
			JasperPrint jasperPrint = 
					JasperFillManager.getInstance(jasperReportsContext).fill(
						jasperReport, 
						reportContext.getParameterValues()
						);
			accessor = new SimpleJasperPrintAccessor(jasperPrint);
		}
		
		reportContext.setParameterValue(WebReportContext.REPORT_CONTEXT_PARAMETER_JASPER_PRINT_ACCESSOR, accessor);
	}
}
