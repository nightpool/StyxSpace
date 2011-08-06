// Package Declaration
package me.iffa.styxspace.api.event.misc;

// Bukkit Imports
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player uses the 'space'-command.
 * 
 * @author iffa
 */
public class SpaceCommandEvent extends Event implements Cancellable {
    // Variables
    private static final long serialVersionUID = -7384621557571433605L;
    private boolean canceled;
    private CommandSender sender;
    private String arguments[];

    // Constructor
    public SpaceCommandEvent(String event, CommandSender sender, String args[]) {
        super(event);
        this.sender = sender;
        this.arguments = args;
    }

    /**
     * Gets the arguments of the command.
     * 
     * @return Given arguments
     */
    public String[] getArgs() {
        return this.arguments;
    }

    /**
     * Gets the command sender.
     * 
     * @return CommandSender
     */
    public CommandSender getSender() {
        return this.sender;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}
