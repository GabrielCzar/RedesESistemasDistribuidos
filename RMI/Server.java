package exemplo.ufc.br;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Calc {
	
	public Server() {
	}

	public String sayHello() {
		return "Hello, world!";
	}

	@Override
	public double mul(double n1, double n2) throws RemoteException {
		return n1 * n2;
	}

	public double soma(double n1, double n2) throws RemoteException {
		return n1 + n2;
	}

	public static void main(String args[]) {
		try {
			Server obj = new Server();
			Calc stub = (Calc) UnicastRemoteObject.exportObject(obj, 0);

			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Calc", stub);

			System.err.println("Servidor pronto");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}

