package me;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

public class Average {

    public static void main(String[] args) {
        System.out.println(average(2,1));
        System.out.println(average(45,34));
    }

    public static double average(int a, int b) {
        BigDecimal sum = (new BigDecimal(a).add(new BigDecimal(b)));
        return sum.divide(new BigDecimal(2L), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public static double average8(int a, int b) {
        return Stream.of(new BigDecimal(a), new BigDecimal(b)).mapToDouble(BigDecimal::doubleValue).average().getAsDouble();
    }
}

