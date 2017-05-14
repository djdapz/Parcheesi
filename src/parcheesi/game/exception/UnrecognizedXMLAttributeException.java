package parcheesi.game.exception;

import org.w3c.dom.Node;

/**
 * Created by devondapuzzo on 5/12/17.
 */
public class UnrecognizedXMLAttributeException extends Exception {

    private Node node;

    public UnrecognizedXMLAttributeException(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
