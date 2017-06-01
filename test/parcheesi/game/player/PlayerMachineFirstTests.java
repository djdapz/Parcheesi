package parcheesi.game.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.gameplay.Game;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.machine.PlayerMachine;
import parcheesi.game.player.machine.PlayerMachineFirst;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/11/17.
 */
@SuppressWarnings("Duplicates")
public class PlayerMachineFirstTests {
    //TODO - create test for bopping user out of entering parcheesi.game.player's enterance
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private PlayerMachine mainPlayer;
    private PlayerMachine playerBlocking;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        mainPlayer = new PlayerMachineFirst();
        playerBlocking = new PlayerMachineFirst();
        game.register(mainPlayer);
        game.register(playerBlocking);
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
    }

    @Test
    public void MovesPawnOutWhenAllAtHome() throws Exception{
        ArrayList<Integer> scenario1dl = new ArrayList<>();
        ArrayList<Integer> scenario2dl = new ArrayList<>();
        ArrayList<Integer> scenario3dl = new ArrayList<>();

        scenario1dl.add(5);
        scenario1dl.add(2);

        scenario2dl.add(1);
        scenario2dl.add(4);

        scenario3dl.add(2);
        scenario3dl.add(3);

        Move move1 = mainPlayer.doMiniMove(board, scenario1dl);
        Move move2 = mainPlayer.doMiniMove(board, scenario2dl);
        Move move3 = mainPlayer.doMiniMove(board, scenario3dl);

        assertEquals(move1.getClass(), EnterPiece.class);
        assertEquals(move2.getClass(), EnterPiece.class);
        assertEquals(move3.getClass(), EnterPiece.class);
    }

    @Test(expected= NoMoveFoundException.class)
    public void MovesPawnOutWhenAllAtHomeButDiceDontAllowEntry() throws Exception{
        ArrayList<Integer> scenariodl = new ArrayList<>();

        scenariodl.add(6);
        scenariodl.add(2);

        Move move1 = mainPlayer.doMiniMove(board, scenariodl);
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
        Pawn blockingPawn0 = playerBlocking.getPawns()[0];
        Pawn blockingPawn1 = playerBlocking.getPawns()[1];

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


    @Test
    public void doesNotMoveBlockadeToetherOnDoublesRollWhenBlockadeIsMostAdvanced() throws Exception {
        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn0b = playerBlocking.getPawns()[0];
        Pawn pawn1b = playerBlocking.getPawns()[1];

        Move enter0 = new EnterPiece(pawn0);
        Move enter1 = new EnterPiece(pawn1);
        assertEquals(MoveResult.ENTERED, enter0.run(board));
        assertEquals(MoveResult.ENTERED, enter1.run(board));

        Space blockedSpace = board.getSpaceAt(pawn0.getExitSpaceId() + 6);
        blockedSpace.addOccupant(pawn0b);
        blockedSpace.addOccupant(pawn1b);
        assertTrue(blockedSpace.isBlockaded());

        assertEquals(RulesChecker.findBlockades(board, mainPlayer).get(0), pawn0.getExitSpace(board));
        assertEquals(RulesChecker.findBlockades(board, playerBlocking).get(0), blockedSpace);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(5);
        dice.add(5);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));
    }

    @Test
    public void movesBlockadeOnDoubles6AND1WhenOvershootOccursOnLargerDouble__REGRESSION_TEST() throws Exception {
        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        Move enter0 = new EnterPiece(pawn0);
        Move enter1 = new EnterPiece(pawn1);
        Move enter2 = new EnterPiece(pawn2);
        Move enter3 = new EnterPiece(pawn3);


        Move moveToHome0 = new MoveMain(pawn0, pawn0.getExitSpaceId(), 17*4+1);
        Move moveToHome1 = new MoveMain(pawn1, pawn1.getExitSpaceId(), 17*4+1);
        Move moveToHome2 = new MoveMain(pawn2, pawn2.getExitSpaceId(), 17*4+3);
        Move moveToHome3 = new MoveMain(pawn3, pawn3.getExitSpaceId(), 17*4+3);

        assertEquals(MoveResult.ENTERED, enter2.run(board));
        assertEquals(MoveResult.ENTERED, enter3.run(board));
        assertEquals(MoveResult.HOME, moveToHome2.run(board));
        assertEquals(MoveResult.HOME, moveToHome3.run(board));

        assertEquals(MoveResult.ENTERED, enter0.run(board));
        assertEquals(MoveResult.ENTERED, enter1.run(board));
        assertEquals(MoveResult.SUCCESS, moveToHome0.run(board));
        assertEquals(MoveResult.SUCCESS, moveToHome1.run(board));

        assertEquals(board.findPawn(pawn0), board.getHomeRows().get(pawn0.getColor()).get(5));
        assertEquals(RulesChecker.findBlockades(board, mainPlayer).get(0), board.getHomeRows().get(pawn0.getColor()).get(5));

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(6);
        dice.add(6);
        dice.add(1);
        dice.add(1);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));
    }



}