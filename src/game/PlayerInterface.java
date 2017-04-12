package game;

import game.Board;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface PlayerInterface {
    void startGame(String color);

    Move doMove(Board brd, int[] dice);

    void DoublesPenalty();
}
