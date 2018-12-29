package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day23 {

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class ThreeDimensionalCoordinate extends Coordinate {
        private int z;

        public ThreeDimensionalCoordinate(final int x, final int y, final int z) {
            super(x, y);
            this.z = z;
        }

        @Override
        public String toString() {
            return "<" + x + "," + y + "," + z + ">";
        }

        int manhattanDistance(final ThreeDimensionalCoordinate other) {
            return super.manhattanDistance(other) + Math.abs(z - other.z);
        }
    }

    @Data
    @EqualsAndHashCode
    static class Nanobot {
        private ThreeDimensionalCoordinate pos;
        private int radius;

        public Nanobot(final int x, final int y, final int z, final int radius) {
            this.pos = new ThreeDimensionalCoordinate(x, y, z);
            this.radius = radius;
        }

        @Override
        public String toString() {
            return "pos=" + pos + ", r=" + radius;
        }

        boolean inRangeOf(final Nanobot other) {
            return pos.manhattanDistance(other.pos) <= radius;
        }

        List<Nanobot> getNanobotsInRange(final List<Nanobot> nanobots) {
            return nanobots.stream()
                    .filter(this::inRangeOf)
                    .collect(Collectors.toList());
        }

        static Optional<Nanobot> parse(final String str) {
            try {
                final String[] parts = str.split("=");
                final String[] coords = parts[1].split(",");
                final String radius = parts[2];

                return Optional.of(new Nanobot(
                        Integer.valueOf(coords[0].substring(1).trim()),
                        Integer.valueOf(coords[1]),
                        Integer.valueOf(coords[2].substring(0, coords[2].length() - 1)),
                        Integer.valueOf(radius.trim())
                ));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final List<Nanobot> nanobots = Utils.read("day23.txt")
                .map(Nanobot::parse)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        final Nanobot strongest = nanobots.stream()
                .max(Comparator.comparingInt(Nanobot::getRadius))
                .get();

        System.out.println(strongest.getNanobotsInRange(nanobots).size());
    }

}
