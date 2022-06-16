package dto;

import java.net.Socket;

public class Member {

	public Socket socket;
	public String id;
	
	public Member(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
	}

}
