package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;

import java.util.ArrayList;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface Player {
    String startGame(Color color);

    ArrayList<Move> doMove(Board brd, ArrayList<Integer> dice) throws Exception;

    void doublesPenalty();

    boolean canMove(ArrayList<Integer> moves, Board board, ArrayList<Space> originalBlockades);

    boolean hasWon(Home home);

    Color getColor();

    void setColor(Color color);

    String getName();

    void setName(String name);
    Pawn[] getPawns();

    void setNestExit(Space space);

    void setPawnColor();

    void setHomeEntrance(Space space);

    void kickOut();

    boolean isKickedOut();

    void messagePlayer(String message);

    void incrementWins();

    void incrementKickedOuts();

    void incrementIndividualWins();

    int getIndividualWins();

}
