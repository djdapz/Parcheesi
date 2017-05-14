package parcheesi.game.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.gameplay.Game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class PlayerNetworkTest {
    private Board board;
    private ArrayList<Player> players;
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
    public void doMove() throws Exception{

    }
}