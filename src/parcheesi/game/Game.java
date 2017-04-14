package parcheesi.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Game implements GameInterface {

    private Player[] players = new Player[4];
    private Board board;

    @Override
    public void register(Player P) {

    }

    @Override
    public void start() {
        board = new Board();
        HashMap<Color, Nest> nests = board.getNests();

        //for now we just create 4 basic players
        Color[] colors = board.getColors();

        for(int i = 0; i < players.length; i ++){
            players[i] = new Player();
        }

        players[0].setName("Red");
        players[1].setName("Blue");
        players[2].setName("Yellow");
        players[3].setName("Green");

        for(int i = 0; i < players.length; i ++){
            players[i].setColor(colors[i]);
        }

        int exitLocation = board.getNestExit();
        int homeRowEntrance = board.getHomeRowEntrance();
        int regionLength = board.getBoardLength()/4;

        for(int i=0; i < colors.length; i ++){
            players[i].setNestExit(board.getSpaceAt(i*regionLength + exitLocation));
            players[i].setHomeEntrance(board.getSpaceAt(i*regionLength + homeRowEntrance));

            //adding pawns to nest for start of parcheesi.game
            Pawn[] tempPawns = players[i].getPawns();
            Nest tempNest = nests.get(players[i].getColor());
            for(int j=0; j < 4; j ++){
                tempNest.addPawn(tempPawns[i]);
            }
        }
    }

    public void play(){
        Dice die = new Dice();
        int die1;
        int die2;
        int numberOfDoubles;
        Move currentMove;
        MoveResult mr;
        int cheatCount;
        int numMoves;

        while(!isWinner()) {
            for(int i = 0; i < players.length && !isWinner(); i ++){
                numberOfDoubles = 0;
                players[i].resetCheatCount();
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
                    for(int j = 0; j < numMoves && !players[i].isKickedOut(); j++){

                        // deep copy moves list incase user cheats and needs to try again
                        List<Integer> clone = new ArrayList<Integer>();
                        for(Integer move : moves){
                            clone.add(move);
                        }

                        currentMove = players[i].doMove(board, moves);
                        mr = currentMove.run(board);



                        if(mr == MoveResult.BLOCKED || mr == MoveResult.OVERSHOT){
                            cheatCount = players[i].increaseCheatCount();
                            //reset and try again
                            moves = clone;
                            j--;
                        }

                        if(players[i].isKickedOut()){
                            kickOutPlayer(players[i]);
                        }

                    }

                }while(die1 != die2 && numberOfDoubles < 3);

                //TODO - Continue turn

            };
        }

    }

    public boolean isWinner(){
        Home home = board.getHome();
        for(int i = 0; i < players.length; i ++){
            if(players[i].hasWon(home)){
                return true;
            }
        }
        return true;
    }

    private void kickOutPlayer(Player player){

    };

    public Player[] getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }
}
