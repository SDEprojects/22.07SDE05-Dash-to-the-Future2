package com.bdj.dash.controller;

import com.bdj.dash.model.Location;
import com.bdj.dash.model.Music;
import com.bdj.dash.model.Player;
import com.bdj.dash.model.State;
import com.bdj.dash.model.Zombie;
import com.bdj.dash.view.ConsoleColors;
import com.bdj.dash.view.Intro;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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

  // File path for playing music
  String filepath = "./src/main/resources/BGM.wav";
  Music musicObject = new Music();

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
    System.out.println(ConsoleColors.CYAN + "Would you like to begin the game? y/n" + ConsoleColors.RESET);
    String command = input.nextLine().toLowerCase().trim();
    if (command.equals("y")) {
      setState(State.PLAY);
      musicObject.playMusic(filepath);
      playerName();
      createMap();
      player.setLocation(gameMap.get("abandoned house").getLocationName());
      zombie1.setLocation(gameMap.get("grocery store").getLocationName());
      zombie2.setLocation(gameMap.get("zombie motel caves").getLocationName());
      zombie3.setLocation(gameMap.get("radioactive club").getLocationName());
      zombie4.setLocation(gameMap.get("radioactive club").getLocationName());
      bossZombie.setLocation(gameMap.get("boss room").getLocationName());

      System.out.println(ConsoleColors.GREEN + "The game has begun!\n" + ConsoleColors.RESET);
    } else if (command.equals("n")) {
      System.out.println(ConsoleColors.RED + "Maybe next time!" + ConsoleColors.RESET);
      setState(State.LOSE);
    } else {
      System.out.println(ConsoleColors.BRIGHT_YELLOW + "Invalid command. Please enter y/n" + ConsoleColors.RESET);
      startGame();
    }
  }

  // This allows the user to create their own player name.
  private void playerName() {
    String userName = player.playerName();
    if (userName.equals("quit")) {
      System.out.println(ConsoleColors.BLUE + "I guess you didn't want to play." + ConsoleColors.RESET);
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
    System.out.println(ConsoleColors.RED + "Enter Command: " + ConsoleColors.RESET);
    String command = input.nextLine().toLowerCase().trim();
    processCommand(command);
  }

  // This handles what happens if the user types in any commands.
  public void processCommand(String commandString) {
    String[] command = commandString.split(" ");
    if (command[0].equals("help")) {
      System.out.println(Intro.HELP_COMMANDS);
    } else if (command[0].equals("quit")) {
      System.out.println(ConsoleColors.CYAN + "See you next time" + ConsoleColors.RESET);
      setState(State.LOSE);
    } else if (command[0].equals("go")) {
      Location currentLocation = gameMap.get(player.getLocation());
      movePlayer(command, currentLocation);
      if (command[1].equals("north") && currentLocation.getNorth().isEmpty()
      || command[1].equals("south") && currentLocation.getSouth().isEmpty()
      || command[1].equals("east") && currentLocation.getEast().isEmpty()
      || command[1].equals("west") && currentLocation.getWest().isEmpty()) {
        System.out.println(ConsoleColors.BRIGHT_RED + ConsoleColors.BOLD + "\nYou can't go this way, there is nothing there.\n" + ConsoleColors.RESET);
      }
    } else if (command[0].equals("new") && command[1].equals("game")) {
      startGame();
    } else if (command[0].equals("get")) {
      addToInventory();
    } else if (command[0].equals("talk")) {
      talkToNpc();
    } else if (command[0].equals("heal")) {
      heal();
    } else if (command[0].equals("music-on")) {
      System.out.println("Starting the music");
    } else if (command[0].equals("music-off")) {
      System.out.println("Turing off the music");
      musicObject.stopMusic(filepath);
    } else {
      System.out.println(ConsoleColors.BRIGHT_RED + "Invalid command. Please enter valid game command such as: \n" + ConsoleColors.RESET
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
      System.out.println(ConsoleColors.YELLOW + "Not a valid direction, try another!" + ConsoleColors.RESET);
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
      System.out.println(ConsoleColors.PURPLE + newItem.get(0) + ConsoleColors.RESET + " has been added to your inventory \n");
    } else {
      System.out.println(ConsoleColors.RED + "There is nothing to get \n" + ConsoleColors.RESET);
    }
  }

// This handles the user trying to talk to NPC
  private void talkToNpc() {
    Location currentLocation = gameMap.get(player.getLocation());
    if (currentLocation.getNpc().isEmpty()) {
      System.out.println(ConsoleColors.BLUE + "There is no one to talk to.\n" + ConsoleColors.RESET);
    } else {
      System.out.println(ConsoleColors.BLUE + currentLocation.getNpc()  + " says: " + ConsoleColors.RESET + currentLocation.getNpcQuote());
    }
  }

  private void heal() {
    if (player.getHealth() <= 50 && newItem.contains("duct tape")) {
      playerHealth += 50;
    } else {
      System.out.println(ConsoleColors.YELLOW + "Healing unnecessary!\n" + ConsoleColors.RESET);
    }
  }

  // Controls situations where the player can win or lose the game.
  public void winOrLose(){
    Location currentLocation = gameMap.get(player.getLocation());
    if (currentLocation.getLocationName().equals("safe haven")){
      System.out.println(ConsoleColors.BRIGHT_GREEN + "You are now Safe! Congratulations, this is your new life." + ConsoleColors.RESET);
      setState(State.WIN);
    } else if (currentLocation.getLocationName().equals("safe haven") && newItem.contains("portal note")) {
      System.out.println(ConsoleColors.BRIGHT_GREEN + "You are a kind man! You saved everyone, thank you for being you!" + ConsoleColors.RESET);
      setState(State.WIN);
    }else if(player.getHealth() <  1 ){
      System.out.println(ConsoleColors.BRIGHT_RED + "Better luck next time! You DIED!" + ConsoleColors.RESET);
      setState(State.LOSE);
    } else if (currentLocation.getLocationName().equals("not deadly depths")) {
      System.out.println(ConsoleColors.BRIGHT_RED + "You took a wrong turn! You DIED!" + ConsoleColors.RESET);
      setState(State.LOSE);
    } else if (currentLocation.getLocationName().equals("toxic river") && !newItem.contains("raft")) {
      System.out.println(ConsoleColors.BRIGHT_RED + "You forgot the raft! The river was too toxic and so was your thinking. You DIED!" + ConsoleColors.RESET);
      setState(State.LOSE);
    }
  }
  // This handles the information about the player's current location, health, items, and inventory
  // This displays the direction that the player can go in current location, and checks for zombies.

  public void statusUpdate() {
    Location currentLocation = gameMap.get(player.getLocation());
    showZombies();
    System.out.println("You are currently at: " + ConsoleColors.PURPLE + ConsoleColors.BOLD + currentLocation.getLocationName() + "\n" + ConsoleColors.RESET);
    System.out.println(currentLocation.getDescription());
    showNPC();
    showPossibleDirections();
    System.out.println("\n" + player.getName() + ", your current health is = " + ConsoleColors.BRIGHT_RED + playerHealth + "\n" + ConsoleColors.RESET);
    showItems();
    showInventory();
  }

  // This prints the players current inventory
  public void showInventory() {
    if (player.getInventory() == null) {
      System.out.println("your inventory is empty \n");
    } else {
      System.out.println("Your inventory currently has: " + ConsoleColors.BRIGHT_BLUE + player.getInventory() + ConsoleColors.RESET);
    }
  }

  // this shows the items in the zone, not the inventory.
  public void showItems() {
    String item = gameMap.get(player.getLocation()).getItems();

    if (!item.isEmpty() && !newItem.contains(item)) {
      System.out.println("You see: " + ConsoleColors.BRIGHT_CYAN + item + ConsoleColors.RESET);
    } else {
      System.out.println(ConsoleColors.BRIGHT_CYAN + "You see no items in this area. \n" + ConsoleColors.RESET);
    }
  }
  // Check to see if there is a NPC in the current zone
  public void showNPC() {
    Location currentLocation = gameMap.get(player.getLocation());

    if (!currentLocation.getNpc().isEmpty()) {
      System.out.println(ConsoleColors.BLUE + "NPC: " + currentLocation.getNpc() + "\n" + ConsoleColors.RESET);
    } else {
      System.out.println(ConsoleColors.BLUE + "NPC: No characters at this location. \n" + ConsoleColors.RESET);
    }
  }

  // Check to see if there is a zombie in the current zone
  public void showZombies() {
    Location currentLocation = gameMap.get(player.getLocation());
    if (zombie1.getLocation().equals(currentLocation.getLocationName())
        || (zombie2.getLocation().equals(currentLocation.getLocationName()))) {
      System.out.println(ConsoleColors.BRIGHT_YELLOW + "There is a zombie nearby! Defend yourself or cry... I mean die!\n" + ConsoleColors.RESET);
    } else if (zombie3.getLocation().equals(currentLocation.getLocationName())) {
      System.out.println(ConsoleColors.BRIGHT_YELLOW + "There are two zombies in this room... This is not looking good for you.\n" + ConsoleColors.RESET);
    } else if (bossZombie.getLocation().equals(currentLocation.getLocationName())) {
      System.out.println(ConsoleColors.BRIGHT_YELLOW + "This is a baby zombie? What harm could it do? \n"
          + "You better have more than one weapon to be safe!\n" + ConsoleColors.RESET);
    }
  }

  // This displays the directions that the player has currently available to them.
  public void showPossibleDirections() {

    Location currentLocation = gameMap.get(player.getLocation());
    if (currentLocation.getNorth().isEmpty()) {
      System.out.println("North: Nothing this way");
    } else {
      System.out.println("North: " + ConsoleColors.BRIGHT_CYAN + currentLocation.getNorth() + ConsoleColors.RESET);
    }

    if (currentLocation.getSouth().isEmpty()) {
      System.out.println("South: Nothing this way");
    } else {
      System.out.println("South: " + ConsoleColors.BRIGHT_CYAN + currentLocation.getSouth() + ConsoleColors.RESET);
    }

    if (currentLocation.getWest().isEmpty()) {
      System.out.println("West:  Nothing this way");
    } else {
      System.out.println("West: " + ConsoleColors.BRIGHT_CYAN + currentLocation.getWest() + ConsoleColors.RESET);
    }

    if (currentLocation.getEast().isEmpty()) {
      System.out.println("East:  Nothing this way");
    } else {
      System.out.println("East: " + ConsoleColors.BRIGHT_CYAN + currentLocation.getEast() + ConsoleColors.RESET);
    }

  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}
