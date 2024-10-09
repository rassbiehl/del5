public class Weapon extends Item {
    private int damage;

    public Weapon(String name, String longName, int damage) {
        super(name, longName);
        this.damage = damage;
    }

    public int getDamage () {
        return this.damage;
    }
    @Override
    public String toString() {
        return getName() + ", " + getLongName() + ".";
    }
}
