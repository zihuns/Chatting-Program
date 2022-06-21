package net;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

import data.DataClass;
import data.ManagerClass;
import data.Room;
import msg.Message;
import msg.ServerProtocol;
import view.ClientFrame;
import view.RoomListFrame;

public class ReadThread extends Thread{
	Socket socket;
	ClientFrame cf;
	RoomListFrame rf;
	DataClass data;
	
	public ReadThread(ManagerClass manager) {
		this.socket = manager.getSocket();
		this.cf = manager.getCf();
		this.rf = manager.getRf();
		this.data = manager.getDc();
	}

	@Override
	public void run() {
		super.run();
		
		try {
			while(true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
				String str = br.readLine();
				if(str == null) continue;
				Message recvMsg = new Message(str);
				
				runClient(recvMsg);
//				Thread.sleep(300);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String runClient(Message recvMsg) {
		String printStr = "";
		String id = recvMsg.getId();
		String time = recvMsg.getTimeString();
		String msg = recvMsg.getMsg();
		recvMsg.log("R");
		switch(recvMsg.getType()) {
			case ServerProtocol.USER_LIST:
			{
				String[] temp = parseStr(msg);
				Integer roomNumber = Integer.parseInt(temp[0]);
				String roomName = temp[1];
				data.setNowRoom(roomNumber, roomName);
				ArrayList<String> userList = new ArrayList<>();
				for(int i = 2; i < temp.length; i++) {
					userList.add(temp[i]);
				}
				data.setRoomUserID(userList);
				break;
			}
			case ServerProtocol.ROOM_LIST:
			{
				data.refreshRoomList();
				String[] temp = parseStr(msg);
				for(int i = 0; i+2 < temp.length;) {
					int roomNumber = Integer.parseInt(temp[i]);
					int userCount = Integer.parseInt(temp[i+2]);
					Room newRoom = new Room(roomNumber, temp[i+1], userCount);
					data.addRoomList(newRoom);
					i += 3;
				}
				break;
			}
			case ServerProtocol.CHANGE_ROOM:
			{
				printStr = "\t----[ERROR-Change Room]("+time+") : "+msg;
				appendChat(printStr);
				break;
			}
			case ServerProtocol.CREATE_ROOM:
			{
				printStr = "\t----[ERROR-Create Room]("+time+") : "+msg;
				appendChat(printStr);
				break;
			}
			case ServerProtocol.SEND_MSG:
			{
				if(msg.length() == 0) break;
				printStr = "["+id+"]("+time+") : "+msg;
				appendChat(printStr);
				break;
			}
			case ServerProtocol.USER_IN:
			{
				String[] temp = parseStr(msg);
				Integer roomNumber = Integer.parseInt(temp[0]);
				String roomName = temp[1];
				data.setNowRoom(roomNumber, roomName);
				ArrayList<String> userList = new ArrayList<>();
				for(int i = 2; i < temp.length; i++) {
					userList.add(temp[i]);
				}
				data.setRoomUserID(userList);
				
				printStr = "\t----["+id+"]("+time+") Enter "+roomName+" Room----";
				appendChat(printStr);
				break;
			}
			case ServerProtocol.USER_OUT:
			{
				String[] temp = parseStr(msg);
				Integer roomNumber = Integer.parseInt(temp[0]);
				String roomName = temp[1];
				data.setNowRoom(roomNumber, roomName);
				ArrayList<String> userList = new ArrayList<>();
				for(int i = 2; i < temp.length; i++) {
					userList.add(temp[i]);
				}
				data.setRoomUserID(userList);
				
				printStr = "\t----["+id+"]("+time+") Out "+roomName+" Room----";
				appendChat(printStr);
				break;
			}
			case ServerProtocol.SERVER_ERROR:
			{
				break;
			}
			default:
			{
				break;
			}
		}
		return printStr;
	}
	
	private void appendChat(String str) {
		cf.textA.append(str+"\n");
	}
	
	private String[] parseStr(String str) {
		String[] temp = str.split("\\|");
		return temp;
	}

}
