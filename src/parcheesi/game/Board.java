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

    public boolean enforceDoublesPenalty(Player player){
        Space bumpedSpace = this.findMostAdvancedPawn(player);

        if(bumpedSpace != null){
            Pawn bumpedPawn = bumpedSpace.getOccupant1();
            bumpedSpace.removeOccupant(bumpedPawn);
            this.getNests().get(bumpedPawn.getColor()).addPawn(bumpedPawn);
            return true;
        }else{
            return false;
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

    public Space findMostAdvancedPawn(Player player){

        Color pColor = player.getColor();
        Vector<Space> hr = homeRows.get(player.getColor());

        for(int i = homeRows.size()-1; i >=0; i --){
            if(hr.get(i).getOccupant2() != null || hr.get(i).getOccupant1() != null){
                return hr.get(i);
            }
        }

        int homeEnterance = player.getPawns()[0].getHomeEntrance().getId();
        int i;
        Space space;

        for(int count = 0; count < getBoardLength(); count ++){


            i = homeEnterance - count;
            if(i < 0){
                i = i + getBoardLength();
            }

            space = spaces.get(i);

            if(space.getOccupant2() != null  && space.getOccupant2().getColor() == pColor){
                return space;
            }



            if(space.getOccupant1() != null  && space.getOccupant1().getColor() == pColor){
                return space;
            }
        }

        return null;
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
