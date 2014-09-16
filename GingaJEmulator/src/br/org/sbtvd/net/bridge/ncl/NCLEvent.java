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

package br.org.sbtvd.net.bridge.ncl;

/**
 * A classe NCLEvent é a classe de evento raiz para todos os eventos gerados
 * pelo formatador NCL que manipula um documento incluindo um Xlet Ginga-J. As
 * máscaras de evento definidas nesta classe são utilizadas para especificar
 * quais tipos de eventos um NCLEventListener deve ouvir.
 * 
 * @see ABNT NBR 15606-2:2007, 10.3.4.3 e 11.2
 */
public class NCLEvent {

	public final static int PRESENTATION_START = 1;
	public final static int PRESENTATION_STOP = 2;
	public final static int PRESENTATION_ABORT = 3;
	public final static int PRESENTATION_PAUSE = 4;
	public final static int PRESENTATION_RESUME = 5;
	public final static int ATTRIBUTION_SET = 6;
	protected int id;

	/**
	 * Retorna o tipo de evento;
	 * 
	 * @return id do evento;
	 */
	public int getID() {
		// TODO
		return id;
	}

	/**
	 * Retorna o identificador do nó ou âncora relacionado ao evento.
	 * 
	 * @return id do nó ou ancôra relacionado ao evento.
	 */
	public native String getValue();

}
