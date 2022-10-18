package com.bdj.dash.model;


import static com.bdj.dash.model.JacksonParser.parse;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;

public class Reader {

  private final JsonNode moveLocation;

  public Reader() {
    try {
      InputStream locationJson = Reader.class.getClassLoader().getResourceAsStream("location.json");
      moveLocation = parse(locationJson);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

}
