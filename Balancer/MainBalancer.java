package Balancer;

import java.io.IOException;

public class MainBalancer {
    public static void main(String[] args) throws IOException {
        Balancer balancer_1 = new Balancer(1, 12345);
        Balancer balancer_2 = new Balancer(2, 777);

        balancer_1.Listen();
        //balancer_2.Listen();
    }
}
