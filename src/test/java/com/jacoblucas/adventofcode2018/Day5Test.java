package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.Iterator;

import static com.jacoblucas.adventofcode2018.Day5.unitList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day5Test {

    @Test
    public void testUnitParse() {
        final Iterator<Day5.Unit> unitList = unitList("aAbB").iterator();

        assertThat(unitList.next().getType(), is('a'));
        assertThat(unitList.next().getType(), is('A'));
        assertThat(unitList.next().getType(), is('b'));
        assertThat(unitList.next().getType(), is('B'));
    }

    @Test
    public void testReact() {
        assertThat(Day5.react(unitList("")), is(false));
        assertThat(Day5.react(unitList("aA")), is(true));
        assertThat(Day5.react(unitList("abBA")), is(true));
        assertThat(Day5.react(unitList("abAB")), is(false));
        assertThat(Day5.react(unitList("aabAAB")), is(false));
        assertThat(Day5.react(unitList("dabAcCaCBAcCcaDA")), is(true));
    }

    @Test
    public void testFullyReact() {
        assertThat(Day5.fullyReact(unitList("dabAcCaCBAcCcaDA")), is("dabCBAcaDA"));
    }

}
