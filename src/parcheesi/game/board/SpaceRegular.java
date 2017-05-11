package parcheesi.game.board;

import parcheesi.game.enums.Color;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceRegular extends Space {

    public SpaceRegular(Color region, int id) {
        super(region, id, false);
    }

    @Override
    public Boolean isSafeSpace() {
        return false;
    }
}
