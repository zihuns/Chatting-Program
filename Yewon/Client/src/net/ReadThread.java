package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import view.MainFrame;
import view.RoomFrame;

public class ReadThread extends Thread {

	Socket socket;
	MainFrame mf;
	
	public ReadThread(Socket socket, MainFrame mf) {
		this.socket = socket;
		this.mf = mf;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			while(true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String str = br.readLine();
				if(str == null) {
					System.out.println("연결 끊김");
				}
				System.out.println(str);
				
				String[] msg = str.split("/");
				
				if(msg[0].equals("fsroom")) {
					if(msg.length>1) {
						mf.roomA.setText("");
						String[] rooms = msg[1].split(":");
						for(String name : rooms) {
							mf.roomA.append(name+"\n");
						}
					}
				}
				else if(msg[0].equals("fsmember")) {
					if(msg[1].equals("main")) {
						mf.memberA.setText("");
						if(msg.length>2) {
							String[] members = msg[2].split(":");
							for(String id : members) {
								mf.memberA.append(id+"\n");
							}	
						}
					}
					else {
						List<RoomFrame> rfList = mf.rfList;
						
						for(RoomFrame rf : rfList) {
							if(rf.room.equals(msg[1])) {
						
								rf.memberA.setText("");
								String[] members = msg[2].split(":");
								for(String id : members) {
									rf.memberA.append(id+"\n");
								}
							}
						}
					}
				}
				else if(msg[0].equals("fsmsg")) {
					if(msg[1].equals("main")) {
						mf.textA.append(msg[2]+"\n");
					}
					else {
						List<RoomFrame> rfList = mf.rfList;
						
						for(RoomFrame rf : rfList) {
							if(rf.room.equals(msg[1])) {
								rf.textA.append(msg[2]+"\n");
							}
						}
					}
				}			
				
				Thread.sleep(300);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
