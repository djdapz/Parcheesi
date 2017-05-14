package parcheesi.game.parser;

/**
 * Created by devondapuzzo on 5/12/17.
 */
public class XMLConstant {
    private final String constant;

    public XMLConstant(String constant) {
        this.constant = constant;
    }

    public String start(){
        return "<" + constant + ">";
    }

    public String end(){
        return "</" + constant + ">";
    }

    public String s(){
        return constant;
    }

    public String element(String inner){
        return start() + inner + end();
    }

    public String element(Integer inner){
        return start() + Integer.toString(inner) + end();
    }
}
