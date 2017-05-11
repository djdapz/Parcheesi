package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Pawn;

import java.util.List;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class RulesChecker {
    public void checkSetOfMoves(Board board, List<Move> moves){

    }

    public boolean makeSurePawnIsOnlyInOneSpot(Board board, Pawn pawn){
        boolean found = false;
        Space space = board.findPawn(pawn);

        if(space != null){
            found = true;
        }



        if(board.getHome().isPawnHome(pawn)){
            if(found){
                return false;
            }else{
                found = true;
            }
        }

        if(board.getNests().get(pawn.getColor()).isAtNest(pawn)){
            if(found){
                return false;
            }else{
                found = true;
            }
        }

        if(board.findPawn(pawn, space) != null){
            return false;
        }

        return true;
    }
}
