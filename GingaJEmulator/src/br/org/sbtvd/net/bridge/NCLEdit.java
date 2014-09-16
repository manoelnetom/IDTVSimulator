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
 * A classe NCLEdit oferece métodos para edição de um documento NCL, que
 * encapsulado em um objeto da classe NCLPlayer instancia objetos da classe
 * NCLEdit (método getNCLEdit) associados. Comandos de edição provenientes da
 * instância de NCLEdit alteram somente a apresentação do documento NCL – o
 * documento original é preservado durante todo o processo de edição.
 * 
 */
public class NCLEdit {

	private int ref;

	native boolean addRegion(int ref, String regionBaseId, String regionId, String regionStr);
	native boolean removeRegion(int ref, String regionId);
	native boolean addRegionBase(int ref, String regionBaseStr);
	native boolean removeRegionBase(int ref, String regionBaseId);
	native boolean addRule(int ref, String ruleStr);
	native boolean removeRule(int ref, String ruleId);
	native boolean addRuleBase(int ref, String ruleBaseStr);
	native boolean removeRuleBase(int ref, String ruleBaseId);
	native boolean addConnector(int ref, String connectorStr);
	native boolean removeConnector(int ref, String connectorId);
	native boolean addConnectorBase(int ref, String connectorBaseStr);
	native boolean removeConnectorBase(int ref, String connectorBaseId);
	native boolean addDescriptor(int ref, String descriptorStr);
	native boolean removeDescriptor(int ref, String descriptorId);
	native boolean addDescriptorSwitch(int ref, String descriptorSwitchStr);
	native boolean removeDescriptorSwitch(int ref, String descriptorSwitchId);
	native boolean addDescriptorBase(int ref, String descriptorBaseStr);
	native boolean removeDescriptorBase(int ref, String descriptorBaseId);
	native boolean addTransition(int ref, String transitionStr);
	native boolean removeTransition(int ref, String transitionId);
	native boolean addTransitionBase(int ref, String transitionBaseStr);
	native boolean removeTransitionBase(int ref, String transitionBaseId);
	native boolean addImportBase(int ref, String docBaseId, String importBaseStr);
	native boolean removeImportBase(int ref, String docBaseId, String importBaseId);
	native boolean addImportedDocumentBase(int ref, String importedDocumentBaseStr);
	native boolean removeImportedDocumentBase(int ref, String importedDocumentBaseId);
	native boolean addImportNCL(int ref, String importNCLStr);
	native boolean removeImportNCL(int ref, String importNCLId);
	native boolean addNode(int ref, String compositeId, String nodeStr);
	native boolean removeNode(int ref, String compositeId, String nodeId);
	native boolean addInterface(int ref, String nodeId, String interfaceStr);
	native boolean removeInterface(int ref, String nodeId, String interfaceId);
	native boolean addLink(int ref, String compositeId, String linkStr);
	native boolean removeLink(int ref, String compositeId, String linkId);
	native boolean setPropertyValue(int ref, String propertyId, String value);
	
	NCLEdit(int nclPlayerRef) {
		this.ref = ref;
	}

	/**
	 * Adiciona um elemento &lt;region&gt; no documento NCL
	 * 
	 * @param regionBaseId
	 *            Region base à qual deseja adicionar o elemento.
	 * @param regionId
	 *            Filho ao qual deseja adicionar o elemento.
	 * @param regionStr
	 *            Definição do elemento.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addRegion(String regionBaseId, String regionId,	String regionStr) {
		return addRegion(ref, regionBaseId, regionId,	regionStr);
	}

	/**
	 * Remove o elemento &lt;region&gt; do documento NCL.
	 * 
	 * @param regionId
	 *            Region base à qual o elemento pertence.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeRegion(String regionId) {
		return removeRegion(ref, regionId);
	}

	/**
	 * Adiciona um elemento &lt;regionBase&gt; ao elemento &lt;head&gt; do
	 * documento NCL.
	 * 
	 * @param regionBaseStr
	 *            Descrição do elemento.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addRegionBase(String regionBaseStr) {
		return addRegionBase(ref, regionBaseStr);
	}

	/**
	 * Remove um elemento &lt;regionBase&gt; do documento NCL.
	 * 
	 * @param regionBaseId
	 *            Descrição do elemento que deseja remover.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeRegionBase(String regionBaseId) {
		return removeRegionBase(ref, regionBaseId);
	}

	/**
	 * Adiciona um elemento &lt;rule&gt; como integrante do elemento
	 * &lt;ruleBase&gt; do documento NCL.
	 * 
	 * @param ruleStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addRule(String ruleStr) {
		return addRule(ref, ruleStr);
	}

	/**
	 * Remove um elemento &lt;rule&gt; do elemento &lt;head&gt; do documento
	 * NCL.
	 * 
	 * @param ruleId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeRule(String ruleId) {
		return removeRule(ref, ruleId);
	}

	/**
	 * Adiciona um elemento &lt;ruleBase&gt; como integrante do elemento
	 * &lt;head&gt; do documento NCL.
	 * 
	 * @param ruleBaseStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addRuleBase(String ruleBaseStr) {
		return addRuleBase(ref, ruleBaseStr);
	}

	/**
	 * Remove um elemento &lt;ruleBase&gt; do elemento &lt;head&gt; do documento
	 * NCL.
	 * 
	 * @param ruleBaseId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeRuleBase(String ruleBaseId) {
		return removeRuleBase(ref, ruleBaseId);
	}

	/**
	 * Adiciona um elemento &lt;connector&gt; como integrante do elemento
	 * &lt;connectorBase&gt; do documento NCL.
	 * 
	 * @param connectorStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addConnector(String connectorStr) {
		return addConnector(ref, connectorStr);
	}

	/**
	 * Remove um elemento &lt;connector&gt; do elemento &lt;connectorBase&gt; do
	 * documento NCL.
	 * 
	 * @param connectorId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeConnector(String connectorId) {
		return removeConnector(ref, connectorId);
	}

	/**
	 * Adiciona um elemento &lt;connectorBase&gt; como integrante do elemento
	 * &lt;head&gt; do documento NCL.
	 * 
	 * @param connectorBaseStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addConnectorBase(String connectorBaseStr) {
		return addConnectorBase(ref, connectorBaseStr);
	}

	/**
	 * Remove o elemento &lt;connectorBase&gt; do elemento &lt;head&gt; do
	 * documento NCL.
	 * 
	 * @param connectorBaseId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeConnectorBase(String connectorBaseId) {
		return removeConnectorBase(ref, connectorBaseId);
	}

	/**
	 * Adiciona um elemento &lt;descriptor&gt; como integrante do elemento
	 * &lt;descriptorBase&gt; do documento NCL.
	 * 
	 * @param descriptorStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addDescriptor(String descriptorStr) {
		return addDescriptor(ref, descriptorStr);
	}

	/**
	 * Remove o elemento &lt;descriptor&gt; do elemento &lt;descriptorBase&gt;
	 * do documento NCL.
	 * 
	 * @param descriptorId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeDescriptor(String descriptorId) {
		return removeDescriptor(ref, descriptorId);
	}

	/**
	 * Adiciona um elemento &lt;descriptorSwitch&gt; como integrante do elemento
	 * &lt;descriptorBase&gt; do documento NCL.
	 * 
	 * @param descriptorSwitchStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addDescriptorSwitch(String descriptorSwitchStr) {
		return addDescriptorSwitch(ref, descriptorSwitchStr);
	}

	/**
	 * Remove o elemento &lt;descriptorSwitch&gt; do elemento
	 * &lt;descriptorBase&gt; do documento NCL.
	 * 
	 * @param descriptorSwitchId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeDescriptorSwitch(String descriptorSwitchId) {
		return removeDescriptorSwitch(ref, descriptorSwitchId);
	}

	/**
	 * Adiciona um elemento &lt;descriptorBase&gt; como integrante do elemento
	 * &lt;head&gt; do documento NCL.
	 * 
	 * @param descriptorBaseStr
	 *            Descrição do elemento a ser adicionado
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addDescriptorBase(String descriptorBaseStr) {
		return addDescriptorBase(ref, descriptorBaseStr);
	}

	/**
	 * Remove o elemento &lt;descriptorBase&gt; do elemento &lt;head&gt; do
	 * documento NCL.
	 * 
	 * @param descriptorBaseId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeDescriptorBase(String descriptorBaseId) {
		return removeDescriptorBase(ref,  descriptorBaseId);
	}

	/**
	 * Adiciona um elemento &lt;transition&gt; como integrante do elemento
	 * &lt;transitionBase&gt; do documento NCL.
	 * 
	 * @param transitionStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addTransition(String transitionStr) {
		return addTransition(ref, transitionStr);
	}

	/**
	 * Remove o elemento &lt;transition&gt; do elemento &lt;transitionBase&gt;
	 * do documento NCL.
	 * 
	 * @param transitionId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeTransition(String transitionId) {
		return removeTransition(ref, transitionId);
	}

	/**
	 * Adiciona um elemento &lt;transitionBase&gt; como integrante do elemento
	 * &lt;head&gt; do documento NCL.
	 * 
	 * @param transitionBaseStr
	 *            Descrição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addTransitionBase(String transitionBaseStr) {
		return addTransitionBase(ref, transitionBaseStr);
	}

	/**
	 * Remove o elemento &lt;transitionBase&gt; do elemento &lt;head&gt; do
	 * documento NCL.
	 * 
	 * @param transitionBaseId
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeTransitionBase(String transitionBaseId) {
		return removeTransitionBase(ref, transitionBaseId);
	}

	/**
	 * Adiciona a um elemento base NCL identificado na String docBaseId
	 * (&lt;regionBase&gt;, &lt;descriptorBase&gt;, &lt;ruleBase&gt;,
	 * &lt;transitionBase&gt; ou &lt;connectorBase&gt;) a definição do elemento
	 * &lt;importBase&gt; contida na String <i>importBaseStr</i> no documento
	 * NCL.
	 * 
	 * @param docBaseId
	 *            Elemento base.
	 * @param importBaseStr
	 *            Definicão do elemento a ser adicionado.
	 * 
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addImportBase(String docBaseId, String importBaseStr) {
		return addImportBase(ref, docBaseId, importBaseStr);
	}

	/**
	 * Remove o elemento &lt;importBase&gt; identificado na String
	 * <i>importBaseId</i> de um elemento base NCL identificado na String
	 * <i>docBaseId</i> (&lt;regionBase&gt;, &lt;descriptorBase&gt;,
	 * &lt;ruleBase&gt;, &lt;transitionBase&gt; ou &lt;connectorBase&gt;) do
	 * documento NCL.
	 * 
	 * @param docBaseId
	 *            Elemento base.
	 * @param importBaseId
	 *            Definição do elemento a ser removida.
	 * 
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeImportBase(String docBaseId, String importBaseId) {
		return removeImportBase(ref, docBaseId, importBaseId);
	}

	/**
	 * Adiciona ao elemento &lt;head&gt; do documento NCL a definição do
	 * elemento &lt;importedDocumentBase&gt; contida na String
	 * <i>imortedDocumentBaseStr</i>.
	 * 
	 * @param importedDocumentBaseStr
	 *            Descrição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addImportedDocumentBase(String importedDocumentBaseStr) {
		return addImportedDocumentBase(ref, importedDocumentBaseStr);
	}

	/**
	 * Remove do elemento &lt;head&gt; do documento NCL o elemento
	 * &lt;importedDocumentBase&gt; identificado pela String
	 * importedDocumentBaseId.
	 * 
	 * @param importedDocumentBaseId
	 *            Elemento sa ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeImportedDocumentBase(String importedDocumentBaseId) {
		return removeImportedDocumentBase(ref, importedDocumentBaseId);
	}

	/**
	 * Adiciona ao elemento &lt;importedDocumentBase&gt; do documento NCL a
	 * definição do elemento &lt;importNCL&gt; contida na String importNCLStr.
	 * 
	 * @param importNCLStr
	 *            Definição do elemento a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addImportNCL(String importNCLStr) {
		return addImportNCL(ref, importNCLStr);
	}

	/**
	 * Remove do elemento &lt;importedDocumentBase&gt; do documento NCL o
	 * elemento &lt;importNCL&gt; identificado pela String importNCLId.
	 * 
	 * @param importNCLId
	 *            Definição do elemento a ser removido.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeImportNCL(String importNCLId) {
		return removeImportNCL(ref, importNCLId);
	}

	/**
	 * Adiciona a um nó de composição NCL identificado na String compositeId
	 * (&lt;body&gt;, &lt;context&gt; ou &lt;switch&gt;) a definição de um nó
	 * NCL (&lt;media&gt;, &lt;context&gt; ou &lt;switch&gt;) contida na String
	 * nodeStr.
	 * 
	 * @param compositeId
	 *            Identificação do nó.
	 * @param nodeStr
	 *            Descrição do nó a ser adicionado.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addNode(String compositeId, String nodeStr) {
		return addNode(ref, compositeId, nodeStr);
	}

	/**
	 * Remove de um nó de composição NCL identificado na String compositeId
	 * (&lt;body&gt;, &lt;context&gt; ou &lt;switch&gt;) a definição de um nó
	 * NCL (&lt;media&gt;, &lt;context&gt; ou &lt;switch&gt;) identificada na
	 * String nodeId.
	 * 
	 * @param compositeId
	 *            Identificação do nó de composição.
	 * @param nodeId
	 *            Identificação do nó.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeNode(String compositeId, String nodeId) {
		return removeNode(ref, compositeId, nodeId);
	}

	/**
	 * Adiciona uma interface do NCL (elemento &lt;port&gt;, &lt;area&gt;,
	 * &lt;property&gt; ou &lt;switchPort&gt;) descrita na String interfaceStr a
	 * um nó (elemento &lt;media&gt;, &lt;body&gt;, &lt;context&gt; ou
	 * &lt;switch&gt;) identificado pela String nodeId do documento NCL.
	 * 
	 * @param nodeId
	 *            Identificação do nó.
	 * @param interfaceStr
	 *            Descrição da interface a ser adicionada.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addInterface(String nodeId, String interfaceStr) {
		return addInterface(ref, nodeId, interfaceStr);
	}

	/**
	 * Remove uma interface do NCL (elemento &lt;port&gt;, &lt;area&gt;,
	 * &lt;property&gt; ou &lt;switchPort&gt;) identificado na String
	 * interfaceId de um nó (elemento &lt;media&gt;, &lt;body&gt;,
	 * &lt;context&gt; ou &lt;switch&gt;) identificado pela String nodeId do
	 * documento NCL.
	 * 
	 * @param nodeId
	 *            Identificação do nó.
	 * @param interfaceId
	 *            Descrição da interface a ser removida.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeInterface(String nodeId, String interfaceId) {
		return removeInterface(ref, nodeId, interfaceId);
	}

	/**
	 * Adiciona a um nó de composição NCL identificado na String compositeId
	 * (&lt;body&gt;, &lt;context&gt; ou &lt;switch&gt;) a definição de um
	 * elemento &lt;link&gt; NCL contida na String linkStr.
	 * 
	 * @param compositeId
	 *            Identificação do nó.
	 * @param linkStr
	 *            Definição de um elemento NCL.
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean addLink(String compositeId, String linkStr) {
		return addLink(ref, compositeId, linkStr);
	}

	/**
	 * Remove de um nó de composição NCL identificado na String compositeId
	 * (&lt;body&gt;, &lt;context&gt; ou &lt;switch&gt;) a definição de um
	 * elemento &lt;link&gt; NCL identificada na String linkId.
	 * 
	 * @param compositeId
	 * @param linkId
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean removeLink(String compositeId, String linkId) {
		return removeLink(ref, compositeId, linkId);
	}

	/**
	 * Atribui o valor da String <i>value</i> a uma propriedade identificada por
	 * propertyId. O valor da instância de String propertyId deve
	 * obrigatoriamente identificar um atributo name de um elemento
	 * &lt;property&gt; ou um atributo id de elemento &lt;switchPort&gt;. O
	 * &lt;property&gt; ou &lt;switchPort&gt; deve obrigatoriamente pertencer a
	 * um nó (elemento &lt;body&gt;, &lt;context&gt;, &lt;switch&gt; ou
	 * &lt;media&gt;) de um documento NCL.
	 * 
	 * @param propertyId
	 *            Propriedade de um nó.
	 * @param value
	 *            Valor
	 * @return True: Se houve sucesso. <br>
	 *         False: Se não houve sucesso.
	 */
	public boolean setPropertyValue(String propertyId, String value) {
		return setPropertyValue(ref, propertyId, value);
	}

}























