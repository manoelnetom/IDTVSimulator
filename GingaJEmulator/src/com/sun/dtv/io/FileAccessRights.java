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
package com.sun.dtv.io;

/**
 * 
 * Provides a means to define different group level access rights
 * to a file or directory. A <code>FileAccessRights</code> consists of a
 * pathname and a set of actions valid for that pathname. Pathname is the
 * pathname of the file or directory granted the specified actions.
 * 
 * <p>This package defines classes that extends the behavior of the
 * implementation of the <code>java.io</code> package. By such, the
 * implementation of <code>java.io</code> on a given platform that follows
 * this specification <span class="rfc2119">must</span> enforce the access
 * model and rights defined in <code>FileAccessRights</code>.</p>
 * 
 * <h3>Pathname</h3>
 * 
 * <p>A pathname that ends in "<code>/*</code>" (where "<code>/</code>"
 * is the file separator character, <code>File.separatorChar</code>)
 * indicates a directory and all the files contained in that directory.
 * A pathname that ends with "<code>/-</code>" indicates a directory and
 * (recursively) all files and subdirectories contained in that directory.
 * The special pathname "<code>&lt;&lt;ALL FILES&gt;&gt;</code>" matches
 * all files.</p>
 * 
 * <p>A pathname consisting of a single "<code>*</code>" indicates all the
 * files in the current directory, while a pathname consisting of a single
 * "<code>-</code>" indicates all the files in the current directory and
 * (recursively) all files and subdirectories contained in the current
 * directory. Current directory is following the same definition as
 * "<code>.</code>" by the <code>Java.io.File</code> class.</p>
 * 
 * <h3 id="actions">Actions</h3>
 * 
 * <p>The actions to be granted are passed to the constructor in a string
 * containing a list of zero or more semicolon-separated set of flags. Three
 * groups are defined and are mapped to the equivalent UNIX file permission
 * groups: <i>user</i>, <i>group</i> and <i>world</i>. However interpretation
 * of these groups are left to the implementation: other platforms like set
 * top boxes may map the groups respectively to <i>application</i>,
 * <i>organization</i> and <i>world</i>.</p>
 * 
 * <p>The action string is described by the following grammar (this
 * definition also gives the canonical representation):</p>
 * <pre>
 * action   ::= perm_set [';' perm_set [';' perm_set ] ]
 * perm_set ::= read write execute
 * read     ::= 'r' | '-' | nil
 * write    ::= 'w' | '-' | nil
 * execute  ::= 'x' | '-' | nil
 * </pre>
 * 
 * <p>The order of the <code>perm_set</code>s in an <code>action</code> string
 * is respectively <i>user</i>, <i>group</i> and <i>world</i></p>
 * 
 * <p>The access rights symbols are defined as follows:</p>
 * <dl>
 * <dt><code>r</code></dt>
 * <dd>the read flag. It explicitly allows the given group to read the
 * associated file.</dd>
 * <dt><code>w</code></dt>
 * <dd>the write flag. It explicitly allows the given group to write in
 * associated file.</dd>
 * <dt><code>x</code></dt>
 * <dd>the execute flag. It explicitly allows the given group to execute
 * the associated file.</dd>
 * <dt><code>-</code></dt>
 * <dd>is used to express the no access for a given access right. It is
 * equivalent to <code>nil</code>.</dd>
 * </dl>
 * 
 * <h4>Examples of valid actions</h4>
 * 
 * <dl>
 * <dt>"<code>rwx;rw-;r</code>"</dt>
 * <dd>make a file readable for all groups, writable for <i>user</i> and
 * <i>group</i> and executable for <i>user</i> only.</dd>
 * <dt>"<code>rw;rw;---</code>"</dt>
 * <dd>make a file readable and writable for <i>user</i> and
 * <i>group</i>.</dd>
 * <dt>"<code>rw;rw</code>"</dt>
 * <dd>is equivalent to the previous definition.</dd>
 * <dt>"<code>r;;</code>"</dt>
 * <dd>make a file readable by the <i>user</i> and deny any other action
 * by any other groups.</dd>
 * <dt>"<code>r</code>"</dt>
 * <dd>is equivalent to the previous definition.</dd>
 * <dt>"<code>;;rx</code>"</dt>
 * <dd>is valid and its canonical equivalent representation is
 * "<code>rx;rx;rx</code>" allowing everybody to read and execute
 * that file.</dd>
 * <dt>""</dt>
 * <dd>make a file unaccessible and useless</dd>
 * </dl>
 * 
 * <h3 id='canonical'>Canonical representation and Consistency check</h3>
 * 
 * <p>The canonical representation of file access rights is defined to be
 * the minimal string representation:
 * </p>
 * 
 * <ol>
 * <li>for that access right that follows the grammar defined in the
 * <a href="#actions">Actions</a> section,</li>
 * <li>which is fully consistent with the intent.</li>
 * </ol>
 * 
 * <p>As a consequence and because of the definition given in the Actions
 * section that an <code>action</code> represents the access right given to
 * three groups that are included in each other, any access rights granted to
 * a larger group implies it is also granted to a lower group:
 * <code>user</code> &isin; <code>group</code> &isin; <code>world</code>.
 * 
 * <p>The following table lists different examples and their standardized
 * canonical representation:</p>
 * 
 * <table class="simple">
 * <thead style="font-weight:bold;">
 * <tr><td nowrap>Access-Rights</td>
 * <td nowrap>Canonical</td>
 * <td width="100%">Description</td>
 * </tr>
 * </thead>
 * <tbody>
 * <tr><td>"<code>r;w;x</code>"</td>
 * <td>"<code>rwx;wx;x</code>"</td>
 * <td>Due to included group inheritance, every smaller group inherits
 * from the rights of the larger group.</td>
 * </tr>
 * <tr><td>"<code>;;rx</code>"</td>
 * <td>"<code>rx;rx;rx</code>"</td>
 * <td>Access rights for <code>world</code> also apply to lower-ring
 * (smaller) groups.</td>
 * </tr>
 * <tr><td>"<code>;r;</code>"</td>
 * <td>"<code>r;r</code>"</td>
 * <td>Access rights for <code>group</code> also apply to
 * <code>user</code>. Moreover last semicolon is removed because there
 * are no access rights for <code>world</code>.</td>
 * </tr>
 * <tr><td>"<code>;;</code>"</td>
 * <td>"<code></code>"</td>
 * <td>Semicolons are removed because there are no access right for any
 * groups.</td>
 * </tr>
 * </tbody>
 * </table>
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
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/io/FileAccessRights.html#FileAccessRights(java.lang.String, java.lang.String)">FileAccessRights</A></B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A>&nbsp;path,
 * <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A>&nbsp;actions)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates a new <code>FileAccessRights</code> object with the specified
 * actions.</TD>
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
 * <CODE>&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/io/FileAccessRights.html#getActions()">getActions</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns the "canonical string representation" of the actions.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;boolean</CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/io/FileAccessRights.html#implies(com.sun.dtv.io.FileAccessRights)">implies</A></B>(<A HREF="../../../../com/sun/dtv/io/FileAccessRights.html" title="class in com.sun.dtv.io">FileAccessRights</A>&nbsp;other)</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Checks if this <code>FileAccessRights</code> object "implies" the
 * specified access rights.</TD>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
 * <CODE>&nbsp;<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A></CODE></FONT></TD>
 * <TD><CODE><B><A HREF="../../../../com/sun/dtv/io/FileAccessRights.html#toString()">toString</A></B>()</CODE>
 * 
 * <BR>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns a string describing the access rights.</TD>
 * </TR>
 * </TABLE>
 * &nbsp;<A NAME="methods_inherited_from_class_java.lang.Object"><!-- --></A>
 * <TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
 * <TR BGCOLOR="#EEEEFF" CLASS="TableSubHeadingColor">
 * <TH ALIGN="left"><B>Methods inherited from class java.lang.<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true" title="class or interface in java.lang">Object</A></B></TH>
 * </TR>
 * <TR BGCOLOR="white" CLASS="TableRowColor">
 * <TD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#clone()" title="class or interface in java.lang">clone</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#equals(java.lang.Object)" title="class or interface in java.lang">equals</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#finalize()" title="class or interface in java.lang">finalize</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#getClass()" title="class or interface in java.lang">getClass</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#hashCode()" title="class or interface in java.lang">hashCode</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#notify()" title="class or interface in java.lang">notify</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#notifyAll()" title="class or interface in java.lang">notifyAll</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#wait()" title="class or interface in java.lang">wait</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#wait(long)" title="class or interface in java.lang">wait</A>, <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/Object.html?is-external=true#wait(long, int)" title="class or interface in java.lang">wait</A></CODE></TD>
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
 * <A NAME="FileAccessRights(java.lang.String, java.lang.String)"><!-- --></A><H3>
 * FileAccessRights</H3>
 * <PRE>
 * public <B>FileAccessRights</B>(<A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A>&nbsp;path,
 * <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A>&nbsp;actions)
 * throws <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/NullPointerException.html?is-external=true" title="class or interface in java.lang">NullPointerException</A></PRE>
 * <DD>Creates a new <code>FileAccessRights</code> object with the specified
 * actions.
 * 
 * <p>The <code>path</code> and <code>actions</code> follow the definition
 * described in the overview section</p>
 * <DD><CODE><A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/NullPointerException.html?is-external=true" title="class or interface in java.lang">NullPointerException</A></CODE> - if any of the argument is <code>null</code></DL>
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
 * <A NAME="implies(com.sun.dtv.io.FileAccessRights)"><!-- --></A><H3>
 * implies</H3>
 * <PRE>
 * public boolean <B>implies</B>(<A HREF="../../../../com/sun/dtv/io/FileAccessRights.html" title="class in com.sun.dtv.io">FileAccessRights</A>&nbsp;other)</PRE>
 * <DD>Checks if this <code>FileAccessRights</code> object "implies" the
 * specified access rights.
 * 
 * <p>More specifically, this method returns <code>true</code> if:</p>
 * 
 * <ol>
 * <li><code>other</code> is an instance of
 * <code>FileAccessRights</code>,</li>
 * <li><code>other</code>'s actions are a proper subset of this object's
 * actions, and</li>
 * <li><code>other</code>'s pathname is implied by this object's
 * pathname.
 * For example, "<code>/tmp/*</code>" implies
 * "<code>/tmp/foo</code>", since "<code>/tmp/*</code>" encompasses
 * the "<code>/tmp</code>" directory and all files in that
 * directory, including the one named "<code>foo</code>".</li>
 * </ol>
 * this object, <code>false</code> if not.</DL>
 * <HR>
 * 
 * <A NAME="getActions()"><!-- --></A><H3>
 * getActions</H3>
 * <PRE>
 * public <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>getActions</B>()</PRE>
 * <DD>Returns the "canonical string representation" of the actions.
 * That is, this method always returns present actions in the order and
 * format described in the grammar found in the section
 * '<a href="#actions">Actions</a>' above. The
 * representation <span class="rfc2119">should</span> also be consistent
 * and minimalistic as described in the '<a href="#canonical">Canonical
 * representation and Consistency check</a>' section above.
 * 
 * <HR>
 * 
 * <A NAME="toString()"><!-- --></A><H3>
 * toString</H3>
 * <PRE>
 * public <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</A> <B>toString</B>()</PRE>
 * <DD>Returns a string describing the access rights. The convention is to
 * specify the class name, the path, and the actions
 * (as returned by <A HREF="../../../../com/sun/dtv/io/FileAccessRights.html#getActions()"><CODE>getActions()</CODE></A>) in the following
 * format: '("ClassName" "name" "actions")'.
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
 * <TD BGCOLOR="#EEEEFF" CLASS="NavBarCell1">    <A HREF="class-use/FileAccessRights.html"><FONT CLASS="NavBarFont1"><B>Use</B></FONT></A>&nbsp;</TD>
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
 * &nbsp;<A HREF="../../../../com/sun/dtv/io/FileProperties.html" title="class in com.sun.dtv.io"><B>NEXT CLASS</B></A></FONT></TD>
 * <TD BGCOLOR="white" CLASS="NavBarCell2"><FONT SIZE="-2">
 * <A HREF="../../../../index.html?com/sun/dtv/io/FileAccessRights.html" target="_top"><B>FRAMES</B></A>  &nbsp;
 * &nbsp;<A HREF="FileAccessRights.html" target="_top"><B>NO FRAMES</B></A>  &nbsp;
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
public final class FileAccessRights extends Object
{
	//following variables are implicitely defined by getter- or setter-methods:
	private String actions;

	/**
	 * Creates a new <code>FileAccessRights</code> object with the specified
	 * actions.
	 * 
	 *The <code>path</code> and <code>actions</code> follow the definition
	 * described in the overview section</p>
	 *
	 * 
	 * @param path - The pathname of the file/directory.
	 * @param actions - The action string.
	 * @throws NullPointerException - if any of the argument is null
	 */
	public FileAccessRights( String path, String actions) throws NullPointerException
	{
		//TODO implement FileAccessRights
	}

	/**
	 * Checks if this <code>FileAccessRights</code> object "implies" the
	 * specified access rights.
	 * 
	 *More specifically, this method returns <code>true</code> if:</p>
	 * 
	 * <ol>
	 * <li><code>other</code> is an instance of
	 * <code>FileAccessRights</code>,</li>
	 * <li><code>other</code>'s actions are a proper subset of this object's
	 * actions, and</li>
	 * <li><code>other</code>'s pathname is implied by this object's
	 * pathname.
	 * For example, "<code>/tmp/*</code>" implies
	 * "<code>/tmp/foo</code>", since "<code>/tmp/*</code>" encompasses
	 * the "<code>/tmp</code>" directory and all files in that
	 * directory, including the one named "<code>foo</code>".</li>
	 * </ol>
	 *
	 * 
	 * @param other - The access rights to check against.
	 * @return true if the specified access rights are implied by this object, false if not.
	 */
	public boolean implies( FileAccessRights other)
	{
		return false;
		//TODO implement implies
	}

	/**
	 * Returns the "canonical string representation" of the actions.
	 * That is, this method always returns present actions in the order and
	 * format described in the grammar found in the section
	 * '<a href="#actions">Actions</a>' above. The
	 * representation <span class="rfc2119">should</span> also be consistent
	 * and minimalistic as described in the '<a href="#canonical">Canonical
	 * representation and Consistency check</a>' section above.
	 *
	 * 
	 * 
	 * @return The canonical string representation of the actions.
	 */
	public String getActions()
	{
		return this.actions;
	}

	/**
	 * Returns a string describing the access rights. The convention is to
	 * specify the class name, the path, and the actions
	 * (as returned by <A HREF="../../../../com/sun/dtv/io/FileAccessRights.html#getActions()"><CODE>getActions()</CODE></A>) in the following
	 * format: '("ClassName" "name" "actions")'.
	 *
	 * 
	 * @return Information about this FileAccessRights object.
	 * @see toString in class Object
	 */
	public String toString()
	{
		return null;
		//TODO implement toString
	}

}
