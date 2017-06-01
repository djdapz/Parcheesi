package parcheesi.game.player.machine.heuristic;

/**
 * Created by devondapuzzo on 5/28/17.
 */
public class Statistics {
    private int wins = 0;
    private int kickedOuts = 0;

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        this.wins++;
    }

    public int getKickedOuts() {
        return kickedOuts;
    }

    public void incrementKickedOuts() {
        this.kickedOuts++;
    }
}
