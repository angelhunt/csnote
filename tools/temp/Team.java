import java.util.LinkedList;
import java.util.List;

public class Team<T, E> {
    private List<Player<T, E>> players = new LinkedList<>();

    public boolean addplayer(T playerName, E playerAge) {
        for (Player<T, E> player : players) {
            if (player.getPlayerName().equals(playerName))
                return false;
        }
        players.add(new Player<T, E>(playerName, playerAge));
        return true;
    }

    public void playerList() {
        for (Player<T, E> player : players) {
            System.out.println(player.getPlayerName().toString() + " --> " + player.getPlayerAge().toString());
        }
    }

}