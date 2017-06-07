package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 5/30/17.
 */
public class DistanceToHomeHeuristic extends AbststractHeuristic {
    private float exponent;

    public DistanceToHomeHeuristic(int weight, float exponent) {
        super(weight);
        this.exponent=exponent;
    }

    @Override
    public Integer evaluate(Board board, Player player) {
        Integer value = 0;

        for(Pawn pawn: player.getPawns()){
            value += board.distanceFromHome(pawn);

        }

        return value;
    }
}
