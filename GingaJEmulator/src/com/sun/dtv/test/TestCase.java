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
 * Defines the necessary methods that must be implemented
 * by tests to be able to run in the provided test framework.
 * 
 * <p>It mainly consists in a entry point and some helper methods. The
 * design of the test framework is such that it is platform independent
 * and allows different configuration of the test harness.</p>
 * 
 * <p>The test harness provides a means to forward command line arguments
 * to a given test case. It is then the responsibility of every test case
 * to read out the arguments that are supported by that particular test case.
 * However, the following arguments are pre-defined by this application
 * and <span class="rfc2119">should</span> always be supported by every
 * test cases:</p>
 * 
 * <dl>
 * <dt><code>-testid <i>id</i></code></dt>
 * <dd>used to give the testcase its ID. If that argument is present
 * in <code>args</code>, it <span class="rfc2119">must</span> be
 * the value returned by <code>getTestId()</code>.</dd>
 * <dt><code>-testtitle <i>title</i></code></dt>
 * <dd>used to give the testcase its title. If that argument is present
 * in <code>args</code>, it <span class="rfc2119">must</span> be
 * the value returned by <code>getTestTitle()</code>.</dd>
 * <dt><code>-select <i>case1,case2,case3...</i></code></dt>
 * <dd>this argument is reserved to indicate the test case that only
 * a specific (sub) case are to be handled. The value is a
 * comma separated list of names that
 * <span class="rfc2119">must</span> corresponds to given
 * methods in the <code>TestCase</code> class.
 * <i>This feature is not yet supported by this specification and
 * this argument is reserved for future use.</i></dd>
 * <dt><code>-exclude <i>case1,case2,case3...</i></code></dt>
 * <dd>similar to the previous <code>select</code> argument but
 * does list those methods that are to be excluded. If both
 * <code>select</code> and <code>exclude</code> are used and list
 * the same test, then that test is considered to be excluded.
 * <i>This feature is not yet supported by this specification and
 * this argument is reserved for future use.</i></dd>
 * <dt></dt>
 * <dd></dd>
 * </dl>
 * 
 * <p>This list is subject to be extended in future version of this
 * specification.</p>
 * 
 * <p>Individual test cases <span class="rfc2119">may</span> add support for
 * new arguments, however these arguments <span class="rfc2119">must</span>
 * be preceded by "<code>-x-</code>" to indicate that these arguments
 * are not standardized: for example a given test case may
 * wanting to control a timeout through the <code>-x-timeout</code>
 * argument.
 * 
 * <HR>
 * 
 * 
 * <!-- ========== METHOD SUMMARY =========== -->
 * 
 * <A NAME="method_summary"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="2"><FONT SIZE="+2">
 * <B>Method Summary</B></FONT></TH>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;int</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/test/TestCase.html#getTestId()">getTestId</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns the id of that test case.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/test/TestCase.html#getTestTitle()">getTestTitle</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns a short description of the test.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;<A HREF="../../../../com/sun/dtv/test/Report.html" title="class in com.sun.dtv.test">Report</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/test/TestCase.html#run(java.lang.String[])">run</A></B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A>[]&nbsp;args)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Runs the test embodied by the implementation.</TD>
 * </TR>
 * </TABLE>
 * &nbsp;
 * 
 * <!-- ============ METHOD DETAIL ========== -->
 * 
 * <A NAME="method_detail"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="1"><FONT SIZE="+2">
 * <B>Method Detail</B></FONT></TH>
 * </TR>
 * </TABLE>
 * 
 * <A NAME="run(java.lang.String[])"><!-- --></A><H3>
 * run</H3>
 * <PRE>
 * <A HREF="../../../../com/sun/dtv/test/Report.html" title="class in com.sun.dtv.test">Report</A> <B>run</B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A>[]&nbsp;args)</PRE>
 * <DD>Runs the test embodied by the implementation.
 * If the format of any value of any given arguments is incorrect, the
 * value of <span class="rfc2119">should</span> be ignored.
 * allow a script to provide configuration information to a test, or to
 * reuse a test with different test values.
 * <HR>
 * 
 * <A NAME="getTestId()"><!-- --></A><H3>
 * getTestId</H3>
 * <PRE>
 * int <B>getTestId</B>()</PRE>
 * <DD>Returns the id of that test case.
 * Every test case has an id which semantic, order, etc... is
 * defined within the context of the test harness.
 * 
 * <p>It <span class="rfc2119">must</span> be equal to the value
 * <code><i>id</i></code> passed in <code>-testid</code> if that
 * argument was passed to <A HREF="../../../../com/sun/dtv/test/TestCase.html#run(java.lang.String[])"><CODE>run(String[])</CODE></A>.</p>
 * 
 * <HR>
 * 
 * <A NAME="getTestTitle()"><!-- --></A><H3>
 * getTestTitle</H3>
 * <PRE>
 * <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>getTestTitle</B>()</PRE>
 * <DD>Returns a short description of the test. The description
 * <span class="rfc2119">should</span> not contain new lines (e.g.
 * carriage returns) and its length be typically be less than
 * 80 characters.
 * 
 * <p>It <span class="rfc2119">must</span> be equal to the value
 * <code><i>title</i></code> passed in <code>-testtitle</code> if that
 * argument was passed to <A HREF="../../../../com/sun/dtv/test/TestCase.html#run(java.lang.String[])"><CODE>run(String[])</CODE></A>.</p>
 * 
 * <!-- ========= END OF CLASS DATA ========= -->
 * <HR>
 * 
 * 
 * <!-- ======= START OF BOTTOM NAVBAR ====== -->
 * <A NAME="navbar_bottom"><!-- --></A>
 * <A HREF="#skip-navbar_bottom" title="Skip navigation links"></A>
 * <TABLE BORDER="0" WIDTH="100%" CELLPADDING="1" CELLSPACING="0" SUMMARY="">
 * <TR>
 * <TD COLSPAN=2 BGCOLOR="#EEEEFF" CLASS="NavBarCell1">
 * <A NAME="navbar_bottom_firstrow"><!-- --></A>
 * <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="3" SUMMARY="">
 * <TR ALIGN="center" VALIGN="top">
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="../../../../overview-summary.html"><FONT CLASS="NavBarFont1"><B>Overview</B></FONT></A>&nbsp;</TD>
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="package-summary.html"><FONT CLASS="NavBarFont1"><B>Package</B></FONT></A>&nbsp;</TD>
 * <TD BGCOLOR="#FFFFFF" CLASS="NavBarCell1Rev"> &nbsp;<FONT CLASS="NavBarFont1Rev"><B>Class</B></FONT>&nbsp;</TD>
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="class-use/TestCase.html"><FONT CLASS="NavBarFont1"><B>Use</B></FONT></A>&nbsp;</TD>
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="../../../../deprecated-list.html"><FONT CLASS="NavBarFont1"><B>Deprecated</B></FONT></A>&nbsp;</TD>
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="../../../../index-all.html"><FONT CLASS="NavBarFont1"><B>Index</B></FONT></A>&nbsp;</TD>
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="../../../../help-doc.html"><FONT CLASS="NavBarFont1"><B>Help</B></FONT></A>&nbsp;</TD>
 * </TR>
 * </TABLE>
 * </TD>
 * <TD ALIGN="right" VALIGN="top" ROWSPAN=3><EM>
 * Java DTV API 1.1<br>28-Feb-2009</EM>
 * </TD>
 * </TR>
 * 
 * <TR>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * &nbsp;<A HREF="../../../../com/sun/dtv/test/Report.html" title="class in com.sun.dtv.test"><B>PREV CLASS</B></A>&nbsp;
 * &nbsp;<A HREF="../../../../com/sun/dtv/test/TestHarness.html" title="class in com.sun.dtv.test"><B>NEXT CLASS</B></A></FONT></TD>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * <A HREF="../../../../index.html?com/sun/dtv/test/TestCase.html" target="_top"><B>FRAMES</B></A>  &nbsp;
 * &nbsp;<A HREF="TestCase.html" target="_top"><B>NO FRAMES</B></A>  &nbsp;
 * &nbsp;<SCRIPT type="text/javascript">
 * <!--
 * if(window==top) {
 * document.writeln('<A HREF="../../../../allclasses-noframe.html"><B>All Classes</B></A>');
 * }
 * //-->
 * </SCRIPT>
 * <NOSCRIPT>
 * <A HREF="../../../../allclasses-noframe.html"><B>All Classes</B></A>
 * </NOSCRIPT>
 * 
 * 
 * </FONT></TD>
 * </TR>
 * <TR>
 * <TD VALIGN="top" CLASS="NavBarCell3"><FONT SIZE="-2">
 * SUMMARY:&nbsp;NESTED&nbsp;|&nbsp;FIELD&nbsp;|&nbsp;CONSTR&nbsp;|&nbsp;<A HREF="#method_summary">METHOD</A></FONT></TD>
 * <TD VALIGN="top" CLASS="NavBarCell3"><FONT SIZE="-2">
 * DETAIL:&nbsp;FIELD&nbsp;|&nbsp;CONSTR&nbsp;|&nbsp;<A HREF="#method_detail">METHOD</A></FONT></TD>
 * </TR>
 * </TABLE>
 * <A NAME="skip-navbar_bottom"></A>
 * <!-- ======== END OF BOTTOM NAVBAR ======= -->
 * 
 * <HR>
 * </BODY>
 * </HTML>
 * 
 */
public interface TestCase
{
	/**
	 * Runs the test embodied by the implementation.
	 * If the format of any value of any given arguments is incorrect, the
	 * value of <span class="rfc2119">should</span> be ignored.
	 *
	 * 
	 * @param args - These are supplied in the description of this class and allow a script to provide configuration information to a test, or to reuse a test with different test values.
	 * @return A Report object representing the outcome of the test.
	 */
	Report run( String[] args);

	/**
	 * Returns the id of that test case.
	 * Every test case has an id which semantic, order, etc... is
	 * defined within the context of the test harness.
	 * 
	 *It <span class="rfc2119">must</span> be equal to the value
	 * <code><i>id</i></code> passed in <code>-testid</code> if that
	 * argument was passed to <A HREF="../../../../com/sun/dtv/test/TestCase.html#run(java.lang.String[])"><CODE>run(String[])</CODE></A>.</p>
	 *
	 * 
	 * 
	 * @return An integer corresponding to the id of that test case.
	 */
	int getTestId();

	/**
	 * Returns a short description of the test. The description
	 * <span class="rfc2119">should</span> not contain new lines (e.g.
	 * carriage returns) and its length be typically be less than
	 * 80 characters.
	 * 
	 *It <span class="rfc2119">must</span> be equal to the value
	 * <code><i>title</i></code> passed in <code>-testtitle</code> if that
	 * argument was passed to <A HREF="../../../../com/sun/dtv/test/TestCase.html#run(java.lang.String[])"><CODE>run(String[])</CODE></A>.</p>
	 *
	 * 
	 * 
	 * @return A short text description of the test.
	 */
	String getTestTitle();

}
