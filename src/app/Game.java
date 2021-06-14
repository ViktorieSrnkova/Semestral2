/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import utils.GameInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static app.Player.COMP_DATEANDTIME;
import static app.Player.COMP_MAPSWON;
import static app.Player.COMP_NAME;
import static app.Player.COMP_WINS;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author VikyVixxxen
 */
public class Game implements GameInterface {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
    static DateTimeFormatter ttf = DateTimeFormatter.ofPattern("HH:mm");
    private List<Player> players;
    private List<Player> players16;
    private List<Maps> maps;
    private List<DateTime> dateTime;

    /**
     *
     */
    public Game() {
        players = new ArrayList<>();
        players16 = new ArrayList<>();
        dateTime = new ArrayList<>();
        maps = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadPlayerInfo(File filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String[] parts;
            String race, playerName, team, nationality;
            int number;
            int points;
            Player p;
            br.readLine();
            br.readLine();
            while ((line = br.readLine()) != null) {
                parts = line.split(","); //1,South Korea,Terran,TY,Afreeca Freecs,3162
                race = parts[2];
                playerName = parts[3];
                team = parts[4];
                nationality = parts[1];
                number = Integer.parseInt(parts[0]);
                points = Integer.parseInt(parts[5]);
                p = new Player(number, nationality, race, playerName, team, points);
                players.add(p);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadMaps(File filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String[] parts;
            String map;
            Maps m;
            br.readLine();
            while ((line = br.readLine()) != null) {
                parts = line.split(",");
                map = parts[0];
                m = new Maps(map);
                maps.add(m);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDatesAndTimes(File filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String[] parts;
            LocalDate date;
            LocalTime time;
            DateTime t;
            while ((line = br.readLine()) != null) {
                parts = line.split(" ");
                date = LocalDate.parse(parts[0], dtf);
                time = LocalTime.parse(parts[1], ttf);
                t = new DateTime(date, time);
                dateTime.add(t);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMassMatchupWon() {
        for (Player p : players) {
            p.setNumOfWins(Integer.MAX_VALUE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMassMapWon() {
        for (Player p : players) {
            p.setNumOfWonMaps(Integer.MAX_VALUE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMassDefaultTime() {
        for (Player p : players) {
            p.setTime(dateTime.get(0).getTime());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String chooseRandomMap() {
        Random rn = new Random();
        int upperbound = maps.size();
        int random = rn.nextInt(upperbound);
        String map = maps.get(random).toString();
        maps.remove(random);
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate chooseRandomDateFromList() {
        Random rn = new Random();
        int upperbound = dateTime.size();
        int random = rn.nextInt(upperbound);
        LocalDate date = dateTime.get(random).getDate();
        dateTime.remove(random);
        return date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidName(String name) {
        for (Player p : players) {
            if (p.getPlayerName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayersSortedByName() {
        Collections.sort(players, COMP_NAME);
        return getPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGroupsSortedByMapsWon(int i, int y) {
        Collections.sort(players.subList(i, y), COMP_MAPSWON);
        return selectGroup(i, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortedByWins() {
        Collections.sort(players16, COMP_WINS);
        Collections.sort(players, COMP_WINS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortByDateAndTime() {
        Collections.sort(players16, COMP_DATEANDTIME);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String selectGroup(int i, int y) {
        StringBuilder sb = new StringBuilder();
        for (Player p : players.subList(i, y)) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> groupToSort(int i, int y) {
        return players.subList(i, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player findByPlayerName(String playerName) {
        for (Player p : players) {
            if (p.getPlayerName().equals(playerName)) {
                return p;
            }
        }
        throw new NoSuchElementException("Player with the name " + playerName + " does not exist");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int chooseGroup(String choice) {
        int bottomLimit = Integer.MAX_VALUE;
        switch (choice) {
            case "a":
            case "group a":
                bottomLimit = 0;
                pcPlayedGroups(bottomLimit + 6, bottomLimit + 12);
                pcPlayedGroups(bottomLimit + 12, bottomLimit + 18);
                pcPlayedGroups(bottomLimit + 18, bottomLimit + 24);
                break;
            case "b":
            case "group b":
                bottomLimit = 6;
                pcPlayedGroups(bottomLimit - 6, bottomLimit);
                pcPlayedGroups(bottomLimit + 6, bottomLimit + 12);
                pcPlayedGroups(bottomLimit + 12, bottomLimit + 18);
                break;
            case "c":
            case "group c":
                bottomLimit = 12;
                pcPlayedGroups(bottomLimit - 12, bottomLimit - 6);
                pcPlayedGroups(bottomLimit - 6, bottomLimit);
                pcPlayedGroups(bottomLimit + 6, bottomLimit + 12);
                break;
            case "d":
            case "group d":
                bottomLimit = 18;
                pcPlayedGroups(bottomLimit - 18, bottomLimit - 12);
                pcPlayedGroups(bottomLimit - 12, bottomLimit - 6);
                pcPlayedGroups(bottomLimit - 6, bottomLimit);
                break;
        }

        return bottomLimit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String separate() {
        LocalDate A = chooseRandomDateFromList();
        LocalDate B = chooseRandomDateFromList();
        LocalDate C = chooseRandomDateFromList();
        LocalDate D = chooseRandomDateFromList();
        int n = 0;
        int rn = ThreadLocalRandom.current().nextInt(30, 90 + 1);
        StringBuilder sb = new StringBuilder();
        sb.append("Group A  ").append(A).append("\n");
        for (Player p : players.subList(0, 6)) {
            p.setDate(A);
            p.setTime(p.getTime().plusMinutes((int) n));
            n = n + rn;
            sb.append(p).append("\n");
        }
        sb.append("Group B  ").append(B).append("\n");
        n = 0;
        for (Player p : players.subList(6, 12)) {
            p.setDate(B);
            p.setTime(p.getTime().plusMinutes((int) n));
            n = n + rn;
            sb.append(p).append("\n");
        }
        sb.append("Group C  ").append(C).append("\n");
        n = 0;
        for (Player p : players.subList(12, 18)) {
            p.setDate(C);
            p.setTime(p.getTime().plusMinutes((int) n));
            n = n + rn;
            sb.append(p).append("\n");
        }
        sb.append("Group D  ").append(D).append("\n");
        n = 0;
        for (Player p : players.subList(18, 24)) {
            p.setDate(D);
            p.setTime(p.getTime().plusMinutes((int) n));
            n = n + rn;
            sb.append(p).append("\n");
        }
        return sb.toString();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void determineFourth(int i, int y) {
        int counter1 = 0;
        int counter0 = 0;
        int a;
        int b;
        int c;
        for (Player p : players.subList(i, y)) {
            if (p.getNumOfWonMaps() == 1) {
                counter1++;
            } else if (p.getNumOfWonMaps() == 0) {
                counter0++;
            }
        }
        if (counter1 == 1) {
            players.subList(i, y).get(0).setNumOfWins(1);
        } else if (counter1 == 2) {
            a = pcThrowsDice();
            b = pcThrowsDice();
            while (a == b) {
                a = pcThrowsDice();
                b = pcThrowsDice();
            }
            int higherTwoWayTie = Math.max(a, b);
            if (higherTwoWayTie == a) {
                players.subList(i, y).get(0).setNumOfWonMaps(players.subList(i, y).get(0).getNumOfWonMaps() + 1);
                players.subList(i, y).get(0).setNumOfWins(1);
            } else if (higherTwoWayTie == b) {
                players.subList(i, y).get(1).setNumOfWonMaps(players.subList(i, y).get(1).getNumOfWonMaps() + 1);
                players.subList(i, y).get(1).setNumOfWins(1);
            }
        } else if (counter1 == 3 || counter0 == 3) {
            a = pcThrowsDice();
            b = pcThrowsDice();
            c = pcThrowsDice();
            while (a == b || b == c || a == c) {
                a = pcThrowsDice();
                b = pcThrowsDice();
                c = pcThrowsDice();
            }
            int higherThreeWayTIe = Math.max(Math.max(a, b), Math.max(c, b));
            if (higherThreeWayTIe == a) {
                players.subList(i, y).get(0).setNumOfWonMaps(players.subList(i, y).get(0).getNumOfWonMaps() + 2);
                players.subList(i, y).get(0).setNumOfWins(1);
            } else if (higherThreeWayTIe == b) {
                players.subList(i, y).get(1).setNumOfWonMaps(players.subList(i, y).get(1).getNumOfWonMaps() + 2);
                players.subList(i, y).get(1).setNumOfWins(1);
            } else {
                players.subList(i, y).get(2).setNumOfWonMaps(players.subList(i, y).get(2).getNumOfWonMaps() + 2);
                players.subList(i, y).get(2).setNumOfWins(1);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String pcPlayedGroups(int i, int y) {
        int n = 0;
        int m = 5;
        int a, b, x, z;
        for (int j = 0; j < 3; j++) {
            players.subList(i, y).get(n);
            players.subList(i, y).get(m);
            z = 0;
            x = 0;
            while (x < 2 && z < 2) {
                a = pcThrowsDice();
                b = pcThrowsDice();
                while (a == b) {
                    a = pcThrowsDice();
                    b = pcThrowsDice();
                }
                if (a > b) {
                    x = x + 1;
                    players.subList(i, y).get(n).setNumOfWonMaps(x);
                    players.subList(i, y).get(m).setNumOfWonMaps(z);
                } else if (b > a) {
                    z = z + 1;
                    players.subList(i, y).get(m).setNumOfWonMaps(z);
                    players.subList(i, y).get(n).setNumOfWonMaps(x);
                }
            }
            if (x > z) {
                players.subList(i, y).get(n).setNumOfWins(1);
                players.subList(i, y).get(m).setNumOfWins(0);
            } else {
                players.subList(i, y).get(m).setNumOfWins(1);
                players.subList(i, y).get(n).setNumOfWins(0);
            }
            n = n + 1;
            m = m - 1;
        }
        getGroupsSortedByMapsWon(i, y);
        determineFourth(i + 3, y);
        getGroupsSortedByMapsWon(i, y);
        return listOfAdvancingFromGroup(i, y - 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int pcThrowsDice() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        return randomNum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createMatch(String name1, String name2) {
        StringBuilder sb = new StringBuilder();
        sb.append(findByPlayerName(name1)).append("\n");
        sb.append(findByPlayerName(name2)).append("\n");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String pcPlayedRounds() throws IOException {
        StringBuilder sb = new StringBuilder();
        Player player1;
        Player player2;
        int a, b, x, z;
        double repeating = 8;
        int range = 16;
        while (repeating >= 1) {
            ArrayList<Integer> list = new ArrayList<>(range);
            for (int j = 0; j < range; j++) {
                list.add(j);
            }
            Random rand = new Random();
            sb.append("*************Round of ").append(range).append(" ****************").append("\n");

            while (list.size() > 0) {
                int index = rand.nextInt(list.size());
                int index2 = rand.nextInt(list.size());
                while (index == index2) {
                    index = rand.nextInt(list.size());
                    index2 = rand.nextInt(list.size());
                }
                player1 = players16.get(list.get(index));
                sb.append(String.format("%10s",player1.getPlayerName())).append("   :   ");
                player2 = players16.get(list.get(index2));
                sb.append(String.format("%10s",player2.getPlayerName()));
                z = 0;
                x = 0;
                while (x < 2 && z < 2) {
                    a = pcThrowsDice();
                    b = pcThrowsDice();
                    while (a == b) {
                        a = pcThrowsDice();
                        b = pcThrowsDice();
                    }
                    if (a > b) {
                        x = x + 1;
                        player1.setNumOfWonMaps(player1.getNumOfWonMaps() + 1);
                    } else if (b > a) {
                        z = z + 1;
                        player2.setNumOfWonMaps(player2.getNumOfWonMaps() + 1);
                    }
                }
                if (x > z) {
                    player1.setNumOfWins(player1.getNumOfWins() + 1);
                    sb.append(String.format("    Winner -->  %10s", player1.getPlayerName())).append("\n");
                } else if (z > x) {
                    player2.setNumOfWins(player2.getNumOfWins() + 1);
                    sb.append(String.format("    Winner -->  %10s", player2.getPlayerName())).append("\n");
                }
                if (index > index2) {
                    list.remove(index);
                    list.remove(index2);
                } else if (index2 > index) {
                    list.remove(index2);
                    list.remove(index);
                }
            }
            repeating = repeating / 2;
            range = range / 2;
            sortedByWins();
          // sb.append("\n").append(listOfAllAdvancingPlayers());
           
        }
        sortedByWins();
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String listOfAdvancingFromGroup(int i, int y) {
        StringBuilder sb = new StringBuilder();
        for (Player p : players.subList(i, y)) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> createListOfAllAdvancing() {
        for (Player p : players.subList(0, 4)) {
            players16.add(p);
        }
        for (Player p : players.subList(6, 10)) {
            players16.add(p);
        }
        for (Player p : players.subList(12, 16)) {
            players16.add(p);
        }
        for (Player p : players.subList(18, 22)) {
            players16.add(p);
        }
        return players16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String listOfAllAdvancingPlayers() {
        StringBuilder sb = new StringBuilder();
        for (Player p : players16) {
            sb.append(p).append(" ").append(p.getDate()).append(" ").append(p.getTime()).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDatesAndTimes() {
        StringBuilder sb = new StringBuilder();
        for (DateTime d : dateTime) {
            sb.append(d).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayers() {
        StringBuilder sb = new StringBuilder();
        for (Player p : players) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaps() {
        StringBuilder sb = new StringBuilder();
        for (Maps m : maps) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String DisplayWinner() {
        StringBuilder sb = new StringBuilder();
        sb.append(players16.get(0).getPlayerName()).append("\n");
        sb.append(players16.get(0)).append("\n");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String displayTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("_______________________________________________").append("\n");
        sb.append("    place           ").append("| ").append("Winnings").append("  |  ").append("Player").append("\n");
        sb.append("_______________________________________________").append("\n");
        sb.append("1.st place          ").append("|  ").append("$65,000").append("  |  ").append(players.get(0).getPlayerName()).append("\n");
        sb.append("_______________________________________________").append("\n");
        sb.append("2.nd place          ").append("|  ").append("$32,500").append("  |  ").append(players.get(1).getPlayerName()).append("\n");
        sb.append("_______________________________________________").append("\n");
        sb.append("3.rd & 4.th place   ").append("|  ").append("$17,500").append("  |  ").append(players.get(2).getPlayerName()).append("\n");
        sb.append("                    ").append("|  ").append("$17,500").append("  |  ").append(players.get(3).getPlayerName()).append("\n");
        sb.append("_______________________________________________").append("\n");
        sb.append("5.th & 8.th place   ").append("|  ").append("$10,000").append("  |  ").append(players.get(4).getPlayerName()).append("\n");
        for (int i = 5; i < 8; i++) {
            sb.append("                    ").append("|  ").append("$10,000").append("  |  ").append(players.get(i).getPlayerName()).append("\n");
        }
        sb.append("_______________________________________________").append("\n");
        sb.append("9.th & 16.th place  ").append("|  ").append("$6,000 ").append("  |  ").append(players.get(8).getPlayerName()).append("\n");
        for (int i = 9; i < 16; i++) {
            sb.append("                    ").append("|  ").append("$6,000 ").append("  |  ").append(players.get(i).getPlayerName()).append("\n");
        }
        sb.append("_______________________________________________").append("\n");
        sb.append("16.th & 24.th place ").append("|  ").append("$3,000 ").append("  |  ").append(players.get(16).getPlayerName()).append("\n");
        for (int i = 17; i < 24; i++) {
            sb.append("                    ").append("|  ").append("$3,000 ").append("  |  ").append(players.get(i).getPlayerName()).append("\n");
        }
        sb.append("_______________________________________________").append("\n");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveLeaderBoard(File filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            pw.println(displayTable());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGroupsResults(File filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            pw.println(listOfAllAdvancingPlayers());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGroups(File filename, String groups) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            pw.println(groups);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveFinalScore(File filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            pw.println(getPlayers());
        }
    }

    /**
     * Creates a binary file and saves the results in it.
     *
     * @param filename - name of the file
     * @throws IOException
     */
    public void saveResultsInBin(File filename) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            for (Player p : players) {
                dos.writeInt(p.getNumOfWins());
                dos.writeUTF(p.getPlayerName());
                dos.writeUTF(p.getRace());
                dos.writeUTF(p.getNationality());
                dos.writeUTF(p.getTeam());
                dos.writeInt(p.getPoints());
                dos.writeInt(p.getNumber());
                
            }
        }
    }

}
