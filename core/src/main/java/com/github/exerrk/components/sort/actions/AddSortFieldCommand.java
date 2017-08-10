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
package com.github.exerrk.components.sort.actions;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.design.JRDesignDataset;
import com.github.exerrk.engine.design.JRDesignSortField;
import com.github.exerrk.web.commands.Command;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class AddSortFieldCommand implements Command 
{
	
	private JRDesignDataset dataset;
	private JRDesignSortField sortField;
	
	/**
	 * 
	 */
	public AddSortFieldCommand(JRDesignDataset dataset, JRDesignSortField sortField) 
	{
		this.dataset = dataset;
		this.sortField = sortField;
	}

	@Override
	public void execute() 
	{
		try
		{
			dataset.addSortField(sortField);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}
	
	@Override
	public void undo() 
	{
		dataset.removeSortField(sortField);
	}

	@Override
	public void redo() 
	{
		execute();
	}

}
