package parcheesi.game.moves;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parcheesi.game.gameplay.Game;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 4/11/17.
 */
public class MoveHomeTest {
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private RulesChecker rc = new RulesChecker();

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
    public void pawnCanMoveWithinHomeRow() throws Exception{

        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Pawn testPawn = players.get(0).getPawns()[0];
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        Space startSpace = testHomeRow.get(0);
        Space endSpace = testHomeRow.get(3);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getId(), 0);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 3);
        Assert.assertEquals(thisMove.run(board), MoveResult.SUCCESS);

        assertEquals(endSpace.getOccupant1(), testPawn);
        assertEquals(startSpace.getOccupant1(), null);

        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void pawnGoesHomeWhenRightNumberSelected() throws Exception{
        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Pawn testPawn = players.get(0).getPawns()[0];
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        Space startSpace = testHomeRow.get(4);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 3);
        Assert.assertEquals(thisMove.run(board), MoveResult.HOME);

        assertTrue(home.isPawnHome(testPawn));
        assertEquals(startSpace.getOccupant1(), null);

        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void pawnCannotGoHomeOnOvershoot() throws Exception{
        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Pawn testPawn = players.get(0).getPawns()[0];
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);

        Space startSpace = testHomeRow.get(4);
        Space endSpace = testHomeRow.get(3);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 4);
        Assert.assertEquals(thisMove.run(board), MoveResult.OVERSHOT);

        assertTrue(!home.isPawnHome(testPawn));
        assertEquals(startSpace.getOccupant1(), testPawn);


        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
    }

    @Test
    public void pawnCannotMovePastBlockadeInHomeRow() throws Exception{
        Pawn testPawn = players.get(0).getPawns()[0];
        Pawn blockingPawn1 = players.get(0).getPawns()[1];
        Pawn blockingPawn2 = players.get(0).getPawns()[2];

        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        Vector<Space> testHomeRow = homeRows.get(testPawn.getColor());

        Nest testPawnNest = board.getNests().get(testPawn.getColor());
        testPawnNest.removePawn(testPawn);
        testPawnNest.removePawn(blockingPawn1);
        testPawnNest.removePawn(blockingPawn2);

        Space startSpace = testHomeRow.get(0);
        Space endSpace = testHomeRow.get(3);
        Space blockedSpace = testHomeRow.get(2);

        blockedSpace.addOccupant(blockingPawn1);
        blockedSpace.addOccupant(blockingPawn2);
        startSpace.addOccupant(testPawn);

        assertEquals(startSpace.getOccupant1(), testPawn);
        assertEquals(blockedSpace.getOccupant1(), blockingPawn1);
        assertEquals(blockedSpace.getOccupant2(), blockingPawn2);

        MoveHome thisMove = new MoveHome(testPawn, startSpace.getId(), 3);
        Assert.assertEquals(thisMove.run(board), MoveResult.BLOCKED);

        assertTrue(!home.isPawnHome(testPawn));
        assertEquals(startSpace.getOccupant1(), testPawn);
        assertEquals(blockedSpace.getOccupant1(), blockingPawn1);
        assertEquals(blockedSpace.getOccupant2(), blockingPawn2);


        assertTrue(rc.doesPawnAppearOnlyOnce(board, testPawn));
        assertTrue(rc.doesPawnAppearOnlyOnce(board, blockingPawn1));
        assertTrue(rc.doesPawnAppearOnlyOnce(board, blockingPawn2));
    }

    @Test
    public void REGRESSION_TEST() throws Exception{
        Player playerMain = players.get(0);
        Board board = XMLDecoder.decodeBoardFromDoMove(
                "<do-move><board><start><pawn><color>red</color><id>1</id></pawn><pawn><color>blue</color><id>1</id></pawn><pawn><color>blue</color><id>2</id></pawn><pawn><color>blue</color><id>3</id></pawn><pawn><color>yellow</color><id>3</id></pawn><pawn><color>yellow</color><id>2</id></pawn><pawn><color>yellow</color><id>1</id></pawn><pawn><color>green</color><id>3</id></pawn><pawn><color>green</color><id>1</id></pawn><pawn><color>green</color><id>2</id></pawn></start><main><piece-loc><pawn><color>green</color><id>0</id></pawn><loc>10</loc></piece-loc><piece-loc><pawn><color>red</color><id>3</id></pawn><loc>22</loc></piece-loc><piece-loc><pawn><color>red</color><id>2</id></pawn><loc>29</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>0</id></pawn><loc>32</loc></piece-loc></main><home-rows><piece-loc><pawn><color>red</color><id>0</id></pawn><loc>2</loc></piece-loc></home-rows><home><pawn><color>blue</color><id>0</id></pawn></home></board><dice><die>2</die><die>1</die></dice></do-move>",
                players);
        ArrayList<Move> chosenMoves = XMLDecoder.decodeDoMoveResponse("<moves><move-piece-home><pawn><color>red</color><id>0</id></pawn><start>2</start><distance>2</distance></move-piece-home><move-piece-home><pawn><color>red</color><id>0</id></pawn><start>4</start><distance>1</distance></move-piece-home></moves>",
                playerMain);


        for(Move move: chosenMoves){
            assertEquals(move.run(board), MoveResult.SUCCESS);
        }

    }



}