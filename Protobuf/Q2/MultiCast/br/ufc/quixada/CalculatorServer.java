package br.ufc.quixada;

import static br.ufc.quixada.services.Calculadora.Request.Operacao.DIV;
import static br.ufc.quixada.services.Calculadora.Request.Operacao.MUL;
import static br.ufc.quixada.services.Calculadora.Request.Operacao.SOM;
import static br.ufc.quixada.services.Calculadora.Request.Operacao.SUB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.ufc.quixada.services.Calculadora.Reply;
import br.ufc.quixada.services.Calculadora.Request;
import br.ufc.quixada.services.Calculadora.Request.Operacao;
import br.ufc.quixada.services.Services;


public class CalculatorServer {
	private static int MY_PORT = 5557;
	
	public static void main(String[] args) {
		initServer(MY_PORT);
	}
	
	public static void initServer(int port) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (true) {
				
				byte[] receiveArray = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
				socket.receive(receivePacket);
				ByteArrayInputStream aInput = new ByteArrayInputStream(receiveArray);
				Request request = Request.parseDelimitedFrom(aInput);
				Double result = calcule(request);
				System.out.println(result);
				Services.Reply replyServices = Services.Reply.parseDelimitedFrom(aInput);
				
				int porta = replyServices.getPort();
				System.out.println(porta);
				ByteArrayOutputStream aOutput = new ByteArrayOutputStream(1024);				
				Reply reply = Reply.newBuilder().setId(request.getId()).setRes(result).build();
				reply.writeDelimitedTo(aOutput);
				byte sendArray[] = aOutput.toByteArray();
				InetAddress broadcast = InetAddress.getByName("255.255.255.255");
				DatagramPacket rep  = new DatagramPacket(sendArray, sendArray.length, broadcast, porta);
				socket.send(rep);
				System.out.println("Aqui");
				
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
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
