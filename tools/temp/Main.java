public class Main {
    public static void main(String[] argv)
    {
        Team<String, Integer> team1 = new Team<String, Integer>();
        team1.addplayer("Messi", 30);
        team1.addplayer("Ronaldo", 32);
        team1.addplayer("Messi", 30);
        team1.addplayer("Neymar", 28);

        System.out.println("Team 1\n==================");
        team1.playerList();

        Team<String, String> team2 = new Team<String, String>();
        team2.addplayer("Federer", "38 years");
        team2.addplayer("Nadal", "32 years");
        team2.addplayer("Djokovic", "30 yrs");
        team2.addplayer("federer", "35 years");

        System.out.println("\nTeam 2\n==================");
        team2.playerList();
    }
}
