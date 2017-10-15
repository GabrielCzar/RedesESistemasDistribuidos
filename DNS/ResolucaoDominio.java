import java.net.*;

public class ResolucaoDominio {

	public static void main(String[] args) {
		try {
			String url = "www.microsoft.com.br"; 
			InetAddress [] ip = InetAddress.getAllByName(url);
			for (int i = 0; i < ip.length; i++)
				System.out.println(ip[i].toString());		
			System.out.println("Know Host!");
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host!");
		}
	}
}