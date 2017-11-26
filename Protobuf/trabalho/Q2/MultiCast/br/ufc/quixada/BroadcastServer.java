package br.ufc.quixada;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.ufc.quixada.services.Services;
import br.ufc.quixada.services.Services.Reply;
import br.ufc.quixada.services.Services.Type;

class BroadcastServer {
	
	private static int PORTA_CALCULATOR = 5557;
	
	public static void main(String args[]) {
		initServer(5555);
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
				Services.Request request = Services.Request.parseDelimitedFrom(aInput);
				Services.Type op = request.getOp();				
				Reply reply = Reply.parseDelimitedFrom(aInput);
				int porta = reply.getPort();
				
				if(op ==  Type.CALCULATOR) {
					ByteArrayOutputStream aOutput = new ByteArrayOutputStream(1024);				
					reply = Services.Reply.newBuilder().setId(request.getId()).setPort(PORTA_CALCULATOR).build();
					System.out.println(reply.getPort());
					reply.writeDelimitedTo(aOutput);
					byte sendArray[] = aOutput.toByteArray();
					DatagramPacket rep  = new DatagramPacket(sendArray, sendArray.length, InetAddress.getByName("255.255.255.255"), porta);
					socket.send(rep);
					System.out.println("Enviei");
					
				}else {
					System.out.println("Dispositivo não encontrado");
					ByteArrayOutputStream aOutput = new ByteArrayOutputStream(1024);				
					reply = Services.Reply.newBuilder().clearPort().build();
					System.out.println(reply.getPort());
					reply.writeDelimitedTo(aOutput);
					byte sendArray[] = aOutput.toByteArray();
					DatagramPacket rep  = new DatagramPacket(sendArray, sendArray.length, InetAddress.getByName("255.255.255.255"), porta);
					socket.send(rep);
					System.out.println("Enviei");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}
