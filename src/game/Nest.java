package game;

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

}
