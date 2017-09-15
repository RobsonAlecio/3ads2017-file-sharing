package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RunClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 7082);
		
		File file = new File("kotlin_logo.png");
		
		String info = String.valueOf(file.length()) + "!kotlin_logo_para_servidor.png\n";
		socket.getOutputStream().write(info.getBytes());
		
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1];
		while (fis.read(buffer) != -1)
			socket.getOutputStream().write(buffer);
		
		fis.close();
	}
}
