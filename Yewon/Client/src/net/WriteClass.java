package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import view.IdFrame;
import view.MainFrame;
import view.RoomFrame;

public class WriteClass {

	Socket socket;
	MainFrame mf;
	RoomFrame rf;
	String id;
	
	public WriteClass(Socket socket, MainFrame mf) {
		this.socket = socket;
		this.mf = mf;
	}
	
	public WriteClass(Socket socket, RoomFrame rf) {
		this.socket = socket;
		this.rf = rf;
	}
	
	public void sendMessage(boolean isMsg, boolean exit) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			
			String msg = "";
			
			//메인 채팅방
			if(mf != null) {
				//첫번쨰 전송
				if(mf.isFirst) {
					InetAddress iaddr = socket.getLocalAddress();
					String ip = iaddr.getHostAddress(); 
					id = mf.id;
					
					msg = "enter/"+mf.room+"/"+id+"/"+ip; //chat+member
				}
				//그외 전송
				else {
					if(isMsg) {
						msg = "msg/"+mf.room+"/"+id+"/"+mf.textF.getText(); //chat
					}
					else if(exit) {
						msg = "exit/"+mf.room+"/"+id; //나가기
					}
					else {
						msg = "room/"+mf.roomF.getText()+"/"+id; //room생성
					}
				}
			}
			//그룹 채팅방
			else {
				if(isMsg) {
					msg = "msg/"+rf.room+"/"+rf.id+"/"+rf.textF.getText(); //chat
				}
				else if(exit) {
					msg = "exit/"+rf.room+"/"+rf.id; //나가기
				}
			}
			
			//server 전송 
			pw.println(msg);
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
