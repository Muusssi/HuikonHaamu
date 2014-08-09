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

	JPanel editorPanel;

	Room testRoom;

	//Roomlist
	JLabel roomListLabel;
	JScrollPane roomListPane;
	JList roomList;
	JPopupMenu roomPopup;
    JMenuItem roomPopupDelete;
	//Objectlist
	JLabel objectListLabel;
	JScrollPane objectListPane;
	JList objectList;
	JPopupMenu objectPopup;
    JMenuItem objectPopupDelete;
	//chart
    JPanel chartPane = null;


	GameWorld gameWorld;
	WorldEditor wegui;

	private void setChartPanel() {

		chartPane = new JPanel();
		chartPane.setBounds(530, 30, 230, 250);
		chartPane.setLayout(null);
		RoomChart.setRoomChart(chartPane, testRoom);
		chartPane.revalidate();
		editorPanel.add(chartPane);
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
			JLabel nameLabel = new JLabel(HC.ROOM_EDITOR_NAME_LABEL);
			nameLabel.setToolTipText(HC.ROOM_EDITOR_NAME_TOOLTIP);

			JLabel descLabel = new JLabel(HC.ROOM_EDITOR_DESCRIPTION_LABEL);
			descLabel.setToolTipText(HC.ROOM_EDITOR_DESCRIPTION_TOOLTIP);

			JLabel codeLabel = new JLabel(HC.ROOM_EDITOR_CODE_LABEL);
			codeLabel.setToolTipText(HC.ROOM_EDITOR_CODE_TOOLTIP);

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
						"New Room", JOptionPane.OK_CANCEL_OPTION);
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
						else if (!gameWorld.gameThings.containsKey(roomCode)) {
							JOptionPane.showMessageDialog(null,
									HC.ROOM_EDITOR_CODE_TAKEN);
						}
						else if (xdim < 2 || xdim > 7 || ydim < 2 || ydim > 7) {
							JOptionPane.showMessageDialog(null,
									HC.ROOM_EDITOR_DIM_OUT_OF_BOUNDS);
						}
						else {
							new Room(gameWorld, roomName, roomDesc, roomCode, xdim, ydim);
							roomOK = true;
							//updateThingList();
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
						Object o = theList.getModel().getElementAt(index);
						System.out.println( o.toString());
						roomPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
					}
				}
				else if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						System.out.println( o.toString()+ " vasemmalla");
						String roomCode = o.toString().split(":")[0];
						Room room = gameWorld.roomMap.get(roomCode);
						RoomChart.setRoomChart(chartPane, room);
						updateObjectList(room);
					}
				}
			}
	    };
	    roomList.addMouseListener(mouseListener);
    }

	public void updateObjectList(Room room) {
		objectList = new JList(room.getObjectArrayForEditor());
		objectListPane.getViewport().add(objectList);
		editorPanel.add(objectListPane);

		// Adding a mouselistener for Room objects window, to examine right clicks
		// and on which objects they were made
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						System.out.println( o.toString());
						objectPopup.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
					}
				}
			}
		};
	    objectList.addMouseListener(mouseListener);
    }

	public WorldEditor() {
		wegui = this;
		gameWorld = new GameWorld("","eng");
		try {
			testRoom = new Room(gameWorld, "Makuuhuone", null, null, 7, 7);
			new GameObject(gameWorld, "tuoli", null, null, testRoom, 0);
			new GameObject(gameWorld, "pyötä", null, null, testRoom, 1);
			new GameObject(gameWorld, "kirja", null, null, testRoom, 2);
			new GameObject(gameWorld, "laukku", null, null, testRoom, 3);
			Room otherRoom = new Room(gameWorld, "Toinen huone", null, null, 4, 4);
			new GameObject(gameWorld, "hattu", null, null, otherRoom, 6);
		} catch (IllegalGameCodeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WorldMakingConflict e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


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
	    roomPopupDelete = new JMenuItem("Delete");
	    roomPopup.add(roomPopupDelete);
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
	    objectPopupDelete = new JMenuItem("Delete");
	    objectPopup.add(objectPopupDelete);
	    objectList = new JList();
	    objectList.add(objectPopup);

	    //Setting room chart
	    setChartPanel();


		JButton button = new JButton(HC.EDITOR_ROOMS_BUTTON_TEXT);
		button.setBounds(400, 10, 80, 30);
		editorPanel.add(button);
		button.addActionListener(new AddRoom());
		button.setToolTipText(HC.EDITOR_ROOMS_BUTTON_TOOLTIP);

	    // Setting up exit listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		updateRoomList();
		updateObjectList(testRoom);
		setVisible(true);
	}

	public static void main(String[] args) {
		new WorldEditor();
	}

}