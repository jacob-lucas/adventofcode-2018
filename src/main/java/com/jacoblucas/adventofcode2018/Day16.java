package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Instruction;
import com.jacoblucas.adventofcode2018.common.Opcode;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.common.Opcode.opcodes;
import static com.jacoblucas.adventofcode2018.common.Register.r0;
import static com.jacoblucas.adventofcode2018.common.Register.r1;
import static com.jacoblucas.adventofcode2018.common.Register.r2;
import static com.jacoblucas.adventofcode2018.common.Register.r3;
import static com.jacoblucas.adventofcode2018.common.Register.r4;
import static com.jacoblucas.adventofcode2018.common.Register.r5;
import static com.jacoblucas.adventofcode2018.common.Register.registerValues;

public class Day16 {

    @Data
    @AllArgsConstructor
    static class Sample {
        int[] beforeRegisterValues;
        Instruction instruction;
        int[] afterRegisterValues;

        @Override
        public String toString() {
            return " Inst=" + instruction + "]\tBefore=" + Arrays.toString(beforeRegisterValues) + "\t\tAfter=" + Arrays.toString(afterRegisterValues);
        }

        List<String> findMatchingOpcodes() {
            return Arrays.stream(opcodes)
                    .collect(Collectors.toMap(Function.identity(), opcode -> {
                        r0.setValue(beforeRegisterValues[0]);
                        r1.setValue(beforeRegisterValues[1]);
                        r2.setValue(beforeRegisterValues[2]);
                        r3.setValue(beforeRegisterValues[3]);
                        r4.setValue(beforeRegisterValues[4]);
                        r5.setValue(beforeRegisterValues[5]);
                        return opcode.apply(instruction);
                    }))
                    .entrySet()
                    .stream()
                    .filter(e -> Arrays.equals(e.getValue(), afterRegisterValues))
                    .map(e -> e.getKey().name())
                    .collect(Collectors.toList());
        }

        public static Sample parse(final String beforeStr, final String instructionStr, final String afterStr) {
            final String[] bp = beforeStr.split(":");
            final int[] before = Arrays.stream(bp[1].trim()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .replaceAll(",", "")
                    .split(" "))
                    .mapToInt(Integer::valueOf)
                    .toArray();

            final String[] ap = afterStr.split(":");
            final int[] after = Arrays.stream(ap[1].trim()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .replaceAll(",", "")
                    .split(" "))
                    .mapToInt(Integer::valueOf)
                    .toArray();

            return new Sample(before, Instruction.parse(instructionStr), after);
        }
    }

    public static Map<Sample, List<String>> findMatchingOpcodesForSamples(final List<Sample> samples) {
        return samples.stream().collect(Collectors.toMap(Function.identity(), Sample::findMatchingOpcodes));
    }

    private static Map<Integer, Opcode> identifyFromSamples(final Map<Sample, List<String>> matchingOpcodesForSamples) {
        final Map<Integer, Opcode> identifiedOpcodes = new HashMap<>();

        final List<Opcode> unidentifiedOpcodes = Arrays.stream(opcodes).collect(Collectors.toList());
        while (!unidentifiedOpcodes.isEmpty()) {
            // find all samples with 1 matching opcode
            final Stream<Map.Entry<Sample, List<String>>> identifiableSamples = matchingOpcodesForSamples.entrySet().stream()
                    .filter(e -> e.getValue().size() == 1);

            final Map<Integer, String> idToNameMap = new HashMap<>();
            identifiableSamples.forEach(e -> idToNameMap.put(e.getKey().instruction.getOpcode(), e.getValue().get(0)));

            idToNameMap.forEach((id, name) -> {
                // mark each entry in the map as identified
                final Opcode identifiedOpcode = Opcode.get(name);
                identifiedOpcodes.put(id, identifiedOpcode);
                unidentifiedOpcodes.remove(identifiedOpcode);

                // remove this entry from matchingOpcodesForSamplesMap
                for (final Map.Entry<Sample, List<String>> entry : matchingOpcodesForSamples.entrySet()) {
                    entry.getValue().remove(name);
                }
            });
        }

        return identifiedOpcodes;
    }

    public static void main(String[] args) throws IOException {
        int blankLineCount = 0;
        final Iterator<String> iterator = Utils.read("day16.txt").iterator();
        final List<Sample> samples = new ArrayList<>();
        final List<Instruction> instructions = new ArrayList<>();
        boolean parsingInstructions = false;
        while (iterator.hasNext()) {
            if (parsingInstructions) {
                instructions.add(Instruction.parse(iterator.next()));
            } else {
                final String before = iterator.next();
                if (!before.isEmpty()) {
                    blankLineCount = 0;
                    final String instruction = iterator.next();
                    final String after = iterator.next();
                    samples.add(Sample.parse(before, instruction, after));
                } else {
                    blankLineCount++;
                    if (blankLineCount == 3) {
                        parsingInstructions = true; // no more sample data
                    }
                }
            }
        }

        final Map<Sample, List<String>> matchingOpcodesForSamples = findMatchingOpcodesForSamples(samples);
        System.out.println(matchingOpcodesForSamples.entrySet().stream().filter(e -> e.getValue().size() >= 3).count());

        final Map<Integer, Opcode> idToOpcodeMap = identifyFromSamples(matchingOpcodesForSamples);

        r0.setValue(0);
        r1.setValue(0);
        r2.setValue(0);
        r3.setValue(0);

        // execute all instructions
        instructions.forEach(i -> idToOpcodeMap.get(i.getOpcode()).apply(i));

        System.out.println(Arrays.toString(registerValues()));
    }

}
