package parcheesi.game.gameplay;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.BlockadeMovesException;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;
import parcheesi.game.player.PlayerMachineFirst;
import parcheesi.game.util.TestingUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 5/12/17.
 */
public class RulesCheckerTest {

    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private Player mainPlayer;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        mainPlayer = new PlayerMachineFirst();
        game.register(mainPlayer);
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
    }

    @Test
    public void doesPawnAppearOnlyOnce() throws Exception {
        for(Player p: players){
            for(Pawn pawn: p.getPawns()){
                assertTrue(RulesChecker.doesPawnAppearOnlyOnce(board, pawn));
            }
        }

        Pawn troublePawn = players.get(0).getPawns()[0];


        troublePawn.getExitSpace(board).addOccupant(troublePawn);
        assertTrue(!RulesChecker.doesPawnAppearOnlyOnce(board, troublePawn));

        board.getNests().get(troublePawn.getColor()).removePawn(troublePawn);
        assertTrue(RulesChecker.doesPawnAppearOnlyOnce(board, troublePawn));

        board.getHomeRows().get(troublePawn.getColor()).get(0).addOccupant(troublePawn);
        assertTrue(!RulesChecker.doesPawnAppearOnlyOnce(board, troublePawn));

        troublePawn.getExitSpace(board).removeOccupant(troublePawn);
        assertTrue(RulesChecker.doesPawnAppearOnlyOnce(board, troublePawn));


        board.getHome().addPawn(troublePawn);
        assertTrue(!RulesChecker.doesPawnAppearOnlyOnce(board, troublePawn));

        board.getHomeRows().get(troublePawn.getColor()).get(0).removeOccupant(troublePawn);
        assertTrue(RulesChecker.doesPawnAppearOnlyOnce(board, troublePawn));
    }

    @Test
    public void checkBlockadesInMainRing() throws Exception {
        players.get(0);
        TestingUtil.moveOneOfEachPawnFromNest(1,0, players.get(0), board);
        TestingUtil.moveOneOfEachPawnFromNest(0,0, players.get(0), board);

        assertEquals(players.get(0).getPawns()[0].getExitSpace(board), RulesChecker.findBlockades(board, players.get(0)).get(0));
        assertEquals(1, RulesChecker.findBlockades(board, players.get(0)).size());

        assertEquals(0, RulesChecker.findBlockades(board, players.get(1)).size());
        assertEquals(0, RulesChecker.findBlockades(board, players.get(2)).size());
        assertEquals(0, RulesChecker.findBlockades(board, players.get(3)).size());
    }

    @Test
    public void checkBlockadesInHomeRow() throws Exception {
        players.get(0);
        TestingUtil.moveOneOfEachPawnFromNest(0,17*4, players.get(1), board);
        TestingUtil.moveOneOfEachPawnFromNest(1,17*4, players.get(1), board);

        assertEquals(board.getHomeRows().get(players.get(1).getColor()).get(4), RulesChecker.findBlockades(board, players.get(1)).get(0));
        assertEquals(1, RulesChecker.findBlockades(board, players.get(1)).size());

        assertEquals(0, RulesChecker.findBlockades(board, players.get(0)).size());
        assertEquals(0, RulesChecker.findBlockades(board, players.get(2)).size());
        assertEquals(0, RulesChecker.findBlockades(board, players.get(3)).size());
    }

    @Test(expected = BlockadeMovesException.class)
    public void catchesMovementOfBlockades() throws Exception {
        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];


        Move enter0 = new EnterPiece(pawn0);
        Move enter1 = new EnterPiece(pawn1);

        assertEquals(MoveResult.ENTERED, enter0.run(board));
        assertEquals(MoveResult.ENTERED, enter1.run(board));

        assertEquals(RulesChecker.findBlockades(board, mainPlayer).get(0), pawn0.getExitSpace(board));

        ArrayList<Move> moves = new ArrayList<>();

        moves.add(new MoveMain(pawn0, pawn0.getExitSpaceId(), 5));
        moves.add(new MoveMain(pawn1, pawn1.getExitSpaceId(), 5));

        assertTrue(RulesChecker.doesBlockadeMove(moves, pawn0.getExitSpace(board), board));

        RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer);

    }

}