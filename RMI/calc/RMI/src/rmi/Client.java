package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Calc proxy = (Calc) registry.lookup("Calc");
            String response = proxy.sayHello();
            System.out.println("Resposta: " + response);
            double resultado = proxy.mul(20, 35);
            System.out.println("Mul 20 * 35 = " + resultado);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
