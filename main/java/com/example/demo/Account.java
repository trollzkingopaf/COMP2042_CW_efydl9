package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Account implements Comparable<Account> {

    private long leaderboardScore = 0;

    private String leaderboardUsername;

    public Account() {}

    public void setLeaderboardScore(long leaderboardScore) {
        this.leaderboardScore = leaderboardScore;
    }

    public void setLeaderboardUsername(String leaderboardUsername) {
        this.leaderboardUsername = leaderboardUsername;
    }

    public static ArrayList<Account> accounts = new ArrayList<>();

    @Override
    public int compareTo(Account o) {
        return Long.compare(o.getLeaderboardScore(), leaderboardScore);
    }

    public long getLeaderboardScore() {
        return leaderboardScore;
    }

    public String getLeaderboardUsername() {
        return leaderboardUsername;
    }

    public void writeLB(String accName, long score) {
        try {
            FileWriter fw = new FileWriter("com/example/demo/leaderboard.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(accName + " " + score + "\n");
            writer.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("Failed to write to file.");
            e.printStackTrace();
        }
    }

    public static ArrayList<Account> readLB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("com/example/demo/leaderboard.txt"));
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

    public static String Champion;

    static {
        try {
            Champion = readLB().get(0).getLeaderboardUsername();
            readLB().clear();
     } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String RunnerUp;

    static {
        try {
            RunnerUp = readLB().get(1).getLeaderboardUsername();
            readLB().clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
