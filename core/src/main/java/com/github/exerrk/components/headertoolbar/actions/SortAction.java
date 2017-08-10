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

import com.github.exerrk.components.sort.actions.SortCommand;
import com.github.exerrk.components.sort.actions.SortData;
import com.github.exerrk.engine.design.JRDesignDataset;
import com.github.exerrk.engine.design.JRDesignDatasetRun;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.repo.JasperDesignCache;
import com.github.exerrk.web.actions.ActionException;
import com.github.exerrk.web.commands.CommandException;
import com.github.exerrk.web.commands.ResetInCacheCommand;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class SortAction extends AbstractVerifiableTableAction {
	
	public SortAction() {
	}

	public SortData getSortData() {
		return (SortData)columnData;
	}

	public void setSortData(SortData sortData) {
		columnData = sortData;
	}

	@Override
	public void performAction() throws ActionException {
		JRDesignDatasetRun datasetRun = (JRDesignDatasetRun)table.getDatasetRun();
		
		String datasetName = datasetRun.getDatasetName();
		
		JasperDesignCache cache = JasperDesignCache.getInstance(getJasperReportsContext(), getReportContext());

		JasperDesign jasperDesign = cache.getJasperDesign(targetUri);
		JRDesignDataset dataset = (JRDesignDataset)jasperDesign.getDatasetMap().get(datasetName);
		
		// execute command
		try {
			getCommandStack().execute(
				new ResetInCacheCommand(
					new SortCommand(getJasperReportsContext(), dataset, getSortData()),
					getJasperReportsContext(),
					getReportContext(), 
					targetUri
					)
				);
		} catch (CommandException e) {
			throw new ActionException(e);
		}
	}

	@Override
	public void verify() throws ActionException {
	}
}
