package GUIs;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import gameCore.*;
import gameExceptions.IllegalGameCodeException;
import gameExceptions.WorldMakingConflict;
import gameObj.*;

public class WorldEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static WorldEditor worldEditor;
	JPanel editorPanel;


	//Roomlist
	JLabel roomListLabel;
	JScrollPane roomListPane;
	JList roomList;
	JPopupMenu roomPopup;
    JMenuItem roomPopupDelete;
    JMenuItem roomPopupEdit;
    
	//Objectlist
	JLabel objectListLabel;
	JScrollPane objectListPane;
	JList objectList;
	JPopupMenu objectPopup;
    JMenuItem objectPopupDelete;
    JMenuItem objectPopupEdit;
    JMenuItem objectPopupMoveToVoid;
    JMenuItem objectPopupMove;
    JMenuItem objectPopupLinkDoor;
    
	//Voidlist
	JLabel voidListLabel;
	JScrollPane voidListPane;
	JList voidList;
	JPopupMenu voidPopup;
    JMenuItem voidPopupDelete;
    JMenuItem voidPopupEdit;
    JMenuItem voidPopupMove;
    
	//Chart
    JPanel chartPane = null;
    
    //Thing editing
    Room editedRoom = null;
    static int chosenPosition;
    static GameObject chosenGameObject;


	GameWorld gameWorld;
	WorldEditor wegui;
	
	
	
	private void setChartPanel() {
		chartPane = new JPanel();
		chartPane.setBounds(530, 30, 230, 250);
		chartPane.setLayout(null);
		chartPane.setVisible(true);
		editorPanel.add(chartPane);
	}
	
	private void updateEditor() {
		this.updateRoomList();
		this.updateObjectList(editedRoom);
		this.updateVoidList();
		RoomChart.setRoomChart(chartPane, editedRoom);
	}
	
	class AddRoom implements ActionListener {
		JTextField roomNameField = new JTextField(0);
		JTextField roomDescriptionField = new JTextField(0);
		JTextField roomCodeField = new JTextField(0);
		JTextField xdimField = new JTextField(0);
		JTextField ydimField = new JTextField(0);
		String roomName;
		String roomDesc;
		String roomCode;
		int xdim;
		int ydim;
		

		public void actionPerformed(ActionEvent e) {
			JLabel nameLabel = new JLabel(HC.EDITOR_NAME_LABEL);
			nameLabel.setToolTipText(HC.ROOM_EDITOR_NAME_TOOLTIP);

			JLabel descLabel = new JLabel(HC.EDITOR_DESCRIPTION_LABEL);
			descLabel.setToolTipText(HC.ROOM_EDITOR_DESCRIPTION_TOOLTIP);

			JLabel codeLabel = new JLabel(HC.EDITOR_CODE_LABEL);
			codeLabel.setToolTipText(HC.EDITOR_CODE_TOOLTIP);

			JLabel xdimLabel = new JLabel(HC.ROOM_EDITOR_XDIM_LABEL);
			xdimLabel.setToolTipText(HC.ROOM_EDITOR_XDIM_TOOLTIP);

			JLabel ydimLabel = new JLabel(HC.ROOM_EDITOR_YDIM_LABEL);
			ydimLabel.setToolTipText(HC.ROOM_EDITOR_YDIM_TOOLTIP);

			JPanel newRoomPanel = new JPanel();

			roomNameField.setText("");
			roomDescriptionField.setText(HC.ROOM_DESCRIPTION);
			roomCodeField.setText(GameThing.getNextCode(gameWorld, "r"));
			xdimField.setText("3");
			ydimField.setText("3");

			newRoomPanel.setLayout(new GridLayout(5,0));
			newRoomPanel.add(nameLabel);
			newRoomPanel.add(roomNameField);
			newRoomPanel.add(descLabel);
			newRoomPanel.add(roomDescriptionField);
			newRoomPanel.add(codeLabel);
			newRoomPanel.add(roomCodeField);

			newRoomPanel.add(xdimLabel);
			newRoomPanel.add(xdimField);
			newRoomPanel.add(ydimLabel);
			newRoomPanel.add(ydimField);

			boolean roomOK = false;

			do {
				int result = JOptionPane.showConfirmDialog(null, newRoomPanel,
						HC.EDITOR_NEW_ROOM_TTILE, JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					try {
						roomName = roomNameField.getText();
						roomDesc = roomDescriptionField.getText();
						roomCode = roomCodeField.getText();
						xdim = Integer.parseInt(xdimField.getText());
						ydim = Integer.parseInt(ydimField.getText());

						if (roomName.equals("")) {
							JOptionPane.showMessageDialog(null,
									HC.ROOM_EDITOR_MISSING_NAME);
						}
						else if (gameWorld.gameThings.containsKey(roomCode)) {
							JOptionPane.showMessageDialog(null,
									HC.EDITOR_CODE_TAKEN);
						}
						else if (xdim < 2 || xdim > 7 || ydim < 2 || ydim > 7) {
							JOptionPane.showMessageDialog(null,
									HC.ROOM_EDITOR_DIM_OUT_OF_BOUNDS);
						}
						else {
							Room newRoom = new Room(gameWorld, roomName, roomDesc, roomCode, xdim, ydim);
							roomOK = true;
							updateRoomList();
							editedRoom = newRoom;
							updateEditor();
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null,
								HC.ROOM_EDITOR_DIM_NOT_INT);
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IllegalGameCodeException e1) {
						JOptionPane.showMessageDialog(null,"GameCode ERROR");
					} catch (WorldMakingConflict e1) {
						e1.printStackTrace();//Not a problem
					}
				}
				else {
					roomOK = true;
				}
			} while (! roomOK);
		};
	}
	
	class EditRoom implements ActionListener {
		JTextField roomNameField = new JTextField(0);
		JTextField roomDescriptionField = new JTextField(0);
		JTextField roomCodeField = new JTextField(0);
		JTextField xdimField = new JTextField(0);
		JTextField ydimField = new JTextField(0);
		String roomName;
		String roomDesc;
		String roomCode;
		int xdim;
		int ydim;

		public void actionPerformed(ActionEvent e) {
			JLabel nameLabel = new JLabel(HC.EDITOR_NAME_LABEL);
			nameLabel.setToolTipText(HC.ROOM_EDITOR_NAME_TOOLTIP);

			JLabel descLabel = new JLabel(HC.EDITOR_DESCRIPTION_LABEL);
			descLabel.setToolTipText(HC.ROOM_EDITOR_DESCRIPTION_TOOLTIP);

			JLabel codeLabel = new JLabel(HC.EDITOR_CODE_LABEL);
			codeLabel.setToolTipText(HC.EDITOR_CODE_TOOLTIP);

			JLabel xdimLabel = new JLabel(HC.ROOM_EDITOR_XDIM_LABEL);
			xdimLabel.setToolTipText(HC.ROOM_EDITOR_XDIM_TOOLTIP);

			JLabel ydimLabel = new JLabel(HC.ROOM_EDITOR_YDIM_LABEL);
			ydimLabel.setToolTipText(HC.ROOM_EDITOR_YDIM_TOOLTIP);

			roomNameField.setText(editedRoom.name);
			roomDescriptionField.setText(editedRoom.description);
			roomCodeField.setText(editedRoom.code);
			xdimField.setText(Integer.toString(editedRoom.xdim));
			ydimField.setText(Integer.toString(editedRoom.ydim));
			
			JPanel newRoomPanel = new JPanel();

			newRoomPanel.setLayout(new GridLayout(5,0));
			newRoomPanel.add(nameLabel);
			newRoomPanel.add(roomNameField);
			newRoomPanel.add(descLabel);
			newRoomPanel.add(roomDescriptionField);
			newRoomPanel.add(codeLabel);
			newRoomPanel.add(roomCodeField);

			newRoomPanel.add(xdimLabel);
			newRoomPanel.add(xdimField);
			newRoomPanel.add(ydimLabel);
			newRoomPanel.add(ydimField);

			boolean roomOK = false;

			do {
				int result = JOptionPane.showConfirmDialog(null, newRoomPanel,
						HC.EDITOR_EDIT_ROOM_TITLE+editedRoom.name, JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					try {
						roomName = roomNameField.getText();
						roomDesc = roomDescriptionField.getText();
						roomCode = roomCodeField.getText();
						xdim = Integer.parseInt(xdimField.getText());
						ydim = Integer.parseInt(ydimField.getText());

						if (roomName.equals("")) {
							JOptionPane.showMessageDialog(null,
									HC.ROOM_EDITOR_MISSING_NAME);
						}
						else if (!editedRoom.code.equals(roomCode) 
								&& gameWorld.gameThings.containsKey(roomCode)) {
							JOptionPane.showMessageDialog(null,
									HC.EDITOR_CODE_TAKEN);
						}
						else if (editedRoom.newDimOutOfBounds(xdim, ydim)) {
							JOptionPane.showMessageDialog(null,
									HC.ROOM_EDITOR_DIM_OUT_OF_BOUNDS);
						}
						else {
							if (editedRoom.xdim != xdim || editedRoom.ydim != ydim) {
								if (editedRoom.objectMap.isEmpty()) {
									editedRoom.changeDimensions(xdim, ydim);
								}
								else {
									JLabel objectsToVoidLabel = new JLabel(HC.EDITOR_NOT_EMPTY_ROOM_DIM_CHG);
									int yes_no = JOptionPane.showConfirmDialog(null, objectsToVoidLabel,
											null, JOptionPane.YES_NO_OPTION);
									if (yes_no == JOptionPane.YES_OPTION) {
										editedRoom.objectsToVoid();
										editedRoom.changeDimensions(xdim, ydim);
									}
								}
							}
							editedRoom.name = roomName;
							editedRoom.giveDescription(roomDesc);
							if (!editedRoom.code.equals(roomCode)) {
								editedRoom.changeCode(roomCode);
							}
							roomOK = true;
							updateEditor();
						}
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null,
								HC.ROOM_EDITOR_DIM_NOT_INT);
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					}
				}
				else {
					roomOK = true;
				}
			} while (! roomOK);
		};
	}
	
	@SuppressWarnings("serial")
	public class PositionSetter extends JFrame {
		public PositionSetter() {
			JPanel positionPanel = new JPanel();
			positionPanel.setLayout(null);
		    RoomChart.setRoomPositionChart(positionPanel, editedRoom);
		    positionPanel.setBounds(0, 0, 300, 300);
		    chosenPosition = -1;
			do {
				JOptionPane optionpane = new JOptionPane(positionPanel, JOptionPane.PLAIN_MESSAGE);
				JDialog dialog = optionpane.createDialog(null, HC.EDITOR_OBJECT_POSITION_SETTER_TITLE);
				dialog.setSize(350, 350);
				dialog.setVisible(true);
			} while (chosenPosition < 0);
		}
	}

	class AddObject implements ActionListener {
		JTextField objectNameField = new JTextField(0);
		JTextField objectDescriptionField = new JTextField(0);
		JTextField objectCodeField = new JTextField(0);
		JTextField positionField = new JTextField(0);
		String objectName;
		String objectDesc;
		String objectCode;
		int position;
		boolean toVoid = false;

		public void actionPerformed(ActionEvent e) {
			if (editedRoom == null) {
				toVoid = true;
			}
			else if (editedRoom.isFull()) {
				JOptionPane.showMessageDialog(null, HC.EDITOR_OBJECT_ROOM_FULL);
				return;
			}
			JLabel nameLabel = new JLabel(HC.EDITOR_NAME_LABEL);
			nameLabel.setToolTipText(HC.OBJECT_EDITOR_NAME_TOOLTIP);

			JLabel descLabel = new JLabel(HC.EDITOR_DESCRIPTION_LABEL);
			descLabel.setToolTipText(HC.OBJECT_EDITOR_DESCRIPTION_TOOLTIP);

			JLabel codeLabel = new JLabel(HC.EDITOR_CODE_LABEL);
			codeLabel.setToolTipText(HC.EDITOR_CODE_TOOLTIP);

			JLabel positionLabel = new JLabel(HC.OBJECT_EDITOR_POSITION_LABEL);
			positionLabel.setToolTipText(HC.OBJECT_EDITOR_POSITION_TOOLTIP);
			

			JLabel toVoidLabel = new JLabel(HC.OBJECT_EDITOR_TOVOID_LABEL);
			toVoidLabel.setToolTipText(HC.OBJECT_EDITOR_TOVOID_TOOLTIP);
			JCheckBox toVoidCB = new JCheckBox();
			toVoidCB.setToolTipText(HC.OBJECT_EDITOR_TOVOID_TOOLTIP);

			JPanel newRoomPanel = new JPanel();

			objectNameField.setText("");
			objectDescriptionField.setText(HC.OBJECT_DESCRIPTION);
			objectCodeField.setText(GameThing.getNextCode(gameWorld, "o"));
			positionField.setText("");

			newRoomPanel.setLayout(new GridLayout(5,0));
			newRoomPanel.add(nameLabel);
			newRoomPanel.add(objectNameField);
			newRoomPanel.add(descLabel);
			newRoomPanel.add(objectDescriptionField);
			newRoomPanel.add(codeLabel);
			newRoomPanel.add(objectCodeField);
			
			if (toVoid) {
				toVoidCB.setSelected(true);
			}
			else {
				toVoidCB.setSelected(false);
				newRoomPanel.add(positionLabel);
				newRoomPanel.add(positionField);
				newRoomPanel.add(toVoidLabel);
				newRoomPanel.add(toVoidCB);
			}
			boolean roomOK = false;

			do {
				int result;
				if (editedRoom == null) {
					result = JOptionPane.showConfirmDialog(null, newRoomPanel,
							"New object in void", JOptionPane.OK_CANCEL_OPTION);
				}
				else {
					result = JOptionPane.showConfirmDialog(null, newRoomPanel,
							"New object in room "+editedRoom.code+": "+editedRoom.name,
							JOptionPane.OK_CANCEL_OPTION);
				}
				if (result == JOptionPane.OK_OPTION) {

					if (toVoidCB.isSelected()) {
						toVoid = true;
					}
					else {
						toVoid = false;
					}

					try {
						objectName = objectNameField.getText();
						objectDesc = objectDescriptionField.getText();
						objectCode = objectCodeField.getText();
						if (!toVoid) {
							if (positionField.getText().equals("")) {
								new PositionSetter();
								position = chosenPosition;
								positionField.setText(Integer.toString(position));
							}
							else {
								position = Integer.parseInt(positionField.getText());
							}
						}

						if (objectName.equals("")) {
							JOptionPane.showMessageDialog(null,
									HC.OBJECT_EDITOR_MISSING_NAME);
						}
						else if (gameWorld.gameThings.containsKey(objectCode)) {
							JOptionPane.showMessageDialog(null,
									HC.EDITOR_CODE_TAKEN);
						}
						else if (!toVoid && (position >= editedRoom.objectArray.length 
								|| editedRoom.objectArray[position] != null)) {
							JOptionPane.showMessageDialog(null,
									HC.OBJECT_EDITOR_POSITION_ILLEGAL);
						}
						else {
							if (toVoid) {
								new GameObject(gameWorld, objectName, objectDesc, objectCode, null, 0);
							}
							else {
								new GameObject(gameWorld, objectName, objectDesc, objectCode, editedRoom, position);

							}
							roomOK = true;
							toVoid = false;
							updateEditor();
						}
					} catch (NumberFormatException e1) {
						// NOT A PROBLEM
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IllegalGameCodeException e1) {
						JOptionPane.showMessageDialog(null,"GameCode ERROR");
					} catch (WorldMakingConflict e1) {
						e1.printStackTrace();//Not a problem
					}
				}
				else {
					roomOK = true;
				}
			} while (! roomOK);
			toVoidCB.setSelected(false);
		};
	}
	
	class AddDoor implements ActionListener {
		JTextField objectNameField = new JTextField(0);
		JTextField objectDescriptionField = new JTextField(0);
		JTextField objectCodeField = new JTextField(0);
		JTextField positionField = new JTextField(0);
		String objectName;
		String objectDesc;
		String objectCode;
		int position;
		boolean toVoid = false;

		public void actionPerformed(ActionEvent e) {
			if (editedRoom == null) {
				toVoid = true;
			}
			else if (editedRoom.isFull()) {
				JOptionPane.showMessageDialog(null, HC.EDITOR_OBJECT_ROOM_FULL);
				return;
			}
			JLabel nameLabel = new JLabel(HC.EDITOR_NAME_LABEL);
			nameLabel.setToolTipText(HC.OBJECT_EDITOR_NAME_TOOLTIP);

			JLabel descLabel = new JLabel(HC.EDITOR_DESCRIPTION_LABEL);
			descLabel.setToolTipText(HC.OBJECT_EDITOR_DESCRIPTION_TOOLTIP);

			JLabel codeLabel = new JLabel(HC.EDITOR_CODE_LABEL);
			codeLabel.setToolTipText(HC.EDITOR_CODE_TOOLTIP);

			JLabel positionLabel = new JLabel(HC.OBJECT_EDITOR_POSITION_LABEL);
			positionLabel.setToolTipText(HC.OBJECT_EDITOR_POSITION_TOOLTIP);
			

			JLabel toVoidLabel = new JLabel(HC.OBJECT_EDITOR_TOVOID_LABEL);
			toVoidLabel.setToolTipText(HC.OBJECT_EDITOR_TOVOID_TOOLTIP);
			JCheckBox toVoidCB = new JCheckBox();
			toVoidCB.setToolTipText(HC.OBJECT_EDITOR_TOVOID_TOOLTIP);

			JPanel newRoomPanel = new JPanel();

			objectNameField.setText("");
			objectDescriptionField.setText(HC.DOOR_DESCRIPTION);
			objectCodeField.setText(GameThing.getNextCode(gameWorld, "d"));
			positionField.setText("");

			newRoomPanel.setLayout(new GridLayout(5,0));
			newRoomPanel.add(nameLabel);
			newRoomPanel.add(objectNameField);
			newRoomPanel.add(descLabel);
			newRoomPanel.add(objectDescriptionField);
			newRoomPanel.add(codeLabel);
			newRoomPanel.add(objectCodeField);
			
			if (toVoid) {
				toVoidCB.setSelected(true);
			}
			else {
				toVoidCB.setSelected(false);
				newRoomPanel.add(positionLabel);
				newRoomPanel.add(positionField);
				newRoomPanel.add(toVoidLabel);
				newRoomPanel.add(toVoidCB);
			}
			boolean roomOK = false;

			do {
				int result;
				if (editedRoom == null) {
					result = JOptionPane.showConfirmDialog(null, newRoomPanel,
							"New object in void", JOptionPane.OK_CANCEL_OPTION);
				}
				else {
					result = JOptionPane.showConfirmDialog(null, newRoomPanel,
							"New object in room "+editedRoom.code+": "+editedRoom.name,
							JOptionPane.OK_CANCEL_OPTION);
				}
				if (result == JOptionPane.OK_OPTION) {

					if (toVoidCB.isSelected()) {
						toVoid = true;
					}
					else {
						toVoid = false;
					}

					try {
						objectName = objectNameField.getText();
						objectDesc = objectDescriptionField.getText();
						objectCode = objectCodeField.getText();
						if (!toVoid) {
							if (positionField.getText().equals("")) {
								new PositionSetter();
								position = chosenPosition;
								positionField.setText(Integer.toString(position));
							}
							else {
								position = Integer.parseInt(positionField.getText());
							}
						}

						if (objectName.equals("")) {
							JOptionPane.showMessageDialog(null,
									HC.OBJECT_EDITOR_MISSING_NAME);
						}
						else if (gameWorld.gameThings.containsKey(objectCode)) {
							JOptionPane.showMessageDialog(null,
									HC.EDITOR_CODE_TAKEN);
						}
						else if (!toVoid && (position >= editedRoom.objectArray.length 
								|| editedRoom.objectArray[position] != null)) {
							JOptionPane.showMessageDialog(null,
									HC.OBJECT_EDITOR_POSITION_ILLEGAL);
						}
						else {
							if (toVoid) {
								new Door(gameWorld, objectName, objectDesc, objectCode, null, 0);
							}
							else {
								new Door(gameWorld, objectName, objectDesc, objectCode, editedRoom, position);

							}
							roomOK = true;
							toVoid = false;
							updateEditor();
						}
					} catch (NumberFormatException e1) {
						// NOT A PROBLEM
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IllegalGameCodeException e1) {
						JOptionPane.showMessageDialog(null,"GameCode ERROR");
					} catch (WorldMakingConflict e1) {
						e1.printStackTrace();//Not a problem
					}
				}
				else {
					roomOK = true;
				}
			} while (! roomOK);
			toVoidCB.setSelected(false);
		};
	}
	
	
	
	class MoveObject implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] roomArray = gameWorld.getRoomArrayForEditor();
			if (roomArray.length != 0) {
				JComboBox combo = new JComboBox(roomArray);
				int result;
				result = JOptionPane.showConfirmDialog(null, combo,
						HC.EDITOR_MOVE_CHOOSE_ROOM_TITLE,
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					editedRoom = gameWorld.roomMap.get(((String) combo
							.getSelectedItem()).split(":")[0]);
					new PositionSetter();
					try {
						editedRoom.putObject(chosenGameObject, chosenPosition);
					} catch (WorldMakingConflict e1) {
						e1.printStackTrace();
					}
					updateEditor();
				}
				updateEditor();
			}
		};
	}
	
	class LinkDoor implements ActionListener {//TODO
		public void actionPerformed(ActionEvent e) {
			if (chosenGameObject instanceof Door ) {
				System.out.println("Linking...");
			}
			/*
			String[] roomArray = gameWorld.getRoomArrayForEditor();
			System.out.println("rooms: " + roomArray.length);
			JComboBox combo = new JComboBox(roomArray);

			int result;
			result = JOptionPane.showConfirmDialog(null, combo,
					HC.EDITOR_MOVE_CHOOSE_ROOM_TITLE, JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				editedRoom = gameWorld.roomMap.get(((String) combo.getSelectedItem()).split(":")[0]);
				new PositionSetter();
				try {
					editedRoom.putObject(chosenGameObject, chosenPosition);
				} catch (WorldMakingConflict e1) {
					e1.printStackTrace();
				}
				updateEditor();
			}
			*/
			updateEditor();
		};
	}
	
	
	class EditObject implements ActionListener {
		JTextField objectNameField = new JTextField(0);
		JTextField objectDescriptionField = new JTextField(0);
		JTextField objectCodeField = new JTextField(0);
		JTextField positionField = new JTextField(0);
		String objectName;
		String objectDesc;
		String objectCode;
		int position;
		boolean toVoid = false;

		public void actionPerformed(ActionEvent e) {
			JLabel nameLabel = new JLabel(HC.EDITOR_NAME_LABEL);
			nameLabel.setToolTipText(HC.OBJECT_EDITOR_NAME_TOOLTIP);

			JLabel descLabel = new JLabel(HC.EDITOR_DESCRIPTION_LABEL);
			descLabel.setToolTipText(HC.OBJECT_EDITOR_DESCRIPTION_TOOLTIP);

			JLabel codeLabel = new JLabel(HC.EDITOR_CODE_LABEL);
			codeLabel.setToolTipText(HC.EDITOR_CODE_TOOLTIP);

			JLabel positionLabel = new JLabel(HC.OBJECT_EDITOR_POSITION_LABEL);
			positionLabel.setToolTipText(HC.OBJECT_EDITOR_POSITION_TOOLTIP);
			
			
			JPanel editObjectPanel = new JPanel();
			
			objectNameField.setText(chosenGameObject.name);
			objectDescriptionField.setText(chosenGameObject.description);
			objectCodeField.setText(chosenGameObject.code);
			positionField.setText(Integer.toString(chosenGameObject.position));

			editObjectPanel.setLayout(new GridLayout(5,0));
			editObjectPanel.add(nameLabel);
			editObjectPanel.add(objectNameField);
			editObjectPanel.add(descLabel);
			editObjectPanel.add(objectDescriptionField);
			editObjectPanel.add(codeLabel);
			editObjectPanel.add(objectCodeField);
			
			boolean roomOK = false;
			boolean inVoid = true;
			if (chosenGameObject.location != null) {
				editObjectPanel.add(positionLabel);
				editObjectPanel.add(positionField);
				inVoid = false;
			}
			do {
				int result;
				result = JOptionPane.showConfirmDialog(null, editObjectPanel,
						chosenGameObject.name, JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					try {
						objectName = objectNameField.getText();
						objectDesc = objectDescriptionField.getText();
						objectCode = objectCodeField.getText();
						if (positionField.getText().equals("")) {
							new PositionSetter();
							position = chosenPosition;
							positionField.setText(Integer.toString(position));
						}
						else {
							position = Integer.parseInt(positionField.getText());
						}
					
						if (objectName.equals("")) {
							JOptionPane.showMessageDialog(null,
									HC.OBJECT_EDITOR_MISSING_NAME);
						}
						else if (!objectCode.equals(chosenGameObject.code) 
								&& gameWorld.gameThings.containsKey(objectCode)) {
							JOptionPane.showMessageDialog(null,
									HC.EDITOR_CODE_TAKEN);
						}
						else if (!inVoid && (position >= editedRoom.objectArray.length 
								|| (chosenGameObject.position != position 
								&& editedRoom.objectArray[position] != null))) {
							JOptionPane.showMessageDialog(null,
									HC.OBJECT_EDITOR_POSITION_ILLEGAL);
						}
						else {
							chosenGameObject.name = objectName;
							chosenGameObject.giveDescription(objectDesc);
							if (!objectCode.equals(chosenGameObject.code)) {
								chosenGameObject.changeCode(objectCode);
							}
							if (!inVoid) {
								chosenGameObject.movePosition(position);
								editedRoom = chosenGameObject.location;
							}
							roomOK = true;
							updateEditor();
						}
					} catch (NumberFormatException e1) {
						// NOT A PROBLEM
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					}
				}
				else {
					roomOK = true;
				}
			} while (! roomOK);
		};
	}
	


	class RemoveObject implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			gameWorld.remove(chosenGameObject);
			updateEditor();
		}	
	}
	
	class RemoveRoom implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			gameWorld.remove(editedRoom);
			editedRoom = null;
			updateEditor();
		}	
	}
	
	class MoveToVoid implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			chosenGameObject.putToVoid();
			updateEditor();
		}	
	}
	
	
	public void updateRoomList() {
		roomList = new JList(gameWorld.getRoomArrayForEditor());
		roomListPane.getViewport().add(roomList);
		editorPanel.add(roomListPane);

		// Adding a mouselistener for Rooms window, to examine right clicks
	    // and on which objects they were made
	    MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						String o = (String) theList.getModel().getElementAt(index);
						editedRoom = (Room)gameWorld.gameThings.get(o.split(":")[0]);
						roomPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
					}
				}
				else if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						String roomCode = o.toString().split(":")[0];
						editedRoom = gameWorld.roomMap.get(roomCode);
						updateEditor();
					}
				}
			}
	    };
	    roomList.addMouseListener(mouseListener);
    }

	public void updateObjectList(Room room) {
		if (room == null) {
			objectList = new JList();
		}
		else {
			objectList = new JList(room.getObjectArrayForEditor());
		}
		objectListPane.getViewport().add(objectList);
		editorPanel.add(objectListPane);
		editedRoom = room;

		// Adding a mouselistener for Room objects window, to examine right clicks
		// and on which objects they were made
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						String line = (String)theList.getModel().getElementAt(index);
						chosenGameObject = (GameObject)gameWorld.gameThings.get(line.split(":")[0]);
						objectPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
						
					}
				}
			}
		};
	    objectList.addMouseListener(mouseListener);
    }
	
	public void updateVoidList() {
		voidList = new JList(gameWorld.getVoidObjectArrayForEditor());
		voidListPane.getViewport().add(voidList);
		editorPanel.add(voidListPane);

		// Adding a mouselistener for Room objects window, to examine right clicks
		// and on which objects they were made
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						String line = (String)theList.getModel().getElementAt(index);
						chosenGameObject = (GameObject)gameWorld.gameThings.get(line.split(":")[0]);
						voidPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
					}
				}
			}
		};
	    voidList.addMouseListener(mouseListener);
    }

	public void setWorldName() {
		JPanel worldNamePanel = new JPanel();
		worldNamePanel.setLayout(new GridLayout(2,1));
		JLabel worldNameLabel = new JLabel(HC.EDITOR_WORLD_NAME_LABEL);
		worldNameLabel.setToolTipText(HC.EDITOR_WORLD_NAME_TOOLTIP);
		JTextField worldNameField = new JTextField(gameWorld.name);
		worldNamePanel.add(worldNameLabel);
		worldNamePanel.add(worldNameField);
		boolean nameOK = false;
		do {
			int result = JOptionPane.showConfirmDialog(null, worldNamePanel,
					HC.EDITOR_WORLD_NAME_TITLE, JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION ) {
				String newName = worldNameField.getText();
				if (!newName.equals("")) {
					gameWorld.name = newName;
					this.setTitle("World Editor - "+ newName);
					nameOK = true;
				}
			}
			else {
				nameOK = true;
			}
				
		} while (!nameOK);
	}
	
	
	class Save implements ActionListener {
		String saveName;
		public void actionPerformed(ActionEvent e) {
			while (gameWorld.name.equals("")) {
				setWorldName();
			}
			JPanel savePanel = new JPanel();
			savePanel.setLayout(new GridLayout(2,1));
			JLabel saveNameLabel = new JLabel(HC.EDITOR_SAVENAME_LABEL);
			saveNameLabel.setToolTipText(HC.EDITOR_SAVENAME_TOOLTIP);
			JTextField saveNameField = new JTextField(gameWorld.name);
			savePanel.add(saveNameLabel);
			savePanel.add(saveNameField);
			
			boolean saveOK = false;
			do {
				int result = JOptionPane.showConfirmDialog(null, savePanel,
						HC.EDITOR_SAVENAME_TITLE, JOptionPane.OK_CANCEL_OPTION);
				saveName = saveNameField.getText();
						
				if (result == JOptionPane.OK_OPTION) {
					if (saveName.equals("")) {
						JOptionPane.showMessageDialog(null, HC.EDITOR_SAVENAME_MISSING);
					}
					else {
						gameWorld.saveWorld(saveName);
						JOptionPane.showMessageDialog(null, saveName+".hhw");
						saveOK = true;
					}
					
				}
				else {
					saveOK = true;
				}
			
			} while (!saveOK);
		}
	}
	
	class Load implements ActionListener {
		String saveName;
		public void actionPerformed(ActionEvent e) {
			JPanel savePanel = new JPanel();
			savePanel.setLayout(new GridLayout(2,1));
			JLabel saveNameLabel = new JLabel(HC.EDITOR_SAVENAME_LABEL);
			saveNameLabel.setToolTipText(HC.EDITOR_SAVENAME_TOOLTIP);
			JTextField saveNameField = new JTextField(gameWorld.name);
			savePanel.add(saveNameLabel);
			savePanel.add(saveNameField);
			
			boolean loadOK = false;
			do {
				int result = JOptionPane.showConfirmDialog(null, savePanel,
						"", JOptionPane.OK_CANCEL_OPTION);
				saveName = saveNameField.getText();
						
				if (result == JOptionPane.OK_OPTION) {
					if (saveName.equals("")) {
						JOptionPane.showMessageDialog(null, HC.EDITOR_SAVENAME_MISSING);
					}
					else {
						File f = new File(saveName+".hhw");
						if(f.exists() && !f.isDirectory()) {
							gameWorld = GameWorld.loadWorld(saveName+".hhw");
							wegui.setTitle("World Editor - "+ gameWorld.name);		
							updateEditor();
							loadOK = true;
						}
						else {
							JOptionPane.showMessageDialog(null, HC.EDITOR_LOAD_FILE_MISSING);
						}
					}
				}
				else {
					loadOK = true;
				}
			
			} while (!loadOK);
		}
	}
	
	public WorldEditor(String lang) {
		wegui = this;
		gameWorld = new GameWorld("", lang);


		setTitle("Huikon Haamu - World Editor - "+gameWorld.name);
		setSize(1150,600);
		setLocationRelativeTo(null);

		editorPanel = new JPanel();
	    getContentPane().add(editorPanel);
	    editorPanel.setLayout(null);


	    // Setting room directory
	    roomListLabel = new JLabel(HC.EDITOR_ROOMS_LIST_LABEL);
	    roomListLabel.setBounds(50,30,100,20);
	    roomListPane = new JScrollPane();
	    roomListPane.setBounds(50, 50, 200, 200);
	    editorPanel.add(roomListLabel);
	    editorPanel.add(roomListPane);

	    roomPopup = new JPopupMenu();
	    roomPopupDelete = new JMenuItem(HC.EDITOR_POPUP_DELETE);
	    roomPopupDelete.addActionListener(new RemoveRoom());
	    roomPopup.add(roomPopupDelete);
	    roomPopupEdit = new JMenuItem(HC.EDITOR_POPUP_EDIT);
	    roomPopupEdit.addActionListener(new EditRoom());
	    roomPopup.add(roomPopupEdit);
	    roomList = new JList();
	    roomList.add(roomPopup);
	   

	    // Setting object directory
	    objectListLabel = new JLabel(HC.EDITOR_OBJECT_LIST_LABEL);
	    objectListLabel.setBounds(300,30,100,20);
	    objectListPane = new JScrollPane();
	    objectListPane.setBounds(300, 50, 200, 200);
	    editorPanel.add(objectListLabel);
	    editorPanel.add(objectListPane);

	    objectPopup = new JPopupMenu();
	    objectPopupDelete = new JMenuItem(HC.EDITOR_POPUP_DELETE);
	    objectPopupDelete.addActionListener(new RemoveObject());
	    objectPopup.add(objectPopupDelete);
	    objectPopupEdit = new JMenuItem(HC.EDITOR_POPUP_EDIT);
	    objectPopupEdit.addActionListener(new EditObject());
	    objectPopup.add(objectPopupEdit);
	    objectPopupMoveToVoid = new JMenuItem(HC.EDITOR_POPUP_MOVE_TO_VOID);
	    objectPopupMoveToVoid.addActionListener(new MoveToVoid());
	    objectPopup.add(objectPopupMoveToVoid);
	    objectPopupMove = new JMenuItem(HC.EDITOR_POPUP_MOVE);
	    objectPopupMove.addActionListener(new MoveObject());
	    objectPopup.add(objectPopupMove);
	    /*
	    objectPopupLinkDoor = new JMenuItem(HC.EDITOR_POPUP_LINK_DOOR);
	    objectPopupLinkDoor.addActionListener(new LinkDoor());
	    objectPopup.add(objectPopupLinkDoor);
	    */
	    objectList = new JList();
	    objectList.add(objectPopup);
	    
	    
	    // Setting void directory
	    voidListLabel = new JLabel(HC.EDITOR_VOID_LIST_LABEL);
	    voidListLabel.setBounds(50,270,100,20);
	    voidListPane = new JScrollPane();
	    voidListPane.setBounds(50, 300, 200, 200);
	    editorPanel.add(voidListLabel);
	    editorPanel.add(voidListPane);

	    voidPopup = new JPopupMenu();
	    voidPopupDelete = new JMenuItem(HC.EDITOR_POPUP_DELETE);
	    voidPopupDelete.addActionListener(new RemoveObject());
	    voidPopup.add(voidPopupDelete);
	    voidPopupEdit = new JMenuItem(HC.EDITOR_POPUP_EDIT);
	    voidPopupEdit.addActionListener(new EditObject());
	    voidPopup.add(voidPopupEdit);
	    voidPopupMove = new JMenuItem(HC.EDITOR_POPUP_MOVE);
	    voidPopupMove.addActionListener(new MoveObject());
	    voidPopup.add(voidPopupMove);
	    voidList = new JList();
	    voidList.add(voidPopup);
	    


		JButton roomButton = new JButton(HC.EDITOR_ROOM_BUTTON_TEXT);
		roomButton.setBounds(100, 5, 80, 30);
		editorPanel.add(roomButton);
		roomButton.addActionListener(new AddRoom());
		roomButton.setToolTipText(HC.EDITOR_ROOM_BUTTON_TOOLTIP);
		
		JButton objectButton = new JButton(HC.EDITOR_OBJECT_BUTTON_TEXT);
		objectButton.setBounds(200, 5, 80, 30);
		editorPanel.add(objectButton);
		objectButton.addActionListener(new AddObject());
		objectButton.setToolTipText(HC.EDITOR_OBJECT_BUTTON_TOOLTIP);
		
		JButton doorButton = new JButton(HC.EDITOR_DOOR_BUTTON_TEXT);
		doorButton.setBounds(300, 5, 80, 30);
		editorPanel.add(doorButton);
		doorButton.addActionListener(new AddDoor());
		doorButton.setToolTipText(HC.EDITOR_DOOR_BUTTON_TOOLTIP);
		
		JButton saveButton = new JButton(HC.EDITOR_SAVE_BUTTON_TEXT);
		saveButton.setBounds(800, 1, 80, 30);
		editorPanel.add(saveButton);
		saveButton.addActionListener(new Save());
		saveButton.setToolTipText(HC.EDITOR_SAVE_BUTTON_TOOLTIP);
		
		JButton loadButton = new JButton(HC.EDITOR_LOAD_BUTTON_TEXT);
		loadButton.setBounds(700, 1, 80, 30);
		editorPanel.add(loadButton);
		loadButton.addActionListener(new Load());
		loadButton.setToolTipText(HC.EDITOR_LOAD_BUTTON_TOOLTIP);
		
	    // Setting up exit listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		//Setting room chart panel
	    setChartPanel();
	    updateEditor();
		setVisible(true);
	}

	public static void main(String[] args) {
		new WorldEditor("eng");
	}

}