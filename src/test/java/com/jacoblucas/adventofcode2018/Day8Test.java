package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.Day8.Node;
import static com.jacoblucas.adventofcode2018.Day8.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day8Test {

    private final Node d = new Node(Collections.emptyList(), Collections.singletonList(99));
    private final Node c = new Node(Collections.singletonList(d), Collections.singletonList(2));
    private final Node b = new Node(Collections.emptyList(), Arrays.asList(10, 11, 12));
    private final Node a = new Node(Arrays.asList(b, c), Arrays.asList(1, 1, 2));

    @Test
    public void testParse() {
        final List<Node> nodes = parse(
                Stream.of(2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2)
                        .mapToInt(Integer::intValue));

        assertThat(nodes, is(Collections.singletonList(a)));
    }

    @Test
    public void testGetMetadataSum() {
        assertThat(a.getMetadataSum(), is(138));
    }

    @Test
    public void testGetValue() {
        assertThat(a.getValue(), is(66));
    }

}
