package parcheesi.game.main;

import parcheesi.game.gameplay.Game;
import parcheesi.game.player.Player;
import parcheesi.game.player.machine.PlayerMachineCustom;
import parcheesi.game.player.machine.PlayerMachineFirst;
import parcheesi.game.player.machine.PlayerMachineLast;

public class Evaluator {

    public static void main(String[] args) throws Exception {
        runTests(10000);
        System.out.println("First won " + Integer.toString(PlayerMachineFirst.getWins()) +
                ".  Last won " + Integer.toString(PlayerMachineLast.getWins()) +
                ".  Custom won: " + Integer.toString(PlayerMachineCustom.getWins()));

        System.out.println("First Kicked: " + Integer.toString(PlayerMachineFirst.getKickedOuts()) +
                ".  Last Kicked " + Integer.toString(PlayerMachineLast.getKickedOuts()) +
                ".  Custom Kicked: " + Integer.toString(PlayerMachineCustom.getKickedOuts()));
    }

    public static void runTests(int numberOfTests) throws Exception {
        System.out.print('a');
        for(int i = 0; i < numberOfTests; i ++){
            Player winner = runTest();
            winner.incrementWins();
            System.out.println("Trial: " + Integer.toString(i)
                    + "   winner:  " + winner.getName());
        }
    }

    public static Player runTest() throws Exception {
        Game game = new Game();
        game.register(new PlayerMachineCustom());
        game.start();
        return game.play();
    }

}
