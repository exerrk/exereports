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

import java.util.List;

import com.github.exerrk.components.table.BaseColumn;
import com.github.exerrk.components.table.StandardColumn;
import com.github.exerrk.components.table.StandardTable;
import com.github.exerrk.components.table.util.TableUtil;
import com.github.exerrk.web.commands.Command;
import com.github.exerrk.web.commands.CommandException;
import com.github.exerrk.web.commands.CommandStack;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class HideUnhideColumnsCommand implements Command {
	
	private StandardTable table;
	private HideUnhideColumnData columnData;
	private CommandStack individualResizeCommandStack;
	
	public HideUnhideColumnsCommand(StandardTable table, HideUnhideColumnData columnData) {
		this.table = table;
		this.columnData = columnData;
		individualResizeCommandStack = new CommandStack();
	}


	@Override
	public void execute() throws CommandException {
		List<BaseColumn> tableColumns = TableUtil.getAllColumns(table);
		int[] columnIndexes = columnData.getColumnIndexes();
		
		if (columnIndexes != null) {
			for(int colIndex: columnIndexes){
				individualResizeCommandStack.execute(new HideUnhideColumnCommand((StandardColumn)tableColumns.get(colIndex), columnData.getHide()));
			}
		}
	}


	@Override
	public void undo() {
		individualResizeCommandStack.undoAll();
	}


	@Override
	public void redo() {
		individualResizeCommandStack.redoAll();
	}

}
