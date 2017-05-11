package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Strategy;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class PlayerMachineLast extends PlayerMachine{
    @Override
    public Move doMiniMove(Board brd, List<Integer> dice) throws InvalidMoveException {
        Nest nest = brd.getNests().get(this.color);
        if(nest.getSize() > 0){
            EnterPiece move = enterPiece(brd, dice, nest);
            if(move != null){
                return move;
            }
        }

        //all the parcheesi.game.player can do is move the pawn out
        Space leastAdvancedSpace = findBestSpace(brd, dice);

        if(leastAdvancedSpace == null){
            return null;
        }


        try{
            return chooseBestMoveGivenSpaceAndDice(dice, leastAdvancedSpace, brd);
        }catch (Exception e){
            throw e;
        }


    }

    @Override
    public Strategy getStrategy() {
        return Strategy.LAST;
    }

    @Override
    protected Space findOptimalSpace(Board board) {
        return board.findLeastAdvancedPawn(this);
    }

    @Override
    protected Space findOptimalSpace(Board board, ArrayList<Space> exclusionList) {
        return board.findLeastAdvancedPawn(this, exclusionList);
    }
}
