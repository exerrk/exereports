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
package com.github.exerrk.components.spiderchart;

import com.github.exerrk.charts.util.CategoryChartHyperlinkProvider;
import com.github.exerrk.charts.util.ChartHyperlinkProvider;
import com.github.exerrk.components.charts.AbstractChartCustomizer;
import com.github.exerrk.components.charts.ChartCustomizer;
import com.github.exerrk.components.charts.FillChartSettings;
import com.github.exerrk.engine.JRChart;
import com.github.exerrk.engine.JRComponentElement;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRPrintHyperlinkParameters;
import com.github.exerrk.engine.JRPrintImage;
import com.github.exerrk.engine.JRRuntimeException;
import com.github.exerrk.engine.component.BaseFillComponent;
import com.github.exerrk.engine.component.FillPrepareResult;
import com.github.exerrk.engine.fill.JRFillCloneFactory;
import com.github.exerrk.engine.fill.JRFillCloneable;
import com.github.exerrk.engine.fill.JRFillExpressionEvaluator;
import com.github.exerrk.engine.fill.JRFillHyperlinkHelper;
import com.github.exerrk.engine.fill.JRFillObjectFactory;
import com.github.exerrk.engine.fill.JRTemplateImage;
import com.github.exerrk.engine.fill.JRTemplatePrintImage;
import com.github.exerrk.engine.type.EvaluationTimeEnum;
import com.github.exerrk.engine.util.JRClassLoader;
import com.github.exerrk.engine.util.JRStringUtil;
import com.github.exerrk.renderers.Renderable;


/**
 * 
 * @author sanda zaharia (shertage@users.sourceforge.net)
 */
public class FillSpiderChart extends BaseFillComponent implements JRFillCloneable
{
	public static final String EXCEPTION_MESSAGE_KEY_CUSTOMIZER_INSTANCE_ERROR = "components.spiderchart.customizer.instance.error";

	private final SpiderChartComponent chartComponent;
	private final FillChartSettings chartSettings;
	private final FillSpiderDataset dataset;
	private final FillSpiderPlot plot;
	
	private Double maxValue;
	private String titleText;
	private String subtitleText;
	private String anchorName;
	private String hyperlinkReference;
	private Boolean hyperlinkWhen;
	private String hyperlinkAnchor;
	private Integer hyperlinkPage;
	private String hyperlinkTooltip;
	private Integer bookmarkLevel;
	private JRPrintHyperlinkParameters hyperlinkParameters;
	
	private JRFillExpressionEvaluator expressionEvaluator;
	private ChartHyperlinkProvider chartHyperlinkProvider;
	private Renderable renderer;
	private String customizerClass;
	protected ChartCustomizer chartCustomizer;

	
	public FillSpiderChart(SpiderChartComponent chartComponent, JRFillObjectFactory factory)
	{
		this.chartComponent = chartComponent;
		this.chartSettings = new FillChartSettings(chartComponent.getChartSettings(), factory);
		this.dataset = new FillSpiderDataset((SpiderDataset)chartComponent.getDataset(), factory);
		factory.registerElementDataset(this.dataset);
		this.plot = new FillSpiderPlot((SpiderPlot)chartComponent.getPlot(), factory);
		this.expressionEvaluator = factory.getExpressionEvaluator();
		
	}

	protected boolean isEvaluateNow()
	{
		return chartComponent.getEvaluationTime() == EvaluationTimeEnum.NOW;
	}
	
	@Override
	public void evaluate(byte evaluation) throws JRException
	{
		if (isEvaluateNow())
		{
			evaluateRenderer(evaluation);
		}
	}

	/**
	 *
	 */
	protected void evaluateRenderer(byte evaluation) throws JRException
	{
		maxValue = (Double) fillContext.evaluate(getPlot().getMaxValueExpression(), evaluation);
		titleText = JRStringUtil.getString(fillContext.evaluate(getChartSettings().getTitleExpression(), evaluation));
		subtitleText = JRStringUtil.getString(fillContext.evaluate(getChartSettings().getSubtitleExpression(), evaluation));
		anchorName = JRStringUtil.getString(fillContext.evaluate(getChartSettings().getAnchorNameExpression(), evaluation));
		hyperlinkReference = JRStringUtil.getString(fillContext.evaluate(getChartSettings().getHyperlinkReferenceExpression(), evaluation));
		hyperlinkWhen = (Boolean)fillContext.evaluate(getChartSettings().getHyperlinkWhenExpression(), evaluation);
		hyperlinkAnchor = JRStringUtil.getString(fillContext.evaluate(getChartSettings().getHyperlinkAnchorExpression(), evaluation));
		hyperlinkPage = (Integer) fillContext.evaluate(getChartSettings().getHyperlinkPageExpression(), evaluation);
		hyperlinkTooltip = JRStringUtil.getString(fillContext.evaluate(getChartSettings().getHyperlinkTooltipExpression(), evaluation));
		hyperlinkParameters = JRFillHyperlinkHelper.evaluateHyperlinkParameters(getChartSettings(), expressionEvaluator, evaluation);

		dataset.evaluateDatasetRun(evaluation);
		dataset.finishDataset();

		chartHyperlinkProvider = new CategoryChartHyperlinkProvider(dataset.getItemHyperlinks());
		bookmarkLevel = getChartSettings().getBookmarkLevel();
		
		JRComponentElement element = fillContext.getComponentElement();	

		SpiderChartSharedBean spiderChartSharedBean = new SpiderChartSharedBean(
				getChartSettings().getRenderType(),
				maxValue,
				titleText,
				subtitleText,
				chartHyperlinkProvider,
				dataset
				);
		
		customizerClass = chartSettings.getCustomizerClass();
		if (customizerClass != null && customizerClass.length() > 0) {
			try {
				Class<?> myClass = JRClassLoader.loadClassForName(customizerClass);
				chartCustomizer = (ChartCustomizer) myClass.newInstance();
			} catch (Exception e) {
				throw 
					new JRRuntimeException(
						EXCEPTION_MESSAGE_KEY_CUSTOMIZER_INSTANCE_ERROR,
						(Object[])null,
						e);
			}

			if (chartCustomizer instanceof AbstractChartCustomizer)
			{
				((AbstractChartCustomizer) chartCustomizer).init(fillContext.getFiller(), getDataset());
			}
		}
		
		renderer = 
			SpiderChartRendererEvaluator.evaluateRenderable(
				fillContext.getFiller().getJasperReportsContext(),
				element, 
				spiderChartSharedBean, 
				chartCustomizer, 
				JRChart.RENDER_TYPE_DRAW, 
				SpiderChartRendererEvaluator.FILL_DATASET
				);
	}


	@Override
	public JRPrintElement fill()
	{
		JRComponentElement element = fillContext.getComponentElement();
		JRTemplateImage templateImage = new JRTemplateImage(fillContext.getElementOrigin(), 
				fillContext.getDefaultStyleProvider());
		templateImage.setStyle(fillContext.getElementStyle());
		templateImage.setLinkType(getLinkType());
		templateImage.setLinkTarget(getLinkTarget());
		templateImage.setUsingCache(false);
		templateImage = deduplicate(templateImage);
		
		JRTemplatePrintImage image = new JRTemplatePrintImage(templateImage, printElementOriginator);
		image.setUUID(element.getUUID());
		image.setX(element.getX());
		image.setY(fillContext.getElementPrintY());
		image.setWidth(element.getWidth());
		image.setHeight(element.getHeight());
		image.setBookmarkLevel(getBookmarkLevel());

		if (isEvaluateNow())
		{
			copy(image);
		}
		else
		{
			fillContext.registerDelayedEvaluation(image, 
					chartComponent.getEvaluationTime(), chartComponent.getEvaluationGroup());
		}
		
		return image;
	}

	@Override
	public FillPrepareResult prepare(int availableHeight)
	{
		return FillPrepareResult.PRINT_NO_STRETCH;
	}

	@Override
	public JRFillCloneable createClone(JRFillCloneFactory factory)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void evaluateDelayedElement(JRPrintElement element, byte evaluation) throws JRException
	{
		evaluateRenderer(evaluation);
		copy((JRPrintImage) element);
		fillContext.getFiller().updateBookmark(element);
	}

	protected void copy(JRPrintImage printImage)
	{
		printImage.setRenderer(getRenderable());
		printImage.setAnchorName(getAnchorName());
		if (getChartSettings().getHyperlinkWhenExpression() == null || Boolean.TRUE.equals(hyperlinkWhen))
		{
			printImage.setHyperlinkReference(getHyperlinkReference());
			printImage.setHyperlinkAnchor(getHyperlinkAnchor());
			printImage.setHyperlinkPage(getHyperlinkPage());
			printImage.setHyperlinkTooltip(getHyperlinkTooltip());
			printImage.setHyperlinkParameters(hyperlinkParameters);
		}
		else
		{
			if (printImage instanceof JRTemplatePrintImage)//this is normally the case
			{
				((JRTemplatePrintImage) printImage).setHyperlinkOmitted(true);
			}
			
			printImage.setHyperlinkReference(null);
		}
//		transferProperties(printImage);
	}


	protected ChartHyperlinkProvider getHyperlinkProvider()
	{
		return chartHyperlinkProvider;
	}

	/**
	 * @return the chartSettings
	 */
	public FillChartSettings getChartSettings() {
		return chartSettings;
	}

	/**
	 * @return the dataset
	 */
	public FillSpiderDataset getDataset() {
		return dataset;
	}

	/**
	 * @return the plot
	 */
	public FillSpiderPlot getPlot() {
		return plot;
	}

	/**
	 * @return the maxValue
	 */
	public Double getMaxValue() {
		return maxValue;
	}

	/**
	 * @return the titleText
	 */
	public String getTitleText() {
		return titleText;
	}

	/**
	 * @return the subtitleText
	 */
	public String getSubtitleText() {
		return subtitleText;
	}

	/**
	 * @return the anchorName
	 */
	public String getAnchorName() {
		return anchorName;
	}

	/**
	 * @return the hyperlinkReference
	 */
	public String getHyperlinkReference() {
		return hyperlinkReference;
	}

	/**
	 * @return the hyperlinkAnchor
	 */
	public String getHyperlinkAnchor() {
		return hyperlinkAnchor;
	}

	/**
	 * @return the hyperlinkPage
	 */
	public Integer getHyperlinkPage() {
		return hyperlinkPage;
	}

	/**
	 * @return the hyperlinkTooltip
	 */
	public String getHyperlinkTooltip() {
		return hyperlinkTooltip;
	}
	
	/**
	 * @return the hyperlinkTooltip
	 */
	public Integer getBookmarkLevel() {
		return bookmarkLevel;
	}

	/**
	 * @return the expressionEvaluator
	 */
	public JRFillExpressionEvaluator getExpressionEvaluator() {
		return expressionEvaluator;
	}

	/**
	 * @return the renderer
	 */
	public Renderable getRenderable() {
		return renderer;
	}

	public String getLinkType()
	{
		return getChartSettings().getLinkType();
	}

	public String getLinkTarget()
	{
		return getChartSettings().getLinkTarget();
	}

	/**
	 * @return the hyperlinkParameters
	 */
	public JRPrintHyperlinkParameters getHyperlinkParameters() {
		return hyperlinkParameters;
	}

}
