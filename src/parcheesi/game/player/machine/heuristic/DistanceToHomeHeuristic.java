package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 5/30/17.
 */
public class DistanceToHomeHeuristic extends AbststractHeuristic {
    private double exponent;

    public DistanceToHomeHeuristic(int weight, double exponent) {
        super(weight);
        this.exponent=exponent;
    }

    @Override
    public Integer evaluate(Board board, Player player) {
        double value = 0;
        for(Pawn pawn: player.getPawns()){
            value += Math.pow(board.distanceFromHome(pawn),exponent);

        }
        return (int) value;
    }
}
