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
package com.github.exerrk.view.save;

import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.github.exerrk.engine.JRException;
import com.github.exerrk.engine.JasperPrint;
import com.github.exerrk.engine.JasperReportsContext;
import com.github.exerrk.engine.export.JRXmlExporter;
import com.github.exerrk.export.SimpleExporterInput;
import com.github.exerrk.export.SimpleXmlExporterOutput;
import com.github.exerrk.view.JRSaveContributor;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRXmlSaveContributor extends JRSaveContributor
{

	/**
	 * 
	 */
	private static final String EXTENSION_XML = ".xml"; 
	private static final String EXTENSION_JRPXML = ".jrpxml"; 

	/**
	 * @see #JRXmlSaveContributor(JasperReportsContext, Locale, ResourceBundle)
	 */
	public JRXmlSaveContributor(Locale locale, ResourceBundle resBundle)
	{
		super(locale, resBundle);
	}
	
	/**
	 * 
	 */
	public JRXmlSaveContributor(
		JasperReportsContext jasperReportsContext, 
		Locale locale, 
		ResourceBundle resBundle
		)
	{
		super(jasperReportsContext, locale, resBundle);
	}
	
	@Override
	public boolean accept(File file)
	{
		if (file.isDirectory())
		{
			return true;
		}
		String name = file.getName().toLowerCase();
		return (name.endsWith(EXTENSION_XML) || name.endsWith(EXTENSION_JRPXML));
	}

	@Override
	public String getDescription()
	{
		return getBundleString("file.desc.xml");
	}

	@Override
	public void save(JasperPrint jasperPrint, File file) throws JRException
	{
		if (
			!file.getName().toLowerCase().endsWith(EXTENSION_XML)
			&& !file.getName().toLowerCase().endsWith(EXTENSION_JRPXML)
			)
		{
			file = new File(file.getAbsolutePath() + EXTENSION_JRPXML);
		}
			
		if (
			!file.exists() ||
			JOptionPane.OK_OPTION == 
				JOptionPane.showConfirmDialog(
					null, 
					MessageFormat.format(
						getBundleString("file.exists"),
						new Object[]{file.getName()}
						), 
					getBundleString("save"), 
					JOptionPane.OK_CANCEL_OPTION
					)
			)
		{
			JRXmlExporter exporter = new JRXmlExporter(getJasperReportsContext());
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
			SimpleXmlExporterOutput output = new SimpleXmlExporterOutput(file);
			output.setEmbeddingImages(false);
			exporter.setExporterOutput(output);
			exporter.exportReport(); 
		}
	}

}
