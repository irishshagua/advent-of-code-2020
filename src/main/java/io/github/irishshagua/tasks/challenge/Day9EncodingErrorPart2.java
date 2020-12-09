package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9EncodingErrorPart2 implements AOCTaskEvaluation {

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
        var contig = findContiguous(sequence, 257342611);
        var outliers = outliers(contig);
        return outliers.smallest() + outliers.largest();
    }

    private List<Long> findContiguous(List<Long> sequence, long target) {
        for (int i = 0; i < sequence.size(); i++) {
            int j = i + 1;
            var set = new ArrayList<Long>();
            long sum = sequence.get(i);
            set.add(sum);
            while (sum < target && j < sequence.size()) {
                var add = sequence.get(j++);
                sum += add;
                set.add(add);
                if (sum == target) return set;
            }
        }

        return List.of();
    }

    private Outliers outliers(List<Long> sequence) {
        long smallest = Long.MAX_VALUE, largest = Long.MIN_VALUE;
        for (Long num : sequence) {
            if (num < smallest) smallest = num;
            if (num > largest) largest = num;
        }

        return new Outliers(smallest, largest);
    }
}

record Outliers(Long smallest, Long largest) {}