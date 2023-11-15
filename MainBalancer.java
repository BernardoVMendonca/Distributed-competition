

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainBalancer {
    public static void main(String[] args) throws IOException, InterruptedException {
    	System.out.println("MainBalancer start");
        Balancer balancer_1 = new Balancer(1, 12345);
        Balancer balancer_2 = new Balancer(2, 1777);
        
        balancer_1.start();
        balancer_2.start();
        
        
        while(true) {
        	boolean bId = new Random().nextBoolean();
        	if(bId) {
        		System.out.println("Balanceador 1 soninho");
        		balancer_1.sleep(5000);
        	} else {
        		System.out.println("Balanceador 2 soninho");
        		balancer_2.sleep(5000);
        	}       	
        	TimeUnit.MILLISECONDS.sleep(5000);        	        
        }
    }
}
