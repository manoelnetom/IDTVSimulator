/*
 * Copyright (c) 2009 LAViD
 *
 *  Copyright (C) 2009 Jefferson Ferreira <jefferson@lavid.ufpb.br>
 */
/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.sun.dtv.lwuit.animations;

public interface Animated {

	/* Value for the animation mode. */

	public static final int ALTERNATING = 2;

	/* Value for the repetition mode. */

	public static final int LOOP = 0;

	/* Value for the animation mode. */
	public static final int REPEATING = 1;

	// Obtains the animation mode for this animation.

	public int getAnimationMode();

	// Obtains the delay after every image for this animation when running.

	public int getDelay();

	// Obtains the current position the animation is at the point of time of th
	// method call.

	public int getPosition();

	// Returns the repetition mode of this animation in form of a number.

	public int getRepetitionMode();

	// Obtains the running mode of an animation.

	public boolean isRunning();

	// Forces an animation to jump to the position indicated by the parameter.

	public void jumpTo(int position);

	// Sets the animation mode for this animation.

	public void setAnimationMode(int mode);

	// Determines the delay after every image when running this animation.

	public void setDelay(int n);

	// Determines how often the animation is repeated once it has been started.

	public void setRepetitionMode(int n);

	// Starts an animation.

	public void start();

	// Stops an animation.

	public void stop();

}
