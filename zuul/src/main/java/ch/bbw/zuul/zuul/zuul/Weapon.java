package ch.bbw.zuul.zuul.zuul;

/**
 * Weapons are the only Items ingame
 * Each Weapon has a Stamina Cost, a Value(in case it's gotten ingame through buying),
 *  a Description, a Name and a Min / Max Damage(since the DamageOutput is random within this Range)
 * @author godu
 *
 */
public class Weapon {
	private String name;
	private String description;
	private int minDamage;
	private int maxDamage;
	private int staminaCost;
	private int value;
	
	/**
	 * Constructor that initializes all Parameters
	 * @param name
	 * @param description
	 * @param minDamage
	 * @param maxDamage
	 * @param staminaCost
	 * @param value
	 */
	public Weapon(String name, String description, int minDamage, int maxDamage, int staminaCost, int value) {
		this.name = name;
		this.description = description;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.staminaCost = staminaCost;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public int getStaminaCost() {
		return staminaCost;
	}

	public void setStaminaCost(int staminaCost) {
		this.staminaCost = staminaCost;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
