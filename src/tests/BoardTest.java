package tests;



import game.Board;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class BoardTest {
    @Test
    public void getBoardLength() throws Exception {
        Board board = new Board();

        Assert.assertEquals(board.getSpaces().size(), board.getBoardLength());

    }
}