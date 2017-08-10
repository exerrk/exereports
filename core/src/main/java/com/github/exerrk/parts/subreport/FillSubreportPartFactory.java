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
package com.github.exerrk.parts.subreport;

import com.github.exerrk.components.list.BaseFillList;
import com.github.exerrk.engine.fill.JRFillCloneFactory;
import com.github.exerrk.engine.fill.JRFillObjectFactory;
import com.github.exerrk.engine.part.PartComponent;
import com.github.exerrk.engine.part.PartComponentFillFactory;
import com.github.exerrk.engine.part.PartFillComponent;

/**
 * Factory of {@link BaseFillList list fill component} instances.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class FillSubreportPartFactory implements PartComponentFillFactory
{

	@Override
	public PartFillComponent cloneFillComponent(PartFillComponent component,
			JRFillCloneFactory factory)
	{
		//TODO implement
		throw new UnsupportedOperationException();
	}

	@Override
	public PartFillComponent toFillComponent(PartComponent component,
			JRFillObjectFactory factory)
	{
		SubreportPartComponent subreportPart = (SubreportPartComponent) component;
		SubreportFillPart fillSubreport = new SubreportFillPart(subreportPart, factory);
		return fillSubreport;
	}

}
