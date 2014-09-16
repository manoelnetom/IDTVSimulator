/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.broadcaster;

import java.io.File;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 *
 * @author Thiago
 */
public class PlayList extends Thread {

    private File file = null;
    private String ip;
    private int port;
    
    
    public PlayList( String ip, int port ) {
        
        this.ip = ip;
        this.port = port;
    }
    
    public void addMedia( File file ) {
        
        this.file = file;
    }    
    
    public void play() {
        
        this.start();
    }
    
    @Override
    public void run() {
        
        this.Begin();
    }
    
    private void Begin() {
        
        String options = this.formatUDPStream();
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(this.file.getPath());
        HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaPlayer.playMedia(this.file.getPath(), options);
    }
    
    private String formatUDPStream() {
                  
        StringBuilder sb = new StringBuilder();
        sb.append(":sout=#transcode{vcodec=mp2v,vb=800,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:duplicate{dst=std{access=udp,mux=ts,");
        //sb.append(":sout=#transcode{vcodec=mp2v,vb=800,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=udp{ttl=1},mux=ts,");
        sb.append("dst=");
        sb.append(this.ip);
        sb.append(':');
        sb.append(this.port);
        sb.append("}}");
        //sb.append("} :sout-keep");
        return sb.toString();
    }
}
