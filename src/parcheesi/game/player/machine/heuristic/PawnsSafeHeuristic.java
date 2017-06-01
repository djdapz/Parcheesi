package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public class PawnsSafeHeuristic extends AbststractHeuristic {
    public PawnsSafeHeuristic(Integer weight) {
        super(weight);
    }

    @Override
    public Integer evaluate(Board board, Player player) {
        int count = 0;
        for(Pawn pawn: player.getPawns()){
            if(board.findPawn(pawn) != null && (board.findPawn(pawn).isSafeSpace() || board.findPawn(pawn).isHomeRow())){
                count++;
            }
        }

        return count * weight;
    }
}
