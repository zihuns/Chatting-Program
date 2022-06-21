package user;

import java.net.Socket;

public class User {
	Socket socket;
	String id;
	
	public User(Socket clientSocket) {
		this.socket = clientSocket;
	}

	public Socket getSocket(){
		return socket;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
}
