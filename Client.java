

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class Client extends Thread {
    private final int id;
    private static final String SERVER_IP = "127.0.0.1";
    private Socket socket;
    private PrintWriter out;

    public Client(int id) throws IOException {
        this.id = id;
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private Socket connectBalancerPort() {
        try {
            Random random = new Random();
            boolean bool = random.nextBoolean();
            int balancer_port = 1777;

            if (bool) {
                balancer_port = 12345;
            }
            while (true) {
                try {
                    socket = new Socket(SERVER_IP, balancer_port);
                    break;
                } catch (SocketException e) {
                    if (balancer_port == 12345)
                        balancer_port = 1777;
                    else
                        balancer_port = 12345;
                    System.out.println("Troquei de porta: " + balancer_port);
                    continue;
                }
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public void run() {
        Random random = new Random();
        while (true) {
            try {
                boolean isWrite = random.nextBoolean(); // False - Write, True - Read

                int sleepTime = getRandomNumber(50, 200);

                int number_1 = getRandomNumber(2, 1000000);
                int number_2 = getRandomNumber(2, 1000000);

                socket = connectBalancerPort();
                out = new PrintWriter(socket.getOutputStream(), true);

                if (!isWrite) {
                    out.println("write " + number_1 + " - " + number_2);
                } else {
                    out.println("read");
                }

                socket.close();

                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
