package game;

import java.awt.Container;

public abstract class SetPhase extends game.Buttons{

	protected int clickCounter = 0;
	protected int[] clickCoordinates = new int[4];
		
	protected SetPhase() {
		super();
	}
	
	protected abstract void buttons(Container pane);
}
