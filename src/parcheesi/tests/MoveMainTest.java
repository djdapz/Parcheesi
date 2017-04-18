package parcheesi.tests;

import parcheesi.game.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class MoveMainTest {
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
    public void basicMoveWorks() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Pawn testPawn = players[1].getPawns()[0];
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 10);
        Assert.assertEquals(thisMove.run(board), MoveResult.SUCCESS);
        Space endSpace = board.getSpaceAt(10);
        assertEquals(endSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getOccupant1(), null);
    }

    @Test
    public void  basicMoveWrapAroundWorks() throws Exception{
        Space startSpace = board.getSpaceAt(65);
        Pawn testPawn = players[0].getPawns()[0];
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Assert.assertEquals(thisMove.run(board), MoveResult.SUCCESS);
        Space endSpace = board.getSpaceAt(3);
        assertEquals(endSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getOccupant1(), null);
    }

    @Test
    public void  cannotMoveIfBlockadeOfOtherPlayer() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(2);
        Pawn testPawn = players[0].getPawns()[0];
        Pawn blockadePawn1 = players[1].getPawns()[0];
        Pawn blockadePawn2 = players[1].getPawns()[1];

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        Space endSpace = board.getSpaceAt(6);
        assertEquals(endSpace.getOccupant1(), null);
        assertEquals(startSpace.getOccupant1(), testPawn);
    }

    @Test
    public void  cannotMoveIfBlockadeOfSamePlayer() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(2);
        Pawn testPawn = players[0].getPawns()[0];
        Pawn blockadePawn1 = players[0].getPawns()[1];
        Pawn blockadePawn2 = players[0].getPawns()[2];

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        Space endSpace = board.getSpaceAt(6);
        assertEquals(endSpace.getOccupant1(), null);
        assertEquals(startSpace.getOccupant1(), testPawn);
    }

    @Test
    public void  cannotMoveIfBlockadeOfPlayerInDestination() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(6);
        Pawn testPawn = players[0].getPawns()[0];
        Pawn blockadePawn1 = players[0].getPawns()[1];
        Pawn blockadePawn2 = players[0].getPawns()[2];

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        MoveResult mr = thisMove.run(board);
        Assert.assertEquals(mr, MoveResult.BLOCKED);
        Space endSpace = board.getSpaceAt(6);
        assertEquals(endSpace.getOccupant1(), blockadePawn1);
        assertEquals(startSpace.getOccupant1(), testPawn);
    }

    @Test
    public void  cannotMoveIfBlockadeOfPlayerInDestinationWrapAround() throws Exception{
        Space startSpace = board.getSpaceAt(65);
        Space blockedSpace = board.getSpaceAt(2);
        Space endSpace = board.getSpaceAt(2);
        Pawn testPawn = players[0].getPawns()[0];
        Pawn blockadePawn1 = players[0].getPawns()[1];
        Pawn blockadePawn2 = players[0].getPawns()[2];

        blockedSpace.addOccupant(blockadePawn1);
        blockedSpace.addOccupant(blockadePawn2);
        startSpace.addOccupant(testPawn);

        MoveMain thisMove = new MoveMain(testPawn, startSpace.getId(), 6);
        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(endSpace.getOccupant1(), blockadePawn1);
        assertEquals(startSpace.getOccupant1(), testPawn);
    }


    @Test
    public void  boppingWorks() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        Space endSpace = board.getSpaceAt(5);

        //player 0 bops player 1
        Pawn bopperPawn = players[0].getPawns()[0];
        Pawn boppedPawn = players[1].getPawns()[0];
        Nest boppedNest = nests.get(boppedPawn.getColor());
        boppedNest.removePawn(boppedPawn);
        boppedNest.removePawn(bopperPawn);

        endSpace.addOccupant(boppedPawn);
        startSpace.addOccupant(bopperPawn);

        MoveMain thisMove = new MoveMain(bopperPawn, startSpace.getId(), 5);

        Assert.assertEquals(thisMove.run(board), MoveResult.BOP);
        assertEquals(startSpace.getOccupant1(), null);
        assertEquals(endSpace.getOccupant2(), null);
        assertEquals(endSpace.getOccupant1(), bopperPawn);
        assertTrue(boppedNest.isAtNest(boppedPawn));
    }

    @Test
    public void  boppingDoesntWorkWhenSafeSpace() throws Exception{
        Space startSpace = board.getSpaceAt(0);
        //Three is a safe space
        Space endSpace = board.getSpaceAt(3);

        //player 0 bops player 1
        Pawn bopperPawn = players[0].getPawns()[0];
        Pawn boppedPawn = players[1].getPawns()[0];

        endSpace.addOccupant(boppedPawn);
        startSpace.addOccupant(bopperPawn);

        MoveMain thisMove = new MoveMain(bopperPawn, startSpace.getId(), 3);

        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);
        assertEquals(startSpace.getOccupant1(), bopperPawn);
        assertEquals(endSpace.getOccupant1(), boppedPawn);
    }

    @Test
    public void  goesToHomeRowAtRightTime() throws Exception{
        Pawn mainPawn = players[0].getPawns()[0];

        // 8 is enterance space so a move of 5 would be 6 -> 7 -> 8 -> home0 -> home1
        Vector<Space> homeRow = board.getHomeRows().get(mainPawn.getColor());
        Space startSpace = board.getSpaceAt(6); // 8 is enterance space
        Space endSpace = homeRow.get(2);

        startSpace.addOccupant(mainPawn);

        MoveMain thisMove = new MoveMain(mainPawn, startSpace.getId(), 5);

        Assert.assertEquals(thisMove.run(board), MoveResult.SUCCESS);
        assertEquals(startSpace.getOccupant1(), null);
        assertEquals(endSpace.getOccupant1(), mainPawn);
    }

    @Test
    public void  canBeBlockadedInHomeRow() throws Exception{
        Pawn mainPawn = players[0].getPawns()[0];
        Pawn blockadePawn1 = players[0].getPawns()[1];
        Pawn blockadePawn2 = players[0].getPawns()[2];

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
    }

}