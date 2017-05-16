package parcheesi.game.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.enums.Color;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.Move;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 5/16/17.
 */
public class PlayerNetworkTest {

    private Board board;
    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.start();
        board = game.getBoard();

    }
    @Test
    public void startGame() throws Exception {
        Player actualPlayer = new PlayerMachineFirst();
        PlayerNetwork networkedPlayer = new PlayerNetwork();

        actualPlayer.setName("Steven");

        PlayerClient pc = new PlayerClient(networkedPlayer.getPortNumber(), actualPlayer);
        new Thread(pc).start();

        assert(networkedPlayer.startGame(Color.RED).equals("Steven"));

        assertTrue(1 ==1);
    }

    @Test
    public void doMove() throws Exception {
        Player actualPlayer = new PlayerMachineFirst();
        actualPlayer.setName("Steven");
        PlayerNetwork playerNetwork = new PlayerNetwork();

        PlayerClient pc = new PlayerClient(playerNetwork.getPortNumber(), actualPlayer);
        new Thread(pc).start();

        ArrayList<Integer> dice = new ArrayList<>();

        dice.add(5);
        dice.add(5);
        dice.add(2);
        dice.add(2);

        playerNetwork.startGame(Color.RED);
        ArrayList<Move> moves = playerNetwork.doMove(board, dice);

        assertEquals(4, moves.size());
    }

}