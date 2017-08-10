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
package com.github.exerrk.engine.util;

import java.math.BigDecimal;

/**
 * {@link BigDecimal} handler interface used by {@link BigDecimalUtils}.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface BigDecimalHandler
{

	/**
	 * Divides two {@link BigDecimal} values.
	 * 
	 * @param dividend the dividend
	 * @param divisor the divisor
	 * @return the division result, rounded according to the rules of the 
	 * specific implementation
	 */
	BigDecimal divide(BigDecimal dividend, BigDecimal divisor);
	
}