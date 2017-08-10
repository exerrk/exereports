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

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.github.exerrk.engine.JRConstants;
import com.github.exerrk.engine.JRFont;
import com.github.exerrk.engine.base.JRBaseObjectFactory;
import com.github.exerrk.engine.component.BaseComponentContext;
import com.github.exerrk.engine.component.ComponentContext;
import com.github.exerrk.engine.component.ContextAwareComponent;
import com.github.exerrk.engine.design.events.JRChangeEventsSupport;
import com.github.exerrk.engine.design.events.JRPropertyChangeSupport;
import com.github.exerrk.engine.type.EvaluationTimeEnum;
import com.github.exerrk.engine.type.HorizontalImageAlignEnum;
import com.github.exerrk.engine.type.SortFieldTypeEnum;
import com.github.exerrk.engine.type.VerticalImageAlignEnum;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class SortComponent implements ContextAwareComponent, Serializable, JRChangeEventsSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	
	public static final String PROPERTY_COLUMN_NAME = "sortFieldName";
	public static final String PROPERTY_COLUMN_TYPE = "sortFieldType";
	public static final String PROPERTY_HANDLER_COLOR = "handlerColor";
	public static final String PROPERTY_HANDLER_VERTICAL_ALIGN = "handlerVerticalAlign";
	public static final String PROPERTY_HANDLER_HORIZONTAL_ALIGN = "handlerHorizontalAlign";
	
	public static final String PROPERTY_SYMBOL_FONT = "symbolFont";

	private EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.NOW;
	private String evaluationGroup;

	private String sortFieldName;
	private SortFieldTypeEnum sortFieldType;
	private Color handlerColor;
	private VerticalImageAlignEnum handlerVerticalImageAlign = VerticalImageAlignEnum.MIDDLE;
	private HorizontalImageAlignEnum handlerHorizontalImageAlign = HorizontalImageAlignEnum.LEFT;
	
	private JRFont symbolFont;

	private ComponentContext context;

	private transient JRPropertyChangeSupport eventSupport;
	
	public SortComponent() {
	}
	
	public SortComponent(SortComponent component, JRBaseObjectFactory objectFactory) {
		this.evaluationTime= component.getEvaluationTime();
		this.evaluationGroup = component.getEvaluationGroup();
		this.context = new BaseComponentContext(component.getContext(), objectFactory);

		this.sortFieldName = component.getSortFieldName();
		this.sortFieldType = component.getSortFieldType();
		this.handlerColor = component.getHandlerColor();
		this.handlerVerticalImageAlign = component.getHandlerVerticalImageAlign();
		this.handlerHorizontalImageAlign = component.getHandlerHorizontalImageAlign();
		
		this.symbolFont = component.getSymbolFont();
	}

	@Override
	public void setContext(ComponentContext context)
	{
		this.context = context;
	}
	
	@Override
	public ComponentContext getContext()
	{
		return context;
	}
	
	/**
	 * @return the evaluationTime
	 */
	public EvaluationTimeEnum getEvaluationTime() {
		return evaluationTime;
	}

	/**
	 * @param evaluationTime the evaluationTime to set
	 */
	public void setEvaluationTime(EvaluationTimeEnum evaluationTime) {
		Object old = this.evaluationTime;
		this.evaluationTime = evaluationTime;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, 
				old, this.evaluationTime);
	}

	public String getEvaluationGroup()
	{
		return evaluationGroup;
	}

	public void setEvaluationGroup(String evaluationGroup)
	{
		Object old = this.evaluationGroup;
		this.evaluationGroup = evaluationGroup;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP, 
				old, this.evaluationGroup);
	}
	
	
	/**
	 * @return the columnName
	 */
	public String getSortFieldName() {
		return sortFieldName;
	}

	/**
	 * @param sortFieldName the sort field name to set
	 */
	public void setSortFieldName(String sortFieldName) {
		Object old = this.sortFieldName;
		this.sortFieldName = sortFieldName;
		getEventSupport().firePropertyChange(PROPERTY_COLUMN_NAME, 
				old, this.sortFieldName);
	}

	/**
	 * @return the columnType
	 */
	public SortFieldTypeEnum getSortFieldType() {
		return sortFieldType;
	}

	/**
	 * @param sortFieldType the sort field type to set
	 */
	public void setSortFieldType(SortFieldTypeEnum sortFieldType) {
		Object old = this.sortFieldType;
		this.sortFieldType = sortFieldType;
		getEventSupport().firePropertyChange(PROPERTY_COLUMN_TYPE, 
				old, this.sortFieldType);
	}

	/**
	 * @return the handlerColor
	 */
	public Color getHandlerColor() {
		return handlerColor;
	}

	/**
	 * @param handlerColor the handlerColor to set
	 */
	public void setHandlerColor(Color handlerColor) {
		Object old = this.handlerColor;
		this.handlerColor = handlerColor;
		getEventSupport().firePropertyChange(PROPERTY_HANDLER_COLOR, 
				old, this.handlerColor);
	}

	/**
	 * @deprecated Replaced by {@link #getHandlerVerticalImageAlign()}.
	 */
	public com.github.exerrk.engine.type.VerticalAlignEnum getHandlerVerticalAlign() {
		return com.github.exerrk.engine.type.VerticalAlignEnum.getVerticalAlignEnum(getHandlerVerticalImageAlign());
	}

	/**
	 * @deprecated Replaced by {@link #setHandlerVerticalImageAlign(VerticalImageAlignEnum)}.
	 */
	public void setHandlerVerticalAlign(com.github.exerrk.engine.type.VerticalAlignEnum handlerVerticalAlign) {
		setHandlerVerticalImageAlign(com.github.exerrk.engine.type.VerticalAlignEnum.getVerticalImageAlignEnum(handlerVerticalAlign));
	}

	/**
	 * @deprecated Replaced by {@link #getHandlerHorizontalImageAlign()}.
	 */
	public com.github.exerrk.engine.type.HorizontalAlignEnum getHandlerHorizontalAlign() {
		return com.github.exerrk.engine.type.HorizontalAlignEnum.getHorizontalAlignEnum(getHandlerHorizontalImageAlign());
	}

	/**
	 * @deprecated Replaced by {@link #setHandlerHorizontalImageAlign(HorizontalImageAlignEnum)}.
	 */
	public void setHandlerHorizontalAlign(com.github.exerrk.engine.type.HorizontalAlignEnum handlerHorizontalAlign) {
		setHandlerHorizontalImageAlign(com.github.exerrk.engine.type.HorizontalAlignEnum.getHorizontalImageAlignEnum(handlerHorizontalAlign));
	}

	/**
	 * @return the handlerVerticalImageAlign
	 */
	public VerticalImageAlignEnum getHandlerVerticalImageAlign() {
		return handlerVerticalImageAlign;
	}

	/**
	 * @param handlerVerticalImageAlign the handlerVerticalImageAlign to set
	 */
	public void setHandlerVerticalImageAlign(VerticalImageAlignEnum handlerVerticalImageAlign) {
		Object old = this.handlerVerticalImageAlign;
		this.handlerVerticalImageAlign = handlerVerticalImageAlign;
		getEventSupport().firePropertyChange(PROPERTY_HANDLER_VERTICAL_ALIGN, 
				old, this.handlerVerticalImageAlign);
	}

	/**
	 * @return the handlerHorizontalImageAlign
	 */
	public HorizontalImageAlignEnum getHandlerHorizontalImageAlign() {
		return handlerHorizontalImageAlign;
	}

	/**
	 * @param handlerHorizontalImageAlign the handlerHorizontalImageAlign to set
	 */
	public void setHandlerHorizontalImageAlign(HorizontalImageAlignEnum handlerHorizontalImageAlign) {
		Object old = this.handlerHorizontalImageAlign;
		this.handlerHorizontalImageAlign = handlerHorizontalImageAlign;
		getEventSupport().firePropertyChange(PROPERTY_HANDLER_HORIZONTAL_ALIGN, 
				old, this.handlerHorizontalImageAlign);
	}

	@Override
	public JRPropertyChangeSupport getEventSupport() {
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}

	public JRFont getSymbolFont() {
		return symbolFont;
	}

	public void setSymbolFont(JRFont symbolFont) {
		Object old = this.symbolFont;
		this.symbolFont = symbolFont;
		getEventSupport().firePropertyChange(PROPERTY_SYMBOL_FONT, 
				old, this.symbolFont);
	}


	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private com.github.exerrk.engine.type.VerticalAlignEnum handlerVerticalAlign;
	/**
	 * @deprecated
	 */
	private com.github.exerrk.engine.type.HorizontalAlignEnum handlerHorizontalAlign;

	@SuppressWarnings("deprecation")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_6_0_2)
		{
			handlerHorizontalImageAlign = com.github.exerrk.engine.type.HorizontalAlignEnum.getHorizontalImageAlignEnum(handlerHorizontalAlign);
			handlerVerticalImageAlign = com.github.exerrk.engine.type.VerticalAlignEnum.getVerticalImageAlignEnum(handlerVerticalAlign);

			handlerHorizontalAlign = null;
			handlerVerticalAlign = null;
		}
	}
}
