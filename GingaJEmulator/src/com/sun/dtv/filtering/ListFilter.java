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
public class ListFilter extends DataSectionFilter {

    ListFilter() {
    }

    ListFilter(int sectionSize) {
    }

    /**
     * This method returns an array of
     * <code>DataSection</code> objects corresponding to the sections of the
     * table. The sections in the array will be ordered according to their
     * section_number. Any sections which have not yet been filtered from the
     * source will have the corresponding entry in the array set to null. If no
     * sections have been filtered then this method will block until at least
     * one section is available or filtering stops.
     *
     *
     * Repeated calls to this method will return the same array, provided that
     * no new calls to
     * <code>startFiltering</code> have been made in the interim. Each time a
     * new filtering operation is started, a new array of
     * <code>DataSection</code> objects will be created. All references except
     * any in the application to the previous array and DataSection objects will
     * be removed. All data accessing methods on the previous
     * <code>DataSection</code> objects will throw a
     * <code>DataUnavailableException</code>.
     *
     *
     *
     * @return The array of DataSection objects
     * @throws FilterInterruptException - if filtering stops before one section
     * is available
     */
    public DataSection[] getSections() throws FilterInterruptException {
        return null;
    }

    public void startFiltering(Object refObj)
            throws FilterResourceUnavailableException,
            ConditionalAccessDeniedException, InvalidFilterException,
            DisconnectedException {
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

    private void notifyListener(int event) {
        /*
        DataSectionFilterEvent filterEvent = null;
        FilterEventListener eventListener;
        if (event == SectionListener.AVAILABLE) {
            filterEvent = new DataSectionAvailableEvent(this, this.refObj);
        } else if (event == SectionListener.TIMEOUT) {
            filterEvent = new FilterTimedOutEvent(this, this.refObj);
        } else if (event == SectionListener.STOP) {
            filterEvent = new FilteringStoppedEvent(this, this.refObj);
        }
        for (int i = 0; i < this.listListeners.size(); i++) {
            eventListener = (FilterEventListener) this.listListeners.get(i);
            eventListener.dataSectionFilterUpdate(filterEvent);
        }
        */
    }
}