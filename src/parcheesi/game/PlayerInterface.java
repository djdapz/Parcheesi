package parcheesi.game;

import java.util.List;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public interface PlayerInterface {
    void startGame(String color);

    Move doMove(Board brd, List<Integer> dice);

    void DoublesPenalty();
}
