package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;

public class LayoutStyle extends Object
{
	/**
	 * Possible argument to getPreferredGap.  Used to indicate the two components
	 * are grouped together.
	 *
	 */
	public static final int RELATED = 0;

	/**
	 * Possible argument to getPreferredGap.  Used to indicate the two components
	 * are not grouped together.
	 * 
	 */
	public static final int UNRELATED = 1;

	/**
	 * Possible argument to getPreferredGap.  Used to indicate the distance
	 * to indent a component is being requested.  To visually indicate
	 * a set of related components they will often times be horizontally
	 * indented, the <code>INDENT</code> constant for this.
	 * For example, to indent a check box relative to a label use this
	 * constant to <code>getPreferredGap</code>.
	 * 
	 */
	public static final int INDENT = 3;

	/**
	 * following variables are implicitely defined by getter- or setter-methods:
	 *
	 */
	private static LayoutStyle sharedInstance = new LayoutStyle();

	/**
	 * Constructor
	 * 
	 */
	public LayoutStyle()
	{
	}

	/**
	 * Sets the LayoutStyle instance to use for this look and feel.
	 * You generally don't need to invoke this, instead use the getter which
	 * will return the LayoutStyle appropriate for the current look and feel.
	 *
	 * 
	 * @param layout - the LayoutStyle to use; a value of null indicates  the default should be used
	 * @see getSharedInstance()
	 */
	public static void setSharedInstance(LayoutStyle layout)
	{
		sharedInstance = layout;
	}

	/**
	 * Factory methods for obtaining the current <code>LayoutStyle</code>
	 * object appropriate for the current look and feel.
	 *
	 * 
	 * @return the current LayoutStyle instance
	 * @see setSharedInstance(com.sun.dtv.lwuit.layouts.LayoutStyle)
	 */
	public static LayoutStyle getSharedInstance()
	{
		return sharedInstance;
	}

	/**
	 * Returns the amount of space to use between two components.
	 * The return value indicates the distance to place
	 * <code>component2</code> relative to <code>component1</code>.
	 * For example, the following returns the amount of space to place
	 * between <code>component2</code> and <code>component1</code>
	 * when <code>component2</code> is placed vertically above
	 * <code>component1</code>:
	 * <pre>
	 * int gap = getPreferredGap(component1, component2,
	 * LayoutStyle.RELATED,
	 * SwingConstants.NORTH, parent);
	 * </pre>
	 * The <code>type</code> parameter indicates the type
	 * of gap being requested.  It can be one of the following values:
	 * <table>
	 * <tr><td><code>RELATED</code>
	 * <td>If the two components will be contained in
	 * the same parent and are showing similar logically related
	 * items, use <code>RELATED</code>.
	 * <tr><td><code>UNRELATED</code>
	 * <td>If the two components will be
	 * contained in the same parent but show logically unrelated items
	 * use <code>UNRELATED</code>.
	 * <tr><td><code>INDENT</code>
	 * <td>Used to obtain the preferred distance to indent a component
	 * relative to another.  For example, if you want to horizontally
	 * indent a JCheckBox relative to a JLabel use <code>INDENT</code>.
	 * This is only useful for the horizontal axis.
	 * </table>
	 *
	 * It's important to note that some look and feels may not distinguish
	 * between <code>RELATED</code> and <code>UNRELATED</code>.
	 *
	 * The return value is not intended to take into account the
	 * current size and position of <code>component2</code> or
	 * <code>component1</code>.  The return value may take into
	 * consideration various properties of the components.  For
	 * example, the space may vary based on font size, or the preferred
	 * size of the component.
	 *
	 * 
	 * @param component1 - the JComponent component2 is being placed relative to
	 * @param component2 - the JComponent being placed
	 * @param type - how the two components are being placed
	 * @param position - the position component2 is being placed relative to component1; one of SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.EAST or SwingConstants.WEST
	 * @param parent - the parent of component2; this may differ from the actual parent and may be null
	 * @return the amount of space to place between the two components
	 * @throws IllegalArgumentException - if position is not one of SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.EAST or SwingConstants.WEST; type not one of INDENT, RELATED or UNRELATED; or component1 or component2 is null
	 */
	public int getPreferredGap(Component component1, Component component2, int type, int position, Container parent)
	{
		if (position != GroupLayout.NORTH && position != GroupLayout.SOUTH && position != GroupLayout.WEST && position != GroupLayout.EAST) {
			throw new IllegalArgumentException("Invalid position");
		}
		
		if (component1 == null || component2== null) {
			throw new IllegalArgumentException("Components must be non-null");
		}
		
		if (type == RELATED) {
			return 6;
		} else if (type == UNRELATED) {
			return 12;
		} else if (type == INDENT) {
			if (position == GroupLayout.EAST || position == GroupLayout.WEST) {
				int gap = getButtonChildIndent(component1, position);
				if (gap != 0) {
					return gap;
				}
				return 6;
			}
			return 6;
		}
		
		throw new IllegalArgumentException("Invalid type");
	}

	/**
	 * Returns the amount of space to position a component inside its
	 * parent.
	 *
	 * 
	 * @param component - the Component being positioned
	 * @param position - the position component is being placed  relative to its parent; one of SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.EAST or SwingConstants.WEST
	 * @param parent - the parent of component; this may differ from the actual parent and may be null
	 * @return the amount of space to place between the component and specified edge
	 * @throws IllegalArgumentException - if position is not one of SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.EAST or SwingConstants.WEST; or component is null
	 */
	public int getContainerGap(Component component, int position, Container parent)
	{
		if (position != GroupLayout.NORTH && position != GroupLayout.SOUTH && position != GroupLayout.WEST && position != GroupLayout.EAST) {
			throw new IllegalArgumentException("Invalid position");
		}

		if (component == null) {
			throw new IllegalArgumentException("Component must be non-null");
		}
		
		return 12;
	}

	/**
	 * Returns the amount to indent the specified component if it's
	 * a JCheckBox or JRadioButton.  If the component is not a JCheckBox or
	 * JRadioButton, 0 will be returned.
	 *
	 */
	int getButtonChildIndent(Component c, int position) {
		return 0;
	}
}

