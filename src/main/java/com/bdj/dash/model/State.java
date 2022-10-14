package com.bdj.dash.model;

public enum State {

  WIN,
  LOSE,
  PLAY {
    @Override
    public boolean isTerminal() {
      return false;
    }
  };

  public boolean isTerminal() {
    return true;
  }

}
