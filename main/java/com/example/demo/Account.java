package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class takes and records an account with the player's username and score.
 * It also contains methods and fields for reading, writing and storing for both username and score to an array list and an external leaderboard text file.
 * It also contains methods to sort and retrieve the top three scores
 *
 * @author Desmond Jun Hong, Lau-modified
 */
public class Account implements Comparable<Account> {
    /**
     * Initialises the player's score of the account.
     */
    private long leaderboardScore = 0;
    /**
     * The player's username of the account.
     */
    private String leaderboardUsername;

    /**
     * Private constructor to construct a new empty Account object.
     */
    public Account() {}

    /**
     * Private setter for the player's score.
     * @param leaderboardScore the player's score to set
     */
    public void setLeaderboardScore(long leaderboardScore) {
        this.leaderboardScore = leaderboardScore;
    }

    /**
     * Private setter for the player's username.
     * @param leaderboardUsername the player's username to set
     */
    public void setLeaderboardUsername(String leaderboardUsername) {
        this.leaderboardUsername = leaderboardUsername;
    }


    /**
     * A static array list containing Account objects representing the entries of players' username and score.
     */
    public static ArrayList<Account> accounts = new ArrayList<>();

    /**
     * Compares score of thi
     * @param o the other Account object to be compared based on their scores.
     * @return a negative integer if the current object's score is less than other object's score
     *         the player's score, a positive integer if this object's leaderboard score is greater than the other
     *         object's score, or zero if both of their leaderboard scores are equal
     */
    @Override
    public int compareTo(Account o) {
        return Long.compare(o.getLeaderboardScore(), leaderboardScore);
    }

    /**
     * Returns the player's score of this account.
     *
     * @return the player's score of this account.
     */
    public long getLeaderboardScore() {
        return leaderboardScore;
    }

    /**
     * Returns the player's username of this account.
     *
     * @return the player's username of this account.
     */
    public String getLeaderboardUsername() {
        return leaderboardUsername;
    }

    /**
     * Writes the given player's account name and score to an external leaderboard text file.
     *
     * @param accName the username of the player's account to write to the leaderboard text file.
     * @param score the score of the player's account to write to the leaderboard text file.
     */
    public void writeLB(String accName, long score) {
        try {
            FileWriter fw = new FileWriter("main/java/com/example/demo/leaderboard.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(accName + " " + score + "\n");
            writer.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("Failed to write to file.");
            e.printStackTrace();
        }
    }

    /**
     * Reads the external leaderboard text file and returns an array list of Account objects sorted by the player's
     * score in descending order.
     *
     * @return a list of Account objects sorted by the player's score in descending order
     * @throws IOException if there is an error reading the leaderboard file
     */
    public static ArrayList<Account> readLB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("leaderboard.txt"));
        String line = br.readLine();
        while(line != null) {
            Account acc = new Account();
            String[] split = line.split(" ");

            acc.setLeaderboardUsername(split [0]);
            acc.setLeaderboardScore(Long.parseLong(split[1]));
            accounts.add(acc);
            line = br.readLine();
        }
        br.close();
        Collections.sort(accounts);
        return accounts;
    }

    /**
     * Initialises the player's username with the highest score and stores them into a variable.
     */
    public static String Champion;

    static {
        try {
            Champion = readLB().get(0).getLeaderboardUsername();
            readLB().clear();
     } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialises the player's username with the second highest score.
     */
    public static String RunnerUp;

    static {
        try {
            RunnerUp = readLB().get(1).getLeaderboardUsername();
            readLB().clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialises the player's username with the third highest score.
     */
    public static String SecondRunnerUp;

    static {
        try {
            SecondRunnerUp = readLB().get(2).getLeaderboardUsername();
            readLB().clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
