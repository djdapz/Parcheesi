package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.player.Pawn;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface Move {

    int getStart();

    Space getStart(Board board);

    Pawn getPawn();

    MoveResult run(Board board);

    int getDistance();

    Space getDestinationSpace(Board board) throws GoesHomeException, InvalidMoveException;

    String getStringOfMove();

}
