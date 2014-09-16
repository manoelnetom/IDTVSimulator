/*

This file is part of XleTView 
Copyright (C) 2003 Martin Sveden

This is free software, and you are 
welcome to redistribute it under 
certain conditions;

See LICENSE document for details.

 */
package xjava.io;

import java.io.FileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;

import net.beiker.cake.Log;
import net.beiker.cake.Logger;

/**
 * 
 * @author Martin Sveden 
 */
public class FileReader extends java.io.FileReader {

    /** Debugging facility */
    private static final Logger logger = Log.getLogger(FileReader.class);

    public FileReader(GingaJFile f) throws FileNotFoundException {
        super(new File(xjava.io.FileInputStream.getBaseDirectory(f)));
    }

    public FileReader(String fileName) throws FileNotFoundException {
        super(xjava.io.FileInputStream.getBaseDirectory(new File(fileName)));
    }

    public FileReader(FileDescriptor fd) {
        super(fd);
    }
}
