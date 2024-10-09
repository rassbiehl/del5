/* CLASS RESPONSIBILITY: this class handles the user's input and will inform the user about the game (output)*/

import java.util.Scanner;
import java.util.ArrayList;


public class UI {
    private Adventure adventure;
    private Scanner scanner;


    public UI() {
        adventure = new Adventure();
        scanner = new Scanner(System.in);
    }


    public void startGame() {
        gameIntro(); // launches the intro.
        boolean gameOver = false;
        while (!gameOver) { //
            System.out.println("Now, what will you do?");
            String input = scanner.nextLine().trim().toLowerCase();
            gameOver = handleInput(input); /*i put the method handleinput in gameover, and if it returns true ("exit" is pressed)
          the game will end.*/
        }

    }

    public boolean handleInput(String input) {
        String[] inputParts = input.split(" ", 2); //smart method from the internet that splits an array into two parts after the spacing.
        //It lets me take the input after "attack" and use it to point towards a possible enemy in the room that needs to be attacked.
        String command = inputParts[0].toLowerCase();
        switch (command) {
            case "help" -> userGuide();
            case "look" -> lookAround();
            case "move" -> move();
            case "xyzzy" -> teleport();
            case "inventory" -> showInventory();
            case "take" -> takeItem();
            case "drop" -> dropItem();
            case "find" -> findItem();
            case "health" -> seeHealth();
            case "eat" -> eatItem();
            case "attack" -> {
                if (inputParts.length > 1) {
                    attackSpecific(inputParts[1]); // attacks specific enemy typed by the user.
                } else {
                    attackClosest(); // attacks the closest enemy in the room (the first on the roomEnemy arraylist)
                }
            }
            case "equip" -> equipWeapon();
            //  case "attack" -> attack();
            case "exit" -> {
                System.out.println("You have now quit the game.");
                return true;
            }
            default -> System.out.println("Unvalid input. Try again.");
        }
        return false;
    }


    //prints the userguide.
    public void userGuide() {
        System.out.println("\"help\" - get instructions and an overview of possible commands.");
        System.out.println("\"look\" - get a description of the room you are currently in.");
        System.out.println("\"move\" - with your compass, move to a new room in a path of your choice.");
        System.out.println("\"xyzzy\" - teleports you to the lobby, or if used before, teleports you to the last room, where you used this command.");
        System.out.println("\"inventory\" - provides a list of all the items in your inventory.");
        System.out.println("\"take\" - pick up an item and add it to your inventory.");
        System.out.println("\"drop\" - drop an item and remove it from your inventory.");
        System.out.println("\"find\" - find out if a given item is in your current room.");
        System.out.println("\"health\" - get a health status.");
        System.out.println("\"eat\" - eat an item of your choice to gain health.");
        System.out.println("\"equip\" - equip an item from your inventory.");
        System.out.println("\"attack\" - attack using your equipped weapon.");
        System.out.println("\"exit\" - quit the game.");
    }

    // everytime this command is used the player will get  the currentrooms extended description.
    public void lookAround() {
        System.out.println("*Looks around* " + adventure.getPlayerCurrentRoom().getExtendedDescription());
    }


    public void move() {
        if (adventure.hasCompass()) {
            System.out.println("In which direction do you want to go? (N, S, E, W or C to cancel)");
            String direction = scanner.nextLine().trim().toLowerCase();
            boolean roomChange = adventure.changePlayerRoom(direction);
            if (!roomChange && adventure.getPlayer().getRoomWish() == null) { //room hasnt been changed, because destination is null.
                System.out.println("You can't go that way! You must choose another path.");
            } else if (!roomChange && adventure.getPlayer().getRoomWish() != null) { //Room hasnt been changed, but the destination is not null.
                System.out.println("You do not have a key for this door. You must choose another path.");
            } else if (!adventure.getPlayerCurrentRoom().getVisitedBefore()) {
                System.out.println(adventure.getPlayerCurrentRoom());
                adventure.setPlayerCurrentRoomVisitedBefore(); //Changes visitedBefore to true for every new room.
            }
        } else {
            System.out.println("You must need a compass, if you want to continue to another room. Use your eyes.");
        }
    }

    //This method makes the player teleport to the last location
    public void teleport() {
        adventure.getPlayer().teleport();
        System.out.println("You have now teleported to " + adventure.getPlayer().getCurrentRoomName());
    }

    //this method prints out the player's inventory and it's sorted in number-rows. if no items, it will inform the player.
    public void showInventory() {
        if (!adventure.getPlayer().getInventory().isEmpty()) {
            System.out.println("Inventory:");
            for (int i = 0; i < adventure.getPlayer().getInventory().size(); i++) {
                System.out.println(i + 1 + ". " + adventure.getPlayer().getInventory().get(i));

            }
        } else {
            System.out.println("As of right now, you do not have any items in your inventory.");
        }

    }

    /*This method let's the player ADD an item to the inventory, and this automatically removes
    the item from the currentrooms roomitems-arraylist.*/
    public void takeItem() {
        System.out.println("Which item do you want to take?");
        String input = scanner.nextLine();
        boolean inventoryChange = adventure.getPlayer().addItem(input);
        if (!inventoryChange) {
            System.out.println("\"" + input + "\"cannot be found in the current room.");
        } else {
            System.out.println("You have now added " + adventure.getPlayer().getInventory().get(adventure.getPlayer().getInventory().size() - 1).getName() + " to your inventory."); //prints out last item in inventory.
        }
    }

    /*This method let's the player REMOVE an item to the inventory, and this automatically adds
the item to the currentrooms roomitems-arraylist.*/
    public void dropItem() {
        if (!adventure.getPlayerInventory().isEmpty()) { // making sure that inventory is not empty, if you want to drop something.
            System.out.println("Which item do you want to drop?");
            String input = scanner.nextLine();
            boolean inventoryChange = adventure.getPlayer().removeItem(input);
            if (!inventoryChange) {
                System.out.println("\"" + input + "\" cannot be found in your inventory");
            } else {
                System.out.println("You have now removed " + input + " from your inventory");
            }
        } else {
            System.out.println("Your inventory is empty!");
        }
    }

    //method to see if a given item is within the currentRoom. to be honest it is a pretty useless command for the player.
    public void findItem() {
        System.out.println("What are you looking for?");
        String input = scanner.nextLine().trim();
        Item foundItem = adventure.getPlayer().getCurrentRoom().findItem(input);

        if (foundItem == null) {
            System.out.println("There is no such item in the room.");
        } else {
            System.out.println(foundItem.getName() + " was found in the current room!");
        }

    }

    public void seeHealth() {
        int health = adventure.getPlayer().getHealth();
        String status;
        if (health > 75) {
            status = " You are in good shape! 😊";
        } else if (health <= 75 && health > 50) {
            status = " You are doing just fine. 🙂";
        } else if (health <= 50 && health > 25) {
            status = " You should try gain back some health. ⚠️";
        } else if (health <= 25 && health > 10) {
            status = " Your health is very low. It is now critical! 🆘";
        } else {
            status = " You might be dead any second now. 💀";
        }

        System.out.println("Health: " + health + status);
    }

    public void eatItem() {
        System.out.println("What do you want to eat?");
        String input = scanner.nextLine().trim();
        Item foundItem = null;
        for (Item item : adventure.getPlayer().getInventory()) { //finds all the objects with the item type in inventory
            if (input.equalsIgnoreCase(item.getName())) { // is the item equal to the userinput, founditem will point to that item.
                foundItem = item;
                break;
            }
        }

        if (foundItem != null && foundItem instanceof Food) {
            Food foodItem = (Food) foundItem;
            System.out.println("*Eating " + foodItem.getName() + "*");
            adventure.getPlayer().eatItem(foodItem);


        } else if (foundItem != null && !(foundItem instanceof Food)) {
            System.out.println("You cannot eat " + foundItem.getName());
        } else {
            System.out.println("Unvalid input. Try again.");
        }
    }

    public void equipWeapon() {
        ArrayList<Weapon> foundWeapons = new ArrayList(); // an arraylist of all the found weapons in the inventory
        for (int i = 0; i < adventure.getPlayerInventory().size(); i++) {
            Item item = adventure.getPlayerInventory().get(i);

            if (item instanceof Weapon) { // every instance of weapon of all the items found in the inventory.
                Weapon weapon = adventure.getMap().checkWeaponType(item); //checks which type of weapon the weapon is and changes its value to that.
                if (weapon != null) {
                    foundWeapons.add(weapon); // adds the found weapon to an arraylist.
                }
            }
        }

        if (!foundWeapons.isEmpty()) { // if foundweapons arraylist is not emtpy, it should display the weapons.
            System.out.println("You have the following weapons in your inventory:");
            for (int i = 0; i < foundWeapons.size(); i++) {
                System.out.println((i + 1) + ". " + foundWeapons.get(i).getName());
            }

            System.out.println("What weapons in your inventory do you want to equip?");
            String input = scanner.nextLine();

            boolean weaponEquipped = false; // checks if itemslot was changed after the "equip" command.

            for (Weapon weapon : foundWeapons) {
                if (weapon.getName().equalsIgnoreCase(input)) {
                    weapon = adventure.getMap().checkWeaponType(weapon);
                    adventure.getPlayer().equipWeapon(weapon);
                    System.out.println("You just equipped your " + weapon.getName());
                    weaponEquipped = true;
                    break;
                }
            }

            if (!weaponEquipped) {
                System.out.println("You do not have any weapons that match your criteria.");
            }
        } else {
            System.out.println("You do not have any weapons in your inventory.");
        }
    }

    public void attackSpecific(String userInput) {
        if (!adventure.getPlayer().getWeaponSlot().isEmpty()) {


            String input = userInput;
            Enemy target = null;

            for (Enemy enemy : adventure.getPlayerCurrentRoom().getRoomEnemies()) {
                if (enemy.getName().equalsIgnoreCase(input)) {
                    target = enemy;
                }
            }
            if (target == null) {
                System.out.println("There are no enemies that match your criteria.");
            } else {
                boolean playerAttack = adventure.getPlayer().attack();
                Weapon weapon = adventure.getMap().checkWeaponType(adventure.getPlayer().getWeaponSlot().get(0));
                if (playerAttack && weapon instanceof RangedWeapon) {
                    System.out.println("Bang bang bang!");
                    RangedWeapon rangedWeapon = (RangedWeapon) weapon;
                    int damageDealt = target.takeDamage(rangedWeapon);
                    boolean isEnemyDeath = target.death();
                    if (!isEnemyDeath) {
                        System.out.println("You just gave " + damageDealt + " damage to " + target.getName());
                    } else if (isEnemyDeath) {
                        adventure.getPlayerCurrentRoom().killEnemy(target);
                        System.out.println("You just killed " + target.getName() + ". " + target.getWeapon().getName() + " has now been dropped");
                    }
                } else if (!playerAttack && weapon instanceof RangedWeapon) {
                    RangedWeapon rangedWeapon = (RangedWeapon) weapon;
                    if (rangedWeapon.getAmmunition() < 1) {
                        System.out.println("You do not have any more ammunition!");
                    }
                } else if (playerAttack && weapon instanceof MeleeWeapon) {
                    System.out.println("Slam slam slam!");
                    MeleeWeapon meleeWeapon = (MeleeWeapon) weapon;
                    int damageDealt = target.takeDamage(meleeWeapon);
                    boolean isEnemyDeath = target.death();
                    if (!isEnemyDeath) {
                        System.out.println("You just gave " + damageDealt + " damage to " + target.getName() + " now the enemy has " + target.getHealth() + " healthpoints left.");
                    } else if (isEnemyDeath) {
                        adventure.getPlayerCurrentRoom().killEnemy(target);
                        System.out.println("You just killed " + target.getName() + ". " + target.getWeapon().getName() + " has now been dropped");
                    }
                } else {
                    System.out.println("You do not have a weapon equipped.");
                }

                if (adventure.getPlayerCurrentRoom().getRoomEnemies().contains(target)) {
                    int damageDealtToPLayer = adventure.getPlayer().takeDamage(target.getWeapon());
                    System.out.println("You just lost " + damageDealtToPLayer + " damage.");


                }
            }
        } else {
            System.out.println("You do not have any weapons in your item slot.");
        }
    }

    public void attackClosest() {
        if (!adventure.getPlayer().getWeaponSlot().isEmpty()) {
            Weapon weapon = adventure.getMap().checkWeaponType(adventure.getPlayer().getWeaponSlot().get(0));
            Enemy target = null;

            for (Enemy enemy : adventure.getPlayerCurrentRoom().getRoomEnemies()) {
                target = enemy;
                break;
            }

            if (target == null) { // this works if there are no enemies and the player just attacks out in the air for nothing.
                boolean playerAttack = adventure.getPlayer().attack();
                if (weapon instanceof RangedWeapon && playerAttack) {
                    System.out.println("Bang! *No enemy*");
                } else if (weapon instanceof RangedWeapon && !playerAttack) {
                    System.out.println("You do not have more ammunition");
                } else if (weapon instanceof MeleeWeapon && playerAttack) {
                    System.out.println("Slam! *No Enemy*");
                }

            }

            else if (!(target == null)) {
                boolean playerAttack = adventure.getPlayer().attack();
                if (playerAttack && weapon instanceof RangedWeapon) {
                    System.out.println("Bang!");
                    RangedWeapon rangedWeapon = (RangedWeapon) weapon;
                    int damageDealt = target.takeDamage(rangedWeapon);
                    boolean isEnemyDeath = target.death();
                    if (!isEnemyDeath) {
                        System.out.println("You just gave " + damageDealt + " damage to " + target.getName());
                    } else if (isEnemyDeath) {
                        adventure.getPlayerCurrentRoom().killEnemy(target);
                        System.out.println("You just killed " + target.getName() + ". " + target.getWeapon().getName() + " has now been dropped");
                    }
                } else if (!playerAttack && weapon instanceof RangedWeapon) {
                    RangedWeapon rangedWeapon = (RangedWeapon) weapon;
                    if (rangedWeapon.getAmmunition() < 1) {
                        System.out.println("You do not have any more ammunition!");
                    }
                } else if (playerAttack && weapon instanceof MeleeWeapon) {
                    System.out.println("Slam!");
                    MeleeWeapon meleeWeapon = (MeleeWeapon) weapon;
                    int damageDealt = target.takeDamage(meleeWeapon);
                    boolean isEnemyDeath = target.death();
                    if (!isEnemyDeath) {
                        System.out.println("You just gave " + damageDealt + " damage to " + target.getName() + " now the enemy has " + target.getHealth() + " healthpoints left.");
                    } else if (isEnemyDeath) {
                        adventure.getPlayerCurrentRoom().killEnemy(target);
                        System.out.println("You just killed " + target.getName() + ". " + target.getWeapon().getName() + " has now been dropped");
                    }
                }
                if (adventure.getPlayerCurrentRoom().getRoomEnemies().contains(target)) { // by this, i mean if the roomenemy arraylist still contains the enemy, it means that the enemy is not dead.
                    int damageDealtToPLayer = adventure.getPlayer().takeDamage(target.getWeapon()); // takeDamage returns how much damage the weapon gave
                    Weapon enemyWeapon = adventure.getMap().checkWeaponType(target.getWeapon());

                    if (enemyWeapon instanceof MeleeWeapon) {
                        System.out.println("Slam!");
                    }
                    else if (enemyWeapon instanceof RangedWeapon) {
                        System.out.println("Bang!");
                        RangedWeapon rangedWeapon = (RangedWeapon) enemyWeapon;
                        rangedWeapon.setAmmunition();
                    }
                    System.out.println("You just lost " + damageDealtToPLayer + " damage.");
                }
            }

        } else {
            System.out.println("You do not have any weapons equipped.");
        }
    }

    //gives the player an introduction. is used at the start of the game.
    public void gameIntro() {
        System.out.println("Choose your username:");
        String newUserName = scanner.nextLine();
        adventure.setPlayerName(newUserName);
        System.out.println("Welcome " + adventure.getPlayerName() + ".");
        System.out.println("You are now at the Starting Point. \n");
        System.out.println("Here is a guide, to help you carry through this game: \n");
        userGuide();
    }

}