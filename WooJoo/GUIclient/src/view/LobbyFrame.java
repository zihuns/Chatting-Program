package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.WriteClass;

public class LobbyFrame extends JFrame implements ActionListener{
		
	
	public Socket socket;
	public boolean isFirst = true; 
	public boolean isMakeRoom = false;
	public static List<String> IdList = new ArrayList<>();
	
	
	WriteClass wc;
	
	JButton btnMake = new JButton("만들기");
	
	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");
	JButton btnRefresh = new JButton("re");
	
	public static JButton roombtn1[] = new JButton[10];
	
	public static JButton roombtn = new JButton();
	
	
	public JTextField textF = new JTextField(20);
	public JTextArea IdListArea = new JTextArea();
	public JTextArea textA = new JTextArea();
	
	
	JLabel lobby = new JLabel("Lobby");
	JLabel chat = new JLabel("chatting");
	public static JLabel id = new JLabel("");
	JPanel panel = new JPanel();
	public static JPanel roompanel = new JPanel();
	
	
	JButton test = new JButton();
	JButton test2 = new JButton();
	
	
	public LobbyFrame(Socket socket) {
		
		
		this.socket = socket;
		wc = new WriteClass(socket,this);
				
		new IdFrame(wc,this);
		
		setLayout(null);
		setTitle(IdFrame.tf.getText());
		
		
		// 대기실
		lobby.setBounds(320, 0 , 50, 30);
		add(lobby);
		JScrollPane scrPane = new JScrollPane(IdListArea);
		//scrPane.setPreferredSize(new Dimension(100,120));
		scrPane.setBounds(270,25,150,300);
		add(scrPane);
		
		
		//대기실 채팅
		chat.setBounds(20,315,50,30);
		add(chat);
		JScrollPane lobbyPane = new JScrollPane(textA);
		lobbyPane.setBounds(20,340,300,150);
		add(lobbyPane);
		
		
		// 입력창
		//id.setBounds(20,500,20,20);
		panel.add(id);
		panel.add(textF);
		panel.add(btnTransfer);		
		panel.add(btnExit);
		panel.setBounds(30,500,400,100);
		add("South", panel);
		
		
		roompanel.setBackground(Color.DARK_GRAY);
		roompanel.setBounds(15, 25, 250, 300);
		add(roompanel);
		for(int i=0; i<10; i++) {
			roombtn1[i] = new JButton();
			roombtn1[i].setText("test");
			roombtn1[i].setBackground(Color.LIGHT_GRAY);
			roompanel.add(roombtn1[i]);
			roombtn1[i].setVisible(false);
			roombtn1[i].addActionListener(this);
		}
		
		
		btnMake.setBounds(330,380,80,80);
		add(btnMake);
		btnRefresh.setBounds(380,330,40,40);
		add(btnRefresh);
		
		
		
		
		btnTransfer.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnMake.addActionListener(this);
		roombtn.addActionListener(this);
		
		
		
		
		
		
		setBounds(200,200,450,600);
		setVisible(false);
				
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnTransfer || obj == textF) {
			
			if(textF.getText().trim().equals("")) return;
			
			String id = IdFrame.tf.getText();
			
			textA.append("[" + id + "]" + textF.getText() + "\n");
			
			//server 전송
			wc.sendMessage();
			
			textF.setText("");
		}
		
		if(obj == btnExit) {
			System.exit(1);
			this.dispose();
		}
		
		if(obj == btnRefresh) {
			
			
			wc.refresh();
			
		}
		
		
		
		
		if(obj == btnMake) {
			
			String[] num = {"2","3","4","5","6"};
			String input = (String)JOptionPane.showInputDialog(null,"제한인원을 선택하세요.", "방 만들기", JOptionPane.QUESTION_MESSAGE, null, num,num[1]);
			
			// 방 만들면 -> 서버에 알림 -> 서버에서 나머지 클라에게 방 만들어짐 알림 
			new RoomFrame(this,this.socket).setTitle(IdFrame.tf.getText());
			wc.sendMakeRoom(input);
			
			
			
			setVisible(false);
		}
		
		if(obj == this.roombtn) {
			// 방 리스트 참가 -> 방 로비 인원 업데이트 . . . 
			
			int ans = JOptionPane.showConfirmDialog(null,"입장하시겠습니까?",btnMake.getText()+"님의 방",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
			
			if(ans == 0) {
				wc.JoinRoom();
				new RoomFrame(this,this.socket).setTitle(IdFrame.tf.getText());
				setVisible(false);
				
			}else {
			}
		}
		
		
		if(obj == this.roombtn1[0] || obj == this.roombtn1[1] || obj == this.roombtn1[2] || obj == this.roombtn1[3] || obj == this.roombtn1[4] ||
				obj == this.roombtn1[5] || obj == this.roombtn1[6] || obj == this.roombtn1[7] || obj == this.roombtn1[8] || obj == this.roombtn1[9] ) {
			int ans = JOptionPane.showConfirmDialog(null,"입장하시겠습니까?",btnMake.getText()+"님의 방",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
			
			if(ans == 0) {
				wc.JoinRoom();
				new RoomFrame(this,this.socket).setTitle(IdFrame.tf.getText());
				setVisible(false);
			}
		}
		
		
		
	}
	
}
