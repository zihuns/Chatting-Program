package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.RoomReadThread;
import net.WriteClass;

public class  RoomFrame extends JFrame implements ActionListener {
	
	LobbyFrame lf;
	WriteClass wc2;
	Socket socket;
	
	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");
	
	
	
	public JTextField textF = new JTextField(20);
	public JTextArea IdListArea = new JTextArea();
	public static List<String> IdList = new ArrayList<>();
	public JTextArea textA = new JTextArea();
	
	JLabel lobby = new JLabel("참가인원");
	JLabel chat = new JLabel("채팅");
	
	JPanel panel = new JPanel();
	
	static RoomReadThread r1;
	
	public RoomFrame(LobbyFrame lf,Socket socket)  {
		this.lf = lf;
		//this.wc = wc;
		this.socket = socket;
		
		
		wc2 = new WriteClass(socket,this);
		
		r1 = new RoomReadThread(socket,this);
		r1.start();
		 
		 
		setLayout(null);
		
		
		// 대기실
				lobby.setBounds(300,375 , 60 , 30);
				add(lobby);
				JScrollPane scrPane = new JScrollPane(IdListArea);
				//scrPane.setPreferredSize(new Dimension(100,120));
				scrPane.setBounds(300,400,100,100);
				
				add(scrPane);
				
				//대기실 채팅
				chat.setBounds(18,5,50,30);
				add(chat);
				
				JScrollPane lobbyPane = new JScrollPane(textA);
				lobbyPane.setBounds(20,30,400,350);
				
				
				
				add(lobbyPane);
				
				panel.add(lf.id);
				panel.add(textF);
				panel.add(btnTransfer);		
				panel.add(btnExit);
				panel.setBounds(30,500,400,100);
				add("South", panel);
				
				btnTransfer.addActionListener(this);
				btnExit.addActionListener(this);
				
				setBounds(200,200,450,600);
				setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnTransfer || obj == textF) {
			
			if(textF.getText().trim().equals("")) return;
			
			String id = IdFrame.tf.getText();
			
			textA.append("[" + id + "]" + textF.getText() + "\n");
			
			//server 전송
			wc2.sendRoomMessage();
			
			textF.setText("");
			
		}
			
			if(obj == btnExit) {
				r1.interrupt();
				wc2.exitRoom();
				this.dispose();
				lf.setVisible(true);
			}
			
			
	}

}
