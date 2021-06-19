/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

/**
 *
 * @author VikyVixxxen
 */
public class Player implements Comparable<Player> {

    private String playerName;
    private String race;
    private String team;
    private String nationality;
    private int number;
    private int points;
    private LocalDate date;
    private LocalTime time;

    private int numOfWonMaps;
    private int numOfWins;

    static final Comparator<Player> COMP_NAME = (Player r1, Player r2) -> r1.playerName.compareTo(r2.playerName);
    static final Comparator<Player> COMP_WINS = (Player r1, Player r2) -> r2.numOfWins - r1.numOfWins;
    static final Comparator<Player> COMP_MAPSWON = (Player r1, Player r2) -> r2.numOfWonMaps - r1.numOfWonMaps;
    static final Comparator<Player> COMP_DATEANDTIME = Comparator.comparing(Player::getDate).thenComparing(Player::getTime);

    /**Constructor
     *
     * @param number
     * @param nationality
     * @param race
     * @param playerName
     * @param team
     * @param points
     */
    public Player(int number, String nationality, String race, String playerName, String team, int points) {
        this.playerName = playerName;
        this.race = race;
        this.team = team;
        this.nationality = nationality;
        this.number = number;
        this.points = points;
    }

    /**Returns date
     *
     * @return LocalDate -date
     */
    public LocalDate getDate() {
        return date;
    }

    /**Returns time
     *
     * @return LocalTime-time
     */
    public LocalTime getTime() {
        return time;
    }

    /**Set date
     *
     * @param date 
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**Set time
     *
     * @param time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**Returns number of won maps
     * default max integer
     * @return int- won maps
     */
    public int getNumOfWonMaps() {
        return numOfWonMaps;
    }

    /**Returns number of wins
     *
     * @return int -wins
     */
    public int getNumOfWins() {
        return numOfWins;
    }

    /**Set number of won maps
     *
     * @param numOfWonMaps
     */
    public void setNumOfWonMaps(int numOfWonMaps) {
        this.numOfWonMaps = numOfWonMaps;
    }

    /**Set number of wins
     *
     * @param numOfWins
     */
    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    /**Returns player name
     *
     * @return String- playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**Returns race
     *
     * @return String - race
     */
    public String getRace() {
        return race;
    }

    /**Returns team
     *
     * @return String - team
     */
    public String getTeam() {
        return team;
    }

    /**Returns points
     *
     * @return int - points
     */
    public int getPoints() {
        return points;
    }

    /**Returns nationality
     *
     * @return String - nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**Returns number of player
     *
     * @return int - number
     */
    public int getNumber() {
        return number;
    }

    /**prints information about the player
     *doesnt print wins or won maps until the players play a match
     * @return String - info
     */
    @Override
    public String toString() {
        String s = String.format("%3d %15s %10s %15s %30s %10d   ", number, nationality, race, playerName, team, points);      
        if (numOfWonMaps != Integer.MAX_VALUE) {
            s = s + String.format("Maps won: %d  Matchups won: %d", numOfWonMaps, numOfWins);
        }
        return s;
    }

    /**Comparable method , descending order of won maps
     *
     * @param t - player
     * @return int - order
     */
    @Override
    public int compareTo(Player t) {
        if (t.getNumOfWonMaps() == getNumOfWonMaps()) {
            return 0;
        } else if (t.getNumOfWonMaps() > getNumOfWonMaps()) {
            return 1;
        } else {
            return -1;
        }

    }
    
    /**testing main
     *
     * @param args
     */
    public static void main(String[] args) {
    Player p1 = new Player(17,"Czech Republic","Zerg","Unnamed","NotKnown",3000);
    Player p2 = new Player(12,"Romania","Terran","Unnamed2","NotKnown2",2758);
    p1.setNumOfWonMaps(5);
    p2.setNumOfWonMaps(12);
    System.out.println(p1.compareTo(p2));
        System.out.println(p1.toString());
        System.out.println(p2.toString());
    }
}
