package parcheesi.game.gui;// the Views: determine ordering of button and label with boolean

import parcheesi.game.board.Board;
import parcheesi.game.exception.InvalidMoveException;
import parcheesi.game.exception.PawnNotFoundException;
import parcheesi.game.gui.panel.ControlPanel;
import parcheesi.game.moves.Move;
import parcheesi.game.player.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewHuman extends Layout implements IDisplay {

	private IListener l;
	private JButton submitButton = controlPanel.getSubmitButton();
	private JLabel msgp = controlPanel.getMsgp();
	protected ActionListener pawnListener;

	protected ActionListener diceListener;

	public ViewHuman(Player player, Board board) {
		super(player, board);
		submitButton.addActionListener(
				e ->{
					try {
						l.submit(selectedPawn, selectedDistance);
						l=null;
					} catch (PawnNotFoundException e1) {
						e1.printStackTrace();
						l=null;
					} catch (InvalidMoveException e1) {
						e1.printStackTrace();
					}
				}
		);
	}

	@Override
	protected ControlPanel createControlPanel() {
	  	pawnListener= this::selectedPawnChanged;
		diceListener = this::dieSelected;

		return new ControlPanel(pawnListener, diceListener, player.getPawns());
	}

	public Move listen(IListener ll) {
		int x=0;
		l = ll;

		submitButton.setEnabled(false);

		// wait until one of the buttons has been clicked
		while (l != null) {
			if(selectedPawn != null && selectedDistance != null){
				submitButton.setEnabled(true);
			}
			System.out.print("");
		}

		selectedPawn = null;
		selectedDistance = null;

		controlPanel.getPawnPanel().getGroup().clearSelection();
		submitButton.setEnabled(false);

		return ll.getMove();

	}

	public void msgDisplay(String s) {
		msgp.setText(s);
	}

	public void updateBoard(Board board){
		this.currentBoard = board;
		super.updateBoard(currentBoard);
	}

	public void updateDice(List<Integer> dice){
		controlPanel.updateDice(dice);
		super.refreshWindow();
	}

	protected void selectedPawnChanged(ActionEvent e) {
		String pawnId = e.getActionCommand();
		this.selectedPawn = player.getPawns()[Integer.parseInt(pawnId)];
		updateBoard(currentBoard);
	}

	protected void dieSelected(ActionEvent e) {
		this.selectedDistance = Integer.parseInt(e.getActionCommand());
	}




}
