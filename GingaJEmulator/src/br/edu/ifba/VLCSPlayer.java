/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba;

import java.awt.Component;
import java.io.File;
import javax.media.ClockStoppedException;
import javax.media.Control;
import javax.media.Controller;
import javax.media.ControllerListener;
import javax.media.GainControl;
import javax.media.IncompatibleSourceException;
import javax.media.IncompatibleTimeBaseException;
import javax.media.Player;
import javax.media.Time;
import javax.media.TimeBase;
import javax.media.protocol.DataSource;

/**
 *
 * @author Felipe
 */
public class VLCSPlayer implements Player {

    private int port;
    private String fileURL;
    private VLCVisualComponent visualComponent;

    public VLCSPlayer(String fileURL){
        this.fileURL = fileURL;
        this.fileURL = this.fileURL.replace("file:" + File.separator, "");

        this.visualComponent = new VLCVisualComponent();
    }
    
    public VLCSPlayer(int port) {
        this.port = port;
        this.visualComponent = new VLCVisualComponent();
    }

    private String getMRL() {
        if (this.fileURL == null)
            return "udp://@:" + port;
        else
            return this.fileURL;
    }

    @Override
    public Component getVisualComponent() {
        return visualComponent.getEmbeddedMediaPlayerComponent();
    }

    public Component getControlPanelComponent() {
        return null;
    }

    public GainControl getGainControl() {
        return null;
    }

    // Methods inherited from interface javax.media.MediaHandler -->
    public void setSource(DataSource source) throws java.io.IOException, IncompatibleSourceException {
    }
    // Methods inherited from interface javax.media.MediaHandler //

    // Methods inherited from interface javax.media.Duration -->
    public Time getDuration() {
        return null;
    }
    // Methods inherited from interface javax.media.Duration //

    // Methods inherited from interface javax.media.Clock -->
    public void setTimeBase(TimeBase master) throws IncompatibleTimeBaseException {
    }

    public void syncStart(Time at) {
    }

    public void setStopTime(Time stopTime) {
    }

    public Time getStopTime() {
        return null;
    }

    public void setMediaTime(Time now) {
    }

    public Time getMediaTime() {
        return null;
    }

    public long getMediaNanoseconds() {
        return -1l;
    }

    public Time getSyncTime() {
        return null;
    }

    public TimeBase getTimeBase() {
        return null;
    }

    public Time mapToTimeBase(Time t) throws ClockStoppedException {
        return null;
    }

    public float getRate() {
        return -1f;
    }

    public float setRate(float factor) {
        return -1f;
    }
    // Methods inherited from interface javax.media.Clock //

    // Methods inherited from interface javax.media.Controller -->
    public int getState() {
        return -1;
    }

    public int getTargetState() {
        return -1;
    }

    public void realize() {
    }

    public void prefetch() {
    }

    public void deallocate() {
    }

    public void close() {
    }

    public Time getStartLatency() {
        return null;
    }

    public Control[] getControls() {
        return null;
    }

    public Control getControl(java.lang.String forName) {
        return null;
    }

    public void addControllerListener(ControllerListener listener) {
        //this.controllerListener = listener;
    }

    public void removeControllerListener(ControllerListener listener) {
        //this.controllerListener = null;
    }

    @Override
    public void addController(Controller newController) throws IncompatibleTimeBaseException {
    }

    @Override
    public void removeController(Controller oldController) {
    }

    @Override
    public void stop() {
        this.visualComponent.setVisible(false);
        this.visualComponent.getEmbeddedMediaPlayerComponent().getMediaPlayer().stop();
    }

    @Override
    public void start() {
        this.visualComponent.setVisible(true);
        this.visualComponent.getEmbeddedMediaPlayerComponent().getMediaPlayer().playMedia(this.getMRL());
    }
}
