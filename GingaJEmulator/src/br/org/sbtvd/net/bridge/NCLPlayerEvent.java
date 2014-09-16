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
 * A classe NCLPlayerEvent é o evento raiz para todos os eventos NCL. As
 * máscaras de evento definidas nesta classe são utilizadas para especificar
 * quais tipos de eventos um NCLPlayerEventListener deve ouvir.
 * 
 * @see ABNT NBR 15606-2:2007, 10.3.4.3
 * 
 */
public class NCLPlayerEvent {
	/**
	 * Máscara de evento para eventos de apresentação (tipo presentation) cuja
	 * ação (campo action) foi o início da reprodução (start) de um nó ou
	 * âncora.
	 */
	public final static int PRESENTATION_START = 1;

	/**
	 * Máscara de evento para eventos de apresentação (tipo presentation) cuja
	 * ação (campo action) foi o término da reprodução (stop) de um nó ou
	 * âncora.
	 */
	public final static int PRESENTATION_STOP = 2;

	/**
	 * Máscara de evento para eventos de apresentação (tipo presentation) cuja
	 * ação (campo action) foi o abortamento da reprodução (abort) de um nó ou
	 * âncora.
	 */
	public final static int PRESENTATION_ABORT = 3;

	/**
	 * Máscara de evento para eventos de apresentação (tipo presentation) cuja
	 * ação (campo action) foi a pausa da reprodução (pause) de um nó ou âncora.
	 */
	public final static int PRESENTATION_PAUSE = 4;

	/**
	 * Máscara de evento para eventos de apresentação (tipo presentation) cuja
	 * ação (campo action) foi a retomada da reprodução (resume) de um nó ou
	 * âncora.
	 */
	public final static int PRESENTATION_RESUME = 5;

	/**
	 * Máscara de evento para eventos de atribuição (tipo attribution) cuja ação
	 * (campo action) foi a definição (set) de um parâmetro de um nó ou âncora.
	 */
	public final static int ATTRIBUTION_SET = 6;

	/**
	 * Identificação do evento.
	 */
	protected int id;

	/**
	 * Retorna o tipo de evento.
	 * 
	 * @return Tipo de evento.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Retorna o identificador do nó ou âncora relacionado ao evento.
	 * 
	 * @return Identificador do nó ou âncora relacionado ao evento.
	 */
	public native String getValue();

}
