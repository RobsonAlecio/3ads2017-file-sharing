package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(7082);
		
		while (true) {
			Socket conexao = server.accept();
			
			ReceberArquivoERegistrar tarefa = new ReceberArquivoERegistrar(conexao);
			Thread execucaoTarefa = new Thread(tarefa);
			
			execucaoTarefa.start();
		}
	}
}
