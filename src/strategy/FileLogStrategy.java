package strategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Logging in a text file.
public class FileLogStrategy implements LogStrategy {

    private String fileName = "drawing_logs.txt";

    @Override
    public void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Cannot write log file.");
        }
    }
}
