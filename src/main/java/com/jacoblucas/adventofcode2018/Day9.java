package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {

    public static long getHighScore(final int numPlayers, final int lastMarbleWorth) {
        final Map<Integer, Long> scoreMap = new HashMap<>();
        IntStream.range(1, numPlayers+1).forEach(i -> scoreMap.put(i, 0L));

        final Vector<Integer> circle = new Vector<>();
        int currentMarbleIndex = 0;
        int currentPlayer = 1;
        circle.add(0);

        for (int marble = 1; marble <= lastMarbleWorth; marble++) {
            final int circleSize = circle.size();
            final int lastPlacedMarbleIndex = currentMarbleIndex;

            // find the position the marble will be placed at
            if (circleSize == 1 || circleSize == 2) {
                currentMarbleIndex = 1;
            } else {
                currentMarbleIndex += 2;
                if (currentMarbleIndex > circleSize) {
                    currentMarbleIndex = 1;
                }
            }

            if (marble % 23 == 0) {
                // special case
                int sevenMarblesCCW = lastPlacedMarbleIndex - 7;
                if (sevenMarblesCCW < 0) {
                    sevenMarblesCCW = circleSize - Math.abs(sevenMarblesCCW);
                }

                // current player keeps the current marble, adding to their score, plus the value 7 marbles CCW from currentMarbleIndex
                scoreMap.put(currentPlayer, scoreMap.get(currentPlayer) + marble + circle.get(sevenMarblesCCW));

                // remove and reset current marble index
                circle.removeElementAt(sevenMarblesCCW);
                currentMarbleIndex = sevenMarblesCCW;
            } else {
                // place the marble
                circle.insertElementAt(marble, currentMarbleIndex);
            }

            // figure out who the next player is
            currentPlayer++;
            if (currentPlayer > numPlayers) {
                currentPlayer = 1;
            }
        }

        return scoreMap.values().stream().max(Long::compareTo).get();
    }

    public static void main(String[] args) throws IOException {
        final String[] inputParts = Utils.read("day9.txt")
                .collect(Collectors.toList())
                .get(0)
                .split(" ");

        final int numPlayers = Integer.valueOf(inputParts[0]);
        final int lastMarbleWorth = Integer.valueOf(inputParts[6]);

        System.out.println(getHighScore(numPlayers, lastMarbleWorth));
        System.out.println(getHighScore(numPlayers, lastMarbleWorth * 100)); // takes about an hour to run :(
    }
}
