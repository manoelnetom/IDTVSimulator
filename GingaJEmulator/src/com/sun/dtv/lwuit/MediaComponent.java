package com.sun.dtv.lwuit;

import javax.media.MediaException;
import javax.media.Player;

public class MediaComponent extends Component {

	private Player player;

	/**
	 * Creates a new instance of MediaComponent.
	 *
	 * 
	 * @param player - the media player
	 */
	public MediaComponent(Player player)
	{
		this.player = player;
		
		setUIID("MediaComponent");

		setFocusable(false);
	}

	/**
	 * Start media playback implicitly setting the component to visible.
	 *
	 * 
	 * 
	 * @throws MediaException - if starting media fails
	 */
	public void startMedia() throws MediaException
	{
		player.start();
	}

	/**
	 * Stop media playback.
	 *
	 * 
	 * 
	 * @throws MediaException - if stopping media fails
	 */
	public void stopMedia() throws MediaException
	{
		player.stop();
	}
}
