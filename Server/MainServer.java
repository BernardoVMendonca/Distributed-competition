import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        Server server_1 = new Server(1, 2899);
        Server server_2 = new Server(2, 2999);

        server_1.start();
        server_2.start();
    }
}
