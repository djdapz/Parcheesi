package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.enums.Strategy;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.moves.Move;
import parcheesi.game.parser.XMLBuilder;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class PlayerNetwork extends Player {

    @Override
    public ArrayList<Move> doMove(Board brd, List<Integer> dice) throws Exception {
        XMLBuilder xmlBuilder = new XMLBuilder();
        Socket socket  = new Socket("localhost", 800);
        OutputStream outstream = socket.getOutputStream();
        PrintWriter out = new PrintWriter(outstream);


        //create message to send to parcheesi.game.player
        String boardXML = xmlBuilder.BoardToXML(brd);

        out.write(boardXML);
        //TODO FINISH

        //wait for response

        //translate response to move objects
        //send back

        return null;
    }

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice) throws InvalidMoveException {
        return null;
    }

    @Override
    public Strategy getStrategy() {
        return null;
    }
}
