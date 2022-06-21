package main;

import java.io.IOException;
import java.net.Socket;

import net.ReadThread;
import view.ClientFrame;

public class MainClass {
	
	public static final int PORT = 9000;

	public static void main(String[] args) {

		try {
			Socket socket = new Socket("192.168.0.95", PORT);
			System.out.println("connection success!");
			ClientFrame cf = new ClientFrame(socket);

			new ReadThread(socket, cf).start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
