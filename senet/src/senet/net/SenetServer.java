package senet.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import senet.logic.exception.InvalidInputException;

public class SenetServer implements Runnable{
	
	private ServerSocket server;
	private Socket client;
	
	private PrintWriter print;
	private Scanner in;
	
	private SenetProtocol sgp;
	
	public SenetServer(int port) throws InvalidInputException{		//7782
		
		try {
			
			server = new ServerSocket(port);
			
			client = server.accept();
			System.out.println("S:accepted");
			
			in = new Scanner(client.getInputStream());
			print = new PrintWriter(client.getOutputStream(), true);
			
			while (true){
				
				String response = readMsg();
	
				System.out.println("S:"+response);
				sgp.processInput(response);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally{
			
			close();
		}
	}

	@Override
	public void run() {
		
		System.out.println("S:server thread started");
	}
	
	public String readMsg() throws IOException {
		
		String msg = in.nextLine();
		System.out.println(msg);
		
		return msg;
	}

	public void writeMsg(String msg) throws IOException {
		
		print.println(msg);
		//print.flush();
	}
	
	public void close(){
		
		in.close();
		print.close();
	}

}
