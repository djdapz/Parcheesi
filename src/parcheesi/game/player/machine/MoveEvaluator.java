package parcheesi.game.player.machine;

import parcheesi.game.board.Board;
import parcheesi.game.player.Player;
import parcheesi.game.player.machine.heuristic.*;

import java.util.ArrayList;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public class MoveEvaluator {

    private int PAWNS_HOME_WEIGHT = 100;
    private int PAWNS_SAFE_WEIGHT = 100;
    private int PAWNS_AT_NEST_WEIGHT = -100;
    private int DISTANCE_TO_HOME_WEIGHT = 100;
    private double DISTANCE_TO_HOME_EXPONENT = 1.2;
    private int BLOCKADES_WEIGHT = 100;

    private ArrayList<Heuristic> heuristics = new ArrayList<>();

    public MoveEvaluator(){
        heuristics.add(new PawnsHomeHeuristic(PAWNS_HOME_WEIGHT));
        heuristics.add(new PawnsSafeHeuristic(PAWNS_SAFE_WEIGHT));
        heuristics.add(new PawnsAtNestHeuristic(PAWNS_AT_NEST_WEIGHT));
        heuristics.add(new BlockadesHeuristic(BLOCKADES_WEIGHT));
        heuristics.add(new DistanceToHomeHeuristic(DISTANCE_TO_HOME_WEIGHT, DISTANCE_TO_HOME_EXPONENT ));
    }



    public int score(Board board, Player player){
        int score = 0;
        for(Heuristic heuristic: heuristics){
            score += heuristic.evaluate(board, player);
        }
        return score;
    };
}
