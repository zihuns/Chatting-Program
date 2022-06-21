package threadEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.Member;

public class ServerThread extends Thread {
	
	Socket socket;
	List<Socket> list;
	Map<String , List<Member>> rooms;
	
	public ServerThread(Socket socket, List<Socket> list, Map<String, List<Member>> rooms) {
		this.socket = socket;
		this.list = list;
		this.rooms = rooms;
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			while(true) {
				String str = null;
				
				//수신
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				str = reader.readLine();
				System.out.println("client로부터 받은 메시지 : "+str);
				
				//message 파싱!
				String[] msg = str.split("/");
				
				String code = msg[0];
				String roomName = msg[1];
				String id = msg[2];
				
				//lobby로 첫 입장
				if(code.equals("enter")) {
					//채팅방이 있으면 입장, 없으면 생성
					findRoom(roomName, id);
					
					//main에 있는 member 리스트
					String memList = makeMemberList(roomName);
					sendToAll("fsmember", "main", memList);

					Thread.sleep(300);
					
					//room 리스트
					String roomList = makeRoomList();
					sendToAll("fsroom", null, roomList);
			
				}
				//message 전송
				else if(code.equals("msg")) {
					String newMsg = "["+id+"] "+msg[3];
					
					sendToMember("fsmsg", roomName, newMsg);
				}
				//room 생성
				else if(code.equals("room")) {
					//채팅방이 있으면 입장, 없으면 생성
					findRoom(roomName, id);
					
					//현재 방 멤버 리스트
					String memList = makeMemberList(roomName);
					sendToMember("fsmember", roomName, memList);

					Thread.sleep(300);

					//main에서 현재 사람 제거
					List<Member> mainMember = rooms.get("main");
					for(int i=0; i<mainMember.size(); i++) {
						if(mainMember.get(i).id.equals(id)) {
							mainMember.remove(i);
							break;
						}
					}
					rooms.put("main", mainMember);
					
					//main방 멤버 리스트 전송
					memList = makeMemberList("main");
					sendToAll("fsmember", "main", memList);

					Thread.sleep(300);
					
					//방 리스트
					String roomList = makeRoomList();
					sendToAll("fsroom", null, roomList);

				}
				//퇴장
				else if(code.equals("exit")) {
					if(roomName.equals("main")) {
						//main에서 현재 사람 제거
						List<Member> mainMember = rooms.get("main");
						for(int i=0; i<mainMember.size(); i++) {
							if(mainMember.get(i).id.equals(id)) {
								mainMember.remove(i);
								break;
							}
						}
						rooms.put("main", mainMember);
						
						//main에 있는 멤버 리스트
						String memList = makeMemberList("main");
						sendToAll("fsmember", "main", memList);
					}
					else {
						//현재 방에서 현재 사람 제거
						List<Member> member = rooms.get(roomName);
						for(int i=0; i<member.size(); i++) {
							if(member.get(i).id.equals(id)) {
								member.remove(i);
								break;
							}
						}
						rooms.put(roomName, member);
						
						//현재 방 멤버 리스트
						String memList = makeMemberList(roomName);
						sendToMember("fsmember", roomName, memList);

						Thread.sleep(300);
						
						//main에 추가
						List<Member> mainMember = rooms.get("main");
						mainMember.add(new Member(socket, id));
						rooms.put("main", mainMember);
						
						//main 멤버 리스트
						memList = makeMemberList("main");
						sendToAll("fsmember", "main", memList);
					}
				}
				
				Thread.sleep(300);
			}
		}
		catch (Exception e) {
			System.out.println("연결이 끊긴 IP : "+socket.getInetAddress());
			list.remove(socket);
			
			//접속되어 있는 남아있는 클라이언트 출력
			for(Socket s : list) {
				System.out.println("접속되어 있는 IP :"+s.getInetAddress());
			}
		}
	}
	
	public void findRoom(String roomName, String id) {
		if(rooms.containsKey(roomName)) {
			List<Member> members = rooms.get(roomName);
			members.add(new Member(socket, id));
			rooms.put(roomName, members);
		}
		else {
			List<Member> members = new ArrayList<>();
			members.add(new Member(socket, id));
			rooms.put(roomName, members);
		}
	}
	
	public String makeMemberList(String roomName) {
		String memList = "";
		List<Member> members = rooms.get(roomName);
		for(Member m : members) {
			memList += m.id+":";
		}
		return memList;
	}
	
	public String makeRoomList() {
		String roomList = "";
		for(String key : rooms.keySet()) {
			if(key.equals("main"))
				continue;
			roomList += key+":";
		}
		return roomList;
	}
	
	public void sendToAll(String code, String roomName, String msg) {
		try {
			for(String key : rooms.keySet()) {
				List<Member> mems = rooms.get(key);
				for(Member m : mems) {
					PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
					if(roomName != null)
						writer.println(code+"/"+roomName+"/"+msg);
					else
						writer.println(code+"/"+msg);
					writer.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendToMember(String code, String roomName, String msg) {
		try {
			List<Member> member = rooms.get(roomName);
			for(int i=0; i<member.size(); i++) {
				PrintWriter writer = new PrintWriter(member.get(i).socket.getOutputStream());
				writer.println(code+"/"+roomName+"/"+msg);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
