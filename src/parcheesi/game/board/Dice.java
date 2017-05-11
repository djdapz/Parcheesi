package parcheesi.game.board;

import java.util.Random;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Dice {
    public int rollOne(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }
}
