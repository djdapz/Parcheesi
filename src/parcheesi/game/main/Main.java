package parcheesi.game.main;

import parcheesi.game.gameplay.Game;
import parcheesi.game.player.Player;
import parcheesi.game.player.ClientPlayer;
import parcheesi.game.player.PlayerHuman;
import parcheesi.game.player.PlayerNetwork;

public class Main {

    public static void main(String[] args) throws Exception {
        int numHumans = 0;
        Game game = new Game();

        if(args.length > 0){
            numHumans = args.length;
        }

        assert(numHumans <= 4);

        for (String name: args){
            PlayerNetwork networkedPlayer = new PlayerNetwork();

            Player actualPlayer = new PlayerHuman(name);

            ClientPlayer pc = new ClientPlayer(networkedPlayer.getPortNumber(), actualPlayer);
            new Thread(pc).start();

            game.register(networkedPlayer);
        }

        game.start();
        game.play();
        System.out.println("winner = " + game.getWinner().getColor().toString());
        System.exit(0);
    }

}
