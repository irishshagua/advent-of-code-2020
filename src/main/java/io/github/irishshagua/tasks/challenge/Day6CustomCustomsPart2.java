package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Day6CustomCustomsPart2 implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var lines = IO.readLinesAs(input, String::toString);
        var start = LocalDateTime.now();
        var result = algo(parse(lines));
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<Group> groups) {
        return groups
                .stream()
                .mapToInt(g -> g.positiveAnswered().size())
                .sum();
    }

    private List<Group> parse(List<String> lines) {
        var groups = new ArrayList<Group>();
        var groupAnswers = new HashSet<Character>();
        var firstInGroup = true;

        for (String line : lines) {
            if (line.isEmpty()) {
                groups.add(new Group(groupAnswers));
                groupAnswers = new HashSet<>();
                firstInGroup = true;
                continue;
            }

            if (firstInGroup) {
                for (Character c : line.toCharArray()) {
                    groupAnswers.add(c);
                }
                firstInGroup = false;
            } else {
                var toBeRemoved = new ArrayList<Character>(groupAnswers.size());
                for (Character c : groupAnswers) {
                    if (line.indexOf(c) == -1) {
                        toBeRemoved.add(c);
                    }
                }
                groupAnswers.removeAll(toBeRemoved);
            }
        }

        return groups;
    }
}

