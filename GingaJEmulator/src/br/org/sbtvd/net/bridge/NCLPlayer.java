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

import java.util.*;

/**
 * A classe NCLPlayer é uma classe que representa um exibidor para um documento
 * NCL, sendo um componente gráfico que estende java.awt.Component. O tratamento
 * dos eventos de entrada (de teclas, por exemplo) será feito pelo exibidor NCL
 * quando o NCLPlayer possuir o foco de interação.
 */
public class NCLPlayer extends java.awt.Component {

	static {
		System.loadLibrary("nativebridge");
		
		NCLPlayerInit();
	}

	/**
	 * Serial UID para serialização
	 */
	private static final long serialVersionUID = 7109343456993131596L;

	public static final int EVENT_MASK_START 				= 1;
	public static final int EVENT_MASK_STOP 				= 2;
	public static final int EVENT_MASK_PAUSE 				= 4;
	public static final int EVENT_MASK_RESUME 			= 8;
	public static final int EVENT_MASK_ABORT 				= 16;
	public static final int EVENT_MASK_ATTRIBUTION	= 32;

	/**
	 * Constante que identifica um documento em execução.
	 */
	public static final int PLAYING = 1;

	/**
	 * Constante que identifica um documento pausado.
	 */
	public static final int PAUSED = 2;

	/**
	 * Constante que identifica um documento parado.
	 */
	public static final int STOPPED = 3;

	private List list;
	private java.net.URL documentURL;
	private NCLEdit nclEdit;
	private int ref;

	class NCLPlayerEventMaskListener {

		private NCLPlayerEventListener listener;
		public long mask;

		public NCLPlayerEventMaskListener(NCLPlayerEventListener listener, long mask) {
			this.listener = listener;
			this.mask = mask;
		}

		public NCLPlayerEventListener getListener() {
			return this.listener;
		}

		public long getEventMask() {
			return this.mask;
		}
	}

	/**
	 * Método construtor para NCLPlayer.
	 * 
	 * @param documentURL
	 *            instância da classe java.net.URL que funciona como localizador
	 *            do documento.
	 */
	public NCLPlayer(java.net.URL documentURL) {
		list = new java.util.ArrayList();
		ref = createRef();
		nclEdit = new NCLEdit(ref);
		setDocument(documentURL);	
	}

	/**
	 * Inicializa variaveis necessarias para a execucao da maquina GingaNCL.
	 * 
	 */
	public native static void NCLPlayerInit();

	public native int createRef();
	public native int destroyRef(int ref);
	public native int getNativeStatus(int ref);
	public native void setNativeDocument(int ref, String document);
	public native boolean startNativeDocument(int ref, java.lang.String interfaceId, int x, int y, int w, int h);
	public native boolean stopNativeDocument(int ref);
	public native boolean pauseNativeDocument(int ref);
	public native boolean resumeNativeDocument(int ref);
	public native boolean destroyNativeDocument(int ref);
	public native void setNativeInputEvents(int ref, boolean b);

	/**
	 * Este método registra um NCLPlayerEventListener para receber todos os
	 * NCLPlayerEvents distribuídos pela máquina associada ao nó que se vincula
	 * ao valor long eventMask fornecido.
	 * 
	 * @param listener
	 *            NCLPlayerEventListener que recebe os NCLPlayerEvents.
	 * @param nclPlayerEventPlayerMask
	 *            Valor que vincula a máquina associada ao nó.
	 */
	public void addNCLPlayerEventListener(NCLPlayerEventListener listener, long nclPlayerEventPlayerMask) {
		list.add(new NCLPlayerEventMaskListener(listener, nclPlayerEventPlayerMask));
	}

	/**
	 * Remove um NCLPlayerEventListener da classe de recepção dos
	 * NCLPlayerEvents distribuídos.
	 * 
	 * @param listener
	 *            NCLPlayerEventListener a ser removido.
	 */
	public void removeNCLPlayerEventListener(NCLPlayerEventListener listener) {
		// TODO::
	}

	/**
	 * Retorna uma lista de todos os NCLPlayerEventListeners registrados neste
	 * NCLPlayer. Note que os objetos ouvintes adicionados várias vezes aparecem
	 * apenas uma vez na lista retornada.
	 * 
	 * @return Uma lista de todos os NCLPlayerEventListeners registrados neste
	 *         NCLPlayer.
	 */
	public NCLPlayerEventListener[] getANCLPlayerEventListeners() {
		NCLPlayerEventListener array[] = new NCLPlayerEventListener[list.size()];

		for (int i=0; i<list.size(); i++) {
			NCLPlayerEventMaskListener obj = (NCLPlayerEventMaskListener)list.get(i);

			array[i] = obj.getListener();
		}

		return array;
	}

	/**
	 * Retorna uma lista de todos os NCLPlayerEventListeners registrados neste
	 * NCLPlayer que ouvem a todos os tipos de eventos indicados no valor long
	 * eventMask. Os objetos ouvintes adicionados várias vezes aparecem apenas
	 * uma vez na lista retornada.
	 * 
	 * @param nclPlayerEventMask
	 *            Valor que relaciona os tipos de eventos que deseja obter.
	 * @return Lista de todos os NCLPlayerEventListeners registrados neste
	 *         NCLPlayer que ouvem a todos os tipos de eventos indicados no
	 *         valor long eventMask.
	 */
	public NCLPlayerEventListener[] getANCLPlayerEventListeners(long nclPlayerEventMask) {
		NCLPlayerEventListener array[] = new NCLPlayerEventListener[list.size()];

		for (int i=0; i<list.size(); i++) {
			NCLPlayerEventMaskListener obj = (NCLPlayerEventMaskListener)list.get(i);

			if ((obj.getEventMask() & nclPlayerEventMask) != 0) {
				array[i] = obj.getListener();
			}
		}

		return array;
	}

	/**
	 * Retorna um objeto da classe java.net.URL que é o localizador do documento
	 * NCL sendo manipulado pelo objeto NCLPlayer em questão.
	 * 
	 * @return Objeto localizador do documento NCL sendo manipulado pelo objeto
	 *         NCLPlayer em questão.
	 */
	public java.net.URL getDocumentURL() {
		return this.documentURL;
	}

	/**
	 * Retorna uma instância de String com o valor da propriedade definida pelo
	 * parâmetro String propertyId.
	 * 
	 * @param propertyId
	 *            Propriedade que deseja obter o valor.
	 * @return Valor da propriedade definida pelo parâmetro String propertyId.
	 */
	public java.lang.String getPropertyValue(java.lang.String propertyId) {
		return "";
	}

	/**
	 * Retorna um valor inteiro representando o estado do objeto NCLPlayer.
	 * 
	 * @return PLAYING: Se está em execução <br>
	 *         PAUSED: Se está pausado <br>
	 *         STOPPED: Se está parado.
	 */
	public int getStatus() {
		return getNativeStatus(ref);
	}

	/**
	 * Define o documento NCL a ser manipulado pelo NCLPlayer.
	 * 
	 * @param documentURL
	 *            Identificador do documento.
	 */
	public void setDocument(java.net.URL documentURL) {
		if (documentURL == null) {
			// TODO:: throw exception
		}

		this.documentURL = documentURL;

		setNativeDocument(ref, documentURL.getPath());
	}

	/*
	 * O novo estado de execução do NCLPlayer deverá ser definido como parado
	 * (STOPPED).
	 */;

	/**
	 * Começa a reprodução de um documento NCL.
	 * 
	 * @param interfaceId
	 *            Interface de documento pela qual deseja começar a
	 *            apresentação.
	 * 
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean startDocument(java.lang.String interfaceId) {
		System.out.println("JAVA:::: " + ref);
		return startNativeDocument(this.ref, interfaceId, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Pára a apresentação de um documento NCL.
	 * 
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean stopDocument() {
		return stopNativeDocument(ref);
	}
	
		/**
	 * Todos os eventos do documento que estão ocorrendo devem obrigatoriamente
	 * serem parados.
	 */

	/**
	 * Pausa a apresentação de um documento NCL.
	 * 
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean pauseDocument() {
		return pauseNativeDocument(ref);
	}
	
	/**
	 * Todos os eventos do documento que estão ocorrendo devem obrigatoriamente
	 * serem pausados.
	 */

	/**
	 * Retoma a apresentação de um documento NCL.
	 * 
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean resumeDocument(int ref) {
		return resumeNativeDocument(ref);
	}
	
	/**
	 * Todos os eventos de documentos que foram previamente pausados pelo método
	 * pauseDocument() devem obrigatoriamente serem retomados
	 */

	public boolean destroyDocument(int ref) {
		return destroyNativeDocument(ref);
	}

	/**
	 * Retorna uma instância da classe NCLEdit, que oferece funcionalidades de
	 * edição do documento NCL em tempo de exibição.
	 * 
	 * @return Instância da classe NCLEdit
	 */
	public NCLEdit getNCLEdit() {
		return this.nclEdit;
	}

	/* LISTENER GOT FOCUS
		if (isFocusOwner() == true) {
			setNativeInputEvents(true);
		}
	*/

	/* LISTENER LOST FOCUS
		setNativeInputEvents(false);
	*/
}
