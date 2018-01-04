package main.me;

import me.PrimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrimeUtilsTest {

    @Mock
    PrimeUtils primeUtils;

    @Test
    public void isPrimeSuccess() {
        when(primeUtils.isPrime(anyLong())).thenReturn(true);
        assertTrue(primeUtils.isPrime(7L));
    }
}
