package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Member;
import threadEx.ServerThread;

public class MainClass {

	public static void main(String[] args) {
		
		//클라이언트의 정보를 담는 소켓 -> 클라이언트의 개수만큼 있어야 함!
		Socket clientSocket = null;
		try {
			//문지기 소켓
			ServerSocket serSocket = new ServerSocket(9000);
			
			List<Socket> list = new ArrayList<>();
			Map<String, List<Member>> rooms = new HashMap<>();
			
			while(true) {
				//버전 확인, binding, listening
				System.out.println("접속 대기중...");
				clientSocket = serSocket.accept();
				
				list.add(clientSocket);
				
				//접속 client 확인
				System.out.println("client IP: "+clientSocket.getInetAddress()+" Port: "+clientSocket.getPort());
				
				new ServerThread(clientSocket, list, rooms).start();;
			}
			//clientSocket.close();
			//serSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
