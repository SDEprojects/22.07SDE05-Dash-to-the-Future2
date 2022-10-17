package com.bdj.dash.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Reader {

  JSONParser jsonParser = new JSONParser();

  public Reader() throws IOException, ParseException {
    InputStream locationJson = Reader.class.getClassLoader().getResourceAsStream("location.json");
    JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(locationJson));
  }

}
