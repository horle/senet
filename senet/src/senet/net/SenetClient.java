package senet.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SenetClient implements Runnable{
	
	private Socket server;
	private Scanner in;
	private PrintWriter print;
	
	public SenetClient(String ip, int port) throws UnknownHostException, IOException{
		
		server = new Socket(ip, port);
		in = new Scanner(server.getInputStream());
		print = new PrintWriter(server.getOutputStream());
	}

	public String readMsg() throws IOException {
		
		String msg = in.nextLine();
		in.close();
		
		return msg;
	}

	public void writeMsg(String msg) throws IOException {
		
		print.println(msg);
		print.flush();			//raus, weil autoflush
	}

	@Override
	public void run() {
		
		System.out.println("C:client thread started");
	}
	
	public void close(){
		
		in.close();
		print.close();
	}
}
