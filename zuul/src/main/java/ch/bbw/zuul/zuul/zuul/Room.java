package ch.bbw.zuul.zuul.zuul;

import java.util.ArrayList;
import java.util.List;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

public class Room 
{
    private String description;
    private String roomName;
    private List<Exit> exits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     */
    public Room(String description, String roomName) 
    {
        this.description = description;
        this.roomName = roomName;
        exits = new ArrayList<>();
    }

    /**
     * Return the description of the room;
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Sets Description of the room
     * @param description
     */
    public void setDescription(String description) {
    	this.description = description;
    }
    
    /**
     * Return the Name of the room;
     */
    public String getRoomName()
    {
        return roomName;
    }
    
    /**
     * Sets Name of the room
     * @param roomName
     */
    public void setRoomName(String roomName) {
    	this.roomName = roomName;
    }
    
    /**
     * Clears exits and sets attribute with new List
     * @param exits new Exitlist;
     */
    public void setExits(List<Exit> exits) 
    {
        this.exits.clear();
        this.exits.addAll(exits);
    }
    
    /**
     * Returns Exits
     * @return List<Exit>
     */
    public List<Exit> getExits() {
    	return exits;
    }
    
    /**
     * Adds an exit to the exits
     * @param exit
     */
    public void addExit(Exit exit) {
    	exits.add(exit);
    }
    
    /**
     * returns Exit with the given Name. If no Exit is found Null is returned
     * @param name
     * @return Exit
     */
    public Exit findExitByName(String name) {
    	for(Exit exit: exits) {
    		if(exit.getName().equals(name)) {
    			return exit;
    		}
    	}
		return null;
    }
}
