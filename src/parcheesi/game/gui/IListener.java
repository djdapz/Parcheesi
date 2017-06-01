package parcheesi.game.gui;

import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Pawn;

public interface IListener {
    void submit(Pawn pawn, Integer dice) throws PawnNotFoundException, InvalidMoveException;

    Move getMove();
}
