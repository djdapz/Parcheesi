package parcheesi.game.gui;

import parcheesi.game.moves.Move;

public interface IDisplay {
    // update the message field
    void msgDisplay(String s);

    // register a listener for button events,
    // wait until one of the buttons has been clicked
    Move listen(IListener ll);
}