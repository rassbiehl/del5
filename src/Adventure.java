/* CLASS RESPONSIBILITY: this class will run the game itself. it manages the player and the world map.
whenever interface has read the user's input, it is the Adventure-class that will handle this information.
It works as the controller.
 */

import java.util.ArrayList;

public class Adventure {
    private Map map;
    private Player player1;

    //Constructor that by-standard sets the player's current Room in the start to the StartingRoom (room1). It also creates a new map.
    public Adventure() {
        map = new Map();
        player1 = new Player(map.getStartingRoom());
    }

    // GETMETHODS--------------------------------------------------------------------------------------------------------
    //gets the current room for player1
    public Room getPlayerCurrentRoom() {
        return player1.getCurrentRoom();
    }

    // gets the name for player1.
    public String getPlayerName() {
        return player1.getUserName();
    }

    //gets the player's inventory
    public ArrayList<Item> getPlayerInventory() {
        return player1.getInventory();
    }

    //gets the playerobject
    public Player getPlayer() {
        return player1;
    }

    //gets the map of the game
    public Map getMap () {
        return this.map;
    }
    //finds out if the player has a compass equipped in the inventory or not.
    public boolean hasCompass() {
        return player1.getInventory().contains(map.getCompass()); //dette er en boolean værdi.
    }

    // SETMETHODS--------------------------------------------------------------------------------------------------------
    public boolean changePlayerRoom(String direction) {
        return player1.changeCurrentRoom(direction);

    }

    //setmethod for changing players currentroom visitedbefore to true.
    public void setPlayerCurrentRoomVisitedBefore() {
        player1.getCurrentRoom().setVisitedBefore();
    }

    public void setPlayerName(String newName) {
        player1.setPlayerName(newName);
    }

}