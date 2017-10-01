import java.net.*;

public class ResolucaoDominios {

	public static void main(String[] args) {
		try {
			InetAddress ip = InetAddress.getByName("www.ufc.br");
		} catch (UnknownHostException e) {
			System.out.println("Unknown Host");
		}
	}
}