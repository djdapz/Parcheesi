package parcheesi.game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 4/18/17.
 */
public class Turn {
    private Player player;
    private Board board;
    private Integer numberOfDoubles;

    public Turn(Player player, Board board) {
        this.player = player;
        this.board = board;
        this.numberOfDoubles = 0;
    }

    public TurnResult processSetOfMoves(List<Integer> moves){
        //This just manages the player going through a given set of options.
        //returns turn result, which can be cheated once, cheated twice, success, kicked out
        int numMoves = moves.size();
        Move currentMove;
        MoveResult mr;

        while(moves.size() != 0 && player.canMove(moves, board)){
            currentMove = player.doMove(board, moves);
            mr = currentMove.run(board);

            //if bopped a player, force player to move a pawn 20.
            //assuming that the player MUST do the bop next movement first
            if(mr == MoveResult.BOP){
                ArrayList<Integer> bonusMove = new ArrayList<Integer>();
                bonusMove.add(20);
                player.doMove(board, bonusMove);
            }


            if(mr == MoveResult.BLOCKED || mr == MoveResult.OVERSHOT){
                //reset and try again
                continue;
            }else if (mr == MoveResult.ENTERED){
                if((moves.contains(4) && moves.contains(1)) || (moves.contains(3) && moves.contains(2))){

                    moves= new ArrayList<>();
                }else if(moves.contains(5)){
                    moves.remove((moves.indexOf(5)));
                }else{
                    continue;
                }
            }else{
                moves.remove(moves.indexOf(currentMove.getDistance()));
            }

        }
        return TurnResult.SUCCESS;
    }


    public TurnResult play(){
        Dice die = new Dice();
        int die1;
        int die2;

        do{
            List<Integer> moves = new ArrayList<>();
            die1 = die.rollOne();
            die2 = die.rollOne();
            moves.add(die1);
            moves.add(die2);
            if(die1==die2){
                moves.add(7-die1);
                moves.add(7-die1);
                numberOfDoubles++;
            }

            if(numberOfDoubles == 3){
                break;
            }

            this.processSetOfMoves(moves);

        }while(die1 != die2);

        if(numberOfDoubles == 3){
            if(board.enforceDoublesPenalty(player)){
                return TurnResult.DOUBLESPENALTY;
            }

        }

        return TurnResult.SUCCESS;
    }
}
