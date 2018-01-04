package main.me;

import me.Fibonacci;
import org.junit.Test;
import org.mockito.Spy;

/**
 * Created by dr186049 on 6/2/2017.
 */
public class FibonacciTest {

    @Spy
    private final Fibonacci fib = new Fibonacci();

    @Test
    public void fibonacciTest() {
        try {
            System.out.println(fib.recFibonacciMemo(500000));
        } catch (StackOverflowError  e) {
            System.out.println("fibonacci memo: exception");
        }
    } //2ms fail

    @Test
    public void fibonacci8Test() {
        try {
            System.out.println(fib.fibonacci8(500000));
        } catch (StackOverflowError  e) {
            System.out.println("fibonacci8 memo: exception");
        }
    } //97ms fail

    @Test
    public void iterFibonacciTest() {
        try {
            System.out.println(fib.iterFibonacci(5000000));
        } catch (StackOverflowError  e) {
            System.out.println("fibonacci iter: exception");
        }
    } //6m 26s 882ms success

    @Test
    public void iterFibonacciMemoTest() {
        try {
            System.out.println(fib.iterFibonacciMemo(5000000L));
        } catch (StackOverflowError  e) {
            System.out.println("fibonacci iter: exception");
        }
    } //6m 11s 494ms success

}
