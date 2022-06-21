package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

import msg.ClientProtocol;
import msg.Message;
import msg.ServerProtocol;
import room.Room;
import room.RoomManager;

public class MainChatThread extends Thread {
	
	private RoomManager roomManager;
	private Room nowRoom;
	private User currentUser;
	private final Room mainRoom;
	
	public MainChatThread(RoomManager roomManager, User user) {
		super();
		this.roomManager = roomManager;
		this.mainRoom = roomManager.getMainRoom();
		this.nowRoom = mainRoom;
		this.currentUser = user;
	}

	@Override
	public void run() {
		super.run();
		
		try {
			
			while(true) {
				
				Socket socket = currentUser.getSocket();
				BufferedReader reader;
				
				reader = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()
								)
							);
				
				String str = reader.readLine();
				Message recvMsg = new Message(str);
				
//				System.out.println("[" + currentUser.getId() +"("+socket.getPort()+")] : " + str);
				log("R", recvMsg);
				
				Message sendMsg = runServer(recvMsg);
				Thread.sleep(300);
			}
		} catch (Exception e) {
			// 방나가기 처리
			try {
				nowRoom.removeUser(currentUser);
				Message sendMsg = sendUserOut(nowRoom, LocalDateTime.now());
				log("S", sendMsg);
			} catch (IOException e1) {
				
			}

			roomManager.checkRoomEmpty(nowRoom);
		}
	}

	private Message runServer(Message recvMsg) throws IOException, InterruptedException {
		Message sendMsg = null;
		LocalDateTime now = null;
		Room beforeRoom;
		Room newRoom;
		switch(recvMsg.getType()) {
			case ClientProtocol.PROGRAM_START:
				// fix user id
				System.out.println( nowRoom.getUserCount());
				nowRoom.removeUser(currentUser);
				currentUser.setId(recvMsg.getId());
				nowRoom.addUser(currentUser);
				// send user in to room mate
				sendMsg = sendUserIn(nowRoom, LocalDateTime.now());
				log("S", sendMsg);
				break;
			case ClientProtocol.REQ_ROOM_LIST:
				sendMsg = sendRoomList(recvMsg.getTime());
				log("S", sendMsg);
				break;
			case ClientProtocol.MOVE_ROOM: // get in new room
				// search 
				beforeRoom = nowRoom;
				int roomNumber = Integer.parseInt(recvMsg.getMsg());
				newRoom = roomManager.getRoom(roomNumber);
				if(newRoom == null || (roomNumber != 0 && newRoom.equals(mainRoom))) {
					sendMsg = sendMoveRoomError("Room Number Error. Please Try again");
					log("S", sendMsg);
					return sendMsg;
				}
				
				nowRoom.removeUser(currentUser);
				newRoom.addUser(currentUser);
				
				now = LocalDateTime.now();
				
				beforeRoom = nowRoom;
				nowRoom = newRoom;
				
				sendMsg = sendUserOut(beforeRoom, now);
				log("S", sendMsg);
				sendMsg = sendUserIn(nowRoom, now);
				log("S", sendMsg);
				break;
			case ClientProtocol.CREATE_ROOM:
				beforeRoom = nowRoom;
				newRoom = roomManager.createRoom(recvMsg.getMsg());
				if(newRoom.equals(mainRoom)) {
					sendMsg = sendCreateRoomError("Too many rooms");
					log("S", sendMsg);
				}
				
				nowRoom.removeUser(currentUser);
				newRoom.addUser(currentUser);
				
				now = LocalDateTime.now();
				
				beforeRoom = nowRoom;
				nowRoom = newRoom;
				
				sendMsg = sendUserOut(beforeRoom, now);
				log("S", sendMsg);
				sendMsg = sendUserIn(nowRoom, now);
				log("S", sendMsg);
				
				nowRoom = newRoom;

				break;
			case ClientProtocol.SEND_MSG:
				sendMsg = sendMsg(recvMsg);
				log("S", sendMsg);
				break;
			case ClientProtocol.PROGRAM_QUIT:
				nowRoom.removeUser(currentUser);
				now = LocalDateTime.now();
				sendMsg = sendUserOut(nowRoom, now);
				log("S", sendMsg);
				break;
			default:
				System.out.println("Wrong Type from "+ "[" + currentUser.getId() + "]");
				break;	
		}

		return sendMsg;
	}
	
	private Message sendRoomList(LocalDateTime time) throws IOException {
		String msg = roomManager.getRoomListStr();
		Message sendMsg = new Message(ServerProtocol.ROOM_LIST, time, currentUser.getId(), msg);
		
		PrintWriter writer = new PrintWriter(currentUser.getSocket().getOutputStream());
		writer.println(sendMsg.getProtocol());
		writer.flush();
		return sendMsg;
	}
	
	private Message sendMoveRoomError(String msg) throws IOException {
		Message sendMsg = new Message(ServerProtocol.CHANGE_ROOM, LocalDateTime.now(), currentUser.getId(), msg);
		PrintWriter writer = new PrintWriter(currentUser.getSocket().getOutputStream());
		writer.println(sendMsg.getProtocol());
		writer.flush();
		return sendMsg;
	}
	
	private Message sendCreateRoomError(String msg) throws IOException {
		Message sendMsg = new Message(ServerProtocol.CREATE_ROOM, LocalDateTime.now(), currentUser.getId(), msg);
		PrintWriter writer = new PrintWriter(currentUser.getSocket().getOutputStream());
		writer.println(sendMsg.getProtocol());
		writer.flush();
		return sendMsg;
	}
	
	private Message sendMsg(Message recvMsg) throws IOException {
		Message sendMsg = new Message(ServerProtocol.SEND_MSG, recvMsg.getTime(), recvMsg.getId(), recvMsg.getMsg());
		
		//send all room user
		List<User> userList = nowRoom.getUserList();
		for(int i = 0; i < userList.size(); i++) {
			User toUser = userList.get(i);
			Socket sendSocket = toUser.getSocket();
			
			PrintWriter writer = new PrintWriter(sendSocket.getOutputStream());
			
			writer.println(sendMsg.getProtocol());
			writer.flush();
		}
		return sendMsg;
	}
	
	private Message sendUserIn(Room inRoom, LocalDateTime time) throws IOException {
		// make string
		StringBuilder sb = new StringBuilder();
		
		// append room name
		sb.append(inRoom.getRoomNumber()+"|");
		sb.append(inRoom.getRoomName()+"|");
		// append user id
		List<User> userList = inRoom.getUserList();
		for(int i = 0; i < userList.size(); i++) {
			User temp = userList.get(i);
			sb.append(temp.getId()+"|");
		}
		
		// make message
		Message sendMsg = new Message(ServerProtocol.USER_IN, time, currentUser.getId(), sb.toString());
		
		//send all room user
		for(int i = 0; i < userList.size(); i++) {
			User toUser = userList.get(i);
			Socket sendSocket = toUser.getSocket();
			
			PrintWriter writer = new PrintWriter(sendSocket.getOutputStream());
			
			writer.println(sendMsg.getProtocol());
			writer.flush();
		}
		return sendMsg;
	}
	
	private Message sendUserOut(Room outRoom, LocalDateTime time) throws IOException {
		if(roomManager.checkRoomEmpty(outRoom.getRoomNumber())) {
			return new Message(ServerProtocol.USER_OUT, time, currentUser.getId(), "Erase Room");
		}
		StringBuilder sb = new StringBuilder();
		
		List<User> userList = outRoom.getUserList();
		// make string
		sb.append(outRoom.getRoomNumber()+"|");
		sb.append(outRoom.getRoomName()+"|");
		for(int i = 0; i < userList.size(); i++) {
			User temp = userList.get(i);
			sb.append(temp.getId()+"|");
		}
		
		// make message
		Message sendMsg = new Message(ServerProtocol.USER_OUT, time, currentUser.getId(), sb.toString());
		
		//send all room user
		for(int i = 0; i < userList.size(); i++) {
			User toUser = userList.get(i);
			Socket sendSocket = toUser.getSocket();
			
			PrintWriter writer = new PrintWriter(sendSocket.getOutputStream());
			
			writer.println(sendMsg.getProtocol());
			writer.flush();
		}
		return sendMsg;
	}
	
	private void log(String state ,Message message) {
		String str = state+"-";
		
		str += "[Room:"+nowRoom.getRoomNumber()+" - id:"+ message.getId() + "][" 
				+ message.getTime() + "](Type:" + message.getType()+") : " 
				+ message.getMsg();
		
		System.out.println(str);
	}
}
