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
public class PlayerMachineFirst extends PlayerMachine {
    public PlayerMachineFirst() {
        super();
        this.name = "FIRST";
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
