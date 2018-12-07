package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

    @Data
    @RequiredArgsConstructor
    static class Instruction {
        @NonNull private String id;
        private List<String> childIds = new ArrayList<>();
        private boolean executed = false;

        public void addChildId(final String childId) {
            childIds.add(childId);
        }

        public void execute() {
            executed = true;
        }
    }

    public static String stepOrder(final Map<String, Instruction> instructionMap) {
        Optional<Instruction> next = getNext(instructionMap);
        final List<Instruction> order = new ArrayList<>();
        while (next.isPresent()) {
            final Instruction nextInstruction = next.get();
            instructionMap.get(nextInstruction.id).execute();
            order.add(nextInstruction);
            next = getNext(instructionMap);
        }

        return order.stream()
                .map(Instruction::getId)
                .collect(Collectors.joining());
    }

    private static Optional<Instruction> getNext(final Map<String, Instruction> instructionMap) {
        // next is the next unexecuted instruction that no one depends on
        // if there are multiple, return the earlier letter in the alphabet
        final List<Instruction> next = instructionMap.entrySet().stream()
                .filter(e -> !e.getValue().isExecuted())
                .filter(e -> getDependencies(e.getKey(), instructionMap).count() == 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        if (next.isEmpty()) {
            return Optional.empty();
        } else {
            next.sort(Comparator.comparing(Instruction::getId));
            return Optional.of(next.get(0));
        }
    }

    private static Stream<Instruction> getDependencies(final String instructionId, final Map<String, Instruction> instructionMap) {
        return instructionMap.entrySet().stream()
                .filter(e1 -> !e1.getValue().isExecuted())
                .filter(e2 -> !e2.getKey().equals(instructionId) && e2.getValue().getChildIds().contains(instructionId))
                .map(Map.Entry::getValue);
    }

    static Map<String, Instruction> parse(final Stream<String> rawInstructions) {
        final Map<String, Instruction> instructionMap = new HashMap<>();

        rawInstructions.forEach(str -> {
            final String[] parts = str.split(" ");
            final String id = parts[1];

            final Instruction instruction;
            if (instructionMap.containsKey(id)) {
                instruction = instructionMap.get(id);
            } else {
                instruction = new Instruction(id);
            }

            final String childId = parts[7];
            instruction.addChildId(childId);
            if (!instructionMap.containsKey(childId)) {
                instructionMap.put(childId, new Instruction(childId));
            }
            instructionMap.put(id, instruction);
        });

        return instructionMap;
    }

    public static void main(String[] args) throws IOException {
        final Map<String, Instruction> instructionMap = parse(Utils.read("day7.txt"));
        System.out.println(Day7.stepOrder(instructionMap));
    }

}
