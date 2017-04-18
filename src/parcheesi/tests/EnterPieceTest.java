package parcheesi.tests;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.*;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 4/11/17.
 */
public class EnterPieceTest {
    //TODO - create test for bopping user out of entering player's enterance
    private Board board;
    private Player[] players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;

    @SuppressWarnings("Duplicates")
    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
    }

    @Test
    public void canBopOnExitFromSafeSpace() throws Exception {
        Player p1 = players[0];
        Player p2 = players[1];

        Pawn p1Pawn = p1.getPawns()[0];
        Pawn p2Pawn = p2.getPawns()[0];

        Space p1ExitSpace = p1Pawn.getExitSpace();
        Nest p1Nest = board.getNests().get(p1Pawn.getColor());


        p1ExitSpace.addOccupant(p2Pawn);
        EnterPiece enterPiece = new EnterPiece(p1Pawn);

        assertTrue(p1Nest.isAtNest(p1Pawn));
        assertEquals(p1ExitSpace.getOccupant1(), p2Pawn);

        enterPiece.run(board);
        assertTrue(!p1Nest.isAtNest(p1Pawn));
        assertEquals(p1ExitSpace.getOccupant1(), p1Pawn);


    }

    @Test
    public void cannotEnterOnBlockade() throws Exception {
        Player p1 = players[0];
        Player p2 = players[1];

        Pawn p1Pawn = p1.getPawns()[0];
        Pawn p2Pawn1 = p2.getPawns()[0];
        Pawn p2Pawn2 = p2.getPawns()[1];

        Space p1ExitSpace = p1Pawn.getExitSpace();
        Nest p1Nest = board.getNests().get(p1Pawn.getColor());


        p1ExitSpace.addOccupant(p2Pawn1);
        p1ExitSpace.addOccupant(p2Pawn2);
        EnterPiece enterPiece = new EnterPiece(p1Pawn);

        assertTrue(p1Nest.isAtNest(p1Pawn));
        assertEquals(p1ExitSpace.getOccupant1(), p2Pawn1);
        assertEquals(p1ExitSpace.getOccupant2(), p2Pawn2);
        assertTrue(p1ExitSpace.isBlockaded());

        assertEquals(enterPiece.run(board), MoveResult.BLOCKED);
        assertEquals(p1ExitSpace.getOccupant1(), p2Pawn1);
        assertEquals(p1ExitSpace.getOccupant2(), p2Pawn2);
        assertTrue(p1ExitSpace.isBlockaded());
    }

    @Test
    public void getDistance() throws Exception {
        Player p1 = players[0];

        Pawn p1Pawn = p1.getPawns()[0];
        EnterPiece enterPiece = new EnterPiece(p1Pawn);

        assertEquals(enterPiece.getDistance(), 0);
    }



}