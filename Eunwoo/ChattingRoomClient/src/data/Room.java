package data;

public class Room {
	private int roomNumber;
	private String roomName;
	private int userCount;
	
	public Room(int roomNumber, String roomName, int userCount) {
		super();
		this.userCount = userCount;
		this.roomNumber = roomNumber;
		this.roomName = roomName;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public String getRoomName() {
		return roomName;
	}

	public int getuserCount() {
		return userCount;
	}

	@Override
	public String toString() {
		return roomNumber+"[방 이름 = " + roomName + ", 참여자 수 = " + userCount + "]";
	}
	
	
}
