package javax.tv.media;

import java.awt.*;

public interface AWTVideoSizeControl extends javax.media.Control {
		  
		  //Reports how closely the underlying platform can approximate a desired video size.
		  public AWTVideoSize checkSize(AWTVideoSize sz);
		  
		  //Reports the default AWTVideoSize for this control.
		  public AWTVideoSize getDefaultSize();

		  //Reports the AWTVideoSize at which the Player is currently operating.
		  public AWTVideoSize getSize();

		  //Reports the size of the source video, in the screen's coordinate system.
		  public java.awt.Dimension getSourceVideoSize();
		  
		  //Sets the video size
		  public boolean setSize(AWTVideoSize sz);

		  //Control interface method
		  public java.awt.Component getControlComponent();
}

