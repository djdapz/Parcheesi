package parcheesi.game.moves;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.parser.XMLConstant;
import parcheesi.game.player.Pawn;

import java.util.ArrayList;

/**
 * Created by devondapuzzo on 5/14/17.
 */
public abstract class MoveAbstract implements Move {
    protected Pawn pawn;
    protected int start;
    protected int distance;

    public MoveAbstract(Pawn pawn, int start, int distance) {
        this.pawn=pawn;
        this.start=start;
        this.distance=distance;
    }

    public MoveAbstract(Pawn pawn) {
        this.pawn=pawn;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public Pawn getPawn() {
        return pawn;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public abstract MoveResult run(Board board);



    @Override
    public abstract Space getDestinationSpace(Board board) throws GoesHomeException, InvalidMoveException;

    @Override
    public abstract String getStringOfMove() ;

    @Override
    public abstract XMLConstant getXMLConstant();

    @Override
    public abstract String getXMLString();

    @Override
    public void editDice(ArrayList<Integer> moves) throws InvalidMoveException {
        try{
            moves.remove(moves.indexOf(getDistance()));
        }catch (Exception e){
            throw e;
        }
    }
}
