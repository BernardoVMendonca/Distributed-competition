package Tester;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester {

    private final String fileName;

    public Tester(String fileName) throws IOException {
        this.fileName = fileName;
    }

    public void readFile() {
        System.out.println(fileName);
        File file = new File(fileName);

        try {
            // System.out.println("Attempting to read from file in:
            // "+file.getCanonicalPath());
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                System.out.println(linha);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // BufferedReader reader;
        // try {
        // reader = new BufferedReader(new FileReader(file));

        // String line;
        // while ((line = reader.readLine()) != null)
        // System.out.println(line);

        // reader.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    public void updateFile(String request) {
        // System.out.println(request);

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

            writer.newLine();
            writer.write("O MDC entre " + numbers.get(0) + " e " + numbers.get(1) + " é " + GCD);
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
}
