import java.net.*;

public class Nslookup {
	public static void main(String[] args) {
		InetAddress[] ip;
		try {
			String site = "www.microsoft.com.br";
			//ip = InetAddress.getByName(site);
			ip = InetAddress.getAllByName(site);
			for (int i = 0; i < ip.length; i++) {
				System.out.println(ip[i].toString());
			}
		} catch (UnknownHostException e) {
			System.out.println("Endereço desconhecido");
		}
	}
}
