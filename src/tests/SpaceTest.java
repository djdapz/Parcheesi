package tests;

import game.*;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by devondapuzzo on 4/10/17.
 */

public class SpaceTest {

    @Test
    public void isBlockaded() throws Exception {
        SpaceRegular ss = new SpaceRegular(Color.GREEN, 1);

        assertFalse(ss.isBlockaded());

        Pawn pawn1 = new Pawn(0, Color.GREEN);
        Pawn pawn2 = new Pawn(1, Color.GREEN);

        assertEquals(ss.addOccupant(pawn1), MoveResult.SUCCESS);
        assertFalse(ss.isBlockaded());
        assertEquals(ss.addOccupant(pawn2), MoveResult.SUCCESS);
        assertTrue(ss.isBlockaded());
        assertTrue(ss.removeOccupant(pawn1));
        assertFalse(ss.isBlockaded());
    }

    @Test
    public void addOccupantEmpty() throws Exception {
        SpaceRegular ss = new SpaceRegular(Color.GREEN, 1);

        Pawn pawn1 = new Pawn(0, Color.GREEN);

        assertEquals(ss.addOccupant(pawn1), MoveResult.SUCCESS);
        assertEquals(ss.getOccupant1(), pawn1);
        assertEquals(ss.getOccupant2(), null);
    }

    @Test
    public void addTwoOccupants() throws Exception {
        SpaceRegular ss = new SpaceRegular(Color.GREEN, 1);

        Pawn pawn1 = new Pawn(0, Color.GREEN);
        Pawn pawn2 = new Pawn(1, Color.GREEN);


        assertEquals(ss.addOccupant(pawn1), MoveResult.SUCCESS);
        assertEquals(ss.addOccupant(pawn2), MoveResult.SUCCESS);
        assertEquals(ss.getOccupant1(), pawn1);
        assertEquals(ss.getOccupant2(), pawn2);
    }

    @Test
    public void addTwoOccupantsThenRemoveFirst() throws Exception {
        SpaceRegular ss = new SpaceRegular(Color.GREEN, 1);

        Pawn pawn1 = new Pawn(0, Color.BLUE);
        Pawn pawn2 = new Pawn(1, Color.BLUE);


        assertEquals(ss.addOccupant(pawn1), MoveResult.SUCCESS);
        assertEquals(ss.addOccupant(pawn2), MoveResult.SUCCESS);
        assertEquals(ss.getOccupant1(), pawn1);
        assertEquals(ss.getOccupant2(), pawn2);

        assertTrue(ss.removeOccupant(pawn1));
        assertEquals(ss.getOccupant1(), pawn2);
        assertEquals(ss.getOccupant2(), null);
    }

    @Test
    public void addTwoOccupantsThenRemoveSecond() throws Exception {
        SpaceRegular ss = new SpaceRegular(Color.GREEN, 1);

        Pawn pawn1 = new Pawn(0, Color.RED);
        Pawn pawn2 = new Pawn(1, Color.RED);


        assertEquals(ss.addOccupant(pawn1), MoveResult.SUCCESS);
        assertEquals(ss.addOccupant(pawn2), MoveResult.SUCCESS);
        assertEquals(ss.getOccupant1(), pawn1);
        assertEquals(ss.getOccupant2(), pawn2);

        assertTrue(ss.removeOccupant(pawn1));
        assertEquals(ss.getOccupant1(), pawn2);
        assertEquals(ss.getOccupant2(), null);
    }


    @Test
    public void getRegion() throws Exception {
        Color r1 = Color.GREEN;
        Color r2 = Color.BLUE;

        SpaceSafe ss1 = new SpaceSafe(r1, 1);
        SpaceSafe ss2 = new SpaceSafe(r2, 1);

        assertEquals(r1, ss1.getRegion());
        assertEquals(r2, ss2.getRegion());
    }

    @Test
    public void getId() throws Exception {
        Color r = Color.GREEN;
        int id1 = 1;
        int id2 = 2;


        SpaceSafe ss1 = new SpaceSafe(r, id1);
        SpaceSafe ss2 = new SpaceSafe(r, id2);

        assertEquals(id1, ss1.getId());
        assertEquals(id2, ss2.getId());
    }

    @Test
    public void isSafeSpace() throws Exception {
        SpaceSafe ss = new SpaceSafe(Color.GREEN, 1);
        SpaceRegular rs = new SpaceRegular(Color.BLUE, 2);

        boolean ssSafe = ss.isSafeSpace();
        boolean rsSafe = rs.isSafeSpace();

        assertEquals(ssSafe, true);
        assertEquals(rsSafe, false);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void cannotInitializeWithNegativeId()  throws Exception {
        SpaceSafe ss = new SpaceSafe(Color.BLUE, -1);
    }

}