package game;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class Pawn {

    private int /* 0-3 */ id;
    private Color color;
    private Space exitSpace;
    private Space homeEntrance;


    public Pawn (int id, Color color){
        this.id = id;
        this.color = color;
    }



    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color){this.color = color;}


    public Space getExitSpace() {
        return exitSpace;
    }
    public void setExitSpace(Space exitSpace){
        this.exitSpace = exitSpace;
    }
    public void setHomeEntrance(Space homeEntrance) {
        this.homeEntrance=homeEntrance;
    }

    public Space getHomeEntrance() {
        return homeEntrance;
    }
}
