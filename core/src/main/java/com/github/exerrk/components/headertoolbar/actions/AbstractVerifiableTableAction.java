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
package com.github.exerrk.components.headertoolbar.actions;

import java.util.UUID;

import com.github.exerrk.components.table.StandardTable;
import com.github.exerrk.engine.JRIdentifiable;
import com.github.exerrk.engine.design.JRDesignComponentElement;
import com.github.exerrk.engine.util.DefaultFormatFactory;
import com.github.exerrk.engine.util.FormatFactory;
import com.github.exerrk.web.actions.AbstractAction;
import com.github.exerrk.web.actions.ActionException;
import com.github.exerrk.web.commands.CommandTarget;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractVerifiableTableAction extends AbstractAction 
{
	protected BaseColumnData columnData;
	
	protected StandardTable table;
	protected String targetUri;
	
	protected static FormatFactory formatFactory = new DefaultFormatFactory();
	
	public AbstractVerifiableTableAction()
	{
	}
	
	@Override
	public String getMessagesBundle() {
		return "com.github.exerrk.components.headertoolbar.actions.messages";
	}

	public StandardTable getTable(String uuid) 
	{
		CommandTarget target = getCommandTarget(UUID.fromString(uuid));
		if (target != null)
		{
			JRIdentifiable identifiable = target.getIdentifiable();
			JRDesignComponentElement componentElement = identifiable instanceof JRDesignComponentElement ? (JRDesignComponentElement)identifiable : null;
			return componentElement == null ? null : (StandardTable)componentElement.getComponent();
		}
		return null;
	}

	public void prepare() throws ActionException 
	{
		if (columnData == null) { 
			errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.validate.no.data");
		}
		if(columnData.getTableUuid() == null || columnData.getTableUuid().trim().length() == 0) {
			errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.validate.no.table");
		}
		CommandTarget target = getCommandTarget(UUID.fromString(columnData.getTableUuid()));
		if (target != null)
		{
			JRIdentifiable identifiable = target.getIdentifiable();
			JRDesignComponentElement componentElement = identifiable instanceof JRDesignComponentElement ? (JRDesignComponentElement)identifiable : null;
			
			if (componentElement == null) {
				errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.validate.no.table.match", columnData.getTableUuid());
			}
			
			table = (StandardTable)componentElement.getComponent();
			targetUri = target.getUri();
		}
	}
	
	@Override
	public void run() throws ActionException 
	{
		prepare();
		verify();
		errors.throwAll();
		performAction();
	}
	
	public abstract void verify() throws ActionException;

}
