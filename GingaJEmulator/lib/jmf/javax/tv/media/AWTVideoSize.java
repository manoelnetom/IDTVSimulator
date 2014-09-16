package javax.tv.media;

import java.awt.Rectangle;

public class AWTVideoSize extends java.lang.Object {
		  Rectangle src, dst;

		  public AWTVideoSize(java.awt.Rectangle source, java.awt.Rectangle dest){
					 src=source;
					 dst=dest;
		  }

		  //Compares this AWTVideoSize with the given object for equality.
		  public boolean equals(java.lang.Object other){
					 return this.equals(other);
		  }


		  //Return a copy of the rectangle representing where the video is to be displayed, in the coordinate system of the screen.
		  public java.awt.Rectangle getDestination(){
					 return dst;
		  }

		  //Return a copy of the rectangle representing the portion of the source video to display, in the coordinate system of the screen.
		  public java.awt.Rectangle getSource(){
					 return src;
		  }

		  //Give the scaling factor applied to the video in the horizontal dimension, i.e., getDestination().width / getSource().width.
		  public float getXScale(){
					 return (float)dst.width / (float)src.width;
		  }

		  //Give the scaling factor applied to the video in the vertical dimension, i.e., getDestination().height / getSource().height.
		  public float getYScale(){
					 return (float)dst.height / (float)src.height;
		  }

		  //Generates a hash code value for this AWTVideoSize.
		  public int hashCode(){
					 return this.hashCode();
		  }

		  //Returns a string representation of this AWTVideoSize and its values.
		  public java.lang.String toString(){
					 return this.toString();
		  }
}

