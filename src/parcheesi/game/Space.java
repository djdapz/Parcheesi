package parcheesi.game;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public abstract class Space {

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


    public Space(Color region, int id) throws IndexOutOfBoundsException {
        this.region = region;

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
                //if the other occupant is the same color as the new one
                occupant2 = pawn;
                return MoveResult.SUCCESS;
            }else{
                //bop the occupant at the turn level
                //TODO - allow bopping from parcheesi.game.Space level if possible... use reflection?
                return MoveResult.BOP;
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
    abstract Boolean isSafeSpace();

}
