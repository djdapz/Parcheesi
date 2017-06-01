package parcheesi.game.main;

import parcheesi.game.player.ClientPlayer;
import parcheesi.game.player.Player;
import parcheesi.game.player.machine.PlayerMachineCustom;

/**
 * Created by devondapuzzo on 5/25/17.
 */
public class PlayTournament {

    public static void main(String[] args) throws Exception {
        assert(args.length == 1);

        Player actualPlayer = new PlayerMachineCustom();
        actualPlayer.setName("Devon's Player");
        ClientPlayer pc = new ClientPlayer(Integer.parseInt(args[0]), actualPlayer);

        new Thread(pc).start();
    }
}
