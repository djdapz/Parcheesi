package parcheesi.game.parser;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.PlayerAction;
import parcheesi.game.gameplay.Game;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;
import parcheesi.game.util.TestingUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static junit.framework.TestCase.*;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class XMLDecoderTest {
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private XMLEncoder xmlEncoder = new XMLEncoder();

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
    public void getActionWorks() throws Exception {
        String xml="<do-move> <board> <start> <pawn> <color> yellow </color> <id> 3 </id> </pawn> <pawn> <color> yellow </color> <id> 2 </id> </pawn> <pawn> <color> yellow </color> <id> 1 </id> </pawn> <pawn> <color> yellow </color> <id> 0 </id> </pawn> <pawn> <color> red </color> <id> 3 </id> </pawn> <pawn> <color> red </color> <id> 2 </id> </pawn> <pawn> <color> red </color> <id> 1 </id> </pawn> <pawn> <color> red </color> <id> 0 </id> </pawn> <pawn> <color> green </color> <id> 3 </id> </pawn> <pawn> <color> green </color> <id> 2 </id> </pawn> <pawn> <color> green </color> <id> 1 </id> </pawn> <pawn> <color> green </color> <id> 0 </id> </pawn> <pawn> <color> blue </color> <id> 3 </id> </pawn> <pawn> <color> blue </color> <id> 2 </id> </pawn> <pawn> <color> blue </color> <id> 1 </id> </pawn> <pawn> <color> blue </color> <id> 0 </id> </pawn> </start> <main> </main> <home-rows> </home-rows> <home> </home> </board> <dice> <die> 5 </die> <die> 3 </die> </dice> </do-move>";
        PlayerAction pa = XMLDecoder.getAction(xml);
        assertEquals(pa, PlayerAction.DO_MOVE);

        xml="<start-game> <color>red</color></start-game>";
        pa = XMLDecoder.getAction(xml);
        assertEquals(pa, PlayerAction.START_GAME);

        xml="<doubles-penalty></doubles-penalty>";
        pa = XMLDecoder.getAction(xml);
        assertEquals(pa, PlayerAction.DOUBLES_PENALTY);
    }

    @Test
    public void decodeStartGame() throws Exception {

        String xml="<start-game> <color>red</color></start-game>";

        PlayerAction pa = XMLDecoder.getAction(xml);
        assertEquals(pa, PlayerAction.START_GAME);

        Color color = XMLDecoder.decodeStartGame(xml);
        assertEquals(color, Color.RED);
    }

    @Test
    public void getDiceFromDoMove() throws Exception{
        String xml="<do-move><board></board><dice><die>1</die><die>2</die></dice></do-move>";

        PlayerAction pa = XMLDecoder.getAction(xml);
        assertEquals(pa, PlayerAction.DO_MOVE);

        ArrayList<Integer> dice = XMLDecoder.getDiceFromDoMove(xml);
        assertEquals(dice.size(), 2);
        assertTrue(dice.contains(1));
        assertTrue(dice.contains(2));
    }

    @Test
    public void initialBoardFromXML() throws Exception {
        String robbysInitialXML = "<board> <start> <pawn> <color> yellow </color> <id> 3 </id> </pawn> <pawn> <color> yellow </color> <id> 2 </id> </pawn> <pawn> <color> yellow </color> <id> 1 </id> </pawn> <pawn> <color> yellow </color> <id> 0 </id> </pawn> <pawn> <color> red </color> <id> 3 </id> </pawn> <pawn> <color> red </color> <id> 2 </id> </pawn> <pawn> <color> red </color> <id> 1 </id> </pawn> <pawn> <color> red </color> <id> 0 </id> </pawn> <pawn> <color> green </color> <id> 3 </id> </pawn> <pawn> <color> green </color> <id> 2 </id> </pawn> <pawn> <color> green </color> <id> 1 </id> </pawn> <pawn> <color> green </color> <id> 0 </id> </pawn> <pawn> <color> blue </color> <id> 3 </id> </pawn> <pawn> <color> blue </color> <id> 2 </id> </pawn> <pawn> <color> blue </color> <id> 1 </id> </pawn> <pawn> <color> blue </color> <id> 0 </id> </pawn> </start> <main> </main> <home-rows> </home-rows> <home> </home></board>";

        Board board = XMLDecoder.decodeBoardFromString(robbysInitialXML, players);

        for(Player player: players){
            for(Pawn pawn: player.getPawns()){
                assertTrue(board.getNests().get(player.getColor()).isAtNest(pawn));
            }
        }
    }

    @Test
    public void testComplexBoardNests() throws Exception {

        TestingUtil.getComplexBoard(board, players);
        String boardString = xmlEncoder.encodeBoard(board);
        Board roundTripBoard = XMLDecoder.decodeBoardFromString(boardString, players);

        HashMap<Color, Nest> firstNest = board.getNests();
        HashMap<Color, Nest> roundTripNests = roundTripBoard.getNests();

        for(Nest nest: firstNest.values()){
            for(Pawn pawn: nest.getPawns()){
                assertTrue(roundTripNests.get(pawn.getColor()).isAtNest(pawn));
            }
        }

        for(Nest nest: roundTripNests.values()){
            for(Pawn pawn: nest.getPawns()){
                assertTrue(firstNest.get(pawn.getColor()).isAtNest(pawn));
            }
        }
    }

    @Test
    public void testComplexBoardHome() throws Exception {
        TestingUtil.getComplexBoard(board, players);
        String boardString = xmlEncoder.encodeBoard(board);
        Board roundTripBoard = XMLDecoder.decodeBoardFromString(boardString, players);

        Home firstHome = board.getHome();
        Home roundTripHome = roundTripBoard.getHome();


        for(Pawn pawn: firstHome.getPawns()){
            assertTrue(roundTripHome.isPawnHome(pawn));
        }


        for(Pawn pawn: roundTripHome.getPawns()){
            assertTrue(firstHome.isPawnHome(pawn));
        }
    }

    @Test
    public void testComplexHomeRows() throws Exception {
        TestingUtil.getComplexBoard(board, players);
        String boardString = xmlEncoder.encodeBoard(board);
        Board roundTripBoard = XMLDecoder.decodeBoardFromString(boardString, players);

        HashMap<Color, Vector<Space>> firstHomeRows = board.getHomeRows();
        HashMap<Color, Vector<Space>> roundTripHomeRows = roundTripBoard.getHomeRows();


        for(Color color: board.getColors()){
           TestingUtil.assertVectorOfSpacesAreIdentical(firstHomeRows.get(color), roundTripHomeRows.get(color));
        }
    }

    @Test
    public void testComplexMainRing() throws Exception {
        TestingUtil.getComplexBoard(board, players);
        String boardString = xmlEncoder.encodeBoard(board);
        Board roundTripBoard = XMLDecoder.decodeBoardFromString(boardString, players);

        Vector<Space> firstMainRing = board.getSpaces();
        Vector<Space> roundTripMainRing = roundTripBoard.getSpaces();

        TestingUtil.assertVectorOfSpacesAreIdentical(firstMainRing, roundTripMainRing);

    }

}