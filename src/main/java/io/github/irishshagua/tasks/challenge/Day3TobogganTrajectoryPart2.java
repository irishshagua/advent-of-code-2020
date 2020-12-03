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
import java.util.Map;

public class Day3TobogganTrajectoryPart2 implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var expenseEntries = IO.readLinesAs(input, this::parseToRowPattern);
        var start = LocalDateTime.now();
        var result = algo(expenseEntries, List.of(
                new TraversalPattern(1, 1),
                new TraversalPattern(1, 3),
                new TraversalPattern(1, 5),
                new TraversalPattern(1, 7),
                new TraversalPattern(2, 1))
        );
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Long algo(List<Pattern> grid, List<TraversalPattern> patterns) {
        long runningTotal = 1;
        for (TraversalPattern pattern : patterns) {
            int colRef = pattern.across();
            int numTrees = 0;
            for (int i = pattern.down(); i < grid.size(); i += pattern.down()) {
                if (grid.get(i).isTree(colRef)) numTrees++;
                colRef += pattern.across();
            }

            runningTotal *= numTrees;
        }

        return runningTotal;
    }

    private Pattern parseToRowPattern(String text) {
        var map = new HashMap<Integer, Boolean>(text.length());
        for (int i = 0; i < text.length(); i++) {
            map.put(i, text.charAt(i) == '#');
        }

        return new Pattern(map);
    }
}

record TraversalPattern(int down, int across) {}