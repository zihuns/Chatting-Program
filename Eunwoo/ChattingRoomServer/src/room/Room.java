package room;

import java.util.ArrayList;

import user.User;

public class Room {
	int roomNumber;
	String roomName;
	ArrayList<User> userList;
	
	public Room(int roomNumber, String roomName) {
		super();
		this.userList = new ArrayList<User>();
		this.roomNumber = roomNumber;
		this.roomName = roomName;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public String getRoomName() {
		return roomName;
	}

	public ArrayList<User> getUserList() {
		ArrayList<User> ret = new ArrayList<User>();
		for(int i = 0; i < userList.size(); i++) {
			ret.add(userList.get(i));
		}
		return ret;
	}
	
	public synchronized boolean addUser(User user) {
		return userList.add(user);
	}
	
	public synchronized boolean removeUser(User user) {
		return userList.remove(user);
	}
	
	public int getUserCount() {
		return userList.size();
	}
	
}
