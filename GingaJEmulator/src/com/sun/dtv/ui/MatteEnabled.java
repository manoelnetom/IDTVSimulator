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
package com.sun.dtv.ui;

/**
 * 
 * The aim of this interface is to enable components for matte compositing.
 * This interface adds the necessary functionality to incorporate Matte
 * functionality into a component, the matte itself is implemented in the
 * <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> class and classes inheriting from it.
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
 * <CODE>&nbsp;<A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui">Matte</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#getMatte()">getMatte</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Return the <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> currently associated with the
 * component implementing this interface.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;void</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#setMatte(com.sun.dtv.ui.Matte)">setMatte</A></B>(<A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui">Matte</A>&nbsp;matte)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Adds an <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> to the component implementing this
 * interface in order to enable matte compositing.</TD>
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
 * <A NAME="setMatte(com.sun.dtv.ui.Matte)"><!-- --></A><H3>
 * setMatte</H3>
 * <PRE>
 * void <B>setMatte</B>(<A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui">Matte</A>&nbsp;matte)
 * throws <A HREF="../../../../com/sun/dtv/ui/MatteException.html" title="class in com.sun.dtv.ui">MatteException</A></PRE>
 * <DD>Adds an <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> to the component implementing this
 * interface in order to enable matte compositing. If there is already a
 * Matte assigned to the component, and this Matte is animated, it has to be
 * stopped before any call to this method.
 * component. At any point of time there can only be one matte associated to
 * the component, that's way any matte that has been associated before will
 * be overriden by a call to this method.
 * The Matte parameter can also be null, in this case there is no matte
 * associated with the component after the call, even if there had been one
 * before.
 * <DD><CODE><A HREF="../../../../com/sun/dtv/ui/MatteException.html" title="class in com.sun.dtv.ui">MatteException</A></CODE> - if the <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> has an
 * unsupported type, the platform does not support mattes at all, or an
 * animated matte is associated with the component and is still running<DT><B>See Also:</B><DD><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#getMatte()"><CODE>getMatte()</CODE></A></DL>
 * <HR>
 * 
 * <A NAME="getMatte()"><!-- --></A><H3>
 * getMatte</H3>
 * <PRE>
 * <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui">Matte</A> <B>getMatte</B>()</PRE>
 * <DD>Return the <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> currently associated with the
 * component implementing this interface.
 * 
 * component or null if there is none<DT><B>See Also:</B><DD><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#setMatte(com.sun.dtv.ui.Matte)"><CODE>setMatte(com.sun.dtv.ui.Matte)</CODE></A></DL>
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
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="class-use/MatteEnabled.html"><FONT CLASS="NavBarFont1"><B>Use</B></FONT></A>&nbsp;</TD>
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
 * &nbsp;<A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><B>PREV CLASS</B></A>&nbsp;
 * &nbsp;<A HREF="../../../../com/sun/dtv/ui/MatteException.html" title="class in com.sun.dtv.ui"><B>NEXT CLASS</B></A></FONT></TD>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * <A HREF="../../../../index.html?com/sun/dtv/ui/MatteEnabled.html" target="_top"><B>FRAMES</B></A>  &nbsp;
 * &nbsp;<A HREF="MatteEnabled.html" target="_top"><B>NO FRAMES</B></A>  &nbsp;
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
public interface MatteEnabled
{
	/**
	 * Adds an <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> to the component implementing this
	 * interface in order to enable matte compositing. If there is already a
	 * Matte assigned to the component, and this Matte is animated, it has to be
	 * stopped before any call to this method.
	 *
	 * 
	 * @param matte - the Matte to be assigned to the  component. At any point of time there can only be one matte associated to the component, that's way any matte that has been associated before will be overriden by a call to this method. The Matte parameter can also be null, in this case there is no matte associated with the component after the call, even if there had been one before.
	 * @throws MatteException - if the Matte has an unsupported type, the platform does not support mattes at all, or an animated matte is associated with the component and is still running
	 * @see getMatte()
	 */
	void setMatte( Matte matte) throws MatteException;

	/**
	 * Return the <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> currently associated with the
	 * component implementing this interface.
	 *
	 * 
	 * 
	 * @return the Matte currently associated with the component or null if there is none
	 * @see setMatte(com.sun.dtv.ui.Matte)
	 */
	Matte getMatte();

}
