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
import app.Players;

/**
 *
 * @author VikyVixxxen
 */
public interface GameInterface {

    public void loadPlayerInfo(File filename) throws FileNotFoundException, IOException;

    public void loadMaps(File filename) throws FileNotFoundException, IOException;

    public void loadDatesAndTimes(File filename) throws FileNotFoundException, IOException;

    public void setMassMatchupWon();

    public void setMassMapWon();

    public void setMassDefaultTime();

    public String chooseRandomMap();

    public LocalDate chooseRandomDateFromList();

    public boolean isValidName(String jmeno);

    public String getPlayersSortedByName();

    public String getGroupsSortedByMapsWon(int i, int y);

    public void sortedByWins();

    public void sortByDateAndTime();

    public String selectGroup(int i, int y);

    public List<Players> groupToSort(int i, int y);

    public Players findByPlayerName(String playerName);
    
    public int chooseGroup(String choice);

    public String separate();

    public void determineFourth(int i, int y);

    public String pcPlayedGroups(int i, int y);

    public int pcThrowsDice();

    public String createMatch(String name1, String name2);

    public String pcPlayedRounds() throws IOException;

    public String listOfAdvancingFromGroup(int i, int y);

    public List<Players> createListOfAllAdvancing();

    public String listOfAllAdvancingPlayers();

    public String getDatesAndTimes();

    public String getPlayers();

    public String getMaps();

    public String DisplayWinner();

    public String displayTable();

    public void saveWinningTable(File filename) throws IOException;

    public void saveGroupsResults(File filename) throws IOException;

    public void saveGroups(File filename, String skupiny) throws IOException;

    public void saveFinalScore(File filename) throws IOException;
}
