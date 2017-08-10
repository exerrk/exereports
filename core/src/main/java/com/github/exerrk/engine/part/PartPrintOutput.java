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
package com.github.exerrk.engine.part;

import java.util.Collection;

import com.github.exerrk.engine.BookmarkHelper;
import com.github.exerrk.engine.JROrigin;
import com.github.exerrk.engine.JRPrintPage;
import com.github.exerrk.engine.JRStyle;
import com.github.exerrk.engine.PrintPart;
import com.github.exerrk.engine.fill.DelayedFillActions;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface PartPrintOutput
{

	void startPart(PrintPart part, FillingPrintPart fillingPart);

	void addPage(JRPrintPage page, DelayedFillActions delayedActions);

	JRPrintPage getPage(int pageIndex);
	
	void pageUpdated(int partPageIndex);
	
	void append(FillPartPrintOutput output);
	
	BookmarkHelper getBookmarkHelper();

	void addStyles(Collection<JRStyle> styles);

	void addOrigins(Collection<JROrigin> origins);
	
}
