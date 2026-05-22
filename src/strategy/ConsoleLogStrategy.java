package strategy;


public class ConsoleLogStrategy implements LogStrategy {

    @Override
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
