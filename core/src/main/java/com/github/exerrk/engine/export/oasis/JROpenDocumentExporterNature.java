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

/*
 * Contributors:
 * Greg Hilton 
 */

package com.github.exerrk.engine.export.oasis;

import java.util.Map;

import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRPrintFrame;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.export.AbstractExporterNature;
import com.github.exerrk.engine.export.CutsInfo;
import com.github.exerrk.engine.export.ExporterFilter;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class JROpenDocumentExporterNature extends AbstractExporterNature
{
	
	/**
	 * 
	 */
	public JROpenDocumentExporterNature(JasperReportsContext jasperReportsContext, ExporterFilter filter)
	{
		super(jasperReportsContext, filter);
	}
	
	@Override
	public boolean isToExport(JRPrintElement element)
	{
		return (filter == null || filter.isToExport(element));
	}
	
	@Override
	public boolean isDeep(JRPrintFrame frame)
	{
		return false;
	}

	@Override
	public boolean isSpanCells()
	{
		return true;
	}
	
	@Override
	public boolean isIgnoreLastRow()
	{
		return true;
	}

	@Override
	public boolean isHorizontallyMergeEmptyCells()
	{
		return false;
	}

	/**
	 * Specifies whether empty page margins should be ignored
	 */
	@Override
	public boolean isIgnorePageMargins()
	{
		return false;
	}
	
	@Override
	public boolean isBreakBeforeRow(JRPrintElement element)
	{
		return false;
	}
	
	@Override
	public boolean isBreakAfterRow(JRPrintElement element)
	{
		return false;
	}
	
	@Override
	public void setXProperties(CutsInfo xCuts, JRPrintElement element, int row1, int col1, int row2, int col2)
	{
		// nothing to do here
	}
	
	@Override
	public void setXProperties(Map<String,Object> xCutsProperties, JRPrintElement element)
	{
		// nothing to do here
	}
	
	@Override
	public void setYProperties(CutsInfo yCuts, JRPrintElement element, int row1, int col1, int row2, int col2)
	{
		// nothing to do here
	}
	
	@Override
	public void setYProperties(Map<String,Object> yCutsProperties, JRPrintElement element)
	{
		// nothing to do here
	}

}
