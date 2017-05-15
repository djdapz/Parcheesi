package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.exception.BlockadeMovesException;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by devondapuzzo on 5/8/17.
 */
public class RulesChecker {
    public static boolean isSetOfMovesOkay(Board board, List<Move> moves, Player player) throws BlockadeMovesException, DuplicatePawnException {


        doBlockadesMove(board, moves, player);
        doPawnsBehaveProperly(board, player);

        return true;

    }

    public static void doBlockadesMove(Board board, List<Move> moves, Player player) throws BlockadeMovesException{
        ArrayList<Space> blockades = findBlockades(board, player);
        for(Space space: blockades){
            if(doesBlockadeMove(moves, space, board)){
                throw new BlockadeMovesException();
            };
        }
    }

    public static void doPawnsBehaveProperly(Board board, Player player) throws DuplicatePawnException {
        for(Pawn pawn:player.getPawns()){
            if(!doesPawnAppearOnlyOnce(board, pawn)){
                throw new DuplicatePawnException();
            }
        }
    }

    public static boolean doesBlockadeMove(List<Move> moves, Space space, Board board) {
        Pawn pawn1 = space.getOccupant1();
        Pawn pawn2 = space.getOccupant2();

        int pawn1Distance = 0;
        int pawn2Distance = 0;

        for(Move move: moves){
            if(move.getPawn() == pawn1){
                pawn1Distance += move.getDistance();
            }

            if(move.getPawn() == pawn2){
                pawn2Distance += move.getDistance();
            }
        }

        if(pawn1Distance > 0 && pawn1Distance == pawn2Distance){
            try{
                space.createMoveFromHere( pawn1Distance, pawn1).getDestinationSpace(board);
            } catch (InvalidMoveException e) {
                return true;
            } catch (GoesHomeException e) {
                return false;
            }
            return true;
        }

        return false;
    }

    public static boolean doesPawnAppearOnlyOnce(Board board, Pawn pawn){
        boolean found = false;
        Space space = board.findPawn(pawn);

        if(space != null){
            found = true;
        }



        if(board.getHome().isPawnHome(pawn)){
            if(found){
                return false;
            }else{
                found = true;
            }
        }

        if(board.getNests().get(pawn.getColor()).isAtNest(pawn)){
            if(found){
                return false;
            }else{
                found = true;
            }
        }

        if(board.findPawn(pawn, space) != null){
            return false;
        }

        return true;
    }

    public static ArrayList<Space> findBlockades(Board board, Player player){
        ArrayList<Space> blockedSpaces = new ArrayList<>();

        blockedSpaces.addAll(searchStripForBlockades(board.getSpaces(), player));
        blockedSpaces.addAll(searchStripForBlockades(board.getHomeRows().get(player.getColor()), player));

        return blockedSpaces;
    }

    private static ArrayList<Space> searchStripForBlockades(Vector<Space> spaces, Player player){
        ArrayList<Space> blockedSpaces = new ArrayList<>();

        for(Space space: spaces){
            if(space.isBlockaded()){
                if(space.getOccupant1().getColor() == player.getColor()){
                    blockedSpaces.add(space);
                }
            }
        }

        return blockedSpaces;
    }


}
