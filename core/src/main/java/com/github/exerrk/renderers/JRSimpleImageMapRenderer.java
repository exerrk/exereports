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
package com.github.exerrk.renderers;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.github.exerrk.engine.ImageMapRenderable;
import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRImageRenderer;
import com.github.exerrk.engine.JRPrintImageAreaHyperlink;

/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 * @deprecated Replaced by {@link SimpleDataRenderer}.
 */
public class JRSimpleImageMapRenderer extends JRImageRenderer implements ImageMapRenderable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private List<JRPrintImageAreaHyperlink> areaHyperlinks;
	
	/**
	 * 
	 */
	public JRSimpleImageMapRenderer(byte[] imageData, List<JRPrintImageAreaHyperlink> areaHyperlinks) 
	{
		super(imageData);
		this.areaHyperlinks = areaHyperlinks;
	}

	/**
	 * @deprecated To be removed.
	 */
	@Override
	public List<JRPrintImageAreaHyperlink> renderWithHyperlinks(Graphics2D grx, Rectangle2D rectangle) throws JRException
	{
		render(grx, rectangle);
		
		return areaHyperlinks;
	}
	
	@Override
	public List<JRPrintImageAreaHyperlink> getImageAreaHyperlinks(Rectangle2D renderingArea) throws JRException 
	{
		return areaHyperlinks;
	}

	@Override
	public boolean hasImageAreaHyperlinks()
	{
		return areaHyperlinks != null && !areaHyperlinks.isEmpty();
	}
}
