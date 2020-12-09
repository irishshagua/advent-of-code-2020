package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day9EncodingError implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var data = IO.readLinesAs(input, Long::parseLong);
        var start = LocalDateTime.now();
        var result = algo(data);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Long algo(List<Long> sequence) {
        return findInvalid(sequence, 25);
    }

    private Long findInvalid(List<Long> sequence, int preambleLength) {
        var preamble = sequence.stream().limit(preambleLength).collect(Collectors.toList());
        for (int i = preambleLength; i < sequence.size(); i++) {
            var num = sequence.get(i);
            if (!isValid(num, preamble)) return num;

            preamble.remove(0);
            preamble.add(num);
        }

        return -1L;
    }

    private boolean isValid(Long number, List<Long> preamble) {
        for (int i = 0; i < preamble.size(); i++) {
            var test = preamble.get(i);
            var idx = preamble.indexOf(number - test);
            if (idx != i && idx != -1) return true;
        }

        return false;
    }
}

