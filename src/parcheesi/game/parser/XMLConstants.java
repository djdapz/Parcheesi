package parcheesi.game.parser;

import parcheesi.game.board.Board;

/**
 * Created by devondapuzzo on 5/12/17.
 */
public class
XMLConstants {
    public static final XMLConstant TITLE = new XMLConstant("parcheesi.game.board");
    public static final XMLConstant HOME = new XMLConstant("home");
    public static final XMLConstant MAIN = new XMLConstant("main");
    public static final XMLConstant HOME_ROW = new XMLConstant("home-rows");
    public static final XMLConstant BOARD = new XMLConstant("board");
    public static final XMLConstant NEST = new XMLConstant("start");
    public static final XMLConstant PIECE_LOC = new XMLConstant("piece-loc");
    public static final XMLConstant LOC = new XMLConstant("loc");
    public static final XMLConstant PAWN =new XMLConstant("pawn");
    public static final XMLConstant COLOR = new XMLConstant("color");
    public static final XMLConstant ID = new XMLConstant("id");
    public static final XMLConstant DICE = new XMLConstant("dice");
    public static final XMLConstant DIE = new XMLConstant("die");
    public static final XMLConstant START = new XMLConstant("start");
    public static final XMLConstant DISTANCE = new XMLConstant("distance");
    public static final XMLConstant MOVE_HOME = new XMLConstant("move-piece-home");
    public static final XMLConstant MOVE_MAIN = new XMLConstant("move-piece-main");
    public static final XMLConstant ENTER_PIECE = new XMLConstant("enter-piece");
    public static final XMLConstant START_GAME = new XMLConstant("start-game");
    public static final XMLConstant NAME = new XMLConstant("name");
    public static final XMLConstant DO_MOVE = new XMLConstant("do-move");
    public static final XMLConstant MOVES = new XMLConstant("moves");
    public static final XMLConstant DOUBLES_PENALTY = new XMLConstant("doubles-penalty");
    public static final XMLConstant VOID = new XMLConstant("void");

    public static final Integer ID_OFFSET = 9;

    public static int convertIdToXML(int id, Board board){
        return (id + ID_OFFSET) % board.getBoardLength();
    }

    public static int convertIdFromXML(int id, Board board){
        return (id - ID_OFFSET + board.getBoardLength()) % board.getBoardLength();
    }
}
