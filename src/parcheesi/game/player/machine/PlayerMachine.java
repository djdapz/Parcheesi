package parcheesi.game.player.machine;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.PlayerAbstract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by devondapuzzo on 5/4/17.
 */
public abstract class PlayerMachine extends PlayerAbstract {

    protected abstract Space findOptimalSpace(Board board);

    protected abstract Space findOptimalSpace(Board board, ArrayList<Space> exclusionList);

    public Move doMiniMove(Board brd, List<Integer> dice) throws InvalidMoveException, DuplicatePawnException, NoMoveFoundException {
        ArrayList<Space> el = new ArrayList<>();
        return doMiniMove(brd, dice, el);
    }

    protected Space findBestSpace(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList){
        Space bestSpace = findOptimalSpace(brd);
        Pawn otherPawn = null;
        ArrayList<Space> exclusionList = new ArrayList<>();


        //all the parcheesi.game.player can do is move the pawn out
        while(bestSpace != null //if null; we've gotten to nest and players must be there
                && bestSpace.getOccupant1()!= null
                && !bestSpace.getOccupant1().canMoveWithoutMovingBlockades(dice, brd, originalBlockadeList)){ // if this condition is true, we can move and we move on
            exclusionList.add(bestSpace);


            bestSpace = findOptimalSpace(brd, exclusionList);
        }

        return bestSpace;
    }

    protected Move chooseBestMoveGivenSpaceAndDice(List<Integer> dice, Space space, Board brd, ArrayList<Space> originalBlockadeList) throws InvalidMoveException{
        Collections.sort(dice);
        Collections.reverse(dice);

        for(Integer die: dice){
            //finds the maximum distance to move this pawn
            List<Integer> singleDie = new ArrayList<>();
            singleDie.add(die);

            if(space.getOccupant1().canMoveWithoutMovingBlockades(singleDie,brd, originalBlockadeList)){
                return createMove(space, die, space.getOccupant1());
            }
        }

        //TODO- thrown assertion/error
        throw new InvalidMoveException();

    }

    private Move createMove(Space space,  Integer distance, Pawn pawn){
        if(space.getRegion() == Color.HOME){
            return new MoveHome(pawn, space.getId(), distance);
        }else{
            return new MoveMain(pawn, space.getId(), distance);
        }
    }

    protected EnterPiece enterPiece(Board brd, List<Integer> dice, Nest nest) throws InvalidMoveException {

        if(dice.contains(5) ||
                (dice.contains(4) && dice.contains(1)) ||
                (dice.contains(3) && dice.contains(2))){
            //return this.enterPiece(brd);
            for(Pawn pawn: pawns){
                //TODO - include below
                if(nest.isAtNest(pawn)){
                    EnterPiece move = new EnterPiece(pawn);
                    move.getDestinationSpace(brd);
                    return move;
                }
            }
        }

        throw new InvalidMoveException();
    }


}
