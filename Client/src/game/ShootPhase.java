package game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ShootPhase extends Buttons{

	public ShootPhase() {
		super();
	}
	
	public void buttons(Container pane) {
		this.al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String source = e.getSource().toString().substring(21);
				getCoordinatesCounter = 0;
				int[] coordinates = getCoordinatesOfClick(source);
				shoot(coordinates);
			}
		};
		this.setButtons();
		pane.add(this.panel, BorderLayout.NORTH);
	}
	
	protected abstract void shoot(int[] coordinates);
	
}
