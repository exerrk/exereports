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
package com.github.exerrk.engine.util;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.Renderable;
import com.github.exerrk.engine.type.ImageTypeEnum;
import com.github.exerrk.engine.type.RenderableTypeEnum;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @deprecated To be removed.
 */
public class WrappingRenderable implements Renderable
{
	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	private com.github.exerrk.engine.JRRenderable renderable;
	
	
	/**
	 *
	 */
	public WrappingRenderable(com.github.exerrk.engine.JRRenderable renderable)
	{
		this.renderable = renderable;
	}


	@Override
	public String getId() {
		return renderable.getId();
	}


	@Override
	public byte getType() {
		return renderable.getType();
	}


	@Override
	public byte getImageType() {
		return renderable.getImageType();
	}


	@Override
	public Dimension2D getDimension() throws JRException {
		return renderable.getDimension();
	}


	@Override
	public byte[] getImageData() throws JRException {
		return renderable.getImageData();
	}


	@Override
	public void render(Graphics2D grx, Rectangle2D rectangle)
			throws JRException {
		renderable.render(grx, rectangle);
	}


	@Override
	public RenderableTypeEnum getTypeValue() {
		return RenderableTypeEnum.getByValue(renderable.getType());
	}


	@Override
	public ImageTypeEnum getImageTypeValue() {
		return ImageTypeEnum.getByValue(renderable.getImageType());
	}


	@Override
	public Dimension2D getDimension(JasperReportsContext jasperReportsContext)
			throws JRException {
		return renderable.getDimension();
	}


	@Override
	public byte[] getImageData(JasperReportsContext jasperReportsContext)
			throws JRException {
		return renderable.getImageData();
	}


	@Override
	public void render(JasperReportsContext jasperReportsContext,
			Graphics2D grx, Rectangle2D rectangle) throws JRException {
		renderable.render(grx, rectangle);
	}

}
