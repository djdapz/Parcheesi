package parcheesi.game.gui.panel;

import parcheesi.game.board.Home;
import parcheesi.game.enums.Color;
import parcheesi.game.gui.DrawingUtil;
import parcheesi.game.player.Pawn;

import javax.swing.*;
import java.awt.*;

/**
 * Created by devondapuzzo on 5/19/17.
 */
public class HomePanel extends JPanel{
    private Home home;
    private Pawn selectedPawn;

    public HomePanel(Home home, Pawn selectedPawn){
        super();
        this.home = home;
        this.selectedPawn = selectedPawn;
    }



    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        DrawingUtil.setPawnSize(this.getWidth()/10);

        g.setColor(Color.HOME.getSystemColor());
        g.fillOval(0, 0, this.getWidth(), this.getHeight());

        for(Pawn pawn: home.getPawns()){
            drawPawn(g, pawn);
        }
    }

    protected void drawPawn(Graphics g, Pawn pawn){
        int idOffset = DrawingUtil.getPawnSize()*2 + pawn.getId() %2 ;
        int colorOffset = DrawingUtil.getPawnSize()*4;
        int start = (this.getWidth() - DrawingUtil.getPawnSize()*7)/2;

        int xPos = idOffset * (pawn.getId() % 2) + colorOffset *(pawn.getColor().getValue() %2) + start;
        int yPos = idOffset * (pawn.getId() / 2) + colorOffset *(pawn.getColor().getValue() /2) + start;

        if(pawn.equals(this.selectedPawn)){
            DrawingUtil.drawPawn(g, xPos, yPos, pawn.getId(), pawn.getColor(), true);
        }else{
            DrawingUtil.drawPawn(g, xPos, yPos, pawn.getId(), pawn.getColor(), false);
        }
    }

    public void updateHome(Home home, Pawn selectedPawn){
        this.selectedPawn = selectedPawn;
        this.home = home;
    }
}
