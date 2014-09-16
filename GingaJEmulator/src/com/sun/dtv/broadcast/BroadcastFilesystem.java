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
package com.sun.dtv.broadcast;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.tv.locator.Locator;

/**
 * <HR>
 * </PRE>
 * 
 * This class represents instances of broadcast filesystems mounted into the local filesystem.
 * That means every service transmitting a broadcast filesystem creates its own filesystem,
 * depending on the definition of the transport stream.
 * 
 * <HR>
 * 
 * 
 * <!-- ======== CONSTRUCTOR SUMMARY ======== -->
 * 
 * <A NAME="constructor_summary"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TH ALIGN="left" COLSPAN="2"><FONT SIZE="+2">
 * <B>Constructor Summary</B></FONT></TH>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/broadcast/BroadcastFilesystem.html#BroadcastFilesystem(javax.tv.locator.Locator)">BroadcastFilesystem</A></B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr927/javax/tv/locator/Locator.html?is-external=true" title="class or interface in javax.tv.locator">Locator</A>&nbsp;l)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create and mount a broadcast filesystem defined by the given
 * transport dependent locator.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/broadcast/BroadcastFilesystem.html#BroadcastFilesystem(java.net.URL)">BroadcastFilesystem</A></B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/net/URL.html?is-external=true" title="class or interface in java.net">URL</A>&nbsp;url)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create and mount a broadcast filesystem defined by the given URL.</TD>
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
 * <CODE>static&nbsp;<A HREF="../../../../com/sun/dtv/broadcast/BroadcastFile.html" title="class in com.sun.dtv.broadcast">BroadcastFile</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/broadcast/BroadcastFilesystem.html#getBroadcastRoot()">getBroadcastRoot</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Retrieves the system specific root mount point of all broadcast filesystems
 * in the local filesystem.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>static&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/broadcast/BroadcastFilesystem.html#getBroadcastRootPath()">getBroadcastRootPath</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Retrieves the system specific root mount point of all broadcast filesystems
 * in the local filesystem.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;<A HREF="../../../../com/sun/dtv/broadcast/BroadcastFile.html" title="class in com.sun.dtv.broadcast">BroadcastFile</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/broadcast/BroadcastFilesystem.html#getMountLocation()">getMountLocation</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Retrieve the root of this mounted broadcast filesystem.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;void</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/broadcast/BroadcastFilesystem.html#unmount()">unmount</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unmount this broadcast filesystem from the local filesystem.</TD>
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
 * <A NAME="BroadcastFilesystem(javax.tv.locator.Locator)"><!-- --></A><H3>
 * BroadcastFilesystem</H3>
 * <PRE>
 * public <B>BroadcastFilesystem</B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr927/javax/tv/locator/Locator.html?is-external=true" title="class or interface in javax.tv.locator">Locator</A>&nbsp;l)
 * throws <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/io/IOException.html?is-external=true" title="class or interface in java.io">IOException</A>,
 * <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/IllegalArgumentException.html?is-external=true" title="class or interface in java.lang">IllegalArgumentException</A></PRE>
 * <DD>Create and mount a broadcast filesystem defined by the given
 * transport dependent locator.
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/io/IOException.html?is-external=true" title="class or interface in java.io">IOException</A></CODE> - if no broadcast filesystem exists at
 * the given location.
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/IllegalArgumentException.html?is-external=true" title="class or interface in java.lang">IllegalArgumentException</A></CODE> - if the given locator
 * cannot be used to identify a broadcast filesystem.</DL>
 * <HR>
 * 
 * <A NAME="BroadcastFilesystem(java.net.URL)"><!-- --></A><H3>
 * BroadcastFilesystem</H3>
 * <PRE>
 * public <B>BroadcastFilesystem</B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/net/URL.html?is-external=true" title="class or interface in java.net">URL</A>&nbsp;url)
 * throws <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/io/IOException.html?is-external=true" title="class or interface in java.io">IOException</A>,
 * <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/IllegalArgumentException.html?is-external=true" title="class or interface in java.lang">IllegalArgumentException</A></PRE>
 * <DD>Create and mount a broadcast filesystem defined by the given URL.
 * The provided URL has to follow the "file:" protocol as defined in *
 * TBD: define the URL
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/io/IOException.html?is-external=true" title="class or interface in java.io">IOException</A></CODE> - if no broadcast filesystem exists at the given location
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/IllegalArgumentException.html?is-external=true" title="class or interface in java.lang">IllegalArgumentException</A></CODE> - if the given URL can't be used to identify a broadcast filesystem</DL>
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
 * <A NAME="unmount()"><!-- --></A><H3>
 * unmount</H3>
 * <PRE>
 * public void <B>unmount</B>()</PRE>
 * <DD>Unmount this broadcast filesystem from the local filesystem.
 * <HR>
 * 
 * <A NAME="getMountLocation()"><!-- --></A><H3>
 * getMountLocation</H3>
 * <PRE>
 * public <A HREF="../../../../com/sun/dtv/broadcast/BroadcastFile.html" title="class in com.sun.dtv.broadcast">BroadcastFile</A> <B>getMountLocation</B>()
 * throws <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/io/IOException.html?is-external=true" title="class or interface in java.io">IOException</A></PRE>
 * <DD>Retrieve the root of this mounted broadcast filesystem.
 * 
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/io/IOException.html?is-external=true" title="class or interface in java.io">IOException</A></CODE> - thrown if the filesystem is unmounted.</DL>
 * <HR>
 * 
 * <A NAME="getBroadcastRoot()"><!-- --></A><H3>
 * getBroadcastRoot</H3>
 * <PRE>
 * public static <A HREF="../../../../com/sun/dtv/broadcast/BroadcastFile.html" title="class in com.sun.dtv.broadcast">BroadcastFile</A> <B>getBroadcastRoot</B>()</PRE>
 * <DD>Retrieves the system specific root mount point of all broadcast filesystems
 * in the local filesystem. A list of all available broadcast filesystems
 * (no matter if mounted or not)
 * can be retrieved from the returned <A HREF="../../../../com/sun/dtv/broadcast/BroadcastFile.html#listDirectory()"><CODE>BroadcastFile.listDirectory()</CODE></A>.
 * 
 * <HR>
 * 
 * <A NAME="getBroadcastRootPath()"><!-- --></A><H3>
 * getBroadcastRootPath</H3>
 * <PRE>
 * public static <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>getBroadcastRootPath</B>()</PRE>
 * <DD>Retrieves the system specific root mount point of all broadcast filesystems
 * in the local filesystem.
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
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="class-use/BroadcastFilesystem.html"><FONT CLASS="NavBarFont1"><B>Use</B></FONT></A>&nbsp;</TD>
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
 * &nbsp;<A HREF="../../../../com/sun/dtv/broadcast/BroadcastFileListener.html" title="interface in com.sun.dtv.broadcast"><B>PREV CLASS</B></A>&nbsp;
 * &nbsp;<A HREF="../../../../com/sun/dtv/broadcast/BroadcastStream.html" title="class in com.sun.dtv.broadcast"><B>NEXT CLASS</B></A></FONT></TD>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * <A HREF="../../../../index.html?com/sun/dtv/broadcast/BroadcastFilesystem.html" target="_top"><B>FRAMES</B></A>  &nbsp;
 * &nbsp;<A HREF="BroadcastFilesystem.html" target="_top"><B>NO FRAMES</B></A>  &nbsp;
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
 * SUMMARY:&nbsp;NESTED&nbsp;|&nbsp;FIELD&nbsp;|&nbsp;<A HREF="#constructor_summary">CONSTR</A>&nbsp;|&nbsp;<A HREF="#method_summary">METHOD</A></FONT></TD>
 * <TD VALIGN="top" CLASS="NavBarCell3"><FONT SIZE="-2">
 * DETAIL:&nbsp;FIELD&nbsp;|&nbsp;<A HREF="#constructor_detail">CONSTR</A>&nbsp;|&nbsp;<A HREF="#method_detail">METHOD</A></FONT></TD>
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
public class BroadcastFilesystem implements Serializable
{
	//following variables are implicitely defined by getter- or setter-methods:
	private BroadcastFile mountLocation;
	private static BroadcastFile broadcastRoot;
	private static String broadcastRootPath;

	/**
	 * Create and mount a broadcast filesystem defined by the given
	 * transport dependent locator.
	 *
	 * 
	 * @param l - the locator of the broadcast filesystem to be mounted
	 * @throws IOException - if no broadcast filesystem exists at the given location.
	 * @throws IllegalArgumentException - if the given locator cannot be used to identify a broadcast filesystem.
	 */
	public BroadcastFilesystem( Locator l) throws IOException, IllegalArgumentException
	{
		//TODO implement BroadcastFilesystem
	}

	/**
	 * Create and mount a broadcast filesystem defined by the given URL.
	 * The provided URL has to follow the "file:" protocol as defined in *
	 * TBD: define the URL
	 *
	 * 
	 * @param url - the URL of the broadcast filesystem to be mounted
	 * @throws IOException - if no broadcast filesystem exists at the given location
	 * @throws IllegalArgumentException - if the given URL can't be used to identify a broadcast filesystem
	 */
	public BroadcastFilesystem( URL url) throws IOException, IllegalArgumentException
	{
		//TODO implement BroadcastFilesystem
	}

	/**
	 * Unmount this broadcast filesystem from the local filesystem.
	 *
	 * 
	 */
	public void unmount()
	{
		//TODO implement unmount
	}

	/**
	 * Retrieve the root of this mounted broadcast filesystem.
	 *
	 * 
	 * 
	 * @return the location of this broadcast filesystem root.
	 * @throws IOException - thrown if the filesystem is unmounted.
	 */
	public BroadcastFile getMountLocation() throws IOException
	{
		return this.mountLocation;
	}

	/**
	 * Retrieves the system specific root mount point of all broadcast filesystems
	 * in the local filesystem. A list of all available broadcast filesystems
	 * (no matter if mounted or not)
	 * can be retrieved from the returned <A HREF="../../../../com/sun/dtv/broadcast/BroadcastFile.html#listDirectory()"><CODE>BroadcastFile.listDirectory()</CODE></A>.
	 *
	 * 
	 * 
	 * @return the location of the broadcast root
	 */
	public static BroadcastFile getBroadcastRoot()
	{
		return broadcastRoot;
	}

	/**
	 * Retrieves the system specific root mount point of all broadcast filesystems
	 * in the local filesystem.
	 *
	 * 
	 * 
	 * @return the path of root mount point
	 */
	public static String getBroadcastRootPath()
	{
		return broadcastRootPath;
	}

}
