package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public static String findCorrectBoxID(final Stream<String> boxIDs) {
        final List<String> boxIDList = boxIDs.collect(Collectors.toList());
        return findCorrectBoxID(boxIDList, 0);
    }

    private static String findCorrectBoxID(final List<String> boxIDList, final int pos) {
        final Optional<Map.Entry<String, Long>> first = boxIDList.stream()
                .map(s -> s.substring(0, pos) + s.substring(pos + 1))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 2L)
                .findFirst();

        if (first.isPresent()) {
            return first.get().getKey();
        } else {
            return findCorrectBoxID(boxIDList, pos + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(checksum(Utils.read("day2.txt")));
        System.out.println(findCorrectBoxID(Utils.read("day2.txt")));
    }

}
