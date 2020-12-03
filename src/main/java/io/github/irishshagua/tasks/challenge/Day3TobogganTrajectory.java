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

public class Day3TobogganTrajectory implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        try {
            var expenseEntries = IO.readLinesAs(input, this::parseToRowPattern);
            var start = LocalDateTime.now();
            var result = algo(expenseEntries);
            var stop = LocalDateTime.now();

            if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
            else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));

        } catch(Exception e) {
            throw new TaskNotImplementedException(5);
        }
    }

    private Integer algo(List<Pattern> grid) {
        int colRef = 3;
        int numTrees = 0;
        for (int i = 1; i < grid.size(); i++) {
            if (grid.get(i).isTree(colRef)) numTrees++;
            colRef += 3;
        }

        return numTrees;
    }

    private Pattern parseToRowPattern(String text) {
        var map = new HashMap<Integer, Boolean>(text.length());
        for (int i = 0; i < text.length(); i++) {
            map.put(i, text.charAt(i) == '#');
        }

        return new Pattern(map);
    }
}

record Pattern(Map<Integer, Boolean> pattern) {
    public boolean isTree(int col) {
        var localizedIndex = col % pattern.size();
        return pattern.get(localizedIndex);
    }
}

