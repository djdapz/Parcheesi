package parcheesi.game.board;

import parcheesi.game.enums.Color;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceHomeRow extends Space {
    public SpaceHomeRow(Color region, int id) throws IndexOutOfBoundsException {
        super(region, id, false);
    }

    @Override
    public Boolean isSafeSpace() {
        return false;
    }
}
