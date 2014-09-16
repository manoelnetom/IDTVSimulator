/**
 * Developed for the GingaCDN Project
 * (http://gingacdn.lavid.ufpb.br)
 * Copyright (C) 2009 company.
 *
 * This file is part of GingaCDN
 *
 * GingaCDN is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation,
 * Inc.,51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * @author Marcos Paulino Roriz Junior - INF-UFG - marcosroriz@inf.ufg.br
 * @author Marco Aurélio Lino Massarani - INF-UFG - massarani.inf@gmail.com
 *
 * @version $Revision$
 * 
 */

package br.org.sbtvd.net.bridge.ncl;

/**
 * A interface Listener que deve ser implementada por quem deseja receber
 * notificação de eventos distribuídos pelo formatador NCL manipulando um
 * documento NCL que inclui um Xlet Ginga-J, sendo objetos que são elementos da
 * classe NCLEvent. A aplicação que deseja monitorar os eventos gerados pelo
 * formatador NCL deve implementar esta interface.
 */
public interface NCLEventListener {

	/**
	 * Método executado quando um evento NCLEvent é gerado pelo formatador NCL.
	 * 
	 * @param event
	 *            Um evento, {@link NCLEvent}, para ser tratado.
	 */
	public void NCLPlayerEventDispatched(NCLEvent event);
}
