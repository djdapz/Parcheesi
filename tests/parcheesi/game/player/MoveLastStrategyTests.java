package tests.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.PlayerInterface;
import parcheesi.game.player.PlayerMachineLast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by devondapuzzo on 4/11/17.
 */
@SuppressWarnings("Duplicates")
public class MoveLastStrategyTests {
    //TODO - create test for bopping user out of entering parcheesi.game.player's enterance
    private Board board;
    private ArrayList<PlayerInterface> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private PlayerInterface mainPlayer;
    private Nest nest;
    private Vector<Space> homeRow;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        mainPlayer = new PlayerMachineLast();
        game.register(mainPlayer);
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
        homeRow = board.getHomeRows().get(mainPlayer.getColor());
        nest = board.getNests().get(mainPlayer.getColor());

    }
    @Test
    public void MovesPawnOutWhenAllAtHome() throws Exception{
        ArrayList<Integer> scenario1dl = new ArrayList<>();
        ArrayList<Integer> scenario2dl = new ArrayList<>();
        ArrayList<Integer> scenario3dl = new ArrayList<>();
        ArrayList<Integer> scenario4dl = new ArrayList<>();

        scenario1dl.add(5);
        scenario1dl.add(2);

        scenario2dl.add(1);
        scenario2dl.add(4);

        scenario3dl.add(2);
        scenario3dl.add(3);

        scenario4dl.add(6);
        scenario4dl.add(2);

        Move move1 = mainPlayer.doMiniMove(board, scenario1dl);
        Move move2 = mainPlayer.doMiniMove(board, scenario2dl);
        Move move3 = mainPlayer.doMiniMove(board, scenario3dl);
        Move move4 = mainPlayer.doMiniMove(board, scenario4dl);


        assertEquals(move1.getClass(), EnterPiece.class);
        assertEquals(move2.getClass(), EnterPiece.class);
        assertEquals(move3.getClass(), EnterPiece.class);
        assertNull(move4);
    }


    @Test
    public void MovesLeastAdvancedPawnWhenInMainRing() throws Exception {

        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        space3.addOccupant(pawn0);

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);



        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 20);
        assertEquals(move.getPawn(), pawn1);

        //scenario 2:
        dice.clear();
        dice.add(1);
        dice.add(4);

        move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 4);
        assertEquals(move.getStart(), 20);
        assertEquals(move.getPawn(), pawn1);

    }


    @Test
    public void MovesLeastAdvancedPawnWhenSomeInMainAndOneIsAtNest() throws Exception {
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        nest.addPawn(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 20);
        assertEquals(move.getPawn(), pawn1);


        //scenario 2 entrancePossible:
        dice.clear();
        dice.add(1);
        dice.add(4);

        move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), EnterPiece.class);
        assertEquals(move.getPawn(), pawn3);
    }

    @Test
    public void MovesLeastAdvancedPawnWhenInHomeRow() throws Exception {
        Space space0 = homeRow.get(0);
        Space space1 = homeRow.get(4);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn0);
        home.addPawn(pawn1);
        home.addPawn(pawn2);
        space0.addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn3);


        //scenario 2:
        dice.clear();
        dice.add(4);
        dice.add(5);

        space0.removeOccupant(pawn3);
        space1.addOccupant(pawn3);

        move = mainPlayer.doMiniMove(board, dice);
        assertNull(move);
    }

    @Test
    public void MovesSecondMostAdvancedPawnWhenInHomeRowAndMostAdvancedIsTooClose() throws Exception {
        Space space0 = homeRow.get(0);
        Space space1 = homeRow.get(4);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn0);
        home.addPawn(pawn1);
        space0.addOccupant(pawn2);
        space1.addOccupant(pawn3);

        //Scenario 1 - too high of dice for pawn 2
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(5);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 5);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn2);


        //Scenario 1 - pawn 2 can go
        dice.clear();
        dice.add(1);
        dice.add(3);

        move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn2);
    }

    @Test
    public void MovesSecondLeastAdvancedPawnWhenInMainRingAndLeastAdvancedIsBlockaded() throws Exception {
        Space space0 = board.getSpaceAt(28);
        Space space1 = board.getSpaceAt(32);
        Space space2 = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(30);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];
        Pawn blockingPawn0 = players.get(2).getPawns()[0];
        Pawn blockingPawn1 = players.get(2).getPawns()[1];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn3);
        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        blockedSpace.addOccupant(blockingPawn0);
        blockedSpace.addOccupant(blockingPawn1);

        //Scenario 1 - too high of dice for pawn 2
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(5);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 5);
        assertEquals(move.getStart(), 32);
        assertEquals(move.getPawn(), pawn1);
    }
}