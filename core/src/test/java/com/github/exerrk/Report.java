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
package com.github.exerrk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.exerrk.data.BuiltinDataFileServiceFactory;
import com.github.exerrk.data.DataAdapterParameterContributorFactory;
import com.github.exerrk.data.DataFileServiceFactory;
import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JRVirtualizer;
import com.github.exerrk.engine.JasperCompileManager;
import com.github.exerrk.engine.JasperFillManager;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReport;
import com.github.exerrk.engine.ParameterContributorFactory;
import com.github.exerrk.engine.SimpleJasperReportsContext;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.engine.export.JRXmlExporter;
import com.github.exerrk.engine.fill.JRAbstractLRUVirtualizer;
import com.github.exerrk.engine.util.JRLoader;
import com.github.exerrk.engine.xml.JRXmlLoader;
import com.github.exerrk.export.SimpleExporterInput;
import com.github.exerrk.export.SimpleXmlExporterOutput;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class Report
{

	private static final Log log = LogFactory.getLog(Report.class);
	
	private String jrxml;
	private String jrpxml;
	
	public Report(String jrxml, String jrpxml)
	{
		this.jrxml = jrxml;
		this.jrpxml = jrpxml;
	}
	
	protected SimpleJasperReportsContext jasperReportsContext;
	protected JasperReport report;
	private JasperFillManager fillManager;
	private String referenceJRPXMLDigest;

	public void init()
	{
		jasperReportsContext = new SimpleJasperReportsContext();
		// for some reason data adapter extensions are not registered by default
		jasperReportsContext.setExtensions(ParameterContributorFactory.class, 
				Collections.singletonList(DataAdapterParameterContributorFactory.getInstance()));
		jasperReportsContext.setExtensions(DataFileServiceFactory.class, 
				Collections.singletonList(BuiltinDataFileServiceFactory.instance()));
		
		try
		{
			compileReport();
			readReferenceDigest();
		}
		catch (JRException | IOException | NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected void compileReport() throws JRException, IOException
	{
		InputStream jrxmlInput = JRLoader.getResourceInputStream(jrxml);
		JasperDesign design;
		try
		{
			design = JRXmlLoader.load(jrxmlInput);
		}
		finally
		{
			jrxmlInput.close();
		}
		
		report = JasperCompileManager.compileReport(design);
		
		fillManager = JasperFillManager.getInstance(jasperReportsContext);
	}

	protected void readReferenceDigest() throws JRException, NoSuchAlgorithmException
	{
		byte[] jrpxmlBytes = JRLoader.loadBytesFromResource(jrpxml);
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.update(jrpxmlBytes);
		referenceJRPXMLDigest = toDigestString(digest);
		log.debug("Reference report digest for " + jrpxml + " is " + referenceJRPXMLDigest);
	}
	
	public void runReport(Map<String, Object> params)
	{
		Map<String, Object> reportParams = reportParams(params);
		try
		{
			JasperPrint print = fillManager.fill(report, reportParams);
			reportComplete(reportParams, print);
		}
		catch (JRException | NoSuchAlgorithmException | IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> reportParams(Map<String, Object> params)
	{
		if (params == null)
		{
			params = new HashMap<String, Object>();
		}
		params.put(JRParameter.REPORT_LOCALE, Locale.US);
		params.put(JRParameter.REPORT_TIME_ZONE, TimeZone.getTimeZone("GMT"));
		return params;
	}

	protected void reportComplete(Map<String, Object> params, JasperPrint print)
			throws NoSuchAlgorithmException, FileNotFoundException, JRException, IOException
	{
		JRVirtualizer virtualizer = (JRVirtualizer) params.get(JRParameter.REPORT_VIRTUALIZER);
		if (virtualizer instanceof JRAbstractLRUVirtualizer)
		{
			((JRAbstractLRUVirtualizer) virtualizer).setReadOnly(true);
		}
		
		assert !print.getPages().isEmpty();
		
		String digestString = xmlDigest(print);
		log.debug("Report " + jrxml + " got " + digestString);
		assert digestString.equals(referenceJRPXMLDigest);
		
		if (virtualizer != null)
		{
			virtualizer.cleanup();
		}
	}

	protected String xmlDigest(JasperPrint print) 
			throws NoSuchAlgorithmException, FileNotFoundException, JRException, IOException
	{
		File outputFile = createXmlOutputFile();
		log.debug("XML export output at " + outputFile.getAbsolutePath());
		
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		FileOutputStream output = new FileOutputStream(outputFile);
		try
		{
			DigestOutputStream out = new DigestOutputStream(output, digest);
			xmlExport(print, out);
		}
		finally
		{
			output.close();
		}
		
		return toDigestString(digest);
	}

	protected String toDigestString(MessageDigest digest)
	{
		byte[] digestBytes = digest.digest();
		StringBuilder digestString = new StringBuilder(digestBytes.length * 2);
		for (byte b : digestBytes)
		{
			digestString.append(String.format("%02x", b));
		}
		return digestString.toString();
	}
	
	protected File createXmlOutputFile() throws IOException
	{
		String outputDirPath = System.getProperty("xmlOutputDir");
		File outputFile;
		if (outputDirPath == null)
		{
			outputFile = File.createTempFile("jr_tests_", ".jrpxml");
		}
		else
		{
			File outputDir = new File(outputDirPath);
			outputFile = File.createTempFile("jr_tests_", ".jrpxml", outputDir);
		}
		return outputFile;
	}

	protected void xmlExport(JasperPrint print, OutputStream out) throws JRException, IOException
	{
		JRXmlExporter exporter = new JRXmlExporter();
		exporter.setExporterInput(new SimpleExporterInput(print));
		SimpleXmlExporterOutput output = new SimpleXmlExporterOutput(out);
		output.setEmbeddingImages(true);
		exporter.setExporterOutput(output);
		exporter.exportReport();
		out.close();
	}

}
