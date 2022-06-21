package view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.WriteClass;

// ID 입력받기 위한 Form
public class IdFrame extends JFrame implements ActionListener {
	public static TextField textIdInput = new TextField(8);

	JButton btn = new JButton("등록");
	JLabel label2;

	ClientFrame cf;
	ClientRoomFrame crf;

	// WriteClass 추가
	WriteClass wc;
	List<String> clientList = WriteClass.clientList;

	public IdFrame(ClientFrame cf, ClientRoomFrame crf) {
		this.cf = cf;
		this.wc = new WriteClass(cf.socket, crf);
		this.crf = crf;

		setTitle("ID input");
		setLayout(null);
//		this.getContentPane().setBackground(new Color(198, 220, 228));

		JLabel label = new JLabel("id:");
		label.setBounds(50, 60, 30, 30);
		add(label);

		textIdInput.setBounds(80, 60, 100, 30);
		add(textIdInput);

		label2 = new JLabel("다른 id를 입력하세요");
		label2.setBounds(50, 20, 30, 30);
		add(label2);
		label2.setVisible(false);

		btn.setBounds(80, 110, 100, 30);
//		btn.setBackground(new Color(255, 230, 230));
		btn.setBorderPainted(false);
		btn.addActionListener(this);
		add(btn);

		setBounds(300, 300, 250, 200);
		setBackground(Color.GREEN);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// id 전송
		wc.sendId(textIdInput.getText());
		
//		boolean idCheck = 
//		if (!idCheck) {
//			label2.setVisible(true);
//		} else {
			label2.setVisible(false);
			// 첫번째 전송
//		cf.isFirst = false;

			// ClientFrame을 시각화
			cf.setVisible(true);

			// 현재창 close
			this.dispose();
//		}

	}
}
