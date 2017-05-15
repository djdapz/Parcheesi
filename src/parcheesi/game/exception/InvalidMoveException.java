package parcheesi.game.exception;

import parcheesi.game.enums.MoveResult;

/**
 * Created by devondapuzzo on 5/4/17.
 */
public class InvalidMoveException extends Exception {
    MoveResult moveResult;

    public MoveResult getMoveResult() {

        if(moveResult != null){
            return moveResult;
        } else{
            return MoveResult.UNKNOWN;
        }
    }

    public InvalidMoveException(MoveResult mr){
        this.moveResult = mr;
    }


    public InvalidMoveException(){
    }
}
