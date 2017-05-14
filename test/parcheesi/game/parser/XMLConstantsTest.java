package parcheesi.game.parser;

import org.junit.Test;
import parcheesi.game.board.Board;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by devondapuzzo on 5/12/17.
 */
public class XMLConstantsTest {

    private Board board = new Board();
    private XMLConstants c = new XMLConstants();

    @Test
    public void convertIdFromXML() throws Exception {
        assertEquals(c.convertIdFromXML(9, board), 0);
        assertEquals(c.convertIdFromXML(8, board), board.getBoardLength()-1);
        assertEquals(c.convertIdFromXML(0, board), board.getBoardLength() - 9);
    }

    @Test
    public void convertIdToXML() throws Exception {
        assertEquals(c.convertIdToXML(0, board), 9);
        assertEquals(c.convertIdToXML(board.getBoardLength()-1, board), 8);
        assertEquals(c.convertIdToXML(board.getBoardLength() - 9, board), 0);
    }

}