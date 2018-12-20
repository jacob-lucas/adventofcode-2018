package com.jacoblucas.adventofcode2018.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class Coordinate {

    public static final Coordinate NW = new Coordinate(-1, -1);
    public static final Coordinate N = new Coordinate(0, -1);
    public static final Coordinate NE = new Coordinate(1, -1);
    public static final Coordinate E = new Coordinate(1, 0);
    public static final Coordinate W = new Coordinate(-1, 0);
    public static final Coordinate SW = new Coordinate(-1, 1);
    public static final Coordinate S = new Coordinate(0, 1);
    public static final Coordinate SE = new Coordinate(1, 1);

    protected int x, y;

    public Stream<Coordinate> getAllAdjacentCoordinates() {
        return Stream.of(NW, N, NE, E, W, SW, S, SE)
                .map(c -> new Coordinate(x + c.getX(), y + c.getY()));
    }

    public String getCoordinateString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public String toString() {
        return getCoordinateString();
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
