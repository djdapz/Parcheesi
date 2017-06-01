package parcheesi.game.gui;

import parcheesi.game.enums.MoveResult;
import parcheesi.game.moves.Move;

/**
 * Created by devondapuzzo on 5/23/17.
 */
public class Messages {

    public static String invalidEnter = "You need to have either a 5 or dice that sum to a 5 to exit the nest!";
    public static String diceLabel = "Step 1: Choose your die.";
    public static String welcome = "Welcome to Parcheesi!";
    public static String alreadyHome = "That pawn is already home!";
    public static String doublesPenalty = "You rolled doubles 3 times :(";
    public static String bop = "Nice Bop! You get a 20 move bonus!";
    public static String pawnLabel = "Step 2: Select your Pawn";

    public static String failedMove(MoveResult mr) {
        return "Oh no! Your move resulted in: "+ mr +"   Try again.";
    }

    public static String successfulMove(Move currentMove) {
        return "Pawn " + (currentMove.getPawn().getId() + 1) + " moved " + currentMove.getDistance() + " spaces.";
    }

    public static String enter(Move currentMove) {
        return "Pawn " +(currentMove.getPawn().getId() + 1)+ " entered";
    }
}
