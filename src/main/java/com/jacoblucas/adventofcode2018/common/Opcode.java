package com.jacoblucas.adventofcode2018.common;

import static com.jacoblucas.adventofcode2018.common.Register.registerValues;
import static com.jacoblucas.adventofcode2018.common.Register.registers;

public abstract class Opcode {

    public static final Opcode[] opcodes = new Opcode[]{
            new addr(), new addi(),
            new mulr(), new muli(),
            new banr(), new bani(),
            new borr(), new bori(),
            new setr(), new seti(),
            new gtir(), new gtri(), new gtrr(),
            new eqir(), new eqri(), new eqrr()
    };

    public abstract int[] apply(final Instruction instruction);

    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Opcode)) {
            return false;
        }

        final Opcode opcode = (Opcode) obj;
        return name().equals(opcode.name());
    }

    @Override
    public String toString() {
        return name();
    }

    public static Opcode get(final String name) {
        switch (name) {
            case "addr":
                return opcodes[0];
            case "addi":
                return opcodes[1];
            case "mulr":
                return opcodes[2];
            case "muli":
                return opcodes[3];
            case "banr":
                return opcodes[4];
            case "bani":
                return opcodes[5];
            case "borr":
                return opcodes[6];
            case "bori":
                return opcodes[7];
            case "setr":
                return opcodes[8];
            case "seti":
                return opcodes[9];
            case "gtir":
                return opcodes[10];
            case "gtri":
                return opcodes[11];
            case "gtrr":
                return opcodes[12];
            case "eqir":
                return opcodes[13];
            case "eqri":
                return opcodes[14];
            case "eqrr":
                return opcodes[15];
            default:
                return null;
        }
    }

    // stores into register C the result of adding register A and register B.
    static class addr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() + registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    // stores into register C the result of adding register A and value B.
    static class addi extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() + instruction.inputB);
            return registerValues();
        }
    }

    // stores into register C the result of multiplying register A and register B.
    static class mulr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() * registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    // stores into register C the result of multiplying register A and value B.
    static class muli extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() * instruction.inputB);
            return registerValues();
        }
    }

    static class banr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() & registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    static class bani extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() & instruction.inputB);
            return registerValues();
        }
    }

    static class borr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() | registers[instruction.inputB].getValue());
            return registerValues();
        }
    }

    static class bori extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() | instruction.inputB);
            return registerValues();
        }
    }

    static class setr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue());
            return registerValues();
        }
    }

    static class seti extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(instruction.inputA);
            return registerValues();
        }
    }

    static class gtir extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(instruction.inputA > registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    static class gtri extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() > instruction.inputB ? 1 : 0);
            return registerValues();
        }
    }

    static class gtrr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() > registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    static class eqir extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(instruction.inputA == registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

    static class eqri extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() == instruction.inputB ? 1 : 0);
            return registerValues();
        }
    }

    static class eqrr extends Opcode {
        @Override
        public int[] apply(final Instruction instruction) {
            registers[instruction.output].setValue(registers[instruction.inputA].getValue() == registers[instruction.inputB].getValue() ? 1 : 0);
            return registerValues();
        }
    }

}
