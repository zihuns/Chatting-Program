package view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.WriteClass;

public class IdFrame extends JFrame implements ActionListener {

	public static TextField tf = new TextField(8);
	JButton btn = new JButton("입력");
	
	MainFrame mf;
	WriteClass wc;
	
	public IdFrame(WriteClass wc, MainFrame mf) {
		this.wc = wc;
		this.mf = mf;
		
		setTitle("ID input");
		setLayout(null);
		
		JLabel label = new JLabel("id :");
		label.setBounds(50, 60, 30, 30);
		add(label);
		
		tf.setBounds(80, 60, 100, 30);
		add(tf);
		
		btn.setBounds(80, 110, 100, 30);
		btn.addActionListener(this);
		add(btn);
		
		setBounds(300, 300, 250, 200);
		setBackground(Color.green);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		mf.id = tf.getText();
		
		wc.sendMessage(false, false);
		
		mf.isFirst = false;
		mf.setVisible(true);
		
		this.dispose();
		
	}

}
