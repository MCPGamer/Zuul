package ch.bbw.zuul.zuul.zuul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean wantToQuit;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Exit ausgang, theatreExit, pubExit, labExit, officeExit;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university", "Ausgang");
        theatre = new Room("in a lecture theatre", "Theatre");
        pub = new Room("in the campus pub", "Pub");
        lab = new Room("in a computing lab", "Lab");
        office = new Room("in the computing admin office", "Office");
        
        // create Exits
        ausgang = new Exit(outside, "VillageCenter");
        theatreExit = new Exit(theatre, "TheatreDoor");
        pubExit = new Exit(pub, "PubDoor");
        labExit = new Exit(lab, "LabDoor");
        officeExit = new Exit(office, "OfficeDoor");
        
        // initialise room exits
        outside.setExits(new ArrayList<Exit>(Arrays.asList(theatreExit, labExit, pubExit, new Exit())));
        theatre.setExits(new ArrayList<Exit>(Arrays.asList(ausgang)));
        pub.setExits(new ArrayList<Exit>(Arrays.asList(ausgang)));
        lab.setExits(new ArrayList<Exit>(Arrays.asList(ausgang, officeExit)));
        office.setExits(new ArrayList<Exit>(Arrays.asList(labExit)));

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Adventure!");
        System.out.println("Adventure is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");

        List<Exit> exits = currentRoom.getExits();
        for(Exit exit : exits) {
        	System.out.print(exit.getName() + " ");
        }
        
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        Exit exit = currentRoom.findExitByName(direction);
        if(exit != null) {
        	nextRoom = exit.getNextRoom();
        }
        

        if(exit.getNextRoom() == null) { // If you went to Outside of the Bounds there is no way back and u lose
        	System.out.println("You have left the Map and so you Lost the Game!");
        	this.wantToQuit = true;
        } else if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
           
            List<Exit> exits = currentRoom.getExits();
            for(Exit e : exits) {
            	System.out.print(e.getName() + " ");
            }
            
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}
