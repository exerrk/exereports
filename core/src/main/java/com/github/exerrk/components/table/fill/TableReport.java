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
package com.github.exerrk.components.table.fill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.exerrk.annotations.properties.Property;
import com.github.exerrk.annotations.properties.PropertyScope;
import com.github.exerrk.components.ComponentsExtensionsRegistryFactory;
import com.github.exerrk.components.headertoolbar.HeaderToolbarElement;
import com.github.exerrk.components.headertoolbar.HeaderToolbarElementUtils;
import com.github.exerrk.components.iconlabel.IconLabelComponent;
import com.github.exerrk.components.iconlabel.IconLabelComponentUtil;
import com.github.exerrk.components.sort.FieldFilter;
import com.github.exerrk.components.sort.FilterTypesEnum;
import com.github.exerrk.components.sort.SortElementHtmlHandler;
import com.github.exerrk.components.sort.actions.FilterCommand;
import com.github.exerrk.components.table.BaseColumn;
import com.github.exerrk.components.table.Cell;
import com.github.exerrk.components.table.Column;
import com.github.exerrk.components.table.ColumnGroup;
import com.github.exerrk.components.table.ColumnVisitor;
import com.github.exerrk.components.table.TableComponent;
import com.github.exerrk.components.table.WhenNoDataTypeTableEnum;
import com.github.exerrk.components.table.util.TableUtil;
import com.github.exerrk.engine.CompositeDatasetFilter;
import com.github.exerrk.engine.DatasetFilter;
import com.github.exerrk.engine.DatasetPropertyExpression;
import com.github.exerrk.engine.JRBand;
import com.github.exerrk.engine.JRChild;
import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRDataset;
import com.github.exerrk.engine.JRElement;
import com.github.exerrk.engine.JRElementGroup;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRExpressionChunk;
import com.github.exerrk.engine.JRField;
import com.github.exerrk.engine.JRGroup;
import com.github.exerrk.engine.JROrigin;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JRPropertiesHolder;
import com.github.exerrk.engine.JRPropertiesMap;
import com.github.exerrk.engine.JRPropertiesUtil;
import com.github.exerrk.engine.JRQuery;
import com.github.exerrk.engine.JRReport;
import com.github.exerrk.engine.JRReportTemplate;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.JRScriptlet;
import com.github.exerrk.engine.JRSection;
import com.github.exerrk.engine.JRSortField;
import com.github.exerrk.engine.JRStaticText;
import com.github.exerrk.engine.JRStyle;
import com.github.exerrk.engine.JRTextField;
import com.github.exerrk.engine.JRValueParameter;
import com.github.exerrk.engine.JRVariable;
import com.github.exerrk.engine.JasperReport;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.base.JRBaseElement;
import com.github.exerrk.engine.base.JRBaseTextElement;
import com.github.exerrk.engine.component.FillContext;
import com.github.exerrk.engine.design.JRDesignBand;
import com.github.exerrk.engine.design.JRDesignElementGroup;
import com.github.exerrk.engine.design.JRDesignExpression;
import com.github.exerrk.engine.design.JRDesignFrame;
import com.github.exerrk.engine.design.JRDesignGenericElement;
import com.github.exerrk.engine.design.JRDesignGenericElementParameter;
import com.github.exerrk.engine.design.JRDesignGroup;
import com.github.exerrk.engine.design.JRDesignSection;
import com.github.exerrk.engine.design.JRDesignTextField;
import com.github.exerrk.engine.export.HtmlExporter;
import com.github.exerrk.engine.export.JRPdfExporterTagHelper;
import com.github.exerrk.engine.export.MatcherExporterFilter;
import com.github.exerrk.engine.fill.DatasetExpressionEvaluator;
import com.github.exerrk.engine.fill.JRExpressionEvalException;
import com.github.exerrk.engine.fill.JRFillField;
import com.github.exerrk.engine.fill.JRFillParameter;
import com.github.exerrk.engine.fill.JRFillVariable;
import com.github.exerrk.engine.type.BandTypeEnum;
import com.github.exerrk.engine.type.ModeEnum;
import com.github.exerrk.engine.type.OrientationEnum;
import com.github.exerrk.engine.type.PositionTypeEnum;
import com.github.exerrk.engine.type.PrintOrderEnum;
import com.github.exerrk.engine.type.RunDirectionEnum;
import com.github.exerrk.engine.type.SectionTypeEnum;
import com.github.exerrk.engine.type.SortFieldTypeEnum;
import com.github.exerrk.engine.type.SortOrderEnum;
import com.github.exerrk.engine.type.SplitTypeEnum;
import com.github.exerrk.engine.type.StretchTypeEnum;
import com.github.exerrk.engine.type.WhenNoDataTypeEnum;
import com.github.exerrk.engine.type.WhenResourceMissingTypeEnum;
import com.github.exerrk.engine.util.JRDataUtils;
import com.github.exerrk.engine.util.JRStringUtil;
import com.github.exerrk.engine.util.Pair;
import com.github.exerrk.engine.util.StyleResolver;
import com.github.exerrk.engine.util.StyleUtil;
import com.github.exerrk.properties.PropertyConstants;
import com.github.exerrk.web.util.JacksonUtil;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class TableReport implements JRReport
{
	public static final String EXCEPTION_MESSAGE_KEY_UNKNOWN_CHILD_TYPE = "fill.table.report.unknown.child.type";
	public static final String EXCEPTION_MESSAGE_KEY_FIELD_NOT_FOUND = "fill.table.report.field.not.found";
	public static final String EXCEPTION_MESSAGE_KEY_VARIABLE_NOT_FOUND = "fill.table.report.variable.not.found";
	public static final String EXCEPTION_MESSAGE_KEY_UNRECOGNIZED_FILTER_EXPRESSION_TYPE = "fill.table.report.unrecognized.filter.expression.type";
	
	public static final String METADATA_KEY_QUALIFICATION = 
			ComponentsExtensionsRegistryFactory.NAMESPACE 
			+ PropertyConstants.COMPONENT_KEY_QUALIFICATION_SEPARATOR 
			+ ComponentsExtensionsRegistryFactory.TABLE_COMPONENT_NAME;
	
	/**
	 * Global property that specifies the character to be used on the column header when the tables's column is sorted ascending
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = "\\u25B2",
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_4_6_0
			)
	public static final String PROPERTY_UP_ARROW_CHAR = JRPropertiesUtil.PROPERTY_PREFIX + "components.sort.up.arrow.char"; //FIXMEJIVE move these from here

	/**
	 * Global property that specifies the character to be used on the column header when the tables's column is sorted descending
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = "\\u25BC",
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_4_6_0
			)
	public static final String PROPERTY_DOWN_ARROW_CHAR = JRPropertiesUtil.PROPERTY_PREFIX + "components.sort.down.arrow.char";

	/**
	 * Global property that specifies the character to be used on the column header when the tables's column has a filtered applied
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = "\\2606",
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_4_6_0
			)
	public static final String PROPERTY_FILTER_CHAR = JRPropertiesUtil.PROPERTY_PREFIX + "components.filter.char";

	/**
	 * Global property that specifies the font to be used for the icons on the column header
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			scopes = {PropertyScope.CONTEXT},
			sinceVersion = PropertyConstants.VERSION_5_5_0
			)
	public static final String PROPERTY_ICON_FONT = JRPropertiesUtil.PROPERTY_PREFIX + "components.icon.font";

	/**
	 * Property that enables/disables the interactivity in the table component
	 * 
	 * <p>
	 * The property can be set:
	 * <ul>
	 * 	<li>globally</li>
	 * 	<li>at report level</li>
	 * 	<li>at component level</li>
	 * 	<li>at column level</li>
	 * </ul>
	 * 
	 * <p>
	 * The default global value of this property is <code>true</code>
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT, PropertyScope.COMPONENT, PropertyScope.TABLE_COLUMN},
			scopeQualifications = {METADATA_KEY_QUALIFICATION},
			sinceVersion = PropertyConstants.VERSION_4_7_1,
			valueType = Boolean.class
			)
	public static final String PROPERTY_INTERACTIVE_TABLE = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.interactive";

	/**
	 *	Property that enables/disables the floating header in the table component when scrolling.
	 *	If the interactivity has been disabled by setting {@link #PROPERTY_INTERACTIVE_TABLE} to <code>false</code>, then
	 *	setting this property will have no effect.
	 *
	 * <p>
	 * It can be set:
	 * <ul>
	 * 	<li>globally</li>
	 * 	<li>at report level</li>
	 * 	<li>at component level</li>
	 * </ul>
	 * </p>
	 *
	 * <p>
	 * The default global value of this property is <code>true</code>
	 * </p>
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT, PropertyScope.COMPONENT},
			scopeQualifications = {METADATA_KEY_QUALIFICATION},
			sinceVersion = PropertyConstants.VERSION_6_3_1,
			valueType = Boolean.class
			)
	public static final String PROPERTY_FLOATING_HEADER = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.floating.header";

	/**
	 * Property that enables/disables the automatic addition of specific custom properties to table cell elements,
	 * that would in turn trigger the creation of special document accessibility tags during PDF export
	 * 
	 * <p>
	 * The property can be set:
	 * <ul>
	 * 	<li>globally</li>
	 * 	<li>at report level</li>
	 * 	<li>at component level</li>
	 * </ul>
	 * 
	 * <p>
	 * The default global value of this property is <code>false</code>
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = PropertyConstants.BOOLEAN_FALSE,
			scopes = {PropertyScope.CONTEXT, PropertyScope.REPORT, PropertyScope.COMPONENT},
			scopeQualifications = {METADATA_KEY_QUALIFICATION},
			sinceVersion = PropertyConstants.VERSION_6_1_2,
			valueType = Boolean.class
			)
	public static final String PROPERTY_GENERATE_TABLE_PDF_TAGS = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.generate.pdf.tags";

	/**
	 * Column property that specifies the field to be used for sorting, filtering and conditional formatting 
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			scopes = {PropertyScope.TABLE_COLUMN},
			sinceVersion = PropertyConstants.VERSION_5_0_1
			)
	public static final String PROPERTY_COLUMN_FIELD = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.column.field";

	/**
	 * Column property that specifies the variable to be used for sorting, filtering and conditional formatting 
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			scopes = {PropertyScope.TABLE_COLUMN},
			sinceVersion = PropertyConstants.VERSION_5_0_1
			)
	public static final String PROPERTY_COLUMN_VARIABLE = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.column.variable";

	/**
	 * Column property that enables/disables sorting
	 * 
	 * <p>
	 * It defaults to <code>true</code>
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.TABLE_COLUMN},
			sinceVersion = PropertyConstants.VERSION_5_0_1,
			valueType = Boolean.class
			)
	public static final String PROPERTY_COLUMN_SORTABLE = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.column.sortable";

	/**
	 * Column property that enables/disables filtering
	 * 
	 * <p>
	 * It defaults to <code>true</code>
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.TABLE_COLUMN},
			sinceVersion = PropertyConstants.VERSION_5_0_1,
			valueType = Boolean.class
			)
	public static final String PROPERTY_COLUMN_FILTERABLE = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.column.filterable";

	/**
	 * Column property that enables/disables conditional formatting
	 * 
	 * <p>
	 * It defaults to <code>true</code>
	 * @deprecated To be removed.
	 */
	@Property(
			category = PropertyConstants.CATEGORY_TABLE,
			defaultValue = PropertyConstants.BOOLEAN_TRUE,
			scopes = {PropertyScope.TABLE_COLUMN},
			sinceVersion = PropertyConstants.VERSION_5_0_1,
			valueType = Boolean.class
			)
	public static final String PROPERTY_COLUMN_CONDITIONALLY_FORMATTABLE = JRPropertiesUtil.PROPERTY_PREFIX + "components.table.column.conditionally.formattable";


	protected static final String SUMMARY_GROUP_NAME = "__SummaryGroup";

	protected static final String HTML_CLASS_CELL_PREFIX = "cel_";
	protected static final String HTML_CLASS_CELL = "jrcel";
	
	/**
	 *
	 */
	public static final String TABLE_HEADER_LABEL_MATCHER_EXPORT_KEY = "com.github.exerrk.components.table.header.label";
	public static final String TABLE_HEADER_ICON_LABEL_MATCHER_EXPORT_KEY = "com.github.exerrk.components.table.header.icon.label";

	private final FillContext fillContext;
	private final TableComponent table;
	private final JasperReport parentReport;
	private final TableReportDataset mainDataset;
	private final BuiltinExpressionEvaluatorFactory builtinEvaluatorFactory;
	private final JRSection detail;
	private final JRDesignBand title;
	private final JRDesignBand summary;
	private final JRDesignBand columnHeader;
	private final JRDesignBand pageFooter;
	private final JRDesignBand lastPageFooter;
	
	private final List<TableIndexProperties> tableIndexProperties;
	private final Map<Integer, JRPropertiesMap> headerHtmlBaseProperties;
	
	private final JRPropertiesUtil propertiesUtil;
	private boolean isInteractiveTable;
	private boolean hasFloatingHeader;
	private boolean isGeneratePdfTags;
	private Map<Column, Pair<Boolean, String>> columnInteractivityMapping;
	
	public TableReport(
		FillContext fillContext, 
		TableComponent table, 
		TableReportDataset mainDataset, 
		List<FillColumn> fillColumns, 
		BuiltinExpressionEvaluatorFactory builtinEvaluatorFactory
		)
	{
		this.tableIndexProperties = new ArrayList<TableIndexProperties>();
		this.headerHtmlBaseProperties = new HashMap<Integer, JRPropertiesMap>();
		
		this.fillContext = fillContext;
		this.table = table;
		this.parentReport = fillContext.getFiller().getJasperReport();
		this.mainDataset = mainDataset;
		this.builtinEvaluatorFactory = builtinEvaluatorFactory;
		
		this.propertiesUtil = JRPropertiesUtil.getInstance(fillContext.getFiller().getJasperReportsContext());
		
		// begin: table interactivity
		this.isInteractiveTable  = Boolean.valueOf(propertiesUtil.getProperty(PROPERTY_INTERACTIVE_TABLE, fillContext.getComponentElement(), this.parentReport));

		this.columnInteractivityMapping = new HashMap<Column, Pair<Boolean, String>>();
		int interactiveColumnCount = 0;
		for (BaseColumn column: TableUtil.getAllColumns(table)) {
			boolean interactiveColumn = isInteractiveTable;
			String columnName = null;
			if (column.getPropertiesMap().containsProperty(PROPERTY_INTERACTIVE_TABLE)) {
				interactiveColumn = Boolean.valueOf(column.getPropertiesMap().getProperty(PROPERTY_INTERACTIVE_TABLE));
			}
			if (interactiveColumn) {
				interactiveColumnCount++;
			}

			if (column.getPropertiesMap().containsProperty(JRComponentElement.PROPERTY_COMPONENT_NAME)) {
				columnName = column.getPropertiesMap().getProperty(JRComponentElement.PROPERTY_COMPONENT_NAME);
			}
			columnInteractivityMapping.put((Column)column, new Pair<Boolean, String>(interactiveColumn, columnName));
		}

		if (interactiveColumnCount > 0) {
			this.isInteractiveTable = true;
			this.hasFloatingHeader = propertiesUtil.getBooleanProperty(PROPERTY_FLOATING_HEADER, true, fillContext.getComponentElement(), this.parentReport);
		}
		// end: table interactivity
		
		this.isGeneratePdfTags  = Boolean.valueOf(propertiesUtil.getProperty(PROPERTY_GENERATE_TABLE_PDF_TAGS, fillContext.getComponentElement(), this.parentReport));
		
		this.columnHeader = createColumnHeader(fillColumns);
		this.detail = wrapBand(createDetailBand(fillColumns), new JROrigin(BandTypeEnum.DETAIL));
		this.title = createTitle(fillColumns);
		this.summary = createSummary(fillColumns); 
		this.pageFooter = createPageFooter(fillColumns);
		
		setGroupBands(fillColumns);
		
		if (pageFooter != null && summary != null)
		{
			// if the table has both column footers and table footers, we need to use
			// a dummy group's footer to print the last column footers so that they
			// appear before the table footers
			addSummaryGroup(fillColumns);
			
			// use an empty last page footer so that the regular page footer doesn't
			// show on the last page
			this.lastPageFooter = new JRDesignBand();
			this.lastPageFooter.setHeight(0);
		}
		else
		{
			// use the regular page footer
			this.lastPageFooter = null;
		}
	}
	
	protected class ReportBandInfo
	{
		final JRDesignBand band;
		final List<BandRowInfo> rows = new ArrayList<BandRowInfo>();
		
		ReportBandInfo(JRDesignBand band)
		{
			this.band = band;
		}

		protected void cellAdded(int rowLevel, CellInfo cell)
		{
			JRElement element = cell.getElement();
			if (band.getHeight() < element.getHeight() + element.getY())
			{
				band.setHeight(element.getHeight() + element.getY());
			}
			
			BandRowInfo row = getBandRowInfo(rowLevel);
			
			row.addCell(cell);
		}
		
		JRDesignElementGroup getRowElementGroup(int rowLevel)
		{
			BandRowInfo row = getBandRowInfo(rowLevel);
			JRDesignElementGroup elementGroup = row.getElementGroup();
			if (elementGroup == null)
			{
				elementGroup = new JRDesignElementGroup();
				row.setElementGroup(elementGroup);
				band.addElementGroup(elementGroup);
			}
			return elementGroup;
		}
		
		BandRowInfo getBandRowInfo(int rowLevel)
		{
			int rowCount = rows.size();
			if (rowLevel >= rowCount)
			{
				for (int level = rowCount; level <= rowLevel; ++level)
				{
					rows.add(new BandRowInfo());
				}
			}
			
			return rows.get(rowLevel);
		}
		
		List<BandRowInfo> getRows()
		{
			return rows;
		}
	}

	protected class BandRowInfo
	{
		JRDesignElementGroup elementGroup;
		List<CellInfo> cells = new ArrayList<CellInfo>();
		
		BandRowInfo()
		{
		}

		protected JRDesignElementGroup getElementGroup()
		{
			return elementGroup;
		}

		protected void setElementGroup(JRDesignElementGroup elementGroup)
		{
			this.elementGroup = elementGroup;
		}
		
		protected List<CellInfo> getCells()
		{
			return cells;
		}
		
		protected void addCell(CellInfo cell)
		{
			cells.add(cell);
		}
	}

	protected class CellInfo
	{
		final JRElement element;
		final int rowSpan;
		final int colSpan;
		
		CellInfo(JRElement element, int rowSpan, int colSpan)
		{
			this.element = element;
			this.rowSpan = rowSpan;
			this.colSpan = colSpan;
		}

		protected JRElement getElement()
		{
			return element;
		}
		
		protected int getRowSpan()
		{
			return rowSpan;
		}
		
		protected int getColSpan()
		{
			return colSpan;
		}
	}

	protected abstract class ReportBandCreator implements ColumnVisitor<Void>
	{
		final ReportBandInfo bandInfo;
		final FillColumn fillColumn;
		int xOffset;
		int yOffset;
		int level;
		
		public ReportBandCreator(ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			this.bandInfo = bandInfo;
			this.fillColumn = fillColumn;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.level = level;
		}

		protected boolean isEmpty(Cell cell)
		{
			return cell == null;
		}
		
		@Override
		public Void visitColumn(Column column)
		{
			Cell cell = columnCell(column);
			
			if (!isEmpty(cell))
			{
				int rowSpan = cell.getRowSpan() == null ? 1 : cell.getRowSpan();
				int rowLevel = level + rowSpan - 1;
				JRDesignElementGroup elementGroup = bandInfo.getRowElementGroup(rowLevel);
				
				JRElement cellElement = createColumnCell(column, elementGroup, cell);
				elementGroup.addElement(cellElement);
				bandInfo.cellAdded(level, new CellInfo(cellElement, rowSpan, fillColumn.getColSpan()));
				
				yOffset += cell.getHeight();
			}
			
			xOffset += column.getWidth();
			
			return null;
		}

		protected abstract Cell columnCell(Column column);
		
		protected JRElement createColumnCell(Column column, JRElementGroup parentGroup, Cell cell)
		{
			return createColumnCell(column, parentGroup, cell, false);
		}
		
		protected JRElement createColumnCell(Column column, JRElementGroup parentGroup, Cell cell, boolean forceFrame)
		{
			return createCell(parentGroup, cell, 
					column.getWidth(), fillColumn.getWidth(), 
					xOffset, yOffset, column.hashCode(), forceFrame);
		}
		
		@Override
		public Void visitColumnGroup(ColumnGroup columnGroup)
		{
			Cell cell = columnGroupCell(columnGroup);
			int cellHeight = 0;
			int sublevel = level;
			if (cell != null)
			{
				int rowSpan = cell.getRowSpan() == null ? 1 : cell.getRowSpan();
				int rowLevel = level + rowSpan - 1;

				int colSpan = 0;
				for (FillColumn subcolumn : fillColumn.getSubcolumns())
				{
					colSpan += subcolumn.getColSpan();
				}
				colSpan = colSpan == 0 ? 1 : colSpan;
				
				JRDesignElementGroup elementGroup = bandInfo.getRowElementGroup(rowLevel);
				
				JRElement cellElement = createColumnGroupCell(columnGroup, cell, elementGroup);
				elementGroup.addElement(cellElement);
				bandInfo.cellAdded(level, new CellInfo(cellElement, rowSpan, colSpan));
				
				cellHeight = cell.getHeight();
				sublevel += rowSpan;
			}
			
			for (FillColumn subcolumn : fillColumn.getSubcolumns())
			{
				ReportBandCreator subVisitor = createSubVisitor(subcolumn, 
						xOffset, yOffset + cellHeight, sublevel);
				subVisitor.visit();
				xOffset = subVisitor.xOffset;
			}
			
			return null;
		}

		protected JRElement createColumnGroupCell(ColumnGroup columnGroup, Cell cell, JRDesignElementGroup elementGroup)
		{
			return createCell(elementGroup, cell, 
					columnGroup.getWidth(), fillColumn.getWidth(), 
					xOffset, yOffset, null, false);
		}

		protected abstract Cell columnGroupCell(ColumnGroup group);
		
		protected abstract ReportBandCreator createSubVisitor(FillColumn subcolumn, 
				int xOffset, int yOffset, int subLevel);
		
		public void visit()
		{
			fillColumn.getTableColumn().visitColumn(this);
		}
	}
	
	protected abstract class ReverseReportBandCreator extends ReportBandCreator
	{
		public ReverseReportBandCreator(ReportBandInfo bandInfo,
				FillColumn fillColumn, int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
		}

		@Override
		public Void visitColumnGroup(ColumnGroup columnGroup)
		{
			Cell cell = columnGroupCell(columnGroup);
			int rowSpan;
			if (cell == null)
			{
				rowSpan = 0;
			}
			else if (cell.getRowSpan() == null)
			{
				rowSpan = 1;
			}
			else
			{
				rowSpan = cell.getRowSpan();
			}
			
			int origXOffset = xOffset;
			int origYOffset = yOffset;
			
			int colSpan = 0;
			
			for (FillColumn subcolumn : fillColumn.getSubcolumns())
			{
				ReportBandCreator subVisitor = createSubVisitor(subcolumn, 
						xOffset, origYOffset, level + rowSpan);
				subVisitor.visit();
				xOffset = subVisitor.xOffset;
				if (subVisitor.yOffset > yOffset)
				{
					yOffset = subVisitor.yOffset;
				}
				colSpan += subcolumn.getColSpan();
			}
			
			colSpan = colSpan == 0 ? 1 : colSpan; 
			
			if (cell != null)
			{
				int rowLevel = level + rowSpan - 1;
				JRDesignElementGroup elementGroup = bandInfo.getRowElementGroup(rowLevel);
				
				JRElement cellElement = createCell(elementGroup, cell, 
						columnGroup.getWidth(), fillColumn.getWidth(), 
						origXOffset, yOffset, null, false);
				elementGroup.addElement(cellElement);
				bandInfo.cellAdded(level, new CellInfo(cellElement, rowSpan, colSpan));
				
				yOffset += cell.getHeight();
			}
			
			return null;
		}
	}
	
	protected class DetailBandCreator extends ReportBandCreator
	{

		public DetailBandCreator(ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
		}
		
		@Override
		protected Cell columnCell(Column column)
		{
			return column.getDetailCell();
		}

		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return null;
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new DetailBandCreator(bandInfo, subcolumn, xOffset, yOffset, sublevel);
		}

		@Override
		protected boolean isEmpty(Cell cell)
		{
			if (super.isEmpty(cell))
			{
				return true;
			}
			
			// also threat zero height cells as empty.
			// FIXME only doing this for the detail cells to minimize impact, it should apply to all cells
			List<JRChild> children = cell.getChildren();
			return cell.getHeight() == 0 && (children == null || children.isEmpty());
		}
	}
	
	protected JRBand createDetailBand(List<FillColumn> fillColumns)
	{
		final JRDesignBand detailBand = new JRDesignBand();
		detailBand.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(detailBand);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			DetailBandCreator subVisitor = new DetailBandCreator(
					bandInfo, subcolumn, xOffset, 0, 0);
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}

		setPdfTags(bandInfo, false);
		
		return detailBand;
	}
	
	protected class ColumnHeaderCreator extends ReportBandCreator
	{
		private Map<Integer, JRPropertiesMap> headerBaseProperties;
		private final AtomicBoolean firstColumn;// we need a mutable boolean reference
		
		public ColumnHeaderCreator(ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level, 
				Map<Integer, JRPropertiesMap> headerBaseProperties, AtomicBoolean firstColumn)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
			this.headerBaseProperties = headerBaseProperties;
			this.firstColumn = firstColumn;
		}

		@Override
		protected Cell columnCell(Column column)
		{
			return column.getColumnHeader();
		}

		@Override
		protected JRDesignFrame createColumnCell(Column column, JRElementGroup parentGroup, Cell cell)
		{
			JRDesignFrame frame = (JRDesignFrame) createColumnCell(column, parentGroup, cell, true);
			addHeaderToolbarElement(column, frame, TableUtil.getCellElement(JRTextField.class, column.getDetailCell(), true));
			return frame;
		}

		protected JRExpression getColumnHeaderLabelExpression(Cell header)
		{
			List<JRChild> detailElements = header == null ? null : header.getChildren();
			// only consider cells with a single text fields
			if (detailElements == null || detailElements.size() != 1)
			{
				return null;
			}

			JRChild detailElement = detailElements.get(0);
			if (detailElement instanceof JRTextField)
			{
				return ((JRTextField) detailElement).getExpression();
			}

			if (detailElement instanceof JRStaticText)
			{
				return builtinEvaluatorFactory.createConstantExpression(((JRStaticText)detailElement).getText());
			}
			
			return null;
		}

		protected void addHeaderToolbarElement(Column column, JRDesignFrame frame, JRTextField sortTextField)
		{
			int columnIndex = TableUtil.getColumnIndex(column, table);
			Pair<Boolean, String> columnData = columnInteractivityMapping.get(column);
			boolean interactiveColumn = columnData.first();
			
			if (sortTextField != null && interactiveColumn)
			{
				Cell header = column.getColumnHeader();
				
				JRDesignGenericElement genericElement = new JRDesignGenericElement(header.getDefaultStyleProvider());
				
				genericElement.setGenericType(HeaderToolbarElement.ELEMENT_TYPE);
				genericElement.setPositionType(com.github.exerrk.engine.type.PositionTypeEnum.FIX_RELATIVE_TO_TOP);
				genericElement.setX(0);
				genericElement.setY(0);
				// TODO lucianc setting to 1 for now; we can't set to frame size as we might not know the padding
				genericElement.setHeight(1);
				genericElement.setWidth(1);
				genericElement.setMode(ModeEnum.TRANSPARENT);
				//genericElement.setStretchType(StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT);
				
				String fieldOrVariableName = null;
				SortFieldTypeEnum columnType = null;
				FilterTypesEnum filterType = null;
				TimeZone formatTimeZone = null;//FIXME also define via properties when sortTextField is not single chunk
				String suffix = "";
				boolean hasFieldOrVariable = false;
				
				if (column.getPropertiesMap().containsProperty(PROPERTY_COLUMN_FIELD))
				{
					hasFieldOrVariable = true;
					fieldOrVariableName = column.getPropertiesMap().getProperty(PROPERTY_COLUMN_FIELD);
					columnType = SortFieldTypeEnum.FIELD;
					JRField field = getField(fieldOrVariableName);
					if (field != null) 
					{
						filterType = HeaderToolbarElementUtils.getFilterType(field.getValueClass());
					} else 
					{
						throw 
							new JRRuntimeException(
								EXCEPTION_MESSAGE_KEY_FIELD_NOT_FOUND,  
								new Object[]{fieldOrVariableName} 
								);
					}
				} else if (column.getPropertiesMap().containsProperty(PROPERTY_COLUMN_VARIABLE))
				{
					hasFieldOrVariable = true;
					fieldOrVariableName = column.getPropertiesMap().getProperty(PROPERTY_COLUMN_VARIABLE);
					columnType = SortFieldTypeEnum.VARIABLE;
					JRVariable variable = getVariable(fieldOrVariableName);
					if (variable != null)
					{
						filterType = HeaderToolbarElementUtils.getFilterType(variable.getValueClass());
					} else
					{
						throw 
							new JRRuntimeException(
								EXCEPTION_MESSAGE_KEY_VARIABLE_NOT_FOUND,  
								new Object[]{fieldOrVariableName} 
								);
					}
				} else if (TableUtil.hasSingleChunkExpression(sortTextField)) 
				{
					JRExpressionChunk sortExpression = sortTextField.getExpression().getChunks()[0];
					fieldOrVariableName = sortExpression.getText();
					
					switch (sortExpression.getType())
					{
					case JRExpressionChunk.TYPE_FIELD:
						columnType = SortFieldTypeEnum.FIELD;
						JRField field = getField(fieldOrVariableName);
						filterType = HeaderToolbarElementUtils.getFilterType(field.getValueClass());
						formatTimeZone = getFormatTimeZone(sortTextField, field.getValueClass());
						break;
						
					case JRExpressionChunk.TYPE_VARIABLE:
						columnType = SortFieldTypeEnum.VARIABLE;
						JRVariable variable = getVariable(fieldOrVariableName);
						filterType = HeaderToolbarElementUtils.getFilterType(variable.getValueClass());
						formatTimeZone = getFormatTimeZone(sortTextField, variable.getValueClass());
						break;
						
					default:
						// never
						throw 
							new JRRuntimeException(
								EXCEPTION_MESSAGE_KEY_UNRECOGNIZED_FILTER_EXPRESSION_TYPE,  
								new Object[]{sortExpression.getType()} 
								);
					}	
				}
				
				boolean isSortable = propertiesUtil.getBooleanProperty(column.getPropertiesMap(), PROPERTY_COLUMN_SORTABLE, true) && fieldOrVariableName != null;
				boolean isFilterable = propertiesUtil.getBooleanProperty(column.getPropertiesMap(), PROPERTY_COLUMN_FILTERABLE, true) 
						&& fieldOrVariableName != null && TableUtil.isFilterable(sortTextField)
						&& filterType != null;
				
				if (isSortable)
				{	// column is sortable
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_CAN_SORT, Boolean.TRUE.toString());
					
					//FIXMEJIVE consider moving in separate method
					JRSortField[] sortFields = TableReport.this.mainDataset.getSortFields();
					if (sortFields != null)
					{
						for(JRSortField sortField : sortFields)
						{
							if (
								sortField.getName().equals(fieldOrVariableName)
								&& sortField.getType() == columnType
								)
							{
								suffix += 
									"" 
									+ (sortField.getOrderValue() == SortOrderEnum.ASCENDING 
										? propertiesUtil.getProperty(PROPERTY_UP_ARROW_CHAR)
										: (sortField.getOrderValue() == SortOrderEnum.DESCENDING 
											? propertiesUtil.getProperty(PROPERTY_DOWN_ARROW_CHAR)
											: ""));
							}
						}
					}
					
				} else
				{	// column is not sortable
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_CAN_SORT, Boolean.FALSE.toString());
				}
				
				if (isFilterable)
				{	// column is filterable
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_CAN_FILTER, Boolean.TRUE.toString());
					
					JasperReportsContext jasperReportsContext = fillContext.getFiller().getJasperReportsContext();
					String serializedFilters = TableReport.this.mainDataset.getPropertiesMap().getProperty(FilterCommand.DATASET_FILTER_PROPERTY);
					if (serializedFilters != null)
					{
						List<? extends DatasetFilter> existingFilters = JacksonUtil.getInstance(jasperReportsContext).loadList(serializedFilters, FieldFilter.class);
						if (existingFilters != null)
						{
							List<FieldFilter> fieldFilters = new ArrayList<FieldFilter>();
							SortElementHtmlHandler.getFieldFilters(new CompositeDatasetFilter(existingFilters), fieldFilters, fieldOrVariableName);
							if (fieldFilters.size() > 0)
							{
								suffix += "" + propertiesUtil.getProperty(PROPERTY_FILTER_CHAR);
							}
						}
					}
					
					if (formatTimeZone != null && !formatTimeZone.equals(fillContext.getFiller().getFillContext().getMasterTimeZone()))
					{
						genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_FORMAT_TIME_ZONE, 
								JRDataUtils.getTimeZoneId(formatTimeZone));
					}
				} else
				{	// column is not filterable
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_CAN_FILTER, Boolean.FALSE.toString());
					
				}
				
				if (suffix.length() > 0)
				{
					addIconLabelComponent(column, frame, suffix);
					
//					HeaderLabelBuiltinExpression evaluator = HeaderLabelUtil.alterHeaderLabel(frame, suffix);
//					if (evaluator != null)
//					{
//						builtinEvaluators.put(evaluator.getExpression(), evaluator);
//					}
				}

				if (isSortable || isFilterable) {
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_TYPE, columnType.getName());
				}
				
				if (filterType != null)
				{
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_FILTER_TYPE, filterType.getName());
				}

				if (columnType != null && hasFieldOrVariable) {
					String property = SortFieldTypeEnum.FIELD.equals(columnType) ? HeaderToolbarElement.PROPERTY_COLUMN_FIELD : HeaderToolbarElement.PROPERTY_COLUMN_VARIABLE;
					genericElement.getPropertiesMap().setProperty(property, fieldOrVariableName);
				}
				
				String columnName = fieldOrVariableName != null ? fieldOrVariableName : String.valueOf(columnIndex);
				String columnUuid = column.getUUID().toString();//columnName + "_" + column.hashCode();
				String cellId = columnName + "_" + column.hashCode();
				
				if (firstColumn.compareAndSet(false, true)) {
					// only setting on the first column to save memory
					//FIXME a cleaner approach would be to set these another single generic element 
					addColumnLabelParameters(genericElement, table);

					// setting component name on first column
					String tableName = propertiesUtil.getProperty(JRComponentElement.PROPERTY_COMPONENT_NAME, fillContext.getComponentElement());
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_TABLE_NAME, tableName);

					// set the hasFloatingHeader property on first column
					genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_FLOATING_HEADER, String.valueOf(hasFloatingHeader));
				}
	
				genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_UUID, columnUuid);
				genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_INDEX, Integer.toString(columnIndex));
	
				genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_NAME, columnName);
				genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_COMPONENT_NAME, columnData.second());
				genericElement.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_TABLE_UUID, fillContext.getComponentElement().getUUID().toString());
				addElementParameter(genericElement, HeaderToolbarElement.PARAMETER_COLUMN_LABEL, getColumnHeaderLabelExpression(header));
	
				frame.getPropertiesMap().setProperty(HtmlExporter.PROPERTY_HTML_CLASS, "jrcolHeader" + (interactiveColumn ? " interactiveElement" : ""));
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_UUID, columnUuid);
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_TABLE_UUID, fillContext.getComponentElement().getUUID().toString());
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_INDEX, String.valueOf(columnIndex));

				String cellIdFixedPart = JRStringUtil.getCSSClass(cellId + "_");
				TableIndexProperties cellIdProperties = new TableIndexProperties(
						HeaderToolbarElement.PROPERTY_CELL_ID, cellIdFixedPart);
				tableIndexProperties.add(cellIdProperties);
				assert frame.getPropertiesMap().getBaseProperties() == null;
				frame.getPropertiesMap().setBaseProperties(cellIdProperties.getPropertiesMap());

				String classFixedPart = TableReport.HTML_CLASS_CELL + " " + TableReport.HTML_CLASS_CELL_PREFIX + cellIdFixedPart;
				TableIndexProperties columnClassProperties = new TableIndexProperties(
						HtmlExporter.PROPERTY_HTML_CLASS, classFixedPart);
				tableIndexProperties.add(columnClassProperties);
				headerBaseProperties.put(column.hashCode(), columnClassProperties.getPropertiesMap());
				
				frame.addElement(0, genericElement);
			} else 
			{
				String columnName = String.valueOf(columnIndex);
				String cellId = columnName + "_" + column.hashCode();
				
				frame.getPropertiesMap().setProperty(HtmlExporter.PROPERTY_HTML_CLASS, "jrcolHeader");
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_UUID, column.getUUID().toString());
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_CELL_ID, cellId);
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_TABLE_UUID, fillContext.getComponentElement().getUUID().toString());
				frame.getPropertiesMap().setProperty(HeaderToolbarElement.PROPERTY_COLUMN_INDEX, String.valueOf(columnIndex));
			}
		}

		protected void addIconLabelComponent(Column column, JRDesignFrame frame, String suffix)
		{
			List<JRChild> children = frame.getChildren();
			if (children.size() > 0)
			{
				JRBaseTextElement headerTextElement = (JRBaseTextElement)children.get(0);
				if (headerTextElement != null) 
				{
					JRComponentElement componentElement = 
						IconLabelComponentUtil.getInstance(fillContext.getFiller().getJasperReportsContext()).createIconLabelComponentElement(headerTextElement);
					IconLabelComponent iconLabelComponent = (IconLabelComponent)componentElement.getComponent();
					
					JRDesignTextField labelTextField = (JRDesignTextField)iconLabelComponent.getLabelTextField();
					if (headerTextElement instanceof JRTextField) 
					{
						labelTextField.setExpression(((JRTextField) headerTextElement).getExpression());
					}
					else if (headerTextElement instanceof JRStaticText) 
					{
						labelTextField.setExpression(builtinEvaluatorFactory.createConstantExpression(((JRStaticText)headerTextElement).getText()));
					}

					JRDesignTextField iconTextField = (JRDesignTextField)iconLabelComponent.getIconTextField();
					iconTextField.setExpression(builtinEvaluatorFactory.createConstantExpression(suffix));
					
					componentElement.getPropertiesMap().setProperty(MatcherExporterFilter.PROPERTY_MATCHER_EXPORT_FILTER_KEY, TABLE_HEADER_ICON_LABEL_MATCHER_EXPORT_KEY);
					
					JRBaseElement element = (JRBaseElement)frame.getChildren().get(0);
					element.getPropertiesMap().setProperty(MatcherExporterFilter.PROPERTY_MATCHER_EXPORT_FILTER_KEY, TABLE_HEADER_LABEL_MATCHER_EXPORT_KEY);

					//frame.getChildren().remove(0);
					frame.getChildren().add(componentElement);
				}
			}
		}
		
		protected void addElementParameter(JRDesignGenericElement element, String name, Object value)
		{
			JRDesignGenericElementParameter param = new JRDesignGenericElementParameter();
			param.setName(name);
			
			JRDesignExpression valueExpression = builtinEvaluatorFactory.createConstantExpression(value);
			param.setValueExpression(valueExpression);
			
			element.addParameter(param);
		}

		protected void addElementParameter(JRDesignGenericElement element, String name, JRExpression expression)
		{
			JRDesignGenericElementParameter param = new JRDesignGenericElementParameter();
			param.setName(name);
			param.setValueExpression(expression);
			element.addParameter(param);
		}
		
		protected void addColumnLabelParameters(JRDesignGenericElement element, TableComponent table) {
			List<BaseColumn> columns = TableUtil.getAllColumns(table);
			for(int i = 0, ln = columns.size(); i < ln; i++) {
				BaseColumn column = columns.get(i);
				JRExpression columnHeaderExpression = getColumnHeaderLabelExpression(column.getColumnHeader());
				boolean interactiveColumn = columnInteractivityMapping.get(column).first() && (TableUtil.getCellElement(JRTextField.class, ((Column)column).getDetailCell(), true) != null);
				String paramName = HeaderToolbarElement.PARAM_COLUMN_LABEL_PREFIX + i + "|" + column.getUUID().toString() + "|" + interactiveColumn;
				addElementParameter(element, paramName, columnHeaderExpression);
			}
		}
		
		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return group.getColumnHeader();
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new ColumnHeaderCreator(bandInfo, subcolumn, xOffset, yOffset, sublevel, 
					headerHtmlBaseProperties, firstColumn);
		}

		@Override
		protected JRElement createColumnGroupCell(ColumnGroup columnGroup, Cell cell, JRDesignElementGroup elementGroup)
		{
			JRDesignFrame frame = (JRDesignFrame) createCell(elementGroup, cell, 
					columnGroup.getWidth(), fillColumn.getWidth(), 
					xOffset, yOffset, null, true);
			frame.getPropertiesMap().setProperty(HtmlExporter.PROPERTY_HTML_CLASS, "jrcolGroupHeader");
			return frame;
		}
	}
	
	protected TimeZone getFormatTimeZone(JRTextField textField, Class<?> valueClass)
	{
		//FIXME this duplicates the logic in JRFillTextField.determineOwnTimeZone()
		String formatTimeZone = null;
		if (textField.hasProperties())
		{
			formatTimeZone = textField.getPropertiesMap().getProperty(JRTextField.PROPERTY_FORMAT_TIMEZONE);
		}
		
		if (formatTimeZone == null || formatTimeZone.isEmpty())
		{
			if (java.sql.Date.class.isAssignableFrom(valueClass))
			{
				formatTimeZone = propertiesUtil.getProperty(parentReport, JRTextField.PROPERTY_SQL_DATE_FORMAT_TIMEZONE);
			}
			else if (java.sql.Timestamp.class.isAssignableFrom(valueClass))
			{
				formatTimeZone = propertiesUtil.getProperty(parentReport, JRTextField.PROPERTY_SQL_TIMESTAMP_FORMAT_TIMEZONE);
			}
			else if (java.sql.Time.class.isAssignableFrom(valueClass))
			{
				formatTimeZone = propertiesUtil.getProperty(parentReport, JRTextField.PROPERTY_SQL_TIME_FORMAT_TIMEZONE);
			}
		}
		
		if (formatTimeZone == null || formatTimeZone.isEmpty())
		{
			formatTimeZone = propertiesUtil.getProperty(parentReport, JRTextField.PROPERTY_FORMAT_TIMEZONE);
		}
		
		TimeZone reportTimeZone = fillContext.getFillDataset().getTimeZone();
		return JRDataUtils.resolveFormatTimeZone(formatTimeZone, reportTimeZone);
	}

	protected JRDesignBand createColumnHeader(List<FillColumn> fillColumns)
	{
		JRDesignBand columnHeader = new JRDesignBand();
		columnHeader.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(columnHeader);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			ColumnHeaderCreator subVisitor = new ColumnHeaderCreator(
					bandInfo, subcolumn, xOffset, 0, 0, headerHtmlBaseProperties,
					new AtomicBoolean());
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}

		setPdfTags(bandInfo, true);
		
		if (columnHeader.getHeight() == 0)
		{
			columnHeader = null;
		}
		return columnHeader;
	}
	
	protected class PageFooterCreator extends ReverseReportBandCreator
	{
		public PageFooterCreator(ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
		}

		@Override
		protected Cell columnCell(Column column)
		{
			return column.getColumnFooter();
		}

		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return group.getColumnFooter();
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new PageFooterCreator(bandInfo, subcolumn, xOffset, yOffset, sublevel);
		}
	}

	protected JRDesignBand createPageFooter(List<FillColumn> fillColumns)
	{
		JRDesignBand pageFooter = new JRDesignBand();
		pageFooter.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(pageFooter);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			PageFooterCreator subVisitor = new PageFooterCreator(
					bandInfo, subcolumn, xOffset, 0, 0);
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}
		
		setPdfTags(bandInfo, false);

		if (pageFooter.getHeight() == 0)
		{
			pageFooter = null;
		}
		return pageFooter;
	}
	
	protected class TitleCreator extends ReportBandCreator
	{
		public TitleCreator(ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
		}

		@Override
		protected Cell columnCell(Column column)
		{
			return column.getTableHeader();
		}

		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return group.getTableHeader();
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new TitleCreator(bandInfo, subcolumn, xOffset, yOffset, sublevel);
		}
	}

	protected JRDesignBand createTitle(List<FillColumn> fillColumns)
	{
		JRDesignBand title = new JRDesignBand();
		title.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(title);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			TitleCreator subVisitor = new TitleCreator(
					bandInfo, subcolumn, xOffset, 0, 0);
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}

		setPdfTags(bandInfo, false);
		
		if (title.getHeight() == 0)
		{
			title = null;
		}
		return title;
	}
	
	protected class SummaryCreator extends ReverseReportBandCreator
	{
		public SummaryCreator(ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
		}

		@Override
		protected Cell columnCell(Column column)
		{
			return column.getTableFooter();
		}

		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return group.getTableFooter();
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new SummaryCreator(bandInfo, subcolumn, xOffset, yOffset, sublevel);
		}
	}

	protected JRDesignBand createSummary(List<FillColumn> fillColumns)
	{
		JRDesignBand summary = new JRDesignBand();
		summary.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(summary);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			SummaryCreator subVisitor = new SummaryCreator(
					bandInfo, subcolumn, xOffset, 0, 0);
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}
		
		setPdfTags(bandInfo, false);

		if (summary.getHeight() == 0)
		{
			summary = null;
		}
		return summary;
	}
	
	protected class GroupHeaderCreator extends ReportBandCreator
	{
		private final String groupName;
		
		public GroupHeaderCreator(String groupName,
				ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
			
			this.groupName = groupName;
		}

		@Override
		protected Cell columnCell(Column column)
		{
			return column.getGroupHeader(groupName);
		}

		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return group.getGroupHeader(groupName);
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new GroupHeaderCreator(groupName, 
					bandInfo, subcolumn, xOffset, yOffset, sublevel);
		}
	}

	protected JRBand createGroupHeader(String groupName, List<FillColumn> fillColumns)
	{
		JRDesignBand header = new JRDesignBand();
		header.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(header);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			GroupHeaderCreator subVisitor = new GroupHeaderCreator(groupName,
					bandInfo, subcolumn, xOffset, 0, 0);
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}
		
		setPdfTags(bandInfo, false);
		
		if (header.getHeight() == 0)
		{
			header = null;
		}
		return header;
	}
	
	protected class GroupFooterCreator extends ReverseReportBandCreator
	{
		private final String groupName;
		
		public GroupFooterCreator(String groupName,
				ReportBandInfo bandInfo, FillColumn fillColumn,
				int xOffset, int yOffset, int level)
		{
			super(bandInfo, fillColumn, xOffset, yOffset, level);
			
			this.groupName = groupName;
		}

		@Override
		protected Cell columnCell(Column column)
		{
			return column.getGroupFooter(groupName);
		}

		@Override
		protected Cell columnGroupCell(ColumnGroup group)
		{
			return group.getGroupFooter(groupName);
		}

		@Override
		protected ReportBandCreator createSubVisitor(FillColumn subcolumn,
				int xOffset, int yOffset, int sublevel)
		{
			return new GroupFooterCreator(groupName, 
					bandInfo, subcolumn, xOffset, yOffset, sublevel);
		}
	}

	protected JRBand createGroupFooter(String groupName, List<FillColumn> fillColumns)
	{
		JRDesignBand footer = new JRDesignBand();
		footer.setSplitType(SplitTypeEnum.PREVENT);
		
		ReportBandInfo bandInfo = new ReportBandInfo(footer);
		int xOffset = 0;
		for (FillColumn subcolumn : fillColumns)
		{
			GroupFooterCreator subVisitor = new GroupFooterCreator(groupName,
					bandInfo, subcolumn, xOffset, 0, 0);
			subVisitor.visit();
			xOffset = subVisitor.xOffset;
		}

		setPdfTags(bandInfo, false);
		
		if (footer.getHeight() == 0)
		{
			footer = null;
		}
		return footer;
	}
	
	private void setGroupBands(List<FillColumn> fillColumns)
	{
		TableReportGroup[] groups = mainDataset.getTableGroups();
		if (groups != null)
		{
			for (TableReportGroup group : groups)
			{
				JRBand header = createGroupHeader(group.getName(), fillColumns);
				if (header != null)
				{
					group.setGroupHeader(header);
				}
				JRBand footer = createGroupFooter(group.getName(), fillColumns);
				if (footer != null)
				{
					group.setGroupFooter(footer);
				}
			}
		}
		
		
	}

	private void setPdfTags(ReportBandInfo bandInfo, boolean isHeader)
	{
		if (!isGeneratePdfTags)
		{
			return;
		}
		
		String cellTagProp = isHeader ? JRPdfExporterTagHelper.PROPERTY_TAG_TH : JRPdfExporterTagHelper.PROPERTY_TAG_TD;

		List<BandRowInfo> rows = bandInfo.getRows();
		
		for (BandRowInfo row : rows)
		{
			List<CellInfo> cells = row.getCells();
			CellInfo firstCell = cells.get(0);
			firstCell.getElement().getPropertiesMap().setProperty(JRPdfExporterTagHelper.PROPERTY_TAG_TR, JRPdfExporterTagHelper.TAG_START);
			
			for (CellInfo cell : cells)
			{
				cell.getElement().getPropertiesMap().setProperty(cellTagProp, JRPdfExporterTagHelper.TAG_FULL);
				if (cell.getRowSpan() > 1)
				{
					cell.getElement().getPropertiesMap().setProperty(JRPdfExporterTagHelper.PROPERTY_TAG_ROWSPAN, String.valueOf(cell.getRowSpan()));
				}
				if (cell.getColSpan() > 1)
				{
					cell.getElement().getPropertiesMap().setProperty(JRPdfExporterTagHelper.PROPERTY_TAG_COLSPAN, String.valueOf(cell.getColSpan()));
				}
			}
			
			CellInfo lastCell = cells.get(cells.size() - 1);
			lastCell.getElement().getPropertiesMap().setProperty(
				JRPdfExporterTagHelper.PROPERTY_TAG_TR, 
				cells.size() == 1 ? JRPdfExporterTagHelper.TAG_FULL : JRPdfExporterTagHelper.TAG_END
				);
		}		
	}

	protected boolean isGeneratePdfTags()
	{
		return isGeneratePdfTags;
	}

	
	protected static final String TABLE_SCRIPTLET_NAME = "__Table";
	
	protected class SummaryGroupFooterPrintWhenEvaluator implements BuiltinExpressionEvaluator
	{

		private JRValueParameter tableScriptletParam;
		private TableReportScriptlet tableScriptlet;
		
		@Override
		public void init(Map<String, JRFillParameter> parametersMap, 
				Map<String, JRFillField> fieldsMap, 
				Map<String, JRFillVariable> variablesMap, 
				WhenResourceMissingTypeEnum resourceMissingType)
				throws JRException
		{
			tableScriptletParam = parametersMap.get(TABLE_SCRIPTLET_NAME 
					+ JRScriptlet.SCRIPTLET_PARAMETER_NAME_SUFFIX);
		}

		protected void ensureValue()
		{
			if (tableScriptlet == null)
			{
				tableScriptlet = (TableReportScriptlet) tableScriptletParam.getValue();
			}
		}
		
		@Override
		public Object evaluate(DatasetExpressionEvaluator evaluator) throws JRExpressionEvalException
		{
			ensureValue();
			return tableScriptlet.hasDetailOnPage();
		}

		@Override
		public Object evaluateEstimated(DatasetExpressionEvaluator evaluator) throws JRExpressionEvalException
		{
			ensureValue();
			return tableScriptlet.hasDetailOnPage();
		}

		@Override
		public Object evaluateOld(DatasetExpressionEvaluator evaluator) throws JRExpressionEvalException
		{
			ensureValue();
			return tableScriptlet.hasDetailOnPage();
		}
	}
	
	protected int computeTableWidth(List<FillColumn> fillColumns)
	{
		int width = 0;
		for (FillColumn column : fillColumns)
		{
			width += column.getWidth();
		}
		return width;
	}
	
	protected void addSummaryGroup(List<FillColumn> fillColumns)
	{
		JRDesignGroup summaryGroup = new JRDesignGroup();
		summaryGroup.setName(SUMMARY_GROUP_NAME);//TODO check for uniqueness
		
		JRDesignBand groupFooter = new JRDesignBand();
		groupFooter.setSplitType(SplitTypeEnum.PREVENT);
		groupFooter.setHeight(pageFooter.getHeight());
		
		// we need to put everything in a frame so that we can tell the frame
		// not to print when there are no detail bands on the current page
		// 
		// we can't do that directly to the band since its print when expression
		// is evaluated too soon
		JRDesignFrame footerFrame = new JRDesignFrame();
		footerFrame.setX(0);
		footerFrame.setY(0);
		footerFrame.setWidth(computeTableWidth(fillColumns));
		footerFrame.setHeight(pageFooter.getHeight());
		footerFrame.getLineBox().setPadding(0);
		footerFrame.getLineBox().getPen().setLineWidth(0f);
		footerFrame.setRemoveLineWhenBlank(true);
		
		JRDesignExpression footerPrintWhen = builtinEvaluatorFactory.createExpression(new SummaryGroupFooterPrintWhenEvaluator());
		footerFrame.setPrintWhenExpression(footerPrintWhen);
		
		// clone the contents of the page footer in the frame
		List<JRChild> footerElements = pageFooter.getChildren();
		for (Iterator<JRChild> iterator = footerElements.iterator(); iterator
				.hasNext();)
		{
			JRChild child = iterator.next();
			JRChild childClone = (JRChild) child.clone(footerFrame);
			if (childClone instanceof JRElement)
			{
				footerFrame.addElement((JRElement) childClone);
			}
			else if (childClone instanceof JRElementGroup)
			{
				footerFrame.addElementGroup((JRElementGroup) childClone);
			}
			else
			{
				throw 
					new JRRuntimeException(
						EXCEPTION_MESSAGE_KEY_UNKNOWN_CHILD_TYPE,  
						new Object[]{childClone.getClass().getName()} 
						);
			}
		}
		
		groupFooter.addElement(footerFrame);
		((JRDesignSection) summaryGroup.getGroupFooterSection()).addBand(groupFooter);
		
		mainDataset.addScriptlet(TABLE_SCRIPTLET_NAME, TableReportScriptlet.class);
		mainDataset.addFirstGroup(summaryGroup);
	}

	protected JRElement createCell(JRElementGroup parentGroup, Cell cell, 
			int originalWidth, int width, 
			int x, int y, Integer columnHashCode, 
			boolean forceFrame)
	{
		if (!forceFrame)
		{
			JRElement cellElement = createCellElement(parentGroup, cell, originalWidth, width, x, y, columnHashCode);
			if (cellElement != null)
			{
				return cellElement;
			}
		}
		
		JRDesignFrame frame = new JRDesignFrame(this);
		frame.setElementGroup(parentGroup);
		frame.setX(x);
		frame.setY(y);
		frame.setWidth(width);
		frame.setHeight(cell.getHeight());
		frame.setPositionType(PositionTypeEnum.FLOAT);
		frame.setStretchType(StretchTypeEnum.ELEMENT_GROUP_HEIGHT);
		
		frame.setStyle(cell.getStyle());
		frame.setStyleNameReference(cell.getStyleNameReference());
		frame.copyBox(cell.getLineBox());

		if (columnHashCode != null && headerHtmlBaseProperties.get(columnHashCode) != null) {
			JRPropertiesMap propertiesMap = frame.getPropertiesMap();
			assert propertiesMap != null && propertiesMap.getBaseProperties() == null;
			propertiesMap.setBaseProperties(headerHtmlBaseProperties.get(columnHashCode));
		}
		// not transferring cell properties to the frame/element for now
		
		for (Iterator<JRChild> it = cell.getChildren().iterator(); it.hasNext();)
		{
			JRChild child = it.next();
			if (child instanceof JRElement)
			{
				JRElement element = (JRElement) child;
				// clone the element in order to set the frame as group
				element = (JRElement) element.clone(frame);
				if (width != originalWidth)
				{
					scaleCellElement(element, originalWidth, width);
					
					if (element instanceof JRElementGroup)//i.e. frame
					{
						JRElementGroup elementGroup = (JRElementGroup) element;
						for (JRElement subelement : elementGroup.getElements())
						{
							scaleCellElement(subelement, originalWidth, width);
						}
					}
				}
				frame.addElement(element);
			}
			else if (child instanceof JRElementGroup)
			{
				JRElementGroup elementGroup = (JRElementGroup) child;
				// clone the elements in order to set the frame as group
				elementGroup = (JRElementGroup) elementGroup.clone(frame);
				frame.addElementGroup(elementGroup);
				
				if (width != originalWidth)
				{
					for (JRElement element : elementGroup.getElements())
					{
						scaleCellElement(element, originalWidth, width);
					}
				}
			}
			else
			{
				throw 
					new JRRuntimeException(
						EXCEPTION_MESSAGE_KEY_UNKNOWN_CHILD_TYPE,  
						new Object[]{child.getClass().getName()} 
						);
			}
		}
		
		return frame;
	}
	
	protected JRElement createCellElement(JRElementGroup elementGroup, Cell cell, 
			int originalWidth, int width, 
			int x, int y, Integer columnHashCode)
	{
		List<JRChild> children = cell.getChildren();
		if (children.size() != 1)
		{
			// several children
			return null;
		}
		
		JRChild child = children.get(0);
		if (!(child instanceof JRStaticText || child instanceof JRTextField))
		{
			// only doing this for texts for now
			return null;
		}
		
		JRElement element = (JRElement) child;
		if (element.getX() != 0 
				|| element.getY() != 0 
				|| element.getWidth() != originalWidth 
				|| element.getHeight() != cell.getHeight())
		{
			// the element is not as large as the cell
			return null;
		}
		
		ModeEnum elementMode = StyleUtil.instance().resolveElementMode(element);
		if (elementMode == null || elementMode == ModeEnum.TRANSPARENT)
		{
			// if the element is not necessarily opaque, check that the cell is transparent
			ModeEnum cellMode = StyleUtil.instance().resolveMode(cell);
			if (cellMode != ModeEnum.TRANSPARENT)
			{
				// the cell is not necessarily transparent, we need the frame
				return null;
			}
		}
		
		if (StyleUtil.instance().hasBox(cell))
		{
			// the cell has box, we need the frame
			return null;
		}
		
		JRElement cellElement = element.clone(elementGroup, y);
		cellElement.setX(x);
		cellElement.setWidth(width);
		cellElement.setStretchType(StretchTypeEnum.ELEMENT_GROUP_HEIGHT);

		if (columnHashCode != null && headerHtmlBaseProperties.get(columnHashCode) != null)
		{
			JRPropertiesMap propertiesMap = cellElement.getPropertiesMap();
			assert propertiesMap != null && propertiesMap.getBaseProperties() == null;
			propertiesMap.setBaseProperties(headerHtmlBaseProperties.get(columnHashCode));
		}
		
		if (width != originalWidth)
		{
			scaleCellElement(element, originalWidth, width);
		}
		
		return cellElement;
	}

	protected void scaleCellElement(JRElement element, Integer cellWidth,
			int scaledCellWidth)
	{
		int scaledX = (int) Math.floor(((float) element.getX() * scaledCellWidth) / cellWidth);
		element.setX(scaledX);
		
		int scaledWidth = (int) Math.floor(((float) element.getWidth() * scaledCellWidth) / cellWidth);
		element.setWidth(scaledWidth);
	}
	
	protected JRSection wrapBand(JRBand band, JROrigin origin)
	{
		JRDesignSection section = new JRDesignSection(origin);
		section.addBand(band);
		return section;
	}
	
	@Override
	public JRBand getBackground()
	{
		return null;
	}

	@Override
	public int getBottomMargin()
	{
		return 0;
	}

	@Override
	public int getColumnCount()
	{
		return 1;
	}

	@Override
	public JRBand getColumnFooter()
	{
		return null;
	}

	@Override
	public JRBand getColumnHeader()
	{
		return columnHeader;
	}

	@Override
	public int getColumnSpacing()
	{
		return 0;
	}

	@Override
	public int getColumnWidth()
	{
		return fillContext.getComponentElement().getWidth();
	}

	@Override
	public JRDataset[] getDatasets()
	{
		return parentReport.getDatasets();
	}

	@Deprecated
	public JRBand getDetail()
	{
		// see #getDetailSection()
		return null;
	}

	@Override
	public JRSection getDetailSection()
	{
		return detail;
	}

	@Override
	public JRField[] getFields()
	{
		return mainDataset.getFields();
	}

	protected JRField getField(String name)
	{
		JRField found = null;
		for (JRField field : getFields())
		{
			if (name.equals(field.getName()))
			{
				found = field;
				break;
			}
		}
		return found;
	}

	@Override
	public String getFormatFactoryClass()
	{
		return parentReport.getFormatFactoryClass();
	}

	@Override
	public JRGroup[] getGroups()
	{
		return mainDataset.getGroups();
	}

	@Override
	public String[] getImports()
	{
		return parentReport.getImports();
	}

	@Override
	public String getLanguage()
	{
		return parentReport.getLanguage();
	}

	@Override
	public JRBand getLastPageFooter()
	{
		return lastPageFooter;
	}

	@Override
	public int getLeftMargin()
	{
		return 0;
	}

	@Override
	public JRDataset getMainDataset()
	{
		return mainDataset;
	}

	@Override
	public String getName()
	{
		return mainDataset.getName();
	}

	@Override
	public JRBand getNoData()
	{
		return null;
	}

	@Override
	public OrientationEnum getOrientationValue()
	{
		return OrientationEnum.PORTRAIT;
	}

	@Override
	public JRBand getPageFooter()
	{
		return pageFooter;
	}

	@Override
	public JRBand getPageHeader()
	{
		return null;
	}

	@Override
	public int getPageHeight()
	{
		return parentReport.getPageHeight();
	}

	@Override
	public int getPageWidth()
	{
		return fillContext.getComponentElement().getWidth();
	}

	@Override
	public JRParameter[] getParameters()
	{
		return mainDataset.getParameters();
	}

	@Override
	public PrintOrderEnum getPrintOrderValue()
	{
		return PrintOrderEnum.VERTICAL;
	}

	@Override
	public RunDirectionEnum getColumnDirection()
	{
		return RunDirectionEnum.LTR;
	}

	@Override
	public String getProperty(String name)
	{
		return mainDataset.getPropertiesMap().getProperty(name);	
	}

	@Override
	public String[] getPropertyNames()
	{
		return mainDataset.getPropertiesMap().getPropertyNames();
	}

	@Override
	public JRQuery getQuery()
	{
		return mainDataset.getQuery();
	}

	@Override
	public String getResourceBundle()
	{
		return mainDataset.getResourceBundle();
	}

	@Override
	public int getRightMargin()
	{
		return 0;
	}

	@Override
	public String getScriptletClass()
	{
		return mainDataset.getScriptletClass();
	}

	@Override
	public JRScriptlet[] getScriptlets()
	{
		return mainDataset.getScriptlets();
	}

	@Override
	public JRSortField[] getSortFields()
	{
		return mainDataset.getSortFields();
	}

	@Override
	public JRStyle[] getStyles()
	{
		return parentReport.getStyles();
	}

	@Override
	public JRBand getSummary()
	{
		return summary;
	}

	@Override
	public JRReportTemplate[] getTemplates()
	{
		// the parent report's templates are always used for the subreport
		return null;
	}

	@Override
	public JRBand getTitle()
	{
		return title;
	}

	@Override
	public int getTopMargin()
	{
		return 0;
	}

	@Override
	public JRVariable[] getVariables()
	{
		return mainDataset.getVariables();
	}

	protected JRVariable getVariable(String name)
	{
		JRVariable found = null;
		for (JRVariable var : getVariables())
		{
			if (name.equals(var.getName()))
			{
				found = var;
				break;
			}
		}
		return found;
	}
	
	@Override
	public WhenNoDataTypeEnum getWhenNoDataTypeValue()
	{
		WhenNoDataTypeTableEnum whenNoDataType = table.getWhenNoDataType(); 
		if (whenNoDataType == null)
		{
			whenNoDataType = 
				WhenNoDataTypeTableEnum.getByName(
					propertiesUtil.getProperty(
						fillContext.getFillDataset(),
						TableComponent.CONFIG_PROPERTY_WHEN_NO_DATA_TYPE
						)
					);
		}

		switch (whenNoDataType)
		{
			case ALL_SECTIONS_NO_DETAIL :
			{
				return WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL;
			}
			case BLANK :
			default :
			{
				return WhenNoDataTypeEnum.NO_PAGES;
			}
		}
	}

	@Override
	public SectionTypeEnum getSectionType()
	{
		return SectionTypeEnum.BAND;
	}

	@Override
	public WhenResourceMissingTypeEnum getWhenResourceMissingTypeValue()
	{
		return mainDataset.getWhenResourceMissingTypeValue();
	}

	@Override
	public boolean isFloatColumnFooter()
	{
		return true;
	}

	@Override
	public boolean isIgnorePagination()
	{
		return false;
	}

	@Override
	public boolean isSummaryNewPage()
	{
		return false;
	}

	@Override
	public boolean isSummaryWithPageHeaderAndFooter()
	{
		return false;
	}

	@Override
	public boolean isTitleNewPage()
	{
		return false;
	}

	@Override
	public void removeProperty(String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setProperty(String name, String value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWhenNoDataType(WhenNoDataTypeEnum whenNoDataType)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWhenResourceMissingType(
			WhenResourceMissingTypeEnum whenResourceMissingType)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public JRStyle getDefaultStyle()
	{
		return parentReport.getDefaultStyle();
	}

	@Override
	public StyleResolver getStyleResolver()
	{
		return parentReport.getStyleResolver();
	}

	@Override
	public JRPropertiesHolder getParentProperties()
	{
		return null;
	}

	@Override
	public JRPropertiesMap getPropertiesMap()
	{
		return mainDataset.getPropertiesMap();
	}

	@Override
	public boolean hasProperties()
	{
		return mainDataset.hasProperties();
	}

	@Override
	public DatasetPropertyExpression[] getPropertyExpressions()
	{
		return mainDataset.getPropertyExpressions();
	}

	@Override
	public UUID getUUID()
	{
		return mainDataset.getUUID();
	}

	public void setTableInstanceIndex(int instanceIndex)
	{
		for (TableIndexProperties properties : tableIndexProperties)
		{
			properties.setTableInstanceIndex(instanceIndex);
		}
	}

	// creates a JRPropertiesMap instance that is used as base properties for table elements.
	// on each table instantiation, a property in the base instance changes its value and the
	// value propagates to the print elements created by the table.
	protected static class TableIndexProperties
	{
		private final String propertyName;
		private final String classFixedPart;
		private JRPropertiesMap propertiesMap;
		
		public TableIndexProperties(String propertyName, String classFixedPart)
		{
			this.propertyName = propertyName;
			this.classFixedPart = classFixedPart;
			
			this.propertiesMap = new JRPropertiesMap();
		}

		public JRPropertiesMap getPropertiesMap()
		{
			return propertiesMap;
		}

		public void setTableInstanceIndex(int instanceIndex)
		{
			propertiesMap.setProperty(propertyName, classFixedPart + instanceIndex);
		}
	}
}
