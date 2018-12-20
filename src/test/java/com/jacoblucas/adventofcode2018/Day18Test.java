package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day18.Acre;
import com.jacoblucas.adventofcode2018.Day18.AcreType;
import com.jacoblucas.adventofcode2018.common.Coordinate;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.Day18.AcreType.LUMBERYARD;
import static com.jacoblucas.adventofcode2018.Day18.AcreType.OPEN_GROUND;
import static com.jacoblucas.adventofcode2018.Day18.AcreType.TREES;
import static com.jacoblucas.adventofcode2018.Day18.iterate;
import static com.jacoblucas.adventofcode2018.Day18.parseField;
import static com.jacoblucas.adventofcode2018.Day18.totalResourceValue;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day18Test {

    private Acre[][] exampleField;

    @Before
    public void setUp() {
        exampleField = parseField(Stream.of(
            ".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|."));
    }

    @Test
    public void testParseAcre() {
        assertThat(AcreType.parse('.').get(), is(OPEN_GROUND));
        assertThat(AcreType.parse('|').get(), is(TREES));
        assertThat(AcreType.parse('#').get(), is(LUMBERYARD));
        assertThat(AcreType.parse('?').isPresent(), is(false));
    }

    @Test
    public void testGetAdjacentAcres() {
        List<String> adjacents = exampleField[2][2].getAdjacentAcres(exampleField)
                .map(Coordinate::getCoordinateString)
                .collect(Collectors.toList());
        assertThat(adjacents, hasItems(
                new Coordinate(1,1).getCoordinateString(),
                new Coordinate(2,1).getCoordinateString(),
                new Coordinate(3,1).getCoordinateString(),
                new Coordinate(1,2).getCoordinateString(),
                new Coordinate(3,2).getCoordinateString(),
                new Coordinate(1,3).getCoordinateString(),
                new Coordinate(2,3).getCoordinateString(),
                new Coordinate(3,3).getCoordinateString()
        ));

        adjacents = exampleField[0][0].getAdjacentAcres(exampleField)
                .map(Coordinate::getCoordinateString)
                .collect(Collectors.toList());

        assertThat(adjacents, hasItems(
                new Coordinate(1,0).getCoordinateString(),
                new Coordinate(0,1).getCoordinateString(),
                new Coordinate(1,1).getCoordinateString()
        ));

        adjacents = exampleField[9][9].getAdjacentAcres(exampleField)
                .map(Coordinate::getCoordinateString)
                .collect(Collectors.toList());
        assertThat(adjacents, hasItems(
                new Coordinate(8,9).getCoordinateString(),
                new Coordinate(8,8).getCoordinateString(),
                new Coordinate(9,8).getCoordinateString()
        ));
    }

    @Test
    public void testAcreIterate() {
        // lumberyard -> open
        Acre acre = exampleField[0][1];
        assertThat(acre.getAcreType(), is(LUMBERYARD));
        acre.iterate(exampleField);
        assertThat(acre.getAcreType(), is(OPEN_GROUND));
        acre.setAcreType(LUMBERYARD);

        // lumberyard -> lumberyard
        acre = exampleField[0][8];
        assertThat(acre.getAcreType(), is(LUMBERYARD));
        acre.iterate(exampleField);
        assertThat(acre.getAcreType(), is(LUMBERYARD));

        // open -> trees
        acre = exampleField[6][0];
        assertThat(acre.getAcreType(), is(OPEN_GROUND));
        acre.iterate(exampleField);
        assertThat(acre.getAcreType(), is(TREES));
        acre.setAcreType(OPEN_GROUND);

        // open -> open
        acre = exampleField[0][0];
        assertThat(acre.getAcreType(), is(OPEN_GROUND));
        acre.iterate(exampleField);
        assertThat(acre.getAcreType(), is(OPEN_GROUND));

        // trees -> lumberyard
        acre = exampleField[0][7];
        assertThat(acre.getAcreType(), is(TREES));
        acre.iterate(exampleField);
        assertThat(acre.getAcreType(), is(LUMBERYARD));
        acre.setAcreType(TREES);

        // trees -> trees
        acre = exampleField[1][6];
        assertThat(acre.getAcreType(), is(TREES));
        acre.iterate(exampleField);
        assertThat(acre.getAcreType(), is(TREES));
    }

    @Test
    public void testIterate() {
        iterate(exampleField, 10);

        final Acre[][] expectedIterationResult = parseField(Stream.of(
                ".||##.....",
                "||###.....",
                "||##......",
                "|##.....##",
                "|##.....##",
                "|##....##|",
                "||##.####|",
                "||#####|||",
                "||||#|||||",
                "||||||||||"));

        assertThat(exampleField, is(expectedIterationResult));
    }

    @Test
    public void testTotalResourceValue() {
        final Acre[][] field = parseField(Stream.of(
                ".||##.....",
                "||###.....",
                "||##......",
                "|##.....##",
                "|##.....##",
                "|##....##|",
                "||##.####|",
                "||#####|||",
                "||||#|||||",
                "||||||||||"));

        assertThat(totalResourceValue(field), is(1147L));
    }

}
