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

    public Turn(Player player, Board board) {
        this.player = player;
        this.board = board;
    }


    public TurnResult play(){
        Dice die = new Dice();
        int die1;
        int die2;
        int numberOfDoubles;
        Move currentMove;
        MoveResult mr;
        int cheatCount;
        int numMoves;


        numberOfDoubles = 0;
        player.resetCheatCount();
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

            numMoves = moves.size();
            for(int j = 0; j < numMoves && !player.isKickedOut() && player.canMove(moves, board); j++){

                currentMove = player.doMove(board, moves);
                mr = currentMove.run(board);

                if(mr == MoveResult.BOP){
                    ArrayList<Integer> bonusMove = new ArrayList<Integer>();
                    bonusMove.add(20);
                    player.doMove(board, bonusMove);
                }


                if(mr == MoveResult.BLOCKED || mr == MoveResult.OVERSHOT){
                    cheatCount = player.increaseCheatCount();
                    //reset and try again
                    j--;
                }else if (mr == MoveResult.ENTERED){
                    if((moves.contains(4) && moves.contains(1)) || (moves.contains(3) && moves.contains(2))){
                        moves= new ArrayList<>();
                    }else if(moves.contains(5)){
                        moves.remove((moves.indexOf(5)));
                    }else{
                        player.increaseCheatCount();
                        j--;
                    }
                }else{
                    moves.remove(moves.indexOf(currentMove.getDistance()));
                }

                if(player.isKickedOut()){
                    return TurnResult.KICKEDOUT;
                }

            }

        }while(die1 != die2 && numberOfDoubles < 3);

        return TurnResult.SUCCESS;
    }
}
