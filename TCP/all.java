import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

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
================================= Servidor ==============================
import java.io.*;
import java.net.*;

class SimpleUDPServer {
	public static void main(String args[]) {
		// Declara socket UDP
		DatagramSocket serverSocket = null;
		try {
			// Instancia socker UDP (define que ele deve usar a porta 6789)
			serverSocket = new DatagramSocket(6789);
			System.out.println("Servidor em execução!");
			// Cria array de bytes que será enviado para o servidor
			byte[] receiveData = new byte[1024];
			int id = 0;
			// Cria loop para receber mais de uma msg
			while (true) {
				id++;
				System.out.println("Esperando Msg " + id + " ...");
				// Cria pacote para receber a mensagem UDP
				DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
				// Espera a chegada de uma msg (bloqueante)
				serverSocket.receive(request);
				// Armazena a mensagem que chegou no formato String
				String sentence = new String(request.getData(), 0, request.getLength());
				// Mostra informações do cliente
				System.out.println("Cliente: " + request.getAddress().getHostAddress() + " - Porta: " + request.getPort());
				System.out.println("Msg: " + sentence);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Fecha o socket 
			if (serverSocket != null) serverSocket.close();
		}
	}
}
=========================== Cliente TCP ======================================
import java.io.*;
import java.net.*;

class SimpleTCPClient {
	public static void main(String argv[]) {
		// Declara as mensagens da aplicação
		String requisicao;
		String resposta;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		// Declara o socket
		Socket clientSocket;
		try {
			// Instancia o socket
			clientSocket = new Socket("localhost", 6789);
			// Instancia objeto que escreve no buffer de saída
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			// Instancia objeto que lê buffer de entrada
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
			System.out.println("Escreva MSG a ser enviada:");
			// Ler entrada do teclado
			requisicao = inFromUser.readLine();
			// Enviar mensagem digitada para o servidor
			outToServer.writeUTF(requisicao + '\n');
			// Esperar resposta do servidor
			resposta = inFromServer.readUTF();
			System.out.println("FROM SERVER: " + resposta);
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
========================= Servidor TCP =======================================
import java.io.*;
import java.net.*;

class SimpleTCPServer {
	public static void main(String argv[]) {
		// Declara o socket do servidor
		ServerSocket listenSocket;
		// Declara objetos necessários às regras de negócio da aplicação
		String msgRequisicao;
		String msgResposta;
		try {
			// Instancia o socket que vai escutar na porta 6789
			listenSocket = new ServerSocket(6789);
			System.out.println("Esperando conexões");
			// Faz um loop para lidar com as requisições
			while (true) {
				// Instancia socket que lida com as requisições de UM cliente
				Socket connectionSocket = listenSocket.accept();
				// Instancia objeto que lê buffer de entrada
				DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
				// Instancia objeto que escreve no buffer de saída
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				// Lê msg enviada pelo cliente
				msgRequisicao = inFromClient.readUTF();
				// Transforma a msg recebida
				msgResposta = msgRequisicao.toUpperCase() + '\n';
				// Escreve msg de resposta para o cliente
				outToClient.writeUTF(msgResposta);
				// Fecha o socket
				connectionSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

