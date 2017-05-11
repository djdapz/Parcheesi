package tests.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.*;
import parcheesi.game.enums.*;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.*;
import parcheesi.game.player.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/11/17.
 */
@SuppressWarnings("Duplicates")
public class MoveFirstStrategyTests {
    //TODO - create test for bopping user out of entering parcheesi.game.player's enterance
    private Board board;
    private ArrayList<PlayerInterface> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private PlayerInterface mainPlayer;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        mainPlayer = new PlayerMachineFirst();
        game.register(mainPlayer);
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
    }

    @Test
    public void MovesPawnOutWhenAllAtHome() throws Exception {
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
    public void MovesMostAdvancedPawnWhenInMainRing() throws Exception {
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

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn0);

    }

    @Test
    public void MovesMostAdvancedPawnWhenFormingBlockadeInMainRing() throws Exception {
        //TODO - implement blockade logic
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        space0.addOccupant(pawn0);
        space0.addOccupant(pawn3);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 0);
        assertTrue(move.getPawn() == pawn0 || move.getPawn() == pawn3);

    }

    @Test
    public void MovesMostAdvancedPawnWhenInHomeRow() throws Exception {
        //TODO - implement blockade logic
        Space space0 = board.getHomeRows().get(mainPlayer.getColor()).get(1);
        Space space1 = board.getSpaceAt(0);
        Space space2 = board.getSpaceAt(20);
        Space space3 = board.getSpaceAt(40);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        space3.addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 1);
        assertTrue(move.getPawn() == pawn0);
    }

    @Test
    public void MovesSecondMostAdvancedPawnWhenInHomeRowAndMostAdvancedIsTooClose() throws Exception {
        Space space0 = board.getHomeRows().get(mainPlayer.getColor()).get(4);
        Space space1 = board.getSpaceAt(0);
        Space space2 = board.getSpaceAt(20);
        Space space3 = board.getSpaceAt(40);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        space3.addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(5);
        dice.add(6);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 6);
        assertEquals(move.getStart(), 0);
        assertTrue(move.getPawn() == pawn1);
    }

    @Test
    public void MovesSecondMostAdvancedPawnWhenInMainRingAndMostAdvancedIsBlockaded() throws Exception {
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(40);
        Space blockedSpace = board.getSpaceAt(3);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn blockingPawn0 = players.get(1).getPawns()[0];
        Pawn blockingPawn1 = players.get(1).getPawns()[1];

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        blockedSpace.addOccupant(blockingPawn0);
        blockedSpace.addOccupant(blockingPawn1);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(3);
        dice.add(5);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 5);
        assertEquals(move.getStart(), 40);
        assertTrue(move.getPawn() == pawn1);
    }




}