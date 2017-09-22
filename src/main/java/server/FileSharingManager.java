package server;

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileSharingManager {

	private static FileSharingManager instance;

	private Lock registerLock;
	
	private List<Socket> connections;
	private Map<InetAddress, File> files;
	
	public static FileSharingManager instance() {
		if (instance == null)
			instance = new FileSharingManager();
		
		return instance;
	}
	
	private FileSharingManager() {
		registerLock = new ReentrantLock();
		
		connections = new ArrayList<>();
		files = new HashMap<>();
	}

	public void register(Socket conexao, File arquivoRecebido) {
		registerLock.lock();
		try {
			connections.add(conexao);
			files.put(conexao.getInetAddress(), arquivoRecebido);
			
			shareAllFilesWith(conexao);
		} finally {
			registerLock.unlock();
		}
	}

	private void shareAllFilesWith(Socket conexao) {
		HashMap<InetAddress, File> filesToSend = new HashMap<>(files);
		filesToSend.remove(conexao.getInetAddress());
		
		SendFilesTo sendFilesTo = new SendFilesTo(conexao, filesToSend.values());
		Thread thread = new Thread(sendFilesTo);
		thread.start();
	}

}
