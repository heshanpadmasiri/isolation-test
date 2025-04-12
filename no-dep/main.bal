const m = 30;

isolated function fibonacci(int n) returns int|error {
    if (n <= 0) {
        return 0;
    }
    if (n == 1) {
        return 1;
    }

    future<int|error> fib1 = start fibonacci(n - 1);
    future<int|error> fib2 = start fibonacci(n - 2);

    int f1 = check wait fib1;
    int f2 = check wait fib2;
    return f1 + f2;
}

public function main() {
    future<int|error> f = start fibonacci(m);
    int|error val = wait f;
    if val is error {
        panic val;
    }
}
