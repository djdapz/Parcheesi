package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.parser.XMLConstant;
import parcheesi.game.parser.XMLConstants;
import parcheesi.game.parser.XMLEncoder;
import parcheesi.game.player.Pawn;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class EnterPiece extends MoveAbstract {
    public EnterPiece(Pawn pawn) {
        super(pawn);
    }

    @Override
    public int getStart() {
        return 0;
    }

    @Override
    public Space getStart(Board board) {
        return null;
    }

    @Override
    public Pawn getPawn() {
        return pawn;
    }

    public MoveResult run(Board board){
        Space exitSpace = pawn.getExitSpace(board);

        if(exitSpace.isBlockaded()){
            return MoveResult.BLOCKED;
        }else{
            MoveResult mr = exitSpace.addOccupant(pawn);
            if(mr == MoveResult.SUCCESS){
                board.getNests().get(pawn.getColor()).removePawn(pawn);
                return MoveResult.ENTERED;
            }else if(mr == MoveResult.BOP) {
                board.getNests().get(pawn.getColor()).removePawn(pawn);
                Pawn boppedPawn = exitSpace.getOccupant2();
                HashMap<Color, Nest> nests = board.getNests();
                Nest bopToNest = nests.get(boppedPawn.getColor());
                bopToNest.addPawn(boppedPawn);

                exitSpace.removeOccupant(boppedPawn);
                return MoveResult.BOP;
            }else{
                return mr;
            }
        }
    }

    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public Space getDestinationSpace(Board board) throws InvalidMoveException{
        Space entranceSpace = pawn.getExitSpace(board);
        if(entranceSpace.isBlockaded()) {
            throw new InvalidMoveException();
        }
        return entranceSpace;
    }

    @Override
    public String getStringOfMove() {
        return "Player " + pawn.getColor().toString() + ", EnterPiece  ";
    }

    @Override
    public XMLConstant getXMLConstant() {
        return XMLConstants.ENTER_PIECE;
    }

    @Override
    public String getXMLString() {
        return getXMLConstant().element(
                XMLEncoder.encodePawn(pawn)
        );
    }

    @Override
    public void editDice(ArrayList<Integer> moves) throws InvalidMoveException {
        if((moves.contains(4) && moves.contains(1)) || (moves.contains(3) && moves.contains(2))){
            moves.clear();
        }else if(moves.contains(5)){
            moves.remove((moves.indexOf(5)));
        }else{
            throw new InvalidMoveException();
        }
    }
}
