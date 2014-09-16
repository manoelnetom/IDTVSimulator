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
 * @see NormaGinga-J, Version: 090508 001 85 006-4
 */

package br.org.sbtvd.net.bridge;

/**
 * 
 * A interface Listener deve ser implementada por quem deseja receber
 * notificação de eventos distribuídos para objetos que são elementos da classe
 * NCLEvent. A aplicação que se interessa em monitorar os eventos NCL de um
 * NCLPlayer implementa esta interface registrando-se com o NCLPlayer através do
 * método NCLPlayer.addNCLPlayerEventListener(). Quando um evento é distribuído
 * no NCLPlayer, o método eventDispatched desse objeto é executado.
 * 
 */
public interface NCLPlayerEventListener {
	/**
	 * Método executado quando um evento é distribuído no NCLPlayer.
	 * 
	 * @param event
	 *            Evento.
	 */
	public void NCLPlayerEventDispatched(NCLPlayerEvent event);

}
