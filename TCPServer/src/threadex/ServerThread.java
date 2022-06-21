package threadex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServerThread extends Thread {
	
	Socket socket;
	List<Socket> list;
	public static Map<Socket,String> map = new HashMap<>();
	public static List<Socket> roomlist = new ArrayList<Socket>();
	public static Map<Socket,String> roomMap = new HashMap<>();
	static int roomNum = -1;
	
	public ServerThread(Socket socket, List<Socket> list) {
		super();
		this.socket = socket;
		this.list = list;
	}
	
	
	@Override
	public void run() {
		super.run();
		try {
		// 수신(recv)
			
			
			while(true) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = "";
			str = reader.readLine();
			System.out.println("client 로부터 받은 메세지:" + str);
			int myport = socket.getPort();
			
			
			// sendmessage
			if(str.substring(0,11).equals("sendMessage")) {
			
			str = str.substring(11);
			String id = str.substring(1,str.indexOf("]"));
			
			map.put(socket, id);
			
			// id 리스트 
			Iterator<Socket> it = map.keySet().iterator();
			String idmsg = "";
			while(it.hasNext()) {
				Socket key = it.next(); //
				String value = map.get(key);
				idmsg += "qwemnbzxcpoi';,q" + value;
			}
			
			// id list + 받은 메세지
			
			str = str + idmsg;
			
			
			}else if(str.substring(0,8).equals("RoomExit")) {
				String name[] = str.split(" ");
				str = "RoomExit[" + name[1] + "]님이 방을 나갔습니다.";
				
				
				// 방에 메시지 뿌려주기
				for(int i=0; i<roomlist.size(); i++) {
					if(socket != roomlist.get(i)) {
						PrintWriter writer = new PrintWriter(roomlist.get(i).getOutputStream());
						writer.println(str);
						writer.flush();
					}
				}
				for(int i=0; i<roomlist.size(); i++) {
					if(socket != roomlist.get(i)) {
						PrintWriter writer = new PrintWriter(roomlist.get(i).getOutputStream());
						writer.println(str);
						writer.flush();
					}
				}
				
				roomMap.remove(socket);
				roomlist.remove(socket);
				list.add(socket);
				map.put(socket,name[1]);
				
			} // 방 입장
			else if(str.substring(0,8).equals("JoinRoom")) {
				
				
				
				String name[] = str.split(" ");
				roomMap.put(socket, name[1]);
				roomlist.add(socket);
				list.remove(socket);
				map.remove(socket);
				
				str = "";
				str = name[0] + "[" + name[1] + "]님이 방에 참가하였습니다." ; 
				
				Iterator<Socket> it = roomMap.keySet().iterator();
				String idmsg = "";
				while(it.hasNext()) {
					Socket key = it.next(); //
					String value = roomMap.get(key);
					idmsg += "qwemnbzxcpoi';,q" + value;
				}
				
				str = str + idmsg;
				
				
				// 방에 메시지 뿌려주기
				for(int i=0; i<roomlist.size(); i++) {
					
					if(socket != roomlist.get(i)) {
						PrintWriter writer = new PrintWriter(roomlist.get(i).getOutputStream());
						writer.println(str);
						writer.flush();
					}
				}
				
				for(int i=0; i<roomlist.size(); i++) {
					
					if(socket != roomlist.get(i)) {
						PrintWriter writer = new PrintWriter(roomlist.get(i).getOutputStream());
						writer.println(str);
						writer.flush();
					}
				}
				continue;
				
			} 
			else if(str.substring(0,8).equals("sendRoom")) {
				
				String id = str.substring(9,str.indexOf("]"));
				roomMap.put(socket, id);
				
				
				// id 리스트 
				Iterator<Socket> it = roomMap.keySet().iterator();
				String idmsg = "";
				while(it.hasNext()) {
					Socket key = it.next(); //
					String value = roomMap.get(key);
					idmsg += "qwemnbzxcpoi';,q" + value;
					//System.out.println(value);
				}
				
				// id list + 받은 메세지
				
				str = str + idmsg;
				System.out.println(str);
				for(int i=0; i<roomlist.size(); i++) {
					if(socket != roomlist.get(i)) {
						PrintWriter writer = new PrintWriter(roomlist.get(i).getOutputStream());
						writer.println(str);
						writer.flush();
					}
				}
				for(int i=0; i<roomlist.size(); i++) {
					if(socket != roomlist.get(i)) {
						PrintWriter writer = new PrintWriter(roomlist.get(i).getOutputStream());
						writer.println(str);
						writer.flush();
					}
				}
				continue;
				
			}
			
			else if(str.substring(0,7).equals("Refresh")) {
				Iterator<Socket> it = map.keySet().iterator();
				String idmsg = "";
				while(it.hasNext()) {
					Socket key = it.next(); //
					String value = map.get(key);
					idmsg += "qwemnbzxcpoi';,q" + value;
				}
				
				str = str + idmsg;
				System.out.println(str);
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				
				writer.println(str);
				writer.flush();
				continue;
				
				
			}
			
			// 방 만들기
			
			else {
				roomNum ++;
				String make[] = null;
				String id = str.substring(1,str.indexOf("]"));
				make = str.split(" ");
				
				roomMap.put(socket, id);
				roomlist.add(socket);
				list.remove(socket);
				map.remove(socket);
				
				str = "moorekam " + make[1] + " " + id + " " + Integer.toString(roomNum) ;
			}
			
	
			// 송신(send)
			for(int i=0; i<list.size(); i++) {
				if(myport != list.get(i).getPort()) {
					PrintWriter writer = new PrintWriter(list.get(i).getOutputStream());
					
					writer.println(str);
					writer.flush();
				}
			}
			
			
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("연결이 끊긴 IP:" + socket.getInetAddress());
				list.remove(socket);
				map.remove(socket);
				
				
				// 접속되어 있는 남아있는 클라이언트 출력
				for(Socket s:list) {
					System.out.println("접속되어 있는 IP: " + s.getInetAddress() + " Port:" + s.getPort());
				}
				
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} // 직접 수신	

	}
}
