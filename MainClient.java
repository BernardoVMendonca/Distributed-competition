

import java.io.IOException;

public class MainClient {
	public static void main(String[] args) throws IOException {
		System.out.println("MainClient start");
		Client client1 = new Client(1);
		Client client2 = new Client(2);

		client1.start();
		client2.start();
	}
}
