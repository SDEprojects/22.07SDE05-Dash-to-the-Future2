package com.bdj.dash.model;

import java.util.Scanner;

public class Player {

  int health = 100;
  String name;

  public String playerName() {
    Scanner userName = new Scanner(System.in);
    System.out.println("What is your name? ");
    name = userName.nextLine().toLowerCase().trim();
    return name;
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
