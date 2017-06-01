package parcheesi.game.gui;

import parcheesi.game.board.Board;
import parcheesi.game.gui.panel.BoardPanel;
import parcheesi.game.gui.panel.ControlPanel;
import parcheesi.game.player.Pawn;
import parcheesi.game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// setting up the layout of the Machine View
public abstract class Layout {

    protected BoardPanel boardPanel;
    protected JFrame frame;


    protected ControlPanel controlPanel;
    protected JPanel mainGrid = new JPanel();
    protected Pawn selectedPawn;
    protected Integer selectedDistance;
    protected Player player;
    protected Board currentBoard;

    protected abstract ControlPanel createControlPanel();

    // ------------------------------------------------------------------

    Layout(Player player, Board board) {
        this.player = player;
        this.currentBoard = board;

        controlPanel = createControlPanel();

        frame = new JFrame("player: " + player.getName());
        frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }});
        boardPanel = new BoardPanel(board, null);
        mainGrid = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 50;
        mainGrid.add(boardPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 50;
        mainGrid.add(controlPanel, c);


        frame.setContentPane(mainGrid);
        frame.pack();
        frame.setVisible(true);
    }

    protected void updateBoard(Board board){
        boardPanel.updateBoard(board, selectedPawn);
        frame.repaint();
    }

    protected void refreshWindow(){
        frame.pack();
        frame.repaint();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public JPanel getMainGrid() {
        return mainGrid;
    }
}

