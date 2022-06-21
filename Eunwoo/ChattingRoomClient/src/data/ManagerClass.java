package data;

import java.net.Socket;
import java.util.ArrayList;

import net.WriteClass;
import view.ClientFrame;
import view.RoomListFrame;

public class ManagerClass {
	private static ManagerClass managerClass = null;
	private Socket socket;
	private ClientFrame cf;
	private RoomListFrame rf;
	private WriteClass wc;
	private DataClass dc;
	
	private ManagerClass(Socket socket) {
		this.dc = DataClass.getInstatnce(this);
		this.wc = new WriteClass(socket);
		this.cf = new ClientFrame(this);
		this.rf = new RoomListFrame(this);
		this.socket = socket;
	}
	
	ArrayList<String> userIdList = new ArrayList<>();
	ArrayList<Room> roomList = new ArrayList<>();

	public static ManagerClass getInstatnce(Socket socket) {
		if(managerClass == null) managerClass = new ManagerClass(socket);
		return managerClass;
	}

	public ClientFrame getCf() {
		return cf;
	}

	public RoomListFrame getRf() {
		return rf;
	}
	
	public WriteClass getWc() {
		return wc;
	}

	public Socket getSocket() {
		return socket;
	}

	public DataClass getDc() {
		return dc;
	}
	
	
}
