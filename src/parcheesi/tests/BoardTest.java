package parcheesi.tests;

import org.junit.Before;
import parcheesi.game.*;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class BoardTest {

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
    public void getBoardLength() throws Exception {
        Assert.assertEquals(board.getSpaces().size(), board.getBoardLength());

    }

    @Test
    public void findPawn() throws Exception {
        Board board = new Board();
        Space space = board.getSpaceAt(10);
        Pawn testPawn = new Pawn(1, Color.GREEN);

        space.addOccupant(testPawn);

        Assert.assertEquals(board.findPawn(testPawn), space);
    }

    @Test
    public void findMostAdvancedPawnWithAllPawnsInRingRoad() throws Exception {
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        Player player = players[0];
        Pawn testPawn0 = player.getPawns()[0];
        Pawn testPawn1 = player.getPawns()[1];
        Pawn testPawn2 = player.getPawns()[2];
        Pawn testPawn3 = player.getPawns()[3];

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);

        Assert.assertEquals(board.findMostAdvancedPawn(player), space0);

        space0.removeOccupant(testPawn0);
        space1.removeOccupant(testPawn1);
        space2.removeOccupant(testPawn2);
        space3.removeOccupant(testPawn3);

        player = players[1];
        testPawn0 = player.getPawns()[0];
        testPawn1 = player.getPawns()[1];
        testPawn2 = player.getPawns()[2];
        testPawn3 = player.getPawns()[3];

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);

        Assert.assertEquals(board.findMostAdvancedPawn(player), space1);
    }

    @Test
    public void findMostAdvancedPawnWithPawnInHomeRow() throws Exception {


        Player player = players[3];
        Pawn testPawn0 = player.getPawns()[0];
        Pawn testPawn1 = player.getPawns()[1];
        Pawn testPawn2 = player.getPawns()[2];
        Pawn testPawn3 = player.getPawns()[3];

        Vector<Space> homeRow = board.getHomeRows().get(testPawn0.getColor());
        Space space0 = homeRow.get(2);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);


        Assert.assertEquals(board.findMostAdvancedPawn(player), space0);
    }
}