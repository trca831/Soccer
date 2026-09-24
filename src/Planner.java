import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planner {
    private List<Team> mTeams;
    private BufferedReader mReader;
    private Map<String, String> mMenu;

    public Planner() {
        mTeams = new ArrayList<Team>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu = new HashMap<String, String>();
        mMenu.put("create", "Add a new Team to the Planner!");
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
}


