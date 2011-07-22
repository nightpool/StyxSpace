package me.iffa.styxspace.schedulers;

import me.iffa.styxspace.StyxSpace;

/**
 * A runnable class for forcing night.
 * 
 * @author iffa
 * 
 */
public class SpaceRunnable implements Runnable {
	public SpaceRunnable() {
	}

	/**
	 * Forces night when repeated every 8399 ticks.
	 */
	@Override
	public void run() {
		StyxSpace.getSpace().setTime(13801);
	}
}
