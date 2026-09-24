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

    @Override
    public String toString() {
        return String.format("%n Team's Name: %s %n Coach's Name: %s", mTeamName, mCoachName);
    }
}
