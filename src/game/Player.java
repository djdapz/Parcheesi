package game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Player implements PlayerInterface {
    private Color color;
    private String name;
    private Pawn[] pawns = new Pawn[4];


    @Override
    public void startGame(String color) {

    }

    @Override
    public Move doMove(Board brd, int[] dice) {
        return null;
    }

    @Override
    public void DoublesPenalty() {

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

    public boolean hasWon(Home home){
        for(int i = 0; i <pawns.length; i++){
            if(home.isPawnHome(pawns[i]) == false){
                return false;
            }
        }
        return true;
    }

    public void setPawnColor(){
        for(int i = 0; i < pawns.length; i ++){
            pawns[i] = new Pawn(i, this.getColor());
        }
    }

    public boolean makeMove(){
        throw new NotImplementedException();
    }

    public void setHomeEntrance(Space space) {
        for(int i = 0; i < pawns.length; i ++){
            pawns[i].setHomeEntrance(space);
        }
    }
}
