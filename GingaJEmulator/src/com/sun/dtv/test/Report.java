/******************************************************************************
 * Este arquivo eh parte da implementacao do Projeto OpenGinga
 *
 * Direitos Autorais Reservados (c) 2005-2009 UFPB/LAVID
 *
 * Este programa eh software livre; voce pode redistribui-lo e/ou modificah-lo sob
 * os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free
 * Software Foundation.
 *
 * Este programa eh distribuido na expectativa de que seja util, porem, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implicita de COMERCIABILIDADE OU
 * ADEQUACAO A UMA FINALIDADE ESPECIFICA. Consulte a Licenca Publica Geral do
 * GNU versao 2 para mais detalhes.
 *
 * Voce deve ter recebido uma copia da Licenca Publica Geral do GNU versao 2 junto
 * com este programa; se nao, escreva para a Free Software Foundation, Inc., no
 * endereco 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 *
 * Para maiores informacoes:
 * ginga @ lavid.ufpb.br
 * http://www.openginga.org
 * http://www.ginga.org.br
 * http://www.lavid.ufpb.br
 * ******************************************************************************
 * This file is part of OpenGinga Project
 *
 * Copyright: 2005-2009 UFPB/LAVID, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more
 * details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 *
 * For further information contact:
 * ginga @ lavid.ufpb.br
 * http://www.openginga.org
 * http://www.ginga.org.br
 * http://www.lavid.ufpb.br
 * *******************************************************************************/
package com.sun.dtv.test;

/**
 * 
 * 
 */
public class Report extends Object
{
	/**
	 * A report type indicating that the test was executed and was successful.
	 *
	 *
	 * <A HREF="../../../../com/sun/dtv/test/Report.html#getType()"><CODE>getType()</CODE></A>,
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.Report.PASSED">Constant Field Values</A></DL>
	 * 
	 */
	public static final int PASSED = 0;

	/**
	 * A report type indicating that the test was executed but the test
	 * reported that it failed.
	 *
	 *
	 * <A HREF="../../../../com/sun/dtv/test/Report.html#getType()"><CODE>getType()</CODE></A>,
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.Report.FAILED">Constant Field Values</A></DL>
	 * 
	 */
	public static final int FAILED = 1;

	/**
	 * A report type indicating that the test was not run because some error
	 * occurred before the test could even be attempted. This is generally
	 * a more serious error than FAILED.
	 *
	 *
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.Report.ERROR">Constant Field Values</A></DL>
	 * 
	 */
	public static final int ERROR = 2;

	/**
	 * A report type indicating that the test has not yet been run in this
	 * context. More specifically, no status file has been recorded for this
	 * test in the current work directory.
	 *
	 *
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.Report.NOT_RUN">Constant Field Values</A></DL>
	 * 
	 */
	public static final int NOT_RUN = 3;

	/**
	 * A report type indicating that the test has produced indecisive
	 * results. They may happen if the execution of the test was interrupted
	 * unexpectedly, there were too many warnings or the test was incorrectly
	 * configured. This usually means that the test operator need to analyze
	 * the test log as well as probably improve the test case.
	 *
	 *
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.Report.UNRESOLVED">Constant Field Values</A></DL>
	 * 
	 */
	public static final int UNRESOLVED = 4;

	/**
	 * Number of report types which are predefined as "constants".
	 *
	 *
	 * 
	 * 
	 */
	protected static final int NUM_STATES = 5;

	//following variables are implicitely defined by getter- or setter-methods:
	private int type;

	/**
	 * Create a Report object.  See <A HREF="../../../../com/sun/dtv/test/Report.html#passed(com.sun.dtv.test.TestCase, java.lang.String)"><CODE>passed(TestCase, String)</CODE></A>,
	 * <A HREF="../../../../com/sun/dtv/test/Report.html#failed(com.sun.dtv.test.TestCase, java.lang.String)"><CODE>failed(TestCase, String)</CODE></A>, <A HREF="../../../../com/sun/dtv/test/Report.html#error(com.sun.dtv.test.TestCase, java.lang.String)"><CODE>error(TestCase, String)</CODE></A>
	 * etc. for more convenient factory methods to create
	 * Report objects. Every time a new report is created, it
	 * <span class="rfc2119">must</span> be automatically logged in the
	 * output stream defined to by <A HREF="../../../../com/sun/dtv/test/TestHarness.html#log"><CODE>TestHarness.log</CODE></A>. The
	 * format is defined to be the return value of <A HREF="../../../../com/sun/dtv/test/Report.html#toString()"><CODE>toString()</CODE></A>
	 * appended with a carriage return (CR) character.
	 *
	 * 
	 * @param type - The type code for the Report object.
	 * @param test - The corresponding test TestCase object.
	 * @param reason - A short string to store in the report. Unprintable  characters (i.e. outside the range 040C to 177C) in the string are replaced by a space.
	 * @throws NullPointerException - if test or reason is null.
	 * @throws IllegalArgumentException - if the specified type is invalid.
	 */
	public Report(int type, TestCase test, String reason) throws NullPointerException, IllegalArgumentException
	{
		//TODO implement Report
	}

	/**
	 * Get the type code indicating the type of this Report object.
	 *
	 * 
	 * 
	 * @return the type code indicating the type of this Report object.
	 * @see PASSED,  FAILED, ERROR, NOT_RUN, UNRESOLVED
	 */
	public int getType()
	{
		return this.type;
	}

	/**
	 * Check if the type code of the report is PASSED.
	 *
	 * 
	 * 
	 * @return true if the type code is PASSED.
	 * @see passed(com.sun.dtv.test.TestCase, java.lang.String),  getType(), PASSED
	 */
	public boolean isPassed()
	{
		return false;
		//TODO implement isPassed
	}

	/**
	 * Check if the type code of the report is FAILED.
	 *
	 * 
	 * 
	 * @return true if the type code is FAILED.
	 * @see failed(com.sun.dtv.test.TestCase, java.lang.String),  getType(), FAILED
	 */
	public boolean isFailed()
	{
		return false;
		//TODO implement isFailed
	}

	/**
	 * Check if the type code of the report is ERROR.
	 *
	 * 
	 * 
	 * @return true if the type code is ERROR.
	 * @see error(com.sun.dtv.test.TestCase, java.lang.String),  getType(), ERROR
	 */
	public boolean isError()
	{
		return false;
		//TODO implement isError
	}

	/**
	 * Check if the type code of the report is NOT_RUN.
	 *
	 * 
	 * 
	 * @return true if the type code is NOT_RUN.
	 * @see notRun(com.sun.dtv.test.TestCase, java.lang.String),  getType(), NOT_RUN
	 */
	public boolean isNotRun()
	{
		return false;
		//TODO implement isNotRun
	}

	/**
	 * Check if the type code of the report is UNRESOLVED.
	 *
	 * 
	 * 
	 * @return true if the type code is UNRESOLVED.
	 * @see unresolved(com.sun.dtv.test.TestCase, java.lang.String),  getType(), UNRESOLVED
	 */
	public boolean isUnresolved()
	{
		return false;
		//TODO implement isUnresolved
	}

	/**
	 * Create a Report to indicate the successful outcome of a test.
	 *
	 * 
	 * @param test - The test TestCase object that passed.
	 * @param reason - A short string describing why the test passed.
	 * @return a Report to indicate the successful outcome of a test.
	 */
	public static Report passed( TestCase test, String reason)
	{
		return null;
		//TODO implement passed
	}

	/**
	 * Create a Report to indicate the unsuccessful outcome of a test:
	 * i.e. the test completed, but the test determined that what was being
	 * tested did not pass the test.
	 *
	 * 
	 * @param test - The test TestCase object that failed.
	 * @param reason - A short string describing why the test failed.
	 * @return a Report to indicate the unsuccessful outcome of a test.
	 */
	public static Report failed( TestCase test, String reason)
	{
		return null;
		//TODO implement failed
	}

	/**
	 * Create a Report to indicate that an error occurred while trying to run
	 * a test: i.e. the test did not complete for some reason, and so it could
	 * not determine whether what was being tested passed or failed.
	 *
	 * 
	 * @param test - The test TestCase object that had an error.
	 * @param reason - A short string describing the error that occurred.
	 * @return a Report to indicate the error outcome of a test.
	 */
	public static Report error( TestCase test, String reason)
	{
		return null;
		//TODO implement error
	}

	/**
	 * Create a Report to indicate that the test has not yet been run.
	 *
	 * 
	 * @param test - The test TestCase object that did not run.
	 * @param reason - A short string indicating why the test has not yet  been run.
	 * @return a Report to indicate the notRun outcome of a test.
	 */
	public static Report notRun( TestCase test, String reason)
	{
		return null;
		//TODO implement notRun
	}

	/**
	 * Create a Report to indicate that the test has produced indecisive
	 * results.
	 *
	 * 
	 * @param test - The test TestCase object.
	 * @param reason - A short string indicating why the test has not yet  been run.
	 * @return a Report to indicate the UNRESOLVED outcome of a test.
	 */
	public static Report unresolved( TestCase test, String reason)
	{
		return null;
		//TODO implement unresolved
	}

	/**
	 * Convert a Report to a string. The convention is to specify the
	 * type of the report, the test id (as returned by <A HREF="../../../../com/sun/dtv/test/TestCase.html#getTestId()"><CODE>TestCase.getTestId()</CODE></A>) and the reason on a space separated line.
	 *
	 * 
	 * @return The complete report in a string.
	 * @see toString in class Object
	 */
	public String toString()
	{
		return null;
		//TODO implement toString
	}

	/**
	 * Convenience <code>exit()</code> function for the <code>main()</code> of
	 * tests to exit in such a way that the report passes up across process
	 * boundaries without losing information, that is: exit codes don't give
	 * the associated text of the report and return codes when exceptions are
	 * thrown could cause unintended results.
	 * 
	 *An identifying marker is written to the error stream, which the
	 * script running the test watches for as the last output before
	 * returning, followed by the type and reason.</p>
	 * 
	 *The method does not return. It calls <code>System.exit</code> with a
	 * value dependent on the type.</p>
	 *
	 * 
	 */
	public void exit()
	{
		//TODO implement exit
	}

}
