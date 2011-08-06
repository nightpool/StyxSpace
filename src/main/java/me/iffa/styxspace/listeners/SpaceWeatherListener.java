// Package Declaration
package me.iffa.styxspace.listeners;

// StyxSpace Imports
import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceConfigHandler;

// Bukkit Imports
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

/**
 * WeatherListener for weather changes.
 * 
 * @author iffa
 * 
 */
public class SpaceWeatherListener extends WeatherListener {
    // Variables
    private StyxSpace plugin;
    
    // Constructor
    public SpaceWeatherListener(StyxSpace plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when the weather changes.
     * 
     * @param event Event data
     */
    @Override
    public void onWeatherChange(WeatherChangeEvent event) {
        if (StyxSpace.worldHandler.isSpaceWorld(event.getWorld())) {
            if (!SpaceConfigHandler.allowWeather(event.getWorld())) {
                if (event.toWeatherState() == true) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
