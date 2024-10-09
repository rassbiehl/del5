public class Food extends Item{
    private int healthGain;


    public Food (String name, String longName, int healthGain) {
        super(name, longName); //calls the constructor of item class.
        this.healthGain = healthGain;
    }

    //GETMETHODS----------------------------------------------------------------------------------------------------

    //gets healthGain on an item
    public int getHealthGain() {
        return healthGain;
    }

    //SETMETHODS----------------------------------------------------------------------------------------------------
}