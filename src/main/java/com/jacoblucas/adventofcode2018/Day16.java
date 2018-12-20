package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day16 {

    private static final Register r0 = new Register(0, 0);
    private static final Register r1 = new Register(1, 0);
    private static final Register r2 = new Register(2, 0);
    private static final Register r3 = new Register(3, 0);
    private static final Register[] registers = new Register[]{r0, r1, r2, r3};

    private static int[] registerValues() {
        return Arrays.stream(registers)
                .mapToInt(Register::getValue)
                .toArray();
    }

    static final Opcode[] opcodes = new Opcode[]{
            new addr(), new addi(),
            new mulr(), new muli(),
            new banr(), new bani(),
            new borr(), new bori(),
            new setr(), new seti(),
            new gtir(), new gtri(), new gtrr(),
            new eqir(), new eqri(), new eqrr()
    };

    @Data
    @AllArgsConstructor
    static class Register {
        private int id;
        private int value;

        @Override
        public String toString() {
            return "R" + id + "=" + value;
        }
    }

    @Data
    @AllArgsConstructor
    static class Instruction {
        private int opcode;
        private int inputA;
        private int inputB;
        private int output;

        @Override
        public String toString() {
            return "opcode=" + opcode + " A=" + inputA + " B=" + inputB + " Out=R" + output;
        }
    }

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
                    .collect(Collectors.toMap(Function.identity(), opcode -> opcode.apply(beforeRegisterValues, instruction)))
                    .entrySet()
                    .stream()
                    .filter(e -> Arrays.equals(e.getValue(), afterRegisterValues))
                    .map(e -> e.getKey().name())
                    .collect(Collectors.toList());
        }

        public static Sample parse(final String beforeStr, final String instructionStr, final String afterStr) {
            String[] bp = beforeStr.split(":");
            int[] before = Arrays.stream(bp[1].trim()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .replaceAll(",", "")
                    .split(" "))
                    .mapToInt(Integer::valueOf)
                    .toArray();

            int[] ip = Arrays.stream(instructionStr.split(" "))
                    .mapToInt(Integer::valueOf)
                    .toArray();
            Instruction instruction = new Instruction(ip[0], ip[1], ip[2], ip[3]);

            String[] ap = afterStr.split(":");
            int[] after = Arrays.stream(ap[1].trim()
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .replaceAll(",", "")
                    .split(" "))
                    .mapToInt(Integer::valueOf)
                    .toArray();

            return new Sample(before, instruction, after);
        }
    }

    abstract static class Opcode {
        String name() {
            return this.getClass().getSimpleName();
        }

        abstract int[] apply(final int[] input, Instruction instruction);

        void setRegisterValuesFromInput(final int[] input) {
            r0.setValue(input[0]);
            r1.setValue(input[1]);
            r2.setValue(input[2]);
            r3.setValue(input[3]);
        }
    }

    // stores into register C the result of adding register A and register B.
    static class addr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() + registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    // stores into register C the result of adding register A and value B.
    static class addi extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() + instruction.inputB);
            return registerValues();
        }
    }

    // stores into register C the result of multiplying register A and register B.
    static class mulr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() * registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    // stores into register C the result of multiplying register A and value B.
    static class muli extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() * instruction.inputB);
            return registerValues();
        }
    }

    static class banr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() & registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    static class bani extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() & instruction.inputB);
            return registerValues();
        }
    }

    static class borr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() | registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    static class bori extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() | instruction.inputB);
            return registerValues();
        }
    }

    static class setr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue());
            return registerValues();
        }
    }

    static class seti extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(instruction.inputA);
            return registerValues();
        }
    }

    static class gtir extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(instruction.inputA > registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    static class gtri extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() > instruction.inputB ? 1 : 0);
            return registerValues();
        }
    }

    static class gtrr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() > registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    static class eqir extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(instruction.inputA == registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    static class eqri extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() == instruction.inputB ? 1 : 0);
            return registerValues();
        }
    }

    static class eqrr extends Opcode {
        @Override
        int[] apply(int[] input, Instruction instruction) {
            setRegisterValuesFromInput(input);
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() == registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    public static Map<Sample, List<String>> findMatchingOpcodesForSamples(final List<Sample> samples) {
        return samples.stream()
                .collect(Collectors.toMap(Function.identity(), Sample::findMatchingOpcodes));
    }

    public static void main(String[] args) throws IOException {
        int blankLineCount = 0;
        final Iterator<String> iterator = Utils.read("day16.txt").iterator();
        final List<Sample> samples = new ArrayList<>();
        while (iterator.hasNext()) {
            final String before = iterator.next();
            if (!before.isEmpty()) {
                blankLineCount = 0;
                final String instruction = iterator.next();
                final String after = iterator.next();
                samples.add(Sample.parse(before, instruction, after));
            } else {
                blankLineCount++;
                if (blankLineCount == 3) {
                    break; // no more sample data
                }
            }
        }

        final Map<Sample, List<String>> matchingOpcodesForSamples = findMatchingOpcodesForSamples(samples);
        System.out.println(matchingOpcodesForSamples.entrySet().stream().filter(e -> e.getValue().size() >= 3).count());
    }

}
