package com.teamtreehouse.model;

import java.util.HashSet;
import java.util.Set;

public class Team {
    private String mTeamName;
    private String mCoachName;
    private Set<Player> mPlayers;

    public Team(String teamName, String coachName) {
        mTeamName = teamName;
        mCoachName = coachName;
        mPlayers = new HashSet<Player>();
    }

    public String getTeamName() {
        return mTeamName;
    }

    public String getCoachName() {
        return mCoachName;
    }

    public void addSelectedPlayer(Player player) {
        if (mPlayers.size() >= 11) {
            System.out.println("Stop. A team cannot exceed 11 players!");

        } else {
            mPlayers.add(player);
            System.out.printf(
                    "Player added: %s %s%n",
                    player.getFirstName(),
                    player.getLastName()
            );
        }
    }

    @Override
    public String toString() {
        return String.format("%n Team's Name: %s %n Coach's Name: %s", mTeamName, mCoachName);
    }
}
