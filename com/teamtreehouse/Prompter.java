package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Queue;



public class Prompter {
  private Map<String, String> mMenu;
  private BufferedReader mReader;
  private Teams mTeams;
  private Set<Player> mPlayerSet;
  private Team mCurrentTeam;
  private String mFirstName;
  private String mLastName;
  private int mHeight;
  private boolean mExperienced;
  private static int mCounter;
  
  public Prompter(Player[] playerArray) {
    mMenu = new LinkedHashMap<>();
    mMenu.put("create", "Create a new team");
    mMenu.put("add", "Add a player to a team");
    mMenu.put("remove", "Remove a player from a team");
    mMenu.put("view", "View the height report or the experience report for a team");
    mMenu.put("print", "Print a roster of a team");
    mMenu.put("quit", "Quit the program");
    
    mReader = new BufferedReader(new InputStreamReader(System.in));
    mTeams = new Teams();
    mPlayerSet = new TreeSet<>((Arrays.asList(playerArray)));
    mCurrentTeam = new Team();

    mFirstName = "";
    mLastName = "";
    mHeight = 0;
    mExperienced = false;

    Prompter.mCounter = mPlayerSet.size();
  }

  // Displays the menu and returns your choice.
  private String promptAction() throws IOException {
    System.out.println("\nHere is a menu of your options: \n");

    // Loops through the menu options
    for (Map.Entry<String, String> option : mMenu.entrySet()) {
      System.out.printf("\t%s - %s %n", option.getKey(), option.getValue());
    }

    // Prompts for your choice and stores it in a variable.
    System.out.print("\nPlease make your choice: ");
    String choice = mReader.readLine();
    return choice.trim().toLowerCase();
  }

  // Displays the list of teams and returns your choice.
  private String promptTeam() throws IOException {
    // Displays an alphabetically ordered list of teams to choose from
    for (Team team : mTeams.getTeams()) {
      System.out.println("\t" + team.getTeamName());
    }

    String choice;
    boolean isTeamMatch = false;
    do {
      System.out.print("\nPlease choose your team: ");
      choice = mReader.readLine();
      for (Team team : mTeams.getTeams()) {
        if (team.getTeamName().equals(choice)) {

          isTeamMatch = true;
        }
      }
      if (!isTeamMatch) {
        System.out.println("\nYou have made an invalid choice. Try again.\n");
      }
    } while (!isTeamMatch);
    return choice.trim();
  }

  // Set the variable mCurrentTeam to point to a chosen team.
  private void setCurrentTeam(String teamChosen) {
    for (Team team : mTeams.getTeams()) {
      if (team.getTeamName().equals(teamChosen)) {
        mCurrentTeam = team;
      }
    }
  }

  // Displays an alphabetically ordered list of players along with their stats.
  private void printPlayers(Set<Player> playerSet) {
    System.out.printf("%-20s %-20s %-20s %s %n",
            "Last Name", "First Name", "Height in Inches", "Is Experienced");
    System.out.println("=============================================================================");
    for (Player player : playerSet) {
      System.out.printf("%-20s %-20s %-20s %s %n",
              player.getLastName(),
              player.getFirstName(),
              player.getHeightInInches(),
              player.isPreviousExperience());
    }
  }

  // Gets player info and places them in member variables.
  private void setChosenPlayerInfo() throws IOException, NumberFormatException {
    System.out.print("\nPlease enter the last name of the player: ");
    mLastName = mReader.readLine();

    System.out.print("Please enter the first name of the player: ");
    mFirstName = mReader.readLine();

    System.out.print("Please enter the player's height in inches: ");
    String strHeight = mReader.readLine();
    mHeight = Integer.parseInt(strHeight);

    System.out.print("Is the player experienced? Type either true or false: ");
    String strExperienced = mReader.readLine();
    mExperienced = Boolean.parseBoolean(strExperienced);
  }

  private Player addRemovePlayer(Set<Player> players, String strMessage) throws IOException, NumberFormatException {
    Player playerToActOn;
    boolean isContained = false;
    do {
      System.out.printf("%n%s %n", strMessage);
      // Displays an alphabetically ordered list of players along with their stats.
      printPlayers(players);

      setChosenPlayerInfo();

      playerToActOn = new Player(mFirstName, mLastName, mHeight, mExperienced);

      for (Player player : players) {
        if (player.equals(playerToActOn)) {
          isContained = true;
        }
      }
      if (!isContained) {
        System.out.println("\nYou have entered a player not on the list. Try again.");
      }
    } while (!isContained);
    return playerToActOn;
  }
  
  public void run() {
    String choice = "";
    String teamName;
    String coach;
    String teamChosen;
    String reportType;



    do {
      try {
        //Testing...
        System.out.println("Number of players: " + mPlayerSet.size());
        System.out.println("mCounter: " + mCounter);
        choice = promptAction();

        switch (choice) {
          // The menu option that allows you to create a new team.
          case "create":
            // Checks to make sure that you do not create more teams than there are players.
            if (mPlayerSet.size() >= Prompter.mCounter && Prompter.mCounter > 0 ) {
              do {
                // Prompts for team name.
                System.out.print("\nWhat is the name of your team? ");
                teamName = mReader.readLine();

                if (teamName.equals("")) {
                  System.out.println("You must provide the team name. Try again.");
                }
              } while (teamName.equals(""));

              do {
                // Prompts for coach name.
                System.out.print("\nWhat is the name of your coach? ");
                coach = mReader.readLine();

                if (coach.equals("")) {
                  System.out.println("You must provide the name of the coach. Try again.");
                }
              } while (coach.equals(""));

              // Team constructor stores team name and coach in newly created instance variables.
              Team team = new Team(teamName, coach);
              mTeams.addTeam(team);
              Prompter.mCounter--;

              System.out.printf("%nYour team %s to be coached by %s has been created!%n",
                      teamName,
                      coach);
            } else {
              System.out.println("\nThere aren't enough players to create another team.");
            }

            break;
          // The menu option that allows you to add players to a team.
          case "add":
            if (mPlayerSet.size() > 0) {
              if (mTeams.getTeams().size() > 0) {
                System.out.println("\nYou may add players to the following teams: ");
                // Prompts you to choose a team from a list and stores it in a variable.
                teamChosen = promptTeam();

                setCurrentTeam(teamChosen);

                // Checks to make sure that you can't add more than 11 players on a team.
                if (mCurrentTeam.getPlayerCount() < Team.MAX_NUM_PLAYERS) {
                  String strMessage = "\nHere is a list of players that you can add to your team.\n";
                  Player playerToAdd = addRemovePlayer(mPlayerSet, strMessage);
                  // Player is added to a chosen team.
                  mCurrentTeam.addPlayer(playerToAdd);
                  if (mCurrentTeam.getPlayerCount() > 1) {
                    Prompter.mCounter--;
                  }

                  for (Player player : mPlayerSet) {
                    if (player.equals(playerToAdd)) {
                      // Removes player from the list after adding player to a chosen team.
                      mPlayerSet.remove(player);
                      break;
                    }
                  }

                  System.out.printf("%n%s %s has been added to %s %n",
                          playerToAdd.getFirstName(),
                          playerToAdd.getLastName(),
                          mCurrentTeam.getTeamName());
                } else {
                  System.out.printf("%nThis team has the maximum number of %d players. You cannot add anymore.%n",
                          Team.MAX_NUM_PLAYERS);
                }
              } else {
                System.out.println("\nThere are no teams. You must first create a team to add to it.\n");
              }

            } else {
              System.out.println("\nThere are no more players to add.");
            }
            break;
          // The menu option that allows you to remove players from a team.
          case "remove":
            if (mTeams.getTeams().size() > 0) {
              System.out.println("You may remove players from the following teams: ");
              // Prompts you to choose a team from a list and stores it in a variable.
              teamChosen = promptTeam();

              setCurrentTeam(teamChosen);
              if (mCurrentTeam.getPlayerCount() > 0) {
                String strMessage = "\nHere is a list of players you can remove from your team: \n";
                Player playerToRemove = addRemovePlayer(mCurrentTeam.getPlayers(), strMessage);
                mPlayerSet.add(playerToRemove);
                if (mCurrentTeam.getPlayerCount() > 1) {
                  Prompter.mCounter++;
                }

                for (Player player : mCurrentTeam.getPlayers()) {
                  if (player.equals(playerToRemove)) {
                    // Player is removed from a chosen team.
                    mCurrentTeam.removePlayer(player);
                    break;
                  }
                }

                System.out.printf("%n%s %s has been removed from %s %n",
                        playerToRemove.getFirstName(),
                        playerToRemove.getLastName(),
                        mCurrentTeam.getTeamName());
              } else {
                System.out.println("\nThere are no players on this team to remove.\n");
              }

            } else {
              System.out.println("\nThere are no teams to remove from.\n");
            }
            break;
          // The menu option to view the height and experience reports.
          case "view":
            if (mTeams.getTeams().size() > 0) {

              System.out.print("\nWhich report do you want to see? Type \"height\" or \"experience\": ");
              reportType = mReader.readLine().trim();

              if (reportType.equalsIgnoreCase("height")) {
                System.out.println("You may view a height report from the following teams: ");
                teamChosen = promptTeam();
                setCurrentTeam(teamChosen);
                if (mCurrentTeam.getPlayerCount() > 0) {
                  // Uses a proper collection that maps by height.
                  Map<Integer, Set<Player>> mapByHeight = new TreeMap<>();

                  Queue<Player> playersQueue = new ArrayDeque<>(mCurrentTeam.getPlayers());

                  while (playersQueue.size() > 0) {
                    Player tempPlayer = playersQueue.poll();
                    Set<Player> playersGroupByHeight = new TreeSet<>();
                    if (!mapByHeight.containsKey(tempPlayer.getHeightInInches())) {

                      playersGroupByHeight.add(tempPlayer);

                    } else {
                      playersGroupByHeight = mapByHeight.get(tempPlayer.getHeightInInches());
                      playersGroupByHeight.add(tempPlayer);

                    }
                    mapByHeight.put(tempPlayer.getHeightInInches(), playersGroupByHeight);

                  }

                  // Displays a report of a chosen team grouped by height range.
                  for (int i = Players.MIN_HEIGHT; i <= Players.MAX_HEIGHT; i = i + 4) {
                    System.out.println("\nHeight Range: " + i + " to " + (i + 3));
                    System.out.println("===============================================================" +
                            "=================================================");
                    System.out.printf("%-15s %-15s %-20s %-30s %s%n",
                            "First Name",
                            "Last Name",
                            "Height in Inches",
                            "Count of Players at Height",
                            "Experience"
                    );
                    for (int j = i; j <= i + 3; j++) {
                      for (Map.Entry<Integer, Set<Player>> entry : mapByHeight.entrySet()) {
                        if (entry.getKey() == j) {
                          Set<Player> playerByHeight = new TreeSet<>(entry.getValue());

                          for (Player player : playerByHeight) {
                            System.out.printf("%-15s %-15s %-20d %-30d %s%n",
                                    player.getFirstName(),
                                    player.getLastName(),
                                    player.getHeightInInches(),
                                    // Shows a count of how many players are that height on each team.
                                    playerByHeight.size(),
                                    player.isPreviousExperience());
                          }
                        }

                      }
                    }
                  }
                } else {
                  System.out.println("\nThere are no players in this team.\n");
                }
              } else if (reportType.equalsIgnoreCase("experience")) {
                // Displays a report that uses a map like solution to properly report
                // experienced vs inexperienced for each team.
                Map<String, Map<String, Integer>> mapTeamToExpCounts = new TreeMap<>();
                Map<String, Integer> mapExpCounts;
                int expCount = 0;
                int inExpCount = 0;


                for (Team team : mTeams.getTeams()) {
                  for (Player player : team.getPlayers()) {
                    if (player.isPreviousExperience()) {
                      expCount++;
                    } else {
                      inExpCount++;
                    }
                  }
                  mapExpCounts = new HashMap<>();
                  mapExpCounts.put("Experienced", expCount);
                  mapExpCounts.put("Inexperience", inExpCount);
                  mapTeamToExpCounts.put(team.getTeamName(), mapExpCounts);
                  expCount = 0;
                  inExpCount = 0;


                }

                // Displays a League Balance Report for all teams in the league showing a
                // total count of experienced vs. inexperienced players.
                System.out.println("\nLeague Balance Report: ");
                System.out.println("========================================================");
                for (Map.Entry<String, Map<String, Integer>> entry : mapTeamToExpCounts.entrySet()) {
                  String expKey = "";
                  String inExpKey = "";
                  System.out.println("\n" + entry.getKey());
                  mapExpCounts = new HashMap<>(mapTeamToExpCounts.get(entry.getKey()));
                  for (Map.Entry<String, Integer> innerEntry : mapExpCounts.entrySet()) {

                    if (innerEntry.getKey().equalsIgnoreCase("Experienced")) {
                      expCount = innerEntry.getValue();
                      expKey = innerEntry.getKey();
                    } else {
                      inExpCount = innerEntry.getValue();
                      inExpKey = innerEntry.getKey();
                    }

                  }

                  double dExpCount = expCount;
                  double dInExpCount = inExpCount;

                  // Calculates the percentage of experienced players on each team.
                  double dExpPercent;
                  double dInExpPercent;

                  if (expCount != 0) {
                    dExpPercent = (dExpCount / (dExpCount + dInExpCount)) * 100;
                  } else {
                    dExpPercent = 0.0;
                  }
                  if (inExpCount != 0) {
                    dInExpPercent = (dInExpCount / (dExpCount + dInExpCount)) * 100;
                  } else {
                    dInExpPercent = 0.0;
                  }

                  System.out.printf("\t%-20s: %d (%.2f%%)%n", expKey, expCount, dExpPercent);
                  System.out.printf("\t%-20s: %d (%.2f%%)%n", inExpKey, inExpCount, dInExpPercent);
                }

              /* Alternative solution for reporting experienced vs inexperienced for each team.
              System.out.println("\nLeague Balance Report: ");
              System.out.println("========================================================");

              for (Team team : mTeams.getTeams()) {
                System.out.println(team.getTeamName());
                System.out.printf("\t%-20s: %d (%s)%n", "Experienced:",
                        team.getExpCount(true),
                        team.getExpPercent(true));
                System.out.printf("\t%-20s: %d (%s)%n%n", "Inexperienced:",
                        team.getExpCount(false),
                        team.getExpPercent(false));

              }*/
              } else {
                System.out.println("\nYou have entered an invalid choice. Try again. \n");
              }
            } else {
              System.out.println("\nThere are no teams to view.\n");
            }
            break;
          // The menu option to print out a roster of all the players on a team
          case "print":
            if (mTeams.getTeams().size() > 0) {
              System.out.println("\nYou may print out a roster of your team.");
              teamChosen = promptTeam();
              setCurrentTeam(teamChosen);

              if (mCurrentTeam.getPlayerCount() > 0) {
                System.out.println("\nHere is a list of players on the team " + mCurrentTeam.getTeamName() +
                                  " coached by " +mCurrentTeam.getCoach() + ":\n");
                // Displays a list of the players on a chosen team.
                printPlayers(mCurrentTeam.getPlayers());
              } else {
                System.out.println("\nThere are no players in the team to print out.\n");
              }
            } else {
              System.out.println("\nThere are no teams to print out.\n");
            }
            break;
          case "quit":
            break;
          default:
            System.out.printf("%nYou have entered an invalid choice. Try again. %n");
        }
      } catch (IOException ioe) {
        System.out.println("Something went wrong with input");
        ioe.printStackTrace();
      } catch (NumberFormatException nfe) {
        System.out.println("You have entered an invalid number");
        nfe.printStackTrace();
      }
    } while (!choice.equals("quit"));
    
  }
}