package parcheesi.game;

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
    public MoveResult run(Board board) {
        int newSpot = start + distance ;
        int remaining = distance;
        int numberToGoDownHomeRow = 0;

        for(int i = remaining; i>=0; i--){
            if(board.getSpaceAt((i+start)%board.getBoardLength()).isBlockaded()){
                return MoveResult.BLOCKED;
            };

            if(board.getSpaceAt((i+start)%board.getBoardLength()).getId() == pawn.getHomeEntrance().getId()){
                numberToGoDownHomeRow = i;
                break;
            };
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


        if(moveResult == MoveResult.BOP){
            if(board.getSpaceAt(newSpot).isSafeSpace()){
                board.getSpaceAt(start).addOccupant(pawn);
                return MoveResult.BLOCKED;
            }
            Pawn boppedPawn = board.getSpaceAt(newSpot).getOccupant1();
            HashMap<Color, Nest> nests = board.getNests();
            Nest bopToNest = nests.get(boppedPawn.getColor());
            bopToNest.addPawn(boppedPawn);
            board.getSpaceAt(newSpot).removeOccupant(boppedPawn);
            board.getSpaceAt(newSpot).addOccupant(pawn);
            return MoveResult.BOP;
        }else{
            return moveResult;
        }
    }
}
