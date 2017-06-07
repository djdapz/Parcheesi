package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Dice;
import parcheesi.game.exception.BlockadeMovesException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Player;

import java.util.ArrayList;

/**
 * Created by devondapuzzo on 4/18/17.
 */
public class Turn {
    private Player player;
    private Board board;
    private Integer numberOfDoubles;
    private RulesChecker rulesChecker;

    public Turn(Player player, Board board) {
        this.player = player;
        this.board = board;
        this.numberOfDoubles = 0;
        this.rulesChecker = new RulesChecker();
    }


    public void processSetOfMoves(ArrayList<Move> moves) throws InvalidMoveException {
        for(Move move: moves){
            move.run(board);
        }
    }


    public void play() throws Exception {
        Dice die = new Dice();
        ArrayList<Move> moves;
        int die1;
        int die2;


        do{
            ArrayList<Integer> dice = new ArrayList<>();
            die1 = die.rollOne();
            die2 = die.rollOne();
            dice.add(die1);
            dice.add(die2);

            if(die1==die2){
                numberOfDoubles++;
            }

            if(numberOfDoubles == 3){
                board.enforceDoublesPenalty(player);
                player.doublesPenalty();
                break;
            }

            try{
                moves = player.doMove(board, dice);
                rulesChecker.doBlockadesMove(board, moves, player);
                processSetOfMoves(moves);
                rulesChecker.doPawnsBehaveProperly(board, player);
            }catch(BlockadeMovesException e){
                kickOut(player, board);
                break;
            } catch(Exception e){
                throw e;
            }
        }
        while(die1 == die2);

    }

    public void kickOut(Player player, Board board){
        player.kickOut();
        try {
            board.kickOut(player);
        } catch (PawnNotFoundException e) {
            e.printStackTrace();
        }
    }
}
