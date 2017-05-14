package parcheesi.game.parser;

import parcheesi.game.board.Board;
import parcheesi.game.board.Home;
import parcheesi.game.board.Nest;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.player.Pawn;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

/**
 * Created by devondapuzzo on 5/9/17.
 */
public class XMLEncoder {

    private XMLConstants c = new XMLConstants();

    public String encodeBoard(Board board){
        return XMLConstants.TITLE.element(
                encodeNests(board) +
                        encodeMainRow(board)+
                        encodeHomeRows(board)+
                        encodeHome(board)
        );
    }

    public String encodeNests(Board board){
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

    public String encodeMainRow(Board board){
        StringBuilder XML = new StringBuilder(XMLConstants.MAIN.start());

        Vector<Space> spaces = board.getSpaces();

        for(Space space: spaces){
            XML.append(encodeSpace(space, board));
        }

        XML.append(XMLConstants.MAIN.end());
        return XML.toString();
    }

    public String
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


    public String encodeHome(Board board){
        StringBuilder XML = new StringBuilder(XMLConstants.HOME.start());

        Home home = board.getHome();
        for(Pawn pawn: home.getPawns()){
            XML.append(encodePawn(pawn));
        }


        XML.append(XMLConstants.HOME.end());
        return XML.toString();
    };

    public String encodePawn(Pawn pawn){
        return XMLConstants.PAWN.element(
                XMLConstants.COLOR.element(pawn.getColor().toString().toLowerCase()) +
                        XMLConstants.ID.element(pawn.getId()));
    }

    public String encodeSpace
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
}
