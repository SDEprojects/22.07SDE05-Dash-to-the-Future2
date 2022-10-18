package com.bdj.dash.model;

import com.fasterxml.jackson.databind.JsonNode;

public class Location {

  //This class contains mostly getter/setter and variables for locations.
  private JsonNode locationName;
  private JsonNode items;
  private JsonNode north;
  private JsonNode south;
  private JsonNode east;
  private JsonNode west;

  private JsonNode description;

  public Location(JsonNode locationName, JsonNode items, JsonNode north, JsonNode south, JsonNode east,
      JsonNode west, JsonNode description) {
    this.locationName = locationName;
    this.items = items;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    this.description = description;
  }

  public JsonNode getLocationName() {
    return locationName;
  }

  public void setLocationName(JsonNode locationName) {
    this.locationName = locationName;
  }

  public JsonNode getItems() {
    return items;
  }

  public void setItems(JsonNode items) {
    this.items = items;
  }

  public JsonNode getNorth() {
    return north;
  }

  public void setNorth(JsonNode north) {
    this.north = north;
  }

  public JsonNode getSouth() {
    return south;
  }

  public void setSouth(JsonNode south) {
    this.south = south;
  }

  public JsonNode getEast() {
    return east;
  }

  public void setEast(JsonNode east) {
    this.east = east;
  }

  public JsonNode getWest() {
    return west;
  }

  public void setWest(JsonNode west) {
    this.west = west;
  }

  public JsonNode getDescription() {
    return description;
  }

  public void setDescription(JsonNode description) {
    this.description = description;
  }
}
