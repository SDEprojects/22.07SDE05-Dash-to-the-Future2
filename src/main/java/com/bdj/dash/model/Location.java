package com.bdj.dash.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Location {

  //This class contains mostly getter/setter and variables for locations.
  @JsonProperty("name")
  private String locationName;
  private String items;
  private String north;
  private String south;
  private String east;
  private String west;

  private String description;

  public Location(String locationName, String items, String north, String south, String east,
      String west, String description) {
    this.locationName = locationName;
    this.items = items;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    this.description = description;
  }

  public Location() {
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getItems() {
    return items;
  }

  public void setItems(String items) {
    this.items = items;
  }

  public String getNorth() {
    return north;
  }

  public void setNorth(String north) {
    this.north = north;
  }

  public String getSouth() {
    return south;
  }

  public void setSouth(String south) {
    this.south = south;
  }

  public String getEast() {
    return east;
  }

  public void setEast(String east) {
    this.east = east;
  }

  public String getWest() {
    return west;
  }

  public void setWest(String west) {
    this.west = west;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
