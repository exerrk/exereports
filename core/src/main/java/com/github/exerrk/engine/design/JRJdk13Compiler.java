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
package com.github.exerrk.engine.design;

//import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.util.JRClassLoader;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRJdk13Compiler extends JRAbstractMultiClassCompiler
{


	/**
	 *  
	 */
	static final Log log = LogFactory.getLog(JRJdk13Compiler.class);

	/**
	 *
	 */
	private static final int MODERN_COMPILER_SUCCESS = 0;


	/**
	 *
	 */
	public JRJdk13Compiler(JasperReportsContext jasperReportsContext)
	{
		super(jasperReportsContext);
	}
	
	@Override
	public String compileClasses(File[] sourceFiles, String classpath) throws JRException
	{
		String[] source = new String[sourceFiles.length + 2];
		for (int i = 0; i < sourceFiles.length; i++)
		{
			source[i] = sourceFiles[i].getPath();
		}
		source[sourceFiles.length] = "-classpath";
		source[sourceFiles.length + 1] = classpath;
		
		String errors = null;
		
		

		try 
		{
			Class<?> clazz = JRClassLoader.loadClassForRealName("com.sun.tools.javac.Main");
			Object compiler = clazz.newInstance();
			
			try 
			{
				Method compileMethod = clazz.getMethod("compile", new Class[] {String[].class, PrintWriter.class});
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int result = ((Integer)compileMethod.invoke(compiler, new Object[] {source, new PrintWriter(baos)})).intValue();
				
				if (result != MODERN_COMPILER_SUCCESS)
				{
					errors = baos.toString();
				}
				else 
				{
					if (log.isInfoEnabled() && baos.size() > 0)
					{
						log.info(baos.toString());
					}
				}
			} 
			catch (NoSuchMethodException ex)
			{
				Method compileMethod = clazz.getMethod("compile", new Class[] {String[].class});

				int result = ((Integer)compileMethod.invoke(compiler, new Object[] {source})).intValue();
				if (result != MODERN_COMPILER_SUCCESS)
				{
					errors = "See error messages above.";
				}
			}
		}
		catch (Exception e)
		{
			StringBuilder files = new StringBuilder();
			for (int i = 0; i < sourceFiles.length; ++i)
			{
				files.append(sourceFiles[i].getPath());
				files.append(' ');
			}
			throw 
				new JRException(
					EXCEPTION_MESSAGE_KEY_JAVA_SOURCE_COMPILE_ERROR,
					new Object[]{files}, 
					e);
		}
		
		return errors;
	}
}
