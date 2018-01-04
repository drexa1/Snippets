package me;

import java.util.Optional;

public class FizzBuzz {

    public String fizzBuzz(int number) {
        // Better performance than boolean vars
        StringBuilder sb = new StringBuilder();
        if (number % 3 == 0)
            sb.append("Fizz");
        if (number % 5 == 0)
            sb.append("Buzz");

        if (sb.length()==0)
            sb.append(number);

        return sb.toString();
    }

    public String fizzBuzz8(int number) {
        String result = Optional.of(number).map(n -> (n % 3 == 0 ? "Fizz" : "") + (n % 5 == 0 ? "Buzz" : "")).get();
        return result.isEmpty() ? Integer.toString(number) : result;
    }

}
