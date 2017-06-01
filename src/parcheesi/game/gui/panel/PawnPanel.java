package parcheesi.game.gui.panel;

import parcheesi.game.gui.Messages;
import parcheesi.game.player.Pawn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Created by devondapuzzo on 5/21/17.
 */
public class PawnPanel extends JPanel{

    private HashMap<Integer, Integer> keyEventHashMap = new HashMap<>();
    protected ButtonGroup group = new ButtonGroup();
    JPanel radioPanel = new JPanel(new GridLayout(0, 1));
    JRadioButton pawnButton;



    public PawnPanel(Pawn[] pawns, ActionListener listener){
        super();

        radioPanel.add(new JLabel(Messages.pawnLabel));
        keyEventHashMap.put(0, KeyEvent.VK_1);
        keyEventHashMap.put(1, KeyEvent.VK_2);
        keyEventHashMap.put(2, KeyEvent.VK_3);
        keyEventHashMap.put(3, KeyEvent.VK_4);

        for(Pawn pawn: pawns){
            pawnButton = new JRadioButton("Pawn "+Integer.toString(pawn.getId() + 1));
            pawnButton.setActionCommand(Integer.toString(pawn.getId()));
            pawnButton.setMnemonic(keyEventHashMap.get(pawn.getId()));
            pawnButton.addActionListener(listener);
            group.add(pawnButton);
            radioPanel.add(pawnButton);
        }
        this.add(radioPanel);
        group.clearSelection();
    }

    public ButtonGroup getGroup() {
        return group;
    }
}
