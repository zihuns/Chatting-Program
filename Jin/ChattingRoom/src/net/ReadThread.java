package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import dto.ChatRoom;

import java.io.InputStreamReader;
import view.ClientFrame;
import view.ClientRoomFrame;
import view.IdFrame;

public class ReadThread extends Thread {

	Socket socket;
	ClientFrame cf;

	public static List<String> clientList = new ArrayList<String>();
	public static Vector<ChatRoom> chatList = new Vector<ChatRoom>();

	public ReadThread(Socket socket, ClientFrame cf) {
		this.socket = socket;
		this.cf = cf;
	}

	@Override
	public void run() {
		super.run();
		try {
			while (true) {
				BufferedReader br;

				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String str = br.readLine();
				if (str == null) {
					System.out.println("접속끊김");
				}

				// 채팅방 목록 테이블에 추가
				cf.model1.setRowCount(0);
				if (!chatList.isEmpty()) {
					for (ChatRoom cr : chatList) {
						cf.model1.addRow(new Object[] { cr.getRoomName(), cr.getUserlistToString() });

					}
				}

				// 접속중인 id 테이블에 추가
				cf.model2.setRowCount(0);
				if (!clientList.isEmpty()) {
					for (String s : clientList) {
						cf.model2.addRow(new Object[] { s });
					}
				}

				Thread.sleep(300);

				readRecieve(str);

				Thread.sleep(300);

				// 채팅방 목록 변경된 사항 포함해서 테이블에 추가
				cf.model1.setRowCount(0);

				if (!chatList.isEmpty()) {
					for (ChatRoom cr : chatList) {

						cf.model1.addRow(new Object[] { cr.getRoomName(), cr.getUserlistToString() });

					}
				}

				// 접속중인 id 테이블에 추가
				cf.model2.setRowCount(0);
				if (!clientList.isEmpty()) {
					for (String s : clientList) {
						cf.model2.addRow(new Object[] { s });
					}
				}

				Thread.sleep(300);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void readRecieve(String str) {
		String myId = IdFrame.textIdInput.getText();
		// 수신(recieve) 문자열 분석 함수
		String result = "";
		StringTokenizer st = new StringTokenizer(str, "|");

		int protocol = Integer.parseInt(st.nextToken());
		String roomName = "";
		String id = "";

		if (protocol == Code.INIT) {

			String users = st.nextToken();
			StringTokenizer stk = new StringTokenizer(users, ",");

			while (stk.hasMoreTokens()) {
				String newId = stk.nextToken();
				if (!clientList.contains(newId)) {

					clientList.add(newId);
				}
			}

			return;
		}

		if (protocol == Code.INITCHATROOM) {
			roomName = st.nextToken();
			String users = st.nextToken();
			StringTokenizer stk = new StringTokenizer(users, ",");

			List<String> temp = new ArrayList<String>();
			while (stk.hasMoreTokens()) {
				String newId = stk.nextToken();
				temp.add(newId);
			}

			ChatRoom cr = new ChatRoom(roomName, temp);
			chatList.add(cr);

			return;
		}

		if (protocol == Code.EXIT) {

			String exitId = st.nextToken();

			if (clientList.contains(exitId)) {
				clientList.remove(exitId);
			}

			for (ChatRoom cr : chatList) {
				List<String> temp = new ArrayList<String>();
				temp = cr.getUserlist();
				if (temp.contains(exitId)) {
					temp.remove(exitId);
					cr.setUserlist(temp);
					chatList.set(chatList.indexOf(cr), cr);
				}
			}

			Iterator<ChatRoom> iter = chatList.iterator();
			while (iter.hasNext()) {
				ChatRoom cr = iter.next();
				if (cr.getUserlist().isEmpty() || cr.getUserlist().size() == 0) {
					iter.remove();
				}
			}

			return;
		}

		if (protocol == Code.EXITROOM) {

			String exitRoomName = st.nextToken();

			String exitId = st.nextToken();

			for (ChatRoom cr : chatList) {
				List<String> temp1 = new ArrayList<String>();

				if (cr.getRoomName().equals(exitRoomName)) {
					temp1 = cr.getUserlist();
					if (temp1.contains(exitId)) {
						temp1.remove(exitId);
						cr.setUserlist(temp1);
						chatList.set(chatList.indexOf(cr), cr);
					}
				}

			}

			Iterator<ChatRoom> iter = chatList.iterator();
			while (iter.hasNext()) {
				ChatRoom cr = iter.next();
				if (cr.getUserlist().isEmpty() || cr.getUserlist().size() == 0) {
					iter.remove();
				}
			}

			return;
		}

		switch (protocol) {
		case Code.LOGIN:
			id = st.nextToken();
			clientList.add(id);
			while (st.hasMoreTokens()) {
				result += st.nextToken();
			}
			if (!clientList.contains(id)) {

				clientList.add(id);
			}
			break;
		case Code.MSG:
			roomName = st.nextToken();
			id = st.nextToken();
			while (st.hasMoreTokens()) {
				result += st.nextToken();
			}
			if (roomName.equals(ClientRoomFrame.roomName)) {
				cf.crf.textChatArea.append(result + "\n");
			}
			break;
		case Code.CHATROOM:
			roomName = st.nextToken();
			id = st.nextToken();
			String code = st.nextToken();
			List<String> temp = new ArrayList<String>();

			while (st.hasMoreTokens()) {
				result += st.nextToken();
			}

			if (!clientList.contains(id)) {

				clientList.add(id);
			}

			int index = -1;
			for (ChatRoom cr : chatList) {
				if (cr.getRoomName().equals(roomName)) {
					temp = cr.getUserlist();
					index = chatList.indexOf(cr);
					break;
				}
			}

			if (index == -1) {
				temp.add(id);
				chatList.add(new ChatRoom(roomName, temp));
			} else {

				if (!chatList.get(index).getUserlist().contains(id)) {
					temp.add(id);
					chatList.get(index).setUserlist(temp);
				}

			}

			if (roomName.equals(ClientRoomFrame.roomName)) {
				cf.crf.textChatArea.append(result + "\n");
			}
			break;
		}

	}

}
