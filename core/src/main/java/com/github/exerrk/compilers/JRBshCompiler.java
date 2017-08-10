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
package com.github.exerrk.compilers;

import java.io.File;
import java.io.Serializable;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRReport;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.design.JRAbstractCompiler;
import com.github.exerrk.engine.design.JRCompilationSourceCode;
import com.github.exerrk.engine.design.JRCompilationUnit;
import com.github.exerrk.engine.design.JRDefaultCompilationSourceCode;
import com.github.exerrk.engine.design.JRSourceCompileTask;
import com.github.exerrk.engine.fill.JRCalculator;
import com.github.exerrk.engine.fill.JREvaluator;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @deprecated To be removed.
 */
public class JRBshCompiler extends JRAbstractCompiler
{
	/**
	 * A constant used to specify that the language used by expressions is BeanShell script.
	 */
	public static final String LANGUAGE_BSH = "bsh";


	/**
	 * 
	 */
	public JRBshCompiler(JasperReportsContext jasperReportsContext)
	{
		super(jasperReportsContext, false);
	}


	@Override
	protected JREvaluator loadEvaluator(Serializable compileData, String unitName) throws JRException
	{
		return new JRBshEvaluator((String) compileData);
	}


	@Override
	protected void checkLanguage(String language) throws JRException
	{
		if (
			!LANGUAGE_BSH.equals(language)
			&& !JRReport.LANGUAGE_JAVA.equals(language)
			)
		{
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_LANGUAGE_NOT_SUPPORTED,
					new Object[]{language, LANGUAGE_BSH, JRReport.LANGUAGE_JAVA});
		}
	}


	@Override
	protected JRCompilationSourceCode generateSourceCode(JRSourceCompileTask sourceTask) throws JRException
	{
		return new JRDefaultCompilationSourceCode(JRBshGenerator.generateScript(sourceTask), null);
	}


	@Override
	protected String compileUnits(JRCompilationUnit[] units, String classpath, File tempDirFile) throws JRException
	{
		verifyScripts(units);

		for (int i = 0; i < units.length; i++)
		{
			String script = units[i].getSourceCode();
			units[i].setCompileData(script);
		}		
		
		return null;
	}


	private void verifyScripts(JRCompilationUnit[] units) throws JRException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		// trick for detecting the Ant class loader
		try
		{
			classLoader.loadClass(JRCalculator.class.getName());
		}
		catch(ClassNotFoundException e)
		{
			classLoader = getClass().getClassLoader();
		}

		ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			Thread.currentThread().setContextClassLoader(classLoader);

			for (int i = 0; i < units.length; i++)
			{
				String script = units[i].getSourceCode();
				
				JRBshEvaluator bshEvaluator = new JRBshEvaluator(script);
				bshEvaluator.verify(units[i].getExpressions());
			}
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(oldContextClassLoader);
		}
	}


	@Override
	protected String getSourceFileName(String unitName)
	{
		return unitName + ".bsh";
	}


}
