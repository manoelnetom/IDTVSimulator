package com.sun.dtv.lwuit;

import java.util.Hashtable;

class CustomFont extends Font {
    private Hashtable colorCache = new Hashtable();

    private String charsets;
    private int color;
    
    // package protected for the resource editor
    private Image bitmap;
    private int[] imageArray;
    
    /**
     * The offset in which to cut the character from the bitmap
     */
    int[] cutOffsets;

    /**
     * The width of the character when drawing... this should not be confused with
     * the number of cutOffset[o + 1] - cutOffset[o]. They are completely different
     * since a character can be "wider" and "seep" into the next region. This is
     * especially true with italic characters all of which "lean" outside of their 
     * bounds.
     */
    int[] charWidth;

    private int imageWidth;
    private int imageHeight;
    
    private int[] getImageArray() {
    	return this.imageArray;
    }
    
    /**
     * Creates a bitmap font with the given arguments
     * 
     * @param bitmap a transparency map in red and black that indicates the characters
     * @param cutOffsets character offsets matching the bitmap pixels and characters in the font 
     * @param charWidth The width of the character when drawing... this should not be confused with
     *      the number of cutOffset[o + 1] - cutOffset[o]. They are completely different
     *      since a character can be "wider" and "seep" into the next region. This is
     *      especially true with italic characters all of which "lean" outside of their 
     *      bounds.
     * @param charsets the set of characters in the font
     * @return a font object to draw bitmap fonts
     */
    public CustomFont(Image bitmap, int[] cutOffsets, int[] charWidth, String charsets) {
        this.cutOffsets = cutOffsets;
        this.charWidth = charWidth;
        this.charsets = charsets;
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        
        this.imageArray = new int[imageWidth * imageHeight];
        
        // default to black colored font
        bitmap.getRGB(imageArray, 0, 0, 0, imageWidth, imageHeight);
        for(int iter = 0 ; iter < imageArray.length ; iter++) {
            // extract the red component from the font image
            // shift the alpha 8 bits to the left
            // apply the alpha to the image
            imageArray[iter] = ((imageArray[iter] & 0xff0000) << 8);
        }
        bitmap = Image.createImage(imageArray, imageWidth, imageHeight);
        
        this.bitmap = bitmap;
    }
    
    /**
     * @inheritDoc
     */
    public int charWidth(char ch) {
        int i = charsets.indexOf(ch);
        if(i < 0) {
            return 0;
        }
        return charWidth[i];
    }

    /**
     * @inheritDoc
     */
    public int getHeight() {
        return imageHeight;
    }

    private void initColor(Graphics g) {
    	int newColor = g.getColor().getRGB();
    	Image cachedImage = (Image) colorCache.get(new Integer(newColor));
    	if(cachedImage == null)
    	{
    		
    		int color = newColor & 0xffffff;
            for(int iter = 0 ; iter < imageArray.length ; iter++) {
                // extract the red component from the font image
                // shift the alpha 8 bits to the left
                // apply the alpha to the image
                imageArray[iter] = color | (imageArray[iter] & 0xff000000);
            }
            bitmap = Image.createImage(imageArray, imageWidth, imageHeight);
            
            colorCache.put(new Integer(newColor), bitmap);
    	}
    	else
    	{
    		this.bitmap = cachedImage;
    	}
    }
    
    /**
     * @inheritDoc
     */
    public void drawChar(Graphics g, char character, int x, int y) {
        int clipX = g.getClipX();
        int clipY = g.getClipY();
        int clipWidth = g.getClipWidth();
        int clipHeight = g.getClipHeight();
        
        int i = charsets.indexOf(character);
        if(i > -1) {
            initColor(g);
            
            // draw region is flaky on some devices, use setClip instead
            g.setClip(x, y, charWidth[i], imageHeight);
            g.drawImage(bitmap/*cache*/, x - cutOffsets[i], y);
            //g.drawRegion(cache, cutOffsets[i], 0, charWidth[i], imageHeight, x, y);
        }

        // restore the clip
        g.setClip(clipX, clipY, clipWidth, clipHeight);
    }

    /**
     * Override this frequently used method for a slight performance boost...
     * 
     * @param g the component graphics
     * @param data the chars to draw
     * @param offset the offset to draw the chars 
     * @param length the length of chars 
     * @param x the x coordinate to draw the chars
     * @param y the y coordinate to draw the chars
     */
    void drawChars(Graphics g, char[] data, int offset, int length, int x, int y) {
    	initColor(g);
        int clipX = g.getClipX();
        int clipY = g.getClipY();
        int clipWidth = g.getClipWidth();
        int clipHeight = g.getClipHeight();

        if(clipY <= y + getHeight() && clipY + clipHeight >= y) {
            char c;
            for ( int i = 0; i < length; i++ ) {
                c = data[offset+i];
                int position = charsets.indexOf(c);
                if(position < 0) {
                    continue;
                }
                // draw region is flaky on some devices, use setClip instead
                g.setClip(x, y, charWidth[position], imageHeight);
                if(g.getClipWidth() > 0 && g.getClipHeight() > 0) {
                    g.drawImage(bitmap, x - cutOffsets[position], y);
                }
                x += charWidth[position];
                g.setClip(clipX, clipY, clipWidth, clipHeight);
            }
        }
    }
    
    /**
     * @inheritDoc
     */
    public void addContrast(byte value) {
        int[] imageArray = getImageArray();
        for(int iter = 0 ; iter < imageArray.length ; iter++) {
            int alpha = (imageArray[iter] >> 24) & 0xff;
            if(alpha != 0) {
                alpha = Math.min(alpha + value, 255);
                imageArray[iter] = ((alpha << 24) & 0xff000000) | color;
            }
        }
    }

    /**
     * @inheritDoc
     */
    public String getCharset() {
        return charsets;
    }

    /**
     * @inheritDoc
     */
    public int charsWidth(char[] ch, int offset, int length){
        int retVal = 0;
        for(int i=0; i<length; i++){
            retVal += charWidth(ch[i + offset]);
        }
        return retVal;
    }


    /**
     * @inheritDoc
     */
    public int substringWidth(String str, int offset, int len){
        return charsWidth(str.toCharArray(), offset, len);
    }

    /**
     * @inheritDoc
     */
    public int stringWidth(String str){
        if( str==null || str.length()==0)
            return 0;
        
        int t = substringWidth(str, 0, str.length());
        return t;
    }

    /**
     * @inheritDoc
     */
    public int getFace(){
        return 0;
    }

    /**
     * @inheritDoc
     */
    public int getSize(){
        return this.getHeight();
    }

    /**
     * @inheritDoc
     */
    public int getStyle() {
        return 0;
    }
    
    /**
    * @inheritDoc
    */
   public boolean equals(Object o) {
       if(o == this) {
           return true;
       }
       if(o != null && o.getClass() == getClass()) {
           CustomFont f = (CustomFont)o;
           if(charsets.equals(f.charsets)) {
               for(int iter = 0 ; iter < cutOffsets.length ; iter++) {
                   if(cutOffsets[iter] != f.cutOffsets[iter]) {
                       return false;
                   }
               }
               return true;
           }
       }
       return false;
   }
}
