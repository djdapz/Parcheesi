package parcheesi.game.parser;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class XMLEncoderTest {
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private XMLEncoder xmlEncoder = new XMLEncoder();

    private final int HOME_TAG_LENGTH = "<home></home>".length();
    private final int START_TAG_LENGTH = "<start></start>".length();
    private final int MAIN_TAG_LENGTH = "<main></main>".length();
    private final int HOME_ROWS_TAG_LENGTH = "<home-rows></home-rows>".length();
    private final int BOARD_TAG_LENGTH = "<parcheesi.game.board></parcheesi.game.board>".length();
    private final int ALL_MAIN_TAG_LENGTH = HOME_TAG_LENGTH+START_TAG_LENGTH+MAIN_TAG_LENGTH+HOME_ROWS_TAG_LENGTH+BOARD_TAG_LENGTH;

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
    public void initialBoardToXML() throws Exception {
        String initalXML = xmlEncoder.encodeBoard(board);
        String pawn0 = xmlEncoder.encodePawn(players.get(0).getPawns()[0]);
        String pawn1 = xmlEncoder.encodePawn(players.get(1).getPawns()[1]);
        String pawn2 = xmlEncoder.encodePawn(players.get(2).getPawns()[2]);
        String pawn3 = xmlEncoder.encodePawn(players.get(3).getPawns()[3]);

        assertTrue(allPawnsPresent(initalXML));

        String robbysInitialXML = "<board> <start> <pawn> <color> yellow </color> <id> 3 </id> </pawn> <pawn> <color> yellow </color> <id> 2 </id> </pawn> <pawn> <color> yellow </color> <id> 1 </id> </pawn> <pawn> <color> yellow </color> <id> 0 </id> </pawn> <pawn> <color> red </color> <id> 3 </id> </pawn> <pawn> <color> red </color> <id> 2 </id> </pawn> <pawn> <color> red </color> <id> 1 </id> </pawn> <pawn> <color> red </color> <id> 0 </id> </pawn> <pawn> <color> green </color> <id> 3 </id> </pawn> <pawn> <color> green </color> <id> 2 </id> </pawn> <pawn> <color> green </color> <id> 1 </id> </pawn> <pawn> <color> green </color> <id> 0 </id> </pawn> <pawn> <color> blue </color> <id> 3 </id> </pawn> <pawn> <color> blue </color> <id> 2 </id> </pawn> <pawn> <color> blue </color> <id> 1 </id> </pawn> <pawn> <color> blue </color> <id> 0 </id> </pawn> </start> <main> </main> <home-rows> </home-rows> <home> </home></board>";
        robbysInitialXML = robbysInitialXML.replaceAll("\\s+","");

        assertEquals(robbysInitialXML.length(), initalXML.length());

    }

    @Test
    public void nestsToXML() throws Exception {
        String initalXML = xmlEncoder.encodeNests(board);
        assertTrue(allPawnsPresent(initalXML));
    }

    @Test
    public void mainToXML() throws Exception {
        EnterPiece enterPiece0 = new EnterPiece(players.get(0).getPawns()[0]);
        EnterPiece enterPiece1 = new EnterPiece(players.get(1).getPawns()[1]);
        EnterPiece enterPiece2 = new EnterPiece(players.get(2).getPawns()[2]);
        EnterPiece enterPiece3 = new EnterPiece(players.get(3).getPawns()[3]);

        assertEquals(enterPiece0.run(board), MoveResult.ENTERED);
        assertEquals(enterPiece1.run(board), MoveResult.ENTERED);
        assertEquals(enterPiece2.run(board), MoveResult.ENTERED);
        assertEquals(enterPiece3.run(board), MoveResult.ENTERED);

        String pawn0 = xmlEncoder.encodePawn(players.get(0).getPawns()[0]);
        String pawn1 = xmlEncoder.encodePawn(players.get(1).getPawns()[1]);
        String pawn2 = xmlEncoder.encodePawn(players.get(2).getPawns()[2]);
        String pawn3 = xmlEncoder.encodePawn(players.get(3).getPawns()[3]);

        String mainXML = xmlEncoder.encodeMainRow(board);

        assertTrue(mainXML.contains(pawn0));
        assertTrue(mainXML.contains(pawn1));
        assertTrue(mainXML.contains(pawn2));
        assertTrue(mainXML.contains(pawn3));

    }

    @Test
    public void homeRowsToXML() throws Exception {
        Pawn pawn0o = players.get(0).getPawns()[0];
        Pawn pawn1o = players.get(1).getPawns()[1];
        Pawn pawn2o = players.get(2).getPawns()[2];
        Pawn pawn3o = players.get(3).getPawns()[3];

        EnterPiece enterPiece0 = new EnterPiece(pawn0o);
        EnterPiece enterPiece1 = new EnterPiece(pawn1o);
        EnterPiece enterPiece2 = new EnterPiece(pawn2o);
        EnterPiece enterPiece3 = new EnterPiece(pawn3o);

        assertEquals(enterPiece0.run(board), MoveResult.ENTERED);
        assertEquals(enterPiece1.run(board), MoveResult.ENTERED);
        assertEquals(enterPiece2.run(board), MoveResult.ENTERED);
        assertEquals(enterPiece3.run(board), MoveResult.ENTERED);

        MoveMain movePiece0 = new MoveMain(pawn0o, pawn0o.getExitSpaceId(), 17*4-1);
        MoveMain movePiece1 = new MoveMain(pawn1o, pawn1o.getExitSpaceId(), 17*4-1);
        MoveMain movePiece2 = new MoveMain(pawn2o, pawn2o.getExitSpaceId(), 17*4-1);
        MoveMain movePiece3 = new MoveMain(pawn3o, pawn3o.getExitSpaceId(), 17*4-1);

        assertEquals(movePiece0.run(board), MoveResult.SUCCESS);
        assertEquals(movePiece1.run(board), MoveResult.SUCCESS);
        assertEquals(movePiece2.run(board), MoveResult.SUCCESS);
        assertEquals(movePiece3.run(board), MoveResult.SUCCESS);

        String pawn0 = xmlEncoder.encodePawn(pawn0o);
        String pawn1 = xmlEncoder.encodePawn(pawn1o);
        String pawn2 = xmlEncoder.encodePawn(pawn2o);
        String pawn3 = xmlEncoder.encodePawn(pawn3o);

        String mainXML = xmlEncoder.encodeMainRow(board);
        String homeRowXML = xmlEncoder.encodeHomeRows(board);

        assertTrue(!mainXML.contains(pawn0));
        assertTrue(!mainXML.contains(pawn1));
        assertTrue(!mainXML.contains(pawn2));
        assertTrue(!mainXML.contains(pawn3));

        assertTrue(homeRowXML.contains(pawn0));
        assertTrue(homeRowXML.contains(pawn1));
        assertTrue(homeRowXML.contains(pawn2));
        assertTrue(homeRowXML.contains(pawn3));
    }

    @Test
    public void homeToXML() throws Exception {

        Pawn pawn0 =players.get(0).getPawns()[0];
        Pawn pawn1 = players.get(1).getPawns()[0];
        String pawn0XML = xmlEncoder.encodePawn(players.get(0).getPawns()[0]);
        String pawn1XML = xmlEncoder.encodePawn(players.get(1).getPawns()[0]);

        board.getHome().addPawn(pawn0);
        board.getHome().addPawn(pawn1);

        String homeXML = xmlEncoder.encodeHome(board);

        assertTrue(homeXML.contains("<home>"));
        assertTrue(homeXML.contains("</home>"));

        assertTrue(homeXML.contains(pawn0XML));
        assertTrue(homeXML.contains(pawn1XML));

        assertEquals(homeXML.length(), "<home>".length() +"</home>" .length() + pawn0XML.length() + pawn1XML.length());
    }

    @Test
    public void pawnToXML() throws Exception {
        assertEquals(xmlEncoder.encodePawn(players.get(0).getPawns()[0]), "<pawn><color>"+players.get(0).getColor().toString().toLowerCase()+"</color><id>0</id></pawn>");
        assertEquals(xmlEncoder.encodePawn(players.get(1).getPawns()[1]), "<pawn><color>"+players.get(1).getColor().toString().toLowerCase()+"</color><id>1</id></pawn>");
        assertEquals(xmlEncoder.encodePawn(players.get(2).getPawns()[2]), "<pawn><color>"+players.get(2).getColor().toString().toLowerCase()+"</color><id>2</id></pawn>");
        assertEquals(xmlEncoder.encodePawn(players.get(3).getPawns()[3]), "<pawn><color>"+players.get(3).getColor().toString().toLowerCase()+"</color><id>3</id></pawn>");
    }

    @Test
    public void spaceToXML() throws Exception {
        board.getSpaceAt(0).addOccupant(players.get(0).getPawns()[0]);

        assertEquals(xmlEncoder.encodeSpace(board.getSpaceAt(0), board), "<piece-loc><pawn><color>red</color><id>0</id></pawn><loc>9</loc></piece-loc>");
        assertEquals(xmlEncoder.encodeSpace(board.getSpaceAt(1), board), "");
    }


    public boolean allPawnsPresent(String XML){
        for(Player player: players){
            for(Pawn pawn: player.getPawns()){
                String currentPawn = xmlEncoder.encodePawn(pawn);
                if(!XML.contains(currentPawn)){
                    return false;
                };
            }
        }

        return true;
    }
}