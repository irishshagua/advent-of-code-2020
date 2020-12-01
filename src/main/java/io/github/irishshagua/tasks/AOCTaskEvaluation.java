package io.github.irishshagua.tasks;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;

import java.nio.file.Path;

public interface AOCTaskEvaluation {

    TaskResult run(Path input) throws TaskNotImplementedException;
}
