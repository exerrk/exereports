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
package com.github.exerrk.web.actions;

import java.io.File;
import java.util.Map;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.JasperReport;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.engine.util.JRSaver;
import com.github.exerrk.repo.JasperDesignCache;
import com.github.exerrk.repo.JasperDesignReportResource;



/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class SaveAction extends AbstractAction {

	public SaveAction() {
	}

	public String getName() {
		return "save_action";
	}

	@Override
	public void performAction() 
	{
//		JasperDesign jasperDesign = getJasperDesign();
		JasperDesignCache cache = JasperDesignCache.getInstance(getJasperReportsContext(), getReportContext());
		Map<String, JasperDesignReportResource> cachedResources = cache.getCachedResources();
		for (String uri : cachedResources.keySet())
		{
			JasperDesignReportResource resource = cachedResources.get(uri);
			JasperDesign jasperDesign = resource.getJasperDesign();
			if (jasperDesign != null)
			{
				JasperReport jasperReport = resource.getReport();
				String appRealPath = null;//FIXMECONTEXT WebFileRepositoryService.getApplicationRealPath();
				try
				{
					JRSaver.saveObject(jasperReport, new File(new File(new File(appRealPath), "WEB-INF/repository"), uri));//FIXMEJIVE harcoded
				}
				catch (JRException e)
				{
					throw new JRRuntimeException(e);
				}
			}
		}
	}

}
