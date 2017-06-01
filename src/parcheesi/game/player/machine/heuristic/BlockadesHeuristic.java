package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public class BlockadesHeuristic extends AbststractHeuristic {
    public BlockadesHeuristic(Integer weight) {
        super(weight);
    }

    @Override
    public Integer evaluate(Board board, Player player) {
        return RulesChecker.findBlockades(board, player).size() * this.weight;
    }
}
