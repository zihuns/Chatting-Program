package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import view.ClientFrame;
import view.IdFrame;
import view.LobbyFrame;
import view.RoomFrame;

public class WriteClass {
	Socket socket;
	ClientFrame cf;
	LobbyFrame lf;
	RoomFrame rf;

	
	
	public WriteClass(Socket socket, ClientFrame cf) {
		this.socket = socket;
		this.cf = cf;
	}
	public WriteClass(Socket socket, LobbyFrame lf) {
		this.socket = socket;
		this.lf = lf;
	}
	public WriteClass(Socket socket, RoomFrame rf) {
		this.socket = socket;
		this.rf = rf;
	}
	
	
	public void sendMessage() {
		try {
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			
			String msg = "";
			String id = IdFrame.tf.getText();
			// 첫번째 전송 = ID 전송 
			if(lf.isFirst) {
				InetAddress iaddr = socket.getLocalAddress();
				String ip = iaddr.getHostAddress(); //
				msg = "[" + id + "]님 로그인(" + ip + ")";
			}
			// 그 외의 전송
			else {
				msg = "[" + id + "]" + lf.textF.getText(); 
				
			}
			
			
			// server 전송
			msg = "sendMessage" + msg;
			pw.println(msg);
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendRoomMessage() {
		try {
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			
			String msg = "";
			String id = IdFrame.tf.getText();
			msg =  "sendRoom"     +"[" + id + "]" + rf.textF.getText(); 
			
			// server 전송
			// System.out.println(msg);
			
			pw.println(msg);
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void sendMakeRoom(String num) {
		
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			
			String msg = "";
			String id = IdFrame.tf.getText();
			
			msg =  "{"+IdFrame.tf.getText() + "]"  +"make-room " + num;
			
			
			pw.println(msg);
			pw.flush();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void JoinRoom() {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			String msg = "";
			String id = IdFrame.tf.getText();
			
			
			msg = "JoinRoom " + id ;
			
			
			System.out.println(msg);
			pw.println(msg);
			pw.flush();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void exitRoom() {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			String msg = "";
			String id = IdFrame.tf.getText();
			
			
			msg = "RoomExit " + id ;
			
			pw.println(msg);
			pw.flush();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void refresh() {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			String msg = "";
			msg = "RefreshLobby";
			
			
			pw.println(msg);
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
