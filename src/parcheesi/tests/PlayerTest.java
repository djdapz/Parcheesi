package parcheesi.tests;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.*;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/18/17.
 */
public class PlayerTest {
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
    public void hasWonWorks() throws Exception{
        Pawn[] pawns = players[0].getPawns();
        Nest nest = nests.get(players[0].getColor());

        assertTrue(!players[0].hasWon(home));

        for(Pawn pawn: pawns){
            nest.removePawn(pawn);
            home.addPawn(pawn);
        }

        assertTrue(players[0].hasWon(home));
    }

}