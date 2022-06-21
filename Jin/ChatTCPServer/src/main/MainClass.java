package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dto.ChatRoom;
import threadex.ServerThread;

public class MainClass {

	public static void main(String[] args) {

		/*
		 * TCP : Transmission Control Protocol
		 */
		Socket clientSocket = null;
		List<String> userIdList = new ArrayList<String>();
		Vector<ChatRoom> chatList = new Vector<ChatRoom>();

		try {

			// 문지기 소켓
			ServerSocket serSocket = new ServerSocket(9000);
			// 버전 확인, binding, listen

			List<Socket> list = new ArrayList<Socket>();

			while (true) {
				System.out.println("접속 대기중...");
				clientSocket = serSocket.accept();

				list.add(clientSocket);

				// 접속 client 확인
				System.out.println("client IP: " + clientSocket.getInetAddress()
						+ "Port : " + clientSocket.getPort());

				new ServerThread(clientSocket, list, userIdList, chatList).start();

			}

//			clientSocket.close();
//			serSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
