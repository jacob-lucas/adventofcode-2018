package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.Day23.Nanobot;
import static com.jacoblucas.adventofcode2018.Day23.distanceToHighestInRangeCoordinate;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day23Test {

    @Test
    public void testParseNanobot() {
        assertThat(Nanobot.parse("pos=<1,2,3>, r=4").get(), is(new Nanobot(1,2,3,4)));
    }

    @Test
    public void testInRangeOf() {
        final Nanobot p = new Nanobot(0,0,0,4);

        assertThat(p.inRangeOf(new Nanobot(1,0,0,1)), is(true));
        assertThat(p.inRangeOf(new Nanobot(4,0,0,3)), is(true));
        assertThat(p.inRangeOf(new Nanobot(0,5,0,3)), is(false));
        assertThat(p.inRangeOf(new Nanobot(1, 3,1,1)), is(false));
    }

    @Test
    public void testGetNanobotsInRange() {
        final List<Nanobot> nanobots = Stream.of(
                "pos=<0,0,0>, r=4",
                "pos=<1,0,0>, r=1",
                "pos=<4,0,0>, r=3",
                "pos=<0,2,0>, r=1",
                "pos=<0,5,0>, r=3",
                "pos=<0,0,3>, r=1",
                "pos=<1,1,1>, r=1",
                "pos=<1,1,2>, r=1",
                "pos=<1,3,1>, r=1")
                .map(Nanobot::parse)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        assertThat(new Nanobot(0,0,0,4).getNanobotsInRange(nanobots).size(), is(7));
    }

    @Test
    public void testDistanceToHighestInRangeCoordinate() {
        final List<Nanobot> nanobots = Stream.of(
                "pos=<10,12,12>, r=2",
                "pos=<12,14,12>, r=2",
                "pos=<16,12,12>, r=4",
                "pos=<14,14,14>, r=6",
                "pos=<50,50,50>, r=200",
                "pos=<10,10,10>, r=5")
                .map(Nanobot::parse)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        assertThat(distanceToHighestInRangeCoordinate(nanobots), is(36));
    }

}
