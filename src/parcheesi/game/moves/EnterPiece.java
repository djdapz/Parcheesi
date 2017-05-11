package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.player.Pawn;

import java.util.HashMap;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class EnterPiece implements Move {
    private Pawn pawn;

    public EnterPiece(Pawn pawn) {
        this.pawn = pawn;
    }

    @Override
    public int getStart() {
        return 0;
    }

    @Override
    public Pawn getPawn() {
        return pawn;
    }

    public MoveResult run(Board board){
        Space exitSpace = pawn.getExitSpace(board);

        if(exitSpace.isBlockaded()){
            return MoveResult.BLOCKED;
        }else{
            MoveResult mr = exitSpace.addOccupant(pawn);
            if(mr == MoveResult.SUCCESS){
                board.getNests().get(pawn.getColor()).removePawn(pawn);
                return MoveResult.ENTERED;
            }else if(mr == MoveResult.BOP) {
                board.getNests().get(pawn.getColor()).removePawn(pawn);
                Pawn boppedPawn = exitSpace.getOccupant2();
                HashMap<Color, Nest> nests = board.getNests();
                Nest bopToNest = nests.get(boppedPawn.getColor());
                bopToNest.addPawn(boppedPawn);

                exitSpace.removeOccupant(boppedPawn);
                return MoveResult.BOP;
            }
            else if(mr == MoveResult.ALREADYHERE) {
                exitSpace.removeOccupant(pawn);
                return mr;
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
        Space entranceSpace = pawn.getExitSpace(board);
        if(entranceSpace.isBlockaded()){
            return MoveResult.BLOCKED;
        }

        if(entranceSpace.getOccupant1() == null){
            return MoveResult.ENTERED;
        }

        if(entranceSpace.getOccupant1().getColor() == pawn.getColor()){
            return MoveResult.ENTERED;
        }else{
            return MoveResult.BOP;
        }
    }

    @Override
    public String getStringOfMove() {
        return "PlayerInterface " + pawn.getColor().toString() + ", EnterPiece  ";
    }
}
