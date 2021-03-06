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
package com.github.exerrk.ant;

import org.apache.tools.ant.BuildException;

import com.github.exerrk.engine.util.ReportUpdater;


/**
 * Utility class that provides a {@link com.github.exerrk.engine.util.ReportUpdater ReportUpdater}
 * implementation
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class UpdaterElement
{


	/**
	 *
	 */
	private ReportUpdater updater;


	/**
	 *
	 */
	public void addText(String className)
	{
		if (className != null)
		{
			try
			{
				Class<?> clazz = JRAntCompileTask.class.getClassLoader().loadClass(className);
				updater = (ReportUpdater)clazz.newInstance();
			}
			catch (ClassNotFoundException e)
			{
				throw new BuildException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new BuildException(e);
			}
			catch (InstantiationException e)
			{
				throw new BuildException(e);
			}
		}
	}
	
	/**
	 * 
	 */
	public ReportUpdater getUpdater()
	{
		return updater;
	}

}
