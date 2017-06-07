package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.BlockadeMovesException;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.gui.Messages;
import parcheesi.game.moves.Move;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/4/17.
 */
public abstract class PlayerAbstract implements Player {

    protected Color color;
    protected String name;
    protected final Pawn[] pawns = new Pawn[4];
    protected boolean kickedOut = false;
    private int individualWins = 0;

    @Override
    public String startGame(Color color) {
        Board board = new Board();

        int exitLocation = board.getNestExit();
        int homeRowEntrance = board.getHomeRowEntrance();
        int regionLength = board.getBoardLength()/4;

        setColor(color);

        setNestExit(board.getSpaceAt(color.getValue()*regionLength + exitLocation));
        setHomeEntrance(board.getSpaceAt(color.getValue()*regionLength + homeRowEntrance));

        return name;
    }

    @Override
    public void doublesPenalty() {

    }

    @Override
    public boolean canMove(ArrayList<Integer> moves, Board board, ArrayList<Space> originalBlockades){
        for(int i =0; i < pawns.length; i ++){
            if(pawns[i].canMoveWithoutMovingBlockades(moves, board, originalBlockades)){
                return true;
            };
        }
        return false;
    };

    @Override
    public ArrayList<Move> doMove(Board originalBoard, ArrayList<Integer> dice) throws InvalidMoveException, DuplicatePawnException, NoMoveFoundException, BlockadeMovesException, Exception {
        final ArrayList<Space> originalBlockadeList = RulesChecker.findBlockades(originalBoard, this);
        return doMove(originalBoard, dice, originalBlockadeList);
    }

    @Override
    public void messagePlayer(String string){
        return;
    }

    public ArrayList<Move> doMove(Board originalBoard, ArrayList<Integer> dice, ArrayList<Space> originalBlockadeList) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException, BlockadeMovesException {

        Move currentMove;
        ArrayList<Move> moveObjects = new ArrayList<>();
        Board brd = new Board(originalBoard);
        MoveResult mr;
        RulesChecker.checkForDoubles(dice, pawns, brd);

        ArrayList<Integer> tester= new ArrayList<>();
        tester.add(20);

        while(dice.size() != 0 && canMove(dice, brd, originalBlockadeList)){
            try{
                currentMove =  this.doMiniMove(brd, dice, originalBlockadeList);
                mr = currentMove.run(brd);
                handleMoveResult(mr, brd, currentMove, dice, moveObjects, originalBlockadeList);
            } catch (NoMoveFoundException e) {
                return moveObjects;
            } catch (InvalidMoveException e){
                throw e;
            }
        }

        try{
            RulesChecker.isSetOfMovesOkay(originalBoard, moveObjects, this);
        } catch (DuplicatePawnException | BlockadeMovesException e) {
            throw e;
        }

        return moveObjects;
    }

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
        if(name == null){
            return color.toString();
        }
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

    public void kickOut(){
        this.incrementKickedOuts();
        this.kickedOut = true;
    }

    public boolean isKickedOut(){return  kickedOut;}

    public abstract Move doMiniMove(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException;



    protected void handleMoveResult(MoveResult mr, Board brd, Move currentMove, ArrayList<Integer> dice, ArrayList<Move> moveObjects, ArrayList<Space> originalBlockadeList) throws InvalidMoveException, NoMoveFoundException, DuplicatePawnException, BlockadeMovesException {
//        if (mr == MoveResult.BOP) {
//           handleBop(brd, originalBlockadeList, currentMove, moveObjects, dice);
//        } else

        if(mr == MoveResult.ENTERED){
           handleEnter(moveObjects, currentMove, dice);
        }
        else if (mr == MoveResult.BLOCKED || mr == MoveResult.OVERSHOT) {
            moveObjects.remove(currentMove);
            messagePlayer(Messages.failedMove(mr));
        } else{
            handleSuccessfulMove(dice, currentMove, mr, moveObjects);
        }
    }

    protected void handleSuccessfulMove(ArrayList<Integer> dice, Move currentMove, MoveResult mr, ArrayList<Move> moveObjects) throws InvalidMoveException {
        if(mr == MoveResult.HOME){
            dice.add(10);
        }else if(mr == MoveResult.BOP){
            dice.add(20);
        }
        currentMove.editDice(dice);
        moveObjects.add(currentMove);
        messagePlayer(Messages.successfulMove(currentMove));
    };

    public void handleBop(Board brd, ArrayList<Space> originalBlockadeList, Move currentMove, ArrayList<Move> moveObjects, ArrayList<Integer> dice ) throws InvalidMoveException, DuplicatePawnException, BlockadeMovesException, NoMoveFoundException {
        ArrayList<Integer> bonusMoveDice = new ArrayList<>();
        bonusMoveDice.add(20);
        messagePlayer(Messages.bop);

        ArrayList<Move> bonusMoveItems = this.doMove(brd, bonusMoveDice, originalBlockadeList);

        for(Move move: bonusMoveItems){
            move.run(brd);
        }

        moveObjects.add(currentMove);
        moveObjects.addAll(bonusMoveItems);

        currentMove.editDice(dice);
        return;

//        dice.add(20);
//        currentMove.editDice(dice);
//        moveObjects.add(currentMove);
//        messagePlayer(Messages.successfulMove(currentMove));
    }


    protected void handleEnter(ArrayList<Move> moveObjects,  Move currentMove, List<Integer> dice) {
        moveObjects.add(currentMove);
        assert(
                (dice.contains(3) && dice.contains(2))
                    ||
                (dice.contains(3) && dice.contains(2))
                    ||
                (dice.contains(5))
            );

        if(dice.contains(3) && dice.contains(2)){
            dice.remove(dice.indexOf(2));
            dice.remove(dice.indexOf(3));
        }else if(dice.contains(4) && dice.contains(1)){
            dice.remove(dice.indexOf(4));
            dice.remove(dice.indexOf(1));
        }else if(dice.contains(5)){
            dice.remove(dice.indexOf(5));
        }
        messagePlayer(Messages.enter(currentMove));
    }



    @Override
    public void incrementWins(){
        throw new NotImplementedException();
    };

    @Override
    public void incrementKickedOuts(){throw new NotImplementedException();}

    @Override
    public void incrementIndividualWins() {
        this.individualWins++;
    }

    @Override
    public int getIndividualWins() {
        return individualWins;
    }
}
