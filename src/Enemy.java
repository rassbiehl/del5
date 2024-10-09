public class Enemy {
    private int health;
    private String name;
    private String description;
    private Weapon weapon;

    public Enemy(String name, String description, Weapon weapon, int health) {
        this.name = name;
        this.description = description;
        this.weapon = weapon;
        this.health = health;
    }

    //GETMETHODS -----------------------------------------------------------------------------------------------
    public Weapon getWeapon() {
        return this.weapon;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getHealth() {
        return this.health;
    }

    // SETHETHODS------------------------------------------------------------------------------------------------
    public void setWeapon(Weapon whatWeapon) {
        this.weapon = whatWeapon;
    }

    //this method accepts a weaponobject since we want to know which weapon it is, so we know how much damage it gives to the player.
    public int takeDamage(Weapon whatWeapon) {
        if (whatWeapon instanceof MeleeWeapon) {
            this.health -= whatWeapon.getDamage();
            return whatWeapon.getDamage(); // return the amount of damage taken
        } else if (whatWeapon instanceof RangedWeapon) {
            this.health -= whatWeapon.getDamage();
            return whatWeapon.getDamage(); // returns the amount of damage taken from the specific weapon.
        }
        else return 0;
    }

    public boolean death() {
        if (this.health < 1) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return this.name + " " + description;
    }
}
