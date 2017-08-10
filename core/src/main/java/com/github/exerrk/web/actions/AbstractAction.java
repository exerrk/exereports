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
package com.github.exerrk.web.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.github.exerrk.engine.JRElement;
import com.github.exerrk.engine.JRElementGroup;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.ReportContext;
import com.github.exerrk.engine.design.JRDesignComponentElement;
import com.github.exerrk.engine.design.JRDesignElement;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.engine.util.JRElementsVisitor;
import com.github.exerrk.engine.util.MessageProvider;
import com.github.exerrk.engine.util.MessageUtil;
import com.github.exerrk.engine.util.UniformElementVisitor;
import com.github.exerrk.repo.JasperDesignCache;
import com.github.exerrk.repo.JasperDesignReportResource;
import com.github.exerrk.web.commands.CommandStack;
import com.github.exerrk.web.commands.CommandTarget;


/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="actionName")
public abstract class AbstractAction implements Action {
	
	public static final String PARAM_COMMAND_STACK = "com.github.exerrk.command.stack";
	public static final String ERR_CONCAT_STRING = "<#_#>";
	
	private JasperReportsContext jasperReportsContext;
	private ReportContext reportContext;
	private CommandStack commandStack;
	protected ActionErrors errors;
	
	public AbstractAction(){
	}

	public String getMessagesBundle() {
		return "com.github.exerrk.web.actions.messages";
	}
	
	public void init(JasperReportsContext jasperReportsContext, ReportContext reportContext)//, String reportUri) 
	{
		this.jasperReportsContext = jasperReportsContext;
		this.reportContext = reportContext;
		commandStack = (CommandStack)reportContext.getParameterValue(PARAM_COMMAND_STACK);
		
		if (commandStack == null) {
			commandStack = new CommandStack();
			reportContext.setParameterValue(PARAM_COMMAND_STACK, commandStack);
		}
		errors = new ActionErrors(MessageUtil.getInstance(jasperReportsContext).getMessageProvider(getMessagesBundle()),
				(Locale) reportContext.getParameterValue(JRParameter.REPORT_LOCALE));
	}
	
	public JasperReportsContext getJasperReportsContext() {
		return jasperReportsContext;
	}
	
	public ReportContext getReportContext() {
		return reportContext;
	}
	
	@Override
	public void run() throws ActionException {
		performAction();
	}
	
	public CommandStack getCommandStack() {
		return commandStack;
	}

	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	
	public abstract void performAction() throws ActionException;


	public static class ActionErrors {
		
		private MessageProvider messageProvider;
		private Locale locale;
		private List<String> errorMessages;


		public ActionErrors (MessageProvider messageProvider, Locale locale) {
			this.messageProvider = messageProvider;
			this.locale = locale;
			this.errorMessages = new ArrayList<String>();
		}
		
		public void add(String messageKey, Object... args) {
			errorMessages.add(messageProvider.getMessage(messageKey, args, locale));
		}

		public void add(String messageKey) {
			add(messageKey, (Object[])null);
		}

		public void addAndThrow(String messageKey, Object... args) throws ActionException {
			errorMessages.add(messageProvider.getMessage(messageKey, args, locale));
			throwAll();
		}
		
		public void addAndThrow(String messageKey) throws ActionException {
			addAndThrow(messageKey, (Object[])null);
		}
		
		public boolean isEmpty() {
			return errorMessages.size() == 0;
		}
		
		public void throwAll() throws ActionException {
			if (!errorMessages.isEmpty()) {
				StringBuilder errBuilder = new StringBuilder();
				for (int i = 0, ln = errorMessages.size(); i < ln; i++) {
					String errMsg = errorMessages.get(i);
					errBuilder.append(errMsg);
					if (i < ln -1) {
						errBuilder.append(ERR_CONCAT_STRING);
					}
				}
				throw new ActionException(errBuilder.toString());
			}	
		}
	}


	/**
	 * 
	 */
	public CommandTarget getCommandTarget(UUID uuid)
	{
		return getCommandTarget(uuid, JRDesignComponentElement.class);
	}

	public CommandTarget getCommandTarget(final UUID uuid, final Class<? extends JRDesignElement> elementType)
	{
		JasperDesignCache cache = JasperDesignCache.getInstance(getJasperReportsContext(), getReportContext());

		Map<String, JasperDesignReportResource> cachedResources = cache.getCachedResources();
		Set<String> uris = cachedResources.keySet();
		for (String uri : uris)
		{
			final CommandTarget target = new CommandTarget();
			target.setUri(uri);
			
			JasperDesign jasperDesign = cache.getJasperDesign(uri);
			JRElementsVisitor.visitReport(jasperDesign, new UniformElementVisitor()
			{
				private boolean found = false;
				
				@Override
				public void visitElementGroup(JRElementGroup elementGroup)
				{
					//NOP
				}
				
				@Override
				protected void visitElement(JRElement element)
				{
					if (!found && elementType.isInstance(element) && uuid.equals(element.getUUID()))
					{
						target.setIdentifiable(element);
						
						// there's no way to stop the graph visit
						found = true;
					}
				}
			});
			
			if (target.getIdentifiable() != null)
			{
				return target;
			}
		}
		return null;
	}


	@Override
	public boolean requiresRefill() {
		return true;
	}
}
