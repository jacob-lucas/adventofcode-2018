package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 {

    @Data
    @RequiredArgsConstructor
    static class Instruction {
        private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        @NonNull private String id;
        private List<String> childIds = new ArrayList<>();
        private boolean executed = false;

        public void addChildId(final String childId) {
            childIds.add(childId);
        }

        public void execute() {
            executed = true;
        }

        public int executionTime(final int base) {
            return base + ALPHABET.indexOf(id) + 1;
        }
    }

    @Data
    @RequiredArgsConstructor
    static class Worker {
        @NonNull private int id;
        private int availableAt = 0;
        private String assignedInstructionId = "";

        public boolean isAvailable(final int tick) {
            return availableAt <= tick;
        }

        public void assign(final Instruction instruction, final int assignmentTick, final int base) {
            availableAt = assignmentTick + instruction.executionTime(base);
            assignedInstructionId = instruction.getId();
        }

        public void completeWork() {
            assignedInstructionId = "";
        }
    }

    public static String stepOrder(final Map<String, Instruction> instructionMap) {
        List<Instruction> next = getNext(instructionMap);
        final List<Instruction> order = new ArrayList<>();
        while (!next.isEmpty()) {
            final Instruction nextInstruction = next.get(0);
            instructionMap.get(nextInstruction.id).execute();
            order.add(nextInstruction);
            next = getNext(instructionMap);
        }

        return order.stream()
                .map(Instruction::getId)
                .collect(Collectors.joining());
    }

    public static int executionTime(Map<String, Instruction> instructionMap, int numWorkers, int base) {
        int tick = 0;
        final Map<Integer, List<String>> completionTimeMap = new HashMap<>();
        final List<Worker> workers = IntStream.range(0, numWorkers)
                .boxed()
                .map(Worker::new)
                .collect(Collectors.toList());

        while (true) {
            // check for any tasks that need completing and free up the worker
            if (completionTimeMap.containsKey(tick)) {
                List<String> completedInstructions = completionTimeMap.get(tick);
                completedInstructions.forEach(id -> {
                    instructionMap.get(id).execute();
                    workers.forEach(w -> {
                        if (w.getAssignedInstructionId().equals(id)) {
                            w.completeWork();
                        }
                    });
                });
            }

            // get new work
            final List<Instruction> nextInstructions = getNext(instructionMap);
            if (nextInstructions.isEmpty()) {
                // no more work
                break;
            }

            // attempt assignment
            for (final Instruction instruction : nextInstructions) {
                // only assign this instruction if it's not being worked on
                if (workers.stream().noneMatch(w -> w.getAssignedInstructionId().equals(instruction.id))) {
                    int now = tick;
                    final Optional<Worker> availableWorker = workers.stream().filter(w -> w.isAvailable(now)).findFirst();
                    if (availableWorker.isPresent()) {
                        // assign to this worker
                        availableWorker.get().assign(instruction, tick, base);

                        // store the completion time
                        final int completionTick = tick + instruction.executionTime(base);
                        List<String> is = new ArrayList<>();
                        if (completionTimeMap.containsKey(completionTick)) {
                            is = completionTimeMap.get(completionTick);
                        }
                        is.add(instruction.id);
                        completionTimeMap.put(completionTick, is);
                    }
                }
            }

            tick++;
        }

        return tick;
    }

    private static List<Instruction> getNext(final Map<String, Instruction> instructionMap) {
        // next is the next unexecuted instruction that no one depends on
        // if there are multiple, return the earlier letter in the alphabet
        return instructionMap.entrySet().stream()
                .filter(e -> !e.getValue().isExecuted())
                .filter(e -> getAvailableDependencies(e.getKey(), instructionMap).count() == 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private static Stream<Instruction> getAvailableDependencies(final String instructionId, final Map<String, Instruction> instructionMap) {
        // available dependencies are instructions which are not yet executed, and where the provided instruction ID is listed as a child
        return instructionMap.entrySet().stream()
                .filter(e -> !e.getValue().isExecuted() && e.getValue().getChildIds().contains(instructionId))
                .map(Map.Entry::getValue);
    }

    static Map<String, Instruction> parse(final Stream<String> rawInstructions) {
        final Map<String, Instruction> instructionMap = new HashMap<>();

        rawInstructions.forEach(str -> {
            final String[] parts = str.split(" ");
            final String id = parts[1];
            final String childId = parts[7];

            final Instruction instruction = instructionMap.getOrDefault(id, new Instruction(id));
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

        // reset
        instructionMap.values().forEach(i -> i.setExecuted(false));

        System.out.println(Day7.executionTime(instructionMap, 5, 60));
    }

}
