package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.Strategy;
import parcheesi.game.moves.Move;
import parcheesi.game.parser.XMLEncoder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class PlayerNetwork extends PlayerAbstract {


    Socket mySocket;


    @Override
    public String startGame(Color color) {
        int portNumber = color.getValue()+8000;
        try {
            mySocket =  new Socket("localhost",  color.getValue()+8000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public ArrayList<Move> doMove(Board brd, List<Integer> dice) throws Exception {
        XMLEncoder xmlEncoder = new XMLEncoder();
        OutputStream outstream = mySocket.getOutputStream();
        PrintWriter out = new PrintWriter(outstream);


        //create message to send to parcheesi.game.player
        String boardXML = xmlEncoder.encodeBoard(brd);

        out.write(boardXML);
        //TODO FINISH

        //wait for response

        //translate response to move objects
        //send back

        return null;
    }


    @Override
    public Strategy getStrategy() {
        return null;
    }
}
