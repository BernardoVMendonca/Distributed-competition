import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Server extends Thread {
    private final int id;
    private final int port;
    private final String fileName;

    public static int sleepTime;

    public Server(int id, int port, String fileName) throws IOException {
        this.id = id;
        this.port = port;
        this.fileName = fileName;
    }

    public void Listen() throws IOException {
        ServerSocket listener = new ServerSocket(port);

        try {
            Socket buffer = listener.accept();

            getBufferRequest(buffer);

            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            listener.close();
        }
    }

    public void getBufferRequest(Socket buffer) throws IOException {
        String completeRequest;
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(buffer.getInputStream()));
            completeRequest = in.readLine();

            if (completeRequest != null) {
                String[] parsedRequest = completeRequest.split(" : ");
                String request = parsedRequest[0];
                sleepTime = Integer.parseInt(parsedRequest[1]);
                
                if (request.startsWith("w")) {
                    updateFile(request);
                } else {
                    readFile();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFile() {
        File file = new File(fileName);

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                System.out.println(linha);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateFile(String request) {
        String[] parsedRequest = request.split(" ");
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for (String word : parsedRequest) {
            try {
                int number = Integer.parseInt(word);
                numbers.add(number);
            } catch (NumberFormatException e) {
            }
        }

        int GCD = calculateGCD(numbers.get(0), numbers.get(1));

        File file = new File(fileName);

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));

            writer.write("O MDC entre " + numbers.get(0) + " e " + numbers.get(1) + " é " + GCD);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateGCD(Integer number1, Integer number2) {
        while (number2 != 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void run() {
        while (true)
            try {
                Listen();
                Thread.sleep(sleepTime);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
