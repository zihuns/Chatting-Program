package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import data.DataClass;
import data.ManagerClass;
import net.ReadThread;
import view.ClientFrame;

public class MainClass {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.0.104", 9000);
			
			System.out.println("connection success");
			
			ManagerClass managerclass = ManagerClass.getInstatnce(socket);

			new ReadThread(managerclass).start();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
