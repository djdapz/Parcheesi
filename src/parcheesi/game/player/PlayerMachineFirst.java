package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Strategy;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class PlayerMachineFirst extends PlayerMachine {
    @Override
    protected Space findOptimalSpace(Board board) {
        return board.findMostAdvancedPawn(this);
    }

    @Override
    protected Space findOptimalSpace(Board board, ArrayList<Space> exclusionList) {
        return board.findMostAdvancedPawn(this, exclusionList);
    }

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException {

        Space mostAdvancedSpace = findBestSpace(brd, dice, originalBlockadeList);

        if(mostAdvancedSpace == null){
            Nest nest = brd.getNests().get(this.color);
            try{
                return enterPiece(brd, dice, nest);
            }catch (Exception e){
                throw new NoMoveFoundException();
            }
        }

        try{
            return super.chooseBestMoveGivenSpaceAndDice(dice, mostAdvancedSpace, brd, originalBlockadeList);
        } catch (Exception e){
            throw e;
        }
    }


    @Override
    public Strategy getStrategy() {
        return Strategy.FIRST;
    }


}
