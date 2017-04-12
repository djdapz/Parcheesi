package tests;

import game.Dice;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class DiceTest {
    @Test
    public void rollOne() throws Exception {
        Dice die = new Dice();
        int n;

        for(int i = 0; i < 10; i++){
            n = die.rollOne();
            assertTrue(n < 7 && n > 0);
        }
    }

}