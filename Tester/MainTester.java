package Tester;

import java.io.IOException;

public class MainTester {
    public static void main(String[] args) throws IOException {
        Tester teste = new Tester("Tester/teste.txt");

        // teste.readFile();
        teste.updateFile("write 4 and 10");
        teste.readFile();
    }
}
