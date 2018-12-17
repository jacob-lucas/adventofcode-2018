package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 {

    @Data
    @AllArgsConstructor
    static class Note {
        String rule;
        boolean containsPlant;

        static Note parse(final String str) {
            final String[] parts = str.split(" ");
            return new Note(parts[0], parts[2].contains("#"));
        }
    }

    @Data
    @AllArgsConstructor
    static class Pot {
        int id;
        boolean containsPlant;
    }

    public static List<Pot> spreadN(final List<Pot> pots, final List<Note> notes, final long n) {
        System.out.println("0: " + Day12.toString(pots));
        List<Pot> result = pots;

        int i = 0;
        while (i < n) {
            result = spread(result, notes);
            i++;
        }

        System.out.println(n + ": " + Day12.toString(result));
        return result;
    }

    static List<Pot> spread(final List<Pot> pots, final List<Note> notes) {
        pots.add(0, new Pot(pots.get(0).id - 1, false));
        pots.add(new Pot(pots.get(pots.size() - 1).id + 1, false));

        final List<Pot> next = new ArrayList<>();
        final String potString = Day12.toString(pots);
        for (int i = 0; i < pots.size(); i++) {
            final Pot pot = pots.get(i);
            boolean matched = false;

            for (final Note n : notes) {
                final int lower = Math.max(0, i - 2);
                final int upper = Math.min(i + 3, pots.size());
                String matchStr = potString.substring(lower, upper);
                if (matchStr.length() < n.rule.length()) {
                    if (i - 2 < 0) {
                        // need to prepend
                        for (int j=i-2; j<0; j++) {
                            matchStr = "." + matchStr;
                        }
                    } else {
                        // need to append
                        for (int j=pots.size(); j<i+3; j++) {
                            matchStr = matchStr + ".";
                        }
                    }
                }
                if (n.rule.equals(matchStr)) {
                    next.add(new Pot(pot.id, n.containsPlant));
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                next.add(new Pot(pot.id, false));
            }
        }

        return next;
    }

    static List<Pot> getInitialState(final String state) {
        final String initialState = state.split(":")[1].trim();
        return IntStream.range(0, initialState.length())
                .mapToObj(i -> new Pot(i, initialState.charAt(i) == '#'))
                .collect(Collectors.toList());
    }

    static String toString(final List<Pot> pots) {
        return pots.stream()
                .map(p -> p.isContainsPlant() ? "#" : ".")
                .collect(Collectors.joining());
    }

    public static long potSum(final List<Pot> pots) {
        return pots.stream()
                .filter(Pot::isContainsPlant)
                .mapToInt(Pot::getId)
                .sum();
    }

    public static void main(String[] args) throws IOException {
        final Iterator<String> iterator = Utils.read("day12.txt").iterator();

        // create the initial state of all the pots
        final List<Pot> pots = getInitialState(iterator.next());

        // skip the blank line
        iterator.next();

        // capture all the notes
        final List<Note> notes = new ArrayList<>();
        iterator.forEachRemaining(s -> notes.add(Note.parse(s)));

        // part 1
        final List<Pot> afterTwenty = spreadN(pots, notes, 20);
        System.out.println(potSum(afterTwenty));
    }

}
