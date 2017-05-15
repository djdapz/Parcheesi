package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Dice;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.enums.TurnResult;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 4/18/17.
 */
public class Turn {
    private Player player;
    private Board board;
    private Integer numberOfDoubles;
    private List<Integer> dice;
    private ArrayList<Move> moves;
    private Move currentMove;
    private int numTries;
    private int numMoves;
    private MoveResult mr;
    private RulesChecker rulesChecker;

    public Turn(Player player, Board board) {
        this.player = player;
        this.board = board;
        this.numberOfDoubles = 0;
        this.rulesChecker = new RulesChecker();
    }


    public TurnResult processSetOfMoves(ArrayList<Move> moves) throws InvalidMoveException {
        for(Move move: moves){
            move.run(board);
        }
        return TurnResult.SUCCESS;
    }


    public ArrayList<Move> play() throws Exception {
        Dice die = new Dice();
        ArrayList<Move> movesForThisTurn = new ArrayList<>();
        int die1;
        int die2;

        do{
            dice = new ArrayList<>();
            die1 = die.rollOne();
            die2 = die.rollOne();
            dice.add(die1);
            dice.add(die2);
            if(die1==die2){
                dice.add(7-die1);
                dice.add(7-die1);
                numberOfDoubles++;
            }

            if(numberOfDoubles == 3){
                break;
            }

            try{
                moves = player.doMove(board, dice);
                rulesChecker.doBlockadesMove(board, moves, player);
                processSetOfMoves(moves);
                rulesChecker.doPawnsBehaveProperly(board, player);
                movesForThisTurn.addAll(moves);

            }catch (Exception e){
                throw e;
            }
        }
        while(die1 == die2);

        if(numberOfDoubles == 3){
            if(board.enforceDoublesPenalty(player)){
                //return TurnResult.DOUBLESPENALTY;
            }

        }

        return movesForThisTurn;
    }
}
