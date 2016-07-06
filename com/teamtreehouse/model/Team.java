package com.teamtreehouse.model;

import java.util.Set;
import java.util.TreeSet;

public class Team implements Comparable<Team> {
  public static final int MAX_NUM_PLAYERS = 11;

  private String mTeamName;
  private String mCoach;
  private Set<Player> mPlayers;

  public Team() {
    mTeamName = "";
    mCoach = "";
    mPlayers = new TreeSet<>();
  }
  
  public Team(String teamName, String coach) {
    mTeamName = teamName;
    mCoach = coach;
    mPlayers = new TreeSet<>();
  }
  
  public String getTeamName() {
    return mTeamName;
  }
  
  public String getCoach() {
    return mCoach;
  }

  public int getPlayerCount() {
    return mPlayers.size();
  }

  public void addPlayer(Player player) {
    mPlayers.add(player);  
  }
  
  public void removePlayer(Player player) {
    mPlayers.remove(player);
  }
  
  public Set<Player> getPlayers() {
    return mPlayers;
  }

  /* Alternate solution for reporting experienced vs. inexperienced for each team.
  public int getExpCount(boolean exp) {
    int count = 0;
    for (Player player : mPlayers) {
      if (player.isPreviousExperience() == exp) {
        count++;
      }
    }
    return count;
  }

  public String getExpPercent(boolean exp) {
    double dCount = getExpCount(exp);

    dCount = (dCount / mPlayers.size()) * 100;
    return String.valueOf(dCount) + "%";
  }
  */

  @Override
  public int compareTo(Team other) {
    return this.mTeamName.compareTo(other.mTeamName);
  }
    
  
} 