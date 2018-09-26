package ch.bbw.zuul.zuul.zuul;

/**
 * Exit class for every Room
 */
public class Exit {
	private String name;
	private Room nextRoom;
	
	public Exit(Room nextRoom, String name) {
		this.nextRoom = nextRoom;
		this.name = name;
	}
	
	/**
	 * Default Constructor sends you to nowhere
	 */
	public Exit() {
		name = "ExitVillage";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Room getNextRoom() {
		return nextRoom;
	}

	public void setNextRoom(Room nextRoom) {
		this.nextRoom = nextRoom;
	}
	
	
}
