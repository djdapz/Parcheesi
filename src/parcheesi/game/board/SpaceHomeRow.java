package parcheesi.game.board;

import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.player.Pawn;

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

    @Override
    public Space copy() throws IndexOutOfBoundsException {
        SpaceHomeRow newSpace = new SpaceHomeRow(this.getRegion(), this.getId());
        if(this.getOccupant1() != null){
            newSpace.addOccupant(this.getOccupant1());
        }
        if(this.getOccupant2() != null){
            newSpace.addOccupant(this.getOccupant2());
        }
        return newSpace;
    }

    @Override
    public Move createMoveFromHere(int distance, Pawn pawn) {
        return new MoveHome(pawn, this.id, distance);
    }
}
