package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.Strategy;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface Player {
    String startGame(String color);

    ArrayList<Move> doMove(Board brd, List<Integer> dice) throws Exception;

    void DoublesPenalty();

    boolean canMove(List<Integer> moves, Board board, ArrayList<Space> originalBlockades);

    boolean hasWon(Home home);

    Color getColor();

    void setColor(Color color);

    String getName();

    void setName(String name);
    Pawn[] getPawns();

    void setNestExit(Space space);

    void setPawnColor();

    void setHomeEntrance(Space space);

    Strategy getStrategy();

    void kickOut();

    boolean isKickedOut();
}
