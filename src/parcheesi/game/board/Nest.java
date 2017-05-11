package parcheesi.game.board;

import parcheesi.game.player.Pawn;
import parcheesi.game.enums.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Nest {

    private Set<Pawn> pawns;
    private Color color;


    public Nest(Color color){
        this.color = color;
        this.pawns = new HashSet<>();
    }

    public Nest(Nest nest){
        this.color = nest.color;
        this.pawns = new HashSet<>(nest.getPawns());
    }

    public Color getColor() {
        return color;
    }

    public boolean removePawn(Pawn pawn){
        return pawns.remove(pawn);
    }

    public boolean addPawn(Pawn pawn){
        return pawns.add(pawn);
    }

    public boolean isAtNest(Pawn pawn){
        return pawns.contains(pawn);
    }

    public int getSize(){
        return pawns.size();
    }

    public Set<Pawn> getPawns() {
        return pawns;
    }
}
