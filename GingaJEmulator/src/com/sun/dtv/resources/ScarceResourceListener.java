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
package com.sun.dtv.resources;

/**
 * 
 * Listener to be notified about release status of a given scarce resource.
 * 
 * <p>When calling any methods of this interface, certain platforms <span
 * class="rfc2119">may</span> define a timespan until when those methods
 * are expected to return. If they do not return within that timeframe,
 * the implementation <span class="rfc2119">must</span> behave as if
 * the method returned. This mechanism allows to avoid that applications
 * block those methods unnecessarily.</p>
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
 * <CODE>&nbsp;void</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/resources/ScarceResourceListener.html#released(com.sun.dtv.resources.ScarceResource)">released</A></B>(<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources">ScarceResource</A>&nbsp;resource)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reports that <code>resource</code> has been released by the
 * implementation and is available for a new reservation.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;void</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/resources/ScarceResourceListener.html#releaseForced(com.sun.dtv.resources.ScarceResource)">releaseForced</A></B>(<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources">ScarceResource</A>&nbsp;resource)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reports that <code>resource</code> is being aggressively requested
 * by another application and gives the current owner the chance to
 * close his task.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;boolean</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/resources/ScarceResourceListener.html#releaseRequested(com.sun.dtv.resources.ScarceResource)">releaseRequested</A></B>(<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources">ScarceResource</A>&nbsp;resource)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reports that <code>resource</code> is being requested by another
 * application.</TD>
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
 * <A NAME="releaseRequested(com.sun.dtv.resources.ScarceResource)"><!-- --></A><H3>
 * releaseRequested</H3>
 * <PRE>
 * boolean <B>releaseRequested</B>(<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources">ScarceResource</A>&nbsp;resource)</PRE>
 * <DD>Reports that <code>resource</code> is being requested by another
 * application. The current owner is free to decide whether it can
 * and want to release that given resource to its competitor.
 * 
 * <p>If this method returns <code>false</code>, the implementation
 * understands that the current owner wants to keep the resource and
 * that it is therefore not available to the competitor.</p>
 * 
 * <p>On the other hand, returning <code>true</code> tells the platform
 * that the current owner is ready to release its resource. In this case
 * the implementation <span class="rfc2119">must</span> release itself
 * the given resource. This in turn will trigger a <code>released</code>
 * event on this interface.</p>
 * 
 * <p>When supported, in case where this method does not return within
 * a given timeframe, the implementation <span class="rfc2119">must</span>
 * behave as if the method returned <code>true</code>.</p>
 * to be released.
 * the owner no longer needs the resource.</DL>
 * <HR>
 * 
 * <A NAME="releaseForced(com.sun.dtv.resources.ScarceResource)"><!-- --></A><H3>
 * releaseForced</H3>
 * <PRE>
 * void <B>releaseForced</B>(<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources">ScarceResource</A>&nbsp;resource)</PRE>
 * <DD>Reports that <code>resource</code> is being aggressively requested
 * by another application and gives the current owner the chance to
 * close his task. On return of this call, the implementation
 * <span class="rfc2119">must</span> release this resource. This in
 * turn will trigger a <code>released</code> event on this interface.
 * to be released.</DL>
 * <HR>
 * 
 * <A NAME="released(com.sun.dtv.resources.ScarceResource)"><!-- --></A><H3>
 * released</H3>
 * <PRE>
 * void <B>released</B>(<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources">ScarceResource</A>&nbsp;resource)</PRE>
 * <DD>Reports that <code>resource</code> has been released by the
 * implementation and is available for a new reservation. This method
 * is called by the implementation subsequently to a
 * <code>ScarceResource.release()</code> or after the
 * <code>releaseForced()</code> or <code>releaseRequested()</code> events.
 * 
 * <p>When the implementation calls this method, the object will have been
 * invalidated, i.e. the application that owned this resource is no more
 * able to interact with the given resource: calls are ignored and
 * methods <span class="rfc2119">may</span> throw an
 * <code>IllegalStateException</code>.</p>
 * released.</DL>
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
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="class-use/ScarceResourceListener.html"><FONT CLASS="NavBarFont1"><B>Use</B></FONT></A>&nbsp;</TD>
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
 * &nbsp;<A HREF="../../../../com/sun/dtv/resources/ScarceResource.html" title="interface in com.sun.dtv.resources"><B>PREV CLASS</B></A>&nbsp;
 * &nbsp;<A HREF="../../../../com/sun/dtv/resources/ScarceResourcePermission.html" title="class in com.sun.dtv.resources"><B>NEXT CLASS</B></A></FONT></TD>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * <A HREF="../../../../index.html?com/sun/dtv/resources/ScarceResourceListener.html" target="_top"><B>FRAMES</B></A>  &nbsp;
 * &nbsp;<A HREF="ScarceResourceListener.html" target="_top"><B>NO FRAMES</B></A>  &nbsp;
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
public interface ScarceResourceListener
{
	/**
	 * Reports that <code>resource</code> is being requested by another
	 * application. The current owner is free to decide whether it can
	 * and want to release that given resource to its competitor.
	 * 
	 *If this method returns <code>false</code>, the implementation
	 * understands that the current owner wants to keep the resource and
	 * that it is therefore not available to the competitor.</p>
	 * 
	 *On the other hand, returning <code>true</code> tells the platform
	 * that the current owner is ready to release its resource. In this case
	 * the implementation <span class="rfc2119">must</span> release itself
	 * the given resource. This in turn will trigger a <code>released</code>
	 * event on this interface.</p>
	 * 
	 *When supported, in case where this method does not return within
	 * a given timeframe, the implementation <span class="rfc2119">must</span>
	 * behave as if the method returned <code>true</code>.</p>
	 *
	 * 
	 * @param resource - The instance of the scarce resource that is requested to be released.
	 * @return A boolean indicating whether the current application and the owner no longer needs the resource.
	 */
	boolean releaseRequested( ScarceResource resource);

	/**
	 * Reports that <code>resource</code> is being aggressively requested
	 * by another application and gives the current owner the chance to
	 * close his task. On return of this call, the implementation
	 * <span class="rfc2119">must</span> release this resource. This in
	 * turn will trigger a <code>released</code> event on this interface.
	 *
	 * 
	 * @param resource - The instance of the scarce resource that is required to be released.
	 */
	void releaseForced( ScarceResource resource);

	/**
	 * Reports that <code>resource</code> has been released by the
	 * implementation and is available for a new reservation. This method
	 * is called by the implementation subsequently to a
	 * <code>ScarceResource.release()</code> or after the
	 * <code>releaseForced()</code> or <code>releaseRequested()</code> events.
	 * 
	 *When the implementation calls this method, the object will have been
	 * invalidated, i.e. the application that owned this resource is no more
	 * able to interact with the given resource: calls are ignored and
	 * methods <span class="rfc2119">may</span> throw an
	 * <code>IllegalStateException</code>.</p>
	 *
	 * 
	 * @param resource - The instance of the scarce resource that has been released.
	 */
	void released( ScarceResource resource);

}
