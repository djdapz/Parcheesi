package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public interface Heuristic {

    Integer evaluate(Board board, Player player);
}
