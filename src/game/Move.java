package game;

import game.Board;
import game.MoveResult;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface Move {

    MoveResult run(Board board);
}
