package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.WriteClass;

// 채팅 form
public class ClientFrame extends JFrame implements WindowListener , ActionListener{
	
	
	
	public Socket socket;
	WriteClass wc;
	
	public JTextField textF = new JTextField(20);
	public JTextArea textA = new JTextArea();
	
	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");
	
	
	JPanel panel = new JPanel();
	
	public boolean isFirst = true; // 첫 번째 전송
	
	
	
	public ClientFrame(Socket socket) {
		super("chatting");
		this.socket = socket;
		
		wc = new WriteClass(socket,this);
		
		new IdFrame(wc,this);
		
		JScrollPane scrPane = new JScrollPane(textA);
		scrPane.setPreferredSize(new Dimension(200,120));
		
		add("Center",scrPane);
		
		panel.add(textF);
		panel.add(btnTransfer);		
		panel.add(btnExit);
		
		add("South", panel);
		
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		
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
