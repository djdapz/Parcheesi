package parcheesi.game.player.machine;

import parcheesi.game.board.Board;
import parcheesi.game.player.Player;
import parcheesi.game.player.machine.heuristic.*;

import java.util.ArrayList;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public class MoveEvaluator {

    private int OTHER_PAWNS_AT_NEST_WEIGHT = 86;
    private int PAWNS_HOME_WEIGHT = 48;
    private int PAWNS_SAFE_WEIGHT = 69;
    private int PAWNS_AT_NEST_WEIGHT = -91;
    private int BLOCKADES_WEIGHT = 16;
    private int DISTANCE_TO_HOME_WEIGHT = -71;
    private float DISTANCE_TO_HOME_EXPONENT = (float) 7312002;

    private ArrayList<Heuristic> heuristics = new ArrayList<>();

    public MoveEvaluator(){
        heuristics.add(new PawnsHomeHeuristic(PAWNS_HOME_WEIGHT));
        heuristics.add(new PawnsSafeHeuristic(PAWNS_SAFE_WEIGHT));
        heuristics.add(new PawnsAtNestHeuristic(PAWNS_AT_NEST_WEIGHT));
        heuristics.add(new BlockadesHeuristic(BLOCKADES_WEIGHT));
        heuristics.add(new DistanceToHomeHeuristic(DISTANCE_TO_HOME_WEIGHT, DISTANCE_TO_HOME_EXPONENT));
        heuristics.add(new OtherPawnsAtNestHeuristic(OTHER_PAWNS_AT_NEST_WEIGHT));
    }

    public MoveEvaluator(boolean b) {
        setBLOCKADES_WEIGHT(getRandomNumber(0, 100));
        setDISTANCE_TO_HOME_EXPONENT(getRandomNumber(0.1, 2.0));
        setDISTANCE_TO_HOME_WEIGHT(getRandomNumber(0, -100));
        setPAWNS_AT_NEST_WEIGHT(getRandomNumber(0, -100));
        setPAWNS_HOME_WEIGHT(getRandomNumber(0, 100));
        setPAWNS_SAFE_WEIGHT(getRandomNumber(0, 100));
        setOTHER_PAWNS_AT_NEST_WEIGHT(getRandomNumber(0, 100));
        heuristics.add(new PawnsHomeHeuristic(PAWNS_HOME_WEIGHT));
        heuristics.add(new PawnsSafeHeuristic(PAWNS_SAFE_WEIGHT));
        heuristics.add(new PawnsAtNestHeuristic(PAWNS_AT_NEST_WEIGHT));
        heuristics.add(new BlockadesHeuristic(BLOCKADES_WEIGHT));
        heuristics.add(new DistanceToHomeHeuristic(DISTANCE_TO_HOME_WEIGHT, DISTANCE_TO_HOME_EXPONENT));
    }

    public MoveEvaluator(MoveEvaluator m1, MoveEvaluator m2) {
        setBLOCKADES_WEIGHT(chooseBetweenTwo(m1.getBLOCKADES_WEIGHT(), m2.getBLOCKADES_WEIGHT()));
        setDISTANCE_TO_HOME_EXPONENT(chooseBetweenTwo(m1.getDISTANCE_TO_HOME_EXPONENT(), m2.getDISTANCE_TO_HOME_EXPONENT()));
        setDISTANCE_TO_HOME_WEIGHT(chooseBetweenTwo(m1.getDISTANCE_TO_HOME_WEIGHT(), m2.getDISTANCE_TO_HOME_WEIGHT()));
        setPAWNS_AT_NEST_WEIGHT(chooseBetweenTwo(m1.getPAWNS_AT_NEST_WEIGHT(), m2.getPAWNS_AT_NEST_WEIGHT()));
        setPAWNS_HOME_WEIGHT(chooseBetweenTwo(m1.getPAWNS_HOME_WEIGHT(), m2.getPAWNS_HOME_WEIGHT()));
        setPAWNS_SAFE_WEIGHT(chooseBetweenTwo(m1.getPAWNS_SAFE_WEIGHT(), m2.getPAWNS_SAFE_WEIGHT()));
        setOTHER_PAWNS_AT_NEST_WEIGHT(chooseBetweenTwo(m1.getOTHER_PAWNS_AT_NEST_WEIGHT(), m2.getOTHER_PAWNS_AT_NEST_WEIGHT()));
        heuristics.add(new PawnsHomeHeuristic(PAWNS_HOME_WEIGHT));
        heuristics.add(new PawnsSafeHeuristic(PAWNS_SAFE_WEIGHT));
        heuristics.add(new PawnsAtNestHeuristic(PAWNS_AT_NEST_WEIGHT));
        heuristics.add(new BlockadesHeuristic(BLOCKADES_WEIGHT));
        heuristics.add(new DistanceToHomeHeuristic(DISTANCE_TO_HOME_WEIGHT, DISTANCE_TO_HOME_EXPONENT));
    }

    private float chooseBetweenTwo(float o1, float o2) {
        if(Math.random() > .5){
            return o1;
        }else{
            return o2;
        }
    }

    private int chooseBetweenTwo(int o1, int o2) {
        if(Math.random() > .5){
            return o1;
        }else{
            return o2;
        }
    }

    public int score(Board board, Player player) {
        int score = 0;
        for (Heuristic heuristic : heuristics) {
            score += heuristic.evaluate(board, player);
        }
        return score;
    }

    public int getPAWNS_HOME_WEIGHT() {
        return PAWNS_HOME_WEIGHT;
    }

    public void setPAWNS_HOME_WEIGHT(int PAWNS_HOME_WEIGHT) {
        this.PAWNS_HOME_WEIGHT = PAWNS_HOME_WEIGHT;
    }

    public int getPAWNS_SAFE_WEIGHT() {
        return PAWNS_SAFE_WEIGHT;
    }

    public void setPAWNS_SAFE_WEIGHT(int PAWNS_SAFE_WEIGHT) {
        this.PAWNS_SAFE_WEIGHT = PAWNS_SAFE_WEIGHT;
    }

    public int getPAWNS_AT_NEST_WEIGHT() {
        return PAWNS_AT_NEST_WEIGHT;
    }

    public void setPAWNS_AT_NEST_WEIGHT(int PAWNS_AT_NEST_WEIGHT) {
        this.PAWNS_AT_NEST_WEIGHT = PAWNS_AT_NEST_WEIGHT;
    }

    public int getBLOCKADES_WEIGHT() {
        return BLOCKADES_WEIGHT;
    }

    public void setBLOCKADES_WEIGHT(int BLOCKADES_WEIGHT) {
        this.BLOCKADES_WEIGHT = BLOCKADES_WEIGHT;
    }

    public int getDISTANCE_TO_HOME_WEIGHT() {
        return DISTANCE_TO_HOME_WEIGHT;
    }

    public void setDISTANCE_TO_HOME_WEIGHT(int DISTANCE_TO_HOME_WEIGHT) {
        this.DISTANCE_TO_HOME_WEIGHT = DISTANCE_TO_HOME_WEIGHT;
    }

    public float getDISTANCE_TO_HOME_EXPONENT() {
        return DISTANCE_TO_HOME_EXPONENT;
    }

    public void setDISTANCE_TO_HOME_EXPONENT(float DISTANCE_TO_HOME_EXPONENT) {
        this.DISTANCE_TO_HOME_EXPONENT = DISTANCE_TO_HOME_EXPONENT;
    }

    public int getOTHER_PAWNS_AT_NEST_WEIGHT() {
        return OTHER_PAWNS_AT_NEST_WEIGHT;
    }

    public void setOTHER_PAWNS_AT_NEST_WEIGHT(int OTHER_PAWNS_AT_NEST_WEIGHT) {
        this.OTHER_PAWNS_AT_NEST_WEIGHT = OTHER_PAWNS_AT_NEST_WEIGHT;
    }

    public int getRandomNumber(int min, int max){
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (min <= max ? min : max);
    }

    public float getRandomNumber(double minD, double maxD){
        double min = (float) minD;
        double max = (float) maxD;
        double range = Math.abs(max - min);
        return (float) ((float) (Math.random() * range) + (min <= max ? min : max));
    }

    public void mutate(double prob) {
        double random = Math.random();

        if(random < prob){
            setBLOCKADES_WEIGHT(getRandomNumber(0, 100));
        }

        if(random < prob){
            setDISTANCE_TO_HOME_EXPONENT(getRandomNumber(0.1, 2.0));
        }

        if(random < prob){
            setDISTANCE_TO_HOME_WEIGHT(getRandomNumber(0, -100));
        }

        if(random < prob){
            setPAWNS_AT_NEST_WEIGHT(getRandomNumber(0, -100));
        }

        if(random < prob){
            setPAWNS_HOME_WEIGHT(getRandomNumber(0, 100));
        }

        if(random < prob){
            setPAWNS_SAFE_WEIGHT(getRandomNumber(0, 100));
        }
    }

    public String toString(){
       return "===========================\n"+
               "Blockades: " + getBLOCKADES_WEIGHT() + "\n" +
               "Distance To Home W: " + getDISTANCE_TO_HOME_WEIGHT() +"\n" +
               "Distance To Home E: " + getDISTANCE_TO_HOME_EXPONENT() +"\n" +
               "Pawns At Nest: " + getPAWNS_AT_NEST_WEIGHT() +"\n" +
               "Pawns At Home: " + getPAWNS_HOME_WEIGHT() +"\n" +
               "Pawns Safe: " + getPAWNS_SAFE_WEIGHT() + "\n" +
                "Other Pawns At Nest: " + getOTHER_PAWNS_AT_NEST_WEIGHT() + "\n";

    }
}
