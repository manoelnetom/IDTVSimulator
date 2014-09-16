package com.sun.dtv.lwuit.plaf;

import java.awt.Color;

import com.sun.dtv.ui.Screen;
import com.sun.dtv.ui.Plane;
import com.sun.dtv.ui.PlaneSetup;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.Font;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.util.Hashtable;

public class UIManager extends Object
{
	private static UIManager instance = new UIManager();
	static boolean accessible = true;
	private LookAndFeel current = new DefaultLookAndFeel();
	private Hashtable styles = new Hashtable();
	private Hashtable themeProps;
	private Style defaultStyle = new Style();
	private Hashtable imageCache = new Hashtable();
	private Hashtable resourceBundle;

	UIManager()
	{
		resetThemeProps(null);
	}

	/**
	 * Singleton instance method.
	 *
	 * 
	 * @return Instance of the ui manager
	 */
	public static UIManager getInstance()
	{
		return instance;
	}

	/**
	 * Returns the currently installed look and feel.
	 * 
	 * 
	 * @return the currently installed look and feel
	 * @see setLookAndFeel(com.sun.dtv.lwuit.plaf.LookAndFeel)
	 */
	public LookAndFeel getLookAndFeel()
	{
		return this.current;
	}

	/**
	 * Sets the currently installed look and feel.
	 *
	 * 
	 * @param plaf - the look and feel
	 * @see getLookAndFeel()
	 */
	public void setLookAndFeel(LookAndFeel plaf)
	{
		this.current = plaf;
	}

	/**
	 * Allows a developer to programmatically install a style into the UI manager.
	 *
	 * 
	 * @param id - the component id matching the given style
	 * @param style - the style object to install
	 * @see getComponentStyle(java.lang.String)
	 */
	public void setComponentStyle(String id, Style style)
	{
		if(id == null || id.length() == 0){
			id = "";
		} else {
			id = id + ".";
		}

		styles.put(id, style);
	}

	/**
	 * Returns the style of the component with the given id or a
	 * <b>new instance</b> of the default style.
	 * This method will always return a new style instance to prevent modification of the global
	 * style object.
	 *
	 * 
	 * @param id - the component id
	 * @return the appropriate style (this method never returns null)
	 * @see setComponentStyle(java.lang.String, com.sun.dtv.lwuit.plaf.Style)
	 */
	public Style getComponentStyle(String id)
	{
		Style style = null;

		if(id == null || id.length() ==0){
			id = "";
		}else{
			id = id + ".";
		}

		style = (Style)styles.get(id);

		if(style == null)
		{
			style = createStyle(id);
			styles.put(id, style);
		}
		return new Style(style);
	}

	private Style createStyle(String id) {
		Style style = new Style(defaultStyle);

		if(themeProps != null)
		{
			String bgColor = (String)themeProps.get(id + Style.BG_COLOR);
			String fgColor = (String)themeProps.get(id + Style.FG_COLOR);
			Object bgImage = (Object)themeProps.get(id + Style.BG_IMAGE);
			String bgSelColor = (String)themeProps.get(id + "sel#bgColor"/*Style.BG_SELECTION_COLOR*/);
			String fgSelColor = (String)themeProps.get(id + "sel#fgColor"/*Style.FG_SELECTION_COLOR*/);
			String transperency = (String)themeProps.get(id + Style.TRANSPARENCY);
			String margin = (String)themeProps.get(id + Style.MARGIN);
			String padding = (String)themeProps.get(id + Style.PADDING);
			Object font = (Object)themeProps.get(id + Style.FONT);
			Object border = (Object)themeProps.get(id + Style.BORDER);
			Object scale = (Object)themeProps.get(id + Style.SCALED_IMAGE);

			if(bgColor != null){
				style.setBgColor(new Color(Integer.valueOf(bgColor, 16).intValue()));
			}

			if(fgColor != null){
				style.setFgColor(new Color(Integer.valueOf(fgColor, 16).intValue()));
			}

			if(bgSelColor != null){
				style.setBgSelectionColor(new Color(Integer.valueOf(bgSelColor, 16).intValue()));
			}

			if(fgSelColor != null){
				style.setFgSelectionColor(new Color(Integer.valueOf(fgSelColor, 16).intValue()));
			}

			if(transperency != null){
				style.setBgTransparency(Integer.valueOf(transperency).intValue());
			}

			if(margin != null){
				int [] marginArr = toIntArray(margin.trim());
				style.setMargin(marginArr[0], marginArr[1], marginArr[2], marginArr[3]);
			} 

			if(padding != null){
				int [] paddingArr = toIntArray(padding.trim());
				style.setPadding(paddingArr[0], paddingArr[1], paddingArr[2], paddingArr[3]);
			}

			if(scale != null){
				style.setScaleImage(!scale.equals("false"));
			}

			if(bgImage != null){
				Image im = null;

				if(bgImage instanceof String){
					try {
						String bgImageStr = (String)bgImage;
						if(imageCache.contains(bgImageStr)) {
							im = (Image)imageCache.get(bgImageStr);
						} else { 
							if(bgImageStr.startsWith("/")) {
								im = Image.createImage(bgImageStr);
							} else {
								im = parseImage((String)bgImage);
							}
							imageCache.put(bgImageStr, im);
						}
						themeProps.put(id + Style.BG_IMAGE, im);
					} catch (IOException ex) {
						System.out.println("failed to parse image for id = "+id + Style.BG_IMAGE);
					}
				}else{
					im = (Image)bgImage;
				}

				if(id.indexOf("Form") > -1){
					Screen screen = Screen.getCurrentScreen();
					Plane planes[] = screen.getAllPlanes();
					
					if (planes != null) {
						PlaneSetup setup = planes[0].getCurrentSetup();
						Dimension d = setup.getScreenResolution();

						if((im.getWidth() != d.getWidth() || im.getHeight() != d.getHeight()) && style.isScaleImage() && accessible) {
							im = im.scaled(d.getWidth(), d.getHeight());
						}
					}
				}

				style.setBgImage(im);
			}

			if(font != null){
				if(font instanceof String){
					style.setFont(parseFont((String)font));
				}else{
					style.setFont((Font)font);
				}
			}

			style.setBorder((Border)border);
			style.resetModifiedFlag();
		} 

		return style;
	}


	private static Font parseFont(String fontStr) {
		if(fontStr.startsWith("System")){
			int face = 0;
			int style = 0;
			int size = 0;
			String faceStr, styleStr, sizeStr;
			String sysFont = fontStr.substring(fontStr.indexOf("{") + 1, fontStr.indexOf("}"));
			faceStr = sysFont.substring(0, sysFont.indexOf(";"));
			sysFont = sysFont.substring(sysFont.indexOf(";") + 1, sysFont.length());
			styleStr = sysFont.substring(0, sysFont.indexOf(";"));
			sizeStr = sysFont.substring(sysFont.indexOf(";") + 1, sysFont.length());

			if(faceStr.indexOf("FACE_SYSTEM") > -1){
				face = Font.FACE_SYSTEM;
			}else if(faceStr.indexOf("FACE_MONOSPACE") > -1){
				face = Font.FACE_MONOSPACE;
			}else if(faceStr.indexOf("FACE_PROPORTIONAL") > -1){
				face = Font.FACE_PROPORTIONAL;
			}

			if(styleStr.indexOf("STYLE_PLAIN") > -1){
				style = Font.STYLE_PLAIN;
			}else{
				if(styleStr.indexOf("STYLE_BOLD") > -1){
					style = Font.STYLE_BOLD;
				}

				if(styleStr.indexOf("STYLE_ITALIC") > -1){
					style = style | Font.STYLE_ITALIC;
				}

				if(styleStr.indexOf("STYLE_UNDERLINED") > -1){
					style = style | Font.STYLE_UNDERLINED;
				}
			}

			if(sizeStr.indexOf("SIZE_SMALL") > -1){
				size = Font.SIZE_SMALL;
			}else if(sizeStr.indexOf("SIZE_MEDIUM") > -1){
				size = Font.SIZE_MEDIUM;
			}else if(sizeStr.indexOf("SIZE_LARGE") > -1){
				size = Font.SIZE_LARGE;
			}

			return Font.createSystemFont(face, style, size);            
		} else {
			if(fontStr.toLowerCase().startsWith("bitmap")) {
				try {
					String bitmapFont = fontStr.substring(fontStr.indexOf("{") + 1, fontStr.indexOf("}"));
					String nameStr = bitmapFont.substring(0, bitmapFont.length());

					if(nameStr.toLowerCase().startsWith("highcontrast")) {
						nameStr = nameStr.substring(nameStr.indexOf(";") + 1, nameStr.length());
						Font f = Font.getBitmapFont(nameStr);
						f.addContrast((byte)30);
						return f;
					}
					return Font.getBitmapFont(nameStr);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * Loads a theme from the given input stream. The theme can be specified
	 * in any stream using the following notation:
	 * <pre>
	 * ButtonFont=System{FACE_SYSTEM;STYLE_PLAIN;SIZE_SMALL}
	 * LabelFgColor=333333
	 * </pre>
	 *
	 * 
	 * @param input - the input stream
	 * @throws IOException - if loading fails
	 */
	public void loadTheme( InputStream input) throws IOException
	{
		Hashtable theme = new Hashtable();
		byte[] buffer = new byte[1024];
		int size = input.read(buffer);
		StringBuffer stringBuffer = new StringBuffer();
		
		while(size > -1) {
			stringBuffer.append(new String(buffer));
			size = input.read(buffer);
		}
		
		String file = stringBuffer.toString();
		stringBuffer = null;
		int previousOffset = 0;
		int fileOffset = nextNewline(file, 0);
		
		while(previousOffset < file.length() && previousOffset > -1) {
			String recordStr = file.substring(previousOffset, fileOffset);
			char currentChar = Character.toUpperCase(recordStr.charAt(0));
			
			while((currentChar < 'A' || currentChar > 'Z') && recordStr.length() > 1) {
				recordStr = recordStr.substring(1, recordStr.length());
				currentChar = Character.toUpperCase(recordStr.charAt(0));
			}

			previousOffset = fileOffset;
			fileOffset = nextNewline(file, previousOffset + 1);
			
			if(recordStr.indexOf("=") > 0 && !recordStr.startsWith("#")){
				int spacesLoc = -1;
			
				while((spacesLoc = recordStr.indexOf(" ")) > -1){
					recordStr = recordStr.substring(0, spacesLoc) + recordStr.substring(spacesLoc + 1, recordStr.length());
				}

				String key = recordStr.substring(0, recordStr.indexOf("="));
				String value = recordStr.substring(recordStr.indexOf("=") + 1, recordStr.length());
				
				while(value.charAt(value.length() - 1) == '\n' || value.charAt(value.length() - 1) == '\r') {
					value = value.substring(0, value.length() - 1);
				}
				
				theme.put(key, value);
			}
		}

		setThemeProps(theme);
	}


	private int nextNewline(String str, int offset) {
		int newline = str.indexOf('\n', offset);

		if(newline < 0) {
			return str.length();
		}

		return newline;
	}

	/**
	 * Returns the name of the current theme for theme switching UI's.
	 *
	 * 
	 * 
	 * @return the name of the current theme for theme switching UI's
	 */
	public String getThemeName()
	{
		if(themeProps != null){
			return (String)themeProps.get("name");
		}

		return null;
	}

	/**
	 * Allows manual theme loading from a hashtable of key/value pairs.
	 *
	 * 
	 * @param themeProps - the hashtable containing the theme's props
	 */
	public void setThemeProps( Hashtable themeProps)
	{
		this.themeProps = themeProps;
		styles.clear();
	}

	/**
	 * Initializes the theme properties with the current "defaults"
	 */
	private void resetThemeProps(Hashtable installedTheme) 
	{
		themeProps = new Hashtable();
		String disabledColor = Integer.toHexString(getLookAndFeel().getDisableColor().getRGB());
        Integer centerAlign = new Integer(Component.CENTER);
        Integer rightAlign = new Integer(Component.RIGHT);

        // global settings
        themeProps.put("sel#transparency", "255");
        themeProps.put("dis#fgColor", disabledColor);
        // component specific settings
        if(installedTheme == null || !installedTheme.containsKey("Button.derive")) {
            themeProps.put("Button.border", Border.getDefaultBorder());
            themeProps.put("Button.padding", "4,4,4,4");
        }
        if(installedTheme == null || !installedTheme.containsKey("Button.press#derive")) {
            themeProps.put("Button.press#border", Border.getDefaultBorder().createPressedVersion());
            themeProps.put("Button.press#derive", "Button");
        }
        themeProps.put("Button.sel#derive", "Button");
        themeProps.put("Button.dis#derive", "Button");

        if(installedTheme == null || !installedTheme.containsKey("CalendarTitle.derive")) {
            themeProps.put("CalendarTitle.align", centerAlign);
        }

        if(installedTheme == null || !installedTheme.containsKey("CalendarSelectedDay.derive")) {
            themeProps.put("CalendarSelectedDay.border", Border.getDefaultBorder());
        }
        themeProps.put("TextArea.border", Border.getDefaultBorder());
		themeProps.put("List.border", Border.getDefaultBorder());
		themeProps.put("TextField.border", Border.getDefaultBorder());
		themeProps.put("ComboBox.border", Border.getDefaultBorder());
		themeProps.put("ComboBoxPopup.border", Border.getDefaultBorder());
		themeProps.put("Title.margin", "0,0,0,0");
		themeProps.put("CommandList.margin", "0,0,0,0");
		themeProps.put("CommandList.transparency", "0");
		themeProps.put("Command.margin", "0,0,0,0");
		themeProps.put("Container.transparency", "0");
		themeProps.put("ComboBoxPopup.transparency", "0");
		themeProps.put("List.transparency", "0");
		themeProps.put("SoftButton.transparency", "255");
		themeProps.put("List.margin", "0,0,0,0");
		themeProps.put("SoftButton.margin", "0,0,0,0");
		themeProps.put("SoftButton.padding", "2,2,2,2");
		themeProps.put("Container.margin", "0,0,0,0");
		themeProps.put("Container.padding", "0,0,0,0");
		themeProps.put("Title.transparency", "255");
		themeProps.put("TabbedPane.margin", "0,0,0,0");
		themeProps.put("TabbedPane.padding", "0,0,0,0");
		themeProps.put("ScrollThumb.padding", "0,0,0,0");
		themeProps.put("ScrollThumb.margin", "0,0,0,0");
		themeProps.put("Form.padding", "0,0,0,0");
		themeProps.put("Form.margin", "0,0,0,0");
		themeProps.put(Style.TRANSPARENCY, "255");
	}

	/**
	 * The resource bundle allows us to implicitly localize the UI on the fly, once its
	 * installed all internal application strings query the resource bundle and extract
	 * their values from this table if applicable.
	 *
	 * 
	 * 
	 * @return the resource bundle
	 * @see setResourceBundle(java.util.Hashtable)
	 */
	public Hashtable getResourceBundle()
	{
		return this.resourceBundle;
	}

	/**
	 * The resource bundle allows us to implicitly localize the UI on the fly, once its
	 * installed all internal application strings query the resource bundle and extract
	 * their values from this table if applicable.
	 *
	 * 
	 * @param resourceBundle - the resourceBundle
	 * @see getResourceBundle()
	 */
	public void setResourceBundle( Hashtable resourceBundle)
	{
		this.resourceBundle = resourceBundle;
	}

	/**
	 * Localizes the given string from the resource bundle if such a String exists in the
	 * resource bundle. If no key exists in the bundle then or a bundle is not installed
	 * the default value is returned.
	 *
	 * 
	 * @param key - The key used to lookup in the resource bundle
	 * @param defaultValue - the value returned if no such key exists
	 * @return either default value or the appropriate value
	 */
	public String localize(String key, String defaultValue)
	{
		if(resourceBundle != null) {
			Object o = resourceBundle.get(key);

			if(o != null) {
				return (String)o;
			}
		}

		return defaultValue;
	}

	/**
	 * This method is used to parse the margin and the padding
	 * @param str
	 * @return
	 */
	private int [] toIntArray(String str){
		int [] retVal = new int[4];
		str  = str + ",";
		for(int i=0; i< retVal.length; i++){
			retVal[i] = Integer.parseInt(str.substring(0, str.indexOf(",")));
			str = str.substring(str.indexOf(",") + 1, str.length());
		}

		return retVal;
	}

	private static Image parseImage(String value) throws IOException {
		int index = 0;
		byte [] imageData = new byte[value.length()/2];
		
		while(index < value.length()){
			String byteStr = value.substring(index, index + 2);
			imageData[index/2] = Integer.valueOf(byteStr, 16).byteValue();
			index += 2;
		}

		ByteArrayInputStream in = new ByteArrayInputStream(imageData);
		Image image = Image.createImage(in);
		in.close();
		
		return image;
	}

}
