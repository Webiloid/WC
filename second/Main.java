package second;

public class Main {

    private static final int A = -5;
    private static final int B = 1;
    private static final int N = 20;
    private static final double LEARNING_NORM = 0.6;
    private static final int WINDOW_WIDTH = 4;

    public static void main(String[] args) {
        Net net = new Net(N, WINDOW_WIDTH, A, B, LEARNING_NORM);
        net.start();
        System.out.println(net.toString());
    }
}
