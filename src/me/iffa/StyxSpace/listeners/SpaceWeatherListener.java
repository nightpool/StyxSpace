package me.iffa.styxspace.listeners;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.utils.SpaceConfig;

import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

public class SpaceWeatherListener extends WeatherListener {
	public static StyxSpace plugin;

	public SpaceWeatherListener(StyxSpace instance) {

		plugin = instance;
	}

	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.getWorld() == StyxSpace.getSpace()) {
			if (SpaceConfig.WEATHER == false) {
				if (event.toWeatherState() == true) {
					event.setCancelled(true);
				}
			}
		}
	}
}
