package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.exception.BadMoveException;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;

import java.util.List;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class Pawn {


    private int /* 0-3 */ id;
    private Color color;
    private int exitSpaceId;
    private int homeEntranceId;


    public Pawn(int id, Color color) {
        this.id = id;
        this.color = color;
    }


    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public int getExitSpaceId() {
        return exitSpaceId;
    }

    public void setExitSpaceId(int exitSpaceId) {
        this.exitSpaceId = exitSpaceId;
    }

    public Space getExitSpace(Board board){
        return board.getSpaceAt(this.exitSpaceId);
    }

    public void setExitSpace(Space space){
        this.exitSpaceId = space.getId();
    }

    public void setHomeEntrance(Space homeEntrance) {
        this.homeEntranceId = homeEntrance.getId();
    }

    public Space getHomeEntrance(Board board) {
        return board.getSpaceAt(this.homeEntranceId);
    }

    public void setHomeEntranceId(int id){
        this.homeEntranceId = id;
    }

    public int getHomeEntranceId() {
        return homeEntranceId;
    }

    public boolean canMove(List<Integer> moves, Board board) {

        Space space = board.findPawn(this);

        if (space == null) {
            if (board.getHome().isPawnHome(this)) {
                return false;
            } else {
                //space must be at the nest
                if (board.getSpaceAt(exitSpaceId).isBlockaded()) {
                    return false;
                } else {
                    //check all posibilities that this pawn can be moved out since the pawns are not trapped
                    if (moves.contains(5)
                            || (moves.contains(4) && moves.contains(1))
                            || (moves.contains(3) && moves.contains(2))) {
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        int spaceIndex = space.getId();


        for (int moveNumber : moves) {
            Move move;
            if (space.getRegion() == Color.HOME) {
                move = new MoveHome(this, spaceIndex, moveNumber);
            }else{
                move = new MoveMain(this, spaceIndex, moveNumber);
            }

            try{
                move.getDestinationSpace(board);
                return true;
            } catch (GoesHomeException e) {
                return true;
            } catch (BadMoveException e) {
                continue;
            }
        }


        //IF no successfull move was found, then return false!
        return false;
    }

}
