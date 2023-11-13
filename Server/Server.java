import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server extends Thread {

    private final int id;
    private final int port;

    private PrintWriter out;

    public Server(int id, int port) throws IOException {
        this.id = id;
        this.port = port;
    }

    public void Listen() throws IOException {
        ServerSocket listener = new ServerSocket(port);

        try {
            System.out.println("[SERVER] " + id + " Esperando por conexão com buffer");
            Socket buffer = listener.accept();
            System.out.println("[SERVER] " + id + " Buffer conectado");

            getBufferRequest(buffer);

            buffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }

    public void getBufferRequest(Socket buffer) throws IOException {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(buffer.getInputStream()));
            String request = in.readLine();

            if (request != null) {
                // System.out.println(request);

                if (request.startsWith("w")) {
                    updateFile(request);
                    out = new PrintWriter(buffer.getOutputStream(), true);
                    out.println(id + ": Write done");
                } else {
                    readFile();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        System.out.println("read");
    }

    private void updateFile(String request) {
        System.out.println(request);
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void run() {
        while (true)
            try {
                Listen();
                int sleepTime = getRandomNumber(500, 2000);
                Thread.sleep(sleepTime);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

    }
}
