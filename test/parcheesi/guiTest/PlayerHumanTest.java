package parcheesi.guiTest;

/**
 * Created by devondapuzzo on 5/22/17.
 */

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.*;
import parcheesi.game.player.ClientPlayer;
import parcheesi.game.player.machine.PlayerMachine;
import parcheesi.game.player.machine.PlayerMachineFirst;
import parcheesi.game.util.TestingUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
public class PlayerHumanTest {
    //TODO - create test for bopping user out of entering parcheesi.game.player's enterance
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private PlayerHuman actualPlayer;
    private PlayerNetwork mainPlayer;
    private PlayerMachine playerBlocking;
    private ArrayList<Integer> dice;

    @Before
    public void setUp() throws Exception {
        game = new Game();

        actualPlayer = new PlayerHuman("Devon");
        mainPlayer = new PlayerNetwork();
        playerBlocking = new PlayerMachineFirst();

        ClientPlayer pc = new ClientPlayer(mainPlayer.getPortNumber(), actualPlayer);
        new Thread(pc).start();

        game.register(mainPlayer);
        game.register(playerBlocking);
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
        dice = new ArrayList<>();
    }


    @Test
    public void testCreateBlockadeScenario() throws Exception{
        Pawn p0 = mainPlayer.getPawns()[0];
        Pawn p1 = mainPlayer.getPawns()[1];

        TestingUtil.enterPieceAndEnsureSuccess(board,p0);
        TestingUtil.enterPieceAndEnsureSuccess(board,p1);


        Move move = new MoveMain(p0, p0.getExitSpaceId(), 4);
        move.run(board);

        dice.add(4);
        ArrayList<Move> moves = mainPlayer.doMove(board,dice);
        assertEquals(moves.get(0).getDistance(), 4);
        assertEquals(moves.get(0).getPawn(), p1);
        assertEquals(moves.get(0).getStart(), p1.getExitSpaceId());
        assertEquals(moves.get(0).run(board), MoveResult.SUCCESS);


        dice.clear();
        dice.add(5);
        moves = mainPlayer.doMove(board,dice);
    }




}
