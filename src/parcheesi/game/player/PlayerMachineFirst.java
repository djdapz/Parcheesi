package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Strategy;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class PlayerMachineFirst extends PlayerMachine {

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice) throws InvalidMoveException {
        Space mostAdvancedSpace = super.findBestSpace(brd, dice);

        if(mostAdvancedSpace == null){
            Nest nest = brd.getNests().get(this.color);
            return super.enterPiece(brd, dice, nest);
        }

        try{
            return super.chooseBestMoveGivenSpaceAndDice(dice, mostAdvancedSpace, brd);
        } catch (Exception e){
            throw e;
        }
    }


    @Override
    protected Space findOptimalSpace(Board board) {
        return board.findMostAdvancedPawn(this);
    }

    @Override
    protected Space findOptimalSpace(Board board, ArrayList<Space> exclusionList) {
        return board.findMostAdvancedPawn(this, exclusionList);
    }

    @Override
    public Strategy getStrategy() {
        return Strategy.FIRST;
    }


}
