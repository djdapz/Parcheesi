package parcheesi.game;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class EnterPiece implements Move {
    private Pawn pawn;

    public EnterPiece(Pawn pawn) {
        this.pawn = pawn;
    }

    public MoveResult run(Board board){
        Space exitSpace = pawn.getExitSpace();

        if(exitSpace.isBlockaded()){
            return MoveResult.BLOCKED;
        }else{
            return exitSpace.addOccupant(pawn);
        }
    }
}
