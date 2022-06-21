package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import room.Room;
import room.RoomManager;
import user.User;
import user.MainChatThread;

public class MainClass {

	public static void main(String[] args) throws IOException {
		Socket clientSocket = null;
		
		ServerSocket serverSocket = new ServerSocket(9000);
		
		// Init roomList
		RoomManager roomManager = RoomManager.getInstance();
		Room mainRoom = roomManager.getRoom(0);
		
		// Accept loop 
		while(true) {
			System.out.println("connecting...");
			
			clientSocket = serverSocket.accept();
			User user = new User(clientSocket);
			mainRoom.addUser(user);
			
			System.out.println("[new Connect] client IP : " + clientSocket.getInetAddress()
			+ " Port : " + clientSocket.getPort());
			
			new MainChatThread(roomManager, user).start();
		}
		
	}

}
