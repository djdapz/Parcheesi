package parcheesi.game.main;

import parcheesi.game.gameplay.Game;
import parcheesi.game.player.Player;
import parcheesi.game.player.PlayerAbstract;
import parcheesi.game.player.machine.MoveEvaluator;
import parcheesi.game.player.machine.PlayerMachineCustom;
import parcheesi.game.player.machine.PlayerMachineFirst;
import parcheesi.game.player.machine.PlayerMachineLast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Evaluator {

    public static void main(String[] args) throws Exception {
        System.out.println(findBestSetofHeuristics().toString());
    }

    public static void runTests(int numberOfTests) throws Exception {
        for(int i = 0; i < numberOfTests; i ++){
            Player winner = runTest();
            winner.incrementWins();
            System.out.println("Trial: " + Integer.toString(i)
                    + "   winner:  " + winner.getName());
        }

        System.out.println("First won " + Integer.toString(PlayerMachineFirst.getWins()) +
                ".  Last won " + Integer.toString(PlayerMachineLast.getWins()) +
                ".  Custom won: " + Integer.toString(PlayerMachineCustom.getWins()));

        System.out.println("First Kicked: " + Integer.toString(PlayerMachineFirst.getKickedOuts()) +
                ".  Last Kicked " + Integer.toString(PlayerMachineLast.getKickedOuts()) +
                ".  Custom Kicked: " + Integer.toString(PlayerMachineCustom.getKickedOuts()));
    }

    public static Player runTest() throws Exception {
        Game game = new Game();
        game.register(new PlayerMachineCustom());
        game.start();
        return game.play();
    }

    public static MoveEvaluator findBestSetofHeuristics() throws  Exception{
        ArrayList<PlayerMachineCustom> players = new ArrayList<>();
        int numPlayers = 16;
        int numRoundsPerIteration = 16;
        int numIterations = 100;
        double randomProbability = .05;

        for(int i = 0; i < numPlayers; i ++){
            players.add(new PlayerMachineCustom(true));
        }


        for(int i = 0; i < numIterations; i ++){
            playRounds(players, numRoundsPerIteration);
            System.out.println(players.get(0).getMoveEvaluator().toString());
            ArrayList<PlayerMachineCustom> newCustomSet = new ArrayList<>();

            for(int j = 0; j < numPlayers; j ++){
                MoveEvaluator me1 = RandomlySelect(players);
                MoveEvaluator me2 = RandomlySelect(players);

                MoveEvaluator child = new MoveEvaluator(me1, me2);

                child.mutate(randomProbability);
                newCustomSet.add(new PlayerMachineCustom(child));
            }

            players = newCustomSet;
        }

        return players.get(0).getMoveEvaluator();
    }


    private static MoveEvaluator RandomlySelect(ArrayList<PlayerMachineCustom> players) {
        int totalWins = 0;
        for (PlayerMachineCustom p : players) {
            totalWins += p.getIndividualWins();
        }

        int randomIndex = -1;
        double random = Math.random() * totalWins;

        for (int i = 0; i < players.size(); ++i) {
            random -= players.get(i).getIndividualWins();
            if (random <= 0.0d) {
                randomIndex = i;
                break;
            }
        }
        return players.get(randomIndex).getMoveEvaluator();
    }

    private static void playRounds(ArrayList<PlayerMachineCustom> players, int rounds) throws Exception {

        Integer numCustomPerRound = 2;
        for(int round = 0; round < rounds; round ++){
            Collections.shuffle(players);
            for(int gameNumber = 0; gameNumber < players.size()/numCustomPerRound; gameNumber++){
                Game game = new Game();
                for(int playerNumber = 0; playerNumber < numCustomPerRound; playerNumber ++ ){
                    game.register(players.get(gameNumber * numCustomPerRound + playerNumber));
                }
                game.start();
                game.play();
                game.getWinner().incrementIndividualWins();
            }
        }

        sortByWinCount(players);
        Collections.reverse(players);
    }

    private static void sortByWinCount(ArrayList<PlayerMachineCustom> players){
        Collections.sort(players, Comparator.comparingInt(PlayerAbstract::getIndividualWins));
    }

}
