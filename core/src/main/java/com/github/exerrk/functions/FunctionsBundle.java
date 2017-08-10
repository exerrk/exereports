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
package com.github.exerrk.functions;

import java.util.ArrayList;
import java.util.List;

import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.util.JRClassLoader;


/**
 * A functions bundle contains a list of classes which have static methods that can be used as functions in report expressions.
 * 
 * The registry may return one or more instances of this classes.
 * 
 * @author Giulio Toffoli (gt78@users.sourceforge.net)
 */
public class FunctionsBundle 
{

	private List<Class<?>> functionClasses = new ArrayList<Class<?>>();

	
	/**
	 *
	 */
	public FunctionsBundle(List<String> functionClassNames) 
	{
		for (String className : functionClassNames)
		{
			addFunctionClass(className);
		}
	}

	
	/**
	 * 
	 */
	public FunctionsBundle() 
	{
		super();
	}


	/**
	 *
	 */
	public void addFunctionClass(Class<?> clazz) 
	{
		functionClasses.add(clazz);
	}


	/**
	 *
	 */
	public void addFunctionClass(String className) 
	{
		try 
		{
			Class<?> clazz = JRClassLoader.loadClassForName(className);
			addFunctionClass(clazz);
		}
		catch (ClassNotFoundException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	
	/**
	 *
	 */
	public List<Class<?>> getFunctionClasses() 
	{
		return functionClasses;
	}


	/**
	 *
	 */
	public void setFunctionClasses(List<Class<?>> functionClasses) 
	{
		this.functionClasses = functionClasses;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionsBundle && functionClasses!=null) {
			return functionClasses.equals(
					((FunctionsBundle) obj).getFunctionClasses());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if (functionClasses != null) {
			return functionClasses.hashCode();
		}
		return super.hashCode();
	}	
	
}
