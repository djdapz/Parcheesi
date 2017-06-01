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
public class BoardPanel extends JPanel {

    private HashMap<Color, NestPanel> nests = new HashMap<>();
    private HomePanel homePanel;
    private HashMap<String, SpacePanel> spacePanels = new HashMap<>();
    private HashMap<Color, SpacesPanel> spacesPanels = new HashMap<>();
    private Pawn selectedPawn;

    public BoardPanel(Board board, Pawn selectedPawn){
        super();
        int bSize = DrawingUtil.getBoardSize();
        this.setSize(bSize,bSize);
        this.setLayout(new GridBagLayout());


        homePanel = new HomePanel(board.getHome(), selectedPawn);

        for(Color color: board.getColors()){
            nests.put(color, new NestPanel(board.getNests().get(color), selectedPawn));

            SpacesPanel tempSpacesPanel = new SpacesPanel(board, color, selectedPawn);
            spacesPanels.put(color, tempSpacesPanel);

            HashMap<String, SpacePanel> tempSpacePanels = tempSpacesPanel.getSpacePanels();
            for(String spKey: tempSpacePanels.keySet()){
                spacePanels.put(spKey, tempSpacePanels.get(spKey));
            }
        }


        //top left (Blue Nest)
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        c.ipady = DrawingUtil.getCellSize()* 25/20;
        c.ipadx = DrawingUtil.getCellSize();
        c.gridx = 0;
        c.gridy = 0;
        this.add(nests.get(Color.BLUE), c);

        //top middle(Blue Region)
        c.ipady = DrawingUtil.getCellSize()* 25/20;
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 1;
        c.gridy = 0;
        this.add(spacesPanels.get(Color.BLUE), c);

        //top right (Red Nest)
        c.ipady = DrawingUtil.getCellSize()* 25/20;
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 2;
        c.gridy = 0;
        this.add(nests.get(Color.RED), c);

        //Middle left(Yellow Region)
        c.ipady = DrawingUtil.getCellSize();
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 0;
        c.gridy = 1;
        this.add(spacesPanels.get(Color.YELLOW), c);
        //Middle Middle(home()
        c.ipady = DrawingUtil.getCellSize();
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 1;
        c.gridy = 1;
        this.add(homePanel, c);

        //Middle right(Red Region)
        c.ipady = DrawingUtil.getCellSize();
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 2;
        c.gridy = 1;
        this.add(spacesPanels.get(Color.RED), c);

        //bottom left(Yellow nest)
        c.ipady = DrawingUtil.getCellSize()* 25/20;
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 0;
        c.gridy = 2;
        this.add(nests.get(Color.YELLOW), c);

        //bottom center(green region)
        c.ipady = DrawingUtil.getCellSize()* 25/20;
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 1;
        c.gridy = 2;
        this.add(spacesPanels.get(Color.GREEN), c);

        //bottom right - green nest
        c.ipady = DrawingUtil.getCellSize()* 25/20;
        c.ipadx = DrawingUtil.getCellSize();;
        c.gridx = 2;
        c.gridy = 2;
        this.add(nests.get(Color.GREEN), c);
    }

    public void updateBoard(Board board, Pawn selectedPawn){
        this.selectedPawn = selectedPawn;
        for(Color color: board.getColors()){
            nests.get(color).updateNest(board.getNests().get(color), selectedPawn);
        }

        for(Space space: board.getSpaces()){
            spacePanels.get(space.getSerializedRepresentation()).updateSpace(space, selectedPawn);
        }

        for(Color color: board.getColors()){
            for(Space space: board.getHomeRows().get(color)){
                spacePanels.get(space.getSerializedRepresentation()).updateSpace(space, selectedPawn);
            }
        }

        homePanel.updateHome(board.getHome(), selectedPawn);
    }


}
