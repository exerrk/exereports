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
package com.github.exerrk.components.list;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.component.Component;
import com.github.exerrk.engine.component.ComponentFillFactory;
import com.github.exerrk.engine.component.FillComponent;
import com.github.exerrk.engine.fill.JRFillCloneFactory;
import com.github.exerrk.engine.fill.JRFillObjectFactory;
import com.github.exerrk.engine.type.PrintOrderEnum;

/**
 * Factory of {@link BaseFillList list fill component} instances.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class FillListFactory implements ComponentFillFactory
{

	@Override
	public FillComponent cloneFillComponent(FillComponent component,
			JRFillCloneFactory factory)
	{
		return (FillComponent) ((BaseFillList) component).createClone(factory);
	}

	@Override
	public FillComponent toFillComponent(Component component,
			JRFillObjectFactory factory)
	{
		try
		{
			ListComponent list = (ListComponent) component;
			FillComponent fillList;
			PrintOrderEnum printOrder = list.getPrintOrderValue();
			if (printOrder == null 
					|| printOrder == PrintOrderEnum.VERTICAL)
			{
				fillList = new VerticalFillList(list, factory);
			}
			else
			{
				fillList = new HorizontalFillList(list, factory);
			}
			return fillList;
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

}
