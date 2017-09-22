package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

public class SendFilesTo implements Runnable {

	private Socket conexao;
	private Collection<File> filesToSend;

	public SendFilesTo(Socket conexao, Collection<File> filesToSend) {
		this.conexao = conexao;
		this.filesToSend = filesToSend;
	}

	@Override
	public void run() {
		for (File file : filesToSend) {
			send(file);
		}
	}

	private void send(File file) {
		try {
			String fileInfo = String.valueOf(file.length()) + "!" + file.getName() + "\n";
			conexao.getOutputStream().write(fileInfo.getBytes());
			
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1];
			while (fis.read(buffer) != -1)
				conexao.getOutputStream().write(buffer);
			
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
