package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.exception.*;
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
            }
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
        return findIdOfMoveThatMakesBlockadeMove(moves, space, board) > 0;
    }

    public static int findIdOfMoveThatMakesBlockadeMove(List<Move> moves, Space space, Board board) {
        Pawn pawn1 = space.getOccupant1();
        Pawn pawn2 = space.getOccupant2();

        int pawn1Distance = 0;
        int pawn2Distance = 0;

        int mostRecent = -1;

        for(int i = 0; i < moves.size(); i ++){
            Move move = moves.get(i);
            if(move.getPawn() == pawn1){
                pawn1Distance += move.getDistance();
                mostRecent = i;
            }

            if(move.getPawn() == pawn2){
                pawn2Distance += move.getDistance();
                mostRecent = i;
            }
        }

        if(pawn1Distance > 0 && pawn1Distance == pawn2Distance){
            try{
                space.createMoveFromHere( pawn1Distance, pawn1).getDestinationSpace(board);
            } catch (InvalidMoveException e) {
                return mostRecent;
            } catch (GoesHomeException e) {
                return -1;
            }
            return mostRecent;
        }

        return -1;
    }

    public static boolean doBlockadesMove(List<Space> originalBlockadeList, Board board, Player player) {
        ArrayList<Space> newBlockades = RulesChecker.findBlockades(board, player);

        for(Space space: originalBlockadeList){
            for(Space newSpace: newBlockades){
                if(space.hasOccupant(newSpace.getOccupant1()) && space.hasOccupant(newSpace.getOccupant2())){
                    return true;
                };
            }
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

        if(board.isAtNest(pawn)){
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


    public static void checkForDoubles(List<Integer> dice, Pawn[] pawns, Board board) {
        if(dice.size() == 2 && dice.get(0).equals(dice.get(1))){
            if(board.allPawnsInPlay(pawns)){
                dice.add(7-dice.get(0));
                dice.add(7-dice.get(0));
            }
        }
    }

    public static Space getSpaceThatBlockadeMovedTo(ArrayList<Move> moveObjects, ArrayList<Space> originalBlockadeList, Board newBoard, Player player) throws SpaceNotFoundException{
        ArrayList<Space> blocks = findBlockades(newBoard, player);

        for(Space space: blocks){
            for(Space space2: originalBlockadeList){
                if(space.getOccupant1() == space2.getOccupant1() && space.getOccupant2()==space2.getOccupant2()){
                    return space2;
                }

                if(space.getOccupant2() == space2.getOccupant1() && space.getOccupant1()==space2.getOccupant2()){
                    return space2;
                }
            }
        }

       throw new SpaceNotFoundException();
    }


    public static void resolveBlockadeMoved(ArrayList<Move> moveObjects, ArrayList<Space> originalBlockadeList, Board brd, Player player){
        if(originalBlockadeList.size() == 0){
            //was called from local movement of blockades and it's okay
            return;
        }
//
//        int index = -1;
//        for (Space anOriginalBlockadeList : originalBlockadeList) {
//            index = findIdOfMoveThatMakesBlockadeMove(moveObjects, anOriginalBlockadeList, brd);
//            if (index > 0) {
//                break;
//            }
//        }

        Space space = null;
        try {
            space = getSpaceThatBlockadeMovedTo( moveObjects,  originalBlockadeList, brd, player);
        } catch (SpaceNotFoundException e) {
            e.printStackTrace();
        }

        for(Move move: moveObjects){
            if(move.getPawn().getId() == space.getOccupant2().getId() ||move.getPawn().getId() == space.getOccupant1().getId()){
                moveObjects.remove(move);
            }
        }
//
//        if(index > 0){
//            moveObjects.remove(index);
//        }else{
//            throw new SpaceNotFoundException();
//        }
    }
}
