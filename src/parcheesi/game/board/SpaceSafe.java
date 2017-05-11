package parcheesi.game.board;

import parcheesi.game.enums.Color;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceSafe extends Space {
    @Override
    public Boolean isSafeSpace() {
        return true;
    }

    public SpaceSafe(Color region, int id){
        super(region, id, true);
    }
}