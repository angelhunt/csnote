public class Player<T, E> {
    private T playerName;
    private E playerAge;

    public T getPlayerName()
    {
        return playerName;
    }

    public E getPlayerAge()
    {
        return playerAge;
    }

    public Player(T playerName, E playerAge)
    {
        this.playerName = playerName;
        this.playerAge = playerAge;
    }
}
