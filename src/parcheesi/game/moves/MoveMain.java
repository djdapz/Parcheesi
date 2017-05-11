package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.player.Pawn;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class MoveMain implements Move {
    private Pawn pawn;
    private int start;
    private int distance;

    public MoveMain(Pawn pawn, int start, int distance) {
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
        int newSpot = (start + distance) %board.getBoardLength();
        int remaining = distance;
        int numberToGoDownHomeRow = 0;

        MoveResult validityTest = this.isValid(board);

        if(validityTest == MoveResult.OVERSHOT || validityTest ==MoveResult.BLOCKED){
            return validityTest;
        }

        if(start <= pawn.getHomeEntranceId() && newSpot >=pawn.getHomeEntranceId()){
            numberToGoDownHomeRow = start + distance - pawn.getHomeEntranceId();
        }


        if(numberToGoDownHomeRow > 0){
            Vector<Space> homeRow = board.getHomeRows().get(pawn.getColor());

            MoveHome moveHome = new MoveHome(pawn, -1, numberToGoDownHomeRow);
            MoveResult mResult= moveHome.run(board);

            if(mResult == MoveResult.SUCCESS || mResult == MoveResult.HOME){
                board.getSpaceAt(start).removeOccupant(pawn);
            }
            return mResult;
        }


        newSpot = (start+distance) % board.getBoardLength();

        board.getSpaceAt(start).removeOccupant(pawn);
        MoveResult moveResult = board.getSpaceAt(newSpot).addOccupant(pawn);

        if(moveResult == MoveResult.BLOCKED){
            //if bop was blocked then replace the occupant
            board.getSpaceAt(start).addOccupant(pawn);
        }


        if(moveResult == MoveResult.BOP){
            //add bopped pawn to its nest
            Pawn boppedPawn = board.getSpaceAt(newSpot).getOccupant2();
            HashMap<Color, Nest> nests = board.getNests();
            Nest bopToNest = nests.get(boppedPawn.getColor());
            bopToNest.addPawn(boppedPawn);


            board.getSpaceAt(newSpot).removeOccupant(boppedPawn);
            return MoveResult.BOP;
        }else{
            return moveResult;
        }
    }

    @Override
    public MoveResult isValid(Board board){
        assert(distance < board.getBoardLength());

        int newSpot = (start + distance) % board.getBoardLength() ;
        int remaining = distance;
        int numberToGoDownHomeRow = 0;


        Space thisSpace = board.getSpaceAt(start);
        Space newSpace = board.getSpaceAt(newSpot);

        if(newSpace.getOccupant1() != null){
            if(newSpace.getOccupant1().getColor() != pawn.getColor()){
                if(newSpace.isSafeSpace()){
                    return MoveResult.BLOCKED;
                };
            }
        }

        if(thisSpace == pawn.getHomeEntrance(board)){
            numberToGoDownHomeRow = remaining;
        }else{
            for(int i = start+1; remaining > 0; remaining--){
                if(board.getSpaceAt(i % board.getBoardLength()).isBlockaded()) {
                    return MoveResult.BLOCKED;
                }

                if(i == pawn.getHomeEntranceId()){
                    numberToGoDownHomeRow = remaining;
                    break;
                }
                i++;
            }
        }

        if(numberToGoDownHomeRow > 0){
            MoveHome moveHome = new MoveHome(pawn, -1, numberToGoDownHomeRow);
            return moveHome.isValid(board);
        }else{
            return MoveResult.SUCCESS;
        }

    }

    @Override
    public String getStringOfMove() {
        return "PlayerInterface " + pawn.getColor().toString() + ", MAIN_MOVE  "+
                "Distance: " + Integer.toString(distance) + "   " +
                "Start: " + Integer.toString(start);
    }
}
