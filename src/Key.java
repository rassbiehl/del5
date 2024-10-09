public class Key extends Item{
    private String id;

    public Key (String name, String longName, String id) {
        super(name, longName);
        this.id = id;
    }

    public String getId () {
        return this.id;
    }
}