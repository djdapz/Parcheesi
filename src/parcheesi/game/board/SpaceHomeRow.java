package parcheesi.game.board;

import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.player.Pawn;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class SpaceHomeRow extends Space {
    private Color colorOfSpace;

    public SpaceHomeRow(Color region, int id, Color colorOfSpace) throws IndexOutOfBoundsException {
        super(region, id, false);
        this. colorOfSpace = colorOfSpace;
    }

    public SpaceHomeRow(Color region, int id) throws IndexOutOfBoundsException {
        this(Color.HOME, id, region);
        assert(region != Color.HOME);
    }

    @Override
    public Space copy() throws IndexOutOfBoundsException {
        SpaceHomeRow newSpace = new SpaceHomeRow(this.getRegion(), this.getId(), this.colorOfSpace);
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

    @Override
    public java.awt.Color getSystemColor() {
        return this.getRegion().getSystemColor();
    }

    @Override
    public String getSerializedRepresentation() {
        return this.getId() + "_" + "HOME_ROW" + "_" +this.colorOfSpace.toString();
    }

    @Override
    public boolean isHomeRow(){
        return true;
    }

}
