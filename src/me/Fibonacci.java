package me;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dr186049 on 5/31/2017.
 */
public class Fibonacci {

    private static List<BigInteger> FIBONACCI_LIST = new ArrayList<>();
    static {
        FIBONACCI_LIST.add(BigInteger.ZERO);
        FIBONACCI_LIST.add(BigInteger.ONE);
    }
    public BigInteger recFibonacciMemo(final int n) {
        if (isInFibonacciList(n)) {
            return FIBONACCI_LIST.get(n);
        }
        BigInteger fibonacci = recFibonacciMemo(n - 1).add(recFibonacciMemo(n - 2));
        FIBONACCI_LIST.add(fibonacci);
        return fibonacci;
    }
    private boolean isInFibonacciList(final int number) {
        return number < FIBONACCI_LIST.size();
    }

    private static Map<Integer, Long> memo = new HashMap<>();
    static {
        memo.put(0,0L); //fibonacci(0)
        memo.put(1,1L); //fibonacci(1)
    }
    public Long fibonacci8(final int x) {
        return memo.computeIfAbsent(x, n -> Math.addExact(fibonacci8(n-1), fibonacci8(n-2)));
    }

    public BigInteger iterFibonacci(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        BigInteger prevPrev = BigInteger.ZERO;
        BigInteger prev = BigInteger.ONE;
        BigInteger result = BigInteger.ZERO;

        for (int i = 2; i <= n; i++) {
            result = prev.add(prevPrev);
            prevPrev = prev;
            prev = result;
        }
        return result;
    }

    private static Map<Long, BigDecimal> iterMemo = new HashMap<Long, BigDecimal>();
    static {
        iterMemo.put(0L, BigDecimal.ZERO);
        iterMemo.put(1L, BigDecimal.ONE);
    }
    public BigDecimal iterFibonacciMemo(Long n) {
        if (0 == n) {
            return BigDecimal.ZERO;
        } else if (1 == n) {
            return BigDecimal.ONE;
        } else {
            if (iterMemo.containsKey(BigDecimal.valueOf(n))) {
                return iterMemo.get(BigDecimal.valueOf(n));
            } else {
                BigDecimal prevPrev = BigDecimal.ZERO,
                           prev = BigDecimal.ONE,
                           result = BigDecimal.ZERO;

                for (int i = 2; i <= n; i++) {
                    result = prev.add(prevPrev);
                    prevPrev = prev;
                    prev = result;
                }
                iterMemo.put(n, result);
                return result;
            }
        }
    }

}
