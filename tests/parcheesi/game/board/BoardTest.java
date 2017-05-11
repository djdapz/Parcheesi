package tests.board;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import parcheesi.game.gameplay.Game;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.PlayerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static junit.framework.TestCase.*;

/**
 * Created by devondapuzzo on 4/10/17.
 */
@SuppressWarnings("Duplicates")
public class BoardTest {


    private Board board;
    private ArrayList<PlayerInterface> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;


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
    public void copyBoardTest() throws Exception{
        Board board = new Board();
        Pawn pawnMainRing = new Pawn(0, Color.BLUE);
        Pawn pawnHomeRow = new Pawn(1, Color.BLUE);
        Pawn pawnNest = new Pawn(3, Color.BLUE);

        board.getSpaceAt(0).addOccupant(pawnMainRing);
        board.getHomeRows().get(pawnHomeRow.getColor()).get(0).addOccupant(pawnHomeRow);
        board.getNests().get(pawnNest.getColor()).addPawn(pawnNest);

        Board boardCopy = new Board(board);

        boardCopy.getSpaces().get(0).removeOccupant(pawnMainRing);
        boardCopy.getSpaces().get(1).addOccupant(pawnMainRing);

        boardCopy.getHomeRows().get(pawnHomeRow.getColor()).get(0).removeOccupant(pawnHomeRow);
        boardCopy.getHomeRows().get(pawnHomeRow.getColor()).get(1).addOccupant(pawnHomeRow);

        boardCopy.getNests().get(pawnNest.getColor()).removePawn(pawnNest);
        boardCopy.getHome().addPawn(pawnNest);

        //make sure original parcheesi.game.board is as expected
        assertEquals(board.findPawn(pawnMainRing), board.getSpaceAt(0));
        assertEquals(board.findPawn(pawnHomeRow), board.getHomeRows().get(pawnHomeRow.getColor()).get(0));
        assertTrue(board.getNests().get(pawnNest.getColor()).isAtNest(pawnNest));
        assertTrue(!board.getHome().isPawnHome(pawnNest));

        //make sure new parcheesi.game.board is unchanged
        assertEquals(boardCopy.findPawn(pawnMainRing), boardCopy.getSpaceAt(1));
        assertEquals(boardCopy.findPawn(pawnHomeRow), boardCopy.getHomeRows().get(pawnHomeRow.getColor()).get(1));
        assertTrue(!boardCopy.getNests().get(pawnNest.getColor()).isAtNest(pawnNest));
        assertTrue(boardCopy.getHome().isPawnHome(pawnNest));

        //make sure original parcheesi.game.board is unchanged
        assertEquals(board.findPawn(pawnMainRing), board.getSpaceAt(0));
        assertEquals(board.findPawn(pawnHomeRow), board.getHomeRows().get(pawnHomeRow.getColor()).get(0));
        assertTrue(board.getNests().get(pawnNest.getColor()).isAtNest(pawnNest));
        assertTrue(!board.getHome().isPawnHome(pawnNest));
    }

    @Test
    public void findMostAdvancedPawnWithAllPawnsInRingRoad() throws Exception {
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        PlayerInterface player = players.get(0);
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

        player = players.get(1);
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


        PlayerInterface player = players.get(3);
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

    @Test
    public void findMostAdvancedPawnWithHRExclusion() throws Exception {

        PlayerInterface player = players.get(0);
        Pawn testPawn0 = player.getPawns()[0];
        Pawn testPawn1 = player.getPawns()[1];
        Pawn testPawn2 = player.getPawns()[2];
        Pawn testPawn3 = player.getPawns()[3];

        Vector<Space> homeRow = board.getHomeRows().get(testPawn0.getColor());
        Space space0 = homeRow.get(4);
        Space space1 = homeRow.get(2);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);

        ArrayList<Space> exclusionList= new ArrayList<Space>();
        exclusionList.add(space0);

        Assert.assertEquals(board.findMostAdvancedPawn(player), space0);
        Assert.assertEquals(board.findMostAdvancedPawn(player, exclusionList), space1);
    }


    @Test
    public void findMostAdvancedPawnWithHRExclusionAndNextInMainRing() throws Exception {

        PlayerInterface player = players.get(0);
        Pawn testPawn0 = player.getPawns()[0];
        Pawn testPawn1 = player.getPawns()[1];
        Pawn testPawn2 = player.getPawns()[2];
        Pawn testPawn3 = player.getPawns()[3];

        Vector<Space> homeRow = board.getHomeRows().get(testPawn0.getColor());
        Space space0 = homeRow.get(4);
        Space space1 = board.getSpaceAt(0);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);

        ArrayList<Space> exclusionList= new ArrayList<Space>();
        exclusionList.add(space0);

        Assert.assertEquals(board.findMostAdvancedPawn(player), space0);
        Assert.assertEquals(board.findMostAdvancedPawn(player, exclusionList), space1);

    }


    @Test
    public void findMostAdvancedPawnWithMainRowExclusion() throws Exception {
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        PlayerInterface player = players.get(0);
        Pawn testPawn0 = player.getPawns()[0];
        Pawn testPawn1 = player.getPawns()[1];
        Pawn testPawn2 = player.getPawns()[2];
        Pawn testPawn3 = player.getPawns()[3];

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);

        ArrayList<Space> exclusionList= new ArrayList<Space>();
        exclusionList.add(space0);

        Assert.assertEquals(board.findMostAdvancedPawn(player), space0);
        Assert.assertEquals(board.findMostAdvancedPawn(player, exclusionList), space3);
    }
}