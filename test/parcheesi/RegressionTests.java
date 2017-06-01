package parcheesi;

import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.gameplay.Game;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.player.Player;
import parcheesi.game.player.machine.PlayerMachineLast;

import java.util.ArrayList;

/**
 * Created by devondapuzzo on 5/31/17.
 */
public class RegressionTests {

    @Test
    public void annoyingBugMovingBlockadesIntoHomeONDOubles() throws Exception{
        Game game = new Game();
        game.register(new PlayerMachineLast());
        game.register(new PlayerMachineLast());
        game.register(new PlayerMachineLast());
        game.register(new PlayerMachineLast());
        game.start();

        Player mainPlayer = game.getPlayers().get(3);

        String doMoveString = "<do-move><board><start><pawn><color>yellow</color><id>3</id></pawn></start><main><piece-loc><pawn><color>red</color><id>1</id></pawn><loc>13</loc></piece-loc><piece-loc><pawn><color>blue</color><id>2</id></pawn><loc>16</loc></piece-loc><piece-loc><pawn><color>blue</color><id>0</id></pawn><loc>17</loc></piece-loc><piece-loc><pawn><color>blue</color><id>3</id></pawn><loc>17</loc></piece-loc><piece-loc><pawn><color>blue</color><id>1</id></pawn><loc>21</loc></piece-loc><piece-loc><pawn><color>red</color><id>3</id></pawn><loc>35</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>2</id></pawn><loc>8</loc></piece-loc></main><home-rows><piece-loc><pawn><color>green</color><id>3</id></pawn><loc>3</loc></piece-loc><piece-loc><pawn><color>green</color><id>2</id></pawn><loc>3</loc></piece-loc><piece-loc><pawn><color>green</color><id>1</id></pawn><loc>4</loc></piece-loc><piece-loc><pawn><color>green</color><id>0</id></pawn><loc>4</loc></piece-loc></home-rows><home><pawn><color>red</color><id>2</id></pawn><pawn><color>yellow</color><id>0</id></pawn><pawn><color>red</color><id>0</id></pawn><pawn><color>yellow</color><id>1</id></pawn></home></board><dice><die>4</die><die>4</die></dice></do-move>";

        Board board = XMLDecoder.decodeBoardFromDoMove(doMoveString, game.getPlayers());
        ArrayList<Integer> dice = XMLDecoder.decodeDiceFromDoMove(doMoveString);

        mainPlayer.doMove(board, dice);
    }
}
