/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba;

import com.sun.jna.NativeLibrary;
import net.beiker.xletview.ui.XContainer;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author Felipe
 */
public class VLCVisualComponent extends XContainer {

    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public VLCVisualComponent() {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        this.mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        this.mediaPlayerComponent.setVisible(true);
        this.add(mediaPlayerComponent);        
    }

    public EmbeddedMediaPlayerComponent getEmbeddedMediaPlayerComponent() {
        return this.mediaPlayerComponent;
    }
}
