package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by devondapuzzo on 5/4/17.
 */
public abstract class PlayerMachine extends Player {


    @Override
    public ArrayList<Move> doMove(Board originalBoard, List<Integer> dice) throws Exception {
        int numMoves = dice.size();
        int numTries = 0;
        RulesChecker rc = new RulesChecker();
        Move currentMove;
        ArrayList<Move> moveObjects = new ArrayList<>();
        Board brd = new Board(originalBoard);
        MoveResult mr;

        while(dice.size() != 0 && canMove(dice, brd)){
            numTries++;
            if(numTries > 100){
                throw new InvalidMoveException();
            }

            try{
                currentMove =  this.doMiniMove(brd, dice);
                if(currentMove == null){
                    numTries++;
                }
                mr = currentMove.run(brd);
                if( !rc.makeSurePawnIsOnlyInOneSpot(brd, currentMove.getPawn())){
                    throw new InvalidMoveException();
                }
                handleMoveResult(mr, brd, currentMove, dice, moveObjects);
            }catch (Exception e){
                throw e;
            }
        }

        return moveObjects;
    }

    private void handleMoveResult(MoveResult mr, Board brd, Move currentMove, List<Integer> dice, ArrayList<Move> moveObjects) throws Exception {
        //if bopped a parcheesi.game.player, force parcheesi.game.player to move a pawn 20.
        //assuming that the parcheesi.game.player MxUST do the bop next movement first
        if (mr == MoveResult.BOP) {
            ArrayList<Integer> bonusMoveDice = new ArrayList<>();
            bonusMoveDice.add(20);

            ArrayList<Move> bonusMoveItems = this.doMove(brd, bonusMoveDice);

            for(Move move: bonusMoveItems){
                move.run(brd);
            }

            moveObjects.add(currentMove);
            moveObjects.addAll(bonusMoveItems);

            editDice(currentMove, dice);
            return;
        } else if(mr == MoveResult.ENTERED){
            moveObjects.add(currentMove);

            if(dice.contains(3) && dice.contains(2)){
                dice.remove(dice.indexOf(2));
                dice.remove(dice.indexOf(3));
            }else if(dice.contains(4) && dice.contains(1)){
                dice.remove(dice.indexOf(4));
                dice.remove(dice.indexOf(1));
            }else if(dice.contains(5)){
                dice.remove(dice.indexOf(5));
            }else{
                throw new InvalidMoveException();
            }
        }
        else if (mr == MoveResult.BLOCKED || mr == MoveResult.OVERSHOT) {
            moveObjects.remove(currentMove);
            // new InvalidMoveException();
        } else {
            editDice(currentMove, dice);
            moveObjects.add(currentMove);
        }


    }
    protected abstract Space findOptimalSpace(Board board);

    protected abstract Space findOptimalSpace(Board board, ArrayList<Space> exclusionList);

    private void editDice(Move currentMove, List<Integer> moves) throws InvalidMoveException{
        if (currentMove.getClass() == EnterPiece.class){
            if((moves.contains(4) && moves.contains(1)) || (moves.contains(3) && moves.contains(2))){
                moves= new ArrayList<>();
            }else if(moves.contains(5)){
                moves.remove((moves.indexOf(5)));
            }else{
                throw new InvalidMoveException();
            }
        }else{
            try{
                moves.remove(moves.indexOf(currentMove.getDistance()));
            }catch (Exception e){
                throw e;
            }

        }
    }

    protected Space findBestSpace(Board brd, List<Integer> dice){
        Space bestSpace = findOptimalSpace(brd);

        ArrayList<Space> exclusionList = new ArrayList<>();

        //all the parcheesi.game.player can do is move the pawn out
        while(bestSpace != null //if null; we've gotten to nest and players must be there
                && bestSpace.getOccupant1()!= null
                && !bestSpace.getOccupant1().canMove(dice,brd)){ // if this condition is true, we can move and we move on
            exclusionList.add(bestSpace);


            bestSpace = findOptimalSpace(brd, exclusionList);
        }

        return bestSpace;
    }

    protected Move chooseBestMoveGivenSpaceAndDice(List<Integer> dice, Space space, Board brd) throws InvalidMoveException{
        Collections.sort(dice);
        Collections.reverse(dice);

        for(Integer die: dice){
            //finds the maximum distance to move this pawn
            List<Integer> singleDie = new ArrayList<>();
            singleDie.add(die);

            if(space.getOccupant1().canMove(singleDie,brd)){
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

    EnterPiece enterPiece(Board brd, List<Integer> dice, Nest nest){

        if(dice.contains(5) ||
                (dice.contains(4) && dice.contains(1)) ||
                (dice.contains(3) && dice.contains(2))){
            //return this.enterPiece(brd);
            for(Pawn pawn: pawns){
                //TODO - include below
                if(nest.isAtNest(pawn)){
                    EnterPiece move = new EnterPiece(pawn);
                    if(move.isValid(brd).isSuccessfullMove()){
                        return move;
                    }
                }
            }
            return null;
        }else{
            return null;
        }

    };

}
