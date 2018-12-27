package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day22.Cave;
import com.jacoblucas.adventofcode2018.common.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static com.jacoblucas.adventofcode2018.Day22.Cave.RegionType.NARROW;
import static com.jacoblucas.adventofcode2018.Day22.Cave.RegionType.ROCKY;
import static com.jacoblucas.adventofcode2018.Day22.Cave.RegionType.WET;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day22Test {

    private Cave cave;

    @Before
    public void setUp() {
        cave = new Cave(510, new Coordinate(10, 10));
    }

    @Test
    public void testGetGeologicIndex() {
        assertThat(cave.getGeologicIndexAt(0, 0), is(0));
        assertThat(cave.getGeologicIndexAt(1, 0), is(16807));
        assertThat(cave.getGeologicIndexAt(0, 1), is(48271));
        assertThat(cave.getGeologicIndexAt(1, 1), is(145722555));
        assertThat(cave.getGeologicIndexAt(10, 10), is(0));
    }

    @Test
    public void testGetErosionLevelAt() {
        assertThat(cave.getErosionLevelAt(0, 0), is(510));
        assertThat(cave.getErosionLevelAt(1, 0), is(17317));
        assertThat(cave.getErosionLevelAt(0, 1), is(8415));
        assertThat(cave.getErosionLevelAt(1, 1), is(1805));
        assertThat(cave.getErosionLevelAt(10, 10), is(510));
    }

    @Test
    public void testGetRegionTypeAt() {
        assertThat(cave.getRegionTypeAt(0, 0), is(ROCKY));
        assertThat(cave.getRegionTypeAt(1, 0), is(WET));
        assertThat(cave.getRegionTypeAt(0, 1), is(ROCKY));
        assertThat(cave.getRegionTypeAt(1, 1), is(NARROW));
        assertThat(cave.getRegionTypeAt(10, 10), is(ROCKY));
    }

    @Test
    public void testGetRiskLevel() {
        assertThat(cave.getRiskLevel(), is(114));
    }

}
