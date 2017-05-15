package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.Strategy;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/4/17.
 */
public abstract class PlayerAbstract implements Player {

    protected Color color;
    private String name;
    protected final Pawn[] pawns = new Pawn[4];
    protected boolean kickedOut = false;

    @Override
    public String startGame(String color) {
        return name;
    }

    @Override
    public void DoublesPenalty() {

    }


    @Override
    public boolean canMove(List<Integer> moves, Board board, ArrayList<Space> originalBlockades){
        for(int i =0; i < pawns.length; i ++){
            if(pawns[i].canMoveWithoutMovingBlockades(moves, board, originalBlockades)){
                return true;
            };
        }
        return false;
    };


    public abstract ArrayList<Move> doMove(Board brd, List<Integer> dice) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException, Exception;

    public boolean hasWon(Home home){
        for(int i = 0; i <pawns.length; i++){
            if(!home.isPawnHome(pawns[i])){
                return false;
            }
        }
        return true;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.setPawnColor();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pawn[] getPawns() {
        return pawns;
    }

    public void setNestExit(Space space){
        for(int i = 0; i < pawns.length; i ++){
            pawns[i].setExitSpaceId(space.getId());
        }
    }

    public void setPawnColor(){
        for(int i = 0; i < pawns.length; i ++){
            pawns[i] = new Pawn(i, this.getColor());
        }
    }

    public void setHomeEntrance(Space space) {
        for(int i = 0; i < pawns.length; i ++){
            pawns[i].setHomeEntrance(space);
        }
    }

    public abstract Strategy getStrategy();

    public void kickOut(){this.kickedOut = true;}

    public boolean isKickedOut(){return  kickedOut;}
}
