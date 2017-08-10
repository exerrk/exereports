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
package com.github.exerrk.components.headertoolbar.actions;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.github.exerrk.components.sort.FilterTypeDateOperatorsEnum;
import com.github.exerrk.components.sort.FilterTypesEnum;
import com.github.exerrk.components.sort.actions.FilterCommand;
import com.github.exerrk.components.sort.actions.FilterData;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.design.JRDesignDataset;
import com.github.exerrk.engine.design.JRDesignDatasetRun;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.repo.JasperDesignCache;
import com.github.exerrk.web.actions.ActionException;
import com.github.exerrk.web.commands.CommandException;
import com.github.exerrk.web.commands.ResetInCacheCommand;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class FilterAction extends AbstractVerifiableTableAction {
	
	public FilterAction() {
	}

	public FilterData getFilterData() {
		return (FilterData) columnData;
	}

	public void setFilterData(FilterData filterData) {
		columnData = filterData;
	}

	@Override
	public void performAction() throws ActionException {
		JRDesignDatasetRun datasetRun = (JRDesignDatasetRun)table.getDatasetRun();

		String datasetName = datasetRun.getDatasetName();
		
		JasperDesignCache cache = JasperDesignCache.getInstance(getJasperReportsContext(), getReportContext());
		
		JasperDesign jasperDesign = cache.getJasperDesign(targetUri);
		JRDesignDataset dataset = (JRDesignDataset)jasperDesign.getDatasetMap().get(datasetName);
		
		// execute command
		try {
			getCommandStack().execute(
				new ResetInCacheCommand(
					new FilterCommand(getJasperReportsContext(), dataset, getFilterData()),
					getJasperReportsContext(),
					getReportContext(), 
					targetUri
					)
				);
		} catch (CommandException e) {
			throw new ActionException(e);
		}
	}

	@Override
	public void verify() throws ActionException {
		FilterData fd = getFilterData();
		if (fd.isClearFilter()) {
			return;
		}
		
		if (fd.getFilterType() == null || fd.getFilterType().length() == 0) {
			errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.filter.invalid.type");
		}
		
		FilterTypesEnum filterType = FilterTypesEnum.getByName(fd.getFilterType());

		Locale locale = (Locale)getReportContext().getParameterValue(JRParameter.REPORT_LOCALE);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		
		if (FilterTypesEnum.DATE.equals(filterType) || FilterTypesEnum.TIME.equals(filterType)) {
			FilterTypeDateOperatorsEnum dateEnum = FilterTypeDateOperatorsEnum.getByEnumConstantName(fd.getFilterTypeOperator());
			boolean containsBetween = FilterTypeDateOperatorsEnum.IS_BETWEEN.equals(dateEnum) || FilterTypeDateOperatorsEnum.IS_NOT_BETWEEN.equals(dateEnum);

			try {
				DateFormat df = formatFactory.createDateFormat(fd.getFilterPattern(), locale, null);
				df.setLenient(false);
			
				if (containsBetween) {
					if (fd.getFieldValueStart() == null || fd.getFieldValueStart().length() == 0) {
						errors.add("com.github.exerrk.components.headertoolbar.actions.filter.empty.start.date");
					} else {
						try {
							df.parse(fd.getFieldValueStart());
						} catch (ParseException e) {
							errors.add("com.github.exerrk.components.headertoolbar.actions.filter.invalid.start.date", fd.getFieldValueStart());
						}
					}

					if (fd.getFieldValueEnd() != null && fd.getFieldValueEnd().length() > 0) {
						try {
							df.parse(fd.getFieldValueEnd());
						} catch (ParseException e) {
							errors.add("com.github.exerrk.components.headertoolbar.actions.filter.invalid.end.date", fd.getFieldValueEnd());
						}
					} else {
						errors.add("com.github.exerrk.components.headertoolbar.actions.filter.empty.end.date");
					}
					
				} else {
					if (fd.getFieldValueStart() == null || fd.getFieldValueStart().length() == 0) {
						errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.filter.empty.date");
					}
					try {
						df.parse(fd.getFieldValueStart());
					} catch (ParseException e) {
						errors.add("com.github.exerrk.components.headertoolbar.actions.filter.invalid.date", fd.getFieldValueStart());
					}
				}
			} catch (IllegalArgumentException e) {
				errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.filter.invalid.pattern");
			}
					
		} else if (filterType == FilterTypesEnum.NUMERIC) {
			if (fd.getFieldValueStart() == null || fd.getFieldValueStart().trim().length() == 0) {
				errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.filter.empty.number");
			}
			try {
				NumberFormat nf = formatFactory.createNumberFormat(fd.getFilterPattern(), locale);
				nf.parse(fd.getFieldValueStart());
				if (fd.getFieldValueEnd() != null && fd.getFieldValueEnd().length() > 0) {
					try {
						nf.parse(fd.getFieldValueEnd());
					} catch (ParseException e) {
						errors.add("com.github.exerrk.components.headertoolbar.actions.filter.invalid.number", new Object[]{fd.getFieldValueEnd()});
					}
				}
			} catch (ParseException e) {
				errors.add("com.github.exerrk.components.headertoolbar.actions.filter.invalid.number", new Object[]{fd.getFieldValueStart()});
			} catch (IllegalArgumentException e) {
				errors.addAndThrow("com.github.exerrk.components.headertoolbar.actions.filter.invalid.pattern");
			}
		}
	}

}
