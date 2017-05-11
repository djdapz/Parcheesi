package parcheesi.game;

import parcheesi.game.enums.Strategy;
import parcheesi.game.gameplay.Game;

public class Evaluator {

    public static void main(String[] args) throws Exception {

        try{
            System.out.print(runTests(100000));
        }catch (Exception e){
            throw e;
        }
    }

    public static String runTests(int numberOfTests) throws Exception {
        int numFirstWins = 0;
        int numLastWins = 0;
        Strategy result;

        for(int i = 0; i < numberOfTests; i ++){
            try{
                result = runTest();
            }catch (Exception e){
                throw e;
            }
            if(result == Strategy.FIRST){
                numFirstWins++;
            }else{
                numLastWins++;
            }
            System.out.println("Trial: " + Integer.toString(i)
                    + "   winner:  " + result);
        }

        return("First won " + Integer.toString(numFirstWins) + ".  Last won " + Integer.toString(numLastWins));
    }

    public static Strategy runTest() throws Exception {
        Game game = new Game();
        game.start();
        try{
            return game.play().getStrategy();
        }catch (Exception e){
            throw e;
        }

    }

}
