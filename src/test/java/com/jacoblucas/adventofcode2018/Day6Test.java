package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day6Test {

    private static final List<Coordinate> coordinates = Arrays.asList(
            new Coordinate(1,1),   // 1
            new Coordinate(1,6),   // 2
            new Coordinate(8,3),   // 3
            new Coordinate(3,4),   // 4
            new Coordinate(5,5),   // 5
            new Coordinate(8,9)    // 6
    );

    @Test
    public void testFindClosestCoordinateIndexWithOneAnswer() {
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(0, 0), coordinates), is(0));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(0, 5), coordinates), is(1));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(6, 0), coordinates), is(2));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(2, 3), coordinates), is(3));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(5, 8), coordinates), is(4));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(8, 8), coordinates), is(5));
    }

    @Test
    public void testFindClosestCoordinateIndexWithManyAnswers() {
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(5, 0), coordinates), is(-1));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(1, 4), coordinates), is(-1));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(0, 4), coordinates), is(-1));
        assertThat(Day6.findClosestCoordinateIndex(new Coordinate(1, 4), coordinates), is(-1));
    }

    @Test
    public void testLargestFiniteArea() {
        assertThat(Day6.largestFiniteArea(coordinates), is(17));
    }

}
