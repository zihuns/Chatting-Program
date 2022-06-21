package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.WriteClass;

public class MainFrame extends JFrame implements WindowListener, ActionListener {
	
	public Socket socket;
	WriteClass wc;
	
	public JTextField textF = new JTextField(20);
	public JTextArea textA = new JTextArea();
	
	public JTextArea memberA = new JTextArea();
	public JTextArea roomA = new JTextArea();
	public JTextField roomF = new JTextField(20);
	
	JLabel labelLobby = new JLabel("LOBBY");
	JLabel labelMember = new JLabel("현재 멤버");
	JLabel labelRoom = new JLabel("채팅방");
	
	JButton btnTransfer = new JButton("send");
	JButton btnRoom = new JButton("make room");
	JButton btnExit = new JButton("exit");
	
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	
	public boolean isFirst = true;
	public String room = "main";
	public String id;
	
	public List<RoomFrame> rfList;
	
	public MainFrame(Socket socket, List<RoomFrame> rfList) {
		super("Lobby");
		setLayout(null);
		
		this.socket = socket;
		this.rfList = rfList;
		
		wc = new WriteClass(socket, this);
		new IdFrame(wc, this);
		
		labelLobby.setBounds(400, 20, 100, 20);
		add(labelLobby);
		
		JScrollPane scrPane = new JScrollPane(textA);
		scrPane.setPreferredSize(new Dimension(350, 370));
		scrPane.setBounds(50, 60, 350, 370);
		add(scrPane);
		
		panel1.add(textF);
		panel1.add(btnTransfer);
		panel1.setBounds(50, 440, 350, 40);
		add(panel1);
		
		labelMember.setBounds(590, 50, 100, 40);
		add(labelMember);
		
		JScrollPane scrPane2 = new JScrollPane(memberA);
		scrPane2.setPreferredSize(new Dimension(350, 350));
		scrPane2.setBounds(440, 80, 350, 350);
		add(scrPane2);
		
		panel2.add(roomF);
		panel2.add(btnRoom);
		panel2.setBounds(440, 440, 350, 40);
		add(panel2);
		
		labelRoom.setBounds(400, 500, 50, 20);
		add(labelRoom);
		
		JScrollPane scrPane3 = new JScrollPane(roomA);
		scrPane3.setPreferredSize(new Dimension(350, 350));
		scrPane3.setBounds(50, 530, 730, 180);
		add(scrPane3);
		
		btnExit.setBounds(395, 730, 60, 30);
		add(btnExit);
		
		btnTransfer.addActionListener(this);
		btnRoom.addActionListener(this);
		btnExit.addActionListener(this);
		
		setBounds(200, 200, 850, 830);
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == btnTransfer || obj == textF) {
			if(textF.getText().trim().equals("")) {
				return;
			}
			
			wc.sendMessage(true, false);
			textF.setText("");
		}
		else if(obj == btnRoom) {
			if(roomF.getText().trim().equals("")) {
				return;
			}
			String roomName = roomF.getText();
			
			boolean isExist = false;
			for(RoomFrame rf : rfList) {
				if(rf.room.equals(roomName)) {
					rf.setVisible(true);
					isExist = true;
					break;
				}
			}			
			if(!isExist) {
				RoomFrame newRF = new RoomFrame(socket, this, roomName);
				rfList.add(newRF);
				newRF.setVisible(true);
			}
			
			wc.sendMessage(false, false);
			roomF.setText("");		

			setVisible(false);
		}
		else if(obj == btnExit) {
			wc.sendMessage(false, true);
			System.exit(0);
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
