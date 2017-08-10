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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.ReportContext;
import com.github.exerrk.engine.util.JRStringUtil;
import com.github.exerrk.web.JRInteractiveException;
import com.github.exerrk.web.WebReportContext;
import com.github.exerrk.web.actions.AbstractAction;
import com.github.exerrk.web.actions.Action;
import com.github.exerrk.web.actions.MultiAction;
import com.github.exerrk.web.util.JacksonUtil;
import com.github.exerrk.web.util.WebUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * @author Narcis Marcu(nmarcu@users.sourceforge.net)
 */
public class ReportActionServlet extends AbstractServlet
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private static final Log log = LogFactory.getLog(ReportActionServlet.class);
		
	private static final String REQUEST_PARAMETER_ACTION = "jr_action";

	

	@Override
	public void service(
		HttpServletRequest request,
		HttpServletResponse response
		) throws IOException, ServletException
	{
		response.setContentType(JSON_CONTENT_TYPE);
		setNoExpire(response);

		PrintWriter out = response.getWriter();
		String contextId = request.getParameter(WebReportContext.REQUEST_PARAMETER_REPORT_CONTEXT_ID);

		if (contextId != null && request.getHeader("accept").indexOf(JSON_ACCEPT_HEADER) >= 0 && request.getParameterMap().containsKey(REQUEST_PARAMETER_ACTION)) {
			WebReportContext webReportContext = WebReportContext.getInstance(request, false);

			if (webReportContext != null) {
				try {
					runAction(request, webReportContext);

					// FIXMEJIVE: actions shoud return their own ActionResult that would contribute with JSON object to the output
					JsonNode actionResult = (JsonNode) webReportContext.getParameterValue("com.github.exerrk.web.actions.result.json");
					if (actionResult != null) {
						out.println("{\"contextid\": " + webReportContext.getId() + ", \"actionResult\": " + actionResult + "}");
						webReportContext.setParameterValue("com.github.exerrk.web.actions.result.json", null);
					} else {
						out.println("{\"contextid\": " + webReportContext.getId() + "}");
					}

				} catch (Exception e) {
					log.error("Error on page status update", e);
					response.setStatus(404);
					out.println("{\"msg\": \"JasperReports encountered an error on context creation!\",");
					out.println("\"devmsg\": \"" + JRStringUtil.escapeJavaStringLiteral(e.getMessage()) + "\"}");
				}
			} else {
				response.setStatus(404);
				out.println("{\"msg\": \"Resource with id '" + contextId + "' not found!\"}");
				return;
			}

		} else {
			response.setStatus(400);
			out.println("{\"msg\": \"Wrong parameters!\"}");
		}
	}


	/**
	 * @throws JRInteractiveException 
	 *
	 */
	public void runAction(
		HttpServletRequest request,
		WebReportContext webReportContext
		) throws JRException, JRInteractiveException
	{
		JasperPrintAccessor jasperPrintAccessor = 
			(JasperPrintAccessor) webReportContext.getParameterValue(
				WebReportContext.REPORT_CONTEXT_PARAMETER_JASPER_PRINT_ACCESSOR
			);
		Action action = getAction(webReportContext, WebUtil.decodeUrl(request.getParameter(REQUEST_PARAMETER_ACTION)));
		Controller controller = new Controller(getJasperReportsContext());
		controller.runReport(webReportContext, action);
	}


	/**
	 *
	 */
	private Action getAction(ReportContext webReportContext, String jsonData)
	{
		Action result = null;
		List<AbstractAction> actions = JacksonUtil.getInstance(getJasperReportsContext()).loadAsList(jsonData, AbstractAction.class);
		if (actions != null)
		{
			if (actions.size() == 1) {
				result = actions.get(0);
			} else if (actions.size() > 1){
				result = new MultiAction(actions);
			}
			
			((AbstractAction)result).init(getJasperReportsContext(), webReportContext);
		}
		return result;
	}

}
