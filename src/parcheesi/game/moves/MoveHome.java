package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.exception.BadMoveException;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.player.Pawn;
import parcheesi.game.board.Space;
import parcheesi.game.enums.MoveResult;

import java.util.Vector;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class MoveHome extends MoveAbstract {

    public MoveHome(Pawn pawn, int start, int distance) {
        super(pawn, start, distance);
    }

    @Override
    public MoveResult run(Board board) {
        Home home = board.getHome();
        Vector<Space> homeRow = board.getHomeRows().get(pawn.getColor());
        Space newSpace;

        //so we can move into the home row from outside of the homerow using this function.
        if(start >=0){
            homeRow.get(start).removeOccupant(pawn);
        }

        try {
            newSpace = this.getDestinationSpace(board);
        } catch (GoesHomeException e) {
            home.addPawn(pawn);
            return MoveResult.HOME;
        } catch (BadMoveException e) {
            if(start >=0){
                //need to put pawn back
                homeRow.get(start).addOccupant(pawn);
            }
            return e.getMoveResult();
        }


        return newSpace.addOccupant(pawn);

    }


    @Override
    public Space getDestinationSpace(Board board) throws BadMoveException, GoesHomeException{
        Home home = board.getHome();
        Vector<Space> homeRow = board.getHomeRows().get(pawn.getColor());

        int target = start + distance;

        if(target > homeRow.size()){
            throw new BadMoveException(MoveResult.OVERSHOT);
        }

        for(int i = start+1; (target == homeRow.size() && i <target) || (target != homeRow.size() && i <=target); i ++){
            if(homeRow.get(i).isBlockaded()){
                throw new BadMoveException(MoveResult.BLOCKED);
            }
        }

        if(target == homeRow.size()){
            throw new GoesHomeException();
        }else{
            return homeRow.get(target);
        }

    }

    @Override
    public String getStringOfMove() {
        return "Player " + pawn.getColor().toString() + ", MOVE_HOME  "+
                "Distance: " + Integer.toString(distance) + "   " +
                "Start: " + Integer.toString(start);
    }
}
