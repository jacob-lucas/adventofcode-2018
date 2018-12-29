package com.jacoblucas.adventofcode2018.common;

import lombok.Data;

@Data(staticConstructor = "of")
public class Pair<A, B> {
    private final A left;
    private final B right;
}

