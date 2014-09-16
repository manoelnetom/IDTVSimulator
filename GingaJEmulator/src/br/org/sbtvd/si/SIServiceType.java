package br.org.sbtvd.si;

public interface SIServiceType {
	
	/**
	 * 
	 */
	public static final short UNKNOWN = -1; 
	
	/**
	 * 
	 */
	public static final short DIGITAL_TELEVISION = 1;
	
	/**
	 * 
	 */
	public static final short DIGITAL_AUDIO = 2;
	
	/**
	 * 
	 */
	public static final short BOOKMAR_LIST = 170;
	
	/**
	 * 
	 */
	// nao seria DATA?? checar prox versao norma
	public static final short DADOS = 192;
	
	/**
	 * 
	 */
	public static final short DATA_EXCLUSIVE_FOR_ACCUMULATION = 169;
	
	/**
	 * 
	 */
	public static final short DATA_FOR_ACCUMULATION_IN_ADVANCE = 168;
	
	/**
	 * 
	 */
	public static final short ENGINEERING_DOWNLOAD = 164;
		
	/**
	 * 
	 */
	public static final short PROMOTION_DATA = 167;
	
	/**
	 * 
	 */
	public static final short PROMOTION_SOUND = 166;
		
	/**
	 * 
	 */
	public static final short PROMOTION_VIDEO = 165;
	
	/**
	 * 
	 */
	public static final short SPECIAL_AUDIO = 162;
	
	/**
	 * 
	 */
	public static final short SPECIAL_DATA = 1630;
	
	/**
	 * 
	 */
	public static final short SPECIAL_VIDEO = 161;
		
}
