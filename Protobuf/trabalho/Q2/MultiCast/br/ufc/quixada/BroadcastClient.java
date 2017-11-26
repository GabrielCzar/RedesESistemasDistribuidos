package br.ufc.quixada;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.ufc.quixada.services.Calculadora;
import br.ufc.quixada.services.Calculadora.Request;
import br.ufc.quixada.services.Calculadora.Request.Operacao;
import br.ufc.quixada.services.Services;
import br.ufc.quixada.services.Services.Reply;
import br.ufc.quixada.services.Services.Type;

class BroadcastClient {
	private static int PORT_SERVER = 5555;
	private static int MY_PORT = 5556;
	private static Scanner sc;

	public static void main(String args[]) {
		initClient(MY_PORT);
	}

	public static void initClient(int port) {
		DatagramSocket socket = null;
		try {
			// Digitar mensagem usando o teclado

			socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			
			ByteArrayOutputStream aOutput = new ByteArrayOutputStream(1024);
			Services.Request requestServices = Services.Request.newBuilder()
					.setOp(Type.forNumber(readDataDevice())).build();

			requestServices.writeDelimitedTo(aOutput);
			Reply replyServices = Services.Reply.newBuilder().setPort(MY_PORT).build();
			replyServices.writeDelimitedTo(aOutput);
			byte sendArray[] = aOutput.toByteArray();
			InetAddress broadcast = InetAddress.getByName("255.255.255.255"); // or aReceivePacket.getAddress();
			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, broadcast, PORT_SERVER);
			socket.send(sendPacket);

			byte[] receiveArray = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
			socket.receive(receivePacket);
			ByteArrayInputStream aInput = new ByteArrayInputStream(receiveArray);
			replyServices = Services.Reply.parseDelimitedFrom(aInput);
			int porta = replyServices.getPort();
			

			if (porta != 2 && porta != 0 && porta != MY_PORT) {
				menu();
				aOutput = new ByteArrayOutputStream(1024);
				Request request1 = readData();
				request1.writeDelimitedTo(aOutput);
				replyServices = Services.Reply.newBuilder().setPort(MY_PORT).build();
				replyServices.writeDelimitedTo(aOutput);
				byte sendArrayCalculator[] = aOutput.toByteArray();
				sendPacket = new DatagramPacket(sendArrayCalculator, sendArrayCalculator.length, broadcast, porta);
				socket.send(sendPacket);
				
				byte[] receiveArrayCalculator = new byte[1024];
				receivePacket = new DatagramPacket(receiveArrayCalculator, receiveArrayCalculator.length);
				socket.receive(receivePacket);
				aInput = new ByteArrayInputStream(receiveArrayCalculator);
				Calculadora.Reply replyCalculadora = Calculadora.Reply.parseDelimitedFrom(aInput);
				System.out.println("Req. " + replyCalculadora.getId() + " -> Resultado: " + replyCalculadora.getRes());
			}else {
				System.out.println("Dispositivo não encontrado");
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
		}

	}

	private static Request readData() {
		sc = new Scanner(System.in);
		Request.Builder req = Request.newBuilder();

		System.out.println("Id: ");
		req.setId(sc.nextInt());

		System.out.println("N1: ");
		req.setN1(sc.nextDouble());

		System.out.println("N2: ");
		req.setN2(sc.nextDouble());

		System.out.println("OP: ");
		req.setOp(Operacao.forNumber(sc.nextInt()));

		sc.close();

		return req.build();
	}
	
	public static int readDataDevice() {
		sc = new Scanner(System.in);
		System.out.println("Digite 0 para impressao, 1  Calculadora, 2 Som, 3 projetor");
		int chose = sc.nextInt();
		return chose;
	}

	private static void menu() {
		System.out.println("0 - SOMA");
		System.out.println("1 - SUBTRACAO");
		System.out.println("2 - MULTIPLICACAO");
		System.out.println("3 - DIVISAO");
	}
}
