package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.PlayerAction;
import parcheesi.game.moves.Move;
import parcheesi.game.parser.XMLConstants;
import parcheesi.game.parser.XMLDecoder;
import parcheesi.game.parser.XMLEncoder;
import parcheesi.game.player.machine.PlayerMachineFirst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by devondapuzzo on 5/15/17.
 */
public class ClientPlayer implements Runnable{

    private final Integer portNumber;
    private Socket client;
    private Player player;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<Player> playersArrayList = new ArrayList<>();
    private String hostName = "localhost";


    public ClientPlayer(Integer port, Player player) {
        this.portNumber = port;
        this.player = player;
        try {
            this.client = new Socket(hostName, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        String line;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("in or out failed");
            System.exit(-1);
        }


        while(true){
            try{
                line = in.readLine();//Send data back to client
                PlayerAction pa = XMLDecoder.getAction(line);
                System.out.println("Server: " + line);
                if(pa == PlayerAction.START_GAME){
                    line = startGame(line);
                }else if(pa == PlayerAction.DO_MOVE){
                    line = doMove(line);
                }else if(pa == PlayerAction.DOUBLES_PENALTY){
                    line = doublesPenalty();
                }


                System.out.println("Myresponse: " + line);
                out.println(line); //Append data to text area
            }catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

    }

    public String startGame(String XML) throws Exception{
        Color color= XMLDecoder.decodeStartGame(XML);
        String name = player.startGame(color);
        //TODO Create representation of playersArrayList

        for(Color color1: Color.values()){
            if(color1 == Color.HOME){
                continue;
            }

            if(color1 == color){
                playersArrayList.add(player);
            }

            else{
                Player tempPlayer = new PlayerMachineFirst();
                tempPlayer.setColor(color1);
                playersArrayList.add(tempPlayer);
            }
        }

        return "<name>" + name + "</name>";
    }

    public String doMove(String XML) throws Exception{
        assert(playersArrayList != null);
        Board board = XMLDecoder.decodeBoardFromDoMove(XML, playersArrayList);
        ArrayList<Integer> dice = XMLDecoder.decodeDiceFromDoMove(XML);
        ArrayList<Move> moves = player.doMove(board, dice);
        return XMLEncoder.encodeMoves(moves);
    }

    public String doublesPenalty() throws Exception{
        player.doublesPenalty();
        return XMLConstants.VOID.element("");
    }


}
