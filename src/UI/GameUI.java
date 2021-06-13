/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import app.Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static app.Music.playMusic;

/**
 *
 * @author VikyVixxxen
 */
public class GameUI {

    static Scanner sc = new Scanner(System.in);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String path = "Data";
        String chosenGroup;
        String groups;
        String player1;
        String player2;
        String opponent1;
        String opponent2;
        String input;
        String inputToEnd;
        int bottomLimit;
        int poolOfPlayers;
        int n;
        int m;
        int pcDiced;
        int diced;
        Pattern p;
        Matcher match;
        do {
            Game comp = new Game();
            try {
                while (true) {
                    try {
                        System.out.println("List of players:");
                        comp.loadPlayerInfo(new File(path + File.separator + "player_info"));
                        System.out.println(comp.getPlayers());
                        comp.loadDatesAndTimes(new File(path + File.separator + "DateTime"));
                        comp.setMassMapWon();
                        comp.setMassMatchupWon();
                        comp.setMassDefaultTime();
                        System.out.println("To display how players are separated into groups press Enter");
                        System.in.read();
                        comp.getPlayersSortedByName();
                        groups = comp.separate();
                        System.out.println(groups);
                        comp.saveGroups(new File(path + File.separator + "Group Stage"), groups);
                        System.out.println("Choose one group with which you will play the group stage matches.");
                        do {
                            chosenGroup = sc.next().toLowerCase();
                        } while (!isAnExistingGroup(chosenGroup));
                        bottomLimit = comp.chooseGroup(chosenGroup);
                        poolOfPlayers = 6;
                        while (poolOfPlayers > 0) {
                            System.out.println(comp.selectGroup(bottomLimit, bottomLimit + 6));
                            comp.loadMaps(new File(path + File.separator + "Maps"));
                            System.out.println("Choose two players that will play against each other. Enter their names.");
                            player1 = sc.next();
                            player2 = sc.next();
                            while (comp.isValidName(player1) == false || comp.isValidName(player2) == false) {
                                if (comp.isValidName(player1) == false) {
                                    System.out.println("Name " + player1 + " is incorrect.");
                                    player1 = sc.next();
                                } else {
                                    System.out.println("Name " + player2 + " is incorrect.");
                                    player2 = sc.next();
                                }
                            }
                            while (comp.findByPlayerName(player1).getNumOfWonMaps() != Integer.MAX_VALUE || comp.findByPlayerName(player2).getNumOfWonMaps() != Integer.MAX_VALUE) {
                                if (comp.findByPlayerName(player1).getNumOfWonMaps() != Integer.MAX_VALUE) {
                                    System.out.println(" " + player1 + "  recently played choose a different player");
                                    player1 = sc.next();
                                } else {
                                    System.out.println(" " + player2 + "  recently played choose a different player");
                                    player2 = sc.next();
                                }
                            }
                            System.out.println(comp.createMatch(player1, player2));
                            poolOfPlayers = poolOfPlayers - 2;
                            System.out.println("Chose a player for whom you wish to throw dice");
                            do {
                                opponent1 = sc.next();
                                while (comp.isValidName(opponent1) == false) {
                                    System.out.println("Name " + opponent1 + " is incorrect.");
                                    opponent1 = sc.next();
                                }
                                opponent2 = determineOpponent2(opponent1, player1, player2);
                            } while (opponent2 == null);
                            n = 0;
                            m = 0;
                            System.out.println("Score " + opponent1 + " 0 : 0 " + opponent2);
                            while (m < 2 && n < 2) {
                                System.out.println("***********************");
                                System.out.println("Map: " + comp.chooseRandomMap());
                                System.out.println("Enter a number between 1 to 6");
                                p = Pattern.compile("(^[1-6]{1}$)");
                                input = sc.next();
                                match = p.matcher(input);
                                while (!match.find()) {
                                    System.out.println("Not in range!");
                                    input = sc.next();
                                    match = p.matcher(input);
                                }

                                diced = Integer.parseInt(input);
                                pcDiced = comp.pcThrowsDice();
                                if (diced > pcDiced) {
                                    n = n + 1;
                                    comp.findByPlayerName(opponent1).setNumOfWonMaps(n);
                                    comp.findByPlayerName(opponent2).setNumOfWonMaps(m);
                                    System.out.println("Computer threw" + pcDiced);
                                    System.out.println("Score " + opponent1 + "  " + comp.findByPlayerName(opponent1).getNumOfWonMaps() + " : " + comp.findByPlayerName(opponent2).getNumOfWonMaps() + "  " + opponent2);
                                } else if (diced < pcDiced) {
                                    m = m + 1;
                                    comp.findByPlayerName(opponent2).setNumOfWonMaps(m);
                                    comp.findByPlayerName(opponent1).setNumOfWonMaps(n);
                                    System.out.println("Computer threw " + pcDiced);
                                    System.out.println("Score " + opponent2 + "  " + comp.findByPlayerName(opponent2).getNumOfWonMaps() + " : " + comp.findByPlayerName(opponent1).getNumOfWonMaps() + "  " + opponent1);
                                } else {
                                    System.out.println("Computer threw " + pcDiced);
                                    System.out.println("################## Tie Throw again ###################");
                                }
                            }
                            if (comp.findByPlayerName(opponent2).getNumOfWonMaps() > comp.findByPlayerName(opponent1).getNumOfWonMaps()) {
                                comp.findByPlayerName(opponent2).setNumOfWins(1);
                                comp.findByPlayerName(opponent1).setNumOfWins(0);
                            } else {
                                comp.findByPlayerName(opponent1).setNumOfWins(1);
                                comp.findByPlayerName(opponent2).setNumOfWins(0);
                            }
                        }
                        System.out.println("Players competing for 4.th advancing position  ");
                        Collections.sort(comp.groupToSort(bottomLimit, bottomLimit + 6));
                        System.out.println(comp.selectGroup(bottomLimit + 3, bottomLimit + 6));
                        comp.determineFourth(bottomLimit + 3, bottomLimit + 6);
                        System.out.println("To display dvancing players from this group press Enter");
                        System.in.read();
                        comp.getGroupsSortedByMapsWon(bottomLimit, bottomLimit + 6);
                        System.out.println(comp.listOfAdvancingFromGroup(bottomLimit, bottomLimit + 4));
                        System.out.println("To display all advancing players press Enter");
                        System.in.read();
                        comp.createListOfAllAdvancing();
                        comp.sortByDateAndTime();
                        System.out.println(comp.listOfAllAdvancingPlayers());
                        comp.saveGroupsResults(new File(path + File.separator + "GroupStageResults"));
                        System.out.println("To start climbing rounds press Enter");
                        System.in.read();
                        System.out.println(comp.pcPlayedRounds());
                        comp.sortedByWins();
                        System.out.println("To see the final score press Enter");
                        System.in.read();
                        System.out.println(comp.getPlayers());
                        comp.saveFinalScore(new File(path + File.separator + "Final Results"));
                        System.out.println("And the winner is:  " + comp.DisplayWinner());
                        playMusic("Music\\\\cheer.wav");
                        System.out.println("To display the leader board press Enter");
                        System.in.read();
                        System.out.println(comp.displayTable());
                        comp.saveLeaderBoard(new File(path + File.separator + "Leader board"));
                        comp.saveResultsInBin(new File(path + File.separator + "Winners in BIN"));
                        break;
                    } catch (FileNotFoundException e) {
                        System.out.println("This file doesnt exist");
                    } catch (NoSuchElementException e) {
                        System.out.println("Player with this name does'nt exist");
                    }
                }
            } catch (IOException e) {
                System.out.println("There was a problem " + e.getMessage());
            }

            System.out.println("To end the program enter 0 or lesser number");
            inputToEnd = sc.next();
            while (isANumber(inputToEnd) == false) {
                inputToEnd = sc.next();
            }
        } while (Integer.parseInt(inputToEnd) > 0);
    }

    /**
     * Determins oponent based on users choice from 2 players
     *
     * @param opponent1 - user input player
     * @param player1 - first available player in this match
     * @param player2 - second available player in this match
     * @return String- name of player who is opponent
     */
    private static String determineOpponent2(String opponent1, String player1, String player2) {
        String opponent2 = null;
        if (opponent1.equals(player1)) {
            opponent2 = player2;
        } else if (opponent1.equals(player2)) {
            opponent2 = player1;
        } else {
            System.out.println("Cannot choose from a different player than these two");
        }
        return opponent2;
    }

    /**
     * Checks if input group name is valid
     *
     * @param chosenGroup - users chosen input
     * @return true - is a group from the available groups, false- is not a
     * group
     */
    private static boolean isAnExistingGroup(String chosenGroup) {
        if (!chosenGroup.equals("a") && !chosenGroup.equals("b") && !chosenGroup.equals("c") && !chosenGroup.equals("d")) {
            System.out.println("No such group.");
            return false;
        }
        return true;
    }

    /**
     * Checks if input is a number
     *
     * @param inputToEnd - user input
     * @return true - is a number, false - isn't a number
     */
    private static boolean isANumber(String inputToEnd) {
        try {
            Integer.parseInt(inputToEnd);

        } catch (NumberFormatException e) {
            System.out.println("Input is not a valid integer");
            return false;
        }
        return true;
    }
}
