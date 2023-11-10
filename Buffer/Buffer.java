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

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        int index = 0;

        Socket socket_server_1, socket_server_2;
        PrintWriter out_server_1, out_server_2;

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

                    if (parsedRequest[1].startsWith("w")) {
                        try {
                            socket_server_1 = new Socket(SERVER_IP, SERVER_PORT.get(0));
                            socket_server_2 = new Socket(SERVER_IP, SERVER_PORT.get(1));

                            out_server_1 = new PrintWriter(socket_server_1.getOutputStream(), true);
                            out_server_1.println(parsedRequest[1]);

                            out_server_2 = new PrintWriter(socket_server_2.getOutputStream(), true);
                            out_server_2.println(parsedRequest[1]);

                            
                        } catch (SocketException e) {
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
