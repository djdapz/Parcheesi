package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.enums.Strategy;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/4/17.
 */
public class PlayerHuman extends Player {

    @Override
    public ArrayList<Move> doMove(Board brd, List<Integer> dice) {
        return null;
    }

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice) throws InvalidMoveException {
        return null;
    }

    @Override
    public Strategy getStrategy() {
        return null;
    }

}
