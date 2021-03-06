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
import com.github.exerrk.engine.JRDefaultStyleProvider;
import com.github.exerrk.engine.JREllipse;
import com.github.exerrk.engine.JROrigin;
import com.github.exerrk.engine.base.JRBasePen;
import com.github.exerrk.engine.util.ObjectUtils;


/**
 * Ellipse information shared by multiple print ellipse objects.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @see JRTemplatePrintEllipse
 */
public class JRTemplateEllipse extends JRTemplateGraphicElement
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;


	/**
	 *
	 */
	protected JRTemplateEllipse(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider, JREllipse ellipse)
	{
		super(origin, defaultStyleProvider);

		setEllipse(ellipse);
	}

	/**
	 * Creates an ellipse template.
	 * 
	 * @param origin the origin of the elements that will use this template
	 * @param defaultStyleProvider the default style provider to use for
	 * this template
	 */
	public JRTemplateEllipse(JROrigin origin, JRDefaultStyleProvider defaultStyleProvider)
	{
		super(origin, defaultStyleProvider);

		this.linePen = new JRBasePen(this);
	}


	/**
	 *
	 */
	protected void setEllipse(JREllipse ellipse)
	{
		super.setGraphicElement(ellipse);
	}

	@Override
	public int getHashCode()
	{
		ObjectUtils.HashCode hash = ObjectUtils.hash();
		addGraphicHash(hash);
		return hash.getHashCode();
	}

	@Override
	public boolean isIdentical(Object object)
	{
		if (this == object)
		{
			return true;
		}
		
		if (!(object instanceof JRTemplateEllipse))
		{
			return false;
		}
		
		JRTemplateEllipse template = (JRTemplateEllipse) object;
		return graphicIdentical(template);
	}


}
