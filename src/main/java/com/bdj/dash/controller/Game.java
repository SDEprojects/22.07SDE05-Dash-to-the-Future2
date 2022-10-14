package com.bdj.dash.controller;

import com.bdj.dash.model.Location;
import com.bdj.dash.model.Player;
import com.bdj.dash.model.State;
import com.bdj.dash.view.AsciiArt;
import java.util.HashMap;
import java.util.Scanner;

// moved methods into game so that we are able to manipulate the player object without having
// all the other classes talk to each other
// Game class is only class that should communicate with other classes in the model.
// created a state class to always be aware of state and not stuck in infinite while loop

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
    locationMap.put("abandoned house", new Location("abandoned house", new String[]{"crossbow"}, "crossroads", null,null,null, "descript"));
    locationMap.put("crossroads", new Location("crossroads", new String[]{}, null, "abandoned house",null,"grocery store", "descrip"));
  }

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
    } else {
      System.out.println("Invalid command. Please enter valid game command!");
    }

  }

  private void movePlayer(String[] command, String currentLocation) {
    String newLocation;
    if(command[1].equals("north")){
      // if null keep location, if not change location
      newLocation = locationMap.get(currentLocation).getNorth() == null ? currentLocation :  locationMap.get(currentLocation).getNorth();
    }else if(command[1].equals("south")){
      newLocation = locationMap.get(currentLocation).getSouth() == null ? currentLocation :  locationMap.get(currentLocation).getSouth();
    }else if(command[1].equals("west")){
      newLocation = locationMap.get(currentLocation).getWest() == null ? currentLocation :  locationMap.get(currentLocation).getWest();
    }else if(command[1].equals("east")){
      newLocation = locationMap.get(currentLocation).getEast() == null ? currentLocation :  locationMap.get(currentLocation).getEast();
    } else{
      newLocation = currentLocation;
      System.out.println("not a valid direction, try again!");
    }
    player.setLocation(newLocation);
  }

  public void statusUpdate(){
    int pHp = player.getHealth();
    System.out.println(player.getName() + " your current health is = " + pHp);
    System.out.println("You are currently at: " + player.getLocation());
    System.out.println("your inventory currently has: " + player.getInventory());
  }
  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}
