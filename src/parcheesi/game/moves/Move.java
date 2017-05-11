package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.player.Pawn;
import parcheesi.game.enums.MoveResult;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface Move {

    int getStart();

    Pawn getPawn();

    MoveResult run(Board board);

    int getDistance();

    MoveResult isValid(Board board);

    String getStringOfMove();

}
