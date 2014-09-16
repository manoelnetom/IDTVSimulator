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
public class SingleFilter extends DataSectionFilter {

    SingleFilter() {
    }

    SingleFilter(int sectionSize) {
    }

    /**
     * This method retrieves a single
     * <code>DataSection</code> object describing a section which matched the
     * active filter definition. This method will never block and will return
     * null if no section has yet matched.
     *
     * Each time a new filtering operation is started, a new
     * <code>DataSection</code> object will be created. All references except
     * any in the application to the previous
     * <code>DataSection</code> object will be removed. All data accessing
     * methods on the previous DataSection object will throw a
     * <code>NoDataAvailableException</code>.
     *
     *
     *
     * @return the single matched DataSection object or null.
     * @throws FilterInterruptException - if the filter was interrupted before
     * the required amount of data was filtered.
     */
    public DataSection getSection() throws FilterInterruptException {
        return null;
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

    private void notifyListener(int event) {
        /*
        DataSectionFilterEvent filterEvent = null;;
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
