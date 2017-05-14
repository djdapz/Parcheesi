package parcheesi.game.moves;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.gameplay.Game;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class MoveMainTest {
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private RulesChecker rc = new RulesChecker();

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
    public void basicMoveWorks() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Pawn testPawn = players.get(1).getPawns()[0];
        startSpace.addOccupant(testPawn);

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 10);
        Space endSpace = board.getSpaceAt(10);


        assertEquals(thisMove.run(board), MoveResult.SUCCESS);
        assertEquals(endSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getOccupant1(), null);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void  basicMoveWrapAroundWorks() throws Exception{
        Space startSpace = board.getSpaceAt(65);
        Pawn testPawn = players.get(0).getPawns()[0];
        startSpace.addOccupant(testPawn);

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Space endSpace = board.getSpaceAt(3);

        assertEquals(thisMove.run(board), MoveResult.SUCCESS);
        assertEquals(endSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getOccupant1(), null);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void  cannotMoveIfBlockadeOfOtherPlayer() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(2);
        Pawn testPawn = players.get(0).getPawns()[0];
        Pawn blockadePawn1 = players.get(1).getPawns()[0];
        Pawn blockadePawn2 = players.get(1).getPawns()[1];

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Space endSpace = board.getSpaceAt(6);

        assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(endSpace.getOccupant1(), null);
        assertEquals(startSpace.getOccupant1(), testPawn);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void  cannotMoveIfBlockadeOfSamePlayer() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(2);
        Pawn testPawn = players.get(0).getPawns()[0];
        Pawn blockadePawn1 = players.get(0).getPawns()[1];
        Pawn blockadePawn2 = players.get(0).getPawns()[2];

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Space endSpace = board.getSpaceAt(6);

        assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(endSpace.getOccupant1(), null);
        assertEquals(startSpace.getOccupant1(), testPawn);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void  cannotMoveIfBlockadeOfPlayerInDestination() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(6);
        Pawn testPawn = players.get(0).getPawns()[0];
        Pawn blockadePawn1 = players.get(0).getPawns()[1];
        Pawn blockadePawn2 = players.get(0).getPawns()[2];

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        MoveResult mr = thisMove.run(board);
        Space endSpace = board.getSpaceAt(6);

        Assert.assertEquals(mr, MoveResult.BLOCKED);
        assertEquals(endSpace.getOccupant1(), blockadePawn1);
        assertEquals(startSpace.getOccupant1(), testPawn);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void  cannotMoveIfBlockadeOfPlayerInDestinationWrapAround() throws Exception{
        Space startSpace = board.getSpaceAt(65);
        Space blockedSpace = board.getSpaceAt(2);
        Space endSpace = board.getSpaceAt(2);
        Pawn testPawn = players.get(0).getPawns()[0];
        Pawn blockadePawn1 = players.get(0).getPawns()[1];
        Pawn blockadePawn2 = players.get(0).getPawns()[2];

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(endSpace.getOccupant1(), blockadePawn1);
        assertEquals(startSpace.getOccupant1(), testPawn);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }


    @Test
    public void  boppingWorks() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space endSpace = board.getSpaceAt(5);

        //parcheesi.game.player 0 bops parcheesi.game.player 1
        Pawn bopperPawn = players.get(0).getPawns()[0];
        Pawn boppedPawn = players.get(1).getPawns()[0];
        Nest boppedNest = nests.get(boppedPawn.getColor());
        Nest bopperNest = nests.get(bopperPawn.getColor());
        boppedNest.removePawn(boppedPawn);
        bopperNest.removePawn(bopperPawn);

        endSpace.addOccupant(boppedPawn);
        startSpace.addOccupant(bopperPawn);

        MoveMain thisMove = new MoveMain(bopperPawn, startSpace.getId(), 5);

        Assert.assertEquals(thisMove.run(board), MoveResult.BOP);
        assertEquals(startSpace.getOccupant1(), null);
        assertEquals(endSpace.getOccupant2(), null);
        assertEquals(endSpace.getOccupant1(), bopperPawn);
        assertTrue(boppedNest.isAtNest(boppedPawn));
        assertTrue(rc.doesPawnAppearOnlyOnce(board, boppedPawn));
        assertTrue(rc.doesPawnAppearOnlyOnce(board, bopperPawn));
    }

    @Test
    public void  boppingDoesntWorkWhenSafeSpace() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        //Three is a safe space
        Space endSpace = board.getSpaceAt(3);

        //parcheesi.game.player 0 bops parcheesi.game.player 1
        Pawn bopperPawn = players.get(0).getPawns()[0];
        Pawn boppedPawn = players.get(1).getPawns()[0];

        Nest boppedNest = nests.get(boppedPawn.getColor());
        Nest bopperNest = nests.get(bopperPawn.getColor());
        boppedNest.removePawn(boppedPawn);
        bopperNest.removePawn(bopperPawn);

        endSpace.addOccupant(boppedPawn);
        startSpace.addOccupant(bopperPawn);

        MoveMain thisMove = new MoveMain(bopperPawn, startSpace.getId(), 3);

        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(startSpace.getOccupant1(), bopperPawn);
        assertEquals(endSpace.getOccupant1(), boppedPawn);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, boppedPawn));
        assertTrue(rc.doesPawnAppearOnlyOnce(board, bopperPawn));
    }

    @Test
    public void  goesToHomeRowAtRightTime() throws Exception{
        Pawn mainPawn = players.get(0).getPawns()[0];

        Nest testPawnNest = board.getNests().get(mainPawn.getColor());
        testPawnNest.removePawn(mainPawn);

        // 8 is enterance space so a move of 5 would be 6 -> 7 -> 8 -> home0 -> home1
        Vector<Space> homeRow = board.getHomeRows().get(mainPawn.getColor());
        Space startSpace = board.getSpaceAt(5); // 8 is enterance space
        Space endSpace = homeRow.get(1);

        startSpace.addOccupant(mainPawn);

        MoveMain thisMove = new MoveMain(mainPawn, startSpace.getId(), 5);

        assertEquals(thisMove.run(board), MoveResult.SUCCESS);
        assertEquals(startSpace.getOccupant1(), null);
        assertEquals(endSpace.getOccupant1(), mainPawn);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, mainPawn));
    }

    @Test
    public void  canBeBlockadedInHomeRow() throws Exception{
        Pawn mainPawn = players.get(0).getPawns()[0];
        Pawn blockadePawn1 = players.get(0).getPawns()[1];
        Pawn blockadePawn2 = players.get(0).getPawns()[2];

        Nest testPawnNest = board.getNests().get(mainPawn.getColor());
        testPawnNest.removePawn(mainPawn);

        // 8 is enterance space so a move of 5 would be 6 -> 7 -> 8 -> home0 -> home1
        Vector<Space> homeRow = board.getHomeRows().get(mainPawn.getColor());
        Space startSpace = board.getSpaceAt(6); // 8 is enterance space
        Space endSpace = homeRow.get(1);
        Space blockedSpot = homeRow.get(0);

        startSpace.addOccupant(mainPawn);
        blockedSpot.addOccupant(blockadePawn1);
        blockedSpot.addOccupant(blockadePawn2);

        MoveMain thisMove = new MoveMain(mainPawn, startSpace.getId(), 5);


        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(startSpace.getOccupant1(), mainPawn);
        assertEquals(endSpace.getOccupant1(), null);
        assertEquals(blockedSpot.getOccupant1(), blockadePawn1);
        assertEquals(blockedSpot.getOccupant2(), blockadePawn2);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, mainPawn));
    }

    @Test
    public void  firstSpotInHomeRowIsBolockaededWhenMoving6__REGRESSIONTEST() throws Exception{
        Pawn mainPawn = players.get(0).getPawns()[0];
        Pawn blockadePawn1 = players.get(0).getPawns()[1];
        Pawn blockadePawn2 = players.get(0).getPawns()[2];

        Nest testPawnNest = board.getNests().get(mainPawn.getColor());
        testPawnNest.removePawn(mainPawn);

        // 8 is enterance space so a move of 5 would be 6 -> 7 -> 8 -> home0 -> home1
        Vector<Space> homeRow = board.getHomeRows().get(mainPawn.getColor());
        Space startSpace = board.getSpaceAt(6); // 8 is enterance space
        Home home = board.getHome();
        Space blockedSpot = homeRow.get(0);

        startSpace.addOccupant(mainPawn);
        blockedSpot.addOccupant(blockadePawn1);
        blockedSpot.addOccupant(blockadePawn2);

        MoveMain thisMove = new MoveMain(mainPawn, startSpace.getId(), 5);


        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(startSpace.getOccupant1(), mainPawn);
        assertFalse(home.isPawnHome(mainPawn));
        assertEquals(blockedSpot.getOccupant1(), blockadePawn1);
        assertEquals(blockedSpot.getOccupant2(), blockadePawn2);
        assertTrue(rc.doesPawnAppearOnlyOnce(board, mainPawn));
    }

}