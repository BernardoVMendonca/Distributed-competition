
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
			Socket client = listener.accept();

			getClientRequest(client);

			client.close();
		} catch (IOException e) {
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
		// Forma de derrubar um dos balanceadores
		boolean aux = true;
		while (true) {
			try {
				if (aux && id == 1)
					Thread.sleep(4000);
				aux = false;
				Listen();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
