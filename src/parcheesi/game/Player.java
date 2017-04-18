package parcheesi.game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Player implements PlayerInterface {
    private Color color;
    private String name;
    private Pawn[] pawns = new Pawn[4];
    private PlayerType playerType = PlayerType.MACHINE;
    private int cheatCount = 0;


    @Override
    public void startGame(String color) {

    }

    @Override
    public Move doMove(Board brd, List<Integer> dice) {
        if(this.playerType == PlayerType.HUMAN){
            this.askUserForMove(brd, dice);
        }else{
            this.decideBestMove(brd, dice);
        }
        return null;
    }

    @Override
    public void DoublesPenalty() {

    }

    private Move askUserForMove(Board brd, List<Integer> dice){

        return null;
    }

    private Move decideBestMove(Board brd, List<Integer> dice){

        return null;
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
            pawns[i].setExitSpace(space);
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

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void makePlayerHuman(){
        playerType = PlayerType.HUMAN;
    }

    public int getCheatCount() {
        return cheatCount;
    }

    public int increaseCheatCount() {
        this.cheatCount++;
        return this.cheatCount;
    }

    public void resetCheatCount() {
        this.cheatCount = 0;
    }


    public boolean isKickedOut() {
       return this.cheatCount >=3;
    }

}
