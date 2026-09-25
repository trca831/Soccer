import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Planner {
    private List<Team> mTeams;
    private BufferedReader mReader;
    private Map<String, String> mMenu;

    public Planner() {
        mTeams = new ArrayList<Team>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String, String>();
        mMenu.put("create", "ADD A NEW TEAM to the Planner!");
        mMenu.put("add", "ADD A PLAYER to Team");
        mMenu.put("remove", "REMOVE A PLAYER from Team");
        mMenu.put("height", "View Team's Height Report");
        mMenu.put("balance", "View League Balance Report");
        mMenu.put("print", "Print Current Roster");
        mMenu.put("quit", "Done planning. Exit the program");
    }

    private String promptAction() throws IOException {
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }

        System.out.print("What do you want to do: ");
        String command = mReader.readLine();
        return command.trim().toLowerCase();
    }

    public void run() {
        String command = "";

        do {
            try {
                command = promptAction();
                switch (command) {

                    case "create":
                        Team team = promptNewTeam();
                        mTeams.add(team);
                        System.out.printf("You added: %s %n%n", team);
                        break;

                    case "add":
                        Team teamAddPlayer = promptForChooseTeam();
                        Player selectedPlayer = promptForChoosePlayer();
                        teamAddPlayer.addSelectedPlayer(selectedPlayer);
                        break;

                    case "remove":
                        Team teamRemovePlayer = promptForChooseTeam();
                        Player playerToRemove = promptForPlayerRemoval(teamRemovePlayer);
                        teamRemovePlayer.removeSelectedPlayer(playerToRemove);
                        break;

                    case "height":
                        Team teamHeight = promptForChooseTeam();
                        heightRanges(teamHeight);
                        break;

                    case "balance":
                        balanceReport();
                        break;

                    case "print":
                        Team teamRoster = promptForChooseTeam();
                        displayMyTeam(teamRoster);
                        break;

                    case "quit":
                        System.out.println("Have a good game!");
                        break;

                    default:
                        System.out.println("Choose again!");
                }
            } catch (IOException ioe) {
                System.out.println("Problem with input");
                ioe.printStackTrace();
            }

        } while (!command.equals("quit"));
    }

    private Team promptNewTeam() throws IOException {
        System.out.print("Enter the team's name:  ");
        String teamName = mReader.readLine();
        System.out.print("Enter the coach's name:  ");
        String coachName = mReader.readLine();
        return new Team(teamName, coachName);
    }

    private void balanceReport() throws IOException {
        Map<String, int[]> balance = new HashMap<String, int[]>();
        for (Team team : mTeams) {
            int expCount = 0;
            int inExpCount = 0;

            for (Player player : team.getPlayers()) {
                if (player.isPreviousExperience()) {
                    expCount++;
                } else {
                    inExpCount++;
                }
            }
            balance.put(
                    team.getTeamName(),
                    new int[]{expCount, inExpCount}
            );
        }

        System.out.println("League Balance Report");

        for (Team team : mTeams) {
            int[] counts = balance.get(team.getTeamName());

            System.out.println("Team: " + team.getTeamName());
            System.out.println("Players with Experience: " + counts[0]);
            System.out.println("Players with No Experience: " + counts[1]);
            System.out.println(" ");
        }
    }

    private void displayMyTeam(Team team) throws IOException {
        System.out.printf("Player Roster for Team: %s%n", team.getTeamName());

        for (Player player : team.getPlayers()) {
            System.out.println(
                    player.getFirstName() + " " +
                            player.getLastName() +
                            " --> Height: " + player.getHeightInInches() +
                            " --> Previous Experience: " + player.isPreviousExperience()
            );
        }
    }

    private void heightRanges(Team team) throws IOException {
        List<Player> height35to40 = new ArrayList<Player>();
        List<Player> height41to46 = new ArrayList<Player>();
        List<Player> height47andUp = new ArrayList<Player>();
        System.out.printf("Team Height Report for Team: %s%n", team.getTeamName());

        for (Player player : team.getPlayers()) {
            if (player.getHeightInInches() >= 35 &&
                    player.getHeightInInches() <= 40) {
                height35to40.add(player);

            } else if (player.getHeightInInches() >= 41 &&
                    player.getHeightInInches() <= 46) {
                height41to46.add(player);

            } else {
                height47andUp.add(player);
            }
        }

        System.out.println("***35-40 Inches: ***%n");
        for (Player player : height35to40) {
            System.out.println(
                    player.getFirstName() + " " +
                            player.getLastName() + " --> " +
                            player.getHeightInInches()
            );
        }
        System.out.println("***41-46 Inches: ***%n");
        for (Player player : height41to46) {
            System.out.println(
                    player.getFirstName() + " " +
                            player.getLastName() + " --> " +
                            player.getHeightInInches()
            );
        }

        System.out.println("***47-50 Inches: ***%n");
        for (Player player : height47andUp) {
            System.out.println(
                    player.getFirstName() + " " +
                            player.getLastName() + " --> " +
                            player.getHeightInInches());
        }
    }

    private void sortTeamsAlphabetically() {
        mTeams.sort(null);
    }

    private Team promptForChooseTeam() throws IOException {
        sortTeamsAlphabetically();
        List<String> chooseTeams = new ArrayList<String>();

        for (Team currentTeam : mTeams) {
            chooseTeams.add(currentTeam.getTeamName());
        }

        int index = promptForIndex(chooseTeams);
        return mTeams.get(index);
    }

    private Player promptForChoosePlayer() throws IOException {
        Player[] players = Players.load();

        Set<Player> assignedPlayers = new HashSet<Player>();
        for (Team team : mTeams) {
            assignedPlayers.addAll(team.getPlayers());
        }
        List<Player> availablePlayers = new ArrayList<Player>();
        List<String> playersAvailable = new ArrayList<String>();
        for (Player player : players) {
            if (!assignedPlayers.contains(player)) {
                availablePlayers.add(player);
            }
        }
        availablePlayers.sort(null);

        for (Player player : availablePlayers) {
            playersAvailable.add(
                    player.getFirstName() + " " +
                            player.getLastName() +
                            " --> Height: " + player.getHeightInInches() +
                            " --> Previous Experience: " + player.isPreviousExperience()
            );
        }

        System.out.println("These are the available players:");
        int index = promptForIndex(playersAvailable);
        return availablePlayers.get(index);
    }

    private Player promptForPlayerRemoval(Team team) throws IOException {
        List<Player> players = new ArrayList<Player>(team.getPlayers());

        players.sort(null);

        List<String> playersAvailable = new ArrayList<String>();

        for (Player player : players) {
            playersAvailable.add(
                    player.getFirstName() + " " +
                            player.getLastName() +
                            " --> Height: " + player.getHeightInInches() +
                            " --> Previous Experience: " + player.isPreviousExperience()
            );
        }

        System.out.println("These are the players on this team:");
        int index = promptForIndex(playersAvailable);
        return players.get(index);
    }


    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;

        for (String option : options) {
            System.out.printf("%d)  %s %n", counter++, option);
        }

        System.out.printf("SELECT ONE: %n");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());

        return choice - 1;
    }


}


