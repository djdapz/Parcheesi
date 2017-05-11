package parcheesi.game.board;

import parcheesi.game.player.Pawn;

import java.util.HashSet;
import java.util.Set;

/*
 * Created by devondapuzzo on 4/10/17.
 */
public class Home {

    private Set<Pawn> pawns;


    public Home() {
        pawns = new HashSet<>();
    }

    public boolean addPawn(Pawn pawn) {
        return pawns.add(pawn);
    }

    public boolean isPawnHome(Pawn pawn) {
        return pawns.contains(pawn);
    }

    public Home(Home home) {
        this.pawns = new HashSet<>(home.getPawns());
    }

    public Set<Pawn> getPawns() {
        return pawns;
    }

}
