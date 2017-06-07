package parcheesi.game.player.machine.heuristic;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.player.Player;

/**
 * Created by devondapuzzo on 6/5/17.
 */
public class OtherPawnsAtNestHeuristic implements Heuristic {
    Integer weight;

    public OtherPawnsAtNestHeuristic(Integer p0) {
        weight = p0;
    }

    @Override
    public Integer evaluate(Board board, Player myPlayer) {
        int num = 0;
        for(Color color: board.getColors()){
            if(color != myPlayer.getColor()){
                Nest nest =  board.getNests().get(color);
                num+= nest.getSize();
            }
        }
        return  num * weight;
    }
}
