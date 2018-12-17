package com.jacoblucas.adventofcode2018;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class Day14 {

    @Data
    @AllArgsConstructor
    static class Iteration {
        private String scores;
        private int elf1Index;
        private int elf2Index;
    }

    public static String tenScoresAfter(final String initialRecipes, final int n) {
        final int elf1Index = 0;
        final int elf2Index = 1;

        Iteration iteration = iterate(initialRecipes, elf1Index, elf2Index);
        while (iteration.scores.length() < n + 10) {
            iteration = iterate(iteration.getScores(), iteration.getElf1Index(), iteration.getElf2Index());
        }

        return iteration.getScores().substring(n, n + 10);
    }

    static Iteration iterate(final String initialRecipes, final int elf1Index, final int elf2Index) {
        final List<Integer> scores = initialRecipes.chars()
                .map(c -> c - '0')
                .boxed()
                .collect(Collectors.toList());

        final int elf1Score = scores.get(elf1Index);
        final int elf2Score = scores.get(elf2Index);
        final int total = elf1Score + elf2Score;

        final String iteratedRecipes = initialRecipes + total;

        return new Iteration(iteratedRecipes, getNextIndex(iteratedRecipes, elf1Index, elf1Score), getNextIndex(iteratedRecipes, elf2Index, elf2Score));
    }

    private static int getNextIndex(final String iteratedRecipes, final int index, final int score) {
        return (index + 1 + score) % iteratedRecipes.length();
    }

    public static void main(String[] args) {
        System.out.println(tenScoresAfter("37", 330121));
    }

}
