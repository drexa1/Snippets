package me;

import java.math.BigInteger;

/**
 * Created by dr186049 on 5/12/2017.
 */
public class IntegerBoundaries {

    public static void main(String[] args) {
        int riskyInt = Integer.MAX_VALUE-1;
        System.out.println("Checking int limit (" + Integer.MAX_VALUE + "): " + (riskyInt+2));

        Integer riskyInteger = Integer.MAX_VALUE-1;
        System.out.println("Checking Integer limit (" + Integer.MAX_VALUE + "): " + Integer.sum(riskyInteger,2));

        System.out.println("Checking Integer limit 1 (" + Integer.MAX_VALUE + "): " + checkedAdd(riskyInteger,1));

        try {
            System.out.println("Checking Integer limit 2 (" + Integer.MAX_VALUE + "): " + checkedAdd(riskyInteger,2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        BigInteger bigInteger = new BigInteger(String.valueOf(Integer.MAX_VALUE));
        System.out.println("Checking BigInteger limit: " + bigInteger.add(new BigInteger("5465465465465422112")));
    }

    protected static int checkedAdd(int a, int b) {
        boolean overflow = a > Integer.MAX_VALUE - b;
        long upperType = a + b; // bitwise check
        if(upperType >>> 32 != 0) {
            throw new ArithmeticException("Overflow summing " + a + " + " + b);
        } else {
            return a+b;
        }
    }
}
