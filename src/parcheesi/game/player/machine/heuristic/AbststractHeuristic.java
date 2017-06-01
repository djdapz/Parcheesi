package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public abstract class AbststractHeuristic implements Heuristic{

    protected Integer weight;

    public AbststractHeuristic(Integer weight){
        this.weight = weight;
    }

    public abstract Integer evaluate(Board board, Player player);
}
