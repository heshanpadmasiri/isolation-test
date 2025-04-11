import ballerina/http;

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

service / on new http:Listener(9090) {

    resource function get number/[int n]() returns int|error {
        future<int|error> result = start fibonacci(n);
        return wait result;
    }
}
