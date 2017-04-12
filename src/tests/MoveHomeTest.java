package tests;

import game.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 4/11/17.
 */
public class MoveHomeTest {
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
    public void pawnCanMoveWithinHomeRow() throws Exception{

        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Pawn testPawn = players[0].getPawns()[0];
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());

        Space startSpace = testHomeRow.get(0);
        Space endSpace = testHomeRow.get(3);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getId(), 0);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 3);
        Assert.assertEquals(thisMove.run(board), MoveResult.SUCCESS);

        assertEquals(endSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getOccupant1(), null);
    }

    @Test
    public void pawnGoesHomeWhenRightNumberSelected() throws Exception{
        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Pawn testPawn = players[0].getPawns()[0];
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());
        Home home = board.getHome();

        Space startSpace = testHomeRow.get(4);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 2);
        Assert.assertEquals(thisMove.run(board), MoveResult.HOME);

        assertTrue(home.isPawnHome(testPawn));
        assertEquals(startSpace.getOccupant1(), null);
    }

    @Test
    public void pawnCannotGoHomeOnOvershoot() throws Exception{
        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Pawn testPawn = players[0].getPawns()[0];
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());
        Home home = board.getHome();

        Space startSpace = testHomeRow.get(4);
        Space endSpace = testHomeRow.get(3);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 3);
        Assert.assertEquals(thisMove.run(board), MoveResult.OVERSHOT);

        assertTrue(!home.isPawnHome(testPawn));
        assertEquals(startSpace.getOccupant1(), testPawn);
    }

    @Test
    public void pawnCannotMovePastBlockadeInHomeRow() throws Exception{
        Pawn testPawn = players[0].getPawns()[0];
        Pawn blockingPawn1 = players[0].getPawns()[1];
        Pawn blockingPawn2 = players[0].getPawns()[2];

        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());
        Home home = board.getHome();

        Space startSpace = testHomeRow.get(0);
        Space endSpace = testHomeRow.get(3);
        Space blockedSpace = testHomeRow.get(2);

        blockedSpace.addOccupant(blockingPawn1);
        blockedSpace.addOccupant(blockingPawn2);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);
        assertEquals(blockedSpace.getOccupant1(), blockingPawn1);
        assertEquals(blockedSpace.getOccupant2(), blockingPawn2);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 3);
        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);

        assertTrue(!home.isPawnHome(testPawn));
        assertEquals(startSpace.getOccupant1(), testPawn);
        assertEquals(blockedSpace.getOccupant1(), blockingPawn1);
        assertEquals(blockedSpace.getOccupant2(), blockingPawn2);
    }

}