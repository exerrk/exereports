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

import com.github.exerrk.engine.JRCommonRectangle;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRDefaultStyleProvider;
import com.github.exerrk.engine.JROrigin;
import com.github.exerrk.engine.JRRectangle;
import com.github.exerrk.engine.JRSubreport;
import com.github.exerrk.engine.base.JRBasePen;
import com.github.exerrk.engine.type.FillEnum;
import com.github.exerrk.engine.util.ObjectUtils;


/**
 * Rectangle information shared by multiple print rectangle objects.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @see JRTemplatePrintRectangle
 */
public class JRTemplateRectangle extends JRTemplateGraphicElement implements JRCommonRectangle
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	private Integer radius;


	/**
	 *
	 */
	protected JRTemplateRectangle(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JRRectangle rectangle)
	{
		super(origin, defaultStyleProvider);

		setRectangle(rectangle);
	}


	/**
	 *
	 */
	protected JRTemplateRectangle(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JRSubreport subreport)
	{
		super(origin, defaultStyleProvider);
		
		setSubreport(subreport);
	}
	
	
	/**
	 * Creates a template rectangle.
	 * 
	 * @param origin the origin of the elements that will use this template
	 * @param defaultStyleProvider the default style provider to use for
	 * this template
	 */
	public JRTemplateRectangle(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider)
	{
		super(origin, defaultStyleProvider);

		this.linePen = new JRBasePen(this);
	}


	/**
	 *
	 */
	protected void setRectangle(JRRectangle rectangle)
	{
		super.setGraphicElement(rectangle);

		setRadius(rectangle.getRadius());
	}


	/**
	 *
	 */
	protected void setSubreport(JRSubreport subreport)
	{
		super.setElement(subreport);

		// don't want to inherit mode because of different defaults for rectangles and subreports
		setMode(subreport.getModeValue());
		
		linePen = new JRBasePen(this);
		
		getLinePen().setLineWidth(0f);
		setFill(FillEnum.SOLID);
	}


	@Override
	public int getRadius()
	{
		return getStyleResolver().getRadius(this);
	}

	@Override
	public Integer getOwnRadius()
	{
		return radius;
	}

	@Override
	public void setRadius(int radius)
	{
		this.radius = Integer.valueOf(radius);
	}

	@Override
	public void setRadius(Integer radius)
	{
		this.radius = radius;
	}


	@Override
	public int getHashCode()
	{
		ObjectUtils.HashCode hash = ObjectUtils.hash();
		addGraphicHash(hash);
		hash.add(radius);
		return hash.getHashCode();
	}


	@Override
	public boolean isIdentical(Object object)
	{
		if (this == object)
		{
			return true;
		}
		
		if (!(object instanceof JRTemplateRectangle))
		{
			return false;
		}
		
		JRTemplateRectangle template = (JRTemplateRectangle) object;
		return graphicIdentical(template) 
				&& ObjectUtils.equals(radius, template.radius);
	}


}
