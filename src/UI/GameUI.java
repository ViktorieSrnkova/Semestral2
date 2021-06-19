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
import utils.GameInterface;

/**
 *
 * @author VikyVixxxen
 */
public class GameUI {

    static Scanner sc = new Scanner(System.in);

    /**
     *
     * 
     * @param args
     */
    static GameInterface comp = new Game();
    static String path = "Data";

    public static void main(String[] args) {
        String inputToEnd;
        int bottomLimit;
        do {

            try {
                while (true) {
                    try {
                        loadListOfPlayers();
                        loadDatesAndTimes();
                        massSet();
                        comp.getPlayersSortedByName();
                        System.out.println("To display how players are separated into groups press Enter");
                        System.in.read();
                        comp.saveGroups(new File(path + File.separator + "Group Stage"), getAllGroups());
                        System.out.println("Choose one group with which you will play the group stage matches.");
                        bottomLimit = chooseOneGroup();
                        groupStage(bottomLimit);
                        System.out.println("Players competing for 4.th advancing position  ");
                        getFourth(bottomLimit);
                        System.out.println("To display advancing players from this group press Enter");
                        System.in.read();
                        advancingFromGroup(bottomLimit);
                        System.out.println("To display all advancing players press Enter");
                        System.in.read();
                        allAdvancingPlayers();
                        comp.saveGroupsResults(new File(path + File.separator + "GroupStageResults"));
                        System.out.println("To start climbing rounds press Enter");
                        System.in.read();
                        System.out.println(comp.pcPlayedRounds());
                        System.out.println("To see the final score press Enter");
                        System.in.read();
                        comp.sortedByWins();
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
                        System.out.println("This file doesn´t exist");
                    } catch (NoSuchElementException e) {
                        System.out.println("Player with this name doesn´t exist");
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
//Metody
    /**loads list of players
     * 
     * @throws IOException 
     */
    private static void loadListOfPlayers() throws IOException {
        System.out.println("List of players:");
        comp.loadPlayerInfo(new File(path + File.separator + "player_info"));
        System.out.println(comp.getPlayers());
    }
/**Loads dates and times
 * 
 * @throws IOException 
 */
    private static void loadDatesAndTimes() throws IOException {
        comp.loadDatesAndTimes(new File(path + File.separator + "DateTime"));

    }
/**mass sets all to be mass set
 * 
 */
    private static void massSet() {
        comp.setMassMapWon();
        comp.setMassMatchupWon();
        comp.setMassDefaultTime();
    }
/**User chooses a group,check if correct input
 * 
 * @return -botom limit of sublist to print out a group
 */
    private static int chooseOneGroup() {
        String chosenGroup;
        do {
            chosenGroup = sc.next().toLowerCase();
        } while (!isAnExistingGroup(chosenGroup));
        return comp.chooseGroup(chosenGroup);
    }
/**Separates players into groups and prints them, also remembers the groups themselves
 * 
 * @return remembered groups
 */
    private static String getAllGroups() {
        String groups = comp.separate();
        System.out.println(groups);
        return groups;
    }
/**Group stage battles between a pc and the user
 * 
 * @param bottomLimit -botom limit of sublist to print out a group
 * @throws IOException 
 */
    private static void groupStage(int bottomLimit) throws IOException {
        int poolOfPlayers = 6;
        String opponent2;
        String opponent1;
        while (poolOfPlayers > 0) {
            System.out.println(comp.selectGroup(bottomLimit, bottomLimit + 6));
            comp.loadMaps(new File(path + File.separator + "Maps"));
            System.out.println("Choose two players that will play against each other. Enter their names.");
            String player1 = sc.next();
            while (checkName(player1) == false)  player1 = sc.next();
            while (checkIfHasntPlayedAlready(player1) == false) player1 = sc.next();
            String player2 = sc.next();
            while (checkName(player2) == false)  player2 = sc.next();
            while (checkIfHasntPlayedAlready(player2) == false)player2 = sc.next();
            System.out.println(comp.createMatch(player1, player2));
            poolOfPlayers = poolOfPlayers - 2;
            System.out.println("Chose a player for whom you wish to throw dice");
            do {
                opponent1 = sc.next();
                while (checkName(opponent1) == false) opponent1 = sc.next();
                opponent2 = determineOpponent2(opponent1, player1, player2);
            } while (opponent2 == null);
            gameOfDice(opponent1, opponent2);
            assignWonGames(opponent1, opponent2);
        }
    }
/**computer throws a dice ,user throws a dice , assigning wins to players
 * 
 * @param opponent1
 * @param opponent2 
 */
    private static void gameOfDice(String opponent1, String opponent2) {
        int n = 0;
        int m = 0;
        System.out.println("Score " + opponent1 + "  " + n + "  :  " + m + "  " + opponent2);
        while (m < 2 && n < 2) {
            System.out.println("***********************");
            System.out.println("Map: " + comp.chooseRandomMap());
            System.out.println("Enter a number between 1 to 6");
            int diced = checkNumInputInRange();
            int pcDiced = comp.pcThrowsDice();
            if (diced > pcDiced) {
                n = n + 1;
                assignWonMaps(opponent2, opponent1, m, n, pcDiced);
                System.out.println("Score " + opponent1 + "  " + comp.findByPlayerName(opponent1).getNumOfWonMaps() + " : " + comp.findByPlayerName(opponent2).getNumOfWonMaps() + "  " + opponent2);
            } else if (diced < pcDiced) {
                m = m + 1;
                assignWonMaps(opponent2, opponent1, m, n, pcDiced);
                System.out.println("Score " + opponent2 + "  " + comp.findByPlayerName(opponent2).getNumOfWonMaps() + " : " + comp.findByPlayerName(opponent1).getNumOfWonMaps() + "  " + opponent1);
            } else {
                System.out.println("Computer threw " + pcDiced);
                System.out.println("################## Tie Throw again ###################");
            }
        }
    }
/**assigns won maps accordingly to who won game of dice
 * 
 * @param opponent2
 * @param opponent1
 * @param m -number of won games of dice for opponent 2
 * @param n -number of won games of dice for opponent1
 * @param pcDiced - number that program threw on dice
 */
    private static void assignWonMaps(String opponent2, String opponent1, int m, int n, int pcDiced) {
        comp.findByPlayerName(opponent2).setNumOfWonMaps(m);
        comp.findByPlayerName(opponent1).setNumOfWonMaps(n);
        System.out.println("Computer threw " + pcDiced);
    }
/**Assigns won games accordingly to won maps
 * 
 * @param opponent2
 * @param opponent1 
 */
    private static void assignWonGames(String opponent2, String opponent1) {
        if (comp.findByPlayerName(opponent2).getNumOfWonMaps() > comp.findByPlayerName(opponent1).getNumOfWonMaps()) {
            comp.findByPlayerName(opponent2).setNumOfWins(1);
            comp.findByPlayerName(opponent1).setNumOfWins(0);
        } else {
            comp.findByPlayerName(opponent1).setNumOfWins(1);
            comp.findByPlayerName(opponent2).setNumOfWins(0);
        }
    }
/**checks if number is a number or in range
 * 
 * @return - false if out of range or not a number, true if in range
 */
    private static int checkNumInputInRange() {
        Pattern p = Pattern.compile("(^[1-6]{1}$)");
        String input = sc.next();
        int diced;
        Matcher match = p.matcher(input);
        while (!match.find()) {
            System.out.println("Not in range! Try again");
            input = sc.next();
            match = p.matcher(input);
        }
        diced = Integer.parseInt(input);
        return diced;
    }
/**Checks if name of said player is spelled correctly
 * 
 * @param player1 - said player
 * @return - true if it is correct, false if missspeled
 */
    private static boolean checkName(String player1) {
        while (!comp.isValidName(player1)) {
            System.out.println("Name " + player1 + " is incorrect. Try again");
            return false;
        }
        return true;
    }
/**Checks if said players hasnt already played a match in groupstage
 * 
 * @param player1 -said player
 * @return true if hasnt played , false if has played
 */
    private static boolean checkIfHasntPlayedAlready(String player1) {
        while (comp.findByPlayerName(player1).getNumOfWonMaps() != Integer.MAX_VALUE) {
            System.out.println(" " + player1 + "  recently played choose a different player");
            return false;
        }
        return true;
    }
/**Sorts full group prints only players competing for fourth place and then determines fourth place
 * 
 * @param bottomLimit -botom limit of sublist to print out a group
 */
    private static void getFourth(int bottomLimit) {
        Collections.sort(comp.groupToSort(bottomLimit, bottomLimit + 6));
        System.out.println(comp.selectGroup(bottomLimit + 3, bottomLimit + 6));
        comp.determineFourth(bottomLimit + 3, bottomLimit + 6);
    }
/**Creates sorts by date and time and prints list of all advancing players
 * 
 */
    private static void allAdvancingPlayers() {
        comp.createListOfAllAdvancing();
        comp.sortByDateAndTime();
        System.out.println(comp.listOfAllAdvancingPlayers());
    }
/**Sorts players in group in descending order based on maps won and prints list or advancing players(those who have at least one won matchup)
 * 
 * @param bottomLimit -botom limit of sublist to print out a group
 */
    private static void advancingFromGroup(int bottomLimit) {
        comp.getGroupsSortedByMapsWon(bottomLimit, bottomLimit + 6);
        System.out.println(comp.listOfAdvancingFromGroup(bottomLimit, bottomLimit + 4));
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
            System.out.println("No such group. Try again");
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
