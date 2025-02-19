package leafagent;

public class TestCode {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("DDDD");
        }).start();
    }
}