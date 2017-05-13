package me;

import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class PrimeUtils {

    public boolean isPrime(long n) {
        return n>1 && IntStream.rangeClosed(2, (int)sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }
}
