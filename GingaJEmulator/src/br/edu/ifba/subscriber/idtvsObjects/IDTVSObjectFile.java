/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.idtvsObjects;

import java.io.File;

/**
 *
 * @author Felipe
 */
public class IDTVSObjectFile extends IDTVSObject{
   
    private File sourceFile;
    
    public IDTVSObjectFile(long id, short type, File sourceFile){
        super(id, type);
        this.sourceFile = sourceFile;
    }
        
    public File getSourceFile(){
        return this.sourceFile;
    }
}
