/*

 This file is part of XleTView 
 Copyright (C) 2003 Martin Sveden
 
 This is free software, and you are 
 welcome to redistribute it under 
 certain conditions;

 See LICENSE document for details.

*/

package xjava.io;

import java.io.File;
import java.net.URI;


/**
 * Represents a file or directory in the filesystem.
 * @author Martin Sveden 
 */
public class GingaJFile extends java.io.File {

    /**
     * Overrides constructor in java.io.GingaJFile
     */
    public GingaJFile(java.io.File parent, String child) {
    	super(parent, child);
    }

    /**
     * Overrides constructor in java.io.GingaJFile
     */
    public GingaJFile(String parent, String child) {
    	super(parent, child);
    }
    
    /**
     * Overrides constructor in java.io.GingaJFile
     */
    public GingaJFile(String path){        
        super(path);
    }
    
    /**
     * Overrides constructor in java.io.GingaJFile
     */
    public GingaJFile(URI uri) {
    	super(uri);
    }
    

}
