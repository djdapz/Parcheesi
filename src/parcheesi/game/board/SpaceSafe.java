package parcheesi.game.board;

import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceSafe extends Space {
    
    @Override
    public Move createMoveFromHere(int distance, Pawn pawn) {
        return new MoveMain(pawn, this.id, distance);
    }

    public SpaceSafe(Color region, int id){
        super(region, id, true);
    }

    @Override
    public Space copy() throws IndexOutOfBoundsException {
        SpaceSafe newSpace = new SpaceSafe(this.getRegion(), this.getId());
        if(this.getOccupant1() != null){
            newSpace.addOccupant(this.getOccupant1());
        }
        if(this.getOccupant2() != null){
            newSpace.addOccupant(this.getOccupant2());
        }
        return newSpace;
    }

    @Override
    public java.awt.Color getSystemColor() {
        return java.awt.Color.MAGENTA;
    }

    @Override
    public String getSerializedRepresentation() {
        return this.getId() + "_" + "MAIN_RING" + "_" +this.getRegion().toString();
    }

    @Override
    public boolean isHomeRow(){
        return false;
    }
}
