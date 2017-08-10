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

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.properties.PropertyConstants;


/**
 * Factory of {@link com.github.exerrk.engine.fill.JRSubreportRunner JRSubreportRunner} instances.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface JRSubreportRunnerFactory
{
	/**
	 * Property specifying the {@link com.github.exerrk.engine.fill.JRSubreportRunnerFactory JRSubreportRunnerFactory}
	 * implementation to use for creating subreport runners.
	 */
	@Property(
			category = PropertyConstants.CATEGORY_FILL,
			defaultValue = "com.github.exerrk.engine.fill.JRThreadSubreportRunnerFactory",
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_1_2_2
			)
	public static final String SUBREPORT_RUNNER_FACTORY = JRPropertiesUtil.PROPERTY_PREFIX + "subreport.runner.factory";
	
	/**
	 * Creates a new {@link com.github.exerrk.engine.fill.JRSubreportRunner JRSubreportRunner} instance.
	 * 
	 * @param fillSubreport the subreport element of the master report
	 * @param subreportFiller the subreport filler created to fill the subreport
	 * @return a new {@link com.github.exerrk.engine.fill.JRSubreportRunner JRSubreportRunner} instance
	 */
	JRSubreportRunner createSubreportRunner(JRFillSubreport fillSubreport, JRBaseFiller subreportFiller);
}