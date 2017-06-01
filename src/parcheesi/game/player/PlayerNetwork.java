package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.moves.Move;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.parser.XMLEncoder;
import parcheesi.game.util.PortNumberGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private Integer portNumber = PortNumberGenerator.getPortNumber();

    private ServerPlayer serverPlayer;

    public PlayerNetwork() {
        serverPlayer = new ServerPlayer(portNumber);
        new Thread(serverPlayer).start();
    }

    public PlayerNetwork(int portNumber) {
        serverPlayer = new ServerPlayer(portNumber);
        this.portNumber = portNumber;
        new Thread(serverPlayer).start();
    }


    @Override
    public String startGame(Color color) {

        String outputLine = XMLEncoder.encodeStartGame(color);
        super.startGame(color);

        serverPlayer.getOut().println(outputLine);

        try {
            String clientResponse = serverPlayer.getIn().readLine();
            return XMLDecoder.decodeStartGameResponse(clientResponse);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    @Override
    public ArrayList<Move> doMove(Board brd, ArrayList<Integer> dice) throws Exception {

        String totalInput = "";
        String encodedDoMove = XMLEncoder.encodeDoMove(brd, dice);

        serverPlayer.getOut().println(encodedDoMove);

        try {
            String clientResponse = serverPlayer.getIn().readLine();
            ArrayList<Move> moves = XMLDecoder.decodeDoMoveResponse(clientResponse, this);
            return moves;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;

    }

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException {
        throw new NotImplementedException();
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    @Override
    public void doublesPenalty() {
        String encodedDoublesPenalty = XMLEncoder.encodeDoublesPenalty();

        serverPlayer.getOut().println(encodedDoublesPenalty);
        try {
            String clientResponse = serverPlayer.getIn().readLine();
            assert(XMLDecoder.ensureDoublesPenaltyIsVoid(clientResponse));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void incrementKickedOuts() {

    }
}
