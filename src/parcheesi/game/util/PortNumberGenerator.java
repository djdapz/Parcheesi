package parcheesi.game.util;

/**
 * Created by devondapuzzo on 5/15/17.
 */
public class PortNumberGenerator {

    private static Integer portNumber = 4321;

    public static Integer getPortNumber(){
        portNumber ++;
        return portNumber;
    }
}
