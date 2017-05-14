package parcheesi.game.parser;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.exception.BadMoveException;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by devondapuzzo on 5/11/17.
 */
public class XMLDecoder {
    public Board decodeBoard(String XML, ArrayList<Player> players) throws Exception {
        Board board = new Board();
        XML = XML.replaceAll("\\s+","");

        Document document = loadXMLFromString(XML);
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        setup(board, root, players, XMLConstants.NEST,(Element e) -> insertPawnIntoNest(board, e, players));
        setup(board, root, players, XMLConstants.HOME,(Element e) -> insertPawnIntoHome(board, e, players));
        setup(board, root, players, XMLConstants.MAIN,(Element e) -> insertPawnOntoBoard(board, e, players, XMLConstants.MAIN));
        setup(board, root, players, XMLConstants.HOME_ROW,(Element e) -> insertPawnOntoBoard(board, e, players, XMLConstants.HOME_ROW));
        return board;
    }

    private boolean insertPawnIntoNest(Board board, Element pawnElement, ArrayList<Player> players){
        Pawn pawn;
        try {
            pawn = findPawnFromElement(pawnElement, players);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return board.getNests().get(pawn.getColor()).addPawn(pawn);
    }

    private boolean insertPawnIntoHome(Board board, Element pawnElement, ArrayList<Player> players){
        Pawn pawn;
        try {
            pawn = findPawnFromElement(pawnElement, players);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return board.getHome().addPawn(pawn);
    }

    private boolean insertPawnOntoBoard(Board board, Element pieceLoc, ArrayList<Player> players, XMLConstant constant){
        Pawn pawn;
        try {
            placePawnUsingPieceLocElement(pieceLoc, board, players, constant);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void setup(Board board, Element root, ArrayList<Player> players, XMLConstant target, Predicate<Element> callback) throws PawnNotFoundException {
        String elementId = target.s();
        Element pawnElement = (Element) root.getElementsByTagName(elementId).item(0).getFirstChild();

        while (pawnElement != null) {
            boolean success = callback.test(pawnElement);
            if(!success){
                throw new PawnNotFoundException();
            }
            pawnElement = (Element) pawnElement.getNextSibling();
        }
    }


    private void placePawnUsingPieceLocElement(Element element, Board board, ArrayList<Player> players, XMLConstant region) throws PawnNotFoundException, BadMoveException {
        Element pawnElement = (Element) element.getElementsByTagName(XMLConstants.PAWN.s()).item(0);
        Element spaceElement = (Element) element.getElementsByTagName(XMLConstants.LOC.s()).item(0);
        Pawn pawn = findPawnFromElement(pawnElement, players);

        Space space;

        int spaceId = Integer.parseInt(spaceElement.getFirstChild().getTextContent());

        if(region.s().equals(XMLConstants.HOME_ROW.s())){
            space = board.getHomeRows().get(pawn.getColor()).get(spaceId);
        }else{
            spaceId = XMLConstants.convertIdFromXML(spaceId, board);
            space = board.getSpaceAt(spaceId);
        }

        MoveResult mr = space.addOccupant(pawn);

        if(mr != MoveResult.SUCCESS){
            throw new BadMoveException(mr);
        };
    }


    public Pawn findPawnFromElement(Element pawnElement, ArrayList<Player> players) throws PawnNotFoundException {
        Color color = Color.valueOf(pawnElement.getElementsByTagName("color").item(0).getTextContent().toUpperCase());
        Integer id = Integer.parseInt(pawnElement.getElementsByTagName("id").item(0).getTextContent());
        return findPawn(color, id, players);

    }

    public Pawn findPawn(Color pcolor, int id, ArrayList<Player> players) throws PawnNotFoundException{
        if(pcolor == Color.HOME || id <0){
            throw new PawnNotFoundException();
        }

        for(Player player: players){
            if(player.getColor() == pcolor){
                return player.getPawns()[id];
            }
        }

        throw new PawnNotFoundException();
    }

    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<?xml version=\"1.0\"?>" + xml);

        ByteArrayInputStream input =  new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));

        return builder.parse(input);
    }
}
