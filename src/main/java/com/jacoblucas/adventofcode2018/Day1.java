package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    public static int frequency(final Stream<Integer> deltas) {
        return deltas.mapToInt(Integer::intValue).sum();
    }

    public static int findFirstDuplicateFrequency(final Stream<Integer> deltas) {
        final Set<Integer> seenFrequencies = new HashSet<>();
        int currentFrequency = 0;

        final List<Integer> deltaList = deltas.collect(Collectors.toList());
        while (true) {
            for (final int delta : deltaList) {
                currentFrequency = currentFrequency + delta;
                if (seenFrequencies.contains(currentFrequency)) {
                    return currentFrequency;
                } else {
                    seenFrequencies.add(currentFrequency);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(frequency(getInput()));
        System.out.println(findFirstDuplicateFrequency(getInput()));
    }

    private static Stream<Integer> getInput() throws IOException {
        return Utils.read("day1.txt").map(Integer::valueOf);
    }

}
