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

public class Day2PasswordPhilosophy implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        try {
            var expenseEntries = IO.readLinesAs(input, this::decode);
            var start = LocalDateTime.now();
            var result = algo(expenseEntries);
            var stop = LocalDateTime.now();

            if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
            else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));

        } catch(Exception e) {
            throw new TaskNotImplementedException(3);
        }
    }

    private Long algo(List<PasswordCypherPair> pwdCypherPairs) {
        return pwdCypherPairs.stream().filter(p -> isValid(p.password(), p.cypher())).count();
    }

    private boolean isValid(String password, PasswordCypher cypher) {
        int i = 0;
        for (Character c : password.toCharArray()) {
            if (c.equals(cypher.requiredCharacter())) i++;
        }

        return i >= cypher.minOccurrences() && i <= cypher.maxOccurrences();
    }

    private PasswordCypherPair decode(String input) {
        var parts = input.split("\\s");
        var numerics = parts[0].split("-");
        var requiredChar = parts[1].charAt(0);

        return new PasswordCypherPair(
                parts[2],
                new PasswordCypher(
                        Integer.valueOf(numerics[0]),
                        Integer.valueOf(numerics[1]),
                        requiredChar
                )
        );
    }
}

record PasswordCypher(Integer minOccurrences, Integer maxOccurrences, Character requiredCharacter) {}
record PasswordCypherPair(String password, PasswordCypher cypher) {}