package parcheesi.game.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by devondapuzzo on 5/16/17.
 */
public class ServerPlayer implements Runnable {


    private  ServerSocket socket;
    private  Socket clientSocket= null;
    private  PrintWriter out = null;
    private  BufferedReader in = null;
    private final int port;

    public ServerPlayer(int port){
       this.port = port;
    }

    @Override
    public void run() {
        try{
            socket = new ServerSocket(port);
            clientSocket = socket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }
}
