package tests.board;

import org.junit.Test;
import parcheesi.game.board.Home;
import parcheesi.game.enums.Color;
import parcheesi.game.player.Pawn;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class HomeTest {

    @Test
    public void copy() throws Exception {
        Home home0 = new Home();
        Home home1;
        Pawn pawn0 = new Pawn(0, Color.RED);
        Pawn pawn1 = new Pawn(1, Color.RED);

        home0.addPawn(pawn0);

        home1 = new Home(home0);
        home1.addPawn(pawn1);

        assertTrue(home1.isPawnHome(pawn1));
        assertTrue(home1.isPawnHome(pawn0));

        assertTrue(home0.isPawnHome(pawn0));
        assertTrue(!home0.isPawnHome(pawn1));


        assertNotEquals(home0, home1);

    }

}