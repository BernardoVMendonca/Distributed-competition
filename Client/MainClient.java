package Client;

import java.io.IOException;

public class MainClient {
	public static void main(String[] args) throws IOException {
		Client client1 = new Client(1);
		Client client2 = new Client(2);
		
		client1.start();
		//client2.start();
	}
}
