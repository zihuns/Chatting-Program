package main;

import java.io.IOException;
import java.net.Socket;

import net.ReadThread;
import view.LobbyFrame;
import view.RoomFrame;

public class MainClass {
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("192.168.0.14",9000);
			System.out.println("connection sccess !! ");
			
			LobbyFrame lf = new LobbyFrame(socket);
			new ReadThread(socket,lf).start();
					
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
