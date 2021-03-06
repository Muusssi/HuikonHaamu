package GUIs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameCore.Game;
import gameObj.GameObject;
import gameObj.Room;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class GameObjectButton extends JButton {
	
	GameObject gameObject = null;
	int chosenPosition = 0;
	
	class PositionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GameObjectButton clickedButton = (GameObjectButton)e.getSource();
			System.out.println(clickedButton.chosenPosition);
			WorldEditor.chosenPosition = clickedButton.chosenPosition;
		}
	}
	
	public GameObjectButton(int xpos, int ypos, GameObject gameObject) {
		this.setBounds(xpos*30, ypos*30, 20, 20);
		this.gameObject = gameObject;
		if (Game.debug) {
			this.setToolTipText(gameObject.position+": "+gameObject.code+": "+gameObject.name);
		}
		else {
			this.setToolTipText(gameObject.name);
		}
		this.chosenPosition = gameObject.position;
		this.addActionListener(new PositionListener());
		
	}
	
	public GameObjectButton(int xpos, int ypos, Room room, int roomPosition) {
		this.setBounds(xpos*30, ypos*30, 20, 20);
		this.setToolTipText(Integer.toString(roomPosition));
		this.chosenPosition = roomPosition;
		this.addActionListener(new PositionListener());
	}



}
