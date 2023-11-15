

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Balancer extends Thread {

    private final int id;
    private final int port;

    private static final String SERVER_IP = "127.0.0.1";
    private static final int BUFFER_PORT = 1999;

    private Socket socket;
    private PrintWriter out;

    public Balancer(int id, int port) throws IOException {
        this.id = id;
        this.port = port;
    }

    public void Listen() throws IOException {
        ServerSocket listener = new ServerSocket(port);
        
        try {
//            System.out.println("[BALANCER] " + id + " Esperando por conexão com cliente");
            Socket client = listener.accept();
//            System.out.println("[BALANCER] " + id + " Cliente conectado");

            getClientRequest(client);

            client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }

    public void getClientRequest(Socket client) throws IOException {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String request = in.readLine();

            if (request != null) {
                // System.out.println(request);

                while (true) {
                    try {
                        socket = new Socket(SERVER_IP, BUFFER_PORT);
                        break;
                    } catch (SocketException e) {
                        continue;
                    }
                }

                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(id + ":" + request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true)
            try {
                Listen();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    
    public void sleep(int duration) throws InterruptedException {
    	Thread.sleep(duration);
    }
}
