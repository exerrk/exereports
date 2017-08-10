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
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */
package com.github.exerrk.engine;

import com.github.exerrk.engine.type.OnErrorTypeEnum;
import com.github.exerrk.renderers.ResourceRenderer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface JRPrintImage extends JRPrintGraphicElement, JRPrintAnchor, JRPrintHyperlink, JRAlignment, JRImageAlignment, JRCommonImage
{


	/**
	 * @deprecated Replaced by {@link #getRenderer()}.
	 */
	public Renderable getRenderable();
		
	/**
	 * @deprecated Replaced by {@link #setRenderer(com.github.exerrk.renderers.Renderable)}.
	 */
	public void setRenderable(Renderable renderer);
		
	/**
	 *
	 */
	public com.github.exerrk.renderers.Renderable getRenderer();
		
	/**
	 *
	 */
	public void setRenderer(com.github.exerrk.renderers.Renderable renderer);
		
	/**
	 *
	 */
	public boolean isUsingCache();

	/**
	 *
	 */
	public void setUsingCache(boolean isUsingCache);
	
	/**
	 * @deprecated Replaced by {@link ResourceRenderer}.
	 */
	public boolean isLazy();

	/**
	 * @deprecated Replaced by {@link ResourceRenderer}.
	 */
	public void setLazy(boolean isLazy);

	/**
	 * 
	 */
	public OnErrorTypeEnum getOnErrorTypeValue();

	/**
	 *
	 */
	public void setOnErrorType(OnErrorTypeEnum onErrorType);


}
