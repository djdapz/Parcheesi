package parcheesi.game.player;

import parcheesi.game.board.Board;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.gui.IListener;
import parcheesi.game.gui.Messages;
import parcheesi.game.gui.ViewHuman;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/4/17.
 *
 */
public class PlayerHuman extends PlayerAbstract {

    private ViewHuman view;
    private List<Player> players;

    public PlayerHuman(String name){
        setName(name);
    }

    @Override
    public String startGame(Color color) {
        super.startGame(color);
        Board currentBoard= getInitialBoard();
        view = new ViewHuman(this, currentBoard);
        return name;
    }

    @Override
    public void doublesPenalty() {
        view.msgDisplay(Messages.doublesPenalty);

    }

    @Override
    public void messagePlayer(String message) {
        view.msgDisplay("<html>"+message+"</html>");
    }


    @Override
    public Move doMiniMove(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList){
        view.updateBoard(brd);
        view.updateDice(dice);
        IListener l = new IListener() {
            private Move move;

            @Override
            public void submit(Pawn pawn, Integer distance) throws PawnNotFoundException, InvalidMoveException {
                Space space = brd.findPawn(pawn);
                if(space == null){
                    if(brd.isAtNest(pawn)){
                        if(distance == 5){
                            move = new EnterPiece(pawn);
                        }else{
                            messagePlayer(Messages.invalidEnter);
                            throw new InvalidMoveException();
                        }
                    }else if(brd.getHome().isPawnHome(pawn)){
                        //Pawn already home!
                        messagePlayer(Messages.alreadyHome);
                        throw new InvalidMoveException();
                    }else{
                        throw new PawnNotFoundException();
                    }
                }else{
                    move = space.createMoveFromHere(distance, pawn);
                }
            }

            @Override
            public Move getMove(){
                return move;
            }
        };

        return view.listen(l);
    }

    private Board getInitialBoard(){
        Board board = new Board();
        players = board.createRepresentationOfOtherPlayers(this);
        for(Player player: players){
            Nest nest =  board.getNests().get(player.getColor());
            for(Pawn pawn: player.getPawns()){
                nest.addPawn(pawn);
            }
        }
        return board;
    }


}
