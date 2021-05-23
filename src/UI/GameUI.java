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

    /**
     * @param args the command line arguments
     */
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String path = "Data";
        int inputToEnd;
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
                        String groups = comp.separate();
                        System.out.println(groups);
                        comp.saveGroups(new File(path + File.separator + "Group Stage"), groups);
                        System.out.println("Choose one group with which you will play the group stage matches.");
                        String chosenGroup = sc.next().toLowerCase();
                        while (!chosenGroup.equals("a") && !chosenGroup.equals("b") && !chosenGroup.equals("c") && !chosenGroup.equals("d")) {
                            System.out.println("No such group.");
                            chosenGroup = sc.next().toLowerCase();
                        }
                        int bottomLimit = Integer.MAX_VALUE, topLimit = Integer.MAX_VALUE;
                        switch (chosenGroup) {
                            case "a":
                            case "group a":
                                bottomLimit = 0;
                                topLimit = 6;
                                System.out.println(comp.pcPlayedGroups(bottomLimit + 6, topLimit + 6));
                                System.out.println(comp.pcPlayedGroups(bottomLimit + 12, topLimit + 12));
                                System.out.println(comp.pcPlayedGroups(bottomLimit + 18, topLimit + 18));
                                break;
                            case "b":
                            case "group b":
                                bottomLimit = 6;
                                topLimit = 12;
                                System.out.println(comp.pcPlayedGroups(bottomLimit - 6, topLimit - 6));
                                System.out.println(comp.pcPlayedGroups(bottomLimit + 6, topLimit + 6));
                                System.out.println(comp.pcPlayedGroups(bottomLimit + 12, topLimit + 12));
                                break;
                            case "c":
                            case "group c":
                                bottomLimit = 12;
                                topLimit = 18;
                                comp.pcPlayedGroups(bottomLimit - 12, topLimit - 12);
                                comp.pcPlayedGroups(bottomLimit - 6, topLimit - 6);
                                comp.pcPlayedGroups(bottomLimit + 6, topLimit + 6);
                                break;
                            case "d":
                            case "group d":
                                bottomLimit = 18;
                                topLimit = 24;
                                comp.pcPlayedGroups(bottomLimit - 18, topLimit - 18);
                                comp.pcPlayedGroups(bottomLimit - 12, topLimit - 12);
                                comp.pcPlayedGroups(bottomLimit - 6, topLimit - 6);
                                break;
                        }
                        int poolOfPlayers = 6;
                        while (poolOfPlayers > 0) {
                            System.out.println(comp.selectGroup(bottomLimit, topLimit));
                            comp.loadMaps(new File(path + File.separator + "Maps"));
                            System.out.println("Choose two players that will play against each other. Enter their names.");
                            String s = sc.next();
                            String s2 = sc.next();

                            while (comp.isValidName(s) == false || comp.isValidName(s2) == false) {
                                if (comp.isValidName(s) == false) {
                                    System.out.println("Name " + s + " is incorrect.");
                                    s = sc.next();
                                } else {
                                    System.out.println("Name " + s2 + " is incorrect.");
                                    s2 = sc.next();
                                }
                            }
                            while (comp.findByPlayerName(s).getNumOfWonMaps() != Integer.MAX_VALUE || comp.findByPlayerName(s2).getNumOfWonMaps() != Integer.MAX_VALUE) {
                                if (comp.findByPlayerName(s).getNumOfWonMaps() != Integer.MAX_VALUE) {
                                    System.out.println(" " + s + "  recently played choose a different player");
                                    s = sc.next();
                                } else {
                                    System.out.println(" " + s2 + "  recently played choose a different player");
                                    s2 = sc.next();
                                }
                            }
                            System.out.println(comp.createMatch(s, s2));
                            poolOfPlayers = poolOfPlayers - 2;
                            System.out.println("Chose a player for whom you wish to throw dice");
                            String opponent1 = sc.next();
                            while (comp.isValidName(opponent1) == false) {
                                System.out.println("Name " + opponent1 + " is incorrect.");
                                opponent1 = sc.next();
                            }
                            String opponent2;
                            if (opponent1.equals(s)) {
                                opponent1 = s;
                                opponent2 = s2;
                            } else {
                                opponent1 = s2;
                                opponent2 = s;
                            }
                            int n = 0;
                            int m = 0;
                            String input;
                            int pcDiced;
                            int diced;
                            System.out.println("Score " + opponent1 + "  " + comp.findByPlayerName(opponent1).getNumOfWonMaps() + " : " + comp.findByPlayerName(opponent2).getNumOfWonMaps() + "  " + opponent2);
                            while (m < 2 && n < 2) {
                                System.out.println("***********************");
                                System.out.println("Map: " + comp.chooseRandomMap());
                                System.out.println("Enter a number between 1 to 6");
                                Pattern p = Pattern.compile("(^[1-6]{1}$)");
                                input = sc.next();
                                Matcher match = p.matcher(input);
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
                        Collections.sort(comp.groupToSort(bottomLimit, topLimit));
                        System.out.println(comp.selectGroup(bottomLimit + 3, topLimit));
                        comp.determineFourth(bottomLimit + 3, topLimit);
                        System.out.println("To display dvancing players from this group press Enter");
                        System.in.read();
                        comp.getGroupsSortedByMapsWon(bottomLimit, topLimit);
                        System.out.println(comp.listOfAdvancingFromGroup(bottomLimit, topLimit - 2));
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
                        System.out.println(comp.getPlayers());
                        comp.saveFinalScore(new File(path + File.separator + "Final Results"));
                        System.out.println("And the winner is:  " + comp.DisplayWinner());
                        playMusic("Music\\\\cheer.wav");
                        System.out.println("To display the winning table press Enter");
                        System.in.read();
                        System.out.println(comp.displayTable());
                        comp.saveWinningTable(new File(path + File.separator + "Winning Table"));
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
            inputToEnd = sc.nextInt();
        } while (inputToEnd > 0);
    }
}
