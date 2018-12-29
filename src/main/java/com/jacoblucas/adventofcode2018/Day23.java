package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.common.Pair;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Day23 {

    private static final ThreeDimensionalCoordinate ORIGIN = new ThreeDimensionalCoordinate(0,0,0);

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

    static int distanceToHighestInRangeCoordinate(final List<Nanobot> nanobots) {
        final PriorityQueue<Pair<Integer, Integer>> q = new PriorityQueue<>(Comparator.comparingInt(Pair::getLeft));
        nanobots.forEach(n -> {
            final int distanceFromZero = ORIGIN.manhattanDistance(n.getPos());
            q.add(Pair.of(Math.max(0, distanceFromZero - n.getRadius()), 1));
            q.add(Pair.of(distanceFromZero + n.getRadius() + 1, -1));
        });

        int count = 0;
        int maxCount = 0;
        int result = 0;

        while (!q.isEmpty()) {
            final Pair<Integer, Integer> p = q.poll();
            count += p.getRight();
            if (count > maxCount) {
                result = p.getLeft();
                maxCount = count;
            }
        }

        return result;
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
        System.out.println(distanceToHighestInRangeCoordinate(nanobots));
    }

}
