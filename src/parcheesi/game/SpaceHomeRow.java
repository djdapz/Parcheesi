package parcheesi.game;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceHomeRow extends Space {
    public SpaceHomeRow(Color region, int id) throws IndexOutOfBoundsException {
        super(region, id);
    }

    @Override
    public Boolean isSafeSpace() {
        return false;
    }
}
