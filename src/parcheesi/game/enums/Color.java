package parcheesi.game.enums;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public enum Color {
    RED(0),
    GREEN(1),
    BLUE(2),
    YELLOW(3),
    HOME(4);


    private final int value;

    Color(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }



}
