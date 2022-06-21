package msg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	
	Integer type;
	String id;
	LocalDateTime time;
	String msg;
	
	String protocol;
	
	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
	public Message(Integer type, LocalDateTime time, String id, String msg) {
		this.type = type;
		this.id = id;
		this.msg = msg;
		this.time = time;
		makeMsg();
	}
	
	public Message(Integer type, String id, String msg) {
		this.type = type;
		this.id = id;
		this.msg = msg;
		this.time = LocalDateTime.now();
		makeMsg();
	}

	public Message(String protocol) {
		this.protocol = protocol;
		parse();
	}
	
	/**
	 * recv string to Object
	 */
	private void parse() {
		String[] temp = protocol.split("\\|");
		try {	
			type = Integer.parseInt(temp[0]);
		} catch (Exception e) {
			type = -1;
			
		}
		try {
			time = LocalDateTime.parse(temp[1], formatter);
		} catch (Exception e) {
			time = LocalDateTime.now();
		}
		
		try {
			id = temp[2];
		} catch (Exception e) {
			id = "";
		}
		
		msg = "";
		for(int i = 3; i < temp.length; i++) {
			if(i != 3) msg += "|";
			msg += temp[i];
		}
	}

	/**
	 * send Object to string
	 */
	private void makeMsg() {
		protocol = "";
		protocol += type + "|";
		protocol += time.format(formatter) + "|";
		protocol += id + "|";
		protocol += msg + "|";
	}

	public Integer getType() {
		return type;
	}
	
	public LocalDateTime getTime() {
		return time;
	}

	public String getId() {
		return id;
	}

	public String getMsg() {
		return msg;
	}

	public String getProtocol() {
		return protocol;
	}
	
}
