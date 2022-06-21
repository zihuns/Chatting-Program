package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.Border;

import data.ManagerClass;
import net.WriteClass;

// 채팅받기위한 Form
public class ClientFrame extends JFrame implements WindowListener, ActionListener {

	ManagerClass manager;
	WriteClass wc;
	RoomListFrame rf;
	
	public Socket socket;
	
	String id;
	public JTextField chatField = new JTextField(20);
	public JTextArea textA = new JTextArea();
	public JTextArea userA = new JTextArea();
	
	public JLabel[] MyInfo = new JLabel[2];
	public JLabel[] RoomInfo = new JLabel[2];
	
	JButton btnSend = new JButton("Send Button");
	JButton btnMove = new JButton("Move Room Button");
	
	JPanel panel = new JPanel();
	
	public ClientFrame(ManagerClass manager) {
		super("Chatting");
		this.manager = manager;
		this.wc = manager.getWc();
		new IDFrame(manager);
		this.rf = manager.getRf();
		
		setTitle("Chatting Room");
		setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.black);
		
		JLabel chatAreaInfo = new JLabel("Chatting Area", SwingConstants.CENTER);
		chatAreaInfo.setBounds(50,50,1000,30);
		chatAreaInfo.setBorder(border);
		chatAreaInfo.setBackground(UiColor.COLOR2);
		chatAreaInfo.setOpaque(true);
		add(chatAreaInfo);
		
		textA.setBounds(50, 80, 1000, 500);
		textA.setBorder(border);
		add(textA);
		
		chatField.setBounds(50, 600, 850, 30);
		chatField.setBorder(border);
		add(chatField);
		chatField.addActionListener(this);
		
		btnSend.setBounds(900, 600, 150, 30);
		btnSend.setBackground(UiColor.COLOR3);
		add(btnSend);
		btnSend.addActionListener(this);
		
		MyInfo[0] = new JLabel("User Information", SwingConstants.CENTER);
		MyInfo[0].setBounds(1080, 50, 150, 30);
		MyInfo[0].setBorder(border);
		MyInfo[0].setBackground(UiColor.COLOR1);
		MyInfo[0].setOpaque(true);
		add(MyInfo[0]);
		
		MyInfo[1] = new JLabel("", SwingConstants.CENTER);
		MyInfo[1].setBounds(1080, 80, 150, 30);
		MyInfo[1].setBorder(border);
		MyInfo[1].setBackground(Color.white);
		MyInfo[1].setOpaque(true);
		add(MyInfo[1]);
		
		RoomInfo[0] = new JLabel("Room Name", SwingConstants.CENTER);
		RoomInfo[0].setBounds(1080, 125, 150, 30);
		RoomInfo[0].setBorder(border);
		RoomInfo[0].setBackground(UiColor.COLOR1);
		RoomInfo[0].setOpaque(true);
		add(RoomInfo[0]);
		
		RoomInfo[1] = new JLabel("", SwingConstants.CENTER);
		RoomInfo[1].setBounds(1080, 155, 150, 30);
		RoomInfo[1].setBorder(border);
		RoomInfo[1].setBackground(Color.white);
		RoomInfo[1].setOpaque(true);
		add(RoomInfo[1]);
		
		JLabel RoomUserLbl = new JLabel("Room User", SwingConstants.CENTER);
		RoomUserLbl.setBounds(1080,200,150,30);
		RoomUserLbl.setBorder(border);
		RoomUserLbl.setBackground(UiColor.COLOR2);
		RoomUserLbl.setOpaque(true);
		add(RoomUserLbl);
		
		userA.setBounds(1080, 230, 150, 350);
		userA.setBorder(border);
		add(userA);
		
		btnMove.setBounds(1080, 600, 150, 30);
		btnMove.setBackground(UiColor.COLOR3);
		add(btnMove);
		btnMove.addActionListener(this);
		
		getContentPane().setBackground(UiColor.COLOR_BASE);
		setBounds(200, 200, 1280, 720);
		setVisible(false);
	}
	
	public void initUserA() {
		userA.setText("");
	}
	
	public void appendUserA(String userId) {
		userA.append(" "+userId+"\n");
	}
	
	public void setUserInfo(String userId) {
		MyInfo[1].setText("ID : "+userId);
	}
	
	public void setRoomInfo(String roomName) {
		RoomInfo[1].setText(roomName);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnSend || obj == chatField) {
			if(chatField.getText().trim().equals("")) return;
			
			id = IDFrame.tf.getText();
			
			wc.sendMessage(chatField.getText());
			
			chatField.setText("");
			return;
		}
		if(obj == btnMove) {
			wc.sendGetRoomList();
			manager.getRf().setVisible(true);
			return;
		}
		
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		wc.sendQuit(id);
		
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		wc.sendQuit(id);
		
		System.exit(0);

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	

}
