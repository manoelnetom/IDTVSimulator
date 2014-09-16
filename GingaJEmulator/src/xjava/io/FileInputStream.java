/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package xjava.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A <code>FileInputStream</code> obtains input bytes
 * from a file in a file system. What files
 * are  available depends on the host environment.
 *
 * <p><code>FileInputStream</code> is meant for reading streams of raw bytes
 * such as image data. For reading streams of characters, consider using
 * <code>FileReader</code>.
 *
 * @author  Arthur van Hoff
 * @version %I%, %G%
 * @see     java.io.File
 * @see     java.io.FileDescriptor
 * @see	    java.io.FileOutputStream
 * @since   JDK1.0
 */
public class FileInputStream extends java.io.FileInputStream {

    public FileInputStream(String fileName) throws FileNotFoundException {
        this(new File(fileName));
    }

    public FileInputStream(File file) throws FileNotFoundException {
        super(new File(getBaseDirectory(file)));
    }

    public static String getBaseDirectory(File file) {
        String base = System.getProperty("application.basedirectory");
        String name = (file != null ? file.getPath() : null);
        if (base != null) {
            String path = base + (name.startsWith("/") ? name : "/" + name);

            File f = new File(path);

            if (f.exists() == true) {
                name = path;
            }
        }

        System.out.println("FILE INPUT STREAM:: " + name);
        return name;
    }
}
