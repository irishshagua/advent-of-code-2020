package io.github.irishshagua.tasks;

import java.nio.file.Path;

public record TaskDetails(Path taskDescription, Path taskInput, AOCTaskEvaluation taskRunner) {}
