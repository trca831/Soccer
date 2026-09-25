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

        Arrays.sort(players);

        List<String> playersAvailable = new ArrayList<String>();

        for (Player player : players) {
            playersAvailable.add(
                    player.getFirstName() + " " +
                            player.getLastName() +
                            " --> Height: " + player.getHeightInInches() +
                            " --> Previous Experience: " + player.isPreviousExperience()
            );
        }

        System.out.println("These are the available players:");
        int index = promptForIndex(playersAvailable);
        return players[index];
    }

    private Player promptForPlayerRemoval(Team team) throws IOException {
        List<Player> players = new ArrayList<Player>(team.getPlayers());
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


