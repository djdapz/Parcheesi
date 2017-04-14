package parcheesi.tests;



import parcheesi.game.Board;
import org.junit.Assert;
import org.junit.Test;
import parcheesi.game.Color;
import parcheesi.game.Pawn;
import parcheesi.game.Space;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Vector;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class BoardTest {
    @Test
    public void getBoardLength() throws Exception {
        Board board = new Board();

        Assert.assertEquals(board.getSpaces().size(), board.getBoardLength());

    }

    @Test
    public void findPawn() throws Exception {
        Board board = new Board();
        Space space = board.getSpaceAt(10);
        Pawn testPawn = new Pawn(1, Color.GREEN);

        space.addOccupant(testPawn);

        Assert.assertEquals(board.findPawn(testPawn), space);
    }

    @Test
    public void findMostAdvancedPawnWithAllPawnsInRingRoad() throws Exception {
        Board board = new Board();
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        Pawn testPawn0 = new Pawn(0, Color.GREEN);
        Pawn testPawn1 = new Pawn(1, Color.GREEN);
        Pawn testPawn2 = new Pawn(2, Color.GREEN);
        Pawn testPawn3 = new Pawn(3, Color.GREEN);

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);


        Assert.assertEquals(board.findMostAdvancedPawn(testPawn0.getColor()), space0);
    }

    @Test
    public void findMostAdvancedPawnWithPawnInHomeRow() throws Exception {
        Board board = new Board();

        Pawn testPawn0 = new Pawn(0, Color.GREEN);
        Pawn testPawn1 = new Pawn(1, Color.GREEN);
        Pawn testPawn2 = new Pawn(2, Color.GREEN);
        Pawn testPawn3 = new Pawn(3, Color.GREEN);

        Vector<Space> homeRow = board.getHomeRows().get(testPawn0.getColor());
        Space space0 = homeRow.get(2);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        space0.addOccupant(testPawn0);
        space1.addOccupant(testPawn1);
        space2.addOccupant(testPawn2);
        space3.addOccupant(testPawn3);


        Assert.assertEquals(board.findMostAdvancedPawn(testPawn0.getColor()), space0);
    }
}