package parcheesi.game;

import java.util.HashSet;
import java.util.List;
import java.util.Vector;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class Pawn {


    private int /* 0-3 */ id;
    private Color color;
    private Space exitSpace;
    private Space homeEntrance;


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


    public Space getExitSpace() {
        return exitSpace;
    }

    public void setExitSpace(Space exitSpace) {
        this.exitSpace = exitSpace;
    }

    public void setHomeEntrance(Space homeEntrance) {
        this.homeEntrance = homeEntrance;
    }

    public Space getHomeEntrance() {
        return homeEntrance;
    }

    public boolean canMove(List<Integer> moves, Board board) {

        Space space = board.findPawn(this);

        if (space == null) {
            if (board.getHome().isPawnHome(this)) {
                return false;
            } else {
                //space must be at the nest
                if (exitSpace.isBlockaded()) {
                    return false;
                } else {
                    //check all posibilities that this pawn can be moved out since the pawns are not trapped
                    if (moves.contains(5) || (moves.contains(4) && moves.contains(1)) || (moves.contains(3) && moves.contains(2))) {
                        return true;
                    }
                }
            }
        }
        int spaceIndex = space.getId();


        if (space.getRegion() == Color.HOME) {
            for (int moveNumber : moves) {
                MoveHome move = new MoveHome(this, spaceIndex, moveNumber);
                if (move.isValid(board).isSuccessfullMove()) {
                    return true;
                }
            }
        } else {
            for (int moveNumber : moves) {
                MoveMain move = new MoveMain(this, spaceIndex, moveNumber);
                if (move.isValid(board).isSuccessfullMove()) {
                    return true;
                }
            }
        }

        //IF no successfull move was found, then return false!
        return false;
    }

}
