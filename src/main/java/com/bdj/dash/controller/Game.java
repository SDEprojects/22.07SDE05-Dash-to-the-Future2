package com.bdj.dash.controller;

import com.bdj.dash.model.Location;
import com.bdj.dash.model.Player;
import com.bdj.dash.model.State;
import com.bdj.dash.view.Intro;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import com.bdj.dash.model.Reader;

// moved methods into game so that we are able to manipulate the player object without having
// all the other classes talk to each other
// Game class is only class that should communicate with other classes in the model.
// created a state class to always be aware of state and not stuck in infinite while loop

// This class contains all the information for the world itself. Information about each location
// and what each location contains from nothing to NPC, Items, Zombie etc.

public class Game {

  Intro titleArt = new Intro();
  Player player = new Player();

  Reader reader = new Reader();
  State state;

  HashMap<JsonNode,Location> locationMap = new HashMap<>();

  ArrayList<String> newItem = new ArrayList<>();

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
    JsonNode location0 = reader.getMoveLocation().get(0);
    // abandoned house
    locationMap.put(location0.get("name"), new Location(location0.get("name"),
        location0.get("items"), location0.get("north"), location0.get("south"),location0.get("east"),location0.get("west"), location0.get("description")));

    JsonNode location1 = reader.getMoveLocation().get(1);
    // crossroads
    locationMap.put(location1.get("name"), new Location(location1.get("name"),
        location1.get("items"), location1.get("north"), location1.get("south"),location1.get("east"),location1.get("west"), location1.get("description")));

    JsonNode location2 = reader.getMoveLocation().get(2);
    //grocery store
    locationMap.put(location2.get("name"), new Location(location2.get("name"),
        location2.get("items"), location2.get("north"), location2.get("south"),location2.get("east"),location2.get("west"), location2.get("description")));

    JsonNode location3 = reader.getMoveLocation().get(3);
    //abandoned fairgrounds
    locationMap.put(location3.get("name"), new Location(location3.get("name"),
        location3.get("items"), location3.get("north"), location3.get("south"),location3.get("east"),location3.get("west"), location3.get("description")));

    JsonNode location4 = reader.getMoveLocation().get(4);
    // street pharmaceuticals
    locationMap.put(location4.get("name"), new Location(location4.get("name"),
        location4.get("items"), location4.get("north"), location4.get("south"),location4.get("east"),location4.get("west"), location4.get("description")));

    JsonNode location5 = reader.getMoveLocation().get(5);
    //radioactive club
    locationMap.put(location5.get("name"), new Location(location5.get("name"),
        location5.get("items"), location5.get("north"), location5.get("south"),location5.get("east"),location5.get("west"), location5.get("description")));

    JsonNode location6 = reader.getMoveLocation().get(6);
    // invasion woods
    locationMap.put(location6.get("name"), new Location(location6.get("name"),
        location6.get("items"), location6.get("north"), location6.get("south"),location6.get("east"),location6.get("west"), location6.get("description")));

    JsonNode location7 = reader.getMoveLocation().get(7);
    //rusty gun store
    locationMap.put(location7.get("name"), new Location(location7.get("name"),
        location7.get("items"), location7.get("north"), location7.get("south"),location7.get("east"),location7.get("west"), location7.get("description")));

    JsonNode location8 = reader.getMoveLocation().get(8);
    //shanty docks
    locationMap.put(location8.get("name"), new Location(location8.get("name"),
        location8.get("items"), location8.get("north"), location8.get("south"),location8.get("east"),location8.get("west"), location8.get("description")));

    JsonNode location9 = reader.getMoveLocation().get(9);
    // toxic river
    locationMap.put(location9.get("name"), new Location(location9.get("name"),
        location9.get("items"), location9.get("north"), location9.get("south"),location9.get("east"),location9.get("west"), location9.get("description")));

    JsonNode location10 = reader.getMoveLocation().get(10);
    //zombie motel caves
    locationMap.put(location10.get("name"), new Location(location10.get("name"),
        location10.get("items"), location10.get("north"), location10.get("south"),location10.get("east"),location10.get("west"), location10.get("description")));;

    JsonNode location11 = reader.getMoveLocation().get(11);
    //secret bunker
    locationMap.put(location11.get("name"), new Location(location11.get("name"),
        location11.get("items"), location11.get("north"), location11.get("south"),location11.get("east"),location11.get("west"), location11.get("description")));

    JsonNode location12 = reader.getMoveLocation().get(12);
    // portal
    locationMap.put(location12.get("name"), new Location(location12.get("name"),
        location12.get("items"), location12.get("north"), location12.get("south"),location12.get("east"),location12.get("west"), location12.get("description")));

    JsonNode location13 = reader.getMoveLocation().get(13);
    //not deadly depths
    locationMap.put(location13.get("name"), new Location(location13.get("name"),
        location13.get("items"), location13.get("north"), location13.get("south"),location13.get("east"),location13.get("west"), location13.get("description")));

    JsonNode location14 = reader.getMoveLocation().get(14);
    //boss room
    locationMap.put(location14.get("name"), new Location(location14.get("name"),
        location14.get("items"), location14.get("north"), location14.get("south"),location14.get("east"),location14.get("west"), location14.get("description")));

    JsonNode location15 = reader.getMoveLocation().get(15);
    //safe haven
    locationMap.put(location15.get("name"), new Location(location15.get("name"),
        location15.get("items"), location15.get("north"), location15.get("south"),location15.get("east"),location15.get("west"), location15.get("description")));;
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
      System.out.println(Intro.HELP_COMMANDS);
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
      removeItemFromArea();
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

    if (!locationMap.get(player.getLocation()).getItems().equals(null)){
      String[] item = locationMap.get(player.getLocation()).getItems();
      newItem.add(item[0]);
      player.setInventory(newItem);
      // reverse so that newest item added is printed to user
      Collections.reverse(newItem);
      System.out.println(newItem.get(0) + " has been added to your inventory");
    }else{
      System.out.println("There is nothing to get");
    }
  }

  public void removeItemFromArea(){

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
    String[] item = locationMap.get(player.getLocation()).getItems();

    if (locationMap.get(player.getLocation()).getItems().length > 0 && !newItem.contains(item[0])){
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
