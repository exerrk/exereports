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
package com.github.exerrk.engine.xml;

import com.github.exerrk.engine.design.JRDesignSubreport;
import com.github.exerrk.engine.design.JasperDesign;

import org.xml.sax.Attributes;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRSubreportFactory extends JRBaseFactory
{

	@Override
	public Object createObject(Attributes atts)
	{
		JasperDesign jasperDesign = (JasperDesign)digester.peek(digester.getCount() - 2);

		JRDesignSubreport subreport = new JRDesignSubreport(jasperDesign);

		String isUsingCache = atts.getValue(JRXmlConstants.ATTRIBUTE_isUsingCache);
		if (isUsingCache != null && isUsingCache.length() > 0)
		{
			subreport.setUsingCache(Boolean.valueOf(isUsingCache));
		}
		
		String runToBottomAttr = atts.getValue(JRXmlConstants.ATTRIBUTE_runToBottom);
		if (runToBottomAttr != null)
		{
			subreport.setRunToBottom(Boolean.valueOf(runToBottomAttr));
		}

		return subreport;
	}
	

}
