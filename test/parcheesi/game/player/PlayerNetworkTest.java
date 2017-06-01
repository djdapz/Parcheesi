package parcheesi.game.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.enums.Color;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.Move;
import parcheesi.game.player.machine.PlayerMachineFirst;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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

        ClientPlayer pc = new ClientPlayer(networkedPlayer.getPortNumber(), actualPlayer);
        new Thread(pc).start();

        assert(networkedPlayer.startGame(Color.RED).equals("Steven"));
    }


    @Test
    public void startGameTwoNetworked() throws Exception {

        Player actualPlayer1 = new PlayerMachineFirst();
        actualPlayer1.setName("Even");
        PlayerNetwork playerNetwork1 = new PlayerNetwork();

        Player actualPlayer2= new PlayerMachineFirst();
        actualPlayer2.setName("Steven");
        PlayerNetwork playerNetwork2 = new PlayerNetwork();

        ClientPlayer pc1 = new ClientPlayer(playerNetwork1.getPortNumber(), actualPlayer1);
        new Thread(pc1).start();

        ClientPlayer pc2 = new ClientPlayer(playerNetwork2.getPortNumber(), actualPlayer2);
        new Thread(pc2).start();

        assert(playerNetwork1.startGame(Color.RED).equals("Even"));
        assert(playerNetwork1.startGame(Color.RED).equals("Steven"));
    }


    @Test
    public void doMove() throws Exception {
        Player actualPlayer = new PlayerMachineFirst();
        actualPlayer.setName("Steven");
        PlayerNetwork playerNetwork = new PlayerNetwork();

        ClientPlayer pc = new ClientPlayer(playerNetwork.getPortNumber(), actualPlayer);
        new Thread(pc).start();

        ArrayList<Integer> dice = new ArrayList<>();

        dice.add(5);
        dice.add(5);

        playerNetwork.startGame(Color.RED);
        ArrayList<Move> moves = playerNetwork.doMove(board, dice);

        assertEquals(2, moves.size());
    }


}