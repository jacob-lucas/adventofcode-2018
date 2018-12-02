package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

    public static Long checksum(final Stream<String> boxIDs) {
        final List<String> boxIDList = boxIDs.collect(Collectors.toList());
        return containsN(boxIDList, 2) * containsN(boxIDList, 3);
    }

    private static long containsN(final List<String> boxIDList, final long n) {
        return boxIDList.stream()
                .filter(id -> containsN(id, n))
                .count();
    }

    private static boolean containsN(final String str, final long n) {
        return str.chars()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .containsValue(n);
    }

    public static void main(String[] args) throws IOException {
        final Stream<String> input = Utils.read("day2.txt");
        System.out.println(checksum(input));
    }

}
