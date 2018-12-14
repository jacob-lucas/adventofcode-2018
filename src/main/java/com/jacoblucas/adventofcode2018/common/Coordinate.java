package com.jacoblucas.adventofcode2018.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class Coordinate {
    protected int x, y;

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public int manhattanDistance(final Coordinate other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public static Optional<Coordinate> parse(final String str) {
        final String[] parts = str.split(",");
        try {
            return Optional.of(new Coordinate(Integer.valueOf(parts[0].trim()), Integer.valueOf(parts[1].trim())));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
