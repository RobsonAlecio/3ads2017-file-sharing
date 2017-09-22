package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceberArquivoERegistrar implements Runnable {

	private Socket conexao;

	public ReceberArquivoERegistrar(Socket conexao) {
		this.conexao = conexao;
	}

	public void run() {
		try {
			String tamanhoENomeArquivo = receberNomeTamanhoArquivo();
			String[] info = tamanhoENomeArquivo.split("!");
			
			File arquivoRecebido = receberArquivo(info);
			
			FileSharingManager.instance().register(conexao, arquivoRecebido);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File receberArquivo(String[] info) throws IOException {
		long tamanhoAReceber = Long.parseLong(info[0]);
		
		File file = new File(info[1]);
		FileOutputStream fos = new FileOutputStream(file);
		
		byte[] buffer = new byte[1];
		do {
			conexao.getInputStream().read(buffer);
			fos.write(buffer);
			
			tamanhoAReceber--;
		} while (tamanhoAReceber > 0);
		
		fos.close();
		
		return file;
	}

	private String receberNomeTamanhoArquivo() throws IOException {
		String nomeTamanhoArquivo = "";
		byte[] buffer = new byte[1];
		
		do {
			conexao.getInputStream().read(buffer);
			if (buffer[0] != '\n')
				nomeTamanhoArquivo += new String(buffer);
			
		} while (buffer[0] != '\n');
		
		return nomeTamanhoArquivo;
	}

}
