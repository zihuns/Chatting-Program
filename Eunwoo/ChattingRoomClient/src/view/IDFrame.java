package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import data.DataClass;
import data.ManagerClass;
import net.WriteClass;

public class IDFrame extends JFrame implements ActionListener {
	public static TextField tf = new TextField(10);
	JButton btn = new JButton("Login Button");
	
	ManagerClass manager;
	
	JLabel txtLbl;
	
	public IDFrame(ManagerClass manager) {
		this.manager = manager;
		
		setTitle("Login");
		setLayout(null);
		
		JLabel idLbl = new JLabel("ID : ");
		idLbl.setBounds(40, 20, 20, 30);
		add(idLbl);
		
		tf.setBounds(60, 20, 180, 30);
		tf.addActionListener(this);
		add(tf);
		
		txtLbl = new JLabel("");
		txtLbl.setBounds(40, 50, 200, 30);
		add(txtLbl);
		
		btn.setBounds(40, 80, 200, 30);
		btn.addActionListener(this);
		btn.setBackground(UiColor.COLOR3);
		add(btn);
		
		setBounds(300, 300, 300, 170);
		getContentPane().setBackground(UiColor.COLOR_BASE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = tf.getText();
		txtLbl.setText("");
		// check id length
		if(id.length() == 0) {
			txtLbl.setText("id를 입력해주세요");
			return;
		}
		
		if(id.length() > 10) {
			txtLbl.setText("id는 10자를 넘을 수 없습니다.");
			return;
		}
		
		// check if id have regex
		for(int i = 0; i < id.length(); i++) {
			char now = id.charAt(i);
			if(now == '|') {
				txtLbl.setText("'|' 문자는 사용하실 수 없습니다.");
				return;
			}
		}
		
		manager.getDc().setId(id);
		// id전송
		manager.getWc().sendNewClient(id);
		// DlientFrame을 시각화
		manager.getCf().setVisible(true);
		// 현재창 닫기
		this.dispose();
	}
}
