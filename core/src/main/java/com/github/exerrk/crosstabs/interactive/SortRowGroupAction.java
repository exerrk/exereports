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
package com.github.exerrk.crosstabs.interactive;

import java.util.UUID;

import com.github.exerrk.crosstabs.design.JRDesignCrosstab;
import com.github.exerrk.web.actions.AbstractAction;
import com.github.exerrk.web.actions.ActionException;
import com.github.exerrk.web.commands.CommandException;
import com.github.exerrk.web.commands.CommandTarget;
import com.github.exerrk.web.commands.ResetInCacheCommand;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class SortRowGroupAction extends AbstractAction
{
	private static final Log log = LogFactory.getLog(SortRowGroupAction.class);

	private SortRowGroupData sortData;
	
	@Override
	public void performAction() throws ActionException
	{
		CommandTarget target = getCommandTarget(UUID.fromString(sortData.getCrosstabId()), JRDesignCrosstab.class);
		if (target != null)
		{
			JRDesignCrosstab crosstab = (JRDesignCrosstab) target.getIdentifiable();
			try
			{
				SortRowGroupCommand sortCommand = new SortRowGroupCommand(crosstab, getSortData());
				ResetInCacheCommand command = new ResetInCacheCommand(sortCommand,
					getJasperReportsContext(), getReportContext(), target.getUri());
				getCommandStack().execute(command);
			}
			catch (CommandException e)
			{
				if (log.isDebugEnabled())
				{
					log.debug("error sorting crosstab", e);
				}
				
				throw new ActionException(e);
			}
		}
	}

	public SortRowGroupData getSortData()
	{
		return sortData;
	}

	public void setSortData(SortRowGroupData sortData)
	{
		this.sortData = sortData;
	}

}
