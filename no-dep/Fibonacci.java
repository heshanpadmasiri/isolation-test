import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;

public class Fibonacci {
    private static final int M = 30;

    private static int fibonacci(int n) throws ExecutionException, InterruptedException {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> fib1 = executor.submit(() -> fibonacci(n - 1));
            Future<Integer> fib2 = executor.submit(() -> fibonacci(n - 2));

            int f1 = fib1.get();
            int f2 = fib2.get();
            return f1 + f2;
        }
    }

    public static void main(String[] args) {
        try {
            int result = fibonacci(M);
            if (result == 0) {
                throw new RuntimeException("Unexpected result: computation was optimized away");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
