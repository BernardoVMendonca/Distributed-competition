package Balancer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Balancer {

    private final int id;
    private final int port;

    private static final String SERVER_IP = "127.0.0.1";
    private static final int BLOCKER_PORT = 999;

    public Balancer(int id, int port) throws IOException {
        this.id = id;
        this.port = port;
    }

    public void Listen() {
            try {
                ServerSocket listener = new ServerSocket(port);

                System.out.println("[SERVIDOR] " + id + " Esperando por conexão com cliente");
                Socket client = listener.accept();
                System.out.println("[SERVIDOR] " + id + " Cliente conectado");
                client.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
