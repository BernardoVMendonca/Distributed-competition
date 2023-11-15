import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
    	System.out.println("MainServerStart");
        Server server_1 = new Server(1, 2899, "DataBase-1.txt");
        Server server_2 = new Server(2, 2999, "DataBase-2.txt");

        server_1.start();
        server_2.start();
    }
}
