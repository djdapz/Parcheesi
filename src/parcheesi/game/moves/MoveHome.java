package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.player.Pawn;
import parcheesi.game.board.Space;
import parcheesi.game.enums.MoveResult;

import java.util.Vector;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class MoveHome implements Move {

    private Pawn pawn;
    private int start;
    private int distance;

    public MoveHome(Pawn pawn, int start, int distance) {
        this.pawn=pawn;
        this.start=start;
        this.distance=distance;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public Pawn getPawn() {
        return pawn;
    }

    @Override
    public MoveResult run(Board board) {
        Home home = board.getHome();
        Vector<Space> homeRow = board.getHomeRows().get(pawn.getColor());
        int target = start + distance;


        MoveResult valididityCheck = this.isValid(board);

        if(valididityCheck != MoveResult.SUCCESS){
            return valididityCheck;
        }

        //so we can move into the home row from outside of the homerow using this function.
        if(start >=0){
            homeRow.get(start).removeOccupant(pawn);
        }

        if(target == homeRow.size()){
            home.addPawn(pawn);
            return MoveResult.HOME;
        }else{
            return homeRow.get(target).addOccupant(pawn);
        }
    }



    public MoveResult isValid(Board board){
        Home home = board.getHome();
        Vector<Space> homeRow = board.getHomeRows().get(pawn.getColor());

        int target = start + distance;
        if(target > homeRow.size()){
            return MoveResult.OVERSHOT;
        }

        //TODO -Possible refactor, combine similar code with MoveMain in parent function?

        for(int i = start+1; (target == homeRow.size() && i <target) || (target != homeRow.size() && i <=target); i ++){
            if(homeRow.get(i).isBlockaded()){
                return MoveResult.BLOCKED;
            }
        }

        return MoveResult.SUCCESS;
    }

    @Override
    public String getStringOfMove() {
        return "PlayerInterface " + pawn.getColor().toString() + ", MOVE_HOME  "+
                "Distance: " + Integer.toString(distance) + "   " +
                "Start: " + Integer.toString(start);
    }
}
