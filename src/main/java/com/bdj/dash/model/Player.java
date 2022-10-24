package com.bdj.dash.model;

import com.bdj.dash.view.ConsoleColors;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

  // This handles information about the player from health to name and location.
  int health = 100;
  String name;

 String location;

  ArrayList<String> inventory;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String playerName() {
    Scanner userName = new Scanner(System.in);
    System.out.println(ConsoleColors.BRIGHT_GREEN + "What is your name? " + ConsoleColors.RESET);
    name = userName.nextLine().toLowerCase().trim();
    return name;
  }

  public boolean playerHasItem(String item){
    if (getInventory().contains(item)){
      return true;
    } else {
      return false;
    }
  }

  public ArrayList<String> getInventory() {
    return inventory;
  }

  public void setInventory(ArrayList<String> inventory) {
    this.inventory = inventory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

}
