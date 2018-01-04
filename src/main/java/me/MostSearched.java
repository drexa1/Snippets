package me;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by dr186049 on 9/21/2017.
 */
public class MostSearched {

    private static Map<String, Integer> citiesSearched = new HashMap<>();

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        int count;
        count = Integer.parseInt(in.nextLine());
        for(int i = 0; i < count; i++) {
            String input = in.nextLine();
            citiesSearched.put(input, citiesSearched.getOrDefault(input, 0) + 1);
        }
        String mostSearched = Collections.max(citiesSearched.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println(mostSearched);
    }

}
