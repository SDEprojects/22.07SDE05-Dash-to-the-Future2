package com.bdj.dash.controller;

import com.bdj.dash.model.Location;
import com.bdj.dash.model.Player;
import com.bdj.dash.model.State;
import com.bdj.dash.model.Zombie;
import com.bdj.dash.view.Intro;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

// moved methods into game so that we are able to manipulate the player object without having
// all the other classes talk to each other
// Game class is only class that should communicate with other classes in the model.
// created a state class to always be aware of state and not stuck in infinite while loop

// This class contains all the information for the world itself. Information about each location
// and what each location contains from nothing to NPC, Items, Zombie etc.

public class Game {

  Intro titleArt = new Intro();
  Player player = new Player();
  int playerHealth = player.getHealth();

  Zombie zombie1 = new Zombie();
  Zombie zombie2 = new Zombie();
  Zombie zombie3 = new Zombie();
  Zombie zombie4 = new Zombie();
  Zombie bossZombie = new Zombie();

  State state;

  Map<String, Location> gameMap;
  ArrayList<String> newItem = new ArrayList<>();


  public void playGame() {
    titleArt.title();
    titleArt.instructions();
    startGame();

    while (!state.isTerminal()) {
      statusUpdate();
      inputCommand();
      winOrLose();
    }

  }

  // This section is displayed when you run the game.

  public void startGame() {
    Scanner input = new Scanner(System.in);
    System.out.println("Would you like to begin the game? y/n");
    String command = input.nextLine().toLowerCase().trim();
    if (command.equals("y")) {
      setState(State.PLAY);
      playerName();
      createMap();
      player.setLocation(gameMap.get("abandoned house").getLocationName());
      zombie1.setLocation(gameMap.get("grocery store").getLocationName());
      zombie2.setLocation(gameMap.get("zombie motel caves").getLocationName());
      zombie3.setLocation(gameMap.get("radioactive club").getLocationName());
      zombie4.setLocation(gameMap.get("radioactive club").getLocationName());
      bossZombie.setLocation(gameMap.get("boss room").getLocationName());

      System.out.println("The game has begun!\n");
    } else if (command.equals("n")) {
      System.out.println("Maybe next time!");
      setState(State.LOSE);
    } else {
      System.out.println("Invalid command. Please enter y/n");
      startGame();
    }
  }

  // This allows the user to create their own player name.
  private void playerName() {
    String userName = player.playerName();
    if (userName.equals("quit")) {
      System.out.println("I guess you didn't want to play.");
    }
  }

  // creating the map
  public void createMap() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      Location[] locations = mapper.readValue(
          getClass().getClassLoader().getResourceAsStream("location.json"), Location[].class);
      Map<String, Location> map = Arrays.stream(locations)
          .collect(Collectors.toMap(Location::getLocationName, (loc) -> loc));
      gameMap = map;
    } catch (IOException e) {
      throw new RuntimeException(e); // fixme
    }
  }

  // This allows users to type commands.
  public void inputCommand() {
    Scanner input = new Scanner(System.in);
    System.out.println("enterCommand: ");
    String command = input.nextLine().toLowerCase().trim();
    processCommand(command);
  }

  // This handles what happens if the user types in any commands.
  public void processCommand(String commandString) {
    String[] command = commandString.split(" ");
    if (command[0].equals("help")) {
      System.out.println(Intro.HELP_COMMANDS);
    } else if (command[0].equals("quit")) {
      System.out.println("See you next time");
      setState(State.LOSE);
    } else if (command[0].equals("go")) {
      Location currentLocation = gameMap.get(player.getLocation());
      movePlayer(command, currentLocation);
      if (command[1].equals("north") && currentLocation.getNorth().isEmpty()
      || command[1].equals("south") && currentLocation.getSouth().isEmpty()
      || command[1].equals("east") && currentLocation.getEast().isEmpty()
      || command[1].equals("west") && currentLocation.getWest().isEmpty()) {
        System.out.println("\nYou can't go this way, there is nothing there.\n");
      }
    } else if (command[0].equals("new") && command[1].equals("game")) {
      startGame();
    } else if (command[0].equals("get")) {
      addToInventory();
    } else if (command[0].equals("talk")) {
      System.out.println();
      talkToNpc();
    } else if (command[0].equals("heal")) {
      heal();
    }
    else {
      System.out.println("Invalid command. Please enter valid game command such as: \n"
          + "go <north, south, east, west>, help, quit");
    }

  }

  // this handles how the user moves around the world and keeps track of the player.

  private void movePlayer(String[] command, Location currentLocation) {
    Location newLocation;

    if (command[1].equals("north")) {
      // if null keep location, if not change location
      newLocation = currentLocation.getNorth().isEmpty() ? currentLocation
          : gameMap.get(currentLocation.getNorth());

    } else if (command[1].equals("south")) {
      newLocation = currentLocation.getSouth().isEmpty() ? currentLocation
          : gameMap.get(currentLocation.getSouth());

    } else if (command[1].equals("west")) {
      newLocation = currentLocation.getWest().isEmpty() ? currentLocation
          : gameMap.get(currentLocation.getWest());

    } else if (command[1].equals("east")) {
      newLocation = currentLocation.getEast().isEmpty() ? currentLocation
          : gameMap.get(currentLocation.getEast());
    } else {
      newLocation = currentLocation;
      System.out.println("not a valid direction, try another!");
    }
    player.setLocation(newLocation.getLocationName());
  }

  // This handles how the user manipulates the inventory from picking up items to using items.
  public void addToInventory() {

    String item = gameMap.get(player.getLocation()).getItems();

    if (!item.isEmpty() && !newItem.contains(item)) {
      newItem.add(item);
      player.setInventory(newItem);
      // reverse so that newest item added is printed to user
      Collections.reverse(newItem);
      System.out.println(newItem.get(0) + " has been added to your inventory \n");
    } else {
      System.out.println("There is nothing to get \n");
    }
  }

// This handles the user trying to talk to NPC
  private void talkToNpc() {
    Location currentLocation = gameMap.get(player.getLocation());
    if (currentLocation.getNpc().isEmpty()) {
      System.out.println("There is no one to talk to.\n");
    } else {
      System.out.println(currentLocation.getNpc() + " says: " + currentLocation.getNpcQuote());
    }
  }

  private void heal() {
    if (player.getHealth() <= 50 && newItem.contains("duct tape")) {
      playerHealth += 50;
    } else {
      System.out.println("Healing unnecessary!\n");
    }
  }

  // Controls situations where the player can win or lose the game.
  public void winOrLose(){
    Location currentLocation = gameMap.get(player.getLocation());
    if (currentLocation.getLocationName().equals("safe haven")){
      System.out.println("You are now Safe! Congratulations, this is your new life.");
      setState(State.WIN);
    } else if (currentLocation.getLocationName().equals("safe haven") && newItem.contains("portal note")) {
      System.out.println("You are a kind man! You saved everyone, thank you for being you!");
      setState(State.WIN);
    }else if(player.getHealth() <  1 ){
      System.out.println("Better luck next time! You DIED!");
      setState(State.LOSE);
    } else if (currentLocation.getLocationName().equals("not deadly depths")) {
      System.out.println("You took a wrong turn, and have died! You DIED!");
      setState(State.LOSE);
    } else if (currentLocation.getLocationName().equals("toxic river") && !newItem.contains("raft")) {
      System.out.println("You forgot the raft! The river was too toxic and so was your thinking. You DIED!");
      setState(State.LOSE);
    }
  }
  // This handles the information about the player's current location, health, items, and inventory
  // This displays the direction that the player can go in current location, and checks for zombies.

  public void statusUpdate() {
    Location currentLocation = gameMap.get(player.getLocation());
    showZombies();
    System.out.println("You are currently at: " + currentLocation.getLocationName() + "\n");
    System.out.println(currentLocation.getDescription());
    showNPC();
    showPossibleDirections();
    System.out.println("\n" + player.getName() + ", your current health is = " + playerHealth + "\n");
    showItems();
    showInventory();
  }

  // This prints the players current inventory
  public void showInventory() {
    if (player.getInventory() == null) {
      System.out.println("your inventory is empty \n");
    } else {
      System.out.println("Your inventory currently has: " + player.getInventory());
    }
  }

  // this shows the items in the zone, not the inventory.
  public void showItems() {
    String item = gameMap.get(player.getLocation()).getItems();

    if (!item.isEmpty() && !newItem.contains(item)) {
      System.out.println("You see: " + item);
    } else {
      System.out.println("You see no items in this area. \n");
    }
  }
  // Check to see if there is a NPC in the current zone
  public void showNPC() {
    Location currentLocation = gameMap.get(player.getLocation());

    if (!currentLocation.getNpc().isEmpty()) {
      System.out.println("NPC: " + currentLocation.getNpc() + "\n");
    } else {
      System.out.println("NPC: No characters at this location. \n");
    }
  }

  // Check to see if there is a zombie in the current zone
  public void showZombies() {
    Location currentLocation = gameMap.get(player.getLocation());
    if (zombie1.getLocation().equals(currentLocation.getLocationName())
        || (zombie2.getLocation().equals(currentLocation.getLocationName()))) {
      System.out.println("There is a zombie nearby! Defend yourself or cry... I mean die!\n");
    } else if (zombie3.getLocation().equals(currentLocation.getLocationName())) {
      System.out.println("There are two zombies in this room... This is not looking good for you.\n");
    } else if (bossZombie.getLocation().equals(currentLocation.getLocationName())) {
      System.out.println("This is a baby zombie? What harm could it do? \n"
          + "You better have more than one weapon to be safe!\n");
    }
  }

  // This displays the directions that the player has currently available to them.
  public void showPossibleDirections() {

    Location currentLocation = gameMap.get(player.getLocation());
    if (currentLocation.getNorth().isEmpty()) {
      System.out.println("North: Nothing this way");
    } else {
      System.out.println("North: " + currentLocation.getNorth());
    }

    if (currentLocation.getSouth().isEmpty()) {
      System.out.println("South: Nothing this way");
    } else {
      System.out.println("South: " + currentLocation.getSouth());
    }

    if (currentLocation.getWest().isEmpty()) {
      System.out.println("West:  Nothing this way");
    } else {
      System.out.println("West: " + currentLocation.getWest());
    }

    if (currentLocation.getEast().isEmpty()) {
      System.out.println("East:  Nothing this way");
    } else {
      System.out.println("East: " + currentLocation.getEast());
    }

  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}
