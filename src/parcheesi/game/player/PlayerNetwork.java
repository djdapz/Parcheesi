package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.Strategy;
import parcheesi.game.moves.Move;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.parser.XMLEncoder;
import parcheesi.game.util.PortNumberGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/15/17.
 */
public class PlayerNetwork extends PlayerAbstract {

    private ServerSocket socket;
    private Socket clientSocket;
    private final Integer portNumber = PortNumberGenerator.getPortNumber();

    private PlayerServer playerServer;

    public PlayerNetwork() {
        playerServer = new PlayerServer(portNumber);
        new Thread(playerServer).start();
    }



    @Override
    public String startGame(Color color) {

        String outputLine = XMLEncoder.encodeStartGame(color);
        String totalInput = "";
        String inputLine;
        super.startGame(color);


        playerServer.getOut().println(outputLine);

        try {
            String clientResponse = playerServer.getIn().readLine();

            try {
                return XMLDecoder.decodeStartGameResponse(clientResponse);
            }catch (Exception e){
                e.printStackTrace();
                System.exit(-1);
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        return null;
    }

    @Override
    public ArrayList<Move> doMove(Board brd, List<Integer> dice) throws Exception {

        String totalInput = "";
        String inputLine;
        String outputLine = XMLEncoder.encodeDoMove(brd, dice);

        playerServer.getOut().println(outputLine);

        try {
            String clientResponse = playerServer.getIn().readLine();
            ArrayList<Move> moves = XMLDecoder.decodeDoMoveResponse(clientResponse, this);
            return moves;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        try {
            return XMLDecoder.decodeDoMoveResponse(totalInput, this);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;

    }

    @Override
    public Strategy getStrategy() {
        return null;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    @Override
    public void DoublesPenalty() {

    }

}
