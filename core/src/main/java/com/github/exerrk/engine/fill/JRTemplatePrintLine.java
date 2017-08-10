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
package com.github.exerrk.engine.fill;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRPrintLine;
import com.github.exerrk.engine.PrintElementVisitor;
import com.github.exerrk.engine.type.LineDirectionEnum;


/**
 * Implementation of {@link com.github.exerrk.engine.JRPrintLine} that uses
 * a {@link com.github.exerrk.engine.fill.JRTemplateLine} instance to
 * store common attributes. 
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRTemplatePrintLine extends JRTemplatePrintGraphicElement implements JRPrintLine
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public JRTemplatePrintLine()
	{
		
	}
	
	/**
	 * Creates a print line element.
	 * 
	 * @param line the template line that the element will use
	 * @deprecated provide a source Id via {@link #JRTemplatePrintLine(JRTemplateLine, int)}
	 */
	public JRTemplatePrintLine(JRTemplateLine line)
	{
		super(line);
	}

	/**
	 * Creates a print line element.
	 * 
	 * @param line the template line that the element will use
	 * @param sourceElementId the Id of the source element
	 * @deprecated replaced by {@link #JRTemplatePrintLine(JRTemplateLine, PrintElementOriginator)}
	 */
	public JRTemplatePrintLine(JRTemplateLine line, int sourceElementId)
	{
		super(line, sourceElementId);
	}

	/**
	 * Creates a print line element.
	 * 
	 * @param line the template line that the element will use
	 * @param originator
	 */
	public JRTemplatePrintLine(JRTemplateLine line, PrintElementOriginator originator)
	{
		super(line, originator);
	}
	
	@Override
	public LineDirectionEnum getDirectionValue()
	{
		return ((JRTemplateLine)this.template).getDirectionValue();
	}
		
	@Override
	public void setDirection(LineDirectionEnum direction)
	{
	}

	@Override
	public <T> void accept(PrintElementVisitor<T> visitor, T arg)
	{
		visitor.visit(this, arg);
	}

}
