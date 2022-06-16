package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.WriteClass;

public class RoomFrame extends JFrame implements WindowListener, ActionListener {

	public Socket socket;
	WriteClass wc;
	MainFrame mf;
	
	public JTextField textF = new JTextField(20);
	public JTextArea textA = new JTextArea();
	
	public JTextArea memberA = new JTextArea();
	
	JLabel title;
	JLabel label = new JLabel("현재 멤버");
	
	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");
	
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	
	public boolean isFirst = true;
	public String room;
	public String id;
	
	public RoomFrame(Socket socket, MainFrame mf, String roomName) {		
		super("lobby");
		setLayout(null);
		
		this.socket = socket;
		this.mf = mf;
		this.room = roomName;
		this.id = mf.id;
		
		wc = new WriteClass(socket, this);
		
		title = new JLabel(room);
		title.setBounds(400, 30, 100, 10);
		add(title);
		
		JScrollPane scrPane = new JScrollPane(textA);
		scrPane.setPreferredSize(new Dimension(350, 380));
		scrPane.setBounds(50, 60, 350, 380);
		add(scrPane);
		
		panel1.add(textF);
		panel1.add(btnTransfer);
		panel1.setBounds(50, 450, 350, 40);
		add(panel1);
		
		label.setBounds(590, 60, 100, 40);
		add(label);
		
		JScrollPane scrPane2 = new JScrollPane(memberA);
		scrPane2.setPreferredSize(new Dimension(350, 350));
		scrPane2.setBounds(440, 90, 350, 350);
		add(scrPane2);
		
		
		btnExit.setBounds(395, 510, 60, 30);
		add(btnExit);
		
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		
		setBounds(200, 230, 850, 600);
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
		else if(obj == btnExit) {
			wc.sendMessage(false, true);
			mf.setVisible(true);
			this.dispose();
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
