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
public class XMLBuilder {

    public String BoardToXML(Board board){

        String XML = "<parcheesi.game.board>";

        XML += NestsToXML(board);

        XML += MainToXML(board);

        XML += HomeRowsToXML(board);

        XML += HomeToXML(board);

        XML += "</parcheesi.game.board>";

        return XML;
    }

    public String NestsToXML(Board board){
        StringBuilder XML = new StringBuilder("<start>");

        HashMap<Color, Nest> nests = board.getNests();
        for(Color color: board.getColors()){
            Set<Pawn> pawns = nests.get(color).getPawns();
            for(Pawn pawn: pawns){
                XML.append(PawnToXML(pawn));
            }
        }

        XML.append("</start>");
        return XML.toString();
    }

    public String MainToXML(Board board){
        StringBuilder XML = new StringBuilder("<main>");

        Vector<Space> spaces = board.getSpaces();

        for(Space space: spaces){
            XML.append(SpaceToXML(space, board));
        }

        XML.append("</main>");
        return XML.toString();
    }

    public String HomeRowsToXML(Board board){
        StringBuilder XML = new StringBuilder("<home-rows>");

        HashMap<Color, Vector<Space>> homeRows = board.getHomeRows();
        for(Color color: board.getColors()){
            Vector<Space> thisHomeRow = homeRows.get(color);
            for(Space space: thisHomeRow){
                XML.append(SpaceToXML(space, board));
            }
        }

        XML.append("</home-rows>");
        return XML.toString();
    }


    public String HomeToXML(Board board){
        StringBuilder XML = new StringBuilder("<home>");

        Home home = board.getHome();
        for(Pawn pawn: home.getPawns()){
            XML.append(PawnToXML(pawn));
        }


        XML.append("</home>");
        return XML.toString();
    };

    public String PawnToXML(Pawn pawn){
        String XML= "<pawn>";
        XML += "<color>" + pawn.getColor().toString().toLowerCase() + "</color>";
        XML += "<id>" + pawn.getId() + "</id>";
        XML += "</pawn>";

        return XML;
    }

    public String SpaceToXML(Space space, Board board){
        String XML = "";

        int SpaceId;

        if(space.getRegion() == Color.HOME){
            SpaceId = space.getId();
        }else{
            SpaceId = ((space.getId() + 5) % board.getBoardLength());
        }


        if(space.getOccupant1()!=null){
            XML += "<piece-loc>";
            XML += PawnToXML(space.getOccupant1());
            XML += "<loc>" + SpaceId + "</loc>";
            XML += "</piece-loc>";
        }

        if(space.getOccupant2()!=null){
            XML += "<piece-loc>";
            XML += PawnToXML(space.getOccupant2());
            XML += "<loc>" + SpaceId + "</loc>";
            XML += "</piece-loc>";
        }

        return XML;
    }
}
