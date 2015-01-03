package GUIs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gameCore.Game;
import gameObj.Door;
import gameObj.GameObject;
import gameObj.GameThing;
import gameObj.HC;
import gameObj.Room;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import GUIs.WorldEditor.EditObject;
import GUIs.WorldEditor.MoveObject;
import GUIs.WorldEditor.RemoveObject;
import GUIs.WorldEditor.UnLinkDoor;

public class GameObjectButton extends JButton {
	
	GameObject gameObject = null;
	int chosenPosition = 0;
	GameUI gameUI = null;
	
	JPopupMenu gameThingPopup;
	GameThing actionObject = null;
	
	class PositionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GameObjectButton clickedButton = (GameObjectButton)e.getSource();
			System.out.println(clickedButton.chosenPosition);
			WorldEditor.chosenPosition = clickedButton.chosenPosition;
		}
	}
	
	
	class GameClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GameObjectButton clickedButton = (GameObjectButton)e.getSource();
			System.out.println(clickedButton.gameObject.name);
			if (gameObject.getClass() == Door.class) {
				clickedButton.gameObject.go();
			}
			else {
				clickedButton.gameObject.explore();
			}
				
			gameUI.updateGame();
		}
	}
	
	/**
	 * Button for editor
	 */
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
	
	/**
	 * Button for game
	 */
	public GameObjectButton(int xpos, int ypos, GameObject gameObject, GameUI gameui) {
		this.setBounds(xpos*30, ypos*30, 20, 20);
		this.gameObject = gameObject;
		this.gameUI = gameui;
		if (Game.debug) {
			this.setToolTipText(gameObject.position+": "+gameObject.code+": "+gameObject.name);
		}
		else {
			this.setToolTipText(gameObject.name);
		}
		this.chosenPosition = gameObject.position;
		this.addActionListener(new GameClickListener());
		
		// Action popups
		gameThingPopup = new JPopupMenu();
		JMenuItem popupGo = new JMenuItem(HC.GO);
		popupGo.addActionListener(new GoObject());
	    gameThingPopup.add(popupGo);
	    JMenuItem popupOpen = new JMenuItem(HC.OPEN);
	    popupOpen.addActionListener(new OpenObject());
	    gameThingPopup.add(popupOpen);
	    JMenuItem popupClose = new JMenuItem(HC.CLOSE);
	    popupClose.addActionListener(new CloseObject());
	    gameThingPopup.add(popupClose);
	    JMenuItem popupTake = new JMenuItem(HC.TAKE);
	    popupTake.addActionListener(new TakeObject());
	    gameThingPopup.add(popupTake);
	    JMenuItem popupExplore = new JMenuItem(HC.LOOK);
	    popupExplore.addActionListener(new ExploreObject());
	    gameThingPopup.add(popupExplore);
	    JMenuItem popupHit = new JMenuItem(HC.HIT);
	    popupHit.addActionListener(new HitObject());
	    gameThingPopup.add(popupHit);
	    
	    this.add(gameThingPopup);
		
		// Adding a mouselistener for Room objects window, to examine right clicks
		// and on which objects they were made
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				GameObjectButton button = (GameObjectButton) mouseEvent.getSource();
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					actionObject = button.gameObject;
					gameThingPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
					
				}
			}
		};
	    this.addMouseListener(mouseListener);
	}
	
	/**
	 * Button for position chooser
	 */
	public GameObjectButton(int xpos, int ypos, Room room, int roomPosition) {
		this.setBounds(xpos*30, ypos*30, 20, 20);
		this.setToolTipText(Integer.toString(roomPosition));
		this.chosenPosition = roomPosition;
		this.addActionListener(new PositionListener());
	}
	
	
	class GoObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionObject.go();
			gameUI.updateGame();
		}
	}
	class OpenObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionObject.open();
			gameUI.updateGame();
		}
	}
	class CloseObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionObject.close();
			gameUI.updateGame();
		}
	}
	class TakeObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionObject.take();
			gameUI.updateGame();
		}
	}
	class ExploreObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionObject.explore();
			gameUI.updateGame();
		}
	}
	class HitObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionObject.hit();
			gameUI.updateGame();
		}
	}


}
