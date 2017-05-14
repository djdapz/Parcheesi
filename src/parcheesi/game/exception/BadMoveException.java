package parcheesi.game.exception;

import parcheesi.game.enums.MoveResult;

/**
 * Created by devondapuzzo on 5/11/17.
 */
public class BadMoveException extends Exception {

    MoveResult moveResult;

    public MoveResult getMoveResult() {
        return moveResult;
    }

    public BadMoveException(MoveResult mr){
        this.moveResult = mr;
    }
}
