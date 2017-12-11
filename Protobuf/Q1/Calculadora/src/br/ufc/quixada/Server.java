package br.ufc.quixada;

import static br.ufc.quixada.calc.Calculadora.Request.Operacao.DIV;
import static br.ufc.quixada.calc.Calculadora.Request.Operacao.MUL;
import static br.ufc.quixada.calc.Calculadora.Request.Operacao.SOM;
import static br.ufc.quixada.calc.Calculadora.Request.Operacao.SUB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufc.quixada.calc.Calculadora.Reply;
import br.ufc.quixada.calc.Calculadora.Request;
import br.ufc.quixada.calc.Calculadora.Request.Operacao;

public class Server {

	public static void main(String[] args) {
		initServer(9999);
	}

	public static void initServer(int port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
					
			System.out.println("Servidor em execução!");
			
			while (true) {
				Socket socket = serverSocket.accept();
				
				Request request = Request.parseDelimitedFrom(socket.getInputStream());
				
				System.out.println("Request from: " + socket.getLocalAddress() + ":" + socket.getPort());
				
				Double result = calcule(request);
					
				Reply reply = Reply.newBuilder().setId(request.getId()).setRes(result).build();
				
				reply.writeDelimitedTo(socket.getOutputStream());	
			}
			
		} catch (Exception e) {
			System.out.println("Erro no servidor \n" + e);
		} finally { if(serverSocket != null)
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	private static double calcule(Request request) {
		Operacao op = request.getOp();
		double num1 = request.getN1(),
				num2 = request.getN2();
		
		if (op == SOM) 
			return num1 + num2;
		 else if (op == SUB) 
			return num1 - num2;
		 else if (op == MUL) 
			return num1 * num2;
		 else if (op == DIV && num2 != 0)
				return num1 / num2;
		else return 0;
	}
}
