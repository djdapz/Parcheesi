package parcheesi.game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

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
            MoveResult mr = exitSpace.addOccupant(pawn);
            if(mr == MoveResult.SUCCESS){
                board.getNests().get(pawn.getColor()).removePawn(pawn);
                return MoveResult.ENTERED;
            }else if(mr == MoveResult.BOP) {
                board.getNests().get(pawn.getColor()).removePawn(pawn);
                Pawn boppedPawn = pawn.getExitSpace().getOccupant2();
                HashMap<Color, Nest> nests = board.getNests();
                Nest bopToNest = nests.get(boppedPawn.getColor());
                bopToNest.addPawn(boppedPawn);

                pawn.getExitSpace().removeOccupant(boppedPawn);
                return MoveResult.BOP;
            }else{
                return mr;
            }
        }
    }

    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public MoveResult isValid(Board board) {
        throw new NotImplementedException();
    }
}
