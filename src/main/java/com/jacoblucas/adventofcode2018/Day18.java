package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {

    enum AcreType {
        OPEN_GROUND('.'),
        TREES('|'),
        LUMBERYARD('#');

        private final char ch;

        AcreType(char ch) {
            this.ch = ch;
        }

        @Override
        public String toString() {
            return String.valueOf(ch);
        }

        static Optional<AcreType> parse(final char ch) {
            if (ch == '.') {
                return Optional.of(OPEN_GROUND);
            } else if (ch == '|') {
                return Optional.of(TREES);
            } else if (ch == '#') {
                return Optional.of(LUMBERYARD);
            } else {
                System.out.println("Unable to parse acreType from '" + ch + "'");
                return Optional.empty();
            }
        }
    }

    @EqualsAndHashCode(callSuper = true)
    static class Acre extends Coordinate {
        @Getter @Setter(AccessLevel.PACKAGE) private AcreType acreType;

        public Acre(final int x, final int y, final AcreType acreType) {
            super(x, y);
            this.acreType = acreType;
        }

        public static Acre copy(final Acre original) {
            return new Acre(original.getX(), original.getY(), original.getAcreType());
        }

        public Stream<Acre> getAdjacentAcres(final Acre[][] field) {
            return getAllAdjacentCoordinates()
                    .map(c -> {
                        int newX = c.getX();
                        int newY = c.getY();
                        return (newX >= 0 && newX < field[0].length && newY >= 0 && newY < field.length) ? field[newY][newX] : null;
                    })
                    .filter(Objects::nonNull);
        }

        public Acre iterate(final Acre[][] field) {
            final Stream<Acre> adjacentAcres = getAdjacentAcres(field);

            if (acreType == AcreType.OPEN_GROUND) {
                if (adjacentAcres
                        .map(c -> field[c.getY()][c.getX()])
                        .filter(a -> a.acreType == AcreType.TREES)
                        .count() >= 3) {
                    acreType = AcreType.TREES;
                }
            } else if (acreType == AcreType.TREES) {
                if (adjacentAcres
                        .map(c -> field[c.getY()][c.getX()])
                        .filter(a -> a.acreType == AcreType.LUMBERYARD)
                        .count() >= 3) {
                    acreType = AcreType.LUMBERYARD;
                }
            } else if (acreType == AcreType.LUMBERYARD) {
                if (adjacentAcres
                        .map(c -> field[c.getY()][c.getX()])
                        .map(Acre::getAcreType)
                        .filter(at -> at == AcreType.TREES || at == AcreType.LUMBERYARD)
                        .distinct()
                        .count() < 2) {
                    acreType = AcreType.OPEN_GROUND;
                }
            }

            return this;
        }

        @Override
        public String toString() {
            return acreType.toString();
        }
    }

    private static long countAcresByType(final Acre[][] field, final AcreType type) {
        return Arrays.stream(field)
                .flatMap(Arrays::stream)
                .filter(a -> a.acreType == type)
                .count();
    }

    static long totalResourceValue(final Acre[][] field) {
        return countAcresByType(field, AcreType.TREES) * countAcresByType(field, AcreType.LUMBERYARD);
    }

    static Acre[][] parseField(final Stream<String> rawFieldInput) {
        final List<List<Acre>> fieldList = new ArrayList<>();

        final List<String> rawFieldRows = rawFieldInput.collect(Collectors.toList());
        for (int y = 0; y < rawFieldRows.size(); y++) {
            final List<Acre> fieldRow = new ArrayList<>();
            final String rawFieldRow = rawFieldRows.get(y);
            for (int x = 0; x < rawFieldRow.length(); x++) {
                final AcreType acreType = AcreType.parse(rawFieldRow.charAt(x)).get();
                fieldRow.add(new Acre(x, y, acreType));
            }
            fieldList.add(fieldRow);
        }

        return fieldList.stream().map(f -> f.toArray(new Acre[0])).toArray(Acre[][]::new);
    }

    static void printField(final Acre[][] field) {
        for (int y = 0; y < field.length; y++) {
            final StringBuilder sb = new StringBuilder();
            for (int x = 0; x < field[y].length; x++) {
                sb.append(field[y][x]);
            }
            System.out.println(sb.toString());
        }
        System.out.println(" ");
    }

    public static void iterate(Acre[][] field, final int numMinutes) {
        System.out.println("Initial state:");
        printField(field);

        int minute = 0;
        while (minute < numMinutes) {
            iterate(field);
            minute++;
            System.out.println("After " + minute + " minute(s):");
            printField(field);
        }
    }

    private static void iterate(Acre[][] field) {
        final Acre[][] iteratedField = new Acre[field.length][field[0].length];
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                final Acre current = field[y][x];
                iteratedField[y][x] = Acre.copy(current).iterate(field);
            }
        }

        for (int y = 0; y < field.length; y++) {
            if (field[y].length >= 0) {
                System.arraycopy(iteratedField[y], 0, field[y], 0, field[y].length);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // parse the field
        final Acre[][] field = parseField(Utils.read("day18.txt"));

        iterate(field, 10);

        System.out.println(totalResourceValue(field));
    }

}
