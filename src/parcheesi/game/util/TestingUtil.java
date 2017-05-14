package parcheesi.game.util;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import java.util.ArrayList;
import java.util.Vector;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by devondapuzzo on 5/12/17.
 */
public class TestingUtil {

    public static void getComplexBoard(Board board, ArrayList<Player> players){
        moveOneOfEachPawnFromNestForEachPlayer(0,10, players, board);
        moveOneOfEachPawnFromNestForEachPlayer(1,17*4, players, board);
        moveOneOfEachPawnFromNestForEachPlayer(2,17*4+2, players, board);
    }


    public static void moveOneOfEachPawnFromNest(int pawnId, int distance, Player player, Board board){
        Pawn pawn0 = player.getPawns()[pawnId];
        EnterPiece enterPiece0 = new EnterPiece(pawn0);
        assertEquals(enterPiece0.run(board), MoveResult.ENTERED);
        if(distance > 0){
            MoveMain movePiece0 = new MoveMain(pawn0, pawn0.getExitSpaceId(), distance);
            MoveResult mr = movePiece0.run(board);
            assertTrue(mr.isSuccessfullMove());
        }
    }


    public static void moveOneOfEachPawnFromNestForEachPlayer(int pawnId, int distance, ArrayList<Player> players, Board board){
        for(int i =0 ;i < players.size(); i ++){
            moveOneOfEachPawnFromNest(pawnId, distance, players.get(i), board );
        }
    }

    public static void assertVectorOfSpacesAreIdentical(Vector<Space> v1, Vector<Space> v2){
        for(Space v1space: v1){
            Space v2Space = v2.get(v1space.getId());
            if(v1space.getOccupant2() != null){
                assertTrue((v1space.getOccupant1() == v2Space.getOccupant1() && v1space.getOccupant2() == v2Space.getOccupant2())
                        ||(v1space.getOccupant2() == v2Space.getOccupant1() && v1space.getOccupant1() == v2Space.getOccupant2()));
            }else if(v1space.getOccupant1()!= null){
                assertNull(v2Space.getOccupant2());
                assertEquals(v1space.getOccupant1(), v2Space.getOccupant1());
            }else{
                assertNull(v2Space.getOccupant2());
                assertNull(v2Space.getOccupant1());
            }
        }
    }
}
