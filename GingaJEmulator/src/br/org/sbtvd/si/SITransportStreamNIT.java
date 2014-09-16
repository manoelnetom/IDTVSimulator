package br.org.sbtvd.si;

/**
 * A interface SITransportStreamNIT indica informações do fluxo de transporte 
 * adquiridas da NIT. Todos os métodos que acessam descritores retornam 
 * informações de descritores adquiridos da NIT.
 * O método que recupera o fluxo de transporte no SIDatabase ou SINetwork 
 * retornam um objeto que implementa essa interface.
 * 
 * @author jonatas
 */

public interface SITransportStreamNIT extends SITransportStream {
	
	/**
	 * 
	 * @return
	 */
	public int getNetworkID();
}
