package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.stream.Stream;

public class Day1 {

    public static int frequency(final Stream<Integer> deltas) {
        return deltas.mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) throws IOException {
        final Stream<String> stream = Utils.read("day1.txt");
        System.out.println(frequency(stream.map(Integer::valueOf)));
    }
}
