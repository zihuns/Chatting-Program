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
				
				//lobby로 첫 입장
				if(msg[0].equals("enter")) {
					if(rooms.containsKey(msg[1])) {
						List<Member> members = rooms.get(msg[1]);
						members.add(new Member(socket, msg[2]));
						rooms.put(msg[1], members);
					}
					else {
						List<Member> members = new ArrayList<>();
						members.add(new Member(socket, msg[2]));
						rooms.put(msg[1], members);
					}
					
					//main에 있는 member 리스트
					String memList = "";
					List<Member> members = rooms.get("main");
					for(Member m : members) {
						memList += m.id+":";
					}
					//main member 전송
					for(String key : rooms.keySet()) {
						List<Member> mems = rooms.get(key);
						for(Member m : mems) {
							PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
							writer.println("fsmember/main/"+memList);
							writer.flush();
						}
					}
					Thread.sleep(300);
					
					//room 리스트
					String roomList = "";
					for(String key : rooms.keySet()) {
						if(key.equals("main"))
							continue;
						roomList += key+":";
					}
					//room 리스트 전송
					for(String key : rooms.keySet()) {
						List<Member> mems = rooms.get(key);
						for(Member m : mems) {
							PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
							writer.println("fsroom/"+roomList);
							writer.flush();
						}
					}				
				}
				//message 전송
				else if(msg[0].equals("msg")) {
					String newMsg = "["+msg[2]+"] "+msg[3];
					
					List<Member> members = rooms.get(msg[1]);
					for(int i=0; i<members.size(); i++) {
						PrintWriter writer = new PrintWriter(members.get(i).socket.getOutputStream());
						writer.println("fsmsg/"+msg[1]+"/"+newMsg);
						writer.flush();
					}
				}
				//room 생성
				else if(msg[0].equals("room")) {
					if(rooms.containsKey(msg[1])) {
						List<Member> members = rooms.get(msg[1]);
						members.add(new Member(socket, msg[2]));
						rooms.put(msg[1], members);
					}
					else {
						List<Member> members = new ArrayList<>();
						members.add(new Member(socket, msg[2]));
						rooms.put(msg[1], members);
					}
					
					//현재 방 멤버 리스트
					List<Member> roomMember = rooms.get(msg[1]);
					String memList = "";
					for(int i=0; i<roomMember.size(); i++) {
						memList += roomMember.get(i).id+":";
					}
					//현재 방 멤버 리스트 전송
					for(int i=0; i<roomMember.size(); i++) {
						PrintWriter writer = new PrintWriter(roomMember.get(i).socket.getOutputStream());
						writer.println("fsmember/"+msg[1]+"/"+memList);
						writer.flush();
					}
					Thread.sleep(300);

					//main에서 현재 사람 제거
					List<Member> mainMember = rooms.get("main");
					for(int i=0; i<mainMember.size(); i++) {
						if(mainMember.get(i).id.equals(msg[2])) {
							mainMember.remove(i);
							break;
						}
					}
					rooms.put("main", mainMember);
					//main방 멤버 리스트 전송
					memList = "";
					for(int i=0; i<mainMember.size(); i++) {
						memList += mainMember.get(i).id+":";
					}
					for(String key : rooms.keySet()) {
						List<Member> mems = rooms.get(key);
						for(Member m : mems) {
							PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
							writer.println("fsmember/main/"+memList);
							writer.flush();
						}
					}
					Thread.sleep(300);
					
					//방 리스트
					String roomList = "";
					for(String key : rooms.keySet()) {
						if(key.equals("main"))
							continue;
						roomList += key+":";
					}
					//방 리스트 전송
					for(String key : rooms.keySet()) {
						List<Member> mems = rooms.get(key);
						for(Member m : mems) {
							PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
							writer.println("fsroom/"+roomList);
							writer.flush();
						}
					}
				}
				//퇴장
				else if(msg[0].equals("exit")) {
					if(msg[1].equals("main")) {
						//main에서 현재 사람 제거
						List<Member> mainMember = rooms.get("main");
						for(int i=0; i<mainMember.size(); i++) {
							if(mainMember.get(i).id.equals(msg[2])) {
								mainMember.remove(i);
								break;
							}
						}
						rooms.put("main", mainMember);
						
						//main에 있는 멤버 리스트
						String memList = "";
						for(int i=0; i<mainMember.size(); i++) {
							memList += mainMember.get(i).id+":";
						}
						//main member 리스트 전송
						for(String key : rooms.keySet()) {
							List<Member> mems = rooms.get(key);
							for(Member m : mems) {
								PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
								writer.println("fsmember/main/"+memList);
								writer.flush();
							}
						}
					}
					else {
						//현재 방에서 현재 사람 제거
						List<Member> member = rooms.get(msg[1]);
						for(int i=0; i<member.size(); i++) {
							if(member.get(i).id.equals(msg[2])) {
								member.remove(i);
								break;
							}
						}
						rooms.put(msg[1], member);
						
						//현재 방 멤버 리스트
						String memList = "";
						for(int i=0; i<member.size(); i++) {
							memList += member.get(i).id+":";
						}
						//현재 방 멤버 리스트 전송
						for(int i=0; i<member.size(); i++) {
							PrintWriter writer = new PrintWriter(member.get(i).socket.getOutputStream());
							writer.println("fsmember/"+msg[1]+"/"+memList);
							writer.flush();
						}
						Thread.sleep(300);
						
						
						//main에 추가
						List<Member> mainMember = rooms.get("main");
						mainMember.add(new Member(socket, msg[2]));
						rooms.put("main", mainMember);
						
						//main 멤버 리스트
						memList = "";
						for(int i=0; i<mainMember.size(); i++) {
							memList += mainMember.get(i).id+":";
						}
						//main 멤버 리스트 전송
						for(String key : rooms.keySet()) {
							List<Member> mems = rooms.get(key);
							for(Member m : mems) {
								PrintWriter writer = new PrintWriter(m.socket.getOutputStream());
								writer.println("fsmember/main/"+memList);
								writer.flush();
							}
						}
						
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
}
