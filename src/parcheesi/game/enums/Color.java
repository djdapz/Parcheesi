package parcheesi.game.enums;

/**
 * Created by devondapuzzo on 4/10/17.
 */
public enum Color {
    RED(0, java.awt.Color.RED),
    GREEN(3, java.awt.Color.GREEN),
    BLUE(1, java.awt.Color.BLUE),
    YELLOW(2, java.awt.Color.YELLOW),
    HOME(4, java.awt.Color.WHITE);


    private final int value;
    private final java.awt.Color systemColor;

    Color(final int newValue, final java.awt.Color systemColor) {
        value = newValue;
        this.systemColor = systemColor;
    }

    public int getValue() { return value; }

    public java.awt.Color getSystemColor(){
        return systemColor;
    }




}
