package dto;

import java.util.List;
import java.util.ArrayList;

public class ChatRoom {
	String roomName;
	List<String> userlist = new ArrayList<String>();

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<String> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<String> userlist) {
		this.userlist = userlist;
	}

	public ChatRoom(String roomName, List<String> userlist) {
		super();
		this.roomName = roomName;
		this.userlist = userlist;
	}

	@Override
	public String toString() {
		String result = "ChatRoom [roomName=" + roomName + ", userlist=";

		for (String s : userlist) {
			result += s + " ";
		}

		result += "]";
		return result;
	}

	public String getUserlistToString() {
		String result = "";

		for (String s : userlist) {
			result += s + ", ";
		}

		if (userlist.size() > 0) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}

}
