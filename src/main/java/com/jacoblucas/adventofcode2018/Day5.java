package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    @Data
    @Builder
    public static class Unit {
        private final char type;

        int polarity() {
            return Character.isLowerCase(type) ? 1 : -1;
        }

        boolean reactsWith(final Unit that) {
            return Character.toUpperCase(type) == Character.toUpperCase(that.type) && polarity() != that.polarity();
        }

        static Unit parse(final int ch) {
            return Unit.builder()
                    .type((char) ch)
                    .build();
        }
    }

    public static String fullyReact(final List<Unit> polymer) {
        boolean keepReacting = true;
        while (keepReacting) {
            keepReacting = react(polymer);
        }

        return polymer.stream()
                .map(u -> String.valueOf(u.getType()))
                .collect(Collectors.joining());
    }

    static boolean react(final List<Unit> polymer) {
        for (int i = 0; i < polymer.size() - 1; i++) {
            final Unit unit = polymer.get(i);
            final Unit next = polymer.get(i + 1);
            if (unit.reactsWith(next)) {
                polymer.remove(i);
                polymer.remove(i);
                return true;
            }
        }
        return false;
    }

    static List<Day5.Unit> unitList(final String input) {
        return input.chars().mapToObj(Day5.Unit::parse).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        final List<Unit> polymer = unitList(Utils.read("day5.txt").iterator().next());

        System.out.println(fullyReact(polymer).length());
    }

}
