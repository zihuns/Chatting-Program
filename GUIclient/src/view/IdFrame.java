package view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.WriteClass;

// 사용자 id를 입력받기 위한 Form
public class IdFrame extends JFrame implements ActionListener{

	
	public static TextField tf = new TextField(8);
	JButton btn = new JButton("등록");
	
	ClientFrame cf;
	LobbyFrame lf;
	
	//writeClass 추가
	WriteClass wc;
	
	public IdFrame(WriteClass wc, ClientFrame cf) {
		
		this.cf = cf;
		this.wc = wc;
		
		
		setTitle("ID input");
		setLayout(null);
		
		JLabel label = new JLabel("ID:");
		label.setBounds(50,60,30,30);
		add(label);
		
		tf.setBounds(80, 60, 100, 30);
		add(tf);
		
		btn.setBounds(80,110,100,30);
		btn.addActionListener(this);
		add(btn);
	
		setBounds(300,300,250,200);
		setBackground(Color.GREEN);
		setVisible(true);
		
		
	}
	
	public IdFrame(WriteClass wc, LobbyFrame lf) {
		
		this.lf = lf;
		this.wc = wc;
		
		
		setTitle("ID input");
		setLayout(null);
		
		JLabel label = new JLabel("ID:");
		label.setBounds(50,60,30,30);
		add(label);
		
		tf.setBounds(80, 60, 100, 30);
		add(tf);
		
		btn.setBounds(80,110,100,30);
		btn.addActionListener(this);
		add(btn);
	
		setBounds(300,300,250,200);
		setBackground(Color.GREEN);
		setVisible(true);
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// id 전송 
		
		
		wc.sendMessage();
		LobbyFrame.IdList.add(tf.getText());
		lf.setTitle(tf.getText() + " 전체 채팅방");
		lf.id.setText(IdFrame.tf.getText());
		
		// 첫번째 전송
		lf.isFirst = false;
		lf.setVisible(true);
		//ClientFrame 을 시각화
		
		// 현재창 닫기
		this.dispose();
		
	}
	
	
	
	
}
