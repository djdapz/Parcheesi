package parcheesi.game.board;

import parcheesi.game.player.Pawn;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public abstract class Space {


    private boolean safeSpace;
    private Color region;
    private int id;


    private Pawn occupant1 = null;
    private Pawn occupant2 = null;

    public Pawn getOccupant1() {
        return occupant1;
    }
    public Pawn getOccupant2() {
        return occupant2;
    }
    public Color getRegion() {
        return region;
    }
    public int getId() {
        return id;
    }
    public boolean hasOccupant(Pawn pawn){return occupant1==pawn || occupant2 == pawn;}


    public Space(Color region, int id, boolean safeSpace) throws IndexOutOfBoundsException {
        this.region = region;
        this.safeSpace = safeSpace;

        if(id < 0){
            throw new IndexOutOfBoundsException();
        }

        this.id = id;
    }


    public boolean isBlockaded(){

        if(occupant2 != null){
            return true;
        }

        return false;
    }

    public MoveResult addOccupant(Pawn pawn){
        if(this.isBlockaded()){
            return MoveResult.BLOCKED;
        }

        if(occupant1 == null){
            occupant1 = pawn;
            return MoveResult.SUCCESS;
        }else{
            if(occupant1.getColor() == pawn.getColor()){
                if(occupant1 == pawn){
                    return MoveResult.ALREADYHERE;
                }
                //if the other occupant is the same color as the new one
                occupant2 = pawn;
                return MoveResult.SUCCESS;
            }else{
                //bop the occupant at the turn level
                if(this.isSafeSpace()){
                    if(pawn.getExitSpaceId() == this.getId()){
                        occupant2 = occupant1;
                        occupant1 = pawn;
                        return MoveResult.BOP;
                    }else{
                        return MoveResult.BLOCKED;
                    }
                }else{
                    occupant2 = occupant1;
                    occupant1 = pawn;
                    return MoveResult.BOP;
                }
            }
        }
    }

    public boolean removeOccupant(Pawn pawn){
        if(occupant2 == pawn){
            occupant2 = null;
            return true;
        }

        if(occupant1 == pawn){
            occupant1 = null;
            occupant1 = occupant2;
            occupant2 = null;

            return true;
        }

        return false;
    }

    //Methods unique to each space
    public Boolean isSafeSpace(){
        return safeSpace;
    };

}
