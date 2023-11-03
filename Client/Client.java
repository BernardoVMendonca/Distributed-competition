package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.plaf.TreeUI;

public class Client extends Thread {
    private int id;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 666;

    private Socket socket;
    private PrintWriter out;

    public Client(int id) throws IOException {
        this.id = id;
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void run() {
        Random random = new Random();
        while (true) {
            try {
                boolean bool = random.nextBoolean(); // False - Write, True - Read

                int sleepTime = getRandomNumber(50, 200);
                int number_1 = getRandomNumber(2, 1000000);
                int number_2 = getRandomNumber(2, 1000000);

                socket = new Socket(SERVER_IP, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream(), true);

                if (!bool) {
                    System.out.println("Client" + id + "cheking GCD:" + number_1 + "&" + number_2);
                    out.println("Write" + number_1 + "-" + number_2);
                } else {
                    System.out.println("Client Reading");
                    out.println("Read");
                }
                socket.close();

                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
