package parcheesi.game.gui.panel;

import parcheesi.game.gui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by devondapuzzo on 5/21/17.
 */
public class DicePanel extends JPanel{
    protected ButtonGroup group = new ButtonGroup();
    JRadioButton radioButton;
    JPanel checkPanel;
    protected ActionListener listener;



    public DicePanel(List<Integer> diceNumbers, ActionListener listener){
        super();
        this.listener = listener;

        checkPanel = createDiceCheckBoxes(diceNumbers);
        this.add(checkPanel);
    }

    public JPanel createDiceCheckBoxes(List<Integer> diceNumbers) {
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));
        checkPanel.add(new JLabel(Messages.diceLabel));

        if(diceNumbers.contains(4) && diceNumbers.contains(1)){
            radioButton= new JRadioButton("4 + 1");
            radioButton.setActionCommand("5");
            radioButton.addActionListener(listener);
            group.add(radioButton);
            checkPanel.add(radioButton);
        }

        if(diceNumbers.contains(2) && diceNumbers.contains(3)){
            radioButton= new JRadioButton("3 + 2");
            radioButton.setActionCommand("5");
            radioButton.addActionListener(listener);
            group.add(radioButton);
            checkPanel.add(radioButton);
        }

        for(Integer die: diceNumbers){
            radioButton= new JRadioButton(die.toString());
            radioButton.setActionCommand(die.toString());
            radioButton.addActionListener(listener);
            group.add(radioButton);
            checkPanel.add(radioButton);
        }

        return checkPanel;
    }

    public void updateDice(List<Integer> diceNumbers){
        this.remove(checkPanel);

        checkPanel = createDiceCheckBoxes(diceNumbers);
        this.add(checkPanel);
    }
}
