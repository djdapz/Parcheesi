package parcheesi.game.gui.panel;

import parcheesi.game.board.Space;
import parcheesi.game.board.SpaceHomeRow;
import parcheesi.game.enums.Color;
import parcheesi.game.gui.DrawingUtil;
import parcheesi.game.player.Pawn;

import javax.swing.*;
import java.awt.*;

/**
 * Created by devondapuzzo on 5/19/17.
 */
public class SpacePanel extends JPanel{

    private final java.awt.Color color;
    private Space space;
    private Pawn selectedPawn;

    public SpacePanel(Space space, Color region, boolean isHomeRow, Pawn selectedPawn ){
        super();
        this.space = space;

        if(isHomeRow){
            this.color = region.getSystemColor();
        }else{
            this.color = space.getSystemColor();
        }
        this.selectedPawn = selectedPawn;
    }

    public SpacePanel(int neg){
        super();
        assert(neg == -1);
        this.space = new SpaceHomeRow(Color.HOME, 0, Color.HOME);
        this.color = java.awt.Color.white;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int offset = DrawingUtil.getRingThickness()/2;

        if(color == java.awt.Color.white){
            g.setColor(java.awt.Color.white);
        }else{
            g.setColor(java.awt.Color.black);
        }
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(color);
        g.fillRect(offset, offset,
                this.getWidth() - 2*offset,
                this.getHeight() - 2*offset);

        boolean o1selected = space.getOccupant1() != null && space.getOccupant1().equals(selectedPawn);
        boolean o2selected = space.getOccupant2() != null && space.getOccupant2().equals(selectedPawn);

        if(space.getOccupant1() != null  && space.getOccupant2() != null){
            DrawingUtil.drawPawn(g, this.getWidth()/2, this.getHeight() /4, space.getOccupant1().getId(), space.getOccupant1().getColor(), o1selected);
            DrawingUtil.drawPawn(g, this.getWidth()/2, this.getHeight()*3/4, space.getOccupant2().getId(), space.getOccupant2().getColor(), o2selected);
        } else if(space.getOccupant1() != null){
            DrawingUtil.drawPawn(g, this.getWidth()/2, this.getHeight()/2, space.getOccupant1().getId(), space.getOccupant1().getColor(), o1selected);
        } else if(space.getOccupant2() != null){
            DrawingUtil.drawPawn(g, this.getWidth()/2, this.getHeight()/2, space.getOccupant2().getId(), space.getOccupant2().getColor(), o2selected);
        }
    }

    public void updateSpace(Space space, Pawn selectedPawn) {
        this.space = space;
        this.selectedPawn =selectedPawn;
    }
}
