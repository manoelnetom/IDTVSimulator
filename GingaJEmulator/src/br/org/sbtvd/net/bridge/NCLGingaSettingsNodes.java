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
 * A classe NCLGingaSettingsNodes é a classe que representa um nó NCL cujos
 * atributos são variáveis globais definidas pelo autor do documento ou
 * variáveis de ambiente que podem ser manipuladas pelo processamento do
 * documento NCL. A lista completa dessas variáveis de ambiente é apresentada na
 * ABNT NBR 15606-2.
 * 
 * @see ABNT NBR 15606-2.
 * 
 */
public class NCLGingaSettingsNodes {

	/**
	 * Método construtor para NCLGingaSettingsNodes.
	 * 
	 * @param nodeId
	 *            Identificador único.
	 */
	public NCLGingaSettingsNodes(java.lang.String nodeId) {
		// TODO: Implementar este método
	}

	/**
	 * Retorna o valor da variável de acordo com a descrição da variável de
	 * ambiente passada como String. A lista completa de variáveis de ambiente
	 * disponíveis encontra-se na ABNT NBR 15606- 2:2007, Tabela 12.
	 * 
	 * @param value
	 *            Descrição da variável de ambiente.
	 * @return Valor da variável.
	 * 
	 * @see ABNT NBR 15606-2.
	 */
	public native String getValue(String value);
}
