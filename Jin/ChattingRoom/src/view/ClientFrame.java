package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.WriteClass;

// 채팅 form
public class ClientFrame extends JFrame implements WindowListener, ActionListener {

	public Socket socket;
	WriteClass wc;
	public ClientRoomFrame crf;
	List<String> clientList = WriteClass.clientList;

	JTable table1, table2;
	public DefaultTableModel model1, model2;
	JTextField textRoomNameInput;
	JTextArea textArea;
	JButton btnCreateRoom, btnEnter, btnExit;
	JScrollBar bar;

	public static String roomName = "";

	public ClientFrame(Socket socket) {
		super("로비"); // 타이틀 , = setTitle();

		this.socket = socket;
		this.wc = new WriteClass(socket, this);

		crf = new ClientRoomFrame(socket);

		new IdFrame(this, crf);
		
//		this.getContentPane().setBackground(new Color(198, 220, 228));

		String[] col1 = { "방이름", "ID" };
		String[][] row1 = new String[0][2];

		model1 = new DefaultTableModel(row1, col1) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		table1 = new JTable(model1);

		table1.getTableHeader().setReorderingAllowed(false);
		table1.getTableHeader().setResizingAllowed(false);
		
		String[] col2 = { "접속중인 id"};
		String[][] row2 = new String[0][1];
		
		model2 = new DefaultTableModel(row2, col2) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		table2 = new JTable(model2);

		table2.getTableHeader().setReorderingAllowed(false);
		table2.getTableHeader().setResizingAllowed(false);

		JScrollPane roomsPane = new JScrollPane(table1);
		JScrollPane usersPane = new JScrollPane(table2);

		textRoomNameInput = new JTextField();
		roomName = textRoomNameInput.getText();

		btnCreateRoom = new JButton("방 생성");
//		btnCreateRoom.setBackground(new Color(242, 209, 209));
		btnCreateRoom.setBorderPainted(false);
		btnCreateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textRoomNameInput.getText().trim().equals(""))
					return;
				ClientRoomFrame.roomName = textRoomNameInput.getText();
				crf.setTitle("chatting room: " + ClientRoomFrame.roomName);
				crf.textChatArea.setText("");
				crf.textInput.setText("");
				wc.sendRoomInfo(ClientRoomFrame.roomName, 1); // 1 = 아마도 새방
				textRoomNameInput.setText("");
				crf.setVisible(true);
			}
		});

		btnEnter = new JButton("입장");
//		btnEnter.setBackground(new Color(242, 209, 209));
		btnEnter.setBorderPainted(false);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table1.getSelectedRow();
				if (row == -1) {
					return;
				}
				ClientRoomFrame.roomName = table1.getModel().getValueAt(row, 0).toString();
				crf.setTitle("chatting room: " + ClientRoomFrame.roomName);
				crf.textChatArea.setText("");
				crf.textInput.setText("");
				wc.sendRoomInfo(ClientRoomFrame.roomName, 0); // 0 = 입장
				textRoomNameInput.setText("");
				crf.setVisible(true);
			}
		});
		
		btnExit = new JButton("종료");
		btnExit.setBackground(new Color(244, 124, 124));
		btnExit.setBorderPainted(false);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wc.Exit();
				System.exit(0);
			}
		});

		// 배치
		setLayout(null);
		roomsPane.setBounds(10, 15, 300, 300);
		add(roomsPane);
		
		usersPane.setBounds(10, 315, 300, 100);
		add(usersPane);

		textRoomNameInput.setBounds(10, 420, 300, 30);
		add(textRoomNameInput);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3, 20, 20));

		buttonPanel.add(btnCreateRoom);
		buttonPanel.add(btnEnter);
		buttonPanel.add(btnExit);
		buttonPanel.setBounds(10, 460, 300, 50);
		add(buttonPanel);

		setBounds(200, 200, 340, 570);

		setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
		wc.Exit();
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
