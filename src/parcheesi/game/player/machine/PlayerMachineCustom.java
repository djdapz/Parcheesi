package parcheesi.game.player.machine;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.exception.DuplicatePawnException;
import parcheesi.game.exception.GoesHomeException;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.gameplay.RulesChecker;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.machine.heuristic.Statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/27/17.
 */
public class PlayerMachineCustom extends PlayerMachine {

    private class EvaluationObject{
        private Move move;
        private Board board;
        private int score = 0;

        public EvaluationObject(Move move) {
            this.move = move;
        }

        public Move getMove() {
            return move;
        }

        public void setMove(Move move) {
            this.move = move;
        }

        public Board getBoard() {
            return board;
        }

        public void setBoard(Board board) {
            this.board = board;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    private MoveEvaluator moveEvaluator = new MoveEvaluator();

    @Override
    protected Space findOptimalSpace(Board board) {
        return null;
    }

    @Override
    protected Space findOptimalSpace(Board board, ArrayList<Space> exclusionList) {
        return null;
    }

    @Override
    public Move doMiniMove(Board brd, List<Integer> dice, ArrayList<Space> originalBlockadeList) throws NoMoveFoundException, InvalidMoveException, DuplicatePawnException {
        ArrayList<EvaluationObject> options = new ArrayList<>();

        for(Pawn pawn: pawns){
            if(brd.isAtNest(pawn)){
                if(pawn.canMove(dice, brd)){
                    options.add(new EvaluationObject(new EnterPiece(pawn)));
                }
            }else if (!brd.isHome(pawn)) {
                for(Integer die: dice){
                    Move nextMove = null;
                    try {
                        nextMove = brd.findPawn(pawn).createMoveFromHere(die, pawn);
                        nextMove.getDestinationSpace(brd);
                        options.add(new EvaluationObject(nextMove));
                    }catch (GoesHomeException e) {
                        options.add(new EvaluationObject(nextMove));
                    } catch (NullPointerException e){
                        nextMove = brd.findPawn(pawn).createMoveFromHere(die, pawn);
                        e.printStackTrace();
                        System.exit(-1);
                    } catch (InvalidMoveException ignored){
                    }
                }
            }
        }

        EvaluationObject bestOption = options.get(0);
        for(EvaluationObject option: options) {
            Board newBoard = new Board(brd);
            option.getMove().run(newBoard);
            option.setBoard(newBoard);

            if(!RulesChecker.doBlockadesMove(originalBlockadeList, newBoard, this)){
                option.setScore(moveEvaluator.score(newBoard, this));
            }else{
                option.setScore(-10000000);
            };

            if(option.getScore() > bestOption.getScore()){
                bestOption = option;
            }
        }

        return bestOption.getMove();
    }

    private static Statistics stats = new Statistics();

    @Override
    public void incrementWins() {
        stats.incrementWins();
    }

    @Override
    public void incrementKickedOuts() {
        stats.incrementKickedOuts();
    }


    public static int getWins() {
        return stats.getWins();
    }

    public static int getKickedOuts() {
        return stats.getKickedOuts();
    }
}
