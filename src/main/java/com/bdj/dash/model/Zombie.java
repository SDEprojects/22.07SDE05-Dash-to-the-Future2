package com.bdj.dash.model;
import com.bdj.dash.model.Location;

public class Zombie {

  // This handles information about the zombies from health to name and location.
  int health = 50;
  String location;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getHealth() {
    return health;
  }

}
