package com.bdj.dash.controller;

import com.bdj.dash.model.Location;
import com.bdj.dash.model.Player;
import com.bdj.dash.model.State;
import com.bdj.dash.view.AsciiArt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

// moved methods into game so that we are able to manipulate the player object without having
// all the other classes talk to each other
// Game class is only class that should communicate with other classes in the model.
// created a state class to always be aware of state and not stuck in infinite while loop

// This class contains all the information for the world itself. Information about each location
// and what each location contains from nothing to NPC, Items, Zombie etc.

public class Game {

  AsciiArt titleArt = new AsciiArt();
  Player player = new Player();
  State state;

  HashMap<String,Location> locationMap = new HashMap<>();

  public void playGame() {
    titleArt.title();
    titleArt.instructions();
    startGame();

    String userName = player.playerName();
    if (userName.equals("quit")) {
      System.out.println("I guess you didn't want to play.");
      return;
    }

    while (!state.isTerminal()) {
      statusUpdate();
      inputCommand();
    }
  }

  // This section is displayed when you run the game.

  public void startGame() {
    Scanner input = new Scanner(System.in);
    System.out.println("Would you like to begin the game? y/n");
    String command = input.nextLine().toLowerCase().trim();
    if (command.equals("y")) {
      setState(State.PLAY);
      createMap();
      player.setLocation("abandoned house");
      System.out.println("The game has begun!");
    } else if (command.equals("n")) {
      System.out.println("Maybe next time!");
      setState(State.LOSE);
    } else {
      System.out.println("Invalid command. Please enter y/n");
    }
  }

  // creating the map
  public void createMap(){

    // The information below contains the structure of the world itself. Name of location, items,
    // directions the player can go, and then description of the area.

    locationMap.put("abandoned house", new Location("abandoned house", new
        String[]{"crossbow"}, "crossroads", null,null,null,
        "Oops....looks like you got sent to the future. What are you going to do?\n"
            + "This future world looks like it's in shambles. What happened? Is there any place "
            + "safe around here,\nor any way back to the past? You see a sign reading \"Safe Haven\""
            + ". \nYou also see a warning sign in a corner that says, \"Proceed with caution."
            + " There are zombies everywhere.\"\n"));

    locationMap.put("crossroads", new Location("crossroads", new String[]{}, null,
        "abandoned house",null,"grocery store", "It doesn't look too safe "
        + "out here. You can see someone close to you, but don't know who that is."));

    locationMap.put("grocery store", new Location("grocery store", new String[]{},
        "abandoned fairgrounds", null,"crossroads",null, "The food"
        + " here doesn't look edible"));

    locationMap.put("abandoned fairgrounds", new Location("abandoned fairgrounds",
        new String[]{"spiked bat"}, "street pharmaceutical", "grocery store",null,
        "invasion woods", "This place probably used to be up and popping."));

    locationMap.put("street pharmaceutical", new Location("street pharmaceutical",
        new String[]{"duct tape"}, null, "abandoned fairgrounds","radioactive club",
        null, "Oh look! You see a man in a trench coat oh wait its just the"
        + " Street Pharmaceuticals."));

    locationMap.put("radioactive club", new Location("radioactive club", new String[]{},
        null, null,null,"street pharmaceutical", "What kind of club is"
        + " this? Looks like you have encountered 2 zombies. You can't kill them both.\n"
        + " YOU DIED!"));

    locationMap.put("invasion woods", new Location("invasion woods", new String[]{},
        "rusty gun store", null,"abandoned fairgrounds",null, "Oh, you"
        + " found yourself in the middle of the woods. You have watched way too many horror films"
        + " to know what happens. Push through as fast as you can!\n Wait, what is that? Someone is"
        + " watching you from the trees above."));

    locationMap.put("rusty gun store", new Location("rusty gun store",
        new String[]{"gun"}, "shanty docks", "invasion woods",null,null,
        "Well, this looks like a useful place. Let's see if they have anything. You "
            + "definitely need a gun moving forward."));

    locationMap.put("shanty docks", new Location("shanty docks", new String[]{"raft"},
        null, "rusty gun store","toxic river",null, "This is exciting."
        + " You see a bunch of rotten rafts. Should you use a raft to cross the toxic river?\n"));

    locationMap.put("toxic river", new Location("toxic river", new String[]{},
        "not deadly depths", null,"boss room","zombie motel caves",
        "Oh man! You must be really thirsty from the long trip. But wait, this rancid"
            + " water cannot be trusted. Think wisely before you proceed!"));

    locationMap.put("zombie motel caves", new Location("zombie motel caves",
        new String[]{}, "invasion woods", "secret bunker",null,null,
        "Who do you think you encounter here? Duh - zombies.\n"));

    locationMap.put("secret bunker", new Location("secret bunker", new String[]{},
        "zombie motel caves", "portal",null,null, "Well, that looks"
        + " like a safe place to hide. What is that piece of paper sticking out from beneath the "
        + "rocks? It is a note that reads, \"The future of safe haven lies in your hands. Take this"
        + " note to the Safe Haven to save the people and restore peace forever.\"\n"));

    locationMap.put("portal", new Location("portal", new String[]{}, "secret "
        + "bunker", null,null,null, "Oh look, fled to the portal instead of"
        + ". Saving everyone else. Congratulations you have gone back to the past. I wish you the"
        + " best with door-dashing. Be aware of the abandoned places in the future!"));

    locationMap.put("not deadly depths", new Location("not deadly depths",
        new String[]{}, null, null,null,null, "I guess those depths "
        + "were deadly after all. You Died!"));

    locationMap.put("boss room", new Location("boss room", new String[]{},
        "safe haven", null,null,null, "Phew! After all that crazy "
        + "adventure, you made it to the Boss Room. The good thing is, you see safe haven directly"
        + " to the north. However, you have to go through the Super Zombie Baby before you make it "
        + "through to the other side. Dig through your inventory to find the most destructive"
        + " weapon!"));

    locationMap.put("safe haven", new Location("safe haven", new String[]{}, null,
        null,null,null, "Congratulations! You have successfully overcome "
        + "all the obstacles and reached Safe Haven. If only you could find a way back to the past."
        + " The people appreciate your selfless help. You can now have all the happiness you ever"
        + " imagined.\n"));
  }

  // This allows users to type commands.
  public void inputCommand() {
    Scanner input = new Scanner(System.in);
    System.out.println("enterCommand: ");
    String command = input.nextLine().toLowerCase().trim();
    processCommand(command);
  }

  public void processCommand(String commandString) {
   String[] command = commandString.split(" ");
    if (command[0].equals("help")) {
      System.out.println(AsciiArt.HELP_COMMANDS);
    } else if (command[0].equals("quit")) {
      System.out.println("See you next time");
      setState(State.LOSE);
    } else if(command[0].equals("go")){
      String currentLocation = player.getLocation();
      movePlayer(command, currentLocation);
      if (currentLocation.equals( player.getLocation())){
        System.out.println("You can't go this way, there is nothing there.");
      }
    } else if(command[0].equals("new") && command[1].equals("game")){
      startGame();
    }else if (command[0].equals("get") ){
      addToInventory();
    } else {
      System.out.println("Invalid command. Please enter valid game command such as: \n"
          + "go <north, south, east, west>, help, quit");
    }

  }

  // this handles how the user moves around the world and keeps track of the player.

  private void movePlayer(String[] command, String currentLocation) {
    String newLocation;
    if(command[1].equals("north")){
      // if null keep location, if not change location
      newLocation = locationMap.get(currentLocation).getNorth() == null ? currentLocation :
          locationMap.get(currentLocation).getNorth();
    }else if(command[1].equals("south")){
      newLocation = locationMap.get(currentLocation).getSouth() == null ? currentLocation :
          locationMap.get(currentLocation).getSouth();
    }else if(command[1].equals("west")){
      newLocation = locationMap.get(currentLocation).getWest() == null ? currentLocation :
          locationMap.get(currentLocation).getWest();
    }else if(command[1].equals("east")){
      newLocation = locationMap.get(currentLocation).getEast() == null ? currentLocation :
          locationMap.get(currentLocation).getEast();
    } else{
      newLocation = currentLocation;
      System.out.println("not a valid direction, try another!");
    }
    player.setLocation(newLocation);
  }


  // This handles how the user manipulates the inventory from picking up items to using items.
  public void addToInventory(){
    ArrayList<String> newItem = new ArrayList<>();

    if (!locationMap.get(player.getLocation()).getItems().equals(null)){
      String[] item = locationMap.get(player.getLocation()).getItems();
      newItem.add(item[0]);
      player.setInventory(newItem);
      player.getInventory();
      System.out.println(newItem.get(0) + " has been added to your inventory");
    }else{
      System.out.println("There is nothing to get");
    }
  }

  // This handles the information about the players current health

  public void statusUpdate(){
    int pHp = player.getHealth();
    System.out.println(player.getName() + " your current health is = " + pHp);
    System.out.println("You are currently at: " + player.getLocation());
    System.out.println(locationMap.get(player.getLocation()).getDescription());
    showPossibleDirections();
    showItems();
    showInventory();
  }


  // this prints the players current inventory
  public void showInventory(){
    if (player.getInventory() == null){
      System.out.println("your inventory is empty");
    } else{
      System.out.println("Your inventory currently has: " + player.getInventory());
    }
  }

  // this shows the items in the zone, not the inventory.
  public void showItems(){
    if (locationMap.get(player.getLocation()).getItems().length > 0){
      String[] item = locationMap.get(player.getLocation()).getItems();
      System.out.println("You see: " + Arrays.toString(item));
    }else{
      System.out.println("You see no items in this area.");
    }
  }


  // This displays the directions that the player has currently available to them.
  public void showPossibleDirections(){

    String north = locationMap.get(player.getLocation()).getNorth();
    if(north == null){
      System.out.println("North: Nothing this way");
    } else{
      System.out.println("North: " + north);
    }

    String south = locationMap.get(player.getLocation()).getSouth();
    if(south == null){
      System.out.println("South: Nothing this way");
//      System.out.println("");
    } else{
      System.out.println("South: " + south);
    }

    String west = locationMap.get(player.getLocation()).getWest();
    if(west == null){
      System.out.println("West:  Nothing this way");
    } else{
      System.out.println("West: " + west);
    }

    String east = locationMap.get(player.getLocation()).getEast();
    if(east == null){
      System.out.println("East:  Nothing this way");
    } else{
      System.out.println("East: " + east);
    }

  }
  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public HashMap<String, Location> getLocationMap() {
    return locationMap;
  }

  public void setLocationMap(HashMap<String, Location> locationMap) {
    this.locationMap = locationMap;
  }
}
