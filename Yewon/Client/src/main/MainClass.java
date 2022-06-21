package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.ReadThread;
import view.MainFrame;
import view.RoomFrame;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Socket socket;
		try {
			socket = new Socket("192.168.0.11", 9000);
			System.out.println("접속 성공");

			List<RoomFrame> rfList = new ArrayList<>();
			MainFrame mf = new MainFrame(socket, rfList);
			new ReadThread(socket, mf).start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
