package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 {

    public static int largestFiniteArea(final List<Coordinate> coordinates) {
        final int gridSize = gridSize(coordinates);
        final Map<Integer, Integer> coordinateIndexToAreaMap = new HashMap<>();
        final Set<Integer> infiniteAreaIndices = new HashSet<>();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                final int closestCoordinateIndex = findClosestCoordinateIndex(new Coordinate(x, y), coordinates);
                if (coordinateIndexToAreaMap.containsKey(closestCoordinateIndex)) {
                    coordinateIndexToAreaMap.put(closestCoordinateIndex, coordinateIndexToAreaMap.get(closestCoordinateIndex) + 1);
                } else {
                    coordinateIndexToAreaMap.put(closestCoordinateIndex, 1);
                }

                if (x == 0 || x == gridSize - 1 || y == 0 || y == gridSize - 1) {
                    infiniteAreaIndices.add(closestCoordinateIndex);
                }
            }
        }

        final List<Map.Entry<Integer, Integer>> finiteAreaMap = coordinateIndexToAreaMap.entrySet()
                .stream()
                .filter(e -> !infiniteAreaIndices.contains(e.getKey()))
                .collect(Collectors.toList());

        return Collections.max(finiteAreaMap, Map.Entry.comparingByValue()).getValue();
    }

    public static int areaNearMostCoordinates(final List<Coordinate> coordinates, final int cap) {
        final int gridSize = gridSize(coordinates);
        int areaNear = 0;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                final Coordinate coordinate = new Coordinate(x, y);
                if (coordinates.stream()
                        .mapToInt(c -> c.manhattanDistance(coordinate))
                        .sum() < cap) {
                    areaNear++;
                }
            }
        }
        return areaNear;
    }

    private static int gridSize(final List<Coordinate> coordinates) {
        final int maxX = coordinates.stream()
                .max(Comparator.comparingInt(Coordinate::getX))
                .get()
                .getX() + 1;

        final int maxY = coordinates.stream()
                .max(Comparator.comparingInt(Coordinate::getY))
                .get()
                .getY() + 1;

        return Math.max(maxX, maxY);
    }

    static int findClosestCoordinateIndex(final Coordinate coordinate, final List<Coordinate> coordinates) {
        int minCoordinateIndex = 0;

        int minDistance = Integer.MAX_VALUE;
        for (final Coordinate c : coordinates) {
            final int distance = c.manhattanDistance(coordinate);
            if (distance < minDistance) {
                minDistance = distance;
                minCoordinateIndex = coordinates.indexOf(c);
            } else if (distance == minDistance) {
                minCoordinateIndex = -1;
            }
        }

        return minCoordinateIndex;
    }

    public static void main(String[] args) throws IOException {
        final List<Coordinate> coordinates = Utils.read("day6.txt")
                .map(Coordinate::parse)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        System.out.println(Day6.largestFiniteArea(coordinates));
        System.out.println(Day6.areaNearMostCoordinates(coordinates, 10000));
    }
}
