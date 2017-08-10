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
package com.github.exerrk.frames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JRParameter;
import com.github.exerrk.engine.JasperCompileManager;
import com.github.exerrk.engine.JasperFillManager;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReport;
import com.github.exerrk.engine.SimpleJasperReportsContext;
import com.github.exerrk.engine.design.JasperDesign;
import com.github.exerrk.engine.export.JRXmlExporter;
import com.github.exerrk.engine.util.JRLoader;
import com.github.exerrk.engine.xml.JRXmlLoader;
import com.github.exerrk.export.SimpleExporterInput;
import com.github.exerrk.export.SimpleXmlExporterOutput;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class FramesTest
{
	private static final Log log = LogFactory.getLog(FramesTest.class);
	
	private JasperFillManager fillManager;

	@BeforeClass
	public void init() throws JRException, IOException
	{
		SimpleJasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
		
		fillManager = JasperFillManager.getInstance(jasperReportsContext);
	}

	@Test
	public void testReports() throws JRException, NoSuchAlgorithmException, IOException
	{
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(JRParameter.REPORT_LOCALE, Locale.US);
		params.put(JRParameter.REPORT_TIME_ZONE, TimeZone.getTimeZone("GMT"));
		
		for (int i = 1; i <= 6; i++)
		{
			String jrxmlFileName = "com/github/exerrk/frames/repo/FramesReport." + i + ".jrxml";
			
			JasperReport report = compileReport(jrxmlFileName);
			
			JasperPrint print = fillManager.fill(report, params);
			assert !print.getPages().isEmpty();
			
			String xmlExportDigest = xmlExportDigest(print);
			log.debug("Plain report got " + xmlExportDigest);
			
			String referenceXmlExportDigest = getFileDigest("com/github/exerrk/frames/repo/FramesReport." + i + ".reference.jrpxml");
			
			assert xmlExportDigest.equals(referenceXmlExportDigest);
		}
	}

	protected JasperReport compileReport(String jrxmlFileName) throws JRException, IOException
	{
		InputStream jrxmlInput = JRLoader.getResourceInputStream(jrxmlFileName);
		JasperDesign design;
		try
		{
			design = JRXmlLoader.load(jrxmlInput);
		}
		finally
		{
			jrxmlInput.close();
		}
		
		return JasperCompileManager.compileReport(design);
	}

	protected String getFileDigest(String fileName) throws JRException, NoSuchAlgorithmException
	{
		byte[] bytes = JRLoader.loadBytesFromResource(fileName);
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		messageDigest.update(bytes);
		String digest = toDigestString(messageDigest);
		log.debug("Reference report digest is " + digest);
		return digest;
	}
	
	protected String xmlExportDigest(JasperPrint print) 
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
