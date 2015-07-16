package senet.net;

import java.io.IOException;
import java.net.UnknownHostException;

import senet.logic.exception.InvalidInputException;

public abstract class NetManager {

	private static SenetClient client;
	private static SenetServer server;
	
	public static boolean startServer() throws InvalidInputException{
		
		server = new SenetServer(7782);
		
		Thread t = new Thread(server);
		t.start();
		
		return true;
	}
	
	public static boolean connectTo(String ip, int port) throws UnknownHostException, IOException{
		
		client = new SenetClient(ip, port);
		
		Thread t = new Thread(client);
		t.start();
		
		return true;
	}
	
	public static void sendToServer(String msg) throws IOException{
		
		client.writeMsg(msg);
	}
	
	public static void sendToClient(String msg) throws IOException{
		
		server.writeMsg(msg);
	}
	
	public static String readFromClient() throws IOException{
		
		return server.readMsg();
	}
	
	public static String readFromServer() throws IOException{
		
		return client.readMsg();
	}
	
	public static void main (String[] args){
		try {
			
			if (startServer())
				connectTo("127.0.0.1", 7782);
		
			sendToServer("hi server");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
