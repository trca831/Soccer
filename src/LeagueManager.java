import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;


public class LeagueManager {

    public static void main(String[] args) {
        Player[] players = Players.load();
        System.out.printf("There are currently %d registered players.%n", players.length);
        //your code here
        Planner planner = new Planner();
        planner.run();

    for (Player player : players) {
      System.out.println(
                player.getFirstName() + " "
              + player.getLastName() + " "
              + player.getHeightInInches() + " "
              + player.isPreviousExperience()
      );
    }

    }

}
