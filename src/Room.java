
//Class RESPONSIBILITY: This class creates rooms and defines them.

import java.util.ArrayList;

public class Room {
    private Room north, south, east, west;
    private final String roomName;
    private final String mainDescription; // Static part that never changes.
    private final String doorCode;
    private String extendedDescription; // Dynamic part that changes depending on amount of items.
    private boolean visitedBefore; // dynamic
    private ArrayList<Item> roomItems;
    private ArrayList<Enemy> roomEnemies;


    //Constructor
    public Room(String roomName, String mainDescription, String doorCode) {
        this.roomName = roomName;
        visitedBefore = false;
        this.roomItems = new ArrayList();
        this.roomEnemies = new ArrayList();
        this.mainDescription = mainDescription;
        this.doorCode = doorCode;
        updateRoomDescription(); // jeg bliver nødt til at bruge denne, ellers vil extendedDescription være NULL.
    }

    //SETMETHODS-----------------------------------------------------------------------------------------------------------

//kills the choosen enemy and completely removes it from the room, and make it drop it's items. (makes a copy of the wepaon in roomitemlist.

    public void killEnemy (Enemy whatEnemy) {
        roomItems.add(whatEnemy.getWeapon());
        this.roomEnemies.remove(whatEnemy);

    }
    //Setmethod for choosing the neighbors
    public void setNeighbor(Room north, Room south, Room east, Room west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    //setmethod for changing a room from not-visited to visited
    public void setVisitedBefore() {

        visitedBefore = true;
    }

    //setMethod for adding new items to the roomItem arraylist and updating the extendedDescription after.
    public void addRoomItems(Item item) {
        roomItems.add(item);
        updateRoomDescription();

    }

    public void addRoomEnemies(Enemy enemy) {
        roomEnemies.add(enemy);
        updateRoomDescription();
    }

    /*Method to keep updating the extendedescription, so it is true to the amount of items in a room.
    it also edits grammar, so the last item will be followed by a "." instead of ","*/

    public void updateRoomDescription() {
        if (roomItems.isEmpty()) {
            extendedDescription = "This is " + roomName + ". No items in sight.";
        }
        else {
            extendedDescription = "This is " + roomName + ". Visible items: \n";
            for (int i = 0; i < roomItems.size(); i++) {

                if (i < roomItems.size() - 1) {
                    extendedDescription += i + 1 + ". " + roomItems.get(i).getName() + ": " + roomItems.get(i).getLongName() + ", \n";
                } else {
                    extendedDescription += i + 1 + ". " + roomItems.get(i).getName() + ": " + roomItems.get(i).getLongName() + ".";
                }
            }
        }

        if (roomEnemies.isEmpty()) {
            extendedDescription += "\n No enemies in sight";
        }
        else {
            extendedDescription += "\n Enemies: \n";
            for (int i = 0; i < roomEnemies.size(); i++) {

                if (i < roomEnemies.size() - 1) {
                    extendedDescription += i + 1 + ". " + roomEnemies.get(i).getName() + ": " + roomEnemies.get(i).getDescription() + ", \n";
                } else {
                    extendedDescription += i + 1 + ". " + roomEnemies.get(i).getName() + ": " + roomEnemies.get(i).getDescription() + ".";
                }
            }
        }
    }


    //GETMETHODS-------------------------------------------------------------------------------------------------------

    //getid
    public String getDoorCode() {
        return doorCode;
    }
    //searchFor method that helps searching for an item in the room with itemName.
    public Item findItem(String input) {

        for (int i = 0; i < roomItems.size(); i++) {
            if (roomItems.get(i).getName().equalsIgnoreCase(input)) {
                return roomItems.get(i);
            }
        }
        return null;

    }

    //getmethod for getting room name.
    public String getRoomName() {return roomName;}

    //getmethod for getting roomitems arraylist
    public ArrayList<Item> getRoomItems() {
        return roomItems;
    }

    //getmethod for finding out if a room have been visited or not.
    public boolean getVisitedBefore() {
        return visitedBefore;
    }
    //getmethod for a list of enemies in a room.
    public String getRoomEnemiesList() {
        String enemyList = "";
        if (roomEnemies.isEmpty()) {
            enemyList += "\n No enemies in sight";
        } else {
            enemyList += "\n Enemies: \n";
            for (int i = 0; i < roomEnemies.size(); i++) {

                if (i < roomEnemies.size() - 1) {
                    enemyList += i + 1 + ". " + roomEnemies.get(i).getName() + ": " + roomEnemies.get(i).getDescription() + ", \n";
                } else {
                    enemyList += i + 1 + ". " + roomEnemies.get(i).getName() + ": " + roomEnemies.get(i).getDescription() + ".";
                }
            }
        }
        return enemyList;
    }
    //getMethod for description
    public String getExtendedDescription() {
        return extendedDescription;
    }

    //getmethod for finding a rooms neighbor
    public Room getNorth() {
        return north;
    }

    public Room getSouth() {
        return south;
    }

    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }

    public ArrayList<Enemy> getRoomEnemies () {
        return this.roomEnemies;
    }

    //toString method
    @Override
    public String toString() {
        return "You are now at " + roomName + " - " + mainDescription + " " + getRoomEnemiesList();
    }
}
