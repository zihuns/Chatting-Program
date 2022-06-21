package room;

import java.util.ArrayList;

public class RoomManager {
	ArrayList<Room> roomList;
	boolean[] roomIndex;
	private Room mainRoom;
	private static RoomManager roomManager = null;
	
	private RoomManager() {
		roomList = new ArrayList<>();
		roomIndex = new boolean[99999999];
		
		// add MainRoom
		this.mainRoom = new Room(0, "전체채팅방");
		roomList.add(mainRoom);
		roomIndex[0] = true;
		
	}

	public static RoomManager getInstance() {
		if(roomManager == null) {
			roomManager = new RoomManager();
		}
		return roomManager;
	}
	
	public Room getMainRoom() {
		return mainRoom;
	}
	
	public synchronized int getEmptyRoomNumber() {
		for(int i = 1; i < roomIndex.length; i++) {
			if(!roomIndex[i]) return i;
		}
		return 0;
	}
	
	public synchronized void setEmptyRoomNumber(int i) {
		if(i <= 0) return;
		roomIndex[i] = false;
	}
	
	public synchronized Room createRoom(String roomName) {
		int roomNumber = roomManager.getEmptyRoomNumber();
		if(roomNumber == 0) return mainRoom;
		
		Room newRoom = new Room(roomNumber, roomName);
		if(!roomList.add(newRoom)) return mainRoom;
		roomIndex[roomNumber] = true;
		return newRoom;
	}
	
	private synchronized boolean removeRoom(int roomNumber) {
		Room now = null;
		for(int i = 1; i < roomList.size(); i++) {
			now = roomList.get(i);
			if(now.getRoomNumber() == roomNumber) break;
		}
		if(now != null && roomNumber != 0) {
			if(now.getUserList().size() == 0) {
				roomList.remove(now);
				return true;
			}
		} 
		return false;
	}
	
	private synchronized boolean removeRoom(Room room) {
		if(room != null && room.getRoomNumber() != 0) {
			if(room.getUserList().size() == 0) {
				roomList.remove(room);
				return true;
			}
		} 
		return false;
	}
	
	public boolean checkRoomEmpty(int roomNumber) {
		Room now = null;
		for(int i = 1; i < roomList.size(); i++) {
			now = roomList.get(i);
			if(now.getRoomNumber() == roomNumber) break;
		}
		if(now != null && roomNumber != 0) {
			if(now.getUserList().size() == 0) {
				return removeRoom(roomNumber);
			}
		} 
		return false;
	}
	
	public boolean checkRoomEmpty(Room room) {
		if(room != null && room.getRoomNumber() != 0) {
			if(room.getUserList().size() == 0) {
				return removeRoom(room);
			}
		}
		return false;
	}
	
	public Room getRoom(int roomNumber) {
		Room mainRoom = roomList.get(0);
		for(int i = 1; i < roomList.size(); i++) {
			Room now = roomList.get(i);
			if(now.getRoomNumber() == roomNumber) return now;
			else if(now.getRoomNumber() == 0) mainRoom = now;
		}
		return mainRoom;
	}
	
	public synchronized String getRoomListStr() {
		StringBuilder msg = new StringBuilder();
		for(int i = 0; i < roomList.size(); i++) {
			Room tempRoom = roomList.get(i);
			if(checkRoomEmpty(tempRoom.getRoomNumber())) continue;
			msg.append(tempRoom.getRoomNumber()+"|"
					+tempRoom.getRoomName()+"|"
					+tempRoom.getUserList().size()+"|");
			
		}
		return msg.toString();
	}
	
}
