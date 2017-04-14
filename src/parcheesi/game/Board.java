package parcheesi.game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class Board {



    private Vector<Space> spaces= new Vector<Space>();
    private Home home = new Home();
    private Color colors[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};



    private HashMap<Color, Vector<Space>> homeRows = new HashMap<Color, Vector<Space>>();
    private HashMap<Color, Nest> nests = new HashMap<Color, Nest>();
    private int regionLength = 17;
    private int homeRowLength = 6;
    private int boardLength;
    private int safeSpace = 3;
    private int homeRowEntrance = 8;
    private int nestExit = 13;



    public Board(){
        //
        for(int i = 0; i < colors.length; i ++){
            for(int j = 0; j < regionLength; j++){

                if(j == safeSpace || j==homeRowEntrance || j==nestExit){
                    SpaceSafe space = new SpaceSafe(colors[i], boardLength);
                    spaces.add(space);
                }else{
                    SpaceRegular space =  new SpaceRegular(colors[i], boardLength);
                    spaces.add(space);
                }

                boardLength ++;
            }

            nests.put(colors[i], new Nest(colors[i]));
            homeRows.put(colors[i], new Vector<Space>());

            for(int j = 0; j < homeRowLength; j ++){
                homeRows.get(colors[i]).add(new SpaceHomeRow(Color.HOME, j));
            }
        }
    }

    public Space findPawn(Pawn pawn){
        Vector<Space> homeRow = homeRows.get(pawn.getColor());

        for(Space space : homeRow){
            if(space.hasOccupant(pawn)){
                return space;
            };
        }

        for(Space space: spaces){
            if(space.hasOccupant(pawn)){
                return space;
            }
        }

        return null;
    }

    public Space findMostAdvancedPawn(Color color){
        throw new NotImplementedException();
    }

    public int getBoardLength() {
        return boardLength;
    }
    public Vector<Space> getSpaces() {
        return spaces;
    }
    public int getHomeRowEntrance() {
        return homeRowEntrance;
    }
    public int getNestExit() {
        return nestExit;
    }
    public Color[] getColors() {
        return colors;
    }
    public Home getHome(){return home;}
    public HashMap<Color, Nest> getNests() {
        return nests;
    }
    public Space getSpaceAt(int spaceIndex){
        return spaces.get(spaceIndex);
    }
    public HashMap<Color, Vector<Space>> getHomeRows() {
        return homeRows;
    }





}
