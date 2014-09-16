package com.sun.dtv.ui;

public class DTVContainerPattern extends Object {

	class Preference {

		private Object value;
		private int priority;

		public Preference(Object value, int priority) {
			this.value = value;
			this.priority = priority;
		}
	}

	public static final int DTV_CONTAINER_PLANE_SETUP = 16;
	public static final int DTV_CONTAINER_DIMENSION = 17;
	public static final int DTV_CONTAINER_LOCATION = 18;

	private Preference [] preferences;

	public DTVContainerPattern()
	{
		preferences = new Preference[3];
		for (int i = 0; i < preferences.length; i++) 
		{
			preferences[i] = new Preference(null,PlaneSetupPattern.DONT_CARE);
		}
	}

	/**
	 * Sets the indicated preference to have the specified value with the
	 * specified priority. A call to this method can be also used to overwrite
	 * values and priorities of previously set preferences.
	 * 
	 * 
	 * @param preference
	 *            - the preference to to be indicated. Valid values are
	 *            DTV_CONTAINER_GRAPHICS_SETUP, DTV_CONTAINER_DIMENSION, and
	 *            DTV_CONTAINER_LOCATION.
	 * @param value
	 *            - the value to be set for the indicated preference
	 * @param priority
	 *            - the priority of the indicated preference. Valid values are:
	 *            REQUIRED, PREFERRED, and DONT_CARE
	 * @throws IllegalArgumentException
	 *             - if any of the parameters has an unappropriate value
	 */
	public void setPreference(int preference, Object value, int priority) throws IllegalArgumentException 
	{
		if (preference < DTV_CONTAINER_PLANE_SETUP || preference > DTV_CONTAINER_LOCATION) 
		{
			throw new IllegalArgumentException("Preference parameter is invalid");
		}
		// Valid values are: REQUIRED, PREFERRED, and DONT_CARE
		if (priority != PlaneSetupPattern.REQUIRED && priority != PlaneSetupPattern.PREFERRED && priority != PlaneSetupPattern.DONT_CARE) 
		{
			throw new IllegalArgumentException("Priority parameter is invalid");
		}

		Preference tmp = preferences[preference - DTV_CONTAINER_PLANE_SETUP];
		tmp.value = value;
		tmp.priority = priority;
	}

	/**
	 * Returns the preference value for the specified preference.
	 * 
	 * 
	 * @param preference
	 *            - the preference for which the value should be retrieved.
	 *            Valid values are DTV_CONTAINER_PLANE_SETUP,
	 *            DTV_CONTAINER_DIMENSION, and DTV_CONTAINER_LOCATION.
	 * @return the currently valid value for the specified preference. This
	 *         should be a PlaneSetup object for DTV_CONTAINER_PLANE_SETUP, and
	 *         a Dimension for DTV_CONTAINER_DIMENSION or DTV_CONTAINER_LOCATION
	 * @throws IllegalArgumentException
	 *             - if the paramter has an unappropriate value
	 */
	public Object getPreferenceValue(int preference) throws IllegalArgumentException 
	{
		if (preference < DTV_CONTAINER_PLANE_SETUP || preference > DTV_CONTAINER_LOCATION) 
		{
			throw new IllegalArgumentException("Preference parameter is invalid");
		}
		else return preferences[preference - DTV_CONTAINER_PLANE_SETUP].value;
	}

	/**
	 * Returns the preference priority for the specified preference.
	 * 
	 * 
	 * @param preference
	 *            - the preference for which the value should be retrieved.
	 *            Valid values are DTV_CONTAINER_GRAPHICS_SETUP,
	 *            DTV_CONTAINER_DIMENSION, and DTV_CONTAINER_LOCATION.
	 * @return the currently valid priority for the specified preference.
	 *         Possible return values are: REQUIRED, PREFERRED, and DONT_CARE
	 * @throws IllegalArgumentException
	 *             - if the paramter has an unappropriate value
	 */
	public int getPriority(int preference) throws IllegalArgumentException 
	{
		if (preference < DTV_CONTAINER_PLANE_SETUP || preference > DTV_CONTAINER_LOCATION) 
		{
			throw new IllegalArgumentException("Preference parameter is invalid");
		}
		else return preferences[preference - DTV_CONTAINER_PLANE_SETUP].priority;
	}

}
