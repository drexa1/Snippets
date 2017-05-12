import org.junit.Test;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by dr186049 on 5/12/2017.
 */
public class FizzBuzzTest {

    FizzBuzz fizzBuzz = new FizzBuzz();

    @Test
    public void fizzBuzzTest() {
        IntStream.rangeClosed(1, 100).forEach(n -> System.out.println(fizzBuzz.fizzBuzz(n)));
    }

    @Test
    public void fizzBuzzTest8() {
        IntStream.rangeClosed(1, 100).forEach(n -> System.out.println(fizzBuzz.fizzBuzz8(n)));
    }
}
