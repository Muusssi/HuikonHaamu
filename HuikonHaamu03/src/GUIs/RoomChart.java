package GUIs;

import gameCore.Game;
import gameCore.GameWorld;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;
import gameObj.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;

public class RoomChart {

	private static final long serialVersionUID = 1L;
	public static JPanel chartPanel = new JPanel();;
	public static JLabel roomNameLabel = new JLabel();
	
	public static void setRoomChart(JPanel panel, Room room) {
		if (room == null) {
			return;
		}
		GameObject[] objArr = room.objectArray;

		int xpos = 0;
		int ypos = 0;
		int xdim = room.xdim;
		int ydim = room.ydim;
		
		String code = "";
		if (Game.debug) {
			code = room.code()+": ";
		}
		roomNameLabel.setText(code+room.name());
		roomNameLabel.setBounds(0, 0, 200, 15);
		panel.add(roomNameLabel);
		
		chartPanel.removeAll();
		chartPanel.setBounds(0, 20, xdim*30-10, ydim*30-10);
		chartPanel.setBorder(new BasicBorders.FieldBorder(Color.black, Color.black, Color.black, Color.black));
		chartPanel.setLayout(null);
		
		JButton objButton;
		int buttonCount = 0;
		for (int i=0;i<objArr.length;i++) {
			if (objArr[i] != null) {
				objButton = new GameObjectButton(xpos, ypos, objArr[i]);
				chartPanel.add(objButton);
				buttonCount++;
			}
			xpos++;
			if ((i+1)%xdim == 0) {
				ypos++;
				xpos = 0;
			}
		}
		panel.add(chartPanel);
	}


	public static void setRoomPositionChart(JPanel panel, Room room) {
		if (room == null) {
			return;
		}
		GameObject[] objArr = room.objectArray;

		int xpos = 0;
		int ypos = 0;
		int xdim = room.xdim;
		int ydim = room.ydim;
		
		String code = "";
		if (Game.debug) {
			code = room.code()+": ";
		}
		roomNameLabel.setText(code+room.name());
		roomNameLabel.setBounds(0, 0, 200, 15);
		panel.add(roomNameLabel);
		
		chartPanel.removeAll();
		chartPanel.setBounds(0, 20, xdim*30-10, ydim*30-10);
		chartPanel.setBorder(new BasicBorders.FieldBorder(Color.black, Color.black, Color.black, Color.black));
		chartPanel.setLayout(null);
		
		JButton objButton;
		int buttonCount = 0;
		for (int i=0;i<objArr.length;i++) {
			if (objArr[i] == null) {
				objButton = new GameObjectButton(xpos, ypos, room, i);
				chartPanel.add(objButton);
				buttonCount++;
			}
			else {
				objButton = new GameObjectButton(xpos, ypos, objArr[i]);
				objButton.setEnabled(false);
				chartPanel.add(objButton);
				buttonCount++;
			}
			xpos++;
			if ((i+1)%xdim == 0) {
				ypos++;
				xpos = 0;
			}
		}
		panel.add(chartPanel);
	}
	
	
}
