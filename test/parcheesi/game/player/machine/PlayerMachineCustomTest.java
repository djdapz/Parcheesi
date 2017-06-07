package parcheesi.game.player.machine;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.enums.Color;
import parcheesi.game.gameplay.Game;
import parcheesi.game.moves.Move;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.parser.XMLEncoder;
import parcheesi.game.player.Player;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by devondapuzzo on 6/5/17.
 */
public class PlayerMachineCustomTest {


//    @Test
//    public static void tournamentRegression() throws Exception{
//
//        ArrayList<Player> players = game.getPlayers();
//
//        String doMove = "<do-move><board><start><pawn><color>yellow</color><id>3</id></pawn><pawn><color>yellow</color><id>2</id></pawn><pawn><color>green</color><id>0</id></pawn><pawn><color>blue</color><id>3</id></pawn></start><main><piece-loc><pawn><color>yellow</color><id>0</id></pawn><loc>66</loc></piece-loc><piece-loc><pawn><color>blue</color><id>2</id></pawn><loc>55</loc></piece-loc><piece-loc><pawn><color>red</color><id>3</id></pawn><loc>29</loc></piece-loc><piece-loc><pawn><color>red</color><id>2</id></pawn><loc>29</loc></piece-loc><piece-loc><pawn><color>red</color><id>1</id></pawn><loc>28</loc></piece-loc><piece-loc><pawn><color>red</color><id>0</id></pawn><loc>22</loc></piece-loc><piece-loc><pawn><color>green</color><id>1</id></pawn><loc>21</loc></piece-loc><piece-loc><pawn><color>green</color><id>3</id></pawn><loc>5</loc></piece-loc><piece-loc><pawn><color>green</color><id>2</id></pawn><loc>5</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>1</id></pawn><loc>4</loc></piece-loc><piece-loc><pawn><color>blue</color><id>1</id></pawn><loc>1</loc></piece-loc><piece-loc><pawn><color>blue</color><id>0</id></pawn><loc>1</loc></piece-loc></main><home-rows></home-rows><home></home></board><dice><die>3</die><die>5</die></dice></do-move>\n";
//

//
//        p
//    }

    @Test
    public void tournamentRegression_AfterFirstFix() throws Exception {
        String doMove = "<do-move><board><start><pawn><color>red</color><id>3</id></pawn><pawn><color>green</color><id>0</id></pawn><pawn><color>blue</color><id>0</id></pawn></start><main><piece-loc><pawn><color>yellow</color><id>0</id></pawn><loc>40</loc></piece-loc><piece-loc><pawn><color>blue</color><id>2</id></pawn><loc>30</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>2</id></pawn><loc>22</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>1</id></pawn><loc>17</loc></piece-loc><piece-loc><pawn><color>green</color><id>3</id></pawn><loc>6</loc></piece-loc></main><home-rows><piece-loc><pawn><color>red</color><id>1</id></pawn><loc>6</loc></piece-loc></home-rows><home><pawn><color>yellow</color><id>3</id></pawn><pawn><color>red</color><id>2</id></pawn><pawn><color>red</color><id>0</id></pawn><pawn><color>green</color><id>2</id></pawn><pawn><color>green</color><id>1</id></pawn><pawn><color>blue</color><id>3</id></pawn><pawn><color>blue</color><id>1</id></pawn></home></board><dice><die>1</die><die>4</die></dice></do-move>";
        String badMove = "<moves><enter-piece><pawn><color>red</color><id>3</id></pawn></enter-piece></moves>";

        Board board = XMLDecoder.getBoardFromDoMove(doMove, players);
        ArrayList<Integer> dice = XMLDecoder.getDiceFromDoMove(doMove);

        Color pColor = Color.RED;
        Player mainPlayer = null;

        for(Player player: players){
            if(player.getColor() == pColor){
                mainPlayer = player;
            }
        }
        assertNotNull(mainPlayer);

        ArrayList<Move> playerMoves = mainPlayer.doMove(board, dice);
        String playerMovesString = XMLEncoder.encodeMoves(playerMoves);

        assertTrue(playerMoves.size() > 1);
        assertFalse(badMove.equals(playerMovesString));
    }
    private Game game;

    private ArrayList<Player> players;

    @Before
    public void setup() throws Exception{
        game = new Game();
        for(Color color: game.getBoard().getColors()){
            game.register(new PlayerMachineCustom());
        }
        game.start();
        players = game.getPlayers();
    }



    @Test
    public void tournamentRegression() throws Exception {
        String doMove = "<do-move><board><start><pawn><color>yellow</color><id>3</id></pawn><pawn><color>yellow</color><id>2</id></pawn><pawn><color>red</color><id>1</id></pawn></start><main><piece-loc><pawn><color>red</color><id>2</id></pawn><loc>65</loc></piece-loc><piece-loc><pawn><color>blue</color><id>3</id></pawn><loc>61</loc></piece-loc><piece-loc><pawn><color>red</color><id>3</id></pawn><loc>36</loc></piece-loc><piece-loc><pawn><color>green</color><id>3</id></pawn><loc>30</loc></piece-loc><piece-loc><pawn><color>green</color><id>2</id></pawn><loc>17</loc></piece-loc></main><home-rows><piece-loc><pawn><color>green</color><id>0</id></pawn><loc>5</loc></piece-loc><piece-loc><pawn><color>blue</color><id>2</id></pawn><loc>6</loc></piece-loc></home-rows><home><pawn><color>yellow</color><id>1</id></pawn><pawn><color>yellow</color><id>0</id></pawn><pawn><color>red</color><id>0</id></pawn><pawn><color>green</color><id>1</id></pawn><pawn><color>blue</color><id>1</id></pawn><pawn><color>blue</color><id>0</id></pawn></home></board><dice><die>4</die><die>3</die></dice></do-move>";
        String badMove = "<moves><move-piece-main><pawn><color>blue</color><id>3</id></pawn><start>61</start><distance>4</distance></move-piece-main><move-piece-main><pawn><color>blue</color><id>3</id></pawn><start>65</start><distance>3</distance></move-piece-main></moves>";

        Board board = XMLDecoder.getBoardFromDoMove(doMove, players);
        ArrayList<Integer> dice = XMLDecoder.getDiceFromDoMove(doMove);

        Color pColor = Color.BLUE;
        Player mainPlayer = null;

        for(Player player: players){
            if(player.getColor() == pColor){
                mainPlayer = player;
            }
        }
        assertNotNull(mainPlayer);

        ArrayList<Move> playerMoves = mainPlayer.doMove(board, dice);
        String playerMovesString = XMLEncoder.encodeMoves(playerMoves);

        assertEquals(3, playerMoves.size());
        assertFalse(badMove.equals(playerMovesString));
    }



    @Test
    public void tournamentRegression_3() throws Exception {
        String doMove = "<do-move><board><start><pawn><color>yellow</color><id>2</id></pawn><pawn><color>green</color><id>1</id></pawn><pawn><color>blue</color><id>3</id></pawn><pawn><color>blue</color><id>0</id></pawn></start><main><piece-loc><pawn><color>blue</color><id>1</id></pawn><loc>62</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>1</id></pawn><loc>51</loc></piece-loc><piece-loc><pawn><color>red</color><id>0</id></pawn><loc>45</loc></piece-loc><piece-loc><pawn><color>green</color><id>0</id></pawn><loc>28</loc></piece-loc><piece-loc><pawn><color>green</color><id>2</id></pawn><loc>21</loc></piece-loc><piece-loc><pawn><color>red</color><id>3</id></pawn><loc>11</loc></piece-loc><piece-loc><pawn><color>blue</color><id>2</id></pawn><loc>9</loc></piece-loc><piece-loc><pawn><color>green</color><id>3</id></pawn><loc>5</loc></piece-loc></main><home-rows><piece-loc><pawn><color>red</color><id>1</id></pawn><loc>6</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>0</id></pawn><loc>3</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>3</id></pawn><loc>0</loc></piece-loc></home-rows><home><pawn><color>red</color><id>2</id></pawn></home></board><dice><die>6</die><die>3</die></dice></do-move>";
        String badMove = "<moves><move-piece-home><pawn><color>yellow</color><id>3</id></pawn><start>0</start><distance>3</distance></move-piece-home></moves>";

        Color thisColor = Color.YELLOW;
        Integer badLength = 1;

        ArrayList<Move> moves = regressionHelperMakePlayerGo(doMove, thisColor);

        assertFalse(badLength == moves.size());
        assertFalse(badMove.equals(XMLEncoder.encodeMoves(moves)));
    }

    private ArrayList<Move> regressionHelperMakePlayerGo(String doMove, Color thisColor) throws Exception {

        Board board = XMLDecoder.getBoardFromDoMove(doMove, players);
        ArrayList<Integer> dice = XMLDecoder.getDiceFromDoMove(doMove);

        Player mainPlayer = regressionHelperPlayer(thisColor);
        return mainPlayer.doMove(board, dice);
    }

    public Player regressionHelperPlayer(Color color) throws Exception{
        Color pColor = Color.BLUE;

        for(Player player: players){
            if(player.getColor() == pColor){
                return player;
            }
        }
        throw new Exception();
    }
}