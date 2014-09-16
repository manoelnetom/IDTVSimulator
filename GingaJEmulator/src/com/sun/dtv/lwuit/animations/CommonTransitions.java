package com.sun.dtv.lwuit.animations;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Dialog;
import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.Painter;

public final class CommonTransitions extends Transition {
	
	private static final int TYPE_EMPTY = 0;
	private static final int TYPE_SLIDE = 1;
	private static final int TYPE_FADE = 2;

	private Motion motion;

	/**
	 * Slide the transition horizontally
	 * 
	 * @see #createSlide
	 */
	public static final int SLIDE_HORIZONTAL = 0;

	/**
	 * Slide the transition vertically
	 * 
	 * @see #createSlide
	 */
	public static final int SLIDE_VERTICAL = 1;

	private int slideType;
	private int speed;
	private int position;
	private int transitionType;
	private Image buffer;
	private Image secondaryBuffer;

	/**
	 * The transition is a special case where we "keep" an allocated buffer
	 *
	 */
	private boolean forward;
	private boolean drawDialogMenu;

	private boolean firstFinished;

	private CommonTransitions(int type) {
		transitionType = type;
	}

	/**
	 * Creates an empty transition that does nothing. This has the same effect
	 * as setting a transition to null.
	 * 
	 * @return empty transition
	 */
	public static CommonTransitions createEmpty() {
		CommonTransitions t = new CommonTransitions(TYPE_EMPTY);
		return t;
	}

	/**
	 * Creates a slide transition with the given duration and direction
	 * 
	 * @param type
	 *            type can be either vertically or horizontally, which means the
	 *            movement direction of the transition
	 * @param forward
	 *            forward is a boolean value, represent the directions of
	 *            switching forms, for example for a horizontally type, true
	 *            means horizontally movement to right.
	 * @param duration
	 *            represent the time the transition should take in millisecond
	 * @return a transition object
	 */
	public static CommonTransitions createSlide(int type, boolean forward, int duration) {
		return createSlide(type, forward, duration, false);
	}

	/**
	 * Creates a slide transition with the given duration and direction
	 * 
	 * @param type
	 *            type can be either vertically or horizontally, which means the
	 *            movement direction of the transition
	 * @param forward
	 *            forward is a boolean value, represent the directions of
	 *            switching forms, for example for a horizontally type, true
	 *            means horizontally movement to right.
	 * @param duration
	 *            represent the time the transition should take in millisecond
	 * @param drawDialogMenu
	 *            indicates that the menu (softkey area) of the dialog should be
	 *            kept during a slide transition. This is only relevant for
	 *            dialog in/out transitions.
	 * @return a transition object
	 */
	public static CommonTransitions createSlide(int type, boolean forward, int duration, boolean drawDialogMenu) {
		CommonTransitions t = new CommonTransitions(TYPE_SLIDE);
		t.slideType = type;
		t.forward = forward;
		t.speed = duration;
		t.position = 0;
		t.drawDialogMenu = drawDialogMenu;
		return t;
	}

	/**
	 * Creates a transition for fading a form in while fading out the original
	 * form
	 * 
	 * @param duration
	 *            represent the time the transition should take in millisecond
	 * @return a transition object
	 */
	public static CommonTransitions createFade(int duration) {
		CommonTransitions t = new CommonTransitions(TYPE_FADE);
		t.speed = duration;
		return t;
	}

	/**
	 * @inheritDoc
	 *
	 */
	public void initTransition() {
		System.gc();

		firstFinished = false;
		if(transitionType == TYPE_EMPTY) {
			return;
		}

		Component source = getSource();
		Component destination = getDestination();
		position = 0;
		int w = source.getWidth();
		int h = source.getHeight();
		if (buffer == null) {
			buffer = Image.createImage(w, h);
		} else {
			// this might happen when screen orientation changes or a MIDlet moves
			// to an external screen
			if(buffer.getWidth() != w || buffer.getHeight() != h) {
				buffer = Image.createImage(w, h);

				// slide motion might need resetting since screen size is different
				motion = null;
			}
		}
		if(transitionType == TYPE_FADE) {
			motion = Motion.createSplineMotion(0, 256, speed);
			motion.start();

			Graphics g = buffer.getGraphics();
			g.translate(-source.getAbsoluteX(), -source.getAbsoluteY());

			if(getSource().getParent() != null){
				getSource().getComponentForm().paintComponent(g);
			}
			// CHANGE:: uncommented:1
			getSource().paintBackgrounds(g);
			g.setClip(0, 0, buffer.getWidth()+source.getAbsoluteX(), buffer.getHeight()+source.getAbsoluteY());
			paint(g, getDestination(), 0, 0);
			
			// CHANGE:: if(g.isAlphaSupported()) {
				secondaryBuffer = buffer;
				buffer = Image.createImage(w, h);
			// }

			paint(g, getSource(), 0, 0);
			g.translate(source.getAbsoluteX(), source.getAbsoluteY());

		} else {
			if (transitionType == TYPE_SLIDE) {
				int dest;
				int startOffset = 0;
				if (slideType == SLIDE_HORIZONTAL) {
					dest = w;
					if(destination instanceof Dialog) {
						startOffset = w - ((Dialog)destination).getContentPane().getWidth();
						if(forward) {
							startOffset -= ((Dialog)destination).getContentPane().getStyle().getMargin(Component.LEFT); 
						} else {
							startOffset -= ((Dialog)destination).getContentPane().getStyle().getMargin(Component.RIGHT); 
						}
					} else {
						if(source instanceof Dialog) {
							dest = ((Dialog)source).getContentPane().getWidth();
							if(forward) {
								dest += ((Dialog)source).getContentPane().getStyle().getMargin(Component.LEFT); 
							} else {
								dest += ((Dialog)source).getContentPane().getStyle().getMargin(Component.RIGHT); 
							}
						} 
					}
				} else {
					dest = h;
					if(destination instanceof Dialog) {
						startOffset = h - ((Dialog)destination).getContentPane().getHeight() -
							((Dialog)destination).getTitleComponent().getHeight();
						if(forward) {
							startOffset -= ((Dialog)destination).getContentPane().getStyle().getMargin(Component.BOTTOM); 
						} else {
							startOffset -= ((Dialog)destination).getContentPane().getStyle().getMargin(Component.TOP); 
							startOffset -= ((Dialog)destination).getTitleStyle().getMargin(Component.TOP); 
							if(!drawDialogMenu && ((Dialog)destination).getCommandCount() > 0) {
								Container p = ((Dialog)destination).getSoftButton(0).getParent();
								if(p != null) {
									startOffset -= p.getHeight();
								}
							}
						}
					} else {
						if(source instanceof Dialog) {
							dest = ((Dialog)source).getContentPane().getHeight() +
								((Dialog)source).getTitleComponent().getHeight();
							if(forward) {
								dest += ((Dialog)source).getContentPane().getStyle().getMargin(Component.BOTTOM); 
							} else {
								dest += ((Dialog)source).getContentPane().getStyle().getMargin(Component.TOP); 
								dest += ((Dialog)source).getTitleStyle().getMargin(Component.TOP); 
								if(((Dialog)source).getCommandCount() > 0) {
									Container p = ((Dialog)source).getSoftButton(0).getParent();
									if(p != null) {
										dest += p.getHeight();
									}
								}
							}
						} 
					}
				}

				motion = Motion.createSplineMotion(startOffset, dest, speed);

				// make sure the destination is painted fully at least once 
				// we must use a full buffer otherwise the clipping will take effect
				Graphics g = buffer.getGraphics();

				// If this is a dialog render the tinted frame once since 
				// tinting is expensive
				if(getSource() instanceof Dialog) {
					paint(g, getDestination(), 0, 0);
				} else {
					if(getDestination() instanceof Dialog) {
						paint(g, getSource(), 0, 0);
					} else {
						paint(g, source, -source.getAbsoluteX(), -source.getAbsoluteY());
					}
				}
				motion.start();
			}
		}

		System.gc();
	}

	/**
	 * @inheritDoc
	 *
	 */
	public boolean animate() {
		if(motion == null) {
			return false;
		}
		position = motion.getValue();

		// after the motion finished we need to paint one last time otherwise
		// there will be a "bump" in sliding
		if(firstFinished) {
			return false;
		}
		boolean finished = motion.isFinished();
		if(finished) {
			if(!firstFinished) {
				firstFinished = true;
			}
		}
		return true;
	}

	// CHANGE:: metodo adicionado a classe por causa da implementacao de referencia
	public void paint(Graphics g) {
		try {
			switch (transitionType) {
				case TYPE_SLIDE:
					// if this is an up or down slide
					if (slideType == SLIDE_HORIZONTAL) {
						paintSlideAtPosition(g, position, 0);
					} else {
						paintSlideAtPosition(g, 0, position);
					}
					return;
				case TYPE_FADE:
					paintAlpha(g);
					return;
			}
		} catch(Throwable t) {
			System.out.println("An exception occurred during transition paint this might be valid in case of a resize in the middle of a transition");
			t.printStackTrace();
		}
	}

	private void paintAlpha(Graphics graphics) {
		// this will always be invoked on the EDT so there is no race condition risk
		if(secondaryBuffer != null) {
			Component src = getSource();
			int w = src.getWidth();
			int h = src.getHeight();
			int position = this.position;
			if (position > 255) {
				position = 255;
			} else {
				if (position < 0) {
					position = 0;
				}
			}
			if(secondaryBuffer != null) {
				Component dest = getDestination();                
				int x = dest.getAbsoluteX();
				int y = dest.getAbsoluteY();

				graphics.drawImage(buffer, x, y);
				secondaryBuffer.modifyAlpha((byte)position);
				graphics.drawImage(secondaryBuffer, x, y);

				/* CHANGE::
				graphics.drawImage(buffer, x, y);
				graphics.setAlpha(position);
				graphics.drawImage(secondaryBuffer, x, y);
				graphics.setAlpha(0xff);
				*/
			}
		} 
	}

	private void paintSlideAtPosition(Graphics g, int slideX, int slideY) {
		Component source = getSource();

		// if this is the first form we can't do a slide transition since we have no source form
		if (source == null) { 
			return;           
		}

		Component dest = getDestination();                
		int w = source.getWidth();
		int h = source.getHeight();

		if (slideType == SLIDE_HORIZONTAL) {
			h = 0;
		} else {
			w = 0;
		}

		if(forward) {
			w = -w;
			h = -h;
		} else {
			slideX = -slideX;
			slideY = -slideY;
		}
		g.setClip(source.getAbsoluteX()+source.getScrollX(), source.getAbsoluteY()+source.getScrollY(), source.getWidth(), source.getHeight());

		// dialog animation is slightly different... 
		if(source instanceof Dialog) {
			g.drawImage(buffer, 0, 0);
			paint(g, source, -slideX, -slideY);
			return;
		} 

		if(dest instanceof Dialog) {
			g.drawImage(buffer, 0, 0);
			paint(g, dest, -slideX - w, -slideY - h);
			return;
		} 

		//g.setClip(source.getAbsoluteX(), source.getAbsoluteY(), source.getWidth(), source.getHeight());

		//g.clipRect(dest.getAbsoluteX(), dest.getAbsoluteY(), source.getWidth(), source.getHeight());
		if(source.getParent() != null){
			source.paintBackgrounds(g);
			paint(g, source, slideX , slideY );
		}else{
			g.drawImage(buffer, slideX, slideY);        
		}
		paint(g, dest, slideX + w, slideY + h);

	}

	private void paint(Graphics g, Component cmp, int x, int y) {
        int cx = g.getClipX();
        int cy = g.getClipY();
        int cw = g.getClipWidth();
        int ch = g.getClipHeight();
        if(cmp instanceof Dialog) {
            if(transitionType != TYPE_FADE) {
                if(!(getSource() instanceof Dialog && getDestination() instanceof Dialog && 
                        cmp == getDestination())) {
                    Painter p = cmp.getStyle().getBgPainter();
                    cmp.getStyle().setBgPainter(null);
                    g.translate(x, y);
                    Dialog dlg = (Dialog)cmp;
                    g.setClip(0, 0, cmp.getWidth(), cmp.getHeight());
                    dlg.getTitleComponent().paintComponent(g, false);
                    g.setClip(0, 0, cmp.getWidth(), cmp.getHeight());
                    dlg.getContentPane().paintComponent(g, false);
                    g.translate(-x, -y);
                    if(drawDialogMenu && dlg.getCommandCount() > 0) {
                        Component menuBar = dlg.getSoftButton(0).getParent();
                        if(menuBar != null) {
                            g.setClip(0, 0, cmp.getWidth(), cmp.getHeight());
                            menuBar.paintComponent(g, false);
                        }
                    }

                    g.setClip(cx, cy, cw, ch);
                    cmp.getStyle().setBgPainter(p);
                    return;
                }
            } 
            cmp.paintComponent(g, false);
            return;
        }
        //g.clipRect(cmp.getAbsoluteX(), cmp.getAbsoluteY(), cmp.getWidth(), cmp.getHeight());
         g.translate(x, y);
        //g.clipRect(cmp.getAbsoluteX(), cmp.getAbsoluteY(), cmp.getWidth(), cmp.getHeight());
        cmp.paintComponent(g, false);
         g.translate(-x, -y);
        
        g.setClip(cx, cy, cw, ch);
    }
    
	/**
	 * @inheritDoc
	 */
	public void cleanup() {
		super.cleanup();
		buffer = null;
		secondaryBuffer = null;
	}

	/**
	 * Motion represents the physical movement within a transition, it can be
	 * replaced by the user to provide a more appropriate physical feel
	 * 
	 * @return the instanceo of the motion class used by this transition
	 */
	public Motion getMotion() {
		return motion;
	}

	/**
	 * Motion represents the physical movement within a transition, it can be
	 * replaced by the user to provide a more appropriate physical feel
	 * 
	 * @param motion
	 *            new instance of the motion class that will be used by the
	 *            transition
	 */
	public void setMotion(Motion motion) {
		this.motion = motion;
	}

	/**
	 * @inheritDoc
	 */
	public Transition copy() {
		CommonTransitions retVal = null;
		if(transitionType == TYPE_FADE){
			retVal = CommonTransitions.createFade(speed);
		}else if(transitionType == TYPE_SLIDE){
			retVal = CommonTransitions.createSlide(slideType, forward, speed, drawDialogMenu);
		}else if(transitionType == TYPE_EMPTY){
			retVal = CommonTransitions.createEmpty();
		}
		return retVal;
	}

}
