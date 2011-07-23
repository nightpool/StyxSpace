package me.iffa.styxspace.listeners;

// StyxSpace Imports
import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.utils.SpaceConfig;

// Bukkit Imports
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

/**
 * WeatherListener
 * 
 * @author iffa
 * 
 */
public class SpaceWeatherListener extends WeatherListener {
	public static StyxSpace plugin;

	public SpaceWeatherListener(StyxSpace instance) {

		plugin = instance;
	}

	/**
	 * Called when the weather changes.
	 */
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
