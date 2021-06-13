/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import app.Player;

/**
 *
 * @author VikyVixxxen
 */
public interface GameInterface {

    /** Loads file with the information about players separated by "," and saves information to its corresponding place, assings each player in a arrayList to their information
     * 
     * 
     * @param filename -where it can find the file to be loaded
     * @throws FileNotFoundException     
     * @throws IOException              
     */
    public void loadPlayerInfo(File filename) throws FileNotFoundException, IOException;

    /**Loads file with the names of maps from the game and saves them into an arrayList
     *
     * @param filename - where it can find the file to be loaded
     * @throws FileNotFoundException   
     * @throws IOException            
     */
    public void loadMaps(File filename) throws FileNotFoundException, IOException;

    /**Loads file with dates and times and saves them into arrayList
     *
     * @param filename -where it can find the file to be loaded
     * @throws FileNotFoundException    
     * @throws IOException             
     */
    public void loadDatesAndTimes(File filename) throws FileNotFoundException, IOException;

    /**Sets 0 to the amount of Wins to each player. Resets all to 0.
     *
     */
    public void setMassMatchupWon();

    /**Sets 0 to the amount of WonMaps to each player. Resets all to 0.
     *
     */
    public void setMassMapWon();

    /**Sets default time from file to all players.
     *
     */
    public void setMassDefaultTime();

    /**Randomly and without repetition chooses map from the arrayList they are stored in for players to play on.
     *
     * @return String  -map
     */
    public String chooseRandomMap();

    /**Randomly and without repetition chooses date from the arrayList they are stored in.
     *
     * @return LocalDate - date
     */
    public LocalDate chooseRandomDateFromList();

    /**Checks if input name of a player is correctly spelled.
     *
     * @param name -user input
     * @return Boolean - true if name is correct, false if name is incorrect
     * 
     */
    public boolean isValidName(String name);

    /**Sorts players aplhabetically by their name.
     *
     * @return String  -list of players sorted by name
     */
    public String getPlayersSortedByName();

    /**Sorts players inside a group in descending order based on how many maps they have won.
     *
     * @param i -bottom range of sublist creating that specific group
     * @param y -top range if sublist creating that specific group
     * @return String  -list of players sorted by won maps in descending order
     */
    public String getGroupsSortedByMapsWon(int i, int y);

    /**Sorts players in descending order based on how many matchups they have won.
     *
     */
    public void sortedByWins();

    /**Sorts players in asending order based on dates and times of begining their games in group stage.
     *
     */
    public void sortByDateAndTime();

    /**Creates a sublist from range and prints only that range of players
     *
     * @param i -bottom range - index from list of players list to create wanted group
     * @param y -top range - index from list of players list to create wanted group
     * @return String  -players in that range
     */
    public String selectGroup(int i, int y);

    /**Selects one group
     *
     *  @param i -bottom range - index from list of players list to select wanted group
     * @param y -top range - index from list of players list to select wanted group
     * @return Sublist - of those players
     */
    public List<Player> groupToSort(int i, int y);

    /**Finds player name in list of players and returns the player.
     *
     * @param playerName -name of the wanted player 
     * @return Player - player
     */
    public Player findByPlayerName(String playerName);
    
    /**Decides what group the user will work with and the rest will be worked on by the program based on user input of the corresponding letter of the group.
     *
     * @param choice  - user input letter
     * @return int - bottom limit of that user chosen sublist.
     */
    public int chooseGroup(String choice);

    /**Separated players into groups of 6 and asigns them the random date (each group one date)
     *
     * @return String  -list of groups of players with their dates
     */
    public String separate();

    /**determins fourth player to continue to next rounds
     *
     * @param i -bottom limit of the sublist of the group
     * @param y -top limit of the sublist of the group
     */
    public void determineFourth(int i, int y);

    /**Program plays the game with the rest of the groups
     *
     * @param i -bottom limit of the sublist of the group
     * @param y -top limit of the sublist of the group
     * @return String  -list of advancing players of those groups
     */
    public String pcPlayedGroups(int i, int y);

    /**Program throws a dice.
     *
     * @return int -number in range of 1-6
     */
    public int pcThrowsDice();

    /**Creates a match between two players.
     *
     * @param name1 -user chosen first name 
     * @param name2 -user chosen second name
     * @return String - displays the two players on the screen
     */
    public String createMatch(String name1, String name2);

    /**Program plays the rest of the competition with the advancing players fromm group stage (16 players from 24)
     *also prints out individual player matches and the player who won, waits for player to read the information and press enter when finished reading.
     * @return String  -list of 16 players with their updated wins
     * @throws IOException
     */
    public String pcPlayedRounds() throws IOException;

    /**Creates list of advancing players from the user played group
     *
     * @param i -bottom limit of the sublist of the group
     * @param y -top limit of the sublist of the group
     * @return String -list of only advancing players from that group(4 players from 6)
     */
    public String listOfAdvancingFromGroup(int i, int y);

    /**Crates list of all advancing players and saves them to a different arrayList
     *
     * @return returns arrayList of advancing players
     */
    public List<Player> createListOfAllAdvancing();

    /**prints list of all advancing players.
     *
     * @return String -list of all advancing players
     */
    public String listOfAllAdvancingPlayers();

    /**Prints players with displayed dates and times from group stage.
     *
     * @return String -list of all players with their dates and times from group stage
     */
    public String getDatesAndTimes();

    /**Prints all players 
     *
     * @return String - list of all players
     */
    public String getPlayers();

    /**Prints all maps
     *
     * @return String - list of all maps
     */
    public String getMaps();

    /**Displays only the player with the most won matchups
     *
     * @return String- player with most wins
     */
    public String DisplayWinner();

    /**Displays leaderboard
     *
     * @return String - leaderboard
     */
    public String displayTable();

    /** Creates a text file and saves the leaderboard to it
     *
     * @param filename - name of the file
     * @throws IOException
     */
    public void saveLeaderBoard(File filename) throws IOException;

    /**Creates a text file and saves all advancing players with their dates and times to it
     *
     * @param filename - name of the file
     * @throws IOException
     */
    public void saveGroupsResults(File filename) throws IOException;

    /**Creates a text file and saves groups of players into it
     *
     * @param filename - name of the file
     * @param groups -list of separated players into groups
     * @throws IOException
     */
    public void saveGroups(File filename, String groups) throws IOException;

    /**Creates a text file and saves the final score list into it
     *
     * @param filename - name of the file
     * @throws IOException
     */
    public void saveFinalScore(File filename) throws IOException;
}
