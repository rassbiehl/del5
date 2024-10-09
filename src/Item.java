public class Item {
    private String Name;
    private String longName;

    public Item(String itemName, String longName) {
        this.Name = itemName;
        this.longName = longName;

    }

    //GETMETHODS-------------------------------------------------------------------------------------------------------

    public String getName() {
        return Name;
    }

    public String getLongName() {
        return longName;
    }
    //SETMETHODS-----------------------------------------------------------------------------------------------------------


    @Override
    public String toString() {
        return longName;
    }
}