import java.io.*;
import java.net.*;

class SimpleUDPClient {
	public static void main(String args[]) {
		// Declara o socket UDP
		DatagramSocket clientSocket = null;
		try {
			// Instancia o socker UDP (define que ele deve usar a porta 10.000)
			clientSocket = new DatagramSocket();
			// Cria array de bytes que será enviado para o servidor
			byte[] sendArray = args[0].getBytes();
			// Ip e porta do servidor
			InetAddress IpServidor = InetAddress.getByName(args[1]);
			int port = 6789;
			// Cria um pacote UDP (array, tamanho do array, ip, porta)
			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, port);
			// Envia o pacote UDP
			clientSocket.send(sendPacket);
			System.out.println("Pacote enviado!");

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Fecha o socket 
			if (clientSocket != null) clientSocket.close();
		}
	}
}
