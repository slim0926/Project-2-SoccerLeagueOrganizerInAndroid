package com.teamtreehouse.model;

import java.util.Set;
import java.util.TreeSet;

public class Teams {
  private Set<Team> mTeams;
  
  public Teams() {
    mTeams = new TreeSet<>();
  }
  
  public void addTeam(Team team) {
    mTeams.add(team);
  }
  
  public Set<Team> getTeams() {
    return mTeams;
  }
}