package parcheesi.game.board;

import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceRegular extends Space {

    public SpaceRegular(Color region, int id) {
        super(region, id, false);
    }

    @Override
    public Space copy() throws IndexOutOfBoundsException {
        SpaceRegular newSpace = new SpaceRegular(this.getRegion(), this.getId());
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
        if(this.getRegion() == Color.HOME){
            return new MoveHome(pawn, this.id, distance);
        }else{
            return new MoveMain(pawn, this.id, distance);
        }
    }

    @Override
    public java.awt.Color getSystemColor() {
        return java.awt.Color.lightGray;
    }

    @Override
    public String getSerializedRepresentation() {
        return this.getId() + "_" + "MAIN_RING" + "_" +this.getRegion().toString();
    }

    @Override
    public boolean isHomeRow() {
        return false;
    }

}
