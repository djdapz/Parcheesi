package parcheesi.game.player.machine;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.moves.Move;
import parcheesi.game.player.machine.heuristic.Statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class PlayerMachineLast extends PlayerMachine{


    public PlayerMachineLast() {
        super();
        this.name = "LAST";
    }

    @Override
    protected Space findOptimalSpace(Board board) {
        return board.findLeastAdvancedPawn(this);
    }

    @Override
    protected Space findOptimalSpace(Board board, ArrayList<Space> exclusionList) {
        return board.findLeastAdvancedPawn(this, exclusionList);
    }

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException {
        Nest nest = brd.getNests().get(this.color);

        if(nest.getSize() > 0){
            try{
                return enterPiece(brd, dice, nest);
            } catch (Exception ignored){}//can't enter and just continue on..}
        }

        Space leastAdvancedSpace = findBestSpace(brd, dice, originalBlockadeList);

        if(leastAdvancedSpace == null){
            throw new NoMoveFoundException();
        }


        return chooseBestMoveGivenSpaceAndDice(dice, leastAdvancedSpace, brd, originalBlockadeList);

    }

    private static Statistics stats = new Statistics();

    @Override
    public void incrementWins() {
        stats.incrementWins();
    }

    @Override
    public void incrementKickedOuts() {
        stats.incrementKickedOuts();
    }


    public static int getWins() {
        return stats.getWins();
    }

    public static int getKickedOuts() {
        return stats.getKickedOuts();
    }

}
