package parcheesi.game.gui.panel;

import parcheesi.game.gui.Messages;
import parcheesi.game.player.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devondapuzzo on 5/21/17.
 */
public class ControlPanel extends JPanel{


    protected JLabel msgp;
    protected JButton submitButton = new JButton("Submit Move");

    protected DicePanel dicePanel;
    protected PawnPanel pawnPanel;

    public ControlPanel( ActionListener pawnListener, ActionListener diceListener, Pawn[] pawns){
        super();
        this.setLayout(new GridLayout(4,1));
        this.setSize(600, 50);

        dicePanel = new DicePanel(new ArrayList<>(), diceListener);
        pawnPanel = new PawnPanel(pawns, pawnListener);

        this.add(dicePanel);
        this.add(pawnPanel);

        //just for spacing purposes
        msgp  = this.add_label(Messages.welcome);

        this.add(submitButton);
    }


    // add a label to the pane
    protected JLabel add_label(String s) {
        JLabel l = new JLabel(s);

        this.add(l,SwingConstants.CENTER);
        return l;
    }


    public void updateDice(List<Integer> dice) {
        dicePanel.updateDice(dice);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JLabel getMsgp() {
        return msgp;
    }

    public PawnPanel getPawnPanel() {
        return pawnPanel;
    }
}
