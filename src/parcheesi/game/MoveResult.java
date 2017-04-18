package parcheesi.game;

import java.util.HashSet;

/**
 * Created by devondapuzzo on 4/11/17.
 */
public enum MoveResult {
    BLOCKED,
    SUCCESS,
    BOP,
    HOME,
    OVERSHOT,
    ENTERED;

    public boolean isSuccessfullMove(){
        if(this == BLOCKED || this == OVERSHOT){
            return false;
        }
        return true;
    }

}
