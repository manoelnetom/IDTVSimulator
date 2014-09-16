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

import java.io.PrintStream;

/**
 * 
 * 
 */
public class TestHarness extends Object
{
	/**
	 * A <code>triggerType</code> used to request synchronization on a given
	 * event between the test client and the test server.
	 * 
	 *It means that the test client has reached a specific state
	 * required for the test and that it is awaiting acknowledgment to
	 * continue the test. Consequently, the implementation of
	 * <A HREF="../../../../com/sun/dtv/test/TestHarness.html#sendTrigger(int, int, java.lang.String)"><CODE>sendTrigger(triggerType, testId,
	 * message)</CODE></A> will wait until such a confirmation is received before
	 * returning.</p>
	 * 
	 *Along with the <code>triggerType</code> and the
	 * <code>testId</code>, the application <span class="rfc2119">may</span>
	 * use the <code>message</code> argument to specify which
	 * synchronization milestone it has reached. It is recommended to
	 * compose that message with a part that can be parsed (for example an
	 * integer code) and a human readable part (i.e. a short
	 * description) to allow both a automated (test server) and/or human
	 * supported implementation of this feature.</p>
	 *
	 *
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.TestHarness.TRIGGER_SYNCHRONIZE">Constant Field Values</A></DL>
	 * 
	 */
	public static final int TRIGGER_SYNCHRONIZE = 2;

	/**
	 * A <code>triggerType</code> used to signal that the test identified by
	 * <code>testId</code> is now considered finished.
	 * 
	 *Along with the <code>triggerType</code> and the
	 * <code>testId</code> when calling <A HREF="../../../../com/sun/dtv/test/TestHarness.html#sendTrigger(int, int, java.lang.String)"><CODE>sendTrigger(triggerType, testId, message)</CODE></A>, the application <span
	 * class="rfc2119">must</span> supply the resulting <code>Report</code>
	 * in the <code>message</code> argument by calling the corresponding
	 * <code>Report.toString()</code> method.</p>
	 *
	 *
	 * <A HREF="../../../../constant-values.html#com.sun.dtv.test.TestHarness.TRIGGER_TERMINATED">Constant Field Values</A></DL>
	 * 
	 */
	public static final int TRIGGER_TERMINATED = 3;

	/**
	 * The default output stream on a given platform to store test reports.
	 * It is left to the implementation where and how to store this log stream.
	 * Implementations <span class="rfc2119">may</span> choose to store it
	 * as a file on the file system or to forward this stream on a serial or
	 * networking interface that is connected to the test server.
	 * 
	 *This <code>log</code> <span class="rfc2119">must</span> be configured
	 * with <code>autoFlush=true</code>.</p>
	 *
	 *
	 * 
	 * 
	 */
	public static final PrintStream log = null;

	/**
	 * 
	 * 
	 */
	public TestHarness()
	{
		//TODO implement TestHarness
	}

	/**
	 * This generic method allows to send a specific trigger to the test
	 * server. Depending on <code>triggerType</code>, the method will
	 * behave differently.
	 * 
	 *Currently the following two triggers
	 * <span class="rfc2119">must</span> be supported by the implementation.
	 * This specification <span class="rfc2119">may</span> define new
	 * triggers in subsequent versions:</p>
	 * 
	 * <ul>
	 * <li><A HREF="../../../../com/sun/dtv/test/TestHarness.html#TRIGGER_SYNCHRONIZE"><CODE>TRIGGER_SYNCHRONIZE</CODE></A></li>
	 * <li><A HREF="../../../../com/sun/dtv/test/TestHarness.html#TRIGGER_TERMINATED"><CODE>TRIGGER_TERMINATED</CODE></A></li>
	 * </ul>
	 * 
	 *It is left to the implementation how to provide the communication
	 * mean between the test client and the test server. It
	 * <span class="rfc2119">may</span> use one of the following
	 * technologies: Serial, USB, Networking, ...</p>
	 * 
	 *However, at last resort and in conjunction with any communication
	 * mean between client and server, the implementation
	 * <span class="rfc2119">shall</span> display a dialog with the
	 * corresponding message to the user or test operator on the screen
	 * of the platform.</p>
	 *
	 * 
	 * @param triggerType - one of any TRIGGER_ constant defined  in this class.
	 * @param testId - an integer value identifying the test. This value can be defined by the test case itself or via arguments (see TestCase).
	 * @param message - depending on the triggerType, this string may have different meaning. This argument may be null.
	 * @throws UnsupportedOperationException - if triggerType is no supported by the platform.
	 */
	public static void sendTrigger(int triggerType, int testId, String message) throws UnsupportedOperationException
	{
		//TODO implement sendTrigger
	}

	/**
	 * This alias method allows to send the report of the given testcase back
	 * to the test server. If no return channel is defined, according
	 * to the description of <code>TestHarness.TRIGGER_TERMINATED</code>,
	 * the report string will be displayed on the screen.
	 * 
	 *This method is equivalent to calling:</p>
	 * <pre class="codeSample">
	 * TestHarness.sendTrigger(TestHarness.TRIGGER_TERMINATED,
	 * <i>testid</i>,
	 * <i>report</i>.toString());</pre>
	 *
	 * 
	 * @param testid - an integer value identifying the test. This value  can be defined by the test case itself or via arguments (see TestCase).
	 * @param report - The report to be sent.
	 * @throws NullPointerException - if report is null
	 */
	public static void sendReport(int testid, Report report) throws NullPointerException
	{
		//TODO implement sendReport
	}

	/**
	 * This method is responsible for launching a given test case, handling
	 * the resulting report and ending the current test application.
	 * 
	 *If the platform provides a return channel, it will return the
	 * report to the test server using <A HREF="../../../../com/sun/dtv/test/TestHarness.html#sendReport(int, com.sun.dtv.test.Report)"><CODE>sendReport()</CODE></A> allowing thereby the server to start the next
	 * test.</p>
	 * 
	 *The implementation <span class="rfc2119">must</span> call
	 * at last <code>Report.exit()</code> method which consequently
	 * provokes the application to end.</p>
	 * 
	 *An implementation is likely to be based on:</p>
	 * <pre class="codeSample">
	 * if (test != null) {
	 * Report r = test.run(args);
	 * TestHarness.sendReport(test.getTestId(), r);
	 * r.exit();
	 * }</pre>
	 *
	 * 
	 * @param test - The test case instance to be launched.
	 * @param args - The list of argument that are passed to the test cases.  Usually are those from the command line.
	 */
	public static void start( TestCase test, String[] args)
	{
		//TODO implement start
	}

}
