package parcheesi.game.gui.panel;

import parcheesi.game.board.Board;
import parcheesi.game.board.Space;
import parcheesi.game.enums.Color;
import parcheesi.game.gui.DrawingUtil;
import parcheesi.game.player.Pawn;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by devondapuzzo on 5/19/17.
 */
public class SpacesPanel extends JPanel{
    private Pawn selectedPawn;
    private Board board;
    private Color region;
    private HashMap<String, SpacePanel> spacePanels = new HashMap<>();


    public SpacesPanel(Board board, Color region, Pawn pawn){
        super();
        assert(region != Color.HOME);
        this.region = region;
        this.board = board;
        this.selectedPawn  = pawn;

        int cSize = DrawingUtil.getCellSize();
        this.setSize(cSize,cSize);
        this.setLayout(new GridLayout(3,8));


        //Row 1
        for(int i = 7; i >=0; i --){
            addNewSpace(i, false);
        }

        //row 2
        addNewSpace(8, false);
        for(int i = 0; i <=6; i ++){
            addNewSpace(i, true);
        }

        //row3
        for(int i = 9; i < 17; i ++){
            addNewSpace(i, false);
        }
    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        int w2 = getWidth() / 2;
        int h2 = getHeight() / 2;
        g2d.rotate((-Math.PI / 2)*((region.getValue()+2)%4) , w2, h2);
        super.paintComponent(g);
    }

    protected void addNewSpace(int index, Boolean homeRow){
        SpacePanel tempPanel;
        Space thisSpace;
        if(index == -1){
            tempPanel = new SpacePanel(-1);
            this.add(tempPanel);
            return;
        }

        if(homeRow){
            thisSpace = board.getHomeRows().get(this.region).get(index);
        }else{
            index = index + region.getValue() * 17;
            thisSpace = board.getSpaceAt(index);
        }

        tempPanel = new SpacePanel(thisSpace, region, homeRow, this.selectedPawn);

        this.add(tempPanel);
        spacePanels.put(thisSpace.getSerializedRepresentation(), tempPanel);
    }


    public HashMap<String, SpacePanel> getSpacePanels() {
        return spacePanels;
    }
}
