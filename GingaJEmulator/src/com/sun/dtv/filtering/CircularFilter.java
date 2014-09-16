/**
 * ****************************************************************************
 * Este arquivo eh parte da implementacao do Projeto OpenGinga
 *
 * Direitos Autorais Reservados (c) 2005-2009 UFPB/LAVID
 *
 * Este programa eh software livre; voce pode redistribui-lo e/ou modificah-lo
 * sob os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela
 * Free Software Foundation.
 *
 * Este programa eh distribuido na expectativa de que seja util, porem, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implicita de COMERCIABILIDADE OU
 * ADEQUACAO A UMA FINALIDADE ESPECIFICA. Consulte a Licenca Publica Geral do
 * GNU versao 2 para mais detalhes.
 *
 * Voce deve ter recebido uma copia da Licenca Publica Geral do GNU versao 2
 * junto com este programa; se nao, escreva para a Free Software Foundation,
 * Inc., no endereco 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 *
 * Para maiores informacoes: ginga
 *
 * @ lavid.ufpb.br http://www.openginga.org http://www.ginga.org.br
 * http://www.lavid.ufpb.br
 * ******************************************************************************
 * This file is part of OpenGinga Project
 *
 * Copyright: 2005-2009 UFPB/LAVID, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License version 2 for
 * more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 *
 * For further information contact: ginga
 * @ lavid.ufpb.br http://www.openginga.org http://www.ginga.org.br
 * http://www.lavid.ufpb.br
 * ******************************************************************************
 */
package com.sun.dtv.filtering;

import com.sun.dtv.transport.ConditionalAccessDeniedException;

/**
 *
 */
public class CircularFilter extends DataSectionFilter {
    //following variables are implicitely defined by getter- or setter-methods:

    CircularFilter(int numberOfEntries) {
    }

    CircularFilter(int numberOfEntries, int sectionSize) {
    }

    public void startFiltering(Object refObj) throws FilterResourceUnavailableException, ConditionalAccessDeniedException, InvalidFilterException, DisconnectedException {
        super.startFiltering(refObj);
        int tableIdEx = this.positiveFilter[3];
        tableIdEx <<= 8;
        tableIdEx = tableIdEx & this.positiveFilter[4];
        int mask = this.positiveMask[3];
        mask <<= 8;
        mask = mask & this.positiveMask[4];
        tableIdEx = tableIdEx & mask;
        if (tableIdEx == 0) {
            tableIdEx = -1;
        }
    }

    /**
     * This method returns the
     * <code>DataSection</code> array for the
     * <code>CircularFilter</code> The application needs to verify which objects
     * contain valid data.
     *
     *
     *
     * @return The array of DataSection objects
     */
    public DataSection[] getSections() {
        return null;
    }

}
