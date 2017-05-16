package parcheesi.game.parser;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Pawn;

import java.util.*;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class XMLEncoder {

    public static String encodeBoard(Board board){
        return XMLConstants.TITLE.element(
                encodeNests(board) +
                        encodeMainRow(board)+
                        encodeHomeRows(board)+
                        encodeHome(board)
        );
    }

    public static String encodeNests(Board board){
        StringBuilder XML = new StringBuilder(XMLConstants.START.start());

        HashMap<Color, Nest> nests = board.getNests();
        for(Color color: board.getColors()){
            Set<Pawn> pawns = nests.get(color).getPawns();
            for(Pawn pawn: pawns){
                XML.append(encodePawn(pawn));
            }
        }

        XML.append(XMLConstants.START.end());
        return XML.toString();
    }

    public static String encodeMainRow(Board board){
        StringBuilder XML = new StringBuilder(XMLConstants.MAIN.start());

        Vector<Space> spaces = board.getSpaces();

        for(Space space: spaces){
            XML.append(encodeSpace(space, board));
        }

        XML.append(XMLConstants.MAIN.end());
        return XML.toString();
    }

    public static String
    encodeHomeRows(Board board){
        StringBuilder XML = new StringBuilder(XMLConstants.HOME_ROW.start());

        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        for(Color color: board.getColors()){
            Vector<Space> thisHomeRow = homeRows.get(color);

            for(Space space: thisHomeRow){
                XML.append(encodeSpace(space, board));
            }
        }

        XML.append(XMLConstants.HOME_ROW.end());
        return XML.toString();
    }


    public static String encodeHome(Board board){
        StringBuilder XML = new StringBuilder(XMLConstants.HOME.start());

        Home home = board.getHome();
        for(Pawn pawn: home.getPawns()){
            XML.append(encodePawn(pawn));
        }


        XML.append(XMLConstants.HOME.end());
        return XML.toString();
    };

    public static String encodePawn(Pawn pawn){
        return XMLConstants.PAWN.element(
                XMLConstants.COLOR.element(pawn.getColor().toString().toLowerCase()) +
                        XMLConstants.ID.element(pawn.getId()));
    }

    public static String encodeSpace
            (Space space, Board board){
        String XML = "";

        int SpaceId;

        if(space.getRegion() == Color.HOME){
            SpaceId = space.getId();
        }else{
            SpaceId = XMLConstants.convertIdToXML(space.getId(), board);
        }


        if(space.getOccupant1()!=null){
            XML += XMLConstants.PIECE_LOC.element(
                    encodePawn(space.getOccupant1())+
                            XMLConstants.LOC.element(SpaceId));
        }

        if(space.getOccupant2()!=null){
            XML +=  XMLConstants.PIECE_LOC.element(
                    encodePawn(space.getOccupant1())+
                            XMLConstants.LOC.element(SpaceId));
        }

        return XML;
    }

    public static String encodeMoves(ArrayList<Move> moves) {
        String moveString = "";

        for(Move move: moves){
            moveString += move.getXMLString();
        }

        return XMLConstants.MOVES.element(moveString);
    }


    public static String encodeStartGame(Color color) {
        return XMLConstants.START_GAME.element(
                XMLConstants.COLOR.element(
                        color.toString()
                )
        );
    }

    public static String encodeDoMove(Board brd, List<Integer> dice) {
        return XMLConstants.DO_MOVE.element(
               encodeBoard(brd) + encodeDice(dice)
        );
    }

    public static String encodeDice(List<Integer> dice){
        String diceString = "";

        for(Integer die: dice){
            diceString += XMLConstants.DIE.element(die);
        }

        return XMLConstants.DICE.element(diceString);
    }
}
