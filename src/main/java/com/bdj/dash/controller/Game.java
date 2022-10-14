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

    locationMap.put("abandoned house", new Location("abandoned house", new
        String[]{"crossbow"}, "crossroads", null,null,null,
        "Oops....looks like you got sent to the future. What are you going to do?\n"
            + "Looks like you are at the abandoned building. This future world looks like it's in"
            + " shambles. What happened? Is there any place safe around here, or any way back to"
            + " the past?\n You don't see much around you, but there is a crossbow lying on the"
            + " ground and a sign reading \"Safe Haven\". \n You can also see a warning sign in one"
            + " corner that says, \"Proceed with caution. There are zombies everywhere.\"\n"
            + "There are also crossroads north of you."));

    locationMap.put("crossroads", new Location("crossroads", new String[]{}, null,
        "abandoned house",null,"grocery store", "Looks like you are standing"
        + " in the middle of the crossroad. It doesn't look too safe out here. You can see someone "
        + "close to you, but don't know who that is. There is a grocery store to the west."
        + " There is nothing else in any other direction."));

    locationMap.put("grocery store", new Location("grocery store", new String[]{},
        "abandoned fairgrounds", null,"crossroads",null, "Oh no! You"
        + " encountered a zombie. It might be smart to add that crossbow to your inventory now. "));

    locationMap.put("abandoned fairgrounds", new Location("abandoned fairgrounds",
        new String[]{"spiked bat"}, "street pharmaceutical", "grocery store",null,
        "invasion woods", "This place probably used to be up and popping. You see a "
        + "spiked bat way out in the ground. There is invasion woods to the west, street"
        + " pharmaceuticals directly to the north and grocery store, south now behind you."));

    locationMap.put("street pharmaceutical", new Location("street pharmaceutical",
        new String[]{"duct tape"}, null, "abandoned fairgrounds","radioactive club",
        null, "Oh look! You are at the Street pharmaceuticals. Looks like all they"
        + " have is a duct tape. There is radioactive club to the east."));

    locationMap.put("radioactive club", new Location("radioactive club", new String[]{},
        null, null,null,"street pharmaceutical", "What kind of club is"
        + " this? Looks like you have encountered 2 zombies. You don't have enough items in your"
        + " inventory to kill them both. You are DEAD!"));

    locationMap.put("invasion woods", new Location("invasion woods", new String[]{},
        "rusty gun store", null,"abandoned fairgrounds",null, "Oh, you"
        + " found yourself in the middle of the woods. You have watched way too many horror films"
        + " to know what happens. Push through as fast as you can!\n Wait, what is that? Someone is"
        + " watching you from the trees above."));

    locationMap.put("rusty gun store", new Location("rusty gun store",
        new String[]{"gun"}, "shanty docks", "invasion woods",null,null,
        "Well, this looks like a useful place. Let's see if they have anything. Oh wait,"
            + " they have a gun. You definitely need a gun moving forward.\n There is Shanty Docks"
            + " to the north, and invasion Woods directly to the south."));

    locationMap.put("shanty docks", new Location("shanty docks", new String[]{"raft"},
        null, "rusty gun store","toxic river",null, "This is exciting."
        + " You see a bunch of rotten rafts. Oh wait, there is a serviceable raft. You can take it"
        + " to get to the toxic river to the east.\n"));

    locationMap.put("toxic river", new Location("toxic river", new String[]{},
        "not deadly depths", null,"boss room","zombie motel caves",
        "Oh man! You must be really thirsty from the long trip. But wait, this stinky"
            + " water cannot be trusted.\n There is Zombie Motel Caves to the west, Not Deadly "
            + "Depths to the north and the Boss Room to the east. Think wisely before you "
            + "proceed!"));

    locationMap.put("zombie motel caves", new Location("zombie motel caves",
        new String[]{}, "invasion woods", "secret bunker",null,null,
        "You found yourself at the Zombie Motel Caves. Who do you think you encounter"
            + " here? Duh - a zombie.\n There is a secret bunker to the south."));

    locationMap.put("secret bunker", new Location("secret bunker", new String[]{},
        "zombie motel caves", "portal",null,null, "Well, that looks"
        + " like a safe place to hide. You can also go back north to the Zombie Motel Caves where"
        + " you came from. What is that piece of paper sticking out from beneath the rocks? It is a"
        + " note that reads, \"The future of safe haven lies in your hands. Take this note to the "
        + "Safe Haven to save the people and restore peace forever.\"\n"));

    locationMap.put("portal", new Location("portal", new String[]{}, "secret "
        + "bunker", null,null,null, "Oh look, you have made it to the "
        + "portal. You've successfully gone back to the past. I wish you the best with door-dashing"
        + ". Be aware of the abandoned places in the future!"));

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
        + "all the obstacles and reached Safe Haven. The people appreciate your selfless help. You"
        + " can now have all the happiness you ever imagined.\n"));
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
      System.out.println("Invalid command. Please enter valid game command such as: \n"
          + "go <north, south, east, west>, help, quit");
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
      System.out.println("not a valid direction, try another!");
    }
    player.setLocation(newLocation);
  }

  public void statusUpdate(){
    int pHp = player.getHealth();
    System.out.println(player.getName() + " your current health is = " + pHp);
    System.out.println("You are currently at: " + player.getLocation());
    System.out.println(locationMap.get(player.getLocation()).getDescription());
    System.out.println("Your inventory currently has: " + player.getInventory());
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
