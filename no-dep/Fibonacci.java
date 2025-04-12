import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fibonacci {
    private static final int M = 30;

    private static CompletableFuture<Integer> fibonacci(int n, ExecutorService executorService)
            throws ExecutionException, InterruptedException {
        if (n <= 0) {
            return CompletableFuture.completedFuture(0);
        }
        if (n == 1) {
            return CompletableFuture.completedFuture(1);
        }

        CompletableFuture<Integer> fib1 = fibonacci(n - 1, executorService);
        CompletableFuture<Integer> fib2 = fibonacci(n - 2, executorService);

        return fib1.thenCombine(fib2, (a, b) -> a + b);
    }


    public static void main(String[] args) {
        try {
            int result = fibonacci(M, Executors.newVirtualThreadPerTaskExecutor()).get();
            if (result == 0) {
                throw new RuntimeException("Unexpected result: computation was optimized away");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
