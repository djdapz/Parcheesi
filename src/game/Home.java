package game;

import game.Pawn;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public class Home {

    private Set<Pawn> pawns;

    public Home(){
        pawns = new HashSet<>();
    }

    public boolean addPawn(Pawn pawn){
        return pawns.add(pawn);
    }

    public boolean isPawnHome(Pawn pawn){
        return pawns.contains(pawn);
    }
}
