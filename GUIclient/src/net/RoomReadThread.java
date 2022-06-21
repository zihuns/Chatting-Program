package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import view.ClientFrame;
import view.LobbyFrame;
import view.RoomFrame;

public class RoomReadThread extends Thread{
	
	Socket socket;
	ClientFrame cf;
	LobbyFrame lf;
	RoomFrame rf;
	List<String> IdList = new ArrayList<>();
	
	
	public RoomReadThread(Socket socket,RoomFrame rf) {
		this.socket = socket;
		this.rf = rf;
	}

	@Override
	public void run() {
		super.run();
		try {
		while(true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String str = br.readLine();
				if(str == null) {
					System.out.println("접속끊김");
					break;
					
				}
				
				else if(str.substring(0,8).equals("RoomExit")) {
					str = str.substring(8);
					String name = str.substring(1,str.indexOf("]"));
					
					RoomFrame.IdList.remove(name);
					rf.IdListArea.setText("");
					for(String s:RoomFrame.IdList) {
						rf.IdListArea.append(s + "\n");
					}
					
					rf.textA.append(str + "\n");
					lf.roombtn.removeAll();
					lf.roombtn.setVisible(false);
					
					
					//lf.remove(lf.roombtn);
					
				}
				else if(str.substring(0,8).equals("JoinRoom")) {
					
					str = str.substring(9);
					String msg[] = str.split("qwemnbzxcpoi';,q");
					RoomFrame.IdList.clear();
					
					
					for(int i=1; i<msg.length; i++) {
						if(!(RoomFrame.IdList.contains(msg[i]))) {
							RoomFrame.IdList.add(msg[i]);
						}
					}
					
					rf.IdListArea.setText("");
					for(String s:RoomFrame.IdList) {
						rf.IdListArea.append(s + "\n");
					}
					
					rf.textA.append(msg[0] + "\n");
					
				}
				// 방 메시지 뿌려주기
				else if(str.substring(0,8).equals("sendRoom")) {
					
					str = str.substring(9);
					String msg[] = str.split("qwemnbzxcpoi';,q");
					
					
					RoomFrame.IdList.clear();
					for(int i=1; i<msg.length; i++) {
						if(!(RoomFrame.IdList.contains(msg[i]))) {
							RoomFrame.IdList.add(msg[i]);
						}
					}
					
					
					rf.IdListArea.setText("");
					for(String s:RoomFrame.IdList) {
						rf.IdListArea.append(s + "\n");
					}
					
					rf.textA.append(msg[0] + "\n");
					
				}
				Thread.sleep(300);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
	}
	

}
