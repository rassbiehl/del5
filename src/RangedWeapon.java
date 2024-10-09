public class RangedWeapon extends Weapon {
    private int ammunition;

    public RangedWeapon (String name, String longName, int damage, int ammunition) {
        super (name, longName, damage);
        this.ammunition = ammunition;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition() {
        ammunition -= 1;
    }
}
