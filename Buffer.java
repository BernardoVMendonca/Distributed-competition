

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Buffer {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 1999;
    private static final ArrayList<Integer> ARRAY_SERVER_PORT = new ArrayList<Integer>(Arrays.asList(2899, 2999));

    private static ArrayList<String> QUEUE_REQUEST = new ArrayList<>();

    public synchronized static void addRequest(String request) {
        QUEUE_REQUEST.add(request);
    }

    public synchronized static void removeFirstRequest() {
        QUEUE_REQUEST.remove(0);
    }

    public synchronized static ArrayList<String> getQueue() {
        return QUEUE_REQUEST;
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    static class RequestToServer implements Runnable {
        private String request;
        private int serverId;
        private int sleepTime;

        public RequestToServer(String request, int serverId, int sleepTime) {
            this.request = request;
            this.serverId = serverId;
            this.sleepTime = sleepTime;
        }

        private Socket connectToServer(Socket socket) throws IOException {
            while (true) {
                try {
                    socket = new Socket(SERVER_IP, ARRAY_SERVER_PORT.get(serverId));
                    break;
                } catch (SocketException e) {
                    // e.printStackTrace();
                    continue;
                } catch (IOException e) {
                    // e.printStackTrace();
                    continue;
                }
            }

            return socket;
        }

        @Override
        public void run() {
            Socket socket = null;
            PrintWriter out;

            try {
                socket = connectToServer(socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                // System.out.println(request);
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(request + " : " + sleepTime);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class RequestFromBalancer implements Runnable {
        private ServerSocket listener;

        @Override
        public void run() {
            try {
                while (true) {
                    listener = new ServerSocket(PORT);
                    // System.out.println("[BUFFER] Esperando por conexão com balanceador");
                    Socket client = listener.accept();

                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    String request = in.readLine();
                    String[] parsedRequest;

                    if (request != null) {
                        parsedRequest = request.split(":");

                        System.out.println("[BUFFER] conectado ao [BALANCER] " + parsedRequest[0]);

                        addRequest(parsedRequest[1]);
                    }
                    listener.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        Thread listenFromBalancer = new Thread(new RequestFromBalancer());

        try {
            listenFromBalancer.start();
        } catch (IllegalThreadStateException e) {
            e.printStackTrace();
        }

        ArrayList<Thread> ServerThread = new ArrayList<Thread>();
        while (true) {
            if (getQueue().size() > 0) {
                try {
                    int sleepTime = getRandomNumber(500, 2000);
                    for (int i = 0; i < ARRAY_SERVER_PORT.size(); i++) { // Dois pois há apenas dois servidores
                        Thread newThread = new Thread(new RequestToServer(QUEUE_REQUEST.get(0), i, sleepTime));
                        ServerThread.add(newThread);
                        ServerThread.get(i).start();
                        ServerThread.get(i).join();
                    }

                    ServerThread.removeAll(ServerThread);
                } catch (IllegalThreadStateException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    removeFirstRequest();
                }
            }
        }
    }
}
