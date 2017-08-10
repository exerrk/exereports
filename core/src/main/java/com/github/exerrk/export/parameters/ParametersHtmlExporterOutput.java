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
package com.github.exerrk.export.parameters;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.export.FileHtmlResourceHandler;
import com.github.exerrk.engine.export.HtmlResourceHandler;
import com.github.exerrk.engine.export.MapHtmlResourceHandler;
import com.github.exerrk.export.HtmlExporterOutput;
import com.github.exerrk.web.util.WebHtmlResourceHandler;


/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class ParametersHtmlExporterOutput extends ParametersWriterExporterOutput implements HtmlExporterOutput
{
	/**
	 * 
	 */
	private HtmlResourceHandler imageHandler;
	private HtmlResourceHandler resourceHandler;
	
	/**
	 * 
	 */
	public ParametersHtmlExporterOutput(
		JasperReportsContext jasperReportsContext,
		Map<com.github.exerrk.engine.JRExporterParameter, Object> parameters,
		JasperPrint jasperPrint
		)
	{
		super(
			jasperReportsContext,
			parameters,
			jasperPrint
			);
		
		Boolean isOutputImagesToDirParameter = (Boolean)parameters.get(com.github.exerrk.engine.export.JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR);
		String imagesUri = (String)parameters.get(com.github.exerrk.engine.export.JRHtmlExporterParameter.IMAGES_URI);

		if (imageHandler == null)
		{
			if (isOutputImagesToDirParameter == null || isOutputImagesToDirParameter.booleanValue())
			{
				File imagesDir = (File)parameters.get(com.github.exerrk.engine.export.JRHtmlExporterParameter.IMAGES_DIR);
				if (imagesDir == null)
				{
					String imagesDirName = (String)parameters.get(com.github.exerrk.engine.export.JRHtmlExporterParameter.IMAGES_DIR_NAME);
					if (imagesDirName != null)
					{
						imagesDir = new File(imagesDirName);
					}
				}
				
				if (imagesDir != null)
				{
					imageHandler = new FileHtmlResourceHandler(imagesDir, imagesUri == null ? imagesDir.getName() + "/{0}" : imagesUri + "{0}");
				}
			}

			if (imageHandler == null && imagesUri != null)
			{
				imageHandler = new WebHtmlResourceHandler(imagesUri + "{0}");
			}
		}

		StringBuffer sb = (StringBuffer)parameters.get(com.github.exerrk.engine.JRExporterParameter.OUTPUT_STRING_BUFFER);
		if (sb == null)
		{
			Writer writer = (Writer)parameters.get(com.github.exerrk.engine.JRExporterParameter.OUTPUT_WRITER);
			if (writer == null)
			{
				OutputStream os = (OutputStream)parameters.get(com.github.exerrk.engine.JRExporterParameter.OUTPUT_STREAM);
				if (os == null)
				{
					File destFile = (File)parameters.get(com.github.exerrk.engine.JRExporterParameter.OUTPUT_FILE);
					if (destFile == null)
					{
						String fileName = (String)parameters.get(com.github.exerrk.engine.JRExporterParameter.OUTPUT_FILE_NAME);
						if (fileName != null)
						{
							destFile = new File(fileName);
						}
						else
						{
							throw 
								new JRRuntimeException(
									EXCEPTION_MESSAGE_KEY_NO_OUTPUT_SPECIFIED,
									(Object[])null);
						}
					}

					if (
						imageHandler == null
						&& (isOutputImagesToDirParameter == null || isOutputImagesToDirParameter.booleanValue())
						)
					{
						File imagesDir = new File(destFile.getParent(), destFile.getName() + "_files");
						imageHandler = new FileHtmlResourceHandler(imagesDir, imagesUri == null ? imagesDir.getName() + "/{0}" : imagesUri + "{0}");
					}

					if (resourceHandler == null)
					{
						File resourcesDir = new File(destFile.getParent(), destFile.getName() + "_files");
						resourceHandler = new FileHtmlResourceHandler(resourcesDir, resourcesDir.getName() + "/{0}");
					}
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		Map<String,byte[]> imageNameToImageDataMap = 
			(Map<String,byte[]>) parameters.get(com.github.exerrk.engine.export.JRHtmlExporterParameter.IMAGES_MAP);
		if (imageNameToImageDataMap != null)
		{
			imageHandler = new MapHtmlResourceHandler(imageHandler, imageNameToImageDataMap);
		}
	}
	
	@Override
	public HtmlResourceHandler getImageHandler() 
	{
		return imageHandler;
	}

	/**
	 * @deprecated Replaced by {@link #getResourceHandler()}.
	 */
	@Override
	public HtmlResourceHandler getFontHandler() 
	{
		return getResourceHandler();
	}

	@Override
	public HtmlResourceHandler getResourceHandler() 
	{
		return resourceHandler;
	}
}
