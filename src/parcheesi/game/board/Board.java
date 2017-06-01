package parcheesi.game.board;

import parcheesi.game.enums.Color;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;
import parcheesi.game.player.machine.PlayerMachineFirst;

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


    //Board Configuration
    private Color colors[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
    private int regionLength = 17;
    private int homeRowLength = 7;
    private int boardLength;
    private int safeSpace = 3;
    private int homeRowEntrance = 8;
    private int nestExit = 13;

    public Board(){
        //
        spaces = new Vector<>();
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
                homeRows.get(colors[i]).add(new SpaceHomeRow(colors[i], j));
            }
        }
    }

    //Copy Constructor
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
        Vector<Space> newSpaces = new Vector<>();

        for(Space space: oldSpaces){
            newSpaces.add(space.copy());
        }

        return newSpaces;
    }

    //Player Management
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

    public void kickOut(Player player) throws PawnNotFoundException {
        for(Pawn pawn: player.getPawns()){
            if(findPawn(pawn) != null){
                findPawn(pawn).removeOccupant(pawn);
            }else if(this.isAtNest(pawn)){
                nests.get(player.getColor()).removePawn(pawn);
            }else if(home.isPawnHome(pawn)){
                home.removePawn(pawn);
            }else{
                throw new PawnNotFoundException();
            }
        }
    }

    public List<Player> createRepresentationOfOtherPlayers(Player myPlayer){
        List<Player> playersArrayList = new ArrayList<>();
        Player tempPlayer;

        for(Color color1: this.getColors()){
            if(color1 == myPlayer.getColor()){
                tempPlayer = myPlayer;
            } else{
                tempPlayer = new PlayerMachineFirst();
                tempPlayer.setColor(color1);
            }

            playersArrayList.add(tempPlayer);
        }
        return playersArrayList;
    }


    //Pawn Information Methods
    public boolean allPawnsInPlay(Pawn[] pawns) {
        for(Pawn pawn: pawns){
            if(this.isAtNest(pawn)){
                return false;
            }
        }

        return true;
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

    public Integer distanceFromHome(Pawn pawn) {
        if(isHome(pawn)){
            return 0;
        }

        if(isAtNest(pawn)){
            return homeRowLength + boardLength - (nestExit - homeRowEntrance);
        }

        Space space = findPawn(pawn);

        if(space.isHomeRow()){
            return homeRowLength - space.getId();
        }

        int distanceToHomeRowEnterance = homeRowEntrance;

        if(homeRowEntrance < space.getId()){
            distanceToHomeRowEnterance += boardLength;
        }

        return distanceToHomeRowEnterance - space.getId() + homeRowLength;
    }


    //General Getters
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

    public boolean isAtNest(Pawn pawn){
        return this.nests.get(pawn.getColor()).isAtNest(pawn);
    }

    public boolean isHome(Pawn pawn){
        return this.home.isPawnHome(pawn);
    }


}
