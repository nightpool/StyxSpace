package me.iffa.styxspace.schedulers;

import me.iffa.styxspace.StyxSpace;

public class SpaceRunnable implements Runnable {
	public SpaceRunnable() {
	}

	@Override
	public void run() {
		StyxSpace.getSpace().setTime(13801);
	}
}
