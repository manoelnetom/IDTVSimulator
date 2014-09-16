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
package com.sun.dtv.net;

import java.net.Authenticator;
import java.net.NetworkInterface;

import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResourceListener;

public class NetworkDevice {

	public final static int TYPE_ADSL = 110;

	public final static int TYPE_ANY = 4095;

	public final static int TYPE_DIAL_UP = 101;

	public final static int TYPE_DOCSIS = 801;

	public final static int TYPE_ETHERNET_DHCP = 403;

	public final static int TYPE_ETHERNET_FIXED_IP = 402;

	public final static int TYPE_ETHERNET_PPPOE = 401;

	public final static int TYPE_FTTH = 901;

	public final static int TYPE_ISDN = 200;

	public final static int TYPE_MOBILE_PHONE = 300;

	public final static int TYPE_MOBILE_PHONE_CDAM_EVDO = 311;

	public final static int TYPE_MOBILE_PHONE_CDMA_1XRTT = 310;

	public final static int TYPE_MOBILE_PHONE_CDMA_EVDO = 321;

	public final static int TYPE_MOBILE_PHONE_GSM_EDGE = 302;

	public final static int TYPE_MOBILE_PHONE_GSM_GPRS = 320;

	public final static int TYPE_OTHER = 100;

	public final static int TYPE_WIFI = 701;

	public final static int TYPE_WIMAX = 601;

	public static void addResourceTypeListener(ResourceTypeListener listener) {

	}

	public void connect(Authenticator auth, String profile,
			NetworkDeviceStatusListener listener, long inactivityTimeout) {

	}

	public void disconnect() {

	}

	public String getConnectionProfile() {
		return null;
	}

	public static NetworkDevice[] getInstances(int type) {
		return null;

	}

	public long getMaximumSpeed() {
		return 0;

	}

	public NetworkInterface getNetworkInterface() {
		return null;

	}

	public int getType() {
		return 0;
	}

	public boolean isAvailable() {
		return false;
	}

	public boolean isConnected() {
		return false;

	}

	void release() {

	}

	static void removeResourceTypeListener(ResourceTypeListener listener) {

	}

	void reserve(boolean force, long timeoutms, ScarceResourceListener listener) {

	}

	static NetworkDevice reserveOne(boolean force, long timeoutms,
			ScarceResourceListener listener, int type) {
		return null;

	}

}
