package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import view.ClientFrame;
import view.LobbyFrame;
import view.RoomFrame;

public class ReadThread extends Thread{
	
	Socket socket;
	ClientFrame cf;
	LobbyFrame lf;
	RoomFrame rf;
	List<String> IdList = new ArrayList<>();
	
	
	public ReadThread(Socket socket,ClientFrame cf) {
		
		this.socket = socket;
		this.cf = cf;
		
	}
	public ReadThread(Socket socket,LobbyFrame lf) {
		
		this.socket = socket;
		this.lf = lf;
		
	}
	public ReadThread(Socket socket,RoomFrame rf) {
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
				
				if(str.substring(0,1).equals("[")) {	
				
				String msg[] = str.split("qwemnbzxcpoi';,q");
				LobbyFrame.IdList.clear();
				for(int i=1; i<msg.length; i++) {
					if(!(LobbyFrame.IdList.contains(msg[i]))) {
						LobbyFrame.IdList.add(msg[i]);
					}
				}
				
				
				lf.IdListArea.setText("");
				for(String s:LobbyFrame.IdList) {
					lf.IdListArea.append(s + "\n");
				}
				
				lf.textA.append(msg[0] + "\n");
				}
				
				else if(str.substring(0,8).equals("moorekam")){
					// 누군가 방 생성
					String make[] = null;
					make = str.split(" ");
					if(make != null) {
						if(make[0].equals("moorekam")) {
							// make[3] == roomNum
							
							lf.textA.append(make[2] + "님이 방을 생성하였습니다.\n" );
							int roomNum = Integer.parseInt(make[3]);
							LobbyFrame.roombtn1[roomNum].setVisible(true);
							LobbyFrame.roombtn1[roomNum].setText(make[2] + "님의 방 입니다.");
							
							
							
							LobbyFrame.IdList.remove(make[2]); // 방만든사람 로비에서 ID 제거
							RoomFrame.IdList.add(make[2]);
						}
					}
				}
				
				else if(str.substring(0,7).equals("Refresh")) {
					str = str.substring(7);
					System.out.println(str);
					String msg[] = str.split("qwemnbzxcpoi';,q");
					LobbyFrame.IdList.clear();
					for(int i=1; i<msg.length; i++) {
						if(!(LobbyFrame.IdList.contains(msg[i]))) {
							LobbyFrame.IdList.add(msg[i]);
						}
					}
					
					
					lf.IdListArea.setText("");
					for(String s:LobbyFrame.IdList) {
						lf.IdListArea.append(s + "\n");
					}
					
					
					
				}
				
				else {
					System.out.println("ReadThread");
					continue;
				}
				
				Thread.sleep(300);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
