package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.parser.XMLConstant;
import parcheesi.game.parser.XMLConstants;
import parcheesi.game.parser.XMLEncoder;
import parcheesi.game.player.Pawn;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class MoveMain extends MoveAbstract {

    public MoveMain(Pawn pawn, int start, int distance) {
        super(pawn, start, distance);
    }

    @Override
    public Space getStart(Board board) {
        return board.getSpaceAt(start);
    }

    @Override
    public MoveResult run(Board board) {
        Space newSpace;
        assertTrue(board.getSpaceAt(this.start).hasOccupant(pawn));

        try {
            newSpace =  this.getDestinationSpace(board);
        } catch (GoesHomeException e) {
            board.getSpaceAt(start).removeOccupant(pawn);
            board.getHome().addPawn(pawn);
            return MoveResult.HOME;
        } catch (InvalidMoveException e) {
            return e.getMoveResult();
        }

        board.getSpaceAt(start).removeOccupant(pawn);
        MoveResult moveResult = newSpace.addOccupant(pawn);

        if(moveResult == MoveResult.BLOCKED){
            //if bop was blocked then replace the occupant
            board.getSpaceAt(start).addOccupant(pawn);
        }


        if(moveResult == MoveResult.BOP){
            //add bopped pawn to its nest
            Pawn boppedPawn = newSpace.getOccupant2();
            HashMap<Color, Nest> nests = board.getNests();
            Nest bopToNest = nests.get(boppedPawn.getColor());
            bopToNest.addPawn(boppedPawn);


            newSpace.removeOccupant(boppedPawn);
            return MoveResult.BOP;
        }else{
            return moveResult;
        }
    }

    @Override
    public Space getDestinationSpace(Board board) throws GoesHomeException, InvalidMoveException {
        assert(distance < board.getBoardLength());

        int newSpot = (start + distance) % board.getBoardLength() ;
        int remaining = distance;
        int numberToGoDownHomeRow = 0;

        Space thisSpace = board.getSpaceAt(start);

        if(thisSpace == pawn.getHomeEntrance(board)){
            numberToGoDownHomeRow = remaining;
        }else{
            for(int i = start+1; remaining > 0; remaining--){
                int spaceIndex = i % board.getBoardLength();

                if(board.getSpaceAt(spaceIndex).isBlockaded()) {
                    throw new InvalidMoveException(MoveResult.BLOCKED);
                }

                if(spaceIndex == pawn.getHomeEntranceId()){
                    numberToGoDownHomeRow = remaining-1;
                    break;
                }
                i++;
            }
        }

        if(numberToGoDownHomeRow > 0){
            MoveHome moveHome = new MoveHome(pawn, -1, numberToGoDownHomeRow);
            return moveHome.getDestinationSpace(board);
        }else{
            Space newSpace = board.getSpaceAt(newSpot);
            if(newSpace.getOccupant1() != null){
                if(newSpace.getOccupant1().getColor() != pawn.getColor()){
                    if(newSpace.isSafeSpace()){
                        throw new InvalidMoveException(MoveResult.BLOCKED);
                    };
                }
            }
            return board.getSpaceAt(newSpot);
        }

    }

    @Override
    public String getStringOfMove() {
        return "Player " + pawn.getColor().toString() + ", MAIN_MOVE  "+
                "Distance: " + Integer.toString(distance) + "   " +
                "Start: " + Integer.toString(start);
    }

    @Override
    public XMLConstant getXMLConstant() {
        return XMLConstants.MOVE_MAIN;
    }

    @Override
    public String getXMLString() {
        return getXMLConstant().element(
                XMLEncoder.encodePawn(pawn)
                        + XMLConstants.START.element(start)
                        + XMLConstants.DISTANCE.element(start)
        );
    }
}
