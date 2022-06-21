package view;

import java.awt.Color;
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
public class ClientRoomFrame extends JFrame implements WindowListener, ActionListener {

	public Socket socket;
	WriteClass wc;

	public static String roomName = "";

	public JTextField textInput = new JTextField(20);
	public JTextArea textChatArea = new JTextArea();

	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");

	JPanel panel = new JPanel();

	public boolean isFirst = true; // 첫번째 전송

	public ClientRoomFrame(Socket socket) {
		super("chatting room: " + roomName); // 타이틀 , = setTitle();

		this.socket = socket;
		this.wc = new WriteClass(socket, this);

//		this.getContentPane().setBackground(new Color(198, 220, 228));

		JScrollPane scrPane = new JScrollPane(textChatArea);
		scrPane.setPreferredSize(new Dimension(200, 120));

		add("Center", scrPane);

		panel.add(textInput);
		panel.add(btnTransfer);

		btnExit.setBackground(Color.LIGHT_GRAY);

		panel.add(btnExit);

		add("South", panel);

		btnTransfer.addActionListener(this);
//		btnTransfer.setBackground(new Color(255, 230, 230));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textChatArea.setText("");
				textInput.setText("");
				wc.ExitRoom(roomName);
				setVisible(false);
			}
		});
		btnExit.setBackground(new Color(244, 124, 124));
		btnTransfer.setBorderPainted(false);
		btnExit.setBorderPainted(false);

		setBounds(400, 300, 450, 600);

		setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnTransfer) { // || obj == textInput
			if (textInput.getText().trim().equals(""))
				return;

			String id = IdFrame.textIdInput.getText();

//			textChatArea.append("[" + id + "]" + textInput.getText() + "\n");

			// server 전송
			wc.sendMessage(roomName);

			textInput.setText("");
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
		textChatArea.setText("");
		wc.ExitRoom(roomName);
		setVisible(false);

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
