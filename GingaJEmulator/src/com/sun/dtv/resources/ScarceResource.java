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
 * 
 */
public interface ScarceResource
{
	/**
	 * Requests reservation of the given scarce resource instance. The
	 * method will block until this instance is available. The method
	 * returns normally only if the reservation succeeded. All other cases
	 * are handled by exceptions.
	 * 
	 *First, if there is a security manager, its
	 * <code>checkPermission</code> method is called with the permission
	 * <code>ScarceResourcePermission(name, "reserve")</code>. If
	 * <code>force</code> is moreover set to <code>true</code>, then
	 * the permission is also checked on <code>ScarceResourcePermission(name,
	 * "force")</code>.</p>
	 * 
	 *During the reservation process, if that resource instance is already
	 * allocated, the implementation <span class="rfc2119">must</span> notify
	 * the current owner of that resource about the reservation request via
	 * the <code>ScarceResourceListener</code> interface:</p>
	 * 
	 * <ul>
	 * 
	 * <li>either by
	 * <code>ScarceResourceListener.releaseRequested()</code> if
	 * <code>force</code> is <code>false</code>,</li>
	 * 
	 * <li>or by
	 * <code>ScarceResourceListener.releaseForced()</code> in the
	 * other case.</li>
	 * 
	 * </ul>
	 * 
	 *The listener will be used for such notification only until this
	 * resource is released. After releasing, the implementation <span
	 * class="rfc2119">must not</span> call any of the listener's interface
	 * methods.</p>
	 * 
	 *After that first event, the implementation will proceed accordingly
	 * and release (or not) the requested resource. In case the
	 * implementation releases the resource, it will trigger a
	 * <code>ScarceResourceListener.released()</code> event to the
	 * original listening owner of the resource to inform him that the
	 * resource does not belong to him anymore.</p>
	 * 
	 *The application may control the time to wait for such a resource
	 * to be available by setting <code>timeoutms</code>. In case the
	 * duration of the reservation exceeds the time expressed in
	 * <code>timeoutms</code>, a <code>TimeoutException</code> is thrown.</p>
	 * 
	 *Under normal operation, resources are released using the
	 * <code>release</code> method. However, in the case where the application
	 * does not release resources when requested or the application is
	 * terminated, the implementation <span class="rfc2119">must</span> release
	 * all resources allocated to the application to allow other applications
	 * to be notified of changes in resource allocation and to be able to
	 * reserve them. See the
	 * <a href="../../../../com/sun/dtv/application/package-summary.html#resourcecleanup">Resource Cleanup</a>
	 * section of the application lifecycle.</p>
	 *
	 * 
	 * @param force - If true, this method will withdraw the resource from the current owner. If false, the implementation will block and wait until the resource is made available (using release()) or until timeoutms.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for the resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @throws SecurityException - If the application has no permission for the reserve action for the resource it is about to reserve. Also thrown if force is set to true but the application has no permission for the force action.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 */
	void reserve(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException, SecurityException;

	/**
	 * Releases this resource.
	 * 
	 *The resource will be made available to another application across
	 * the platform. After calling this method, it is no more possible to
	 * interact with the given resource: calls to critical methods of that
	 * scarce resource must be ignored and <span class="rfc2119">may</span>
	 * throw <code>IllegalStateException</code>. This assertion is valid and
	 * the behavior required for any class implementing the
	 * <code>ScarceResource</code> interface. In order to interact
	 * again with the given resource, the application must call the
	 * <code>reserve()</code> method and become the owner again.</p>
	 * 
	 *The implementation <span class="rfc2119">may</span> dispose any
	 * system resources that this object is using. After the implementation
	 * <span class="rfc2119">must</span> not call any of the methods of the
	 * listener that was attached at reservation time.</p>
	 * 
	 *If the resource was already available (i.e. not reserved), this
	 * method does nothing.</p>
	 *
	 * 
	 */
	void release();

	/**
	 * Checks whether the given resource is currently available for
	 * reservation. The returned value gives the current situation
	 * and does not guarantee that the resource will still be available
	 * at a later moment e.g. at reservation time: another application may
	 * have taken that resource in the meantime.
	 *
	 * 
	 * 
	 * @return A boolean set to true if the given resource is  currently available for reservation.
	 */
	boolean isAvailable();

}
