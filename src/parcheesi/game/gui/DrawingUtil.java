package parcheesi.game.gui;

import parcheesi.game.enums.Color;

import java.awt.*;

/**
 * Created by devondapuzzo on 5/19/17.
 */
public class DrawingUtil {

    private static int pawnSize = 10;
    private static int ringThickness = 2;
    private static int boardSize = 600;

    public static void drawPawn(Graphics g, int xPos, int yPos, int id, Color primaryColor, boolean selected){

        if(selected){
            drawCenteredCircle(g, xPos, yPos, pawnSize+ringThickness*3, java.awt.Color.WHITE);
        }else{
            drawCenteredCircle(g, xPos, yPos, pawnSize+ringThickness*2, java.awt.Color.BLACK);
        }


        drawCenteredCircle(g, xPos, yPos, pawnSize, primaryColor.getSystemColor());

        g.setColor(java.awt.Color.BLACK);
        g.drawString(Integer.toString(id + 1), xPos, yPos);
    }

    public static void drawCenteredCircle(Graphics g, int x, int y, int r, java.awt.Color color) {
        x = x-(r/2);
        y = y-(r/2);
        g.setColor(color);
        g.fillOval(x,y,r,r);
    }

    public static int getPawnSize() {
        return pawnSize;
    }

    public static void setPawnSize(int pawnSize) {
        DrawingUtil.pawnSize = pawnSize;
    }

    public static int getRingThickness() {
        return ringThickness;
    }

    public static void setRingThickness(int ringThickness) {
        DrawingUtil.ringThickness = ringThickness;
    }

    public static int getCellSize() {
        return boardSize/3;
    }

    public static int getBoardSize() {
        return boardSize;
    }

    public static void setBoardSize(int boardSize) {
        DrawingUtil.boardSize = boardSize;
    }
}
