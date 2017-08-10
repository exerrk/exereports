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
package com.github.exerrk.view;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.util.JRClassLoader;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SaveContributorUtils
{

	private static final Log log = LogFactory.getLog(SaveContributorUtils.class);
	
	private static final String[] DEFAULT_CONTRIBUTORS = {
		"com.github.exerrk.view.save.JRPrintSaveContributor",
		"com.github.exerrk.view.save.JRPdfSaveContributor",
		"com.github.exerrk.view.save.JRRtfSaveContributor",
		"com.github.exerrk.view.save.JROdtSaveContributor",
		"com.github.exerrk.view.save.JRDocxSaveContributor",
		"com.github.exerrk.view.save.JRHtmlSaveContributor",
		"com.github.exerrk.view.save.JRSingleSheetXlsSaveContributor",
		"com.github.exerrk.view.save.JRMultipleSheetsXlsSaveContributor",
		"com.github.exerrk.view.save.JRCsvSaveContributor",
		"com.github.exerrk.view.save.JRXmlSaveContributor",
		"com.github.exerrk.view.save.JREmbeddedImagesXmlSaveContributor"
	};
	
	private static final Class<?>[] CONSTRUCTOR_SIGNATURE = {
		JasperReportsContext.class, Locale.class, ResourceBundle.class};
	
	public static List<JRSaveContributor> createBuiltinContributors(JasperReportsContext context,
			Locale locale, ResourceBundle resourceBundle)
	{
		ArrayList<JRSaveContributor> contributors = new ArrayList<JRSaveContributor>(DEFAULT_CONTRIBUTORS.length);
		for (String contributorClassName : DEFAULT_CONTRIBUTORS)
		{
			try
			{
				Class<?> saveContribClass = JRClassLoader.loadClassForName(contributorClassName);
				Constructor<?> constructor = saveContribClass.getConstructor(CONSTRUCTOR_SIGNATURE);
				JRSaveContributor saveContrib = (JRSaveContributor) constructor.newInstance(context, locale, resourceBundle);
				contributors.add(saveContrib);
			}
			catch (Exception e)
			{
				// shouldn't happen, but log anyway
				log.warn("Error creating save contributor of type " + contributorClassName, e);
			}
		}
		return contributors;
	}
	
}
