package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.app.io.LogLevel;
import io.github.irishshagua.app.io.LogTarget;
import io.github.irishshagua.app.io.Output;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Day1ReportRepairPart2 implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var expenseEntries = IO.readLinesAs(input, Integer::valueOf);
        var start = LocalDateTime.now();
        var result = algo(expenseEntries);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<Integer> expenseEntries) {
        for (int i = 0; i < expenseEntries.size(); i++) {
            for (int j = i + 1; j < expenseEntries.size(); j++) {
                for (int k = j + 1; k < expenseEntries.size(); k++) {
                    var f = expenseEntries.get(i);
                    var s = expenseEntries.get(j);
                    var t = expenseEntries.get(k);
                    if (f + s + t == 2020) {
                        Output.print("f ["+f+"], s ["+s+"], t ["+t+"]", LogTarget.CONSOLE_ONLY, LogLevel.INFO);
                        return f * s * t;
                    }
                }
            }
        }

        return -1;
    }
}
