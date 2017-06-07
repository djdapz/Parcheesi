package parcheesi.game.player;

import org.junit.Before;
import org.junit.Test;
import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.gameplay.Game;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.player.machine.PlayerMachine;
import parcheesi.game.player.machine.PlayerMachineFirst;
import parcheesi.game.player.machine.PlayerMachineLast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 4/11/17.
 */
@SuppressWarnings("Duplicates")
public class PlayerMachineLastTests {
    //TODO - create test for bopping user out of entering parcheesi.game.player's enterance
    private Board board;
    private ArrayList<Player> players;
    private Game game;
    private Home home;
    private HashMap<Color, Nest> nests;
    private Nest nest;
    private Vector<Space> homeRow;
    private PlayerMachine mainPlayer;
    private PlayerMachine playerBlocking;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        mainPlayer = new PlayerMachineLast();
        playerBlocking = new PlayerMachineFirst();
        game.register(mainPlayer);
        game.register(playerBlocking);
        game.start();
        players = game.getPlayers();
        board = game.getBoard();
        home = board.getHome();
        nests = board.getNests();
        homeRow = board.getHomeRows().get(mainPlayer.getColor());
        nest = board.getNests().get(mainPlayer.getColor());

    }
    @Test
    public void MovesPawnOutWhenAllAtHome() throws Exception{
        ArrayList<Integer> scenario1dl = new ArrayList<>();
        ArrayList<Integer> scenario2dl = new ArrayList<>();
        ArrayList<Integer> scenario3dl = new ArrayList<>();

        scenario1dl.add(5);
        scenario1dl.add(2);

        scenario2dl.add(1);
        scenario2dl.add(4);

        scenario3dl.add(2);
        scenario3dl.add(3);

        Move move1 = mainPlayer.doMiniMove(board, scenario1dl);
        Move move2 = mainPlayer.doMiniMove(board, scenario2dl);
        Move move3 = mainPlayer.doMiniMove(board, scenario3dl);

        assertEquals(move1.getClass(), EnterPiece.class);
        assertEquals(move2.getClass(), EnterPiece.class);
        assertEquals(move3.getClass(), EnterPiece.class);
    }

    @Test(expected= NoMoveFoundException.class)
    public void MovesPawnOutWhenAllAtHomeButDiceDontAllowEntry() throws Exception{
        ArrayList<Integer> scenariodl = new ArrayList<>();

        scenariodl.add(6);
        scenariodl.add(2);

        Move move1 = mainPlayer.doMiniMove(board, scenariodl);
    }


    @Test
    public void MovesLeastAdvancedPawnWhenInMainRing() throws Exception {

        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);
        Space space3 = board.getSpaceAt(40);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        space3.addOccupant(pawn0);

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);



        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 20);
        assertEquals(move.getPawn(), pawn1);

        //scenario 2:
        dice.clear();
        dice.add(1);
        dice.add(4);

        move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 4);
        assertEquals(move.getStart(), 20);
        assertEquals(move.getPawn(), pawn1);

    }


    @Test
    public void MovesLeastAdvancedPawnWhenSomeInMainAndOneIsAtNest() throws Exception {
        Space space0 = board.getSpaceAt(0);
        Space space1 = board.getSpaceAt(20);
        Space space2 = board.getSpaceAt(30);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        nest.addPawn(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 20);
        assertEquals(move.getPawn(), pawn1);


        //scenario 2 entrancePossible:
        dice.clear();
        dice.add(1);
        dice.add(4);

        move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), EnterPiece.class);
        assertEquals(move.getPawn(), pawn3);
    }

    @Test
    public void MovesLeastAdvancedPawnWhenInHomeRow() throws Exception {
        Space space0 = homeRow.get(0);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn0);
        home.addPawn(pawn1);
        home.addPawn(pawn2);
        space0.addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(3);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn3);
    }

    @Test(expected = NoMoveFoundException.class)
    public void DoesMovesLeastAdvancedPawnWhenInHomeRowIfCanOnlyOvershoot() throws Exception {
        Space space0 = homeRow.get(0);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn0);
        home.addPawn(pawn1);
        home.addPawn(pawn2);
        space0.addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(5);

        space0.removeOccupant(pawn3);

        Move move = mainPlayer.doMiniMove(board, dice);
    }

    @Test
    public void MovesSecondMostAdvancedPawnWhenInHomeRowAndMostAdvancedIsTooClose() throws Exception {
        Space space0 = homeRow.get(0);
        Space space1 = homeRow.get(4);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn0);
        home.addPawn(pawn1);
        space0.addOccupant(pawn2);
        space1.addOccupant(pawn3);

        //Scenario 1 - too high of dice for pawn 2
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(5);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 5);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn2);


        //Scenario 1 - pawn 2 can go
        dice.clear();
        dice.add(1);
        dice.add(3);

        move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveHome.class);
        assertEquals(move.getDistance(), 3);
        assertEquals(move.getStart(), 0);
        assertEquals(move.getPawn(), pawn2);
    }

    @Test
    public void MovesSecondLeastAdvancedPawnWhenInMainRingAndLeastAdvancedIsBlockaded() throws Exception {
        Space space0 = board.getSpaceAt(28);
        Space space1 = board.getSpaceAt(32);
        Space space2 = board.getSpaceAt(0);
        Space blockedSpace = board.getSpaceAt(30);

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];
        Pawn blockingPawn0 = players.get(2).getPawns()[0];
        Pawn blockingPawn1 = players.get(2).getPawns()[1];

        nest.removePawn(pawn0);
        nest.removePawn(pawn1);
        nest.removePawn(pawn2);
        nest.removePawn(pawn3);

        home.addPawn(pawn3);
        space0.addOccupant(pawn0);
        space1.addOccupant(pawn1);
        space2.addOccupant(pawn2);
        blockedSpace.addOccupant(blockingPawn0);
        blockedSpace.addOccupant(blockingPawn1);

        //Scenario 1 - too high of dice for pawn 2
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(5);

        Move move = mainPlayer.doMiniMove(board, dice);

        assertEquals(move.getClass(), MoveMain.class);
        assertEquals(move.getDistance(), 5);
        assertEquals(move.getStart(), 32);
        assertEquals(move.getPawn(), pawn1);
    }

    @Test
    public void doesNotMoveBlockadeToetherOnDoublesRollWhenBlockadeIsMostAdvanced() throws Exception {
        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        Move enter0 = new EnterPiece(pawn0);
        Move enter1 = new EnterPiece(pawn1);
        Move enter2 = new EnterPiece(pawn2);
        Move enter3 = new EnterPiece(pawn3);
        Move move2 = new MoveMain(pawn2, pawn2.getExitSpaceId(), 10);
        Move move3 = new MoveMain(pawn3, pawn3.getExitSpaceId(), 11);

        assertEquals(MoveResult.ENTERED, enter2.run(board));
        assertEquals(MoveResult.ENTERED, enter3.run(board));
        assertEquals(MoveResult.SUCCESS, move2.run(board));
        assertEquals(MoveResult.SUCCESS, move3.run(board));

        assertEquals(MoveResult.ENTERED, enter0.run(board));
        assertEquals(MoveResult.ENTERED, enter1.run(board));

        assertEquals(RulesChecker.findBlockades(board, mainPlayer).get(0), pawn0.getExitSpace(board));

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(5);
        dice.add(5);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }

    @Test
    public void blockadeMovingTogether__REGRESSION_TEST() throws Exception {
        for(Player player: players){
            if(player.getColor() == Color.YELLOW){
                mainPlayer = (PlayerMachine) player;
                break;
            }
        }

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];


        board.getNests().get(Color.YELLOW).removePawn(pawn0);
        board.getNests().get(Color.YELLOW).removePawn(pawn1);
        board.getNests().get(Color.YELLOW).removePawn(pawn2);
        board.getNests().get(Color.YELLOW).removePawn(pawn3);

        board.getSpaceAt(41).addOccupant(pawn0);
        board.getSpaceAt(41).addOccupant(pawn1);
        board.getSpaceAt(42).addOccupant(pawn2);
        board.getHomeRows().get(Color.YELLOW).get(1).addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(4);
        dice.add(1);
        dice.add(1);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }


    @Test
    public void blockadeMovingTogetherWhenStaggered__REGRESSION_TEST() throws Exception {
        for(Player player: players){
            if(player.getColor() == Color.RED){
                mainPlayer = (PlayerMachine) player;
                break;
            }
        }

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];


        board.getNests().get(Color.RED).removePawn(pawn0);
        board.getNests().get(Color.RED).removePawn(pawn1);
        board.getNests().get(Color.RED).removePawn(pawn2);
        board.getNests().get(Color.RED).removePawn(pawn3);

        board.getSpaceAt(3).addOccupant(pawn2);
        board.getSpaceAt(4).addOccupant(pawn1);
        board.getSpaceAt(4).addOccupant(pawn0);
        board.getSpaceAt(5).addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(5);
        dice.add(5);
        dice.add(2);
        dice.add(2);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }

    @Test
    public void enterAndMoveIncorrectlyThrowingError__REGRESSION_TEST() throws Exception {
        Color color = Color.RED;

        for(Player player: players){
            if(player.getColor() == color){
                mainPlayer = (PlayerMachine) player;
                break;
            }
        }

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];


        board.getNests().get(color).removePawn(pawn0);
        board.getNests().get(color).removePawn(pawn1);
        board.getNests().get(color).removePawn(pawn2);
        board.getNests().get(color).removePawn(pawn3);

        board.getSpaceAt(3).addOccupant(pawn2);
        board.getSpaceAt(4).addOccupant(pawn1);
        board.getSpaceAt(4).addOccupant(pawn0);
        board.getSpaceAt(5).addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(5);
        dice.add(5);
        dice.add(2);
        dice.add(2);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }

    @Test
    public void wontChooseToMoveBlockadeWhenDoublesAllowBlockadeToEnterHomeRow__REGRESSION_TEST() throws Exception {
        Color color = Color.RED;

        for(Player player: players){
            if(player.getColor() == color){
                mainPlayer = (PlayerMachine) player;
                break;
            }
        }

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];


        board.getNests().get(color).removePawn(pawn0);
        board.getNests().get(color).removePawn(pawn1);

        board.getSpaceAt(6).addOccupant(pawn1);
        board.getSpaceAt(6).addOccupant(pawn0);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(6);
        dice.add(6);
        dice.add(1);
        dice.add(1);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }

    @Test
    public void dontMoveBlockadeWhenThereAreTwoBlockadesAndBackBlockadeIsBockaded__REGRESSION_TEST() throws Exception {
        Color color = Color.RED;
        Color color2 = Color.YELLOW;

        for(Player player: players){
            if(player.getColor() == color){
                mainPlayer = (PlayerMachine) player;
                break;
            }
        }

        for(Player player: players){
            if(player.getColor() == color2){
                playerBlocking = (PlayerMachine) player;
                break;
            }
        }

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];


        Pawn bpawn0 = playerBlocking.getPawns()[0];
        Pawn bpawn1 = playerBlocking.getPawns()[1];


        board.getNests().get(color).removePawn(pawn0);
        board.getNests().get(color).removePawn(pawn1);

        board.getNests().get(color).removePawn(pawn2);
        board.getNests().get(color).removePawn(pawn3);
        board.getNests().get(color).removePawn(bpawn0);
        board.getNests().get(color).removePawn(bpawn1);

        board.getSpaceAt(19).addOccupant(pawn0);
        board.getSpaceAt(19).addOccupant(pawn1);
        board.getSpaceAt(20).addOccupant(bpawn0);
        board.getSpaceAt(20).addOccupant(bpawn1);
        board.getHomeRows().get(color).get(2).addOccupant(pawn2);
        board.getHomeRows().get(color).get(2).addOccupant(pawn3);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(5);
        dice.add(5);
        dice.add(2);
        dice.add(2);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }

    @Test
    public void dontLetBlockadeMoveOnMultipleBops__REGRESSION_TEST() throws Exception {
        Color color = Color.RED;
        Color color2 = Color.YELLOW;

        for(Player player: players){
            if(player.getColor() == color){
                mainPlayer = (PlayerMachine) player;
                break;
            }
        }

        for(Player player: players){
            if(player.getColor() == color2){
                playerBlocking = (PlayerMachine) player;
                break;
            }
        }

        Pawn pawn0 = mainPlayer.getPawns()[0];
        Pawn pawn1 = mainPlayer.getPawns()[1];
        Pawn pawn2 = mainPlayer.getPawns()[2];
        Pawn pawn3 = mainPlayer.getPawns()[3];

        Pawn bPawn0 = playerBlocking.getPawns()[0];
        Pawn bPawn1 = playerBlocking.getPawns()[1];

        board.getNests().get(color).removePawn(pawn0);
        board.getNests().get(color).removePawn(pawn1);
        board.getNests().get(color).removePawn(pawn2);
        board.getNests().get(color).removePawn(pawn3);

        board.getSpaceAt(31).addOccupant(pawn0);
        board.getSpaceAt(31).addOccupant(pawn1);
        board.getSpaceAt(21).addOccupant(pawn2);
        board.getSpaceAt(22).addOccupant(pawn3);


        board.getSpaceAt(29).addOccupant(bPawn0);
        board.getSpaceAt(51).addOccupant(bPawn1);

        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(4);
        dice.add(4);
        dice.add(3);
        dice.add(3);

        ArrayList<Move> moves = mainPlayer.doMove(board, dice);
        assertTrue(RulesChecker.isSetOfMovesOkay(board, moves, mainPlayer));

    }

    @Test
    public void kickedOutOfTourney() throws Exception {
        String server = "<do-move><board><start><pawn><color>red</color><id>3</id></pawn><pawn><color>green</color><id>3</id></pawn><pawn><color>green</color><id>2</id></pawn><pawn><color>green</color><id>1</id></pawn><pawn><color>blue</color><id>0</id></pawn></start><main><piece-loc><pawn><color>yellow</color><id>0</id></pawn><loc>56</loc></piece-loc><piece-loc><pawn><color>red</color><id>2</id></pawn><loc>55</loc></piece-loc><piece-loc><pawn><color>red</color><id>0</id></pawn><loc>55</loc></piece-loc><piece-loc><pawn><color>blue</color><id>3</id></pawn><loc>54</loc></piece-loc><piece-loc><pawn><color>blue</color><id>2</id></pawn><loc>54</loc></piece-loc><piece-loc><pawn><color>blue</color><id>1</id></pawn><loc>53</loc></piece-loc><piece-loc><pawn><color>red</color><id>1</id></pawn><loc>52</loc></piece-loc><piece-loc><pawn><color>green</color><id>0</id></pawn><loc>47</loc></piece-loc><piece-loc><pawn><color>yellow</color><id>2</id></pawn><loc>4</loc></piece-loc></main><home-rows></home-rows><home><pawn><color>yellow</color><id>3</id></pawn><pawn><color>yellow</color><id>1</id></pawn></home></board><dice><die>5</die><die>6</die></dice></do-move>";

        Board board = XMLDecoder.getBoardFromDoMove(server,  game.getPlayers());
        ArrayList<Integer> dice = XMLDecoder.getDiceFromDoMove(server);

        game.getPlayers().get(3).doMove(board, dice);

    }

}