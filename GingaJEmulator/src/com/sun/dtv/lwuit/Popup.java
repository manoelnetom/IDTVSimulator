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
package com.sun.dtv.lwuit;

/**
 * Used to pop up a Component on the screen, this element is used  for 
 * the combo box pulldown and the menu.
 * 
 * @author Chen Fishbein
 */
class Popup {

    private Component contents;
    private boolean visible = false;
    private com.sun.dtv.lwuit.Form owner;
    
    /**
     * Creates a new popup with with the given parent form and content
     * 
     * @param owner The Form this popup will be attached
     * @param contents The Content to draw when the popup becomes visible
     */
    public Popup(com.sun.dtv.lwuit.Form owner, Component contents){
        this.owner = owner;
        this.contents = contents;
        contents.setParent(owner);
    }

    /**
     * Uses the current form as default for this popup
     * 
     * @param contents The Content to draw when the popup becomes visible
     */
    public Popup(Component contents) {
        // this(Display.getInstance().getCurrent(), contents);
    }
    
    
    /**
     * Displays the popup on the screen
     * 
     * @param visible true if popup will be display on the screen
     */
    public void setVisible(boolean visible) {
        if(this.visible != visible) {
            this.visible = visible;
            if(visible){
                getContents().setParent(owner);
                owner.showPopup(this);
            }else{
                getContents().setParent(null);
                owner.hidePopups();
            }
        }
    }

    /**
     * Returns true if the popuup is currently set to visible
     * 
     * @return true if the popuup is currently set to visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Returns the component within the current popup
     * 
     * @return the component within the current popup
     */
    public Component getContents() {
        return contents;
    }
}
