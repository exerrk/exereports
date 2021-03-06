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
package com.github.exerrk.components.sort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.github.exerrk.components.sort.actions.FilterAction;
import com.github.exerrk.components.sort.actions.FilterCommand;
import com.github.exerrk.components.sort.actions.SortAction;
import com.github.exerrk.components.sort.actions.SortData;
import com.github.exerrk.engine.CompositeDatasetFilter;
import com.github.exerrk.engine.DatasetFilter;
import com.github.exerrk.engine.JRGenericPrintElement;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JRPropertiesMap;
import com.github.exerrk.engine.JRSortField;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.ReportContext;
import com.github.exerrk.engine.design.JRDesignDataset;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.engine.export.GenericElementJsonHandler;
import com.github.exerrk.engine.export.JsonExporterContext;
import com.github.exerrk.engine.type.NamedEnum;
import com.github.exerrk.engine.type.SortFieldTypeEnum;
import com.github.exerrk.engine.util.MessageProvider;
import com.github.exerrk.engine.util.MessageUtil;
import com.github.exerrk.repo.JasperDesignCache;
import com.github.exerrk.web.commands.CommandTarget;
import com.github.exerrk.web.util.JacksonUtil;
import com.github.exerrk.web.util.VelocityUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SortElementJsonHandler implements GenericElementJsonHandler
{
	private static final Log log = LogFactory.getLog(SortElementJsonHandler.class);
	
	private static final String SORT_ELEMENT_HTML_TEMPLATE = "net/sf/jasperreports/components/sort/resources/SortElementJsonTemplate.vm";
	private static final String PARAM_GENERATED_TEMPLATE = "com.github.exerrk.sort";

	private static final String SORT_DATASET = "exporter_first_attempt";

	@Override
	public String getJsonFragment(JsonExporterContext context, JRGenericPrintElement element)
	{
		String htmlFragment = null;
		ReportContext reportContext = context.getExporterRef().getReportContext();
		if (reportContext != null)//FIXMEJIVE
		{
			String sortColumnName = (String) element.getParameterValue(SortElement.PARAMETER_SORT_COLUMN_NAME);
			String sortColumnType = (String) element.getParameterValue(SortElement.PARAMETER_SORT_COLUMN_TYPE);
			
			String sortDatasetName = element.getPropertiesMap().getProperty(SortElement.PROPERTY_DATASET_RUN);
			boolean templateAlreadyLoaded = false;
			
			FilterTypesEnum filterType = FilterTypesEnum.getByName(element.getPropertiesMap().getProperty(SortElement.PROPERTY_FILTER_TYPE));
			if (filterType == null)//FIXMEJIVE
			{
				return null;
			}
			
			String filterPattern = element.getPropertiesMap().getProperty(SortElement.PROPERTY_FILTER_PATTERN);
			Locale locale = (Locale) reportContext.getParameterValue(JRParameter.REPORT_LOCALE);
			JasperReportsContext jrContext = context.getJasperReportsContext();
			
			if (log.isDebugEnabled()) {
				log.debug("report locale: " + locale);
			}
			
			if (locale == null) {
				locale = Locale.getDefault();
			}
			
			VelocityContext contextMap = new VelocityContext();

			Boolean isClearCache = (Boolean)reportContext.getParameterValue(PARAMETER_CLEAR_CONTEXT_CACHE);

			if (reportContext.getParameterValue(PARAM_GENERATED_TEMPLATE) != null && !(isClearCache != null && isClearCache)) {
				templateAlreadyLoaded = true;
			} else {
				reportContext.setParameterValue(PARAM_GENERATED_TEMPLATE, true);
			}
			contextMap.put("templateAlreadyLoaded", templateAlreadyLoaded);

			if (!sortDatasetName.equals(context.getValue(SORT_DATASET))) 
			{
				context.setValue(SORT_DATASET, sortDatasetName);

				// operators
				contextMap.put("numericOperators", JacksonUtil.getInstance(jrContext).getJsonString(getTranslatedOperators(jrContext, FilterTypeNumericOperatorsEnum.class.getName(), FilterTypeNumericOperatorsEnum.values(), locale)));
				contextMap.put("dateOperators", JacksonUtil.getInstance(jrContext).getJsonString(getTranslatedOperators(jrContext, FilterTypeDateOperatorsEnum.class.getName(), FilterTypeDateOperatorsEnum.values(), locale)));
				contextMap.put("timeOperators", JacksonUtil.getInstance(jrContext).getJsonString(getTranslatedOperators(jrContext, FilterTypeDateOperatorsEnum.class.getName(), FilterTypeDateOperatorsEnum.values(), locale)));
				contextMap.put("textOperators", JacksonUtil.getInstance(jrContext).getJsonString(getTranslatedOperators(jrContext, FilterTypeTextOperatorsEnum.class.getName(), FilterTypeTextOperatorsEnum.values(), locale)));
				contextMap.put("booleanOperators", JacksonUtil.getInstance(jrContext).getJsonString(getTranslatedOperators(jrContext, FilterTypeBooleanOperatorsEnum.class.getName(), FilterTypeBooleanOperatorsEnum.values(), locale)));
				
				contextMap.put("exporterFirstAttempt", true);
			}


			String sortField = getCurrentSortField(context.getJasperReportsContext(), reportContext, element.getUUID().toString(), sortDatasetName, sortColumnName, sortColumnType);
			SortData sortData;
			if (sortField == null) 
			{
				sortData = new SortData(element.getUUID().toString(), sortColumnName, sortColumnType, SortElement.SORT_ORDER_ASC);
			}
			else 
			{
				String[] sortActionData = SortElementUtils.extractColumnInfo(sortField);
				boolean isAscending = SortElement.SORT_ORDER_ASC.equals(sortActionData[2]);
				String sortOrder = !isAscending ? SortElement.SORT_ORDER_NONE : SortElement.SORT_ORDER_DESC;
				sortData = new SortData(element.getUUID().toString(), sortColumnName, sortColumnType, sortOrder);
			}

			// existing filters
			String filterValueStart = "";
			String filterValueEnd = "";
			String filterTypeOperator = "";
			List<FieldFilter> fieldFilters = getExistingFiltersForField(context.getJasperReportsContext(), reportContext, element.getUUID().toString(), sortColumnName);
			
			if (fieldFilters.size() > 0) {
				FieldFilter ff = fieldFilters.get(0);
				if (ff.getFilterValueStart() != null) {
					filterValueStart = ff.getFilterValueStart();
				}
				if (ff.getFilterValueEnd() != null) {
					filterValueEnd = ff.getFilterValueEnd();
				}
				filterTypeOperator = ff.getFilterTypeOperator();
			}

			contextMap.put("id", element.hashCode());
			contextMap.put("uuid", element.getUUID().toString());
			contextMap.put("isFilterable", filterType != null);

			contextMap.put("datasetUuid", element.getUUID().toString());
			contextMap.put("actionData", getActionData(context, sortData));
			
			contextMap.put("isField", SortFieldTypeEnum.FIELD.equals(SortFieldTypeEnum.getByName(sortColumnType)));
			contextMap.put("fieldName", sortColumnName);
			contextMap.put("fieldValueStart", filterValueStart);
			contextMap.put("fieldValueEnd", filterValueEnd);
			contextMap.put("filterType", filterType.getName());
			contextMap.put("filterTypeOperator", filterTypeOperator);
			contextMap.put("filterPattern", filterPattern != null ? filterPattern : "");
//			velocityContext.put("localeCode",);
//			velocityContext.put("timeZoneId",);

			htmlFragment = VelocityUtil.processTemplate(SortElementJsonHandler.SORT_ELEMENT_HTML_TEMPLATE, contextMap);
		}
		
		return htmlFragment;
	}
	
	private String getCurrentSortField(JasperReportsContext jasperReportsContext, ReportContext reportContext, String uuid, String sortDatasetName, String sortColumnName, String sortColumnType) 
	{
		JasperDesignCache cache = JasperDesignCache.getInstance(jasperReportsContext, reportContext);
		SortAction action = new SortAction();
		action.init(jasperReportsContext, reportContext);
		CommandTarget target = action.getCommandTarget(UUID.fromString(uuid));
		if (target != null)
		{
			JasperDesign jasperDesign = cache.getJasperDesign(target.getUri());
			JRDesignDataset dataset = (JRDesignDataset)jasperDesign.getMainDataset();
			
			List<JRSortField> existingFields =  dataset.getSortFieldsList();
			String sortField = null;
	
			if (existingFields != null && existingFields.size() > 0) {
				for (JRSortField field: existingFields) {
					if (field.getName().equals(sortColumnName) && field.getType().getName().equals(sortColumnType)) {
						sortField = sortColumnName + SortElement.SORT_COLUMN_TOKEN_SEPARATOR + sortColumnType + SortElement.SORT_COLUMN_TOKEN_SEPARATOR;
						switch (field.getOrderValue()) {
							case ASCENDING:
								sortField += SortElement.SORT_ORDER_ASC;
								break;
							case DESCENDING:
								sortField += SortElement.SORT_ORDER_DESC;
								break;
						}
						break;
					}
				}
			}
		
			return sortField;
		}
		
		return null;
	}
	
	@Override
	public boolean toExport(JRGenericPrintElement element) {
		return true;
	}
	
	private List<LinkedHashMap<String, String>> getTranslatedOperators(
			JasperReportsContext jasperReportsContext,
			String bundleName,
			NamedEnum[] operators,
			Locale locale
	) //FIXMEJIVE make utility method for translating enums
	{
		List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
		MessageProvider messageProvider = MessageUtil.getInstance(jasperReportsContext).getMessageProvider(bundleName);
		LinkedHashMap<String, String> keys;

		for (NamedEnum operator: operators)
		{
			keys = new LinkedHashMap<String, String>();
			String key = bundleName + "." + ((Enum<?>)operator).name();
			keys.put("key", ((Enum<?>)operator).name());
			keys.put("val", messageProvider.getMessage(key, null, locale));
			result.add(keys);
		}

		return result;
	}
	
	public static void getFieldFilters(DatasetFilter existingFilter, List<FieldFilter> fieldFilters, String fieldName) {//FIXMEJIVE put this in some util and reuse
		if (existingFilter instanceof FieldFilter) {
			if ( fieldName == null || (fieldName != null && ((FieldFilter)existingFilter).getField().equals(fieldName))) {
				fieldFilters.add((FieldFilter)existingFilter);
			} 
		} else if (existingFilter instanceof CompositeDatasetFilter) {
			for (DatasetFilter filter : ((CompositeDatasetFilter)existingFilter).getFilters())
			{
				getFieldFilters(filter, fieldFilters, fieldName);
			}
		}
	}
	
	private String getActionData(JsonExporterContext context, SortData sortData) {
		return "{\"actionName\":\"sortica\",\"sortData\":" + JacksonUtil.getInstance(context.getJasperReportsContext()).getJsonString(sortData)+ "}";
	}
	
	private List<FieldFilter> getExistingFiltersForField(
			JasperReportsContext jasperReportsContext, 
			ReportContext reportContext, 
			String uuid, 
			String filterFieldName
			) 
		{
			JasperDesignCache cache = JasperDesignCache.getInstance(jasperReportsContext, reportContext);
			FilterAction action = new FilterAction();
			action.init(jasperReportsContext, reportContext);
			CommandTarget target = action.getCommandTarget(UUID.fromString(uuid));
			List<FieldFilter> result = new ArrayList<FieldFilter>();
			if (target != null)
			{
				JasperDesign jasperDesign = cache.getJasperDesign(target.getUri());
				JRDesignDataset dataset = (JRDesignDataset)jasperDesign.getMainDataset();
				
				// get existing filter as JSON string
				String serializedFilters = "[]";
				JRPropertiesMap propertiesMap = dataset.getPropertiesMap();
				if (propertiesMap.getProperty(FilterCommand.DATASET_FILTER_PROPERTY) != null) {
					serializedFilters = propertiesMap.getProperty(FilterCommand.DATASET_FILTER_PROPERTY);
				}
				
				List<? extends DatasetFilter> existingFilters = JacksonUtil.getInstance(jasperReportsContext).loadList(serializedFilters, FieldFilter.class);
				if (existingFilters.size() > 0) {
					for (DatasetFilter filter: existingFilters) {
						if (((FieldFilter)filter).getField().equals(filterFieldName)) {
							result.add((FieldFilter)filter);
							break;
						}
					}
				}
			}
			
			return result;
		}
}
