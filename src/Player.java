/* CLASS RESPONSIBILITY: Playerklassen er selve spilleren. Den kender sin egen position klassen Map,
og den sporer sit nuværende rum, og kontrollerer sine bevægelser i forhold til Map */

import java.util.ArrayList;

public class Player {
    private String userName;
    private Room currentRoom;
    private Room startingRoom;
    private Room lastTeleportRoom;
    private ArrayList<Item> inventory;
    private ArrayList<Weapon> weaponSlot;  // Weaponslot is an arraylist in case i want to increase the size of the inventory slot.
    private int health;
    private final int maxHealth = 100;
    private final int minHealth = 0;
    private Room roomWish; //stores what room the user wanted to go to last time

    /*Constructor. You can't decide the player's current room from start,
    since the player-class is not directly linked to the mapclass. That's why i set startingRoom as a parameter for later use.*/
    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.startingRoom = startingRoom;
        this.inventory = new ArrayList<>();
        this.weaponSlot = new ArrayList<>();
        this.currentRoom.setVisitedBefore();// sets the current room to visited before, because otherwise it wont work
        this.health = 100;
    }

    // GETMETHODS--------------------------------------------------------------------------------------------------------
    /* Gets the currentRoom for player. the player's currentRoom is initialized in adventureClass, and in order to
    use it, i have to create a get method for it in this class.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String getCurrentRoomName() {
        return currentRoom.getRoomName();
    }

    //gets username for a player.
    public String getUserName() {
        return userName;
    }

    //gets the player's inventory
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    //gets playerHealth bar
    public int getHealth() {
        return this.health;
    }

    public ArrayList<Weapon> getWeaponSlot() {
        return this.weaponSlot;
    }

    public boolean death() {
        if (this.health < 1) {
            return true;
        }
        else {
            return false;
        }
    }
    // SETMETHODS--------------------------------------------------------------------------------------------------------


    //method if method wants to eat item in the inventory. eating an item is only possible if it's in the inventory.
    public void eatItem(Food whatFood) {
        this.health += whatFood.getHealthGain();
        this.inventory.remove(whatFood);
        if (this.health > maxHealth) { // if the health is above 100 it it will become 100(maxHealth).
            this.health = maxHealth;
        }
    }

    //this method accepts a weaponobject since we want to know which weapon it is, so we know how much damage it gives to the player.
    public int takeDamage(Weapon whatWeapon) {
        if (whatWeapon instanceof MeleeWeapon) {
            this.health -= whatWeapon.getDamage();
            return 30; // return the amount of damage taken
        }
        else if (whatWeapon instanceof RangedWeapon) {
            this.health -= 50;
            return 50; // returns the amount of damage taken
        }
        else {
            return 0;
        }
    }

    public void equipWeapon(Weapon whatWeapon) {

        // if weaponslot is not empty removes the current weapon from the slot, and back to inventory.
        if (!weaponSlot.isEmpty()) {
            Weapon currentWeapon = weaponSlot.get(0);

            this.inventory.add(currentWeapon);

            this.weaponSlot.clear();
        }

        // adds the new weapon.
        this.weaponSlot.add(whatWeapon); // adds new weapon to the inventory.
        this.inventory.remove(whatWeapon); // removes the item in the inventory
    }

    //setMethod for changing playername.
    public void setPlayerName(String newName) {
        this.userName = newName;
    }

    /*setMethod for adding new items to the playerItem arraylist and
    removing the item from the currentrooms roomitem arraylist.*/
    public boolean addItem(String input) {
        for (Item item : currentRoom.getRoomItems()) {
            if (item.getName().equalsIgnoreCase(input.toLowerCase())) {
                inventory.add(item);
                currentRoom.getRoomItems().remove(item);
                currentRoom.updateRoomDescription();
                return true;
            }
        }
        return false;
    }

    /*setMethod for removing new items to the roomITem arraylist and
    removing the item from the currentrooms roomitem arraylist.*/ // burde overveje at ændre fra boolean til object som returntype
    public boolean removeItem(String input) {

        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(input.toLowerCase())) {
                inventory.remove(item);
                currentRoom.getRoomItems().add(item);
                currentRoom.updateRoomDescription();
                return true;
            }
        }
        return false;
    }


    /* Keeps track of the player's currentRoom based on userChoice (scanner-string). It makes most sense to do it in
    this class, as it holds all the information needed in relation to the player's currentRoom;*/
    public boolean changeCurrentRoom(String direction) {
        roomWish = null;
        switch (direction) {
            case "go north", "go n", "north", "n" -> {
                roomWish = currentRoom.getNorth();

            }

            case "go south", "go s", "south", "s" -> {
                roomWish = currentRoom.getSouth();
            }

            case "go east", "go e", "east", "e" -> {
                roomWish = currentRoom.getEast();
            }

            case "go west", "west", "w" -> {
                roomWish = currentRoom.getWest();
            }

            default -> {
                roomWish = null;
            }
        }

        if (roomWish != null && hasKey(roomWish)) {
            currentRoom = roomWish;
            return true;
        } else {
            return false;
        }

    }

    //This method returns a boolean it checks if a given room has a doorcode or not, and if so does the player have the right key for the door.
    //It will also return true if there is no code on the door, then the user can just go in.
    public boolean hasKey(Room whatRoom) {
        if (whatRoom.getDoorCode() != null) {
            for (Item item : inventory) {
                if (item instanceof Key) {
                    Key foundKey = (Key) item;
                    if (foundKey.getId().equals(whatRoom.getDoorCode())) {
                        return true;
                    }
                }
            }
        } else if (whatRoom.getDoorCode() == null) {
            return true;
        }
        return false;
    }

    // If player has never teleported before (lastTeleportRoom == null) player will teleport to starting room.
//Otherwise the player will teleport to lastteleport room and the lastteleportroom will be updated to currentroom before teleport.
    public void teleport() {
        if (lastTeleportRoom == null) {
            lastTeleportRoom = currentRoom;
            currentRoom = startingRoom;
        } else {
            Room temporaryRoom = currentRoom;
            currentRoom = lastTeleportRoom;
            lastTeleportRoom = temporaryRoom;
        }
    }

    public boolean attack() { // attack method that turns true if the used weapons are either melee or ranged and if the ranged have the right amount of ammo
        if (weaponSlot.get(0) instanceof RangedWeapon) {
            RangedWeapon rangedWeapon = (RangedWeapon) weaponSlot.get(0);

            if (rangedWeapon.getAmmunition() > 0) {
                rangedWeapon.setAmmunition();
                return true;
            } else {
                return false;
            }
        } else if (weaponSlot.get(0) instanceof MeleeWeapon) {
            return true;
        }
        else if (weaponSlot.isEmpty()) {
            return false;
        }
        return false;
    }


    public Room getRoomWish() {
        return roomWish;
    }
}