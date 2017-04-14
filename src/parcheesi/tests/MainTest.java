package parcheesi.tests;

import parcheesi.game.Main;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class MainTest {


    @Test
    public void firstFunction() throws Exception {
        Main main = new Main();
        int x = main.firstFunction();
        assertEquals(x, 1);
    }


    @Test
    public void testSomething(){
        assertEquals(1,1);
    }


}