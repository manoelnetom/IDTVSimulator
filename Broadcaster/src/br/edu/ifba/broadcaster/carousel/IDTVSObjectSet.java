
package br.edu.ifba.broadcaster.carousel;

import java.util.ArrayList;

public class IDTVSObjectSet extends ArrayList<IDTVSObject> {
    
    public IDTVSObject mainObject;
    
    public IDTVSObjectSet(){
        
    }
    
    public IDTVSObjectSet(IDTVSObject mainObject) {
        
        this.mainObject = mainObject;
    }

    public IDTVSObject getMainObject() {
        return mainObject;
    }

    public void setMainObject(IDTVSObject mainObject) {
        this.mainObject = mainObject;
    }
    
    
}
