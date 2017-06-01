package parcheesi.game.gui.panel;

import parcheesi.game.board.Nest;
import parcheesi.game.enums.Color;
import parcheesi.game.gui.DrawingUtil;
import parcheesi.game.player.Pawn;

import javax.swing.*;
import java.awt.*;

/**
 * Created by devondapuzzo on 5/18/17.
 */
public class NestPanel extends JPanel{
    private Nest nest;
    private Pawn selectedPawn;
    private Color selectedColor;

    public NestPanel(Nest nest, Pawn selectedPawn){
        super();
        this.nest = nest;
        this.setSize(DrawingUtil.getCellSize(), DrawingUtil.getCellSize());
        this.selectedPawn = selectedPawn;

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        DrawingUtil.drawCenteredCircle(g, this.getWidth()/2, this.getHeight()/2, Math.min(this.getWidth(), this.getHeight()), nest.getColor().getSystemColor());
        for(Pawn pawn: nest.getPawns()){
            drawPawn(g, pawn);
        }
    }

    private void drawPawn(Graphics g, Pawn pawn){
        int startX = this.getWidth()/2 - DrawingUtil.getPawnSize();
        int startY = this.getHeight()/2 - DrawingUtil.getPawnSize();
        int xPos = startX + (pawn.getId()/2) * DrawingUtil.getPawnSize()*2;
        int yPos = startY + (pawn.getId()%2) * DrawingUtil.getPawnSize()*2;

        if(pawn.equals(this.selectedPawn)){
            DrawingUtil.drawPawn(g, xPos, yPos, pawn.getId(), pawn.getColor(), true);
        }else{
            DrawingUtil.drawPawn(g, xPos, yPos, pawn.getId(), pawn.getColor(), false);
        }
    }


    public void updateNest(Nest nest , Pawn selectedPawn) {
        this.selectedPawn = selectedPawn;
        this.nest = nest;
    }
}
