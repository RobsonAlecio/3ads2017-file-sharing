package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer {

	
//	private static class ClasseInterna implements Runnable {
//
//		public void run() {
//		}
//
//	}

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(7082);
		
		while (true) {
			Socket conexao = server.accept();
			
			ReceberArquivoERegistrar tarefa = new ReceberArquivoERegistrar(conexao);
			Thread execucaoTarefa = new Thread(tarefa);
			
			execucaoTarefa.start();
			
//			Runnable tarefaOnTheFly = new Runnable() {
//				
//				public void run() {
//				}
//			};
//			
//			new Thread(new ClasseInterna());
//			
//			new Thread(tarefaOnTheFly);
		}
	}
}
