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
package com.sun.dtv.service;

import com.sun.dtv.tuner.Tuner;

/**
 * 
 * This class provides a generic means to access the low level SI (Service
 * Information) database,  which resides on the platform.
 * 
 * <p>A SI database provides means to asynchronously query or monitor SI
 * tables, which are transmitted in the transport stream.</p>
 * 
 * <p>To be fully flexible the SIDatabase provides a common base class, from
 * which the actual SIDatabase implementation class is derived. This mechanism
 * permits to use different SI database formats as provided in the actual
 * broadcasting system.</p>
 * 
 * <p>An implementation of this API must extend SIDatabase and provide
 * additional SI specific access methods to obtain the individual SI
 * tables.</p>
 * 
 * <p>Each <code>SIDatabase</code> instance is associated to a network
 * interface. The <code>SIDatabase</code> class offers the method
 * <code>getSIDatabase</code> to query the <code>Tuner</code> for a specific
 * SIDatabase. The method <code>getAllSIDatabases</code> returns all
 * SIDatabases in the system. The type of SI information, which is supported
 * by a specific SIDatabase can be queried with the method
 * <code>getSICodingFormat</code>.</p>
 * 
 * <HR>
 * 
 * <!-- =========== FIELD SUMMARY =========== -->
 * 
 * <A NAME="field_summary"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="2"><FONT SIZE="+2">
 * <B>Field Summary</B></FONT></TH>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>static&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#SIFormatARIB">SIFormatARIB</A></B></CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Format of the SIDatabase: ARIB service information.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>static&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#SIFormatDVB">SIFormatDVB</A></B></CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Format of the SIDatabase: DVB service information.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>static&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#SIFormatSBTVD">SIFormatSBTVD</A></B></CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Format of the SIDatabase: SBTVD service information.</TD>
 * </TR>
 * </TABLE>
 * &nbsp;
 * <!-- ======== CONSTRUCTOR SUMMARY ======== -->
 * 
 * <A NAME="constructor_summary"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="2"><FONT SIZE="+2">
 * <B>Constructor Summary</B></FONT></TH>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>protected </CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#SIDatabase()">SIDatabase</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Protected constructor - not to be called directly.</TD>
 * </TR>
 * </TABLE>
 * &nbsp;
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
 * <CODE>static&nbsp;<A HREF="../../../../com/sun/dtv/service/SIDatabase.html" title="class in com.sun.dtv.service">SIDatabase</A>[]</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#getAllSIDatabases()">getAllSIDatabases</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns an array of all SIDatabase instances in the system.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#getSICodingFormat()">getSICodingFormat</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns the coding format of the service information database.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>static&nbsp;<A HREF="../../../../com/sun/dtv/service/SIDatabase.html" title="class in com.sun.dtv.service">SIDatabase</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/service/SIDatabase.html#getSIDatabase(com.sun.dtv.tuner.Tuner)">getSIDatabase</A></B>(<A HREF="../../../../com/sun/dtv/tuner/Tuner.html" title="class in com.sun.dtv.tuner">Tuner</A>&nbsp;tuner)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns the SIDatabase instance which corresponds to the native system.</TD>
 * </TR>
 * </TABLE>
 * &nbsp;<A NAME="methods_inherited_from_class_java.lang.Object"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#EEEEFF" CLASS="TableSubHeadingColor">
 * <TH ALIGN="left"><B>Methods inherited from class java.lang.<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true" title="class or interface in java.lang">Object</A></B></TH>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#clone()" title="class or interface in java.lang">clone</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#equals(java.lang.Object)" title="class or interface in java.lang">equals</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#finalize()" title="class or interface in java.lang">finalize</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#getClass()" title="class or interface in java.lang">getClass</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#hashCode()" title="class or interface in java.lang">hashCode</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#notify()" title="class or interface in java.lang">notify</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#notifyAll()" title="class or interface in java.lang">notifyAll</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#toString()" title="class or interface in java.lang">toString</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#wait()" title="class or interface in java.lang">wait</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#wait(long)" title="class or interface in java.lang">wait</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#wait(long, int)" title="class or interface in java.lang">wait</A></CODE></TD>
 * </TR>
 * </TABLE>
 * &nbsp;
 * 
 * <!-- ============ FIELD DETAIL =========== -->
 * 
 * <A NAME="field_detail"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="1"><FONT SIZE="+2">
 * <B>Field Detail</B></FONT></TH>
 * </TR>
 * </TABLE>
 * 
 * <A NAME="SIFormatDVB"><!-- --></A><H3>
 * SIFormatDVB</H3>
 * <PRE>
 * public static final <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>SIFormatDVB</B></PRE>
 * <DD>Format of the SIDatabase: DVB service information.
 * <HR>
 * 
 * <A NAME="SIFormatARIB"><!-- --></A><H3>
 * SIFormatARIB</H3>
 * <PRE>
 * public static final <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>SIFormatARIB</B></PRE>
 * <DD>Format of the SIDatabase: ARIB service information.
 * <HR>
 * 
 * <A NAME="SIFormatSBTVD"><!-- --></A><H3>
 * SIFormatSBTVD</H3>
 * <PRE>
 * public static final <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>SIFormatSBTVD</B></PRE>
 * <DD>Format of the SIDatabase: SBTVD service information.
 * 
 * <!-- ========= CONSTRUCTOR DETAIL ======== -->
 * 
 * <A NAME="constructor_detail"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="1"><FONT SIZE="+2">
 * <B>Constructor Detail</B></FONT></TH>
 * </TR>
 * </TABLE>
 * 
 * <A NAME="SIDatabase()"><!-- --></A><H3>
 * SIDatabase</H3>
 * <PRE>
 * protected <B>SIDatabase</B>()</PRE>
 * <DD>Protected constructor - not to be called directly.
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
 * <A NAME="getSIDatabase(com.sun.dtv.tuner.Tuner)"><!-- --></A><H3>
 * getSIDatabase</H3>
 * <PRE>
 * public static <A HREF="../../../../com/sun/dtv/service/SIDatabase.html" title="class in com.sun.dtv.service">SIDatabase</A> <B>getSIDatabase</B>(<A HREF="../../../../com/sun/dtv/tuner/Tuner.html" title="class in com.sun.dtv.tuner">Tuner</A>&nbsp;tuner)
 * throws <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/NullPointerException.html?is-external=true" title="class or interface in java.lang">NullPointerException</A></PRE>
 * <DD>Returns the SIDatabase instance which corresponds to the native system.
 * 
 * <p>The <code>tuner</code> is used to select the interface.</p>
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/NullPointerException.html?is-external=true" title="class or interface in java.lang">NullPointerException</A></CODE> - if <code>tuner</code> is <code>null</code>.</DL>
 * <HR>
 * 
 * <A NAME="getAllSIDatabases()"><!-- --></A><H3>
 * getAllSIDatabases</H3>
 * <PRE>
 * public static <A HREF="../../../../com/sun/dtv/service/SIDatabase.html" title="class in com.sun.dtv.service">SIDatabase</A>[] <B>getAllSIDatabases</B>()</PRE>
 * <DD>Returns an array of all SIDatabase instances in the system.
 * 
 * <HR>
 * 
 * <A NAME="getSICodingFormat()"><!-- --></A><H3>
 * getSICodingFormat</H3>
 * <PRE>
 * public <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>getSICodingFormat</B>()</PRE>
 * <DD>Returns the coding format of the service information database.
 * 
 * <p>The following return values are predefined:</p>
 * 
 * <ul>
 * <li>SIDatabase.SIFormatDVB</li>
 * <li>SIDatabase.SIFormatARIB</li>
 * <li>SIDatabase.SIFormatSBTVD</li>
 * </ul>
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
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="class-use/SIDatabase.html"><FONT CLASS="NavBarFont1"><B>Use</B></FONT></A>&nbsp;</TD>
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
 * &nbsp;PREV CLASS&nbsp;
 * &nbsp;NEXT CLASS</FONT></TD>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * <A HREF="../../../../index.html?com/sun/dtv/service/SIDatabase.html" target="_top"><B>FRAMES</B></A>  &nbsp;
 * &nbsp;<A HREF="SIDatabase.html" target="_top"><B>NO FRAMES</B></A>  &nbsp;
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
 * SUMMARY:&nbsp;NESTED&nbsp;|&nbsp;<A HREF="#field_summary">FIELD</A>&nbsp;|&nbsp;<A HREF="#constructor_summary">CONSTR</A>&nbsp;|&nbsp;<A HREF="#method_summary">METHOD</A></FONT></TD>
 * <TD VALIGN="top" CLASS="NavBarCell3"><FONT SIZE="-2">
 * DETAIL:&nbsp;<A HREF="#field_detail">FIELD</A>&nbsp;|&nbsp;<A HREF="#constructor_detail">CONSTR</A>&nbsp;|&nbsp;<A HREF="#method_detail">METHOD</A></FONT></TD>
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
public class SIDatabase extends Object
{
	/**
	 * Format of the SIDatabase: DVB service information.
	 *
	 *
	 * 
	 */
	public static final String SIFormatDVB = "DVB";

	/**
	 * Format of the SIDatabase: ARIB service information.
	 *
	 *
	 * 
	 */
	public static final String SIFormatARIB = "ARIB";

	/**
	 * Format of the SIDatabase: SBTVD service information.
	 *
	 *
	 * 
	 * 
	 */
	public static final String SIFormatSBTVD = "SBTVD";

	//following variables are implicitely defined by getter- or setter-methods:
	private static SIDatabase[] allSIDatabases;
	private String sICodingFormat;

	/**
	 * Protected constructor - not to be called directly.
	 *
	 * 
	 * 
	 */
	protected SIDatabase()
	{
		//TODO implement SIDatabase
	}

	/**
	 * Returns the SIDatabase instance which corresponds to the native system.
	 * 
	 *The <code>tuner</code> is used to select the interface.</p>
	 *
	 * 
	 * @param tuner - The tuner to get the SI database from.
	 * @return the SIDatabase instance.
	 * @throws NullPointerException - if tuner is null.
	 */
	public static SIDatabase getSIDatabase( Tuner tuner) throws NullPointerException
	{
		return null;
		//TODO implement getSIDatabase
	}

	/**
	 * Returns an array of all SIDatabase instances in the system.
	 *
	 * 
	 * 
	 * @return an array of all SIDatabase instances.
	 */
	public static SIDatabase[] getAllSIDatabases()
	{
		return allSIDatabases;
	}

	/**
	 * Returns the coding format of the service information database.
	 * 
	 *The following return values are predefined:</p>
	 * 
	 * <ul>
	 * <li>SIDatabase.SIFormatDVB</li>
	 * <li>SIDatabase.SIFormatARIB</li>
	 * <li>SIDatabase.SIFormatSBTVD</li>
	 * </ul>
	 *
	 * 
	 * 
	 * @return the coding format.
	 */
	public String getSICodingFormat()
	{
		return this.sICodingFormat;
	}

}
