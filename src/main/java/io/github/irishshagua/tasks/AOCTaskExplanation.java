package io.github.irishshagua.tasks;

import io.github.irishshagua.app.exceptions.TaskNotDocumentedException;

public interface AOCTaskExplanation extends AOCTask {

    TaskResult run() throws TaskNotDocumentedException;
}
