import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;

class TCPServer {
	private static final String QTD_REQUESTS = "\\REQNUM", TIME_EXECUTION = "\\UPTIME";
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String argv[]) {
		if (argv.length < 1) {
			System.err.println("ERROR:\n 	Insert connection port\n 	java TCPServer 9000");
			return;
		}
		// Declara o socket do servidor
		ServerSocket listenSocket;
		// Declara objetos necessários às regras de negócio da aplicação
		String msgRequisicao;
		String msgResposta;
		int qtdRequests = 0;
		LocalTime initTime = LocalTime.now(ZoneId.systemDefault());

		try {
			// Instancia o socket que vai escutar na porta definida no argv
			listenSocket = new ServerSocket(Integer.parseInt(argv[0]));
			System.out.println("Waiting connections");
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
				// Atualiza quantidade de requisições
				qtdRequests++;
				// Invoca o resultado esperado
				String out = getAnswer(msgRequisicao, initTime, qtdRequests);
				// Escreve msg de resposta para o cliente
				outToClient.writeUTF(out);	
				// Fecha o socket
				connectionSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static Duration duration(LocalTime initTime) {
		return Duration.between(initTime, LocalTime.now(ZoneId.systemDefault()));
	}

	private static String getAnswer(String msgRequisicao, LocalTime initTime, int qtdRequests) {
		if (msgRequisicao.toLowerCase().contains(TIME_EXECUTION.toLowerCase())) {
			Duration duration = duration(initTime);
			LocalTime timeServer = LocalTime.ofNanoOfDay(duration.toNanos());
			return timeServer.format(dtf).toString();
		} if (msgRequisicao.toLowerCase().contains(QTD_REQUESTS.toLowerCase())) 
			return String.valueOf(qtdRequests);
		else
			return "Request Invalid!\n";
	}

}

