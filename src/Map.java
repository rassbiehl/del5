
/*CLASS RESPONSIBILITY: Map is the class that initializes the rooms and connects them.
It builds the map for the game */

public class Map {
    private Room room1;
    private Item compass;

    //Constructor, that by standard links all the rooms together in the desired way with the buildWorld-method.
    public Map() {
        buildWorld();
    }

    //buildWorld-method that initializes all the rooms and links them together as neighbors.
    public void buildWorld() {
        //Initializes the rooms and sets their names and descriptions.
        room1 = new Room("the Lobby", "The welcoming area filled with cozy seating and a reception desk.", null);
        Room room2 = new Room("the Dining Room", "A spacious area where guests enjoy meals together.", null);
        Room room3 = new Room("the Library", "A quiet room lined with bookshelves and comfortable reading nooks.", null);
        Room room4 = new Room("the Casino", "A lively room filled with card tables and bright slot machines.", "roullette");
        Room room5 = new Room("the Basement", "A dark, eerie space filled with shadows and the sound of dripping water.", null);
        Room room6 = new Room("the Conference Room", "A modern space equipped for meetings and events", null);
        Room room7 = new Room("the Cinema", "A nostalgic theater featuring classic films and vintage decor.", null);
        Room room8 = new Room("the Ball Hall", "A grand hall for dancing, adorned with chandeliers and mirrors.", null);
        Room room9 = new Room("the Rooftop Terrace", "An outdoor area with stunning views and seating for guests.", null);
        //Sets the rooms neighbors, so it creates a map.
        room1.setNeighbor(null, room4, room2, null);
        room2.setNeighbor(null, null, room3, room1);
        room3.setNeighbor(null, room6, null, room2);
        room4.setNeighbor(room1, room7, null, null);
        room5.setNeighbor(null, room8, null, null);
        room6.setNeighbor(room3, room9, null, null);
        room7.setNeighbor(room4, null, room8, null);
        room8.setNeighbor(room5, null, room9, room7);
        room9.setNeighbor(room6, null, null, room8);
        //create items to the the game.
        compass = new Item("Compass", "a rusty compass 🧭");
        Item book = new Item("Book", "an old book 📖");
        //Food
        Food apple = new Food("Apple", "a delicious fruit. \uD83C\uDF4E 20+ health.", 20);
        Food cake = new Food("Cake", "a colorful birthday dessert. \uD83C\uDF70 40+ health", 20);
        //Keys
        Key casinoKey = new Key("Key", "Will open the casino", "roullette");
        //Weapons
        RangedWeapon pistol = new RangedWeapon("Pistol", "Desert Eagle. 7 bullets", 100, 7);
        MeleeWeapon sword = new MeleeWeapon("Sword", "a long sword 🗡️", 50);
        RangedWeapon awp = new RangedWeapon("Sniper rifle", "a hunting rifle with scope. 3 bullets", 25, 7); //imagine if i made a subclass of rangedWeapon
        //Enemies
        Enemy enemy1 = new Enemy("psychopath", "creepy man with a weapon", pistol, 50);

        //adds the objects to the room
        room1.addRoomItems(compass);
        room2.addRoomItems(book);
        room8.addRoomItems(apple);
        room4.addRoomItems(cake);
        room3.addRoomItems(casinoKey);
        room4.addRoomItems(sword);
        room6.addRoomItems(awp);
        //Adds enemies to the room
        room5.addRoomEnemies(enemy1);
    }

    // GETMETHODS ------------------------------------------------------------------------------------------------------

    public Room getStartingRoom() {
        return room1;
    }

    public Item getCompass() {
        return compass;
    }

    //Method that returns the weapon object as the right subclass of what kind of weapon it is.
    public Weapon checkWeaponType(Item whatItem) {
        if (whatItem instanceof Weapon) {
            whatItem = (Weapon) whatItem;
        }
        if (whatItem instanceof MeleeWeapon) {
            return (MeleeWeapon) whatItem;
        }
        else if (whatItem instanceof RangedWeapon) {
            return (RangedWeapon) whatItem;
        }

        return null;
    }

    // SETMETHODS ----------------------------------------------------------------------------------------


}
