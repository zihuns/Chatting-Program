package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import msg.ClientProtocol;
import msg.Message;

public class WriteClass {
	Socket socket;
	String id;
	String state = "S";
	
	public WriteClass(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public void sendNewClient(String id) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			this.id = id;
			checkIdEmpty();
			Message sendMsg = new Message(ClientProtocol.PROGRAM_START, LocalDateTime.now(), id, id);
		
			// send to server
			pw.println(sendMsg.getProtocol());
			pw.flush();
			sendMsg.log(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendGetRoomList() {
		try {
			checkIdEmpty();
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			Message sendMsg;
			
			sendMsg = new Message(ClientProtocol.REQ_ROOM_LIST, LocalDateTime.now(), id, id);
		
			// send to server
			pw.println(sendMsg.getProtocol());
			pw.flush();
			
			sendMsg.log(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMoveRoom(int roomNumber) {
		try {
			checkIdEmpty();
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			Message sendMsg;
			
			sendMsg = new Message(ClientProtocol.MOVE_ROOM, LocalDateTime.now(), id, roomNumber+"");
		
			// send to server
			pw.println(sendMsg.getProtocol());
			pw.flush();
			
			sendMsg.log(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendCreateRoom(String roomName) {
		try {
			checkIdEmpty();
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			Message sendMsg;
			
			sendMsg = new Message(ClientProtocol.CREATE_ROOM, LocalDateTime.now(), id, roomName);
		
			// send to server
			pw.println(sendMsg.getProtocol());
			pw.flush();
			
			sendMsg.log(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
		try {
			checkIdEmpty();
			
			if(msg == null || msg.length() == 0) return;
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			Message sendMsg;
			
			sendMsg = new Message(ClientProtocol.SEND_MSG, LocalDateTime.now(), id, msg);
		
			// send to server
			pw.println(sendMsg.getProtocol());
			pw.flush();
			
			sendMsg.log(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendQuit(String msg) {
		try {
			checkIdEmpty();
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			Message sendMsg;
			
			sendMsg = new Message(ClientProtocol.PROGRAM_QUIT, LocalDateTime.now(), id, id);
		
			// send to server
			pw.println(sendMsg.getProtocol());
			pw.flush();
			
			sendMsg.log(state);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkIdEmpty() {
		if(id == null || id.length() == 0) {
			id = socket.getInetAddress().getHostAddress();
			return true;
		}
		return false;
		
	}
}
