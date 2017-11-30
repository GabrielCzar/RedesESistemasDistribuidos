package br.ufc.quixada;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import br.ufc.quixada.calc.Calculadora.Reply;
import br.ufc.quixada.calc.Calculadora.Request;
import br.ufc.quixada.calc.Calculadora.Request.Operacao;

public class Client {
	public static void main(String[] args) {
		initClient("localhost", 9999);
	}
	
	public static void initClient(String ip, int port) {
		Socket clientSocket = null;
		menu();
		
		try {
			clientSocket = new Socket(ip, port);
			Request request = readData();
			
			request.writeDelimitedTo(clientSocket.getOutputStream());
			
			Reply reply = Reply.parseDelimitedFrom(clientSocket.getInputStream());
			
			System.out.println("Req. " + reply.getId() + " -> Resultado: " + reply.getRes());
			
		} catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){System.out.println("IO: " + e.getMessage());
		} finally {if(clientSocket != null)
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}}
	}
	
	private static Request readData() {
		Scanner scanner = new Scanner(System.in);
		Request.Builder req = Request.newBuilder();
		
		System.out.println("Id: ");
		req.setId(scanner.nextInt());
		
		System.out.println("N1: ");
		req.setN1(scanner.nextDouble());
		
		System.out.println("N2: ");
		req.setN2(scanner.nextDouble());
		
		System.out.println("OP: ");
		req.setOp(Operacao.forNumber(scanner.nextInt()));
		
		scanner.close();
		
		return req.build();
	}
	
	private static void menu() {
		System.out.println("0 - SOMA");
		System.out.println("1 - SUBTRACAO");
		System.out.println("2 - MULTIPLICACAO");
		System.out.println("3 - DIVISAO");
	}
}
