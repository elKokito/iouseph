package iouseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	private String token = null;

	private Socket socket = null;
	private ServerSocket serverSocket = null;

	public void run() {

		System.out.println("Creating server socket on port " + 9999);
		try {
			serverSocket = new ServerSocket(9999);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	  }

	public void connect(String url){
		BufferedReader br = null;
		String response = null;
		OutputStream os = null;
		PrintWriter out = null;
		try {
			socket = serverSocket.accept();
		os = socket.getOutputStream();
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		token = br.readLine();

		response = "<html><h3>MERCI</h3></html>";
		out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println("HTTP/1.1 200 OK");
		out.println("Content-Type: text/html");
		out.println("Content-Length: " + response.length());
		out.println();
		out.println(response);
		out.flush();
		out.close();
	}

	public void close(){
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getToken() {
		/*while(token==null)
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
