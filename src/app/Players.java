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
public class Players implements Comparable<Players> {

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

    static final Comparator<Players> COMP_NAME = (Players r1, Players r2) -> r1.playerName.compareTo(r2.playerName);
    static final Comparator<Players> COMP_WINS = (Players r1, Players r2) -> r2.numOfWins - r1.numOfWins;
    static final Comparator<Players> COMP_MAPSWON = (Players r1, Players r2) -> r2.numOfWonMaps - r1.numOfWonMaps;
    static final Comparator<Players> COMP_DATEANDTIME = Comparator.comparing(Players::getDate).thenComparing(Players::getTime);

    public Players(int number, String nationality, String race, String playerName, String team, int points) {
        this.playerName = playerName;
        this.race = race;
        this.team = team;
        this.nationality = nationality;
        this.number = number;
        this.points = points;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getNumOfWonMaps() {
        return numOfWonMaps;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWonMaps(int numOfWonMaps) {
        this.numOfWonMaps = numOfWonMaps;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRace() {
        return race;
    }

    public String getTeam() {
        return team;
    }

    public int getPoints() {
        return points;
    }

    public String getNationality() {
        return nationality;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        String s = String.format("%3d %15s %10s %15s %30s %10d   ", number, nationality, race, playerName, team, points);      
        if (numOfWonMaps != Integer.MAX_VALUE) {
            s = s + String.format("Maps won: %d  Matchups won: %d", numOfWonMaps, numOfWins);
        }
        return s;
    }

    @Override
    public int compareTo(Players t) {
        if (t.getNumOfWonMaps() == getNumOfWonMaps()) {
            return 0;
        } else if (t.getNumOfWonMaps() > getNumOfWonMaps()) {
            return 1;
        } else {
            return -1;
        }

    }
    
    public static void main(String[] args) {
    Players p1 = new Players(17,"Czech Republic","Zerg","Unnamed","NotKnown",3000);
    Players p2 = new Players(12,"Romania","Terran","Unnamed2","NotKnown2",2758);
    p1.setNumOfWonMaps(5);
    p2.setNumOfWonMaps(12);
    System.out.println(p1.compareTo(p2));
        System.out.println(p1.toString());
        System.out.println(p2.toString());
    }
}
