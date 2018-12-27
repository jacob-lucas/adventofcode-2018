package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 {

    private static final Coordinate CAVE_MOUTH = new Coordinate(0, 0);

    @Data
    static class Cave {
        enum RegionType {
            ROCKY(0),
            WET(1),
            NARROW(2);

            @Getter private final int riskLevel;

            RegionType(final int riskLevel) {
                this.riskLevel = riskLevel;
            }
        }

        private final int depth;
        private final Coordinate target;
        private final Map<Coordinate, Integer> geologicIndexCache;

        public Cave(final int depth, final Coordinate target) {
            this.depth = depth;
            this.target = target;
            this.geologicIndexCache = new HashMap<>();
        }

        int getGeologicIndexAt(final int x, final int y) {
            final Coordinate coordinate = new Coordinate(x, y);
            if (geologicIndexCache.containsKey(coordinate)) {
                return geologicIndexCache.get(coordinate);
            }

            final int index;
            if (coordinate.equals(CAVE_MOUTH) || coordinate.equals(target)) {
                index = 0;
            } else if (y == 0) {
                index = x * 16807;
            } else if (x == 0) {
                index = y * 48271;
            } else {
                index = getErosionLevelAt(x - 1, y) * getErosionLevelAt(x, y - 1);
            }

            geologicIndexCache.put(coordinate, index);
            return index;
        }

        int getErosionLevelAt(final int x, final int y) {
            return (getGeologicIndexAt(x, y) + depth) % 20183;
        }

        RegionType getRegionTypeAt(final int x, final int y) {
            final int erosionLevel = getErosionLevelAt(x, y);
            switch (erosionLevel % 3) {
                case 0: return RegionType.ROCKY;
                case 1: return RegionType.WET;
                case 2: return RegionType.NARROW;
                default:
                    throw new RuntimeException("unable to determine region type for cave coordinate " + this);
            }
        }

        public int getRiskLevel() {
            return IntStream.range(CAVE_MOUTH.getY(), target.getY() + 1)
                    .flatMap(y -> IntStream.range(CAVE_MOUTH.getX(), target.getX() + 1)
                            .mapToObj(x -> new Coordinate(x, y))
                            .mapToInt(c -> getRegionTypeAt(c.getX(), c.getY()).getRiskLevel()))
                    .sum();
        }
    }

    public static void main(String[] args) throws IOException {
        final List<String> input = Utils.read("day22.txt").collect(Collectors.toList());
        final String[] targetStr = input.get(1).split(":")[1].trim().split(",");

        final int depth = Integer.valueOf(input.get(0).split(":")[1].trim());
        final Coordinate target = new Coordinate(Integer.valueOf(targetStr[0]), Integer.valueOf(targetStr[1]));

        final Cave cave = new Cave(depth, target);

        System.out.println(cave.getRiskLevel());
    }

}
