package parcheesi.game.gameplay;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by devondapuzzo on 5/1/17.
 */
@SuppressWarnings("Duplicates")
public class GameTest {
    //TODO - create test for bopping user out of entering parcheesi.game.player's enterance
    private ArrayList<Player> players;
    private Game game;


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void allPawnsAtHomeAtStart() throws Exception {
        game = new Game();
        game.start();
        players = game.getPlayers();
        Board board = game.getBoard();

        for(Player player: players){
            for(Pawn pawn: player.getPawns()){
                assertTrue(board.isAtNest(pawn));
            }
        }
    }

    @Test
    public void getWinner() throws Exception {
        Nest nest;
        game = new Game();
        game.start();
        players = game.getPlayers();
        Home home = game.getBoard().getHome();

        for(Player player: players){
            nest = game.getBoard().getNests().get(player.getColor());
            for(Pawn pawn: player.getPawns()){
                nest.removePawn(pawn);
            }
        }

        Player player0 = players.get(0);

        home.addPawn(player0.getPawns()[0]);
        assertTrue(!game.isWinner());
        home.addPawn(player0.getPawns()[1]);
        assertTrue(!game.isWinner());
        home.addPawn(player0.getPawns()[2]);
        assertTrue(!game.isWinner());
        home.addPawn(player0.getPawns()[3]);
        assertTrue(game.isWinner());
        assertEquals(player0, game.getWinner());
    }
}
