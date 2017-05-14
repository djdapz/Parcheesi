package parcheesi.game.board;

import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;
import parcheesi.game.enums.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by devondapuzzo on 4/9/17.
 */
public class Board {



    private final Vector<Space> spaces;
    private final Home home;
    private final HashMap<Color, Vector<Space>> homeRows = new HashMap<Color, Vector<Space>>();
    private final HashMap<Color, Nest> nests = new HashMap<Color, Nest>();

    private Color colors[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

    private int regionLength = 17;
    private int homeRowLength = 6;
    private int boardLength;
    private int safeSpace = 3;
    private int homeRowEntrance = 8;
    private int nestExit = 13;


    public Board(Board oldBoard){

        spaces = copyVector(oldBoard.getSpaces());
        home = new Home(oldBoard.getHome());

        for(Color color: colors){
            homeRows.put(color, copyVector(oldBoard.getHomeRows().get(color)));
            nests.put(color, new Nest(oldBoard.getNests().get(color)));
        }

        boardLength = oldBoard.getBoardLength();
    }

    private Vector<Space> copyVector(Vector<Space> oldSpaces){
        Space newSpace;
        Vector<Space> newSpaces = new Vector<>();

        for(Space space: oldSpaces){
            if(space.isSafeSpace()){
                newSpace = new SpaceSafe(space.getRegion(), space.getId());
            }else{
                newSpace = new SpaceRegular(space.getRegion(), space.getId());
            }

            if(space.getOccupant1()!=null){
                newSpace.addOccupant(space.getOccupant1());
            }

            if(space.getOccupant2()!=null){
                newSpace.addOccupant(space.getOccupant2());
            }

            newSpaces.add(newSpace);
        }

        return newSpaces;
    }

    public Board(){
        //
        spaces = new Vector<Space>();
        home = new Home();
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
        return findPawn(pawn, null);
    }

    public Space findPawn(Pawn pawn, Space excludedSpace){
        Vector<Space> homeRow = homeRows.get(pawn.getColor());

        for(Space space : homeRow){
            if(space.hasOccupant(pawn) && space!=excludedSpace){
                return space;
            };
        }

        for(Space space: spaces){
            if(space.hasOccupant(pawn) && space!=excludedSpace){
                return space;
            }
        }

        return null;
    }

    public Space findMostAdvancedPawn(Player player){
        return findMostAdvancedPawn(player, new ArrayList<>());

    }

    public Space findMostAdvancedPawn(Player player, List<Space> exclusionList){
        Color pColor = player.getColor();
        Vector<Space> hr = homeRows.get(player.getColor());

        for(int i = hr.size()-1; i >=0; i --){
            if(exclusionList.contains(hr.get(i))){
                continue;
            }

            if(hr.get(i).getOccupant2() != null || hr.get(i).getOccupant1() != null){
                return hr.get(i);
            }
        }

        int homeEntrance = player.getPawns()[0].getHomeEntranceId();
        int i;
        Space space;

        for(int count = 0; count < getBoardLength(); count ++){

            i = homeEntrance - count;
            if(i < 0){
                i = i + getBoardLength();
            }

            space = spaces.get(i);
            if(exclusionList.contains(space)){
                continue;
            }

            if(space.getOccupant2() != null  && space.getOccupant2().getColor() == pColor){
                return space;
            }

            if(space.getOccupant1() != null  && space.getOccupant1().getColor() == pColor){
                return space;
            }
        }

        return null;

    }

    public Space findLeastAdvancedPawn(Player player){
        return findLeastAdvancedPawn(player, new ArrayList<>());
    }

    public Space findLeastAdvancedPawn(Player player, List<Space> exclusionList){
        //TODO - Consolidate with findMostAdvanced
        Color pColor = player.getColor();
        Vector<Space> hr = homeRows.get(player.getColor());

        int exitSpaceId = player.getPawns()[0].getExitSpaceId();
        int i;
        Space space;

        for(int count = 0; count < getBoardLength(); count ++){

            i = exitSpaceId + count;

            if(i >= getBoardLength()){
                i = (getBoardLength() + i)%getBoardLength();
            }

            space = spaces.get(i);
            if(exclusionList.contains(space)){
                continue;
            }

            if(space.getOccupant2() != null  && space.getOccupant2().getColor() == pColor){
                return space;
            }

            if(space.getOccupant1() != null  && space.getOccupant1().getColor() == pColor){
                return space;
            }
        }

        for(int j = 0 ; j <hr.size(); j ++){
            if(exclusionList.contains(hr.get(j))){
                continue;
            }

            if(hr.get(j).getOccupant2() != null || hr.get(j).getOccupant1() != null){
                return hr.get(j);
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
