package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Day8HandheldHalting implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var data = IO.readLinesAs(input, this::parse);
        var start = LocalDateTime.now();
        var result = algo(data);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<Instruction> instructions) {
        int acc = 0;
        int  i = 0;
        var invocationHistory = new HashMap<Integer, Boolean>();
        while(true) {
            if (invocationHistory.getOrDefault(i, false)) break;
            else invocationHistory.put(i, true);

            var ins = instructions.get(i);
            switch (ins.command()) {
                case NOP -> i += 1;
                case ACC -> {
                    acc += ins.value();
                    i += 1;
                }
                case JMP -> {
                    i += ins.value();
                }
            }
        }

        return acc;
    }

    private Instruction parse(String line) {
        var parts = line.split("\\s");
        return switch (parts[0]) {
            case "nop" -> new Instruction(Command.NOP, 0);
            case "acc" -> new Instruction(Command.ACC, Integer.parseInt(parts[1]));
            case "jmp" -> new Instruction(Command.JMP, Integer.parseInt(parts[1]));
            default    -> throw new IllegalArgumentException("Cannot parse ["+line+"]");
        };
    }
}

enum Command {
    NOP,
    ACC,
    JMP;
}
record Instruction(Command command, int value) {}

