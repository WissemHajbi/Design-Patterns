package strategy;

// Logging in the console.
public class ConsoleLogStrategy implements LogStrategy {

    @Override
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
