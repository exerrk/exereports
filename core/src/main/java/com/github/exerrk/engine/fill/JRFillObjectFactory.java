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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.exerrk.charts.JRAreaPlot;
import com.github.exerrk.charts.JRBar3DPlot;
import com.github.exerrk.charts.JRBarPlot;
import com.github.exerrk.charts.JRBubblePlot;
import com.github.exerrk.charts.JRCandlestickPlot;
import com.github.exerrk.charts.JRCategoryDataset;
import com.github.exerrk.charts.JRCategorySeries;
import com.github.exerrk.charts.JRChartAxis;
import com.github.exerrk.charts.JRGanttDataset;
import com.github.exerrk.charts.JRGanttSeries;
import com.github.exerrk.charts.JRHighLowDataset;
import com.github.exerrk.charts.JRHighLowPlot;
import com.github.exerrk.charts.JRLinePlot;
import com.github.exerrk.charts.JRMeterPlot;
import com.github.exerrk.charts.JRMultiAxisPlot;
import com.github.exerrk.charts.JRPie3DPlot;
import com.github.exerrk.charts.JRPieDataset;
import com.github.exerrk.charts.JRPiePlot;
import com.github.exerrk.charts.JRPieSeries;
import com.github.exerrk.charts.JRScatterPlot;
import com.github.exerrk.charts.JRThermometerPlot;
import com.github.exerrk.charts.JRTimePeriodDataset;
import com.github.exerrk.charts.JRTimePeriodSeries;
import com.github.exerrk.charts.JRTimeSeries;
import com.github.exerrk.charts.JRTimeSeriesDataset;
import com.github.exerrk.charts.JRTimeSeriesPlot;
import com.github.exerrk.charts.JRValueDataset;
import com.github.exerrk.charts.JRXyDataset;
import com.github.exerrk.charts.JRXySeries;
import com.github.exerrk.charts.JRXyzDataset;
import com.github.exerrk.charts.JRXyzSeries;
import com.github.exerrk.charts.fill.JRFillAreaPlot;
import com.github.exerrk.charts.fill.JRFillBar3DPlot;
import com.github.exerrk.charts.fill.JRFillBarPlot;
import com.github.exerrk.charts.fill.JRFillBubblePlot;
import com.github.exerrk.charts.fill.JRFillCandlestickPlot;
import com.github.exerrk.charts.fill.JRFillCategoryDataset;
import com.github.exerrk.charts.fill.JRFillCategorySeries;
import com.github.exerrk.charts.fill.JRFillChartAxis;
import com.github.exerrk.charts.fill.JRFillGanttDataset;
import com.github.exerrk.charts.fill.JRFillGanttSeries;
import com.github.exerrk.charts.fill.JRFillHighLowDataset;
import com.github.exerrk.charts.fill.JRFillHighLowPlot;
import com.github.exerrk.charts.fill.JRFillLinePlot;
import com.github.exerrk.charts.fill.JRFillMeterPlot;
import com.github.exerrk.charts.fill.JRFillMultiAxisPlot;
import com.github.exerrk.charts.fill.JRFillPie3DPlot;
import com.github.exerrk.charts.fill.JRFillPieDataset;
import com.github.exerrk.charts.fill.JRFillPiePlot;
import com.github.exerrk.charts.fill.JRFillPieSeries;
import com.github.exerrk.charts.fill.JRFillScatterPlot;
import com.github.exerrk.charts.fill.JRFillThermometerPlot;
import com.github.exerrk.charts.fill.JRFillTimePeriodDataset;
import com.github.exerrk.charts.fill.JRFillTimePeriodSeries;
import com.github.exerrk.charts.fill.JRFillTimeSeries;
import com.github.exerrk.charts.fill.JRFillTimeSeriesDataset;
import com.github.exerrk.charts.fill.JRFillTimeSeriesPlot;
import com.github.exerrk.charts.fill.JRFillValueDataset;
import com.github.exerrk.charts.fill.JRFillXyDataset;
import com.github.exerrk.charts.fill.JRFillXySeries;
import com.github.exerrk.charts.fill.JRFillXyzDataset;
import com.github.exerrk.charts.fill.JRFillXyzSeries;
import com.github.exerrk.crosstabs.JRCrosstab;
import com.github.exerrk.crosstabs.JRCrosstabDataset;
import com.github.exerrk.crosstabs.JRCrosstabParameter;
import com.github.exerrk.crosstabs.fill.JRFillCrosstabParameter;
import com.github.exerrk.engine.ExpressionReturnValue;
import com.github.exerrk.engine.JRAbstractObjectFactory;
import com.github.exerrk.engine.JRBand;
import com.github.exerrk.engine.JRBreak;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRConditionalStyle;
import com.github.exerrk.engine.JRDataset;
import com.github.exerrk.engine.JRDatasetRun;
import com.github.exerrk.engine.JRDefaultStyleProvider;
import com.github.exerrk.engine.JRElementGroup;
import com.github.exerrk.engine.JREllipse;
import com.github.exerrk.engine.JRExpression;
import com.github.exerrk.engine.JRField;
import com.github.exerrk.engine.JRFrame;
import com.github.exerrk.engine.JRGenericElement;
import com.github.exerrk.engine.JRGroup;
import com.github.exerrk.engine.JRImage;
import com.github.exerrk.engine.JRLine;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JRRectangle;
import com.github.exerrk.engine.JRReportTemplate;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.JRSection;
import com.github.exerrk.engine.JRStaticText;
import com.github.exerrk.engine.JRStyle;
import com.github.exerrk.engine.JRStyleContainer;
import com.github.exerrk.engine.JRStyleSetter;
import com.github.exerrk.engine.JRSubreport;
import com.github.exerrk.engine.JRSubreportReturnValue;
import com.github.exerrk.engine.JRTextField;
import com.github.exerrk.engine.JRVariable;
import com.github.exerrk.engine.VariableReturnValue;
import com.github.exerrk.engine.analytics.dataset.FillMultiAxisData;
import com.github.exerrk.engine.analytics.dataset.MultiAxisData;
import com.github.exerrk.engine.base.JRBaseConditionalStyle;
import com.github.exerrk.engine.base.JRBaseStyle;


/**
 * A factory used to instantiate fill objects based on compiled report objects.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRFillObjectFactory extends JRAbstractObjectFactory
{

	private static final Log log = LogFactory.getLog(JRFillObjectFactory.class);

	public static final String EXCEPTION_MESSAGE_KEY_UNRESOLVED_STYLE = "fill.object.factory.unresolved.style";
	public static final String EXCEPTION_MESSAGE_KEY_STYLE_NOT_FOUND = "fill.object.factory.style.not.found";
	
	/**
	 *
	 */
	protected JRBaseFiller filler;
	protected BaseReportFiller reportFiller;
	private JRFillExpressionEvaluator evaluator;

	private JRFillObjectFactory parentFiller;
	
//	private JRFont defaultFont;

	private List<JRFillElementDataset> elementDatasets = new ArrayList<JRFillElementDataset>();
	private Map<String,List<JRFillElementDataset>> elementDatasetMap = new HashMap<String,List<JRFillElementDataset>>();
	
	private LinkedList<List<JRFillDatasetRun>> trackedDatasetRunsStack = new LinkedList<List<JRFillDatasetRun>>();
	
	private Map<String,List<JRStyleSetter>> delayedStyleSettersByName = new HashMap<String,List<JRStyleSetter>>();
	
	protected static class StylesList
	{
		private final List<JRStyle> styles = new ArrayList<JRStyle>();
		private final Map<String,Integer> stylesIdx = new HashMap<String,Integer>();
		
		public boolean containsStyle(String name)
		{
			return stylesIdx.containsKey(name);
		}
		
		public JRStyle getStyle(String name)
		{
			Integer idx = stylesIdx.get(name);
			return idx == null ? null : styles.get(idx.intValue());
		}
		
		public void addStyle(JRStyle style)
		{
			styles.add(style);
			stylesIdx.put(style.getName(), Integer.valueOf(styles.size() - 1));
		}
		
		public void renamed(String oldName, String newName)
		{
			Integer idx = stylesIdx.remove(oldName);
			stylesIdx.put(newName, idx);
		}
	}
	
	private Set<JRStyle> originalStyleList;
	private StylesList stylesMap = new StylesList();


	/**
	 *
	 */
	protected JRFillObjectFactory(JRBaseFiller filler)
	{
		this(filler, filler.calculator);
	}


	/**
	 *
	 */
	public JRFillObjectFactory(JRBaseFiller filler, JRFillExpressionEvaluator expressionEvaluator)
	{
		this.filler = filler;
		this.reportFiller = filler;
		this.evaluator = expressionEvaluator;
	}

	
	public JRFillObjectFactory(JRFillObjectFactory parent, JRFillExpressionEvaluator expressionEvaluator)
	{
		this.parentFiller = parent;
		this.filler = parent.filler;
		this.reportFiller = parent.reportFiller;
		this.evaluator = expressionEvaluator;
	}

	
	public JRFillObjectFactory(BaseReportFiller reportFiller)
	{
		this.reportFiller = reportFiller;
		this.evaluator = reportFiller.calculator;
	}


	/**
	 * Returns the expression evaluator which is to be used by objects
	 * created by this factory.
	 * 
	 * @return the expression evaluator associated with this factory
	 */
	public JRFillExpressionEvaluator getExpressionEvaluator()
	{
		return evaluator;
	}

	/**
	 *
	 */
	protected JRFillChartDataset[] getDatasets()
	{
		return elementDatasets.toArray(new JRFillChartDataset[elementDatasets.size()]);
	}


	protected JRFillElementDataset[] getElementDatasets(JRDataset dataset)
	{
		JRFillElementDataset[] elementDatasetsArray;
		List<JRFillElementDataset> elementDatasetsList;
		if (dataset.isMainDataset())
		{
			elementDatasetsList = elementDatasets;
		}
		else
		{
			elementDatasetsList = elementDatasetMap.get(dataset.getName());
		}

		if (elementDatasetsList == null || elementDatasetsList.size() == 0)
		{
			elementDatasetsArray = new JRFillElementDataset[0];
		}
		else
		{
			elementDatasetsArray = new JRFillElementDataset[elementDatasetsList.size()];
			elementDatasetsList.toArray(elementDatasetsArray);
		}

		return elementDatasetsArray;
	}


	protected void registerDelayedStyleSetter(JRStyleSetter delayedSetter, String styleName)
	{
		if (parentFiller == null)
		{
			List<JRStyleSetter> setters = delayedStyleSettersByName.get(styleName);
			if (setters == null)
			{
				setters = new ArrayList<JRStyleSetter>();
				delayedStyleSettersByName.put(styleName, setters);
			}
			
			setters.add(delayedSetter);
		}
		else
		{
			parentFiller.registerDelayedStyleSetter(delayedSetter, styleName);
		}
	}

	public void registerDelayedStyleSetter(JRStyleSetter delayedSetter, JRStyleContainer styleContainer)
	{
		JRStyle style = styleContainer.getStyle();
		String nameReference = styleContainer.getStyleNameReference();
		if (style != null)
		{
			registerDelayedStyleSetter(delayedSetter, style.getName());
		}
		else if (nameReference != null)
		{
			registerDelayedStyleSetter(delayedSetter, nameReference);
		}
	}
	
	@Override
	public JRBaseStyle getStyle(JRStyle style)
	{
		JRBaseStyle fillStyle = null;

		if (style != null)
		{
			fillStyle = (JRBaseStyle) get(style);
			if (fillStyle == null)
			{
				fillStyle = new JRBaseStyle(style, this);
				
				// deduplicate to previously created identical instances
				fillStyle = filler.fillContext.deduplicate(fillStyle);
				
				put(style, fillStyle);
				
				if (originalStyleList != null && originalStyleList.contains(style))
				{
					renameExistingStyle(style.getName());
					stylesMap.addStyle(style);
				}				
			}
		}

		return fillStyle;
	}

	protected void renameExistingStyle(String name)
	{
		JRStyle originalStyle = stylesMap.getStyle(name);
		if (originalStyle != null)
		{
			//found a previous external style with the same name
			//renaming the previous style
			JRBaseStyle style = (JRBaseStyle) get(originalStyle);
			
			String newName;
			int suf = 1;
			do
			{
				newName = name + suf;
				++suf;
			}
			while(stylesMap.containsStyle(newName));
			
			style.rename(newName);
			stylesMap.renamed(name, newName);
		}
	}


	@Override
	public void setStyle(JRStyleSetter setter, JRStyleContainer styleContainer)
	{
		JRStyle style = styleContainer.getStyle();
		String nameReference = styleContainer.getStyleNameReference();
		if (style != null)
		{
			JRStyle newStyle = getStyle(style);
			setter.setStyle(newStyle);
		}
		else if (nameReference != null)
		{
			JRStyle originalStyle = stylesMap.getStyle(nameReference);
			if (originalStyle == null)
			{
				throw 
					new JRRuntimeException(
						EXCEPTION_MESSAGE_KEY_UNRESOLVED_STYLE,  
						new Object[]{nameReference} 
						);
			}
			
			JRStyle externalStyle = (JRStyle) get(originalStyle);
			setter.setStyle(externalStyle);
		}
	}


	/**
	 *
	 */
	protected JRFillParameter getParameter(JRParameter parameter)
	{
		JRFillParameter fillParameter = null;

		if (parameter != null)
		{
			fillParameter = (JRFillParameter)get(parameter);
			if (fillParameter == null)
			{
				fillParameter = new JRFillParameter(parameter, this);
			}
		}

		return fillParameter;
	}


	/**
	 *
	 */
	protected JRFillField getField(JRField field)
	{
		JRFillField fillField = null;

		if (field != null)
		{
			fillField = (JRFillField)get(field);
			if (fillField == null)
			{
				fillField = new JRFillField(field, this);
			}
		}

		return fillField;
	}


	/**
	 *
	 */
	public JRFillVariable getVariable(JRVariable variable)
	{
		JRFillVariable fillVariable = null;

		if (variable != null)
		{
			fillVariable = (JRFillVariable)get(variable);
			if (fillVariable == null)
			{
				fillVariable = new JRFillVariable(variable, this);
			}
		}

		return fillVariable;
	}


	/**
	 *
	 */
	public JRFillGroup getGroup(JRGroup group)
	{
		JRFillGroup fillGroup = null;

		if (group != null)
		{
			fillGroup = (JRFillGroup)get(group);
			if (fillGroup == null)
			{
				fillGroup = new JRFillGroup(group, this);
			}
		}

		return fillGroup;
	}


	/**
	 *
	 */
	protected JRFillSection getSection(JRSection section)
	{
		JRFillSection fillSection = null;

		if (section == null)
		{
			fillSection = filler.missingFillSection;
		}
		else
		{
			fillSection = (JRFillSection)get(section);
			if (fillSection == null)
			{
				fillSection = new JRFillSection(filler, section, this);
			}
		}

		return fillSection;
	}


	/**
	 *
	 */
	protected JRFillBand getBand(JRBand band)
	{
		JRFillBand fillBand = null;

		if (band == null)
		{
			fillBand = filler.missingFillBand;
		}
		else
		{
			fillBand = (JRFillBand)get(band);
			if (fillBand == null)
			{
				fillBand = new JRFillBand(filler, band, this);
			}
		}

		return fillBand;
	}


	@Override
	public void visitElementGroup(JRElementGroup elementGroup)
	{
		JRFillElementGroup fillElementGroup = null;

		if (elementGroup != null)
		{
			fillElementGroup = (JRFillElementGroup)get(elementGroup);
			if (fillElementGroup == null)
			{
				fillElementGroup = new JRFillElementGroup(elementGroup, this);
			}
		}

		setVisitResult(fillElementGroup);
	}


	@Override
	public void visitBreak(JRBreak breakElement)
	{
		JRFillBreak fillBreak = null;

		if (breakElement != null)
		{
			fillBreak = (JRFillBreak)get(breakElement);
			if (fillBreak == null)
			{
				fillBreak = new JRFillBreak(filler, breakElement, this);
			}
		}

		setVisitResult(fillBreak);
	}


	@Override
	public void visitLine(JRLine line)
	{
		JRFillLine fillLine = null;

		if (line != null)
		{
			fillLine = (JRFillLine)get(line);
			if (fillLine == null)
			{
				fillLine = new JRFillLine(filler, line, this);
			}
		}

		setVisitResult(fillLine);
	}


	@Override
	public void visitRectangle(JRRectangle rectangle)
	{
		JRFillRectangle fillRectangle = null;

		if (rectangle != null)
		{
			fillRectangle = (JRFillRectangle)get(rectangle);
			if (fillRectangle == null)
			{
				fillRectangle = new JRFillRectangle(filler, rectangle, this);
			}
		}

		setVisitResult(fillRectangle);
	}


	@Override
	public void visitEllipse(JREllipse ellipse)
	{
		JRFillEllipse fillEllipse = null;

		if (ellipse != null)
		{
			fillEllipse = (JRFillEllipse)get(ellipse);
			if (fillEllipse == null)
			{
				fillEllipse = new JRFillEllipse(filler, ellipse, this);
			}
		}

		setVisitResult(fillEllipse);
	}


	@Override
	public void visitImage(JRImage image)
	{
		JRFillImage fillImage = null;

		if (image != null)
		{
			fillImage = (JRFillImage)get(image);
			if (fillImage == null)
			{
				fillImage = new JRFillImage(filler, image, this);
			}
		}

		setVisitResult(fillImage);
	}


	@Override
	public void visitStaticText(JRStaticText staticText)
	{
		JRFillStaticText fillStaticText = null;

		if (staticText != null)
		{
			fillStaticText = (JRFillStaticText)get(staticText);
			if (fillStaticText == null)
			{
				fillStaticText = new JRFillStaticText(filler, staticText, this);
			}
		}

		setVisitResult(fillStaticText);
	}


	@Override
	public void visitTextField(JRTextField textField)
	{
		JRFillTextField fillTextField = null;

		if (textField != null)
		{
			fillTextField = (JRFillTextField)get(textField);
			if (fillTextField == null)
			{
				fillTextField = new JRFillTextField(filler, textField, this);
			}
		}

		setVisitResult(fillTextField);
	}


	@Override
	public void visitSubreport(JRSubreport subreport)
	{
		JRFillSubreport fillSubreport = null;

		if (subreport != null)
		{
			fillSubreport = (JRFillSubreport)get(subreport);
			if (fillSubreport == null)
			{
				fillSubreport = new JRFillSubreport(filler, subreport, this);
			}
		}

		setVisitResult(fillSubreport);
	}


	@Override
	public void visitChart(JRChart chart)
	{
		JRFillChart fillChart = null;

		if (chart != null)
		{
			fillChart = (JRFillChart)get(chart);
			if (fillChart == null)
			{
				fillChart = new JRFillChart(filler, chart, this);
			}
		}

		setVisitResult(fillChart);
	}


	@Override
	public JRPieDataset getPieDataset(JRPieDataset pieDataset)
	{
		JRFillPieDataset fillPieDataset = null;

		if (pieDataset != null)
		{
			fillPieDataset = (JRFillPieDataset)get(pieDataset);
			if (fillPieDataset == null)
			{
				fillPieDataset = new JRFillPieDataset(pieDataset, this);
				registerElementDataset(fillPieDataset);
			}
		}

		return fillPieDataset;
	}


	@Override
	public JRPiePlot getPiePlot(JRPiePlot piePlot)
	{
		JRFillPiePlot fillPiePlot = null;

		if (piePlot != null)
		{
			fillPiePlot = (JRFillPiePlot)get(piePlot);
			if (fillPiePlot == null)
			{
				fillPiePlot = new JRFillPiePlot(piePlot, this);
			}
		}

		return fillPiePlot;
	}


	@Override
	public JRPie3DPlot getPie3DPlot(JRPie3DPlot pie3DPlot)
	{
		JRFillPie3DPlot fillPie3DPlot = null;

		if (pie3DPlot != null)
		{
			fillPie3DPlot = (JRFillPie3DPlot)get(pie3DPlot);
			if (fillPie3DPlot == null)
			{
				fillPie3DPlot = new JRFillPie3DPlot(pie3DPlot, this);
			}
		}

		return fillPie3DPlot;
	}


	@Override
	public JRCategoryDataset getCategoryDataset(JRCategoryDataset categoryDataset)
	{
		JRFillCategoryDataset fillCategoryDataset = null;

		if (categoryDataset != null)
		{
			fillCategoryDataset = (JRFillCategoryDataset)get(categoryDataset);
			if (fillCategoryDataset == null)
			{
				fillCategoryDataset = new JRFillCategoryDataset(categoryDataset, this);
				registerElementDataset(fillCategoryDataset);
			}
		}

		return fillCategoryDataset;
	}

	@Override
	public JRXyzDataset getXyzDataset( JRXyzDataset xyzDataset ){
		JRFillXyzDataset fillXyzDataset = null;
		if( xyzDataset != null ){
			fillXyzDataset = (JRFillXyzDataset)get( xyzDataset );
			if( fillXyzDataset == null ){
				fillXyzDataset = new JRFillXyzDataset( xyzDataset, this );
				registerElementDataset(fillXyzDataset);
			}
		}

		return fillXyzDataset;

	}


	/**
	 *
	 */
	public JRXyDataset getXyDataset(JRXyDataset xyDataset)
	{
		JRFillXyDataset fillXyDataset = null;

		if (xyDataset != null)
		{
			fillXyDataset = (JRFillXyDataset)get(xyDataset);
			if (fillXyDataset == null)
			{
				fillXyDataset = new JRFillXyDataset(xyDataset, this);
				registerElementDataset(fillXyDataset);
			}
		}

		return fillXyDataset;
	}


	@Override
	public JRTimeSeriesDataset getTimeSeriesDataset( JRTimeSeriesDataset timeSeriesDataset ){
		JRFillTimeSeriesDataset fillTimeSeriesDataset = null;

		if( timeSeriesDataset != null ){

			fillTimeSeriesDataset = (JRFillTimeSeriesDataset)get( timeSeriesDataset );

			if( fillTimeSeriesDataset == null ){
				fillTimeSeriesDataset = new JRFillTimeSeriesDataset( timeSeriesDataset, this );
				registerElementDataset(fillTimeSeriesDataset);
			}
		}

		return fillTimeSeriesDataset;
	}

	@Override
	public JRTimePeriodDataset getTimePeriodDataset( JRTimePeriodDataset timePeriodDataset ){
		JRFillTimePeriodDataset fillTimePeriodDataset = null;
		if( timePeriodDataset != null ){
			fillTimePeriodDataset = (JRFillTimePeriodDataset)get( timePeriodDataset );
			if( fillTimePeriodDataset == null ){
				fillTimePeriodDataset = new JRFillTimePeriodDataset( timePeriodDataset, this );
				registerElementDataset(fillTimePeriodDataset);
			}
		}
		return fillTimePeriodDataset;
	}
	
	/**
	 * 
	 */
	public JRGanttDataset getGanttDataset(JRGanttDataset ganttDataset)
	{
		JRFillGanttDataset fillGanttDataset = null;
		
		if (ganttDataset != null)
		{
			fillGanttDataset = (JRFillGanttDataset)get(ganttDataset);
			if (fillGanttDataset == null)
			{
				fillGanttDataset = new JRFillGanttDataset(ganttDataset, this);
				registerElementDataset(fillGanttDataset);
			}
		}
		
		return fillGanttDataset;
	}
	
	@Override
	public JRPieSeries getPieSeries(JRPieSeries pieSeries)
	{
		JRFillPieSeries fillPieSeries = null;

		if (pieSeries != null)
		{
			fillPieSeries = (JRFillPieSeries)get(pieSeries);
			if (fillPieSeries == null)
			{
				fillPieSeries = new JRFillPieSeries(pieSeries, this);
			}
		}

		return fillPieSeries;
	}


	@Override
	public JRCategorySeries getCategorySeries(JRCategorySeries categorySeries)
	{
		JRFillCategorySeries fillCategorySeries = null;

		if (categorySeries != null)
		{
			fillCategorySeries = (JRFillCategorySeries)get(categorySeries);
			if (fillCategorySeries == null)
			{
				fillCategorySeries = new JRFillCategorySeries(categorySeries, this);
			}
		}

		return fillCategorySeries;
	}


	@Override
	public JRXyzSeries getXyzSeries( JRXyzSeries xyzSeries ){
		JRFillXyzSeries fillXyzSeries = null;

		if( xyzSeries != null ){
			fillXyzSeries = (JRFillXyzSeries)get( xyzSeries );

			if( fillXyzSeries == null ){
				fillXyzSeries = new JRFillXyzSeries( xyzSeries, this );
			}
		}

		return fillXyzSeries;
	}


	/**
	 *
	 */
	public JRXySeries getXySeries(JRXySeries xySeries)
	{
		JRFillXySeries fillXySeries = null;

		if (xySeries != null)
		{
			fillXySeries = (JRFillXySeries)get(xySeries);
			if (fillXySeries == null)
			{
				fillXySeries = new JRFillXySeries(xySeries, this);
			}
		}

		return fillXySeries;
	}
	
	
	/**
	 * 
	 */
	public JRGanttSeries getGanttSeries(JRGanttSeries ganttSeries)
	{
		JRFillGanttSeries fillGanttSeries = null;
		
		if (ganttSeries != null)
		{
			fillGanttSeries = (JRFillGanttSeries)get(ganttSeries);
			if (fillGanttSeries == null)
			{
				fillGanttSeries = new JRFillGanttSeries(ganttSeries, this);
			}
		}
		
		return fillGanttSeries;
	}
	
	
	@Override
	public JRBarPlot getBarPlot(JRBarPlot barPlot)
	{
		JRFillBarPlot fillBarPlot = null;

		if (barPlot != null)
		{
			fillBarPlot = (JRFillBarPlot)get(barPlot);
			if (fillBarPlot == null)
			{
				fillBarPlot = new JRFillBarPlot(barPlot, this);
			}
		}

		return fillBarPlot;
	}


	@Override
	public JRTimeSeries getTimeSeries(JRTimeSeries timeSeries)
	{
		JRFillTimeSeries fillTimeSeries = null;

		if (timeSeries != null)
		{
			fillTimeSeries = (JRFillTimeSeries)get(timeSeries);
			if (fillTimeSeries == null)
			{
				fillTimeSeries = new JRFillTimeSeries(timeSeries, this);
			}
		}

		return fillTimeSeries;
	}

	@Override
	public JRTimePeriodSeries getTimePeriodSeries( JRTimePeriodSeries timePeriodSeries ){
		JRFillTimePeriodSeries fillTimePeriodSeries = null;
		if( timePeriodSeries != null ){
			fillTimePeriodSeries = (JRFillTimePeriodSeries)get( timePeriodSeries );
			if( fillTimePeriodSeries == null ){
				fillTimePeriodSeries = new JRFillTimePeriodSeries( timePeriodSeries, this );
			}
		}

		return fillTimePeriodSeries;
	}


	@Override
	public JRBar3DPlot getBar3DPlot(JRBar3DPlot barPlot) {
		JRFillBar3DPlot fillBarPlot = null;

		if (barPlot != null){
			fillBarPlot = (JRFillBar3DPlot)get(barPlot);
			if (fillBarPlot == null){
				fillBarPlot = new JRFillBar3DPlot(barPlot, this);
			}
		}

		return fillBarPlot;
	}


	@Override
	public JRLinePlot getLinePlot(JRLinePlot linePlot) {
		JRFillLinePlot fillLinePlot = null;

		if (linePlot != null){
			fillLinePlot = (JRFillLinePlot)get(linePlot);
			if (fillLinePlot == null){
				fillLinePlot = new JRFillLinePlot(linePlot, this);
			}
		}

		return fillLinePlot;
	}


	/**
	 *
	 */
	public JRScatterPlot getScatterPlot(JRScatterPlot scatterPlot) {
		JRFillScatterPlot fillScatterPlot = null;

		if (scatterPlot != null){
			fillScatterPlot = (JRFillScatterPlot)get(scatterPlot);
			if (fillScatterPlot == null){
				fillScatterPlot = new JRFillScatterPlot(scatterPlot, this);
			}
		}

		return fillScatterPlot;
	}


	@Override
	public JRAreaPlot getAreaPlot(JRAreaPlot areaPlot) {
		JRFillAreaPlot fillAreaPlot = null;

		if (areaPlot != null)
		{
			fillAreaPlot = (JRFillAreaPlot)get(areaPlot);
			if (fillAreaPlot == null)
			{
				fillAreaPlot = new JRFillAreaPlot(areaPlot, this);
			}
		}

		return fillAreaPlot;
	}


	/* (non-Javadoc)
	 * @see com.github.exerrk.engine.JRAbstractObjectFactory#getBubblePlot(com.github.exerrk.charts.JRBubblePlot)
	 */
	@Override
	public JRBubblePlot getBubblePlot(JRBubblePlot bubblePlot) {
		JRFillBubblePlot fillBubblePlot = null;

		if (bubblePlot != null)
		{
			fillBubblePlot = (JRFillBubblePlot)get(bubblePlot);
			if (fillBubblePlot == null)
			{
				fillBubblePlot = new JRFillBubblePlot(bubblePlot, this);
			}
		}

		return fillBubblePlot;
	}


	/**
	 *
	 */
	public JRHighLowDataset getHighLowDataset(JRHighLowDataset highLowDataset)
	{
		JRFillHighLowDataset fillHighLowDataset = null;

		if (highLowDataset != null)
		{
			fillHighLowDataset = (JRFillHighLowDataset)get(highLowDataset);
			if (fillHighLowDataset == null)
			{
				fillHighLowDataset = new JRFillHighLowDataset(highLowDataset, this);
				registerElementDataset(fillHighLowDataset);
			}
		}

		return fillHighLowDataset;
	}


	/**
	 *
	 */
	public JRHighLowPlot getHighLowPlot(JRHighLowPlot highLowPlot) {
		JRFillHighLowPlot fillHighLowPlot = null;

		if (highLowPlot != null){
			fillHighLowPlot = (JRFillHighLowPlot)get(highLowPlot);
			if (fillHighLowPlot == null){
				fillHighLowPlot = new JRFillHighLowPlot(highLowPlot, this);
			}
		}

		return fillHighLowPlot;
	}


	@Override
	public JRCandlestickPlot getCandlestickPlot(JRCandlestickPlot candlestickPlot)
	{
		JRFillCandlestickPlot fillCandlestickPlot = null;

		if (candlestickPlot != null){
			fillCandlestickPlot = (JRFillCandlestickPlot)get(candlestickPlot);
			if (fillCandlestickPlot == null){
				fillCandlestickPlot = new JRFillCandlestickPlot(candlestickPlot, this);
			}
		}

		return fillCandlestickPlot;
	}



	public JRTimeSeriesPlot getTimeSeriesPlot( JRTimeSeriesPlot plot ){
		JRFillTimeSeriesPlot fillPlot = null;
		if( plot != null ){
			fillPlot = (JRFillTimeSeriesPlot)get( plot );
			if( fillPlot == null ){
				fillPlot = new JRFillTimeSeriesPlot( plot, this );
			}
		}

		return fillPlot;

	}

	/**
	 *
	 */
	public JRValueDataset getValueDataset(JRValueDataset valueDataset)
	{
		JRFillValueDataset fillValueDataset = null;

		if (valueDataset != null)
		{
			fillValueDataset = (JRFillValueDataset)get(valueDataset);
			if (fillValueDataset == null)
			{
				fillValueDataset = new JRFillValueDataset(valueDataset, this);
				registerElementDataset(fillValueDataset);
			}
		}

		return fillValueDataset;
	}

	/**
	 *
	 */
	public JRMeterPlot getMeterPlot(JRMeterPlot meterPlot)
	{
		JRFillMeterPlot fillMeterPlot = null;

		if (meterPlot != null)
		{
			fillMeterPlot = (JRFillMeterPlot)get(meterPlot);
			if (fillMeterPlot == null)
			{
				fillMeterPlot = new JRFillMeterPlot(meterPlot, this);
			}
		}

		return fillMeterPlot;
	}

	/**
	 *
	 */
	public JRThermometerPlot getThermometerPlot(JRThermometerPlot thermometerPlot)
	{
		JRFillThermometerPlot fillThermometerPlot = null;

		if (thermometerPlot != null)
		{
			fillThermometerPlot = (JRFillThermometerPlot)get(thermometerPlot);
			if (fillThermometerPlot == null)
			{
				fillThermometerPlot = new JRFillThermometerPlot(thermometerPlot, this);
			}
		}

		return fillThermometerPlot;
	}


	/**
	 *
	 */
	public JRMultiAxisPlot getMultiAxisPlot(JRMultiAxisPlot multiAxisPlot)
	{
		JRFillMultiAxisPlot fillMultiAxisPlot = null;

		if (multiAxisPlot != null)
		{
			fillMultiAxisPlot = (JRFillMultiAxisPlot)get(multiAxisPlot);
			if (fillMultiAxisPlot == null)
			{
				fillMultiAxisPlot = new JRFillMultiAxisPlot(multiAxisPlot, this);
			}
		}

		return fillMultiAxisPlot;
	}


	protected JRFillVariableReturnValue getSubreportReturnValue(JRSubreportReturnValue returnValue)
	{
		JRFillVariableReturnValue fillReturnValue = null;

		if (returnValue != null)
		{
			fillReturnValue = (JRFillVariableReturnValue) get(returnValue);
			if (fillReturnValue == null)
			{
				fillReturnValue = new JRFillVariableReturnValue(returnValue, this, reportFiller);
			}
		}

		return fillReturnValue;
	}


	protected JRFillVariableReturnValue getReturnValue(VariableReturnValue returnValue)
	{
		JRFillVariableReturnValue fillReturnValue = null;

		if (returnValue != null)
		{
			fillReturnValue = (JRFillVariableReturnValue) get(returnValue);
			if (fillReturnValue == null)
			{
				fillReturnValue = new JRFillVariableReturnValue(returnValue, this, filler);
			}
		}

		return fillReturnValue;
	}


	protected JRFillExpressionReturnValue getReturnValue(ExpressionReturnValue returnValue)
	{
		JRFillExpressionReturnValue fillReturnValue = null;

		if (returnValue != null)
		{
			fillReturnValue = (JRFillExpressionReturnValue) get(returnValue);
			if (fillReturnValue == null)
			{
				fillReturnValue = new JRFillExpressionReturnValue(returnValue, this, filler);//FIXMERETURN passing the right filler? 
				//why two possible fillers here after all? if single filler, could remove getSubreportReturnValue() above
			}
		}

		return fillReturnValue;
	}


	@Override
	public void visitCrosstab(JRCrosstab crosstabElement)
	{
		JRFillCrosstab fillCrosstab = null;

		if (crosstabElement != null)
		{
			fillCrosstab = (JRFillCrosstab) get(crosstabElement);
			if (fillCrosstab == null)
			{
				fillCrosstab = new JRFillCrosstab(filler, crosstabElement, this);
			}
		}

		setVisitResult(fillCrosstab);
	}


	public JRFillCrosstab.JRFillCrosstabDataset getCrosstabDataset(JRCrosstabDataset dataset, JRFillCrosstab fillCrosstab)
	{
		JRFillCrosstab.JRFillCrosstabDataset fillDataset = null;

		if (dataset != null)
		{
			fillDataset = (JRFillCrosstab.JRFillCrosstabDataset)get(dataset);
			if (fillDataset == null)
			{
				fillDataset = fillCrosstab.new JRFillCrosstabDataset(dataset, this);
				registerElementDataset(fillDataset);
			}
		}

		return fillDataset;
	}


	public JRFillDataset getDataset(JRDataset dataset)
	{
		JRFillDataset fillDataset = null;

		if (dataset != null)
		{
			fillDataset = (JRFillDataset) get(dataset);
			if (fillDataset == null)
			{
				fillDataset = new JRFillDataset(reportFiller, dataset, this);
			}
		}

		return fillDataset;
	}


	/**
	 * Register an element dataset with the report filler.
	 * 
	 * <p>
	 * Registration of element datasets is required in order for the filler
	 * to increment the datasets when iterating through the datasource.
	 * 
	 * @param elementDataset the dataset to register
	 */
	public void registerElementDataset(JRFillElementDataset elementDataset)
	{
		List<JRFillElementDataset> elementDatasetsList;
		JRDatasetRun datasetRun = elementDataset.getDatasetRun();
		if (datasetRun == null)
		{
			elementDatasetsList = elementDatasets;
		}
		else
		{
			String datasetName = datasetRun.getDatasetName();
			elementDatasetsList = getElementDatasetsList(datasetName);
			
			registerDatasetRun((JRFillDatasetRun) datasetRun);
		}
		elementDatasetsList.add(elementDataset);
	}
	
	protected List<JRFillElementDataset> getElementDatasetsList(String datasetName)
	{
		if (parentFiller != null)
		{
			return parentFiller.getElementDatasetsList(datasetName);
		}
		
		List<JRFillElementDataset> elementDatasetsList = elementDatasetMap.get(datasetName);
		if (elementDatasetsList == null)
		{
			elementDatasetsList = new ArrayList<JRFillElementDataset>();
			elementDatasetMap.put(datasetName, elementDatasetsList);
		}
		return elementDatasetsList;
	}

	public void trackDatasetRuns()
	{
		if (log.isDebugEnabled())
		{
			log.debug("tracking dataset runs");
		}

		ArrayList<JRFillDatasetRun> trackedDatasets = new ArrayList<JRFillDatasetRun>(2);
		trackedDatasetRunsStack.push(trackedDatasets);
	}
	
	public void registerDatasetRun(JRFillDatasetRun datasetRun)
	{
		if (!trackedDatasetRunsStack.isEmpty())
		{
			if (log.isDebugEnabled())
			{
				log.debug("added tracked dataset run " + datasetRun);
			}
			
			trackedDatasetRunsStack.getFirst().add(datasetRun);
		}
	}
	
	public List<JRFillDatasetRun> getTrackedDatasetRuns()
	{
		if (log.isDebugEnabled())
		{
			log.debug("poping tracked dataset runs");
		}

		return trackedDatasetRunsStack.pop();
	}

	public JRFillDatasetRun getDatasetRun(JRDatasetRun datasetRun)
	{
		JRFillDatasetRun fillDatasetRun = null;

		if (datasetRun != null)
		{
			fillDatasetRun = (JRFillDatasetRun) get(datasetRun);
			if (fillDatasetRun == null)
			{
				fillDatasetRun = new JRFillDatasetRun(datasetRun, this);
			}
		}

		return fillDatasetRun;
	}


	public JRFillCrosstabParameter getCrosstabParameter(JRCrosstabParameter parameter)
	{
		JRFillCrosstabParameter fillParameter = null;

		if (parameter != null)
		{
			fillParameter = (JRFillCrosstabParameter) get(parameter);
			if (fillParameter == null)
			{
				fillParameter = new JRFillCrosstabParameter(parameter, this);
			}
		}

		return fillParameter;
	}


	@Override
	public void visitFrame(JRFrame frame)
	{
		Object fillFrame = null;
		// This is the only place where an object gets replaced in the factory map by something else,
		// and we can no longer make a precise cast when getting it.
		// The JRFillFrame object is replaced in the map by a JRFillFrameElements object.
		//JRFillFrame fillFrame = null;

		if (frame != null)
		{
			fillFrame = get(frame);
			//fillFrame = (JRFillFrame) get(frame);
			if (fillFrame == null)
			{
				fillFrame = new JRFillFrame(filler, frame, this);
			}
		}

		setVisitResult(fillFrame);
	}

	public BaseReportFiller getReportFiller()
	{
		return reportFiller;
	}

	/**
	 * Returns the current report filler.
	 * 
	 * @return the current report filler
	 */
	public JRBaseFiller getFiller()
	{
		return filler;
	}


	@Override
	public JRConditionalStyle getConditionalStyle(JRConditionalStyle conditionalStyle, JRStyle style)
	{
		JRBaseConditionalStyle baseConditionalStyle = null;
		if (conditionalStyle != null)
		{
			baseConditionalStyle = (JRBaseConditionalStyle) get(conditionalStyle);
			if (baseConditionalStyle == null) {
				baseConditionalStyle = new JRBaseConditionalStyle(conditionalStyle, style, this);
				put(conditionalStyle, baseConditionalStyle);
			}
		}
		return baseConditionalStyle;
	}


	@Override
	public JRExpression getExpression(JRExpression expression, boolean assignNotUsedId)
	{
		return expression;
	}


	public JRChartAxis getChartAxis(JRChartAxis axis)
	{
		JRFillChartAxis fillAxis = null;
		if (axis != null)
		{
			fillAxis = (JRFillChartAxis) get(axis);
			if (fillAxis == null)
			{
				fillAxis = new JRFillChartAxis(axis, this);
			}
		}
		return fillAxis;
	}


	public JRFillReportTemplate getReportTemplate(JRReportTemplate template)
	{
		JRFillReportTemplate fillTemplate = null;
		if (template != null)
		{
			fillTemplate = (JRFillReportTemplate) get(template);
			if (fillTemplate == null)
			{
				fillTemplate = new JRFillReportTemplate(template, filler, this);
			}
		}
		return fillTemplate;
	}
	
	public List<JRStyle> setStyles(List<JRStyle> styles)
	{
		originalStyleList = new HashSet<JRStyle>(styles);
		
		//filtering requested styles
		Set<JRStyle> requestedStyles = collectRequestedStyles(styles);
		
		//collect used styles
		Map<JRStyle,Object> usedStylesMap = new LinkedMap();
		Map<String,JRStyle> allStylesMap = new HashMap<String,JRStyle>();
		for (Iterator<JRStyle> it = styles.iterator(); it.hasNext();)
		{
			JRStyle style = it.next();
			if (requestedStyles.contains(style))
			{
				collectUsedStyles(style, usedStylesMap, allStylesMap);
			}
			allStylesMap.put(style.getName(), style);
		}
		
		List<JRStyle> includedStyles = new ArrayList<JRStyle>();
		for (Iterator<JRStyle> it = usedStylesMap.keySet().iterator(); it.hasNext();)
		{
			JRStyle style = it.next();
			JRStyle newStyle = getStyle(style);
			
			includedStyles.add(newStyle);
			if (requestedStyles.contains(style))
			{
				useDelayedStyle(newStyle);
			}			
		}
		
		checkUnresolvedReferences();
		
		return includedStyles;
	}

	protected Set<JRStyle> collectRequestedStyles(List<JRStyle> externalStyles)
	{
		Map<String,JRStyle> requestedStylesMap = new HashMap<String,JRStyle>();
		for (Iterator<JRStyle> it = externalStyles.iterator(); it.hasNext();)
		{
			JRStyle style = it.next();
			String name = style.getName();
			if (delayedStyleSettersByName.containsKey(name))
			{
				requestedStylesMap.put(name, style);
			}
		}
		
		return new HashSet<JRStyle>(requestedStylesMap.values());
	}

	protected void collectUsedStyles(JRStyle style, Map<JRStyle,Object> usedStylesMap, Map<String,JRStyle> allStylesMap)
	{
		if (!usedStylesMap.containsKey(style) && originalStyleList.contains(style))
		{
			JRStyle parent = style.getStyle();
			if (parent == null)
			{
				String parentName = style.getStyleNameReference();
				if (parentName != null)
				{
					parent = allStylesMap.get(parentName);
					if (parent == null)
					{
						throw 
							new JRRuntimeException(
								EXCEPTION_MESSAGE_KEY_STYLE_NOT_FOUND,  
								new Object[]{parentName} 
								);
					}
				}
			}
			
			if (parent != null)
			{
				collectUsedStyles(parent, usedStylesMap, allStylesMap);
			}
			
			usedStylesMap.put(style, null);
		}
	}
	
	protected void useDelayedStyle(JRStyle style)
	{
		List<JRStyleSetter> delayedSetters = delayedStyleSettersByName.remove(style.getName());
		if (delayedSetters != null)
		{
			for (Iterator<JRStyleSetter> it = delayedSetters.iterator(); it.hasNext();)
			{
				JRStyleSetter setter = it.next();
				setter.setStyle(style);
			}
		}
	}

	protected void checkUnresolvedReferences()
	{
		if (!delayedStyleSettersByName.isEmpty())
		{
			StringBuilder errorMsg = new StringBuilder();
			for (Iterator<String> it = delayedStyleSettersByName.keySet().iterator(); it.hasNext();)
			{
				String name = it.next();
				errorMsg.append(name);
				errorMsg.append(", ");
			}
			
			throw 
				new JRRuntimeException(
					EXCEPTION_MESSAGE_KEY_UNRESOLVED_STYLE,  
					new Object[]{errorMsg.substring(0, errorMsg.length() - 2)} 
					);
		}
	}

	@Override
	public JRDefaultStyleProvider getDefaultStyleProvider()
	{
		return filler.getJasperPrint().getDefaultStyleProvider();
	}


	@Override
	public void visitComponentElement(JRComponentElement componentElement)
	{
		JRFillComponentElement fill = null;

		if (componentElement != null)
		{
			fill = (JRFillComponentElement) get(componentElement);
			if (fill == null)
			{
				fill = new JRFillComponentElement(filler, componentElement, this);
			}
		}

		setVisitResult(fill);
	}


	@Override
	public void visitGenericElement(JRGenericElement element)
	{
		JRFillGenericElement fill = null;
		if (element != null)
		{
			fill = (JRFillGenericElement) get(element);
			if (fill == null)
			{
				fill = new JRFillGenericElement(filler, element, this);
			}
		}
		setVisitResult(fill);
	}

	public FillMultiAxisData getBidimensionalData(MultiAxisData data)
	{
		FillMultiAxisData fillData = null;
		if (data != null)
		{
			fillData = (FillMultiAxisData) get(data);
			if (fillData == null)
			{
				fillData = new FillMultiAxisData(data, this);
			}
		}
		return fillData;
	}

}
