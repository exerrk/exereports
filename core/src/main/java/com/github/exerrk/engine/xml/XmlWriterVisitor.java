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
 * Eugene D - eugenedruy@users.sourceforge.net 
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */
package com.github.exerrk.engine.xml;

import java.io.IOException;

import com.github.exerrk.crosstabs.JRCrosstab;
import com.github.exerrk.engine.JRBreak;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRElementGroup;
import com.github.exerrk.engine.JREllipse;
import com.github.exerrk.engine.JRFrame;
import com.github.exerrk.engine.JRGenericElement;
import com.github.exerrk.engine.JRImage;
import com.github.exerrk.engine.JRLine;
import com.github.exerrk.engine.JRRectangle;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.JRStaticText;
import com.github.exerrk.engine.JRSubreport;
import com.github.exerrk.engine.JRTextField;
import com.github.exerrk.engine.JRVisitor;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class XmlWriterVisitor implements JRVisitor
{
	
	private JRXmlWriter xmlWriter;
	
	/**
	 *
	 */
	public XmlWriterVisitor(JRXmlWriter xmlWriter)
	{
		this.xmlWriter = xmlWriter;
	}

	@Override
	public void visitBreak(JRBreak breakElement)
	{
		try
		{
			xmlWriter.writeBreak(breakElement);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitChart(JRChart chart)
	{
		try
		{
			xmlWriter.writeChartTag(chart);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitCrosstab(JRCrosstab crosstab)
	{
		try
		{
			xmlWriter.writeCrosstab(crosstab);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitElementGroup(JRElementGroup elementGroup)
	{
		try
		{
			xmlWriter.writeElementGroup(elementGroup);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitEllipse(JREllipse ellipse)
	{
		try
		{
			xmlWriter.writeEllipse(ellipse);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitFrame(JRFrame frame)
	{
		try
		{
			xmlWriter.writeFrame(frame);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitImage(JRImage image)
	{
		try
		{
			xmlWriter.writeImage(image);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitLine(JRLine line)
	{
		try
		{
			xmlWriter.writeLine(line);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitRectangle(JRRectangle rectangle)
	{
		try
		{
			xmlWriter.writeRectangle(rectangle);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitStaticText(JRStaticText staticText)
	{
		try
		{
			xmlWriter.writeStaticText(staticText);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitSubreport(JRSubreport subreport)
	{
		try
		{
			xmlWriter.writeSubreport(subreport);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitTextField(JRTextField textField)
	{
		try
		{
			xmlWriter.writeTextField(textField);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}
	
	@Override
	public void visitComponentElement(JRComponentElement componentElement)
	{
		try
		{
			xmlWriter.writeComponentElement(componentElement);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	@Override
	public void visitGenericElement(JRGenericElement element)
	{
		try
		{
			xmlWriter.writeGenericElement(element);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
	}

}
