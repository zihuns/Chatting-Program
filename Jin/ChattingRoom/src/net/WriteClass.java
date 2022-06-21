package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import view.ClientFrame;
import view.ClientRoomFrame;
import view.IdFrame;

public class WriteClass {
	Socket socket;
	ClientRoomFrame crf;
	ClientFrame cf;
	public int protocol;

	public static List<String> clientList = ReadThread.clientList;

	public WriteClass(Socket socket, ClientRoomFrame crf) {
		this.socket = socket;
		this.crf = crf;
	}

	public WriteClass(Socket socket, ClientFrame cf) {
		this.socket = socket;
		this.cf = cf;
	}

	public void sendId(String id) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

			String msg = "";
//			String id = IdFrame.textIdInput.getText();

			// 첫번째 전송
			if (!clientList.contains(id)) {
				InetAddress iaddr = socket.getLocalAddress();
				String ip = iaddr.getHostAddress(); // xxx.xxx.xxx.xxx

				msg = "[" + id + "]님 로그인(" + ip + ")";

			}
//			// 그 외 전송
//			else {
//				msg = "[" + id + "]" + crf.textInput.getText();
//			}
			protocol = Code.LOGIN;
			String result = toSend(msg, id, "");

			// server로 전송
			pw.println(result);
			pw.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String roomName) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

			String msg = "";
			String id = IdFrame.textIdInput.getText();

			protocol = Code.MSG;

			msg += "[" + id + "]" + crf.textInput.getText();
			String result = toSend(msg, id, roomName);

			// server로 전송
			pw.println(result);
			pw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRoomInfo(String roomName, int checkRoom) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

			String msg = "";
			String id = IdFrame.textIdInput.getText();

			protocol = Code.CHATROOM;

			InetAddress iaddr = socket.getLocalAddress();
			String ip = iaddr.getHostAddress(); // xxx.xxx.xxx.xxx

			List<String> temp = new ArrayList<String>();
			int index = -1;

			msg += "[" + id + "]님 방입장(" + ip + ")";


			String result = toSend(msg, id, roomName);

			// server로 전송
			pw.println(result);
			pw.flush();

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Exit() {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

			String msg = "";
			String id = IdFrame.textIdInput.getText();

			protocol = Code.EXIT;

			String result = toSend(msg, id, "");

			// server로 전송
			pw.println(result);
			pw.flush();

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ExitRoom(String roomName) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

			String msg = "";
			String id = IdFrame.textIdInput.getText();

			protocol = Code.EXITROOM;

			String result = toSend(msg, id, roomName);

			// server로 전송
			pw.println(result);
			pw.flush();

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public String toSend(String msg, String id, String room) {
		String result = msg;

		result = protocol + "|" + room + "|" + id + "|" + msg;

		return result;
	}
}
