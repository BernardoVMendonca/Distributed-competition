package Buffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 1999;
    private static final ArrayList<Integer> SERVER_PORT = new ArrayList<Integer>(Arrays.asList(2899, 2999));
    private static Queue<String> resquestQueue = new LinkedList<>();

    static class RequestToServer implements Runnable {
        private String request;

        public RequestToServer(String request) {
            this.request = request;
        }

        @Override
        public void run() {
            System.out.println("Testando a cricao de threads   " + request);
            // Socket socket;
            // PrintWriter out;
            // try {
            // socket = new Socket(SERVER_IP, SERVER_PORT.get(0));

            // out = new PrintWriter(socket.getOutputStream(), true);
            // out.println(request);

            // } catch (SocketException e) {
            // e.printStackTrace();
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                System.out.println("[BUFFER] Esperando por conexão com balanceador");
                Socket client = listener.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String request = in.readLine();
                String[] parsedRequest;

                if (request != null) {
                    parsedRequest = request.split(":");

                    System.out.println("[BUFFER] conectado ao [BALANCER] " + parsedRequest[0]);
                    System.out.println(parsedRequest[1]);

                    resquestQueue.add(parsedRequest[1]);
                    // System.out.println(resquestQueue);

                    ArrayList<Thread> ServerThread = new ArrayList<Thread>();
                    if (parsedRequest[1].startsWith("w")) {
                        for (int i = 0; i < 2; i++) { // Dois pois há apenas dois servidores
                            Thread newThread = new Thread(new RequestToServer(parsedRequest[1]));
                            ServerThread.add(newThread);
                            ServerThread.get(i).start();
                        }
                        try {
                            for (int i = 0; i < 2; i++)
                                ServerThread.get(i).join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }
}
