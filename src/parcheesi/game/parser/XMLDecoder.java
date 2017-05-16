package parcheesi.game.parser;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.enums.MoveResult;
import parcheesi.game.enums.PlayerAction;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.NoMoveFoundException;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.moves.EnterPiece;
import parcheesi.game.moves.Move;
import parcheesi.game.moves.MoveHome;
import parcheesi.game.moves.MoveMain;
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

    public static PlayerAction getAction(String XML) throws Exception {
        Element root = loadXMLFromString(XML);

        String tagName = root.getTagName();

        if(tagName.equals(XMLConstants.DO_MOVE.s())){
            return PlayerAction.DO_MOVE;
        }

        if(tagName.equals(XMLConstants.DOUBLES_PENALTY.s())){
            return PlayerAction.DOUBLES_PENALTY;
        }

        if(tagName.equals(XMLConstants.START_GAME.s())){
            return PlayerAction.START_GAME;
        }

        throw new NoMoveFoundException();
    }

    public static Color decodeStartGame(String XML) throws Exception{
        Element root = loadXMLFromString(XML);

        String colorText =root.getFirstChild().getFirstChild().getTextContent().toUpperCase();
        return Color.valueOf(colorText);
    }

    public static ArrayList<Integer> getDiceFromDoMove(String XML)  throws Exception{
        Element root = loadXMLFromString(XML);

        Element diceNode = (Element) root.getElementsByTagName("dice").item(0);

        return decodeDiceFromNode(diceNode);
    }

    private static ArrayList<Integer> decodeDiceFromNode(Element diceNode) throws PawnNotFoundException {
        ArrayList<Integer> dice = new ArrayList<>();
        setupDice(diceNode, dice);
        return dice;
    }


    public static Board getBoardFromDoMove(String XML, ArrayList<Player> players) throws  Exception{
        Element root = loadXMLFromString(XML);

        Element boardNode = (Element) root.getElementsByTagName("board").item(0);

        return decodeBoardFromNode(boardNode, players);
    }




    public static Board decodeBoardFromString(String XML, ArrayList<Player> players) throws Exception {
        Element root = loadXMLFromString(XML);
        return decodeBoardFromNode(root, players);
    }

    public static Board decodeBoardFromNode(Element root, ArrayList<Player> players) throws Exception {
        Board board = new Board();
        setup(root, XMLConstants.NEST,(Element e) -> insertPawnIntoNest(board, e, players));
        setup(root, XMLConstants.HOME,(Element e) -> insertPawnIntoHome(board, e, players));
        setup(root, XMLConstants.MAIN,(Element e) -> insertPawnOntoBoard(board, e, players, XMLConstants.MAIN));
        setup(root, XMLConstants.HOME_ROW,(Element e) -> insertPawnOntoBoard(board, e, players, XMLConstants.HOME_ROW));
        return board;
    }

    private static boolean insertPawnIntoNest(Board board, Element pawnElement, ArrayList<Player> players){
        Pawn pawn;
        try {
            pawn = findPawnFromElement(pawnElement, players);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return board.getNests().get(pawn.getColor()).addPawn(pawn);
    }

    private static boolean insertPawnIntoHome(Board board, Element pawnElement, ArrayList<Player> players){
        Pawn pawn;
        try {
            pawn = findPawnFromElement(pawnElement, players);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return board.getHome().addPawn(pawn);
    }

    private static boolean insertPawnOntoBoard(Board board, Element pieceLoc, ArrayList<Player> players, XMLConstant constant){
        Pawn pawn;
        try {
            placePawnUsingPieceLocElement(pieceLoc, board, players, constant);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    private static void setup(Element root, XMLConstant target, Predicate<Element> callback) throws PawnNotFoundException {
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

    private static void setupDice(Element root, ArrayList<Integer> dice){
        Element dieElement =  (Element) root.getFirstChild();

        while (dieElement != null) {

            dice.add(Integer.parseInt(dieElement.getFirstChild().getTextContent()));
            dieElement = (Element) dieElement.getNextSibling();
        }
    }


    private static void placePawnUsingPieceLocElement(Element element, Board board, ArrayList<Player> players, XMLConstant region) throws PawnNotFoundException, InvalidMoveException {
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
            throw new InvalidMoveException(mr);
        };
    }


    public static Pawn findPawnFromElement(Element pawnElement, ArrayList<Player> players) throws PawnNotFoundException {
        Color color = Color.valueOf(pawnElement.getElementsByTagName("color").item(0).getTextContent().toUpperCase());
        Integer id = Integer.parseInt(pawnElement.getElementsByTagName("id").item(0).getTextContent());
        return findPawn(color, id, players);
    }

    public static Pawn findPawnFromElement(Element pawnElement, Player player) throws PawnNotFoundException {
        ArrayList<Player> players= new ArrayList<>();
        players.add(player);
        return findPawnFromElement(pawnElement, players);
    }

    public static Pawn findPawn(Color pcolor, int id, ArrayList<Player> players) throws PawnNotFoundException{
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


    public static Element loadXMLFromString(String xml) throws Exception
    {
        xml = xml.replaceAll("\\s+","");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<?xml version=\"1.0\"?>" + xml);

        ByteArrayInputStream input =  new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));

        Document document = builder.parse(input);
        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        return root;
    }


    public static String decodeStartGameResponse(String inputLine) throws Exception {
        Element root = loadXMLFromString(inputLine);
        assert(root.getTagName() == XMLConstants.NAME.s());
        return root.getFirstChild().getTextContent();
    }

    public static ArrayList<Move> decodeDoMoveResponse(String XML, Player player) {
        Element root = null;
        try {
            root = loadXMLFromString(XML);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Element moveElement = (Element) root.getFirstChild();
        ArrayList<Move> moves= new ArrayList<>();

        while (moveElement != null) {
            try {
                moves.add(moveElementToMove(moveElement, player));
            } catch(Exception e){
                e.printStackTrace();
                System.exit(-1);
            }
            moveElement = (Element) moveElement.getNextSibling();
        }

        return moves;
    }

    private static Move moveElementToMove(Element moveElement, Player player) throws PawnNotFoundException, NoMoveFoundException {
        String tagName = moveElement.getTagName();


        Pawn pawn = findPawnFromElement((Element) moveElement.getElementsByTagName(XMLConstants.PAWN.s()).item(0), player);


        if (tagName.equals(XMLConstants.ENTER_PIECE.s())) {
            return new EnterPiece(pawn);
        }

        Integer distance = Integer.parseInt(moveElement.getElementsByTagName(XMLConstants.START.s()).item(0).getTextContent());
        Integer start = Integer.parseInt(moveElement.getElementsByTagName(XMLConstants.DISTANCE.s()).item(0).getTextContent());

        if (tagName.equals(XMLConstants.MOVE_HOME.s())) {
            return new MoveHome(pawn, start, distance);
        } else if (tagName.equals(XMLConstants.MOVE_MAIN.s())) {
            return new MoveMain(pawn, start, distance);
        }

        throw new NoMoveFoundException();
    }
}
