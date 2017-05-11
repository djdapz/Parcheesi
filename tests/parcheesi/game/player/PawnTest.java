package tests.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.gameplay.Game;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.PlayerInterface;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/17/17.
 */
public class PawnTest {

    private Board board;
    private ArrayList<PlayerInterface> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    List<Integer> moves;

    @SuppressWarnings("Duplicates")
    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();

        moves = new ArrayList<>();
    }


    @Test
    public void canMoveFailsWithBlockadeInHomeRow() throws Exception {
        Pawn pawn = players.get(0).getPawns()[0];
        Pawn pawnBlocking1 = players.get(0).getPawns()[2];
        Pawn pawnBlocking2 = players.get(0).getPawns()[1];

        moves.add(1);
        moves.add(2);

        Vector<Space> hr = board.getHomeRows().get(pawn.getColor());

        hr.get(0).addOccupant(pawn);

        assertTrue(pawn.canMove(moves, board));

        hr.get(1).addOccupant(pawnBlocking1);
        hr.get(1).addOccupant(pawnBlocking2);
        assertTrue(!pawn.canMove(moves, board));


        hr.get(1).removeOccupant(pawnBlocking1);
        hr.get(1).removeOccupant(pawnBlocking2);
        assertTrue(pawn.canMove(moves, board));


    }

    @Test
    public void canMoveFailsIfHome() throws Exception {

        Pawn pawn = players.get(0).getPawns()[0];
        home.addPawn(pawn);
        moves.add(1);
        moves.add(3);
        moves.add(5);
        moves.add(6);
        assertTrue(!pawn.canMove(moves, board));



    }

    @Test
    public void canMoveFailsOnMovementIntoHomeRowWithBlockade() throws Exception {
        Pawn pawn = players.get(0).getPawns()[0];
        Pawn pawnBlocking1 = players.get(0).getPawns()[1];
        Pawn pawnBlocking2 = players.get(0).getPawns()[2];

        moves.add(6);
        moves.add(5);

        Vector<Space> hr = board.getHomeRows().get(pawn.getColor());
        Vector<Space> mainRing = board.getSpaces();

        mainRing.get(pawn.getHomeEntranceId()-1).addOccupant(pawn);
        assertTrue(pawn.canMove(moves, board));

        hr.get(3).addOccupant(pawnBlocking1);
        hr.get(3).addOccupant(pawnBlocking2);
        assertTrue(!pawn.canMove(moves, board));

        moves.add(1);
        assertTrue(pawn.canMove(moves, board));

        moves.remove(moves.indexOf(1));
        assertTrue(!pawn.canMove(moves, board));


        hr.get(3).removeOccupant(pawnBlocking1);
        hr.get(3).removeOccupant(pawnBlocking2);
        assertTrue(pawn.canMove(moves, board));
    }

    @Test
    public void canMoveFilsOnBlockadeInRegularRing() throws Exception {

    }

    @Test
    public void canMoveFailsOnOvershoot() throws Exception {

    }

    @Test
    public void canMoveFailsIfCannotEnter() throws Exception {

    }




}