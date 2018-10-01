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
    
    // The Different Characters in the Game
    private Player player, blackSmith, motelOwner, enemy, boss;
    // Needed for unlocking later on
    private Room bossRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        
        createPlayers();
        
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together with the Exception of "Tunnel" : Exit 
     * since that is only created and added after Enemy in EnemyRoom is defeated;
     */
    private void createRooms()
    {
        Room armory, motel, forest, villageCenter, caveOpening, enemyRoom;
        Exit armoryDoor, armoryDoorBack, motelDoor, motelDoorBack, dirtRoad, dirtRoadBack, stoneRoad, mainRoad, mainRoadBack, exitVillage, caveEntrance, caveEntranceBack, tunnelBack;
      
        // create the rooms
        armory = new Room("in an Armory full with Weapons and Armorstands, sadly all the Armor is sold out so all u can Buy are Weapons", "Armory");
        motel = new Room("in a old Motel where you can decide to Rest to refill your Life and Stamina (costs 5 gold) or just nap for Stamina (No Life Regen but costs 0 gold)", "Motel");
        forest = new Room("in the middle of a Forest filled with Animals you can hunt for gold, the more Damage you do the more gold you earn (A Hunt costs the amount of Stamina an Attack with your Weapon costs)", "Forest");
        villageCenter = new Room("in the Center your Village, but if you Leave the Village through the Exit your Adventure Ends", "VillageCenter");
        caveOpening = new Room("in front of a massive Cave which you can decide to Enter", "CaveOpening");
        enemyRoom = new Room("just passed the Opening but there is a Enemy in front of you. You can Attack it if you'd Like but that costs Stamina and you probably lose Life but to Pass you need to Kill it", "EnemyRoom");
        bossRoom = new Room("passed the Tunnel you found after beating that puny Enemy but in front of you Stands a giant Dragon. You must Defeat it so that you win by saving your Village but that takes Stamina and Life", "BossRoom");
        
        // create Exits
        armoryDoor = new Exit(armory, "ArmoryDoor");
        armoryDoorBack = new Exit(villageCenter, "ArmoryDoorBack");
        motelDoor = new Exit(motel, "MotelDoor");
        motelDoorBack = new Exit(villageCenter, "MotelDoorBack");
        dirtRoad = new Exit(forest, "DirtRoad");
        dirtRoadBack = new Exit(villageCenter, "DirtRoadBack");
        stoneRoad = new Exit(armory, "StoneRoad");
        mainRoad = new Exit(caveOpening, "MainRoad");
        mainRoadBack = new Exit(villageCenter, "MainRoadBack");
        exitVillage = new Exit();
        caveEntrance = new Exit(enemyRoom, "CaveEntrance");
        caveEntranceBack = new Exit(caveOpening, "CaveEntranceBack");
        tunnelBack = new Exit(enemyRoom, "TunnelBack");
        
        // initialise room exits
        armory.setExits(new ArrayList<Exit>(Arrays.asList(armoryDoorBack)));
        motel.setExits(new ArrayList<Exit>(Arrays.asList(motelDoorBack)));
        forest.setExits(new ArrayList<Exit>(Arrays.asList(dirtRoadBack, stoneRoad)));
        villageCenter.setExits(new ArrayList<Exit>(Arrays.asList(armoryDoor, motelDoor, dirtRoad, mainRoad, exitVillage)));
        caveOpening.setExits(new ArrayList<Exit>(Arrays.asList(mainRoadBack, caveEntrance)));
        enemyRoom.setExits(new ArrayList<Exit>(Arrays.asList(caveEntranceBack))); // Tunnel only added once 1st Enemy Defeated
        bossRoom.setExits(new ArrayList<Exit>(Arrays.asList(tunnelBack)));

        currentRoom = villageCenter;  // start game at VillageCenter
    }
    
    /**
     * Instantiates all Characters and the Player
     */
    private void createPlayers() {
    	this.player = new Player(PlayerType.Player);
    	this.blackSmith = new Player(PlayerType.BlackSmith);
    	this.motelOwner= new Player(PlayerType.MotelOwner);
    	this.enemy = new Player(PlayerType.Enemy);
    	this.boss = new Player(PlayerType.Boss);
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
        System.out.println("Adventure is a new, incredibly boring adventure game. You have to get Gold so that you can Buy some Weapon to save your village from a giant Dragon.");
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
        if (commandWord.equals("help")) {
            printHelp();
    	} else if (commandWord.equals("go")) {
        	goRoom(command);
        } else if (commandWord.equals("quit")) {
        	wantToQuit = quit(command);
        } else if (commandWord.equals("buy")) {
        	if(currentRoom.getRoomName().equals("Armory")) {
        		buy(command);
        	} else {
        		System.out.println("Cant buy unless you're in the Armory");
        	}
        }  else if (commandWord.equals("sleep")) {
        	if(currentRoom.getRoomName().equals("Motel")) {
        		sleep(command);
        	} else {
        		System.out.println("Cant sleep unless you're in the Motel");
        	}
        }  else if (commandWord.equals("hunt")) {
        	if(currentRoom.getRoomName().equals("Forest")) {
        		hunt(command);
        	} else {
        		System.out.println("Cant hunt unless you're in the Forest");
        	}
        }  else if (commandWord.equals("attack")) {
        	if(currentRoom.getRoomName().equals("EnemyRoom") || currentRoom.getRoomName().equals("BossRoom")) {
        		attack(command);
        	} else {
        		System.out.println("Cant attack unless you're in the EnemyRoom or BossRoom");
        	}
        }

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
        System.out.println("You need to Defend your village from a Dragon");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
        System.out.println();
        System.out.println("In Some Rooms you may use the Commands:");
        System.out.println("   buy sleep attack hunt");
        System.out.println();
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
        
        
        if(exit == null) {
        	System.out.println("That isn't a valid Exit");
        } else if(exit.getNextRoom() == null) { // If you went to Outside of the Bounds there is no way back and u lose
        	System.out.println("You have left the Map and so you Lost the Game!");
        	this.wantToQuit = true;
        } else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.print("Exits: ");
           
            List<Exit> exits = currentRoom.getExits();
            for(Exit e : exits) {
            	System.out.print(e.getName() + " ");
            }
            
            System.out.println();
            System.out.println();
            
            // Show special Room Texts / Commands
            if(currentRoom.getRoomName().equals("Forest")) {
            	System.out.println("You Currently have " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and " + player.getLife() + "/" + player.getMaxLife() + " Life Left.");
            	System.out.println();
            	System.out.println("List of Weapons in your Inventory: ");
            	for(Weapon w : player.getInventory()) {
            			System.out.println("\t " + w.getName() + "(Stamina Cost: " + w.getStaminaCost() + ", MinDamage: " + w.getMinDamage() + ", MaxDamage: " + w.getMaxDamage() + ")");
            	}
            	System.out.println();
            	System.out.println("To Hunt type \"hunt <WeaponName>\"");
            	System.out.println();
            } else if(currentRoom.getRoomName().equals("Motel")) {
            	System.out.println("You Currently have " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and " + player.getLife() + "/" + player.getMaxLife() + " Life Left.");
            	System.out.println();
            	System.out.println("Do you wanna Nap to restore Stamina or Rest for 5 Gold to Restore Life and Stamina?");
            	System.out.println();
            	System.out.println("To Sleep type \"sleep <Nap/Rest>\"");
            	System.out.println();
            } else if(currentRoom.getRoomName().equals("Armory")) {
            	System.out.println("You Currently have " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and " + player.getLife() + "/" + player.getMaxLife() + " Life Left.");
            	System.out.println();
            	System.out.println("List of Weapons in the Armory ");
            	for(Weapon w : blackSmith.getInventory()) {
            			System.out.println("\t " + w.getName() + "(Stamina Cost: " + w.getStaminaCost() + ", MinDamage: " + w.getMinDamage() + ", MaxDamage: " + w.getMaxDamage() + ") Price: " + w.getValue());
            	}
            	System.out.println();
            	System.out.println("To Buy type \"buy <WeaponName>\"");
            	System.out.println();
            } else if(currentRoom.getRoomName().equals("EnemyRoom") || currentRoom.getRoomName().equals("BossRoom")) {
            	System.out.println("You Currently have " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and " + player.getLife() + "/" + player.getMaxLife() + " Life Left.");
            	System.out.println();
            	if(!(currentRoom.getRoomName().equals("EnemyRoom" ) && enemy.getLife() <= 0)) {            		
            		System.out.println("List of Weapons in your Inventory: ");
            		for(Weapon w : player.getInventory()) {
            			System.out.println("\t " + w.getName() + "(Stamina Cost: " + w.getStaminaCost() + ", MinDamage: " + w.getMinDamage() + ", MaxDamage: " + w.getMaxDamage() + ")");
            		}
            		System.out.println();
            		System.out.println("To Attack type \"attack <WeaponName>\"");
            		System.out.println();
            	}
            }
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
    
    /**
     * Used in the Forest, Gives "(int) (randomWeaponDamage * 1.5)" gold for the Stamina Cost of the chosen Weapon
     * @param command
     */
    private void hunt(Command command) {
    	Weapon weapon = null;
    	
    	// Check if Player has Weapon 
    	for(Weapon w : player.getInventory()) {
    		if(w.getName().equals(command.getSecondWord())) {
    			weapon = w;
    		}
    	}
    	
    	if(weapon != null) {
    		if(player.getStamina() >= weapon.getStaminaCost()) { 
    			// Random Damage and Random amount of Money trough that
    			int damage = player.calculateDamage(weapon);
    			int wonGold = (int) (damage * 1.5);
    			
    			player.setStamina(player.getStamina() - weapon.getStaminaCost());
    			player.setGoldAmount(player.getGoldAmount() + wonGold);
    			System.out.println("You Hunt Some Animals with your" + command.getSecondWord() + " and get " + wonGold + " gold");
    			System.out.println("You're now at " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and have " + player.getLife() + "/" + player.getMaxLife() + " Life Left." );
    		} else {
    			System.out.println("You're too Tired to Hunt, go to the Motel and Sleep!");
    		}
    	} else {
    		System.out.println("Hunt with What?");
    	}
    }
    
    /**
     * Used in the Motel, Restores either Stamina or both Stamina and Life for 5 Gold
     * @param command
     */
    private void sleep(Command command) {
    	 if(command.hasSecondWord()) {
    		 // Nap for Stamina
    		 if(command.getSecondWord().equals("Nap")) {
    			 System.out.println("You Nap and Restore your Stamina");
    			 player.setStamina(player.getMaxStamina());
    			 System.out.println("You're now at " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina Left.");
    		 } else if (command.getSecondWord().equals("Rest")) { // Rest for Life aswell as Stamina, Costs 5 which go to the MotelOwner and MotelOwner's Money is only for Stats calculation
    			 if(player.getGoldAmount() >= 5) {
	    			 System.out.println("You Rest and Restore your Stamina aswell as your Life");
	    			 player.setStamina(player.getMaxStamina());
	    			 player.setLife(player.getMaxLife());
	    			 player.setGoldAmount(player.getGoldAmount() - 5);
	    			 motelOwner.setGoldAmount(motelOwner.getGoldAmount() + 5);
	     			System.out.println("You're now at " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and have " + player.getLife() + "/" + player.getMaxLife() + " Life Left." );
    			 } else {
    				 System.out.println("You dont have 5 Gold so you can't Rest");
    			 } 
    		 } else {
    			 System.out.println("That Kind of Sleep wasn't Found");
    		 }
    	 } else {
    		 System.out.println("Sleep Rest or Sleep Nap");
    	 }
    }
    
    /**
     * Buy Items from Blacksmith in the Armory, Price is the Weapons Value
     * @param command
     */
    private void buy(Command command) {
    	Weapon weapon = null;
    	
    	// Check if BlackSmith has Weapon 
    	for(Weapon w : blackSmith.getInventory()) {
    		if(w.getName().equals(command.getSecondWord())) {
    			weapon = w;
    		}
    	}
    	
    	if(weapon != null) {// Buy the Weapon you chose and take it from BlackSmiths Inventory, give him the Money for Stats calculation
    		if(player.getGoldAmount() >= weapon.getValue()) { 
    			System.out.println("You buy " + weapon.getName());
    			blackSmith.getInventory().remove(weapon);
    			blackSmith.setGoldAmount(blackSmith.getGoldAmount() + weapon.getValue());
    			player.getInventory().add(weapon);
    			player.setGoldAmount(player.getGoldAmount() - weapon.getValue());
    			System.out.println("You're now at " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and have " + player.getLife() + "/" + player.getMaxLife() + " Life Left." );
    		} else {
    			System.out.println("You Don't have enough money to buy this");
    		}
    	} else {
    		System.out.println("Buy What?");
    	}
    }
    
    /**
     * Attack an Enemy, Gives Damage to you and to Him but also uses Stamina
     * @param command
     */
    private void attack(Command command) {
    	Weapon weapon = null;
    	
    	// Check if Player has Weapon 
    	for(Weapon w : player.getInventory()) {
    		if(w.getName().equals(command.getSecondWord())) {
    			weapon = w;
    		}
    	}
    	
    	if(weapon != null) {
    		if(player.getStamina() >= weapon.getStaminaCost()) { 
    			// Random Damage and Random amount of Money trough that
    			int damage = player.calculateDamage(weapon); // Calc your Damage and use Stamina
    			player.setStamina(player.getStamina() - weapon.getStaminaCost());

    			if(currentRoom.getRoomName().equals("EnemyRoom")) { 
    				if((enemy.getLife() - damage)  > 0) {
    					enemy.setLife(enemy.getLife() - damage);// Enemy takes Damage
    					int damageTaken = enemy.calculateDamage(enemy.getInventory().get(0));
    					System.out.println("You've done " + damage + " damage, the Enemy is now at " + enemy.getLife() + "/" + enemy.getMaxLife() + " life");
    					if((player.getLife() - damageTaken)  >= 0) { // Take Damage
    						player.setLife(player.getLife() - damageTaken);    						
    					} else {
    						System.out.println("The Enemy killed you. GG");// Died and Lost
    						wantToQuit = true;
    					}
    				} else {
    					System.out.println("You Killed the Enemy and won his Weapon and his Gold"); // Killed the Enemy and got WeaponDrop
    					player.getInventory().add(enemy.getInventory().get(0));
    					player.setGoldAmount(player.getGoldAmount() + enemy.getGoldAmount());
    					currentRoom.setDescription("just passed the Opening where you've slain the Enemy and a Tunnel Opens in front of you.");
    					Exit tunnel = new Exit(bossRoom, "Tunnel");
    					currentRoom.addExit(tunnel);
    				}
    			} else if(currentRoom.getRoomName().equals("BossRoom")) { 
    				if((boss.getLife() - damage)  > 0) {
    					boss.setLife(boss.getLife() - damage);// Boss takes Damage
    					int damageTaken = boss.calculateDamage(boss.getInventory().get(0));
     					System.out.println("You've done " + damage + " damage, the Boss is now at " + boss.getLife() + "/" + boss.getMaxLife() + " life");
						if((boss.getLife() - damageTaken)  >= 0) { // Take Damage
    						boss.setLife(boss.getLife() - damageTaken);    						
    					} else {
    						System.out.println("The Boss killed you. GG");// Died and Lost
    						wantToQuit = true;
    					}
    				} else {
    					System.out.println("You Killed the Boss and won the Game. GG!"); // Killed the Boss and Won
						wantToQuit = true;
    				}
    			}
    			
    			System.out.println("You're now at " + player.getGoldAmount() + " Gold and have " + player.getStamina() + "/" + player.getMaxStamina() + " Stamina and have " + player.getLife() + "/" + player.getMaxLife() + " Life Left." );
    		} else {
    			System.out.println("You're too Tired to Hunt, go to the Motel and Sleep!");
    		}
    	} else {
    		System.out.println("Hunt with What?");
    	}
    }
}
