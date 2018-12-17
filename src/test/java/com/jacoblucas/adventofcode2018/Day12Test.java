package com.jacoblucas.adventofcode2018;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.Day12.Note;
import static com.jacoblucas.adventofcode2018.Day12.Pot;
import static com.jacoblucas.adventofcode2018.Day12.getInitialState;
import static com.jacoblucas.adventofcode2018.Day12.spread;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day12Test {

    private final List<Note> notes = Stream.of(
            "...## => #",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => #",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #")
            .map(Note::parse)
            .collect(Collectors.toList());

    private List<Pot> pots;

    @Before
    public void setUp() {
        pots = getInitialState("initial state: #..#.#..##......###...###");
    }

    @Test
    public void testParseNote() {
        final Note n1 = Note.parse("...## => #");
        assertThat(n1.rule, is("...##"));
        assertThat(n1.containsPlant, is(true));

        final Note n2 = Note.parse("...#. => .");
        assertThat(n2.rule, is("...#."));
        assertThat(n2.containsPlant, is(false));
    }

    @Test
    public void testToString() {
        assertThat(Day12.toString(pots), is("#..#.#..##......###...###"));
    }

    @Test
    public void testSpread() {
        final List<Pot> generationOne = spread(pots, notes);
        assertThat(Day12.toString(generationOne), is(".#...#....#.....#..#..#..#."));
    }

    @Test
    public void testSpreadExpandsListOfPots() {
        List<Pot> expandedPots = spread(pots, notes);
        assertThat(Day12.toString(expandedPots), containsString("#...#....#.....#..#..#..#")); // 1

        expandedPots = spread(expandedPots, notes);
        assertThat(Day12.toString(expandedPots), containsString("##..##...##....#..#..#..##")); // 2

        expandedPots = spread(expandedPots, notes);
        assertThat(Day12.toString(expandedPots), containsString("#.#...#..#.#....#..#..#...#")); // 3
    }

    @Test
    public void testSpreadN() {
        final List<Pot> potsAfterTwentyGens = Day12.spreadN(pots, notes, 20);
        assertThat(Day12.toString(potsAfterTwentyGens), containsString("#....##....#####...#######....#.#..##"));
    }

}
