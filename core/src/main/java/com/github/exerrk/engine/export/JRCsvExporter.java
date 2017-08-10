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

/*
 * Contributors:
 * Mirko Wawrowsky - mawawrosky@users.sourceforge.net
 */
package com.github.exerrk.engine.export;

import java.io.IOException;

import com.github.exerrk.engine.DefaultJasperReportsContext;
import com.github.exerrk.engine.JRGenericPrintElement;
import com.github.exerrk.engine.JRPrintElement;
import com.github.exerrk.engine.JRPrintPage;
import com.github.exerrk.engine.JRPrintText;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.PrintPageFormat;
import com.github.exerrk.engine.util.JRStyledText;
import com.github.exerrk.export.CsvExporterConfiguration;
import com.github.exerrk.export.CsvReportConfiguration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Exports a JasperReports document to CSV format. It has character output type and exports the document to a
 * grid-based layout.
 * <p/>
 * Because CSV is a data-oriented file format, exporting rich content documents to
 * CSV results in a tremendous loss of quality. The CSV exporter will completely ignore graphic elements present in
 * the source document that needs to be exported. It will only deal will text elements, and
 * from those, it will only extract the text value, completely ignoring the style properties.
 * <p/>
 * CSV is a character-based file format whose content is structured in rows and columns, so
 * the {@link com.github.exerrk.engine.export.JRCsvExporter} is a grid exporter
 * because it must transform the free-form content of
 * each page from the source document into a grid-like structure using the special grid layout algorithm.
 * <p/>
 * By default, the CSV exporter uses commas to separate column values and newline
 * characters to separate rows in the resulting file. However, one can redefine the delimiters
 * using the two special exporter configuration settings in the 
 * {@link com.github.exerrk.export.CsvExporterConfiguration} class:
 * <ul>
 * <li>{@link com.github.exerrk.export.CsvExporterConfiguration#getFieldDelimiter() getFieldDelimiter()}</li>
 * <li>{@link com.github.exerrk.export.CsvExporterConfiguration#getRecordDelimiter() getRecordDelimiter()}</li>
 * <ul>
 * which both provide <code>java.lang.String</code> values.
 * 
 * @see com.github.exerrk.export.CsvExporterConfiguration
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRCsvExporter extends JRAbstractCsvExporter<CsvReportConfiguration, CsvExporterConfiguration, JRCsvExporterContext>
{
	private static final Log log = LogFactory.getLog(JRCsvExporter.class);

	protected class ExporterContext extends BaseExporterContext implements JRCsvExporterContext
	{
	}

	/**
	 * @see #JRCsvExporter(JasperReportsContext)
	 */
	public JRCsvExporter()
	{
		this(DefaultJasperReportsContext.getInstance());
	}

	
	/**
	 *
	 */
	public JRCsvExporter(JasperReportsContext jasperReportsContext)
	{
		super(jasperReportsContext);
		
		exporterContext = new ExporterContext();
	}


	@Override
	protected Class<CsvExporterConfiguration> getConfigurationInterface()
	{
		return CsvExporterConfiguration.class;
	}


	@Override
	protected Class<CsvReportConfiguration> getItemConfigurationInterface()
	{
		return CsvReportConfiguration.class;
	}
	

	@Override
	@SuppressWarnings("deprecation")
	protected void ensureOutput()
	{
		if (exporterOutput == null)
		{
			exporterOutput = 
				new com.github.exerrk.export.parameters.ParametersWriterExporterOutput(
					getJasperReportsContext(),
					getParameters(),
					getCurrentJasperPrint()
					);
		}
	}
	

	@Override
	protected void exportPage(JRPrintPage page) throws IOException
	{
		CsvExporterConfiguration configuration = getCurrentConfiguration();
		
		String fieldDelimiter = configuration.getFieldDelimiter();
		String recordDelimiter = configuration.getRecordDelimiter();
		
		CsvReportConfiguration lcItemConfiguration = getCurrentItemConfiguration();
		
		PrintPageFormat pageFormat = jasperPrint.getPageFormat(pageIndex); 
		
		JRGridLayout layout = 
			new JRGridLayout(
				nature,
				page.getElements(), 
				pageFormat.getPageWidth(), 
				pageFormat.getPageHeight(), 
				lcItemConfiguration.getOffsetX() == null ? 0 : lcItemConfiguration.getOffsetX(), 
				lcItemConfiguration.getOffsetY() == null ? 0 : lcItemConfiguration.getOffsetY(),
				null //address
				);
		
		Grid grid = layout.getGrid();

		CutsInfo xCuts = layout.getXCuts();
		CutsInfo yCuts = layout.getYCuts();

		StringBuilder rowBuilder = null;
		
		boolean isFirstColumn = true;
		int rowCount = grid.getRowCount();
		for(int y = 0; y < rowCount; y++)
		{
			Cut yCut = yCuts.getCut(y);

			rowBuilder = new StringBuilder();

			if (yCut.isCutNotEmpty())
			{
				isFirstColumn = true;
				GridRow row = grid.getRow(y);
				int rowSize = row.size();
				for(int x = 0; x < rowSize; x++)
				{
					JRPrintElement element = row.get(x).getElement();
					if(element != null)
					{
						String text = null;
						if (element instanceof JRPrintText)
						{
							JRStyledText styledText = getStyledText((JRPrintText)element);
							if (styledText == null)
							{
								text = "";
							}
							else
							{
								text = styledText.getText();
							}
						}
						else if (element instanceof JRGenericPrintElement)
						{
							JRGenericPrintElement genericPrintElement = (JRGenericPrintElement)element;
							GenericElementCsvHandler handler = (GenericElementCsvHandler) 
								GenericElementHandlerEnviroment.getInstance(getJasperReportsContext()).getElementHandler(
										genericPrintElement.getGenericType(), CSV_EXPORTER_KEY);
							
							if (handler == null)
							{
								if (log.isDebugEnabled())
								{
									log.debug("No CSV generic element handler for " 
											+ genericPrintElement.getGenericType());
								}
								
								// it shouldn't get to this due to JRCsvExporterNature.isToExport, but let's be safe
								text = "";
							}
							else
							{
								text = handler.getTextValue(exporterContext, genericPrintElement);
							}
						}

						if (text != null)
						{
							if (!isFirstColumn)
							{
								rowBuilder.append(fieldDelimiter);
							}
							rowBuilder.append(
								prepareText(text)
								);
							isFirstColumn = false;
						}
					}
					else
					{
						if (xCuts.getCut(x).isCutNotEmpty())
						{
							if (!isFirstColumn)
							{
								rowBuilder.append(fieldDelimiter);
							}
							isFirstColumn = false;
						}
					}
				}
				
				if (rowBuilder.length() > 0)
				{
					writer.write(rowBuilder.toString());
					writer.write(recordDelimiter);
				}
			}
		}
		
		JRExportProgressMonitor progressMonitor  = lcItemConfiguration.getProgressMonitor();
		if (progressMonitor != null)
		{
			progressMonitor.afterPageExport();
		}
	}
}