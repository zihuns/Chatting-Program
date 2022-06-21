package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import data.DataClass;
import data.ManagerClass;
import data.Room;

public class RoomListFrame extends JFrame implements ActionListener {
	
	ManagerClass manager;
	
	JButton refreshBtn, goBtn, createBtn;
	public JComboBox<String> roomCmb;
	JTextField roomNameTf;
	JLabel noticeLbl;
	JLabel roomNameLbl[] = new JLabel[2];
	
	public RoomListFrame(ManagerClass manager) {
		this.manager = manager;
		setTitle("Move Room");
		setLayout(null);
		
		roomCmb = new JComboBox<>();
		roomCmb.setBounds(50, 30, 280, 20);
		add(roomCmb);
		
		refreshBtn = new JButton("Refresh Button");
		refreshBtn.setBounds(350, 30, 150, 50);
		refreshBtn.addActionListener(this);
		refreshBtn.setBackground(UiColor.COLOR3);
		add(refreshBtn);
		
		goBtn = new JButton("Go Room Button");
		goBtn.setBounds(350, 90, 150, 50);
		goBtn.addActionListener(this);
		goBtn.setBackground(UiColor.COLOR3);
		add(goBtn);
		
		roomNameLbl[0] = new JLabel("Do you want to Create room?");
		roomNameLbl[0].setBounds(50, 200, 400, 20);
		add(roomNameLbl[0]);
		roomNameLbl[1] = new JLabel("Input room name and press Create button.");
		roomNameLbl[1].setBounds(50, 220, 400, 20);
		add(roomNameLbl[1]);
		
		roomNameTf = new JTextField(10);
		roomNameTf.setBounds(50, 250, 280, 30);
		add(roomNameTf);
		
		createBtn = new JButton("Create Button");
		createBtn.setBounds(350, 250, 150, 30);
		createBtn.addActionListener(this);
		createBtn.setBackground(UiColor.COLOR3);
		add(createBtn);
		
		setBounds(350, 350, 550, 360);
		getContentPane().setBackground(UiColor.COLOR_BASE);
		setVisible(false);
	}
	
	public void refreshRoomCmb() {
		roomCmb.removeAllItems();
	}
	
	public void addItemRoomCmb(String roomInfo) {
		roomCmb.addItem(roomInfo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == refreshBtn) {
			manager.getWc().sendGetRoomList();
			return;
		}
		
		if(obj == goBtn) {
			//Select room number
			String selected = (String)roomCmb.getSelectedItem();
			String[] temp = selected.split("\\[");
			int selectRoomNumber = Integer.parseInt(temp[0]);
			
			// check different with now room
			if(!manager.getDc().getNowRoomNumber().equals(selectRoomNumber)) {
				manager.getWc().sendMoveRoom(selectRoomNumber);
			}
			
			this.setVisible(false);
			return;
		}
		
		if(obj == createBtn) {
			String roomName = roomNameTf.getText();
			// check id length
			if(roomName.length() < 2) {
				roomNameLbl[1].setText("방 이름을 2글자 이상 입력해주세요");
				this.add(roomNameLbl[1]);
				return;
			}
			
			if(roomName.length() > 10 || roomName.length() < 2) {
				roomNameLbl[1].setText("방 이름은 10글자를 넘을 수 없습니다.");
				this.add(roomNameLbl[1]);
				return;
			}
			
			// check if id have regex
			for(int i = 0; i < roomName.length(); i++) {
				char now = roomName.charAt(i);
				if(now == '|') {
					roomNameLbl[1].setText("'|' 문자는 사용하실 수 없습니다.");
					this.add(roomNameLbl[1]);
					return;
				}
			}
			manager.getWc().sendCreateRoom(roomName);
			this.setVisible(false);
			return;
		}
		
		return;
	}
	
}
