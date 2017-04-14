package parcheesi.game;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceRegular extends Space {

    public SpaceRegular(Color region, int id) {
        super(region, id);
    }

    @Override
    public Boolean isSafeSpace() {
        return false;
    }
}
