package me.iffa.styxspace.api.player;

/**
 * @author iffa
 */
public interface SpacePlayer extends org.bukkit.entity.Player {
	/**
	 * Checks if a player is in space.
	 * 
	 * @return true if the player is in space
	 */
	public boolean isInSpace();

	/**
	 * Checks if a player has the specified permission node.
	 * 
	 * @param permission
	 *            Permission node to check
	 * @return true if the player has permission
	 */
	public boolean hasAnyPermission(String permission);

	/**
	 * Checks if a player has a space helmet.
	 * 
	 * @return true if the player has a spacehelmet
	 */
	public boolean hasSpaceHelmet();

	/**
	 * Overrides the configuration value and changes the space helmet.
	 * 
	 * @param blockid
	 *            any block id
	 */
	public void setSpaceHelmet(Integer blockid);

	/**
	 * Sets the player's space suit to the given armor type.
	 * 
	 * @param armortype
	 *            Diamond, chainmail, gold, iron or leather
	 */
	public void setSpaceSuit(String armortype);

	/**
	 * Gives a player the specified space suit
	 * 
	 * @param armortype
	 *            Diamond, chainmail, gold, iron, leather or null
	 */
	public void giveSpaceSuit(String armortype);

}
