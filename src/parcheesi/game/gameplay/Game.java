package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Dice;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.player.*;
import parcheesi.game.player.machine.PlayerMachineFirst;
import parcheesi.game.player.machine.PlayerMachineLast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Game implements GameInterface {

    private ArrayList<Player> players;
    private Board board;
    private Turn currentTurn;
    private Color[] colors;
    private final int maxPlayers = 4;
    private int round =0;

    public Game(){
        board = new Board();
        players = new ArrayList<>();
        colors = board.getColors();
    }

    @Override
    public void register(Player P) {
        if(players.size() < maxPlayers){
            players.add(P);
        }
    }

    @Override
    public void start() {
        HashMap<Color, Nest> nests = board.getNests();
        Dice die = new Dice();

        int playersToAdd = maxPlayers - players.size();

        //random selection from strategies
        for(int i = 0; i < playersToAdd; i ++){
            if(die.rollOne() > 3){
                this.register(new PlayerMachineFirst());
            }else{
                this.register(new PlayerMachineLast());
            }
            this.register(new PlayerMachineFirst());
        }

        for(int i=0; i < colors.length; i ++){
            Player player = players.get(i);
            player.startGame(colors[i]);

            //adding pawns to nest for start of parcheesi.parcheesi.game.gameplay
            Nest nest = nests.get(player.getColor());

            for(Pawn pawn: player.getPawns()){
                nest.addPawn(pawn);
            }

        }


    }

    public Player play() throws Exception{
        while(!isWinner()) {
            round ++;
            for(int i = 0; i < players.size() && !isWinner(); i ++){
                if(!players.get(i).isKickedOut()){
                    currentTurn = new Turn(players.get(i), board);
                    currentTurn.play();
                }
            }
        }
        return getWinner();
    }


    public boolean isWinner(){
        return !(this.getWinner() == null);
    }

    public Player getWinner(){
        Home home = board.getHome();
        for(int i = 0; i < players.size(); i ++){
            if(players.get(i).hasWon(home)){
                return players.get(i);
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }
}
