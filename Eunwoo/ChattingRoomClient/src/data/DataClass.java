package data;

import java.util.ArrayList;

public class DataClass {
	private static DataClass dataclass = null;
	ManagerClass manager;
	
	private String id="";

	private ArrayList<Room> roomList = new ArrayList<>();
	
	private Integer nowRoomNumber;
	private String nowRoomName;
	private ArrayList<String> roomUserId = new ArrayList<>();
	
	private DataClass(ManagerClass manager){
		this.manager = manager;
	}
	
	public static DataClass getInstatnce(ManagerClass manager) {
		if(dataclass == null) dataclass = new DataClass(manager);
		return dataclass;
	}
	
	// User ID -----------------------
	public String getId() {
		return id;
	}
	
	public void setId(String userId) {
		if(this.id.length() == 0) {
			this.id = userId;
			manager.getCf().setUserInfo(userId);
		}
	}
	
	// roomUserId ----------------------------------
	public ArrayList<String> getRoomUserId() {
		ArrayList<String> ret = new ArrayList<String>();
		for(int i = 0 ; i < roomUserId.size(); i++) {
			ret.add(roomUserId.get(i));
		}
		return ret;
	}
	
	public synchronized void setRoomUserID(ArrayList<String> list) {
		removeUserList();
		for(int i = 0; i < list.size(); i++) {
			String now = list.get(i);
			roomUserId.add(now);
			appendUserList(now);
		}
	}
	
	private synchronized void removeUserList() {
		roomUserId = new ArrayList<>();
		manager.getCf().initUserA();
	}
	
	private void appendUserList(String userId) {
		manager.getCf().appendUserA(userId);
	}
	
	// RoomList ----------------------------------
	public Integer getRoomFromList(int roomNumber) {
		for(int i = 0; i < roomList.size(); i++) {
			Room select = roomList.get(i);
			if(select.getRoomNumber() == roomNumber) {
				return select.getRoomNumber();
			}
		}
		return nowRoomNumber;
	}
	
	public synchronized boolean addRoomList(Room room) {
		manager.getRf().addItemRoomCmb(room.toString());
		return roomList.add(room);
	}
	
	public synchronized void refreshRoomList() {
		manager.getRf().refreshRoomCmb();
		roomList = new ArrayList<>();
	}

	// Now Room --------------------------------------
	public String getNowRoomName() {
		return nowRoomName;
	}
	
	public Integer getNowRoomNumber() {
		return nowRoomNumber;
	}

	public void setNowRoom(Integer nowRoomNumber, String nowRoomName) {
		this.nowRoomNumber = nowRoomNumber;
		this.nowRoomName = nowRoomName;
		manager.getCf().setRoomInfo(nowRoomName);
		
	}
	
}
