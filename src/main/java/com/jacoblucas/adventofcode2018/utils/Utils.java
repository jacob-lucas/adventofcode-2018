package com.jacoblucas.adventofcode2018.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utils {

    public static Stream<String> read(final String filename) throws IOException {
        return Files.lines(Paths.get("src/main/resources/" + filename));
    }

}
