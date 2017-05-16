package parcheesi.game.gameplay;

import parcheesi.game.board.Board;
import parcheesi.game.board.Dice;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;
import parcheesi.game.player.PlayerMachineFirst;
import parcheesi.game.player.PlayerMachineLast;

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
    private HashMap<Color, ArrayList<Move>> keepTrackOfMoves = new HashMap();

    public Game(){
        board = new Board();
        players = new ArrayList<>();
        colors = board.getColors();
        for(Color color: colors){
            keepTrackOfMoves.put(color, new ArrayList<>());
        }
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

        for(int i = 0; i < playersToAdd; i ++){
            if(die.rollOne() > 3){
                this.register(new PlayerMachineFirst());
            }else{
                this.register(new PlayerMachineLast());
            }
            this.register(new PlayerMachineFirst());
        }

        int exitLocation = board.getNestExit();
        int homeRowEntrance = board.getHomeRowEntrance();
        int regionLength = board.getBoardLength()/4;

        for(int i=0; i < colors.length; i ++){
            Player player = players.get(i);
            player.setColor(colors[i]);

            player.setNestExit(board.getSpaceAt(i*regionLength + exitLocation));
            player.setHomeEntrance(board.getSpaceAt(i*regionLength + homeRowEntrance));

            //adding pawns to nest for start of parcheesi.parcheesi.game.gameplay
            Nest nest = nests.get(player.getColor());

            for(Pawn pawn: player.getPawns()){
                nest.addPawn(pawn);
            }
            player.startGame(colors[i]);
        }


    }

    public Player play() throws Exception{
        int turnCount = 0;
        ArrayList<Move> thisTurnsMoves;
        while(!isWinner()) {
            for(int i = 0; i < players.size() && !isWinner(); i ++){
                if(!players.get(i).isKickedOut()){
                    currentTurn = new Turn(players.get(i), board);
                    try{
                        thisTurnsMoves = currentTurn.play();
                        keepTrackOfMoves.get(players.get(i).getColor()).addAll(thisTurnsMoves);
                    } catch (Exception e) {
                        throw e;
                    }
                }
            };
            turnCount++;
        }
        return getWinner();
    }

    private void removeFromGame(Player player, Board board) {
        //TODO - IMPLEMENT
        player.kickOut();
        for(Pawn pawn: player.getPawns()){
            this.board.findPawn(pawn).removeOccupant(pawn);
        }
    }

    public boolean isWinner(){

        if(this.getWinner() != null){
            return true;
        }else{
            return false;
        }
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
