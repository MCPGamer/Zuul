package ch.bbw.zuul.zuul.zuul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/***
 * Class Player for all the NPC's, Mobs and the Player itself
 * Each Player has a PlayerType, a Inventory, a GoldAmount, a stamina / maxStamina and a Life / maxLife
 * @author godu
 *
 */
public class Player {
	private PlayerType playerType;
	private List<Weapon> inventory;
	private int goldAmount;
	private int stamina;
	private int maxStamina;
	private int life;
	private int maxLife;
	
	/**
	 * Constructor Initializes the Different Players all based on PlayerType
	 * @param playerType
	 */
	public Player(PlayerType playerType) {
		this.playerType = playerType;
		inventory = new ArrayList<>();
		
		// Initializaton dependent on PlayerType START
		
		// the Player
		if(this.playerType.equals(PlayerType.Player)) {
			this.goldAmount = 0;
			this.stamina = 10;
			this.maxStamina = 10;
			this.life = 20;
			this.maxLife = 20;
			
			Weapon fist = new Weapon("Fist", "Your Fist", 1, 2, 3, 0);
			inventory.add(fist);
		} else if(this.playerType.equals(PlayerType.BlackSmith)) { // the BlackSmith
			this.goldAmount = 0;
			this.stamina = 0;
			this.maxStamina = 0;
			this.life = 0;
			this.maxLife = 0;
			
			Weapon woodSword = new Weapon("WoodSword", "A badly made Wooden Sword", 2, 4, 3, 5);
			Weapon stoneSword = new Weapon("StoneSword", "A Stone Sword", 3, 5, 3, 20);
			Weapon metalSword = new Weapon("MetalSword", "A high quality Metal Sword", 4, 5, 2, 50);
			Weapon bow = new Weapon("Bow", "A weak Bow", 3, 4, 2, 30);
			Weapon crossBow = new Weapon("CrossBow", "A really awesome Crossbow", 3, 4, 1, 70);
			
			inventory.addAll(Arrays.asList(woodSword, stoneSword, metalSword, bow, crossBow));
		} else if(this.playerType.equals(PlayerType.MotelOwner)) { // the MotelOwner
			this.goldAmount = 0;
			this.stamina = 0;
			this.maxStamina = 0;
			this.life = 0;
			this.maxLife = 0;
			
		} else if(this.playerType.equals(PlayerType.Enemy)) { // the Enemy
			this.goldAmount = 20;
			this.stamina = 0;
			this.maxStamina = 0;
			this.life = 35;
			this.maxLife = 35;
			
			Weapon heavySword = new Weapon("HeavySword", "A Strong Sword", 4, 4, 3, 0);
			
			inventory.addAll(Arrays.asList(heavySword));
		} else if(this.playerType.equals(PlayerType.Boss)) { // the Boss
			this.goldAmount = 0;
			this.stamina = 0;
			this.maxStamina = 0;
			this.life = 75;
			this.maxLife = 75;
			
			Weapon fireBreath = new Weapon("FireBreath", "Duhh Breaths Fire since its a Dragon", 5, 5, 1, 0);
			
			inventory.addAll(Arrays.asList(fireBreath));
		}
		
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	public List<Weapon> getInventory() {
		return inventory;
	}

	public void setInventory(List<Weapon> inventory) {
		this.inventory = inventory;
	}

	public int getGoldAmount() {
		return goldAmount;
	}

	public void setGoldAmount(int goldAmount) {
		this.goldAmount = goldAmount;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
	
	/**
	 * Calculate how much Weapon an Attack / Hunt did, which is random within the Weapons DamageRange
	 * @param w = the weapon
	 * @return the DamageValue
	 */
	public int calculateDamage(Weapon w) {
		return ThreadLocalRandom.current().nextInt(w.getMinDamage(), w.getMaxDamage() + 1);
	}
}
