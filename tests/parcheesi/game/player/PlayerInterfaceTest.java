package tests.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.gameplay.Game;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.PlayerInterface;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/18/17.
 */
public class PlayerInterfaceTest {
    private Board board;
    private ArrayList<PlayerInterface> players;
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
        Pawn[] pawns = players.get(0).getPawns();
        Nest nest = nests.get(players.get(0).getColor());

        assertTrue(!players.get(0).hasWon(home));

        for(Pawn pawn: pawns){
            nest.removePawn(pawn);
            home.addPawn(pawn);
        }

        assertTrue(players.get(0).hasWon(home));
    }

}